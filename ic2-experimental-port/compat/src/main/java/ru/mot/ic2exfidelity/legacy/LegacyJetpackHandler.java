package ru.mot.ic2exfidelity.legacy;

import ic2.api.item.ElectricItem;
import ic2.api.item.IBackupElectricItemManager;
import ic2.api.item.IElectricItem;
import ic2.core.init.MainConfig;
import ic2.core.item.armor.jetpack.IJetpack;
import ic2.core.ref.Ic2Items;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Jetpack attachment marker, backup EU manager and event bridge from IC2 2.8.222. */
public final class LegacyJetpackHandler implements IBackupElectricItemManager {
    private static final Set<Item> BLACKLISTED_ITEMS =
            Collections.newSetFromMap(new IdentityHashMap<>());
    private static final Map<Player, ItemStack> PLAYER_ARMOR_BUFFER = new WeakHashMap<>();
    private static LegacyJetpackHandler instance;

    private boolean internalHandlesCheck;

    private LegacyJetpackHandler() {
    }

    public static synchronized void install() {
        if (instance != null) {
            return;
        }

        BLACKLISTED_ITEMS.add(Ic2Items.JETPACK);
        BLACKLISTED_ITEMS.add(RestoredLegacyContent.ELECTRIC_JETPACK.get());
        BLACKLISTED_ITEMS.add(Ic2Items.QUANTUM_CHESTPLATE);
        List<ItemStack> configured = ConfigUtil.asStackList(
                MainConfig.get(), "recipes/jetpackAttachmentBlacklist");
        if (configured != null) {
            for (ItemStack stack : configured) {
                if (stack != null && !stack.m_41619_()) {
                    BLACKLISTED_ITEMS.add(stack.m_41720_());
                }
            }
        }

        instance = new LegacyJetpackHandler();
        ElectricItem.registerBackupManager(instance);
        MinecraftForge.EVENT_BUS.register(instance);
    }

    public static boolean isBlacklisted(Item item) {
        return BLACKLISTED_ITEMS.contains(item);
    }

    public static void setJetpackAttached(ItemStack stack, boolean attached) {
        if (stack == null) {
            return;
        }
        if (!attached) {
            if (!stack.m_41782_()) {
                return;
            }
            stack.m_41783_().m_128473_("hasIC2Jetpack");
            if (stack.m_41783_().m_128456_()) {
                stack.m_41751_(null);
            }
        } else if (LivingEntity.m_147233_(stack) == EquipmentSlot.CHEST) {
            StackUtil.getOrCreateNbtData(stack).m_128379_("hasIC2Jetpack", true);
        }
    }

    public static boolean hasJetpackAttached(ItemStack stack) {
        return stack != null
                && LivingEntity.m_147233_(stack) == EquipmentSlot.CHEST
                && stack.m_41782_()
                && stack.m_41783_().m_128471_("hasIC2Jetpack");
    }

    public static boolean hasJetpack(ItemStack stack) {
        return stack != null
                && (hasJetpackAttached(stack) || stack.m_41720_() instanceof IJetpack);
    }

    public static IJetpack getJetpack(ItemStack stack) {
        if (stack.m_41720_() instanceof IJetpack jetpack) {
            return jetpack;
        }
        return (IJetpack) RestoredLegacyContent.ELECTRIC_JETPACK.get();
    }

    private static ItemStack jetpackStack() {
        return new ItemStack(RestoredLegacyContent.ELECTRIC_JETPACK.get());
    }

    private static double getTransferLimit() {
        ItemStack jetpack = jetpackStack();
        return ((IElectricItem) jetpack.m_41720_()).getTransferLimit(jetpack);
    }

    @Override
    public double charge(
            ItemStack stack,
            double amount,
            int tier,
            boolean ignoreTransferLimit,
            boolean simulate) {
        if (getTier(stack) > tier) {
            return 0.0;
        }
        if (!ignoreTransferLimit) {
            amount = Math.min(amount, getTransferLimit());
        }
        double stored = stack.m_41782_() ? stack.m_41783_().m_128459_("charge") : 0.0;
        amount = Math.max(0.0, Math.min(amount, getMaxCharge(stack) - stored));
        if (!simulate && amount > 0.0) {
            StackUtil.getOrCreateNbtData(stack).m_128347_("charge", stored + amount);
        }
        return amount;
    }

    @Override
    public double discharge(
            ItemStack stack,
            double amount,
            int tier,
            boolean ignoreTransferLimit,
            boolean externally,
            boolean simulate) {
        if (externally || getTier(stack) > tier || !stack.m_41782_()) {
            return 0.0;
        }
        if (!ignoreTransferLimit) {
            amount = Math.min(amount, getTransferLimit());
        }
        double stored = stack.m_41783_().m_128459_("charge");
        amount = Math.max(0.0, Math.min(amount, stored));
        if (!simulate && amount > 0.0) {
            double remaining = stored - amount;
            if (remaining == 0.0) {
                stack.m_41783_().m_128473_("charge");
                if (stack.m_41783_().m_128456_()) {
                    stack.m_41751_(null);
                }
            } else {
                stack.m_41783_().m_128347_("charge", remaining);
            }
        }
        return amount;
    }

    @Override
    public double getCharge(ItemStack stack) {
        return discharge(stack, Double.MAX_VALUE, Integer.MAX_VALUE, true, false, true);
    }

    @Override
    public double getStackCharge(ItemStack stack) {
        return stack.m_41782_() ? stack.m_41783_().m_128459_("charge") : 0.0;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return ElectricItem.manager.getMaxCharge(jetpackStack());
    }

    @Override
    public boolean canUse(ItemStack stack, double amount) {
        return ElectricItem.rawManager.canUse(stack, amount);
    }

    @Override
    public boolean use(ItemStack stack, double amount, LivingEntity entity) {
        return ElectricItem.rawManager.use(stack, amount, entity);
    }

    @Override
    public void chargeFromArmor(ItemStack stack, LivingEntity entity) {
    }

    @Override
    public String getToolTip(ItemStack stack) {
        return ElectricItem.rawManager.getToolTip(stack);
    }

    @Override
    public int getTier(ItemStack stack) {
        return ElectricItem.manager.getTier(jetpackStack());
    }

    @Override
    public synchronized boolean handles(ItemStack stack) {
        if (internalHandlesCheck) {
            return false;
        }
        internalHandlesCheck = true;
        try {
            return hasJetpackAttached(stack) && ElectricItem.manager.getMaxCharge(stack) <= 0.0;
        } finally {
            internalHandlesCheck = false;
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        ItemStack chest = event.player.m_6844_(EquipmentSlot.CHEST);
        if (hasJetpack(chest)) {
            LegacyJetpackLogic.onArmorTick(event.player, chest, getJetpack(chest));
        }

        if (!event.player.m_20193_().f_46443_) {
            ItemStack previous = PLAYER_ARMOR_BUFFER.remove(event.player);
            if (previous != null
                    && hasJetpackAttached(previous)
                    && chest.m_41619_()) {
                ItemStack replacement = jetpackStack();
                ElectricItem.manager.charge(
                        replacement,
                        ElectricItem.manager.getCharge(previous),
                        Integer.MAX_VALUE,
                        true,
                        false);
                event.player.m_8061_(EquipmentSlot.CHEST, replacement);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onTooltip(ItemTooltipEvent event) {
        if (hasJetpackAttached(event.getItemStack())) {
            event.getToolTip().add(
                    Component.m_237115_("ic2.jetpackAttached").m_130940_(ChatFormatting.YELLOW));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player
                && event.getSource() != null
                && !event.getSource().m_19379_()) {
            ItemStack chest = player.m_6844_(EquipmentSlot.CHEST);
            if (hasJetpackAttached(chest)) {
                PLAYER_ARMOR_BUFFER.put(player, chest.m_41777_());
            }
        }
    }
}

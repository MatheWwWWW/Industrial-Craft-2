/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.RenderLivingBase
 *  net.minecraft.client.renderer.entity.layers.LayerRenderer
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraftforge.client.event.RenderLivingEvent$Post
 *  net.minecraftforge.client.event.RenderLivingEvent$Pre
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.living.LivingAttackEvent
 *  net.minecraftforge.event.entity.player.ItemTooltipEvent
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.LoaderState
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.armor.jetpack;

import ic2.api.item.ElectricItem;
import ic2.api.item.IBackupElectricItemManager;
import ic2.api.item.IElectricItem;
import ic2.core.init.Localization;
import ic2.core.item.armor.jetpack.IJetpack;
import ic2.core.item.armor.jetpack.JetpackLogic;
import ic2.core.item.armor.jetpack.LayerJetpackOverride;
import ic2.core.ref.ItemName;
import ic2.core.util.ReflectionUtil;
import ic2.core.util.StackUtil;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JetpackHandler
implements IBackupElectricItemManager {
    private static Map<EntityPlayer, ItemStack> playerArmorBuffer = new WeakHashMap<EntityPlayer, ItemStack>();
    @SideOnly(value=Side.CLIENT)
    private static LayerJetpackOverride render;
    @SideOnly(value=Side.CLIENT)
    private static Field renderLayers;
    private boolean internalHandlesCheck = false;
    static final ItemStack jetpack;
    public static JetpackHandler instance;

    private JetpackHandler() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        ElectricItem.registerBackupManager(this);
    }

    public static void init() {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            throw new IllegalStateException();
        }
        instance = new JetpackHandler();
    }

    public static void setJetpackAttached(ItemStack stack, boolean value) {
        if (stack == null) {
            return;
        }
        if (!value) {
            if (!stack.func_77942_o()) {
                return;
            }
            stack.func_77978_p().func_82580_o("hasIC2Jetpack");
            if (stack.func_77978_p().func_82582_d()) {
                stack.func_77982_d(null);
            }
        } else if (EntityLiving.func_184640_d((ItemStack)stack) == EntityEquipmentSlot.CHEST) {
            StackUtil.getOrCreateNbtData(stack).func_74757_a("hasIC2Jetpack", true);
        }
    }

    public static boolean hasJetpackAttached(ItemStack stack) {
        return stack != null && EntityLiving.func_184640_d((ItemStack)stack) == EntityEquipmentSlot.CHEST && stack.func_77942_o() && stack.func_77978_p().func_74767_n("hasIC2Jetpack");
    }

    public static boolean hasJetpack(ItemStack stack) {
        return stack != null && (JetpackHandler.hasJetpackAttached(stack) || stack.func_77973_b() instanceof IJetpack);
    }

    public static IJetpack getJetpack(ItemStack stack) {
        assert (JetpackHandler.hasJetpack(stack));
        if (stack.func_77973_b() instanceof IJetpack) {
            return (IJetpack)stack.func_77973_b();
        }
        return (IJetpack)jetpack.func_77973_b();
    }

    public static double getTransferLimit() {
        return ((IElectricItem)jetpack.func_77973_b()).getTransferLimit(jetpack);
    }

    @Override
    public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
        if (this.getTier(stack) > tier) {
            return 0.0;
        }
        if (!ignoreTransferLimit) {
            amount = Math.min(amount, JetpackHandler.getTransferLimit());
        }
        double charge = stack.func_77942_o() ? stack.func_77978_p().func_74769_h("charge") : 0.0;
        amount = Math.min(amount, this.getMaxCharge(stack) - charge);
        if (!simulate) {
            StackUtil.getOrCreateNbtData(stack).func_74780_a("charge", charge + amount);
        }
        return amount;
    }

    @Override
    public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
        if (externally || this.getTier(stack) > tier || !stack.func_77942_o()) {
            return 0.0;
        }
        if (!ignoreTransferLimit) {
            amount = Math.min(amount, JetpackHandler.getTransferLimit());
        }
        double charge = stack.func_77978_p().func_74769_h("charge");
        amount = Math.min(amount, charge);
        if (!simulate) {
            if ((charge -= amount) == 0.0) {
                stack.func_77978_p().func_82580_o("charge");
                if (stack.func_77978_p().func_82582_d()) {
                    stack.func_77982_d(null);
                }
            } else {
                stack.func_77978_p().func_74780_a("charge", charge);
            }
        }
        return amount;
    }

    @Override
    public double getCharge(ItemStack stack) {
        return this.discharge(stack, Double.MAX_VALUE, Integer.MAX_VALUE, true, false, true);
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return ElectricItem.manager.getMaxCharge(jetpack.func_77946_l());
    }

    @Override
    public boolean canUse(ItemStack stack, double amount) {
        return ElectricItem.rawManager.canUse(stack, amount);
    }

    @Override
    public boolean use(ItemStack stack, double amount, EntityLivingBase entity) {
        return ElectricItem.rawManager.use(stack, amount, entity);
    }

    @Override
    public void chargeFromArmor(ItemStack stack, EntityLivingBase entity) {
    }

    @Override
    public String getToolTip(ItemStack stack) {
        return ElectricItem.rawManager.getToolTip(stack);
    }

    @Override
    public int getTier(ItemStack stack) {
        return ElectricItem.manager.getTier(jetpack.func_77946_l());
    }

    @Override
    public synchronized boolean handles(ItemStack stack) {
        if (this.internalHandlesCheck) {
            return false;
        }
        this.internalHandlesCheck = true;
        boolean handle = JetpackHandler.hasJetpackAttached(stack) && ElectricItem.manager.getMaxCharge(stack) <= 0.0;
        this.internalHandlesCheck = false;
        return handle;
    }

    @SubscribeEvent
    public void tick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        ItemStack stack = event.player.func_184582_a(EntityEquipmentSlot.CHEST);
        if (JetpackHandler.hasJetpack(stack)) {
            JetpackLogic.onArmorTick(event.player.func_130014_f_(), event.player, stack, JetpackHandler.getJetpack(stack));
        }
        if (playerArmorBuffer.containsKey(event.player)) {
            ItemStack lastStack = playerArmorBuffer.get(event.player);
            if (StackUtil.isEmpty(lastStack) && JetpackHandler.hasJetpackAttached(lastStack) && StackUtil.isEmpty(stack)) {
                ItemStack newJetpack = jetpack.func_77946_l();
                double oldCharge = ElectricItem.manager.getCharge(lastStack);
                ElectricItem.manager.charge(newJetpack, oldCharge, Integer.MAX_VALUE, true, false);
                event.player.func_184201_a(EntityEquipmentSlot.CHEST, newJetpack);
            }
            playerArmorBuffer.remove(event.player);
        }
    }

    @SideOnly(value=Side.CLIENT)
    @SubscribeEvent(priority=EventPriority.HIGH)
    public void tooltip(ItemTooltipEvent event) {
        if (JetpackHandler.hasJetpackAttached(event.getItemStack())) {
            event.getToolTip().add(TextFormatting.YELLOW + Localization.translate("ic2.jetpackAttached"));
        }
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
    public void livingAttack(LivingAttackEvent event) {
        EntityPlayer player;
        ItemStack currentArmor;
        if (event.getEntityLiving() instanceof EntityPlayer && event.getSource() != null && !event.getSource().func_76363_c() && JetpackHandler.hasJetpackAttached(currentArmor = (player = (EntityPlayer)event.getEntityLiving()).func_184582_a(EntityEquipmentSlot.CHEST))) {
            playerArmorBuffer.put(player, currentArmor);
        }
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void render(RenderLivingEvent.Pre<EntityLivingBase> event) {
        EntityLivingBase entity = event.getEntity();
        if (JetpackHandler.hasJetpackAttached(entity.func_184582_a(EntityEquipmentSlot.CHEST))) {
            if (render == null) {
                render = new LayerJetpackOverride(event.getRenderer());
                renderLayers = ReflectionUtil.getField(RenderLivingBase.class, List.class);
            }
            event.getRenderer().func_177094_a((LayerRenderer)render);
        }
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void renderPost(RenderLivingEvent.Post<EntityLivingBase> event) {
        if (render != null) {
            ((List)ReflectionUtil.getFieldValue(renderLayers, event.getRenderer())).remove((Object)render);
        }
    }

    static {
        jetpack = ItemName.jetpack_electric.getItemStack();
    }
}


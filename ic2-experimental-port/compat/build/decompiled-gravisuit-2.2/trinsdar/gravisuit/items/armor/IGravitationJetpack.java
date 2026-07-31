/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.api.items.electric.ElectricItem
 *  ic2.core.item.wearable.armor.electric.QuantumSuit
 *  ic2.core.item.wearable.base.IC2ModularElectricArmor
 *  ic2.core.platform.player.PlayerHandler
 *  ic2.core.utils.tooltips.ILangHelper
 *  ic2.core.utils.tooltips.ToolTipHelper
 *  io.github.ladysnake.pal.AbilitySource
 *  io.github.ladysnake.pal.Pal
 *  io.github.ladysnake.pal.VanillaAbilities
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingTickEvent
 */
package trinsdar.gravisuit.items.armor;

import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.wearable.armor.electric.QuantumSuit;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.tooltips.ILangHelper;
import ic2.core.utils.tooltips.ToolTipHelper;
import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import trinsdar.gravisuit.items.armor.IHasOverlay;
import trinsdar.gravisuit.util.GravisuitKeys;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.IGravisuitPlayerHandler;

public interface IGravitationJetpack
extends ILangHelper,
IHasOverlay {
    public static final AbilitySource SOURCE = Pal.getAbilitySource((String)"gravisuit", (String)"gravisuit");
    public static final Map<Player, Boolean> playersWithFlight = new WeakHashMap<Player, Boolean>();

    default public boolean armorTick(ItemStack stack, Level world, Player player) {
        CompoundTag tag = this.nbtData(stack, true);
        boolean enabled = tag.m_128471_("engine_on");
        byte jetpackTicker = tag.m_128445_("JetpackTicker");
        PlayerHandler handler = PlayerHandler.getHandler((Player)player);
        Entity entity = player.m_20201_();
        boolean server = IC2.PLATFORM.isSimulating();
        if (((IGravisuitPlayerHandler)handler).isFlightKeyDown() && !handler.screenOpen && jetpackTicker <= 0) {
            tag.m_128344_("JetpackTicker", (byte)10);
            boolean disabled = !enabled;
            tag.m_128379_("engine_on", disabled);
            if (server) {
                String lang = disabled ? GravisuitLang.graviEngineOn : GravisuitLang.graviEngineOff;
                player.m_5661_((Component)this.translate(lang), false);
            }
            if (server && enabled && !player.m_7500_() && !player.m_5833_()) {
                SOURCE.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
            }
            enabled = disabled;
        }
        if (enabled) {
            if (ElectricItem.MANAGER.getCharge(stack) >= 512) {
                if (jetpackTicker > 0) {
                    jetpackTicker = (byte)(jetpackTicker - 1);
                    tag.m_128344_("JetpackTicker", jetpackTicker);
                }
                if (!player.m_7500_() && !player.m_5833_()) {
                    this.useEu(player, stack, player.m_150110_().f_35935_ ? 512 : 256);
                    if (ElectricItem.MANAGER.getCharge(stack) < 512) {
                        tag.m_128379_("ResetFlying", true);
                    }
                }
                if (server) {
                    SOURCE.grantTo(player, VanillaAbilities.ALLOW_FLYING);
                }
                boolean flying = player.m_150110_().f_35935_;
                Attribute flyingSpeed = Attributes.f_22280_;
                if (!flying || IGravitationJetpack.hasQuantumLegs(player)) {
                    // empty if block
                }
                return false;
            }
            if (tag.m_128441_("ResetFlying")) {
                tag.m_128473_("ResetFlying");
                if (server && !player.m_7500_() && !player.m_5833_()) {
                    SOURCE.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
                }
            }
        }
        return true;
    }

    @OnlyIn(value=Dist.CLIENT)
    default public void toolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(GravisuitKeys.G_KEY, GravisuitLang.graviEngineToggle, new Object[0]));
        boolean enabled = this.isJetpackEnabled(stack);
        String lang = enabled ? GravisuitLang.graviEngineOnInfo : GravisuitLang.graviEngineOffInfo;
        helper.addSimpleToolTip(lang, new Object[0]);
    }

    @OnlyIn(value=Dist.CLIENT)
    default public MutableComponent buildKeyDescription(KeyMapping key, String translationKey, Object ... args) {
        return this.buildKeyDescription(new Object[]{this.combineKeys(new KeyMapping[]{key}).m_130940_(ChatFormatting.GOLD), this.translate(translationKey, args).m_130940_(ChatFormatting.UNDERLINE)});
    }

    public CompoundTag nbtData(ItemStack var1, boolean var2);

    default public boolean isJetpackEnabled(ItemStack stack) {
        CompoundTag tag = this.nbtData(stack, false);
        if (tag == null) {
            return false;
        }
        return tag.m_128471_("engine_on");
    }

    default public void useEu(Player player, ItemStack stack, int amount) {
        if (stack.m_41720_() != this) {
            ElectricItem.MANAGER.discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
        } else if (!ElectricItem.MANAGER.use(stack, amount, (LivingEntity)player)) {
            ElectricItem.MANAGER.discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
        }
    }

    default public void onLivingTickEvent(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player) {
            Player player = (Player)livingEntity;
            if (!player.f_19853_.f_46443_) {
                boolean previousHasFlight;
                boolean flight;
                ItemStack stack = player.m_6844_(EquipmentSlot.CHEST);
                boolean bl = flight = this.isStackGravisuit(stack) && this.isJetpackEnabled(stack);
                if (!playersWithFlight.containsKey(player)) {
                    playersWithFlight.put(player, false);
                }
                if (!(previousHasFlight = playersWithFlight.get(player).booleanValue()) && flight) {
                    playersWithFlight.put(player, true);
                }
                if (previousHasFlight && !flight) {
                    playersWithFlight.put(player, false);
                    if (!player.m_7500_() && !player.m_5833_()) {
                        SOURCE.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
                    }
                }
            }
        }
    }

    private boolean isStackGravisuit(ItemStack stack) {
        IC2ModularElectricArmor armor;
        Item item;
        return stack.m_41720_() instanceof IGravitationJetpack || (item = stack.m_41720_()) instanceof IC2ModularElectricArmor && (armor = (IC2ModularElectricArmor)item).getInstalledModules(stack).stream().anyMatch(s -> s.m_41720_() instanceof IGravitationJetpack);
    }

    public static boolean hasQuantumLegs(Player player) {
        ItemStack legs = player.m_6844_(EquipmentSlot.LEGS);
        return !legs.m_41619_() && legs.m_41720_() instanceof QuantumSuit;
    }
}


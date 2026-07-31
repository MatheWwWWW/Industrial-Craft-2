/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.core.IC2;
import ic2.core.item.wearable.base.IArmorToolTip;
import ic2.core.item.wearable.modules.BaseModuleItem;
import ic2.core.item.wearable.modules.IModuleUseAction;
import ic2.core.platform.player.KeyHelper;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnergyShieldModuleItem
extends BaseModuleItem
implements IArmorToolTip,
IModuleUseAction {
    public static final String ALWAYS = "always";

    public EnergyShieldModuleItem(String textureFolder, String textureName) {
        super("energy_shield_overlay_module", null, textureFolder, textureName, IArmorModule.ModuleType.HUD);
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.HEAD;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.SNEAK_KEY, "tooltip.item.ic2.module_energy_shield", new Object[0]));
        helper.addSimpleToolTip(EnergyShieldModuleItem.isAlways(stack) ? "tooltip.item.ic2.module_energy_shield.enable" : "tooltip.item.ic2.module_energy_shield.disable", new Object[0]);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack armor, ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.SNEAK_KEY, "tooltip.item.ic2.module_energy_shield", new Object[0]));
        helper.addSimpleToolTip(EnergyShieldModuleItem.isAlways(armor) ? "tooltip.item.ic2.module_energy_shield.enable" : "tooltip.item.ic2.module_energy_shield.disable", new Object[0]);
    }

    @Override
    public boolean use(Level world, Player player, ItemStack module, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        if (module.m_41720_() == this && player.m_6144_()) {
            if (world.f_46443_) {
                return true;
            }
            this.onUninstall(module, armor, holder);
            boolean result = !EnergyShieldModuleItem.isAlways(module);
            EnergyShieldModuleItem.setAlways(module, result);
            if (IC2.PLATFORM.isSimulating()) {
                player.m_5661_((Component)this.translate(result ? "tooltip.item.ic2.module_energy_shield.enable" : "tooltip.item.ic2.module_energy_shield.disable"), false);
            }
            this.onInstall(module, armor, holder);
            return true;
        }
        return false;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        if (player.m_6144_()) {
            ItemStack stack = player.m_21120_(hand);
            boolean result = !EnergyShieldModuleItem.isAlways(stack);
            EnergyShieldModuleItem.setAlways(stack, result);
            if (IC2.PLATFORM.isSimulating()) {
                player.m_5661_((Component)this.translate(result ? "tooltip.item.ic2.module_energy_shield.enable" : "tooltip.item.ic2.module_energy_shield.disable"), false);
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(world, player, hand);
    }

    @Override
    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        boolean isAlways = EnergyShieldModuleItem.isAlways(stack);
        EnergyShieldModuleItem.setAlways(armor, isAlways);
        holder.addAddModifier(armor, isAlways ? IArmorModule.ArmorMod.ENERGY_SHIELD_ALWAYS : IArmorModule.ArmorMod.ENERGY_SHIELD, 1);
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        boolean isAlways = EnergyShieldModuleItem.isAlways(armor);
        EnergyShieldModuleItem.setAlways(stack, isAlways);
        armor.m_41749_(ALWAYS);
        holder.removeAddModifier(armor, isAlways ? IArmorModule.ArmorMod.ENERGY_SHIELD_ALWAYS : IArmorModule.ArmorMod.ENERGY_SHIELD, 1);
    }

    public static void setAlways(ItemStack stack, boolean value) {
        stack.m_41784_().m_128379_(ALWAYS, value);
    }

    public static boolean isAlways(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128471_(ALWAYS);
    }
}


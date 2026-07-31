/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
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
import ic2.core.block.misc.LightBlock;
import ic2.core.item.wearable.base.IArmorToolTip;
import ic2.core.item.wearable.modules.BaseModuleItem;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LightModuleItem
extends BaseModuleItem
implements IArmorToolTip {
    public static final String LIGHT = "light";

    public LightModuleItem(String textureFolder, String textureName) {
        super("light_module", null, textureFolder, textureName, IArmorModule.ModuleType.GENERIC);
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.HEAD;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        this.handleToolTip(stack, helper::addSimpleToolTip);
        helper.addKeybindingTooltip(this.buildKeyDescription(new KeyHelper[]{KeyHelper.SNEAK_KEY, KeyHelper.HUD_KEY, KeyHelper.MODE_KEY}, "tooltip.item.ic2.light.name", new Object[0]));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack armor, ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(new KeyHelper[]{KeyHelper.SNEAK_KEY, KeyHelper.HUD_KEY, KeyHelper.MODE_KEY}, "tooltip.item.ic2.light.name", new Object[0]));
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        armor.m_41749_(LIGHT);
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        CompoundTag nbt = armor.m_41698_(LIGHT);
        boolean enabled = nbt.m_128471_("enabled");
        PlayerHandler handler = PlayerHandler.getHandler(player);
        if (handler.hudModeKeyDown && handler.modeSwitchKeyDown && IC2.KEYBOARD.isSneakKeyDown(player) && world.m_46467_() - nbt.m_128454_("last") >= 5L) {
            nbt.m_128379_("enabled", !enabled);
            nbt.m_128356_("last", world.m_46467_());
            if (!world.f_46443_) {
                if (enabled) {
                    player.m_5661_((Component)this.translate("tooltip.item.ic2.light.disable"), false);
                } else {
                    player.m_5661_((Component)this.translate("tooltip.item.ic2.light.enable"), false);
                }
            }
        }
        if (enabled && LightBlock.placeLightBlock(world, player.m_20183_().m_7494_(), 5, () -> this.canUseEnergy(armor, 10))) {
            this.useEnergy(armor, 10, (LivingEntity)player);
        }
    }
}


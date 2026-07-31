/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.wearable.modules.BaseModuleItem;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpeedBoostModuleItem
extends BaseModuleItem {
    public SpeedBoostModuleItem(String textureFolder, String textureName) {
        super("speed_boost_module", null, textureFolder, textureName, IArmorModule.ModuleType.MOVEMENT);
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.LEGS;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        this.handleToolTip(stack, helper::addSimpleToolTip);
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        if (this.canUseEnergy(armor, 1000)) {
            PlayerHandler handler = PlayerHandler.getHandler(player);
            if (handler.doSpringBoost && player.m_20142_() || handler.boostKeyDown && handler.forwardKeyDown) {
                if (++handler.speedTicker % 10 == 0) {
                    this.useEnergy(armor, 1000, (LivingEntity)player);
                }
                player.m_19920_(player.m_20069_() ? 0.1f : (player.m_20096_() || player.m_21255_() ? 0.22f : 0.025f), new Vec3(0.0, 0.0, 1.0));
            }
        }
    }
}


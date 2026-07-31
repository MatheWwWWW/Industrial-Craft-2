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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class JumpBoostModuleItem
extends BaseModuleItem {
    public JumpBoostModuleItem(String textureFolder, String textureName) {
        super("jump_boost_module", null, textureFolder, textureName, IArmorModule.ModuleType.MOVEMENT);
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.FEET;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        this.handleToolTip(stack, helper::addSimpleToolTip);
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        PlayerHandler handler = PlayerHandler.getHandler(player);
        if (handler.jumpBoost < 1.0f && player.m_20096_() && this.canUseEnergy(armor, 1000)) {
            handler.jumpBoost = 1.0f;
            this.useEnergy(armor, 1000, (LivingEntity)player);
        }
        if (player.m_20184_().m_7098_() >= 0.0 && handler.jumpBoost > 0.0f && !player.m_20069_()) {
            if (handler.jumpKeyDown && handler.boostKeyDown) {
                double mod = handler.jumpBoost == 1.0f ? 1.75 : 1.0;
                player.m_20256_(player.m_20184_().m_82542_(mod, 1.0, mod).m_82520_(0.0, (double)handler.jumpBoost * 0.15, 0.0));
                handler.jumpBoost *= 0.75f;
            } else if (handler.jumpBoost < 1.0f) {
                handler.jumpBoost = 0.0f;
            }
        }
    }
}


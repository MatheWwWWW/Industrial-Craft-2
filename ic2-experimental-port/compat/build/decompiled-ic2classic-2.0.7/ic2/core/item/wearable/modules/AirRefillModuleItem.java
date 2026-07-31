/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.wearable.modules.BaseModuleItem;
import ic2.core.platform.registries.IC2Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AirRefillModuleItem
extends BaseModuleItem {
    public AirRefillModuleItem(String textureFolder, String textureName) {
        super("air_refill_module", null, textureFolder, textureName, IArmorModule.ModuleType.GENERIC);
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.HEAD;
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        int air = player.m_20146_();
        if (air < 100 && this.canUseEnergy(armor, 1000)) {
            player.m_20301_(air + 200);
            this.useEnergy(armor, 2000, (LivingEntity)player);
        } else if (air <= 0) {
            player.m_36220_(IC2Stats.DROWNED_WITH_Q_HELMET);
        }
    }
}


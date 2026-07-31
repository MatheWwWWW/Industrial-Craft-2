/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.wearable.base;

import ic2.api.items.armor.IArmorModule;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IBaseArmorModule
extends IArmorModule {
    @Override
    default public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return true;
    }

    @Override
    default public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
    }

    @Override
    default public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
    }

    @Override
    default public void transferToArmor(ItemStack stack, ItemStack oldArmor, ItemStack newArmor) {
    }

    @Override
    default public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
    }

    @Override
    default public void onEquipped(ItemStack stack, ItemStack armor, Player entity) {
    }

    @Override
    default public void onUnequipped(ItemStack stack, ItemStack armor, Player entity) {
    }

    @Override
    default public void provideCapabilities(ItemStack stack, ItemStack armor) {
    }
}


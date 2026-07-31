/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.wearable.modules.BaseModuleItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class EnergyHudModuleItem
extends BaseModuleItem {
    public EnergyHudModuleItem(String textureFolder, String textureName) {
        super("energy_hud_module", null, textureFolder, textureName, IArmorModule.ModuleType.HUD);
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.HEAD;
    }

    @Override
    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        holder.addAddModifier(armor, IArmorModule.ArmorMod.HUD_ENERGY, 1);
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        holder.removeAddModifier(armor, IArmorModule.ArmorMod.HUD_ENERGY, 1);
    }
}


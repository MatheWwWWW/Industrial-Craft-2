/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.api.items.electric.ElectricItem;
import ic2.core.item.wearable.modules.BaseModuleItem;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import net.minecraft.world.item.ItemStack;

public class BatteryModuleItem
extends BaseModuleItem
implements IArmorModule,
ISimpleItemModel {
    protected int capacity;
    protected int tier;
    protected int transferLimit;

    public BatteryModuleItem(String itemName, String folder, String texture, int capacity, int tier, int transferLimit) {
        super(itemName, null, folder, texture, IArmorModule.ModuleType.BATTERY);
        this.capacity = capacity;
        this.tier = tier;
        this.transferLimit = transferLimit;
    }

    @Override
    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        holder.addAddModifier(armor, IArmorModule.ArmorMod.ENERGY_STORAGE, this.capacity);
        holder.addAddModifier(armor, IArmorModule.ArmorMod.ENERGY_TIER, this.tier);
        holder.addAddModifier(armor, IArmorModule.ArmorMod.ENERGY_TRANSFER, this.transferLimit);
        ElectricItem.MANAGER.discharge(stack, ElectricItem.MANAGER.charge(armor, ElectricItem.MANAGER.getCharge(stack), Integer.MAX_VALUE, true, false), Integer.MAX_VALUE, true, false, false);
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        ElectricItem.MANAGER.discharge(armor, ElectricItem.MANAGER.charge(stack, ElectricItem.MANAGER.getCharge(armor), Integer.MAX_VALUE, true, false), Integer.MAX_VALUE, true, false, false);
        holder.removeAddModifier(armor, IArmorModule.ArmorMod.ENERGY_STORAGE, this.capacity);
        holder.removeAddModifier(armor, IArmorModule.ArmorMod.ENERGY_TIER, this.tier);
        holder.removeAddModifier(armor, IArmorModule.ArmorMod.ENERGY_TRANSFER, this.transferLimit);
    }
}


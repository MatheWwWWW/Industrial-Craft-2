/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.upgrades.energy;

import ic2.api.items.IUpgradeItem;
import ic2.api.tiles.IMachine;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.upgrades.base.BaseUpgradeItem;
import net.minecraft.world.item.ItemStack;

public class EnergyStorageMultiplierUpgradeItem
extends BaseUpgradeItem.SimpleUpgradeItem {
    public EnergyStorageMultiplierUpgradeItem() {
        super("energy_storage_multiplier", new PropertiesBuilder().maxStackSize(3));
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.MACHINE_MOD;
    }

    @Override
    public double getEnergyStorageMultiplier(ItemStack stack, IMachine machine) {
        return 2.0;
    }
}


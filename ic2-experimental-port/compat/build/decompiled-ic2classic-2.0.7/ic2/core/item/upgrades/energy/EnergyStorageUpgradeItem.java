/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.upgrades.energy;

import ic2.api.items.IUpgradeItem;
import ic2.api.tiles.IMachine;
import ic2.core.item.upgrades.base.BaseUpgradeItem;
import net.minecraft.world.item.ItemStack;

public class EnergyStorageUpgradeItem
extends BaseUpgradeItem.SimpleUpgradeItem {
    public EnergyStorageUpgradeItem() {
        super("energy_storage");
    }

    @Override
    public int getExtraEnergyStorage(ItemStack stack, IMachine machine) {
        return 10000;
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.MACHINE_MOD;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.upgrades.speed;

import ic2.api.items.IUpgradeItem;
import ic2.api.tiles.IMachine;
import ic2.core.item.upgrades.base.BaseUpgradeItem;
import net.minecraft.world.item.ItemStack;

public class OverclockerUpgradeItem
extends BaseUpgradeItem.SimpleUpgradeItem {
    public OverclockerUpgradeItem() {
        super("overclocker");
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.RECIPE_MOD;
    }

    @Override
    public double getProcessingSpeedMultiplier(ItemStack stack, IMachine machine) {
        return 1.3;
    }

    @Override
    public double getEnergyDemandMultiplier(ItemStack stack, IMachine machine) {
        return 1.6;
    }
}


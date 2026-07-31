/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.upgrades.redstone;

import ic2.api.items.IUpgradeItem;
import ic2.api.tiles.IMachine;
import ic2.core.item.upgrades.base.BaseUpgradeItem;
import net.minecraft.world.item.ItemStack;

public class RedstoneInverterUpgradeItem
extends BaseUpgradeItem.SimpleUpgradeItem {
    public RedstoneInverterUpgradeItem() {
        super("redstone_inverter");
    }

    @Override
    public boolean useRedstoneInvertion(ItemStack stack, IMachine machine) {
        return true;
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.REDSTONE_MOD;
    }
}


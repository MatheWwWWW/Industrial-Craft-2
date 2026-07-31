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

public class RedstoneSensitivityUpgradeItem
extends BaseUpgradeItem.SimpleUpgradeItem {
    public RedstoneSensitivityUpgradeItem() {
        super("redstone_sensitivity");
    }

    @Override
    public void onInstall(ItemStack stack, IMachine machine) {
        machine.setRedstoneSensitive(!machine.isRedstoneSensitive());
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.REDSTONE_MOD;
    }
}


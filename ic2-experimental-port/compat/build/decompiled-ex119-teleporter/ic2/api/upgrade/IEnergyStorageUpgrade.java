/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.upgrade;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.IUpgradeItem;
import net.minecraft.world.item.ItemStack;

public interface IEnergyStorageUpgrade
extends IUpgradeItem {
    public int getExtraEnergyStorage(ItemStack var1, IUpgradableBlock var2);

    public double getEnergyStorageMultiplier(ItemStack var1, IUpgradableBlock var2);
}


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

public interface IProcessingUpgrade
extends IUpgradeItem {
    public int getExtraProcessTime(ItemStack var1, IUpgradableBlock var2);

    public double getProcessTimeMultiplier(ItemStack var1, IUpgradableBlock var2);

    public int getExtraEnergyDemand(ItemStack var1, IUpgradableBlock var2);

    public double getEnergyDemandMultiplier(ItemStack var1, IUpgradableBlock var2);
}


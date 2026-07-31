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

public interface ITransformerUpgrade
extends IUpgradeItem {
    public int getExtraTier(ItemStack var1, IUpgradableBlock var2);
}


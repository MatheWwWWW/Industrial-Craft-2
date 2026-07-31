/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.upgrade;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import java.util.Collection;
import java.util.Set;
import net.minecraft.world.item.ItemStack;

public interface IUpgradeItem {
    public boolean isSuitableFor(ItemStack var1, Set<UpgradableProperty> var2);

    public boolean onTick(ItemStack var1, IUpgradableBlock var2);

    public Collection<ItemStack> onProcessEnd(ItemStack var1, IUpgradableBlock var2, Collection<ItemStack> var3);
}


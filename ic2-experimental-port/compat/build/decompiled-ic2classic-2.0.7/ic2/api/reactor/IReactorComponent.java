/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorProduct;
import net.minecraft.world.item.ItemStack;

public interface IReactorComponent
extends IReactorProduct {
    @Override
    default public boolean isValidForReactor(ItemStack stack, IReactor reactor) {
        return true;
    }

    public void processChamber(ItemStack var1, IReactor var2, int var3, int var4, boolean var5, boolean var6);

    public boolean acceptUraniumPulse(ItemStack var1, IReactor var2, ItemStack var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9);

    public boolean canStoreHeat(ItemStack var1, IReactor var2, int var3, int var4);

    public int getStoredHeat(ItemStack var1, IReactor var2, int var3, int var4);

    public int getMaxStoredHeat(ItemStack var1, IReactor var2, int var3, int var4);

    public int storeHeat(ItemStack var1, IReactor var2, int var3, int var4, int var5);

    public float getExplosionInfluence(ItemStack var1, IReactor var2);
}


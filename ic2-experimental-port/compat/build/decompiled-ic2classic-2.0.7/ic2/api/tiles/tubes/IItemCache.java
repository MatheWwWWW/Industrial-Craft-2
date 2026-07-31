/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.tiles.tubes;

import ic2.api.util.SidedObject;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;

public interface IItemCache {
    public static final SidedObject<IItemCache> CACHE = new SidedObject();

    public static IItemCache getCache() {
        return CACHE.get();
    }

    public Supplier<ItemStack> getItem(int var1);

    public int registerItem(ItemStack var1);

    public void updateCache();

    public void clearCache();
}


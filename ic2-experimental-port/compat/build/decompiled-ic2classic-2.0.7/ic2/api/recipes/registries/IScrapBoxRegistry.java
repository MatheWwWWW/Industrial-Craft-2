/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.api.recipes.registries;

import ic2.api.recipes.registries.IListenableRegistry;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public interface IScrapBoxRegistry
extends IListenableRegistry<IScrapBoxRegistry> {
    default public void addDrop(ItemLike prov, float chance) {
        if (prov == null) {
            return;
        }
        this.addDrop(new ItemStack(prov), chance);
    }

    public void addDrop(ItemStack var1, float var2);

    public IDrop getRandomDrop(ItemStack var1, boolean var2);

    public void removeDrops(ItemStack var1);

    public void removeDrop(IDrop var1);

    public List<IDrop> getAllDrops();

    public static interface IDrop {
        public ItemStack getDrop();

        public float getChance();

        public float getPoolChance();
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.inventory.filter;

import ic2.core.inventory.filter.IFilter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class SimpleFilter
implements IFilter {
    Item item;

    public SimpleFilter(ItemLike item) {
        this.item = item.m_5456_();
    }

    @Override
    public boolean matches(ItemStack input) {
        return input.m_41720_() == this.item;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.filter;

import ic2.core.inventory.filter.IFilter;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TagFilter
implements IFilter {
    TagKey<Item> tag;

    public TagFilter(TagKey<Item> tag) {
        this.tag = tag;
    }

    @Override
    public boolean matches(ItemStack input) {
        return input.m_204117_(this.tag);
    }
}


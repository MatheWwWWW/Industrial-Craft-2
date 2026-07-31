/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.filter;

import ic2.core.inventory.filter.IFilter;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SetItemFilter
implements IFilter {
    Set<Item> filter;
    boolean result;

    public SetItemFilter(Item ... item) {
        this((Set<Item>)new ObjectOpenHashSet((Object[])item), true);
    }

    public SetItemFilter(boolean result, Item ... item) {
        this((Set<Item>)new ObjectOpenHashSet((Object[])item), result);
    }

    public SetItemFilter(Set<Item> filter, boolean result) {
        this.filter = filter;
        this.result = result;
    }

    @Override
    public boolean matches(ItemStack input) {
        return !input.m_41619_() && this.filter.contains(input.m_41720_()) == this.result;
    }
}


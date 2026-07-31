/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectIterators
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.filter;

import ic2.core.inventory.filter.IFilter;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.world.item.ItemStack;

public class ArrayAndFilter
implements IFilter {
    ObjectList<IFilter> filters = CollectionUtils.createList();

    public ArrayAndFilter(Iterable<IFilter> iter) {
        ObjectIterators.pour(iter.iterator(), this.filters);
    }

    public ArrayAndFilter(IFilter ... filter) {
        this.filters.addAll((ObjectList)ObjectArrayList.wrap((Object[])filter));
    }

    @Override
    public boolean matches(ItemStack input) {
        int m = this.filters.size();
        for (int i = 0; i < m; ++i) {
            if (((IFilter)this.filters.get(i)).matches(input)) continue;
            return false;
        }
        return true;
    }
}


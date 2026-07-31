/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.filter;

import ic2.core.inventory.filter.IFilter;
import net.minecraft.world.item.ItemStack;

public class ClassFilter
implements IFilter {
    Class<?> clz;

    public ClassFilter(Class<?> clz) {
        this.clz = clz;
    }

    @Override
    public boolean matches(ItemStack input) {
        return this.clz.isInstance(input.m_41720_());
    }
}


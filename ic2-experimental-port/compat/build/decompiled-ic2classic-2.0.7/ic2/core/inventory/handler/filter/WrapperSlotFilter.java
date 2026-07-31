/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.handler.filter;

import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.handler.filter.ISlotFilter;
import net.minecraft.world.item.ItemStack;

public class WrapperSlotFilter
implements ISlotFilter {
    IFilter filter;

    public WrapperSlotFilter(IFilter filter) {
        this.filter = filter;
    }

    @Override
    public boolean matches(int slot, ItemStack stack) {
        return this.filter.matches(stack);
    }
}


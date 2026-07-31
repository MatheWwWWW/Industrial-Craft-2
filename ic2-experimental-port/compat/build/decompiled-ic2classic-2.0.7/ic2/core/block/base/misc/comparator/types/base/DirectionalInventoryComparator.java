/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.base.misc.comparator.types.base;

import ic2.core.block.base.misc.comparator.types.BaseDirectionalComparator;
import ic2.core.inventory.handler.IHasInventoryHandler;
import ic2.core.inventory.handler.InventoryHandler;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class DirectionalInventoryComparator
extends BaseDirectionalComparator {
    IHasInventoryHandler inv;
    boolean slots;

    public DirectionalInventoryComparator(String id, Component name, IHasInventoryHandler inv, boolean slots) {
        super(id, name);
        this.inv = inv;
        this.slots = slots;
    }

    @Override
    protected int createValue(Direction dir) {
        InventoryHandler handler = this.inv.getInventoryHandler();
        IItemHandler inv = (IItemHandler)handler.getHandler(dir).orElse(null);
        if (inv == null) {
            return 0;
        }
        if (this.slots) {
            int full = 0;
            int m = inv.getSlots();
            for (int i = 0; i < m; ++i) {
                full += inv.getStackInSlot(i).m_41619_() ? 0 : 1;
            }
            return DirectionalInventoryComparator.value(full, inv.getSlots(), 15);
        }
        int total = 0;
        int count = 0;
        int m = inv.getSlots();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.m_41619_()) {
                total += inv.getSlotLimit(i);
                continue;
            }
            total += Math.min(stack.m_41741_(), inv.getSlotLimit(i));
            count = stack.m_41613_();
        }
        return DirectionalInventoryComparator.value(count, total, 15);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.personal.trade;

import ic2.core.block.personal.trade.Trade;
import ic2.core.inventory.filter.StackFilter;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class ItemTrade
extends Trade {
    public SimpleInventory offered = new SimpleInventory(4);

    @Override
    public boolean canDoPerPlayer() {
        return true;
    }

    public boolean hasStock(IItemTransporter transporter, int filter, NonNullList<ItemStack> result) {
        int amount = 0;
        for (int i = 0; i < 4; ++i) {
            ItemStack stack = this.offered.getStackInSlot(i);
            if (stack.m_41619_()) continue;
            ++amount;
            if (this.infinite) {
                result.add((Object)stack.m_41777_());
                continue;
            }
            ItemStack fetched = transporter.removeItem(new StackFilter(stack, filter), null, stack.m_41613_(), false);
            if (fetched.m_41613_() < stack.m_41613_()) {
                return false;
            }
            result.add((Object)fetched);
        }
        return amount > 0;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        CompoundTag data = this.offered.save(new CompoundTag());
        if (!data.m_128456_()) {
            nbt.m_128365_("offered", (Tag)data);
        }
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.offered.load(nbt.m_128469_("offered"));
    }
}


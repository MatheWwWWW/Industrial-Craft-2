/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMaps
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.inventory.inv;

import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.INBTSavable;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class SimpleInventory
implements IHasInventory,
INBTSavable {
    protected NonNullList<ItemStack> slots;
    protected int size;
    protected int max = 64;

    public SimpleInventory(int size) {
        this.size = size;
        this.slots = NonNullList.m_122780_((int)size, (Object)ItemStack.f_41583_);
    }

    public SimpleInventory setMaxStackSize(int max) {
        this.max = max;
        return this;
    }

    @Override
    public int getSlotCount() {
        return this.size;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return (ItemStack)this.slots.get(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.slots.set(slot, (Object)stack);
    }

    @Override
    public int getMaxStackSize(int slot) {
        return this.max;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public void load(CompoundTag nbt) {
        NBTUtils.loadItems(nbt, this.slots);
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        NBTUtils.saveItems(nbt, this.slots);
        return nbt;
    }

    public void addToDrops(List<ItemStack> list) {
        for (int i = 0; i < this.size; ++i) {
            if (((ItemStack)this.slots.get(i)).m_41619_()) continue;
            list.add((ItemStack)this.slots.get(i));
        }
    }

    public void overrideTo(IHasInventory target) {
        int m = Math.min(this.getSlotCount(), target.getSlotCount());
        for (int i = 0; i < m; ++i) {
            target.setStackInSlot(i, (ItemStack)this.slots.get(i));
        }
    }

    public void copyFrom(IHasInventory source) {
        this.copyFrom(source, 0, source.getSlotCount());
    }

    public void copyFrom(IHasInventory source, int from, int to) {
        to = Math.min(to, Math.min(this.getSlotCount(), source.getSlotCount()));
        for (int i = from; i < to; ++i) {
            this.slots.set(i, (Object)source.getStackInSlot(i).m_41777_());
        }
    }

    public void copyFrom(IItemHandler source) {
        this.copyFrom(source, 0, source.getSlots());
    }

    public void copyFrom(IItemHandler source, int from, int to) {
        to = Math.min(to, Math.min(this.getSlotCount(), source.getSlots()));
        for (int i = from; i < to; ++i) {
            this.slots.set(i, (Object)source.getStackInSlot(i).m_41777_());
        }
    }

    public void pasteTo(IHasInventory target) {
        this.pasteTo(target, 0, target.getSlotCount());
    }

    public void pasteTo(IHasInventory target, int from, int to) {
        to = Math.min(to, Math.min(this.getSlotCount(), target.getSlotCount()));
        for (int i = from; i < to; ++i) {
            target.setStackInSlot(i, ((ItemStack)this.slots.get(i)).m_41777_());
        }
    }

    public void clear() {
        this.slots.clear();
    }

    public void clear(int from, int to) {
        for (int i = from; i < to && i < this.slots.size(); ++i) {
            this.slots.set(i, (Object)ItemStack.f_41583_);
        }
    }

    public Int2ObjectMap<ItemStack[]> getDifference(IHasInventory inventory, boolean includeNonMaxStacksize) {
        if (this.getSlotCount() != inventory.getSlotCount()) {
            return Int2ObjectMaps.emptyMap();
        }
        Int2ObjectLinkedOpenHashMap differences = new Int2ObjectLinkedOpenHashMap();
        int m = this.getSlotCount();
        for (int i = 0; i < m; ++i) {
            if (ItemStack.m_41728_((ItemStack)inventory.getStackInSlot(i), (ItemStack)this.getStackInSlot(i)) && (!includeNonMaxStacksize || StackUtil.getStackSizeLeft(inventory.getStackInSlot(i)) <= 0)) continue;
            differences.put(i, (Object)new ItemStack[]{inventory.getStackInSlot(i).m_41777_(), this.getStackInSlot(i).m_41777_()});
        }
        return differences;
    }

    public void applyDifferences(IItemHandler handler) {
        int m = Math.min(this.getSlotCount(), handler.getSlots());
        for (int i = 0; i < m; ++i) {
            ItemStack original = handler.getStackInSlot(i);
            ItemStack newHandler = (ItemStack)this.slots.get(i);
            if (original.m_41619_() && !newHandler.m_41619_()) {
                handler.insertItem(i, newHandler, false);
                continue;
            }
            if (!original.m_41619_() && newHandler.m_41619_()) {
                handler.extractItem(i, original.m_41613_(), false);
                continue;
            }
            if (original.m_41619_()) continue;
            if (StackUtil.isStackEqual(original, newHandler)) {
                int difference = newHandler.m_41613_() - original.m_41613_();
                if (difference < 0) {
                    handler.extractItem(i, -difference, false);
                    continue;
                }
                if (difference <= 0) continue;
                handler.insertItem(i, StackUtil.copyWithSize(newHandler, difference), false);
                continue;
            }
            handler.extractItem(i, original.m_41613_(), false);
            handler.insertItem(i, newHandler, false);
        }
    }

    public static SimpleInventory createCopy(IHasInventory inventory) {
        SimpleInventory copy = new SimpleInventory(inventory.getSlotCount());
        copy.copyFrom(inventory);
        return copy;
    }

    public static SimpleInventory createCopy(IItemHandler handler) {
        SimpleInventory copy = new SimpleInventory(handler.getSlots());
        int m = copy.getSlotCount();
        for (int i = 0; i < m; ++i) {
            copy.setStackInSlot(i, handler.extractItem(i, 64, true));
        }
        return copy;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.wrapper.EmptyHandler
 */
package ic2.core.inventory.inv;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;

public class ArrayItemHandler
implements IItemHandler {
    protected final IItemHandler[] itemHandler;
    protected final int[] baseIndex;
    protected final int slotCount;

    public ArrayItemHandler(List<IItemHandler> handlers) {
        this(handlers.toArray(new IItemHandler[handlers.size()]));
    }

    public ArrayItemHandler(IItemHandler ... itemHandler) {
        this.itemHandler = itemHandler;
        this.baseIndex = new int[itemHandler.length];
        int index = 0;
        for (int i = 0; i < itemHandler.length; ++i) {
            this.baseIndex[i] = index += itemHandler[i].getSlots();
        }
        this.slotCount = index;
    }

    protected int getIndexForSlot(int slot) {
        if (slot < 0) {
            return -1;
        }
        for (int i = 0; i < this.baseIndex.length; ++i) {
            if (slot - this.baseIndex[i] >= 0) continue;
            return i;
        }
        return -1;
    }

    protected IItemHandler getHandlerFromIndex(int index) {
        if (index < 0 || index >= this.itemHandler.length) {
            return EmptyHandler.INSTANCE;
        }
        return this.itemHandler[index];
    }

    protected int getSlotFromIndex(int slot, int index) {
        if (index <= 0 || index >= this.baseIndex.length) {
            return slot;
        }
        return slot - this.baseIndex[index - 1];
    }

    public int getSlots() {
        return this.slotCount;
    }

    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        int index = this.getIndexForSlot(slot);
        return this.getHandlerFromIndex(index).getStackInSlot(this.getSlotFromIndex(slot, index));
    }

    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        int index = this.getIndexForSlot(slot);
        return this.getHandlerFromIndex(index).insertItem(this.getSlotFromIndex(slot, index), stack, simulate);
    }

    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        int index = this.getIndexForSlot(slot);
        return this.getHandlerFromIndex(index).extractItem(this.getSlotFromIndex(slot, index), amount, simulate);
    }

    public int getSlotLimit(int slot) {
        int index = this.getIndexForSlot(slot);
        return this.getHandlerFromIndex(index).getSlotLimit(this.getSlotFromIndex(slot, index));
    }

    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        int index = this.getIndexForSlot(slot);
        return this.getHandlerFromIndex(index).isItemValid(this.getSlotFromIndex(slot, index), stack);
    }
}


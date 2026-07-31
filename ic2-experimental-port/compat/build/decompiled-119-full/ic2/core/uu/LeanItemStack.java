/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.uu;

import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

class LeanItemStack {
    private final Item item;
    private final CompoundTag nbt;
    private final int size;
    private int hashCode;

    public LeanItemStack(Item item) {
        this(item, 1);
    }

    public LeanItemStack(Item item, int n) {
        this(item, null, n);
    }

    public LeanItemStack(ItemStack itemStack) {
        this(itemStack.m_41720_(), itemStack.m_41783_(), StackUtil.getSize(itemStack));
    }

    public LeanItemStack(ItemStack itemStack, int n) {
        this(itemStack.m_41720_(), itemStack.m_41783_(), n);
    }

    public LeanItemStack(Item item, CompoundTag compoundTag, int n) {
        if (item == null) {
            throw new NullPointerException("null item");
        }
        this.item = item;
        this.nbt = compoundTag;
        this.size = n;
    }

    public Item getItem() {
        return this.item;
    }

    public CompoundTag getNbt() {
        return this.nbt;
    }

    public int getSize() {
        return this.size;
    }

    public String toString() {
        return String.format("%dx%s", this.size, Util.getName(this.item));
    }

    public boolean hasSameItem(LeanItemStack leanItemStack) {
        return this.item == leanItemStack.item && StackUtil.checkNbtEquality(this.nbt, leanItemStack.nbt);
    }

    public LeanItemStack copy() {
        return this.copyWithSize(this.size);
    }

    public LeanItemStack copyWithSize(int n) {
        LeanItemStack leanItemStack = new LeanItemStack(this.item, this.nbt, n);
        leanItemStack.hashCode = this.hashCode;
        return leanItemStack;
    }

    public ItemStack toMcStack() {
        if (this.size <= 0) {
            return StackUtil.emptyStack;
        }
        ItemStack itemStack = new ItemStack((ItemLike)this.item, this.size);
        itemStack.m_41751_(this.nbt);
        return itemStack;
    }

    public boolean equals(Object object) {
        if (!(object instanceof LeanItemStack)) {
            return false;
        }
        LeanItemStack leanItemStack = (LeanItemStack)object;
        return this.item == leanItemStack.item && (this.nbt == null && leanItemStack.nbt == null || this.nbt != null && leanItemStack.nbt != null && this.nbt.equals((Object)leanItemStack.nbt));
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.calculateHashCode();
        }
        return this.hashCode;
    }

    private int calculateHashCode() {
        int n = System.identityHashCode(this.item);
        if (this.nbt != null) {
            n = n * 31 + this.nbt.hashCode();
        }
        if (n == 0) {
            n = -1;
        }
        return n;
    }
}


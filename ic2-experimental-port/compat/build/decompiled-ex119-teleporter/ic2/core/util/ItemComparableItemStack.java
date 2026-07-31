/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.util;

import ic2.core.util.StackUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ItemComparableItemStack {
    private final Item item;
    private final CompoundTag nbt;
    private final int hashCode;

    public ItemComparableItemStack(ItemStack itemStack, boolean bl) {
        this.item = itemStack.m_41720_();
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag != null) {
            if (compoundTag.m_128456_()) {
                compoundTag = null;
            } else {
                if (bl) {
                    compoundTag = compoundTag.m_6426_();
                }
                boolean bl2 = bl;
                for (String string : StackUtil.ignoredNbtKeys) {
                    if (!bl2 && compoundTag.m_128441_(string)) {
                        compoundTag = compoundTag.m_6426_();
                        bl2 = true;
                    }
                    compoundTag.m_128473_(string);
                }
                if (compoundTag.m_128456_()) {
                    compoundTag = null;
                }
            }
        }
        this.nbt = compoundTag;
        this.hashCode = this.calculateHashCode();
    }

    private ItemComparableItemStack(ItemComparableItemStack itemComparableItemStack) {
        this.item = itemComparableItemStack.item;
        this.nbt = itemComparableItemStack.nbt != null ? itemComparableItemStack.nbt.m_6426_() : null;
        this.hashCode = itemComparableItemStack.hashCode;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ItemComparableItemStack)) {
            return false;
        }
        ItemComparableItemStack itemComparableItemStack = (ItemComparableItemStack)object;
        if (itemComparableItemStack.hashCode != this.hashCode) {
            return false;
        }
        if (itemComparableItemStack == this) {
            return true;
        }
        return itemComparableItemStack.item == this.item && (itemComparableItemStack.nbt == null && this.nbt == null || itemComparableItemStack.nbt != null && this.nbt != null && itemComparableItemStack.nbt.equals((Object)this.nbt));
    }

    public int hashCode() {
        return this.hashCode;
    }

    private int calculateHashCode() {
        int n = 0;
        if (this.item != null) {
            n = System.identityHashCode(this.item);
        }
        if (this.nbt != null) {
            n = n * 31 + this.nbt.hashCode();
        }
        return n;
    }

    public ItemComparableItemStack copy() {
        if (this.nbt == null) {
            return this;
        }
        return new ItemComparableItemStack(this);
    }

    public ItemStack toStack() {
        return this.toStack(1);
    }

    public ItemStack toStack(int n) {
        if (this.item == null) {
            return null;
        }
        ItemStack itemStack = new ItemStack((ItemLike)this.item, n);
        itemStack.m_41751_(this.nbt);
        return itemStack;
    }
}


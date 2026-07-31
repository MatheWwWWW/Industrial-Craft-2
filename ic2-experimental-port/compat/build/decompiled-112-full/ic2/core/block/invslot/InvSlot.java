/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.util.EnumFacing
 */
package ic2.core.block.invslot;

import ic2.core.IC2;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;

public class InvSlot
implements Iterable<ItemStack> {
    public final IInventorySlotHolder<?> base;
    public final String name;
    private final ItemStack[] contents;
    protected final Access access;
    public final InvSide preferredSide;
    private int stackSizeLimit;

    public InvSlot(IInventorySlotHolder<?> base, String name, Access access, int count) {
        this(base, name, access, count, InvSide.ANY);
    }

    public InvSlot(IInventorySlotHolder<?> base, String name, Access access, int count, InvSide preferredSide) {
        if (count <= 0) {
            throw new IllegalArgumentException("invalid slot count: " + count);
        }
        this.contents = new ItemStack[count];
        this.clear();
        this.base = base;
        this.name = name;
        this.access = access;
        this.preferredSide = preferredSide;
        this.stackSizeLimit = 64;
        base.addInventorySlot(this);
    }

    public InvSlot(int count) {
        this.contents = new ItemStack[count];
        this.clear();
        this.base = null;
        this.name = null;
        this.access = Access.NONE;
        this.preferredSide = InvSide.ANY;
    }

    public void readFromNbt(NBTTagCompound nbt) {
        this.clear();
        NBTTagList contentsTag = nbt.func_150295_c("Contents", 10);
        for (int i = 0; i < contentsTag.func_74745_c(); ++i) {
            NBTTagCompound contentTag = contentsTag.func_150305_b(i);
            int index = contentTag.func_74771_c("Index") & 0xFF;
            if (index >= this.size()) {
                IC2.log.error(LogCategory.Block, "Can't load item stack for %s, slot %s, index %d is out of bounds.", Util.toString(this.base.getParent()), this.name, index);
                continue;
            }
            ItemStack stack = new ItemStack(contentTag);
            if (StackUtil.isEmpty(stack)) {
                IC2.log.warn(LogCategory.Block, "Can't load item stack %s for %s, slot %s, index %d, no matching item for %d:%d.", StackUtil.toStringSafe(stack), Util.toString(this.base.getParent()), this.name, index, contentTag.func_74765_d("id"), contentTag.func_74765_d("Damage"));
                continue;
            }
            if (!this.isEmpty(index)) {
                IC2.log.error(LogCategory.Block, "Loading content to non-empty slot for %s, slot %s, index %d, replacing %s with %s.", Util.toString(this.base.getParent()), this.name, index, this.get(index), stack);
            }
            this.putFromNBT(index, stack);
        }
        this.onChanged();
    }

    public void writeToNbt(NBTTagCompound nbt) {
        NBTTagList contentsTag = new NBTTagList();
        for (int i = 0; i < this.contents.length; ++i) {
            ItemStack content = this.contents[i];
            if (StackUtil.isEmpty(content)) continue;
            NBTTagCompound contentTag = new NBTTagCompound();
            contentTag.func_74774_a("Index", (byte)i);
            content.func_77955_b(contentTag);
            contentsTag.func_74742_a((NBTBase)contentTag);
        }
        nbt.func_74782_a("Contents", (NBTBase)contentsTag);
    }

    public int size() {
        return this.contents.length;
    }

    public boolean isEmpty() {
        for (ItemStack stack : this.contents) {
            if (StackUtil.isEmpty(stack)) continue;
            return false;
        }
        return true;
    }

    public boolean isEmpty(int index) {
        return StackUtil.isEmpty(this.contents[index]);
    }

    public ItemStack get() {
        return this.get(0);
    }

    public ItemStack get(int index) {
        return this.contents[index];
    }

    public void put(ItemStack content) {
        this.put(0, content);
    }

    protected void putFromNBT(int index, ItemStack content) {
        this.contents[index] = content;
    }

    public void put(int index, ItemStack content) {
        if (StackUtil.isEmpty(content)) {
            content = StackUtil.emptyStack;
        }
        this.contents[index] = content;
        this.onChanged();
    }

    public void clear() {
        Arrays.fill(this.contents, StackUtil.emptyStack);
    }

    public void clear(int index) {
        this.put(index, StackUtil.emptyStack);
    }

    public void onChanged() {
    }

    public boolean accepts(ItemStack stack) {
        return true;
    }

    public boolean canInput() {
        return this.access == Access.I || this.access == Access.IO;
    }

    public boolean canOutput() {
        return this.access == Access.O || this.access == Access.IO;
    }

    public void organize() {
        block0: for (int dstIndex = 0; dstIndex < this.contents.length - 1; ++dstIndex) {
            ItemStack dst = this.contents[dstIndex];
            if (!StackUtil.isEmpty(dst) && StackUtil.getSize(dst) >= dst.func_77976_d()) continue;
            for (int srcIndex = dstIndex + 1; srcIndex < this.contents.length; ++srcIndex) {
                ItemStack src = this.contents[srcIndex];
                if (StackUtil.isEmpty(src)) continue;
                if (StackUtil.isEmpty(dst)) {
                    this.contents[srcIndex] = StackUtil.emptyStack;
                    this.contents[dstIndex] = dst = src;
                    continue;
                }
                if (!StackUtil.checkItemEqualityStrict(dst, src)) continue;
                int space = Math.min(this.getStackSizeLimit(), dst.func_77976_d() - StackUtil.getSize(dst));
                int srcSize = StackUtil.getSize(src);
                if (srcSize <= space) {
                    this.contents[srcIndex] = StackUtil.emptyStack;
                    this.contents[dstIndex] = dst = StackUtil.incSize(dst, srcSize);
                    if (srcSize != space) continue;
                    continue block0;
                }
                this.contents[srcIndex] = StackUtil.decSize(src, space);
                this.contents[dstIndex] = StackUtil.incSize(dst, space);
                continue block0;
            }
        }
    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

    @Override
    public Iterator<ItemStack> iterator() {
        return new Iterator<ItemStack>(){
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return this.idx < InvSlot.this.contents.length;
            }

            @Override
            public ItemStack next() {
                if (this.idx >= InvSlot.this.contents.length) {
                    throw new NoSuchElementException();
                }
                return InvSlot.this.contents[this.idx++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public String toString() {
        String ret = this.name + "[" + this.contents.length + "]: ";
        for (int i = 0; i < this.contents.length; ++i) {
            ret = ret + this.contents[i];
            if (i >= this.contents.length - 1) continue;
            ret = ret + ", ";
        }
        return ret;
    }

    protected ItemStack[] backup() {
        ItemStack[] ret = new ItemStack[this.contents.length];
        for (int i = 0; i < this.contents.length; ++i) {
            ItemStack content = this.contents[i];
            ret[i] = StackUtil.isEmpty(content) ? StackUtil.emptyStack : content.func_77946_l();
        }
        return ret;
    }

    protected void restore(ItemStack[] backup) {
        if (backup.length != this.contents.length) {
            throw new IllegalArgumentException("invalid array size");
        }
        for (int i = 0; i < this.contents.length; ++i) {
            this.contents[i] = backup[i];
        }
    }

    public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
    }

    public static enum InvSide {
        ANY(EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST),
        TOP(EnumFacing.UP),
        BOTTOM(EnumFacing.DOWN),
        SIDE(EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST),
        NOTSIDE(new EnumFacing[0]);

        private Set<EnumFacing> acceptedSides;

        private InvSide(EnumFacing ... sides) {
            if (sides.length == 0) {
                this.acceptedSides = Collections.emptySet();
            } else {
                EnumSet<EnumFacing> acceptedSides = EnumSet.noneOf(EnumFacing.class);
                acceptedSides.addAll(Arrays.asList(sides));
                this.acceptedSides = Collections.unmodifiableSet(acceptedSides);
            }
        }

        public boolean matches(EnumFacing side) {
            return this.acceptedSides.contains(side);
        }

        public Set<EnumFacing> getAcceptedSides() {
            return this.acceptedSides;
        }
    }

    public static enum Access {
        NONE,
        I,
        O,
        IO;


        public boolean isInput() {
            return (this.ordinal() & 1) != 0;
        }

        public boolean isOutput() {
            return (this.ordinal() & 2) != 0;
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package ic2.core.ref;

import ic2.core.block.state.IIdProvider;
import ic2.core.ref.IMultiBlock;
import ic2.core.ref.IMultiItem;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum BlockName {
    te,
    resource,
    leaves,
    rubber_wood,
    sapling,
    scaffold,
    foam,
    fence,
    sheet,
    glass,
    wall,
    mining_pipe,
    reinforced_door,
    dynamite,
    refractory_bricks;

    private Block instance;
    public static final BlockName[] values;

    public boolean hasInstance() {
        return this.instance != null;
    }

    public <T extends Block> T getInstance() {
        if (this.instance == null) {
            throw new IllegalStateException("the requested block instance for " + this.name() + " isn't set (yet)");
        }
        return (T)this.instance;
    }

    public <T extends Block> void setInstance(T instance) {
        if (this.instance != null) {
            throw new IllegalStateException("conflicting instance");
        }
        this.instance = instance;
    }

    public <T extends IIdProvider> IBlockState getBlockState(T variant) {
        if (this.instance == null) {
            return null;
        }
        if (this.instance instanceof IMultiBlock) {
            IMultiBlock block = (IMultiBlock)this.instance;
            return block.getState(variant);
        }
        if (variant == null) {
            return this.instance.func_176223_P();
        }
        throw new IllegalArgumentException("not applicable");
    }

    public boolean hasItemStack() {
        if (this.instance == null) {
            return false;
        }
        if (this.instance instanceof IMultiItem) {
            return true;
        }
        Item item = Item.func_150898_a((Block)this.instance);
        return item != null && item != Items.field_190931_a;
    }

    public <T extends Enum<T>> ItemStack getItemStack() {
        return this.getItemStack((String)null);
    }

    public <T extends Enum<T>> ItemStack getItemStack(T variant) {
        if (this.instance == null) {
            return null;
        }
        if (this.instance instanceof IMultiItem) {
            IMultiItem multiItem = (IMultiItem)this.instance;
            return multiItem.getItemStack((IIdProvider)((Object)variant));
        }
        if (variant == null) {
            return this.getItemStack((String)null);
        }
        throw new IllegalArgumentException("not applicable");
    }

    public <T extends Enum<T>> ItemStack getItemStack(String variant) {
        if (this.instance == null) {
            return null;
        }
        if (this.instance instanceof IMultiItem) {
            IMultiItem multiItem = (IMultiItem)this.instance;
            return multiItem.getItemStack(variant);
        }
        if (variant == null) {
            Item item = Item.func_150898_a((Block)this.instance);
            if (item == null || item == Items.field_190931_a) {
                throw new IllegalArgumentException("No item found for " + this.instance);
            }
            return new ItemStack(item);
        }
        throw new IllegalArgumentException("not applicable");
    }

    public String getVariant(ItemStack stack) {
        if (this.instance == null) {
            return null;
        }
        if (this.instance instanceof IMultiItem) {
            return ((IMultiItem)this.instance).getVariant(stack);
        }
        return null;
    }

    static {
        values = BlockName.values();
    }
}


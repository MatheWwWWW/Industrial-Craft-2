/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package ic2.core.apihelper;

import ic2.api.item.IItemAPI;
import ic2.core.ref.BlockName;
import ic2.core.ref.FluidName;
import ic2.core.ref.IMultiBlock;
import ic2.core.ref.ItemName;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemAPI
implements IItemAPI {
    @Override
    public ItemStack getItemStack(String name, String variant) {
        BlockName blockName;
        int idx;
        if (name == null) {
            return null;
        }
        if (variant == null && (idx = name.indexOf(35)) != -1) {
            variant = name.substring(idx + 1);
            name = name.substring(0, idx);
        }
        if ((blockName = this.getBlockName(name)) != null) {
            return blockName.getItemStack(variant);
        }
        ItemName itemName = this.getItemName(name);
        if (itemName != null) {
            return itemName.getItemStack(variant);
        }
        return null;
    }

    @Override
    public Block getBlock(String name) {
        if (name == null) {
            return null;
        }
        BlockName blockName = this.getBlockName(name);
        if (blockName != null) {
            return blockName.getInstance();
        }
        FluidName fluidName = this.getFluidName(name);
        if (fluidName != null) {
            return fluidName.getInstance().getBlock();
        }
        return null;
    }

    @Override
    public Item getItem(String name) {
        Item ret;
        if (name == null) {
            return null;
        }
        ItemName itemName = this.getItemName(name);
        if (itemName != null) {
            return itemName.getInstance();
        }
        Block block = this.getBlock(name);
        if (block != null && ((ret = Item.func_150898_a((Block)block)) != Items.field_190931_a || block == Blocks.field_150350_a)) {
            return ret;
        }
        return null;
    }

    @Override
    public IBlockState getBlockState(String name, String variant) {
        BlockName blockName;
        int idx;
        if (variant == null && (idx = name.indexOf(35)) != -1) {
            variant = name.substring(idx + 1);
            name = name.substring(0, idx);
        }
        if ((blockName = this.getBlockName(name)) != null) {
            Object block = blockName.getInstance();
            if (block instanceof IMultiBlock) {
                return ((IMultiBlock)block).getState(variant);
            }
            assert (variant == null);
            return block.func_176223_P();
        }
        FluidName fluidName = this.getFluidName(name);
        if (fluidName != null) {
            assert (variant == null);
            return fluidName.getInstance().getBlock().func_176223_P();
        }
        return null;
    }

    private ItemName getItemName(String itemName) {
        for (ItemName name : ItemName.values) {
            if (!name.name().equalsIgnoreCase(itemName)) continue;
            return name;
        }
        return null;
    }

    private BlockName getBlockName(String blockName) {
        for (BlockName name : BlockName.values) {
            if (!name.name().equalsIgnoreCase(blockName)) continue;
            return name;
        }
        return null;
    }

    private FluidName getFluidName(String fluidName) {
        for (FluidName name : FluidName.values) {
            if (!name.name().equalsIgnoreCase(fluidName)) continue;
            return name;
        }
        return null;
    }
}


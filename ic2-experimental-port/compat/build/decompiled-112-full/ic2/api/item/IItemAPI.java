/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package ic2.api.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IItemAPI {
    public IBlockState getBlockState(String var1, String var2);

    public ItemStack getItemStack(String var1, String var2);

    public Block getBlock(String var1);

    public Item getItem(String var1);
}


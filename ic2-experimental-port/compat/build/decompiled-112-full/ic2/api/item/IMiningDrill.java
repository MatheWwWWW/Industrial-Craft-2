/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.api.item;

import ic2.api.item.ElectricItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMiningDrill {
    public int energyUse(ItemStack var1, World var2, BlockPos var3, IBlockState var4);

    public int breakTime(ItemStack var1, World var2, BlockPos var3, IBlockState var4);

    public boolean breakBlock(ItemStack var1, World var2, BlockPos var3, IBlockState var4);

    default public boolean tryUsePower(ItemStack drill, double amount) {
        return ElectricItem.manager.use(drill, amount, null);
    }
}


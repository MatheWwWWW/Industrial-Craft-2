/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.api.items.electric;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public interface IScanner {
    public int getScanRadius(ItemStack var1, boolean var2);

    public boolean hasScanEffect(ItemStack var1);

    default public boolean isOreValuable(ItemStack stack, BlockState state, LevelReader world, BlockPos pos) {
        return this.getOreValue(stack, state) > 0;
    }

    default public boolean isOreValuable(ItemStack stack, BlockState state) {
        return this.getOreValue(stack, state) > 0;
    }

    default public int getOreValue(ItemStack stack, BlockState state, LevelReader world, BlockPos pos) {
        return this.getOreValue(stack, state);
    }

    public int getOreValue(ItemStack var1, BlockState var2);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.material.FluidState
 */
package ic2.api.items.electric;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.FluidState;

public interface IFluidScanner {
    public int getScanRadius(ItemStack var1, boolean var2);

    default public boolean isValuableFluid(ItemStack stack, FluidState state, LevelReader world, BlockPos pos) {
        return this.isValuableFluid(stack, state);
    }

    public boolean isValuableFluid(ItemStack var1, FluidState var2);
}


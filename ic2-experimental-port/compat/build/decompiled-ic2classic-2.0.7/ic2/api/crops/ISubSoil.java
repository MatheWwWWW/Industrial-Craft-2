/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.api.crops;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface ISubSoil {
    default public int getHumidity(Level world, BlockPos pos) {
        return this.getHumidity(world.m_8055_(pos));
    }

    public int getHumidity(BlockState var1);

    default public int getNutrients(Level world, BlockPos pos) {
        return this.getNutrients(world.m_8055_(pos));
    }

    public int getNutrients(BlockState var1);

    default public boolean isSpecial() {
        return false;
    }

    default public BlockState getSpecialState(Block block) {
        return block.m_49966_();
    }
}


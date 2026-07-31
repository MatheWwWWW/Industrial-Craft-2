/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.crops.soils;

import ic2.api.crops.IFarmland;
import ic2.core.block.crops.CropRegistry;
import ic2.core.block.crops.PlanterTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PlanterPotFarmland
implements IFarmland {
    @Override
    public int getHumidity(BlockState state) {
        return 0;
    }

    @Override
    public int getNutrients(BlockState state) {
        return 0;
    }

    @Override
    public int getHumidity(Level world, BlockPos pos) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof PlanterTileEntity) {
            Block block = ((PlanterTileEntity)tile).block;
            IFarmland farm = CropRegistry.INSTANCE.getFarmland(block);
            return farm == null ? 0 : farm.getHumidity(block.m_49966_());
        }
        return 0;
    }

    @Override
    public int getNutrients(Level world, BlockPos pos) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof PlanterTileEntity) {
            Block block = ((PlanterTileEntity)tile).block;
            IFarmland farm = CropRegistry.INSTANCE.getFarmland(block);
            return farm == null ? 0 : farm.getNutrients(block.m_49966_());
        }
        return 0;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.hv;

import ic2.core.block.machines.tiles.lv.PumpTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class HyperClockedPumpTileEntity
extends PumpTileEntity {
    public HyperClockedPumpTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 512, 12500);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.HYPERCLOCKED_PUMP;
    }

    @Override
    public int getPumpMaxProgress() {
        return 5;
    }

    @Override
    public int getPumpCost() {
        return 40;
    }
}


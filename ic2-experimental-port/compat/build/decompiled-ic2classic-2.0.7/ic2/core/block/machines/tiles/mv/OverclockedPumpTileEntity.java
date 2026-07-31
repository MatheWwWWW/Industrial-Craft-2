/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.mv;

import ic2.core.block.machines.tiles.lv.PumpTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class OverclockedPumpTileEntity
extends PumpTileEntity {
    public OverclockedPumpTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 128, 2500);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.OVERCLOCKED_PUMP;
    }

    @Override
    public int getPumpMaxProgress() {
        return 25;
    }

    @Override
    public int getPumpCost() {
        return 8;
    }
}


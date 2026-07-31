/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.storage.tiles.charging;

import ic2.core.block.base.tiles.impls.BaseChargingBenchTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EVChargingBenchTileEntity
extends BaseChargingBenchTileEntity {
    public EVChargingBenchTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 2048, 10000000);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.CHARGING_BENCH_EV;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.65;
    }
}


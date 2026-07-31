/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.storage.tiles.transformer;

import ic2.core.block.base.tiles.impls.BaseTransformerTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class HVTransformerTileEntity
extends BaseTransformerTileEntity {
    public HVTransformerTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 512, 2048, 4096);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.TRANSFORMER_HV;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.8;
    }
}


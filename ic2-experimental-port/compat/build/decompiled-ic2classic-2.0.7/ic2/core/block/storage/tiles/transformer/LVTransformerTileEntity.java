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

public class LVTransformerTileEntity
extends BaseTransformerTileEntity {
    public LVTransformerTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 32, 128, 256);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.TRANSFORMER_LV;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.85;
    }

    @Override
    public boolean isHarvestWrenchRequired(Player player) {
        return false;
    }
}


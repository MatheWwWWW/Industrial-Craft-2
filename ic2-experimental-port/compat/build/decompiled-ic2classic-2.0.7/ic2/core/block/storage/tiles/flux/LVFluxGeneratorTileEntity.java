/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.storage.tiles.flux;

import ic2.core.block.base.tiles.impls.BaseFluxGeneratorTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class LVFluxGeneratorTileEntity
extends BaseFluxGeneratorTileEntity {
    public LVFluxGeneratorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1, 32, 40000);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.FLUX_GENERATOR_LV;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.misc.tiles;

import ic2.core.block.misc.tiles.TexturedBlockTileEntity;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.platform.registries.ColorMaps;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CamouflageTiles {

    public static class CamouflageWallTileEntity
    extends TexturedBlockTileEntity {
        public CamouflageWallTileEntity(BlockPos pos, BlockState state) {
            super(pos, state, CamouflageShape.WALL, ColorMaps.CFOAM_WALLS);
        }

        @Override
        public BlockEntityType<?> createType() {
            return IC2Tiles.TEXTURED_WALL;
        }
    }

    public static class CamouflageStairsTileEntity
    extends TexturedBlockTileEntity {
        public CamouflageStairsTileEntity(BlockPos pos, BlockState state) {
            super(pos, state, CamouflageShape.STAIRS, ColorMaps.CFOAM_STAIRS);
        }

        @Override
        public BlockEntityType<?> createType() {
            return IC2Tiles.TEXTURED_STAIRS;
        }
    }

    public static class CamouflageSlabTileEntity
    extends TexturedBlockTileEntity {
        public CamouflageSlabTileEntity(BlockPos pos, BlockState state) {
            super(pos, state, CamouflageShape.SLAB, ColorMaps.CFOAM_SLABS);
        }

        @Override
        public BlockEntityType<?> createType() {
            return IC2Tiles.TEXTURED_SLAB;
        }
    }

    public static class CamouflageBlockTileEntity
    extends TexturedBlockTileEntity {
        public CamouflageBlockTileEntity(BlockPos pos, BlockState state) {
            super(pos, state, CamouflageShape.FULL_CUBE, ColorMaps.CFOAM_BLOCKS);
        }

        @Override
        public BlockEntityType<?> createType() {
            return IC2Tiles.TEXTURED_BLOCK;
        }
    }
}


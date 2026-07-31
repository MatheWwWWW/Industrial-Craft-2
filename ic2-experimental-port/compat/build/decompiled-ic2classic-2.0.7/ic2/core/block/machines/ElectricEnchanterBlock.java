/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package ic2.core.block.machines;

import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.machines.NoStateMachineBlock;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ElectricEnchanterBlock
extends NoStateMachineBlock
implements IBlockModifiers {
    public static final AABB BOX = new AABB(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    public static final VoxelShape SHAPE = Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)12.0, (double)16.0);

    public ElectricEnchanterBlock(String blockName, IBlockDropProvider drops, ITextureProvider provider, BlockEntityType<? extends BaseTileEntity> type) {
        super(blockName, drops, provider, type);
    }

    @Override
    public AABB getModelBounds(BlockState state) {
        return BOX;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext selectionContext) {
        return SHAPE;
    }

    @Override
    public boolean hasTextureRotation(BlockState state, Direction side) {
        return false;
    }

    @Override
    public int getTextureRotation(BlockState state, Direction side) {
        return 0;
    }

    @Override
    public boolean hasCustomTextureUVs(BlockState state, Direction side) {
        return side.m_122416_() != -1;
    }

    @Override
    public float[] getCustomTextureUVs(BlockState state, Direction side) {
        return new float[]{0.0f, 4.0f, 16.0f, 16.0f};
    }
}


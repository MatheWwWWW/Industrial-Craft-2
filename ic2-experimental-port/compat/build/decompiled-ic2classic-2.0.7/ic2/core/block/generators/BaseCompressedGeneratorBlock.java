/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SimpleWaterloggedBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package ic2.core.block.generators;

import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.generators.BaseGeneratorBlock;
import ic2.core.platform.rendering.features.ITextureProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BaseCompressedGeneratorBlock
extends BaseGeneratorBlock
implements SimpleWaterloggedBlock {
    public static final AABB BOX = new AABB(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    public static final VoxelShape SHAPE = Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)8.0, (double)16.0);
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;

    public BaseCompressedGeneratorBlock(String blockName, IBlockDropProvider drop, String textureFolder, BlockEntityType<? extends BlockEntity> tile) {
        super(blockName, drop, ITextureProvider.toggleIC2(textureFolder), tile);
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    public AABB getModelBounds(BlockState state) {
        return BOX;
    }

    public FluidState m_5888_(BlockState state) {
        return (Boolean)state.m_61143_((Property)WATER) != false ? Fluids.f_76193_.m_76145_() : Fluids.f_76191_.m_76145_();
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext context) {
        FluidState state = context.m_43725_().m_6425_(context.m_8083_());
        return (BlockState)super.m_5573_(context).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(state.m_76152_() == Fluids.f_76193_));
    }

    @Override
    public void m_6861_(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.m_6861_(state, world, pos, block, fromPos, isMoving);
        if (((Boolean)state.m_61143_((Property)WATER)).booleanValue()) {
            world.m_186469_(pos, (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)world));
        }
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{WATER});
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    public VoxelShape m_5940_(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return SHAPE;
    }
}


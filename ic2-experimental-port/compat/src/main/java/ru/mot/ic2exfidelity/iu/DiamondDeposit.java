package ru.mot.ic2exfidelity.iu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Original thin, falling and waterlogged diamond-deposit block. */
public final class DiamondDeposit extends FallingBlock implements SimpleWaterloggedBlock {
    private static final VoxelShape SHAPE = Shapes.m_83048_(0, 0, 0, 1, .2, 1);
    public DiamondDeposit() {
        super(BlockBehaviour.Properties.m_60939_(Material.f_76278_).m_60913_(3, 5).m_60955_());
        m_49959_(m_49966_().m_61124_(BlockStateProperties.f_61362_, false));
    }
    @Override protected void m_7926_(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) { builder.m_61104_(BlockStateProperties.f_61362_); }
    @Override public BlockState m_5573_(BlockPlaceContext context) { return m_49966_().m_61124_(BlockStateProperties.f_61362_, context.m_43725_().m_6425_(context.m_8083_()).m_76152_() == Fluids.f_76193_); }
    @Override public FluidState m_5888_(BlockState state) { return state.m_61143_(BlockStateProperties.f_61362_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(state); }
    @Override public BlockState m_7417_(BlockState state, Direction side, BlockState adjacent, LevelAccessor level, BlockPos pos, BlockPos neighbor) {
        if (state.m_61143_(BlockStateProperties.f_61362_)) level.m_186469_(pos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(level));
        return super.m_7417_(state, side, adjacent, level, pos, neighbor);
    }
    @Override public VoxelShape m_5940_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return SHAPE; }
}

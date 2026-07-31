/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicates
 */
package net.minecraft.world.level.block;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EndPortalFrameBlock
extends Block {
    public static final DirectionProperty f_53042_ = HorizontalDirectionalBlock.f_54117_;
    public static final BooleanProperty f_53043_ = BlockStateProperties.f_61433_;
    protected static final VoxelShape f_53044_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 13.0, 16.0);
    protected static final VoxelShape f_53045_ = Block.m_49796_(4.0, 13.0, 4.0, 12.0, 16.0, 12.0);
    protected static final VoxelShape f_53046_ = Shapes.m_83110_(f_53044_, f_53045_);
    private static BlockPattern f_53047_;

    public EndPortalFrameBlock(BlockBehaviour.Properties p_53050_) {
        super(p_53050_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_53042_, Direction.NORTH)).m_61124_(f_53043_, false));
    }

    @Override
    public boolean m_7923_(BlockState p_53079_) {
        return true;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_53073_, BlockGetter p_53074_, BlockPos p_53075_, CollisionContext p_53076_) {
        return p_53073_.m_61143_(f_53043_) != false ? f_53046_ : f_53044_;
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_53052_) {
        return (BlockState)((BlockState)this.m_49966_().m_61124_(f_53042_, p_53052_.m_8125_().m_122424_())).m_61124_(f_53043_, false);
    }

    @Override
    public boolean m_7278_(BlockState p_53054_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_53061_, Level p_53062_, BlockPos p_53063_) {
        if (p_53061_.m_61143_(f_53043_).booleanValue()) {
            return 15;
        }
        return 0;
    }

    @Override
    public BlockState m_6843_(BlockState p_53068_, Rotation p_53069_) {
        return (BlockState)p_53068_.m_61124_(f_53042_, p_53069_.m_55954_(p_53068_.m_61143_(f_53042_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_53065_, Mirror p_53066_) {
        return p_53065_.m_60717_(p_53066_.m_54846_(p_53065_.m_61143_(f_53042_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53071_) {
        p_53071_.m_61104_(f_53042_, f_53043_);
    }

    public static BlockPattern m_53077_() {
        if (f_53047_ == null) {
            f_53047_ = BlockPatternBuilder.m_61243_().m_61247_("?vvv?", ">???<", ">???<", ">???<", "?^^^?").m_61244_('?', BlockInWorld.m_61169_(BlockStatePredicate.f_61281_)).m_61244_('^', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50258_).m_61295_(f_53043_, (Predicate<Object>)Predicates.equalTo((Object)true)).m_61295_(f_53042_, (Predicate<Object>)Predicates.equalTo((Object)Direction.SOUTH)))).m_61244_('>', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50258_).m_61295_(f_53043_, (Predicate<Object>)Predicates.equalTo((Object)true)).m_61295_(f_53042_, (Predicate<Object>)Predicates.equalTo((Object)Direction.WEST)))).m_61244_('v', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50258_).m_61295_(f_53043_, (Predicate<Object>)Predicates.equalTo((Object)true)).m_61295_(f_53042_, (Predicate<Object>)Predicates.equalTo((Object)Direction.NORTH)))).m_61244_('<', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50258_).m_61295_(f_53043_, (Predicate<Object>)Predicates.equalTo((Object)true)).m_61295_(f_53042_, (Predicate<Object>)Predicates.equalTo((Object)Direction.EAST)))).m_61249_();
        }
        return f_53047_;
    }

    @Override
    public boolean m_7357_(BlockState p_53056_, BlockGetter p_53057_, BlockPos p_53058_, PathComputationType p_53059_) {
        return false;
    }
}


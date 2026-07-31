/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ScaffoldingBlock
extends Block
implements SimpleWaterloggedBlock {
    private static final int f_154382_ = 1;
    private static final VoxelShape f_56015_;
    private static final VoxelShape f_56016_;
    private static final VoxelShape f_56017_;
    private static final VoxelShape f_56018_;
    public static final int f_154381_ = 7;
    public static final IntegerProperty f_56012_;
    public static final BooleanProperty f_56013_;
    public static final BooleanProperty f_56014_;

    protected ScaffoldingBlock(BlockBehaviour.Properties p_56021_) {
        super(p_56021_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_56012_, 7)).m_61124_(f_56013_, false)).m_61124_(f_56014_, false));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56051_) {
        p_56051_.m_61104_(f_56012_, f_56013_, f_56014_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_56057_, BlockGetter p_56058_, BlockPos p_56059_, CollisionContext p_56060_) {
        if (!p_56060_.m_7142_(p_56057_.m_60734_().m_5456_())) {
            return p_56057_.m_61143_(f_56014_) != false ? f_56016_ : f_56015_;
        }
        return Shapes.m_83144_();
    }

    @Override
    public VoxelShape m_6079_(BlockState p_56053_, BlockGetter p_56054_, BlockPos p_56055_) {
        return Shapes.m_83144_();
    }

    @Override
    public boolean m_6864_(BlockState p_56037_, BlockPlaceContext p_56038_) {
        return p_56038_.m_43722_().m_150930_(this.m_5456_());
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_56023_) {
        BlockPos $$1 = p_56023_.m_8083_();
        Level $$2 = p_56023_.m_43725_();
        int $$3 = ScaffoldingBlock.m_56024_($$2, $$1);
        return (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_56013_, $$2.m_6425_($$1).m_76152_() == Fluids.f_76193_)).m_61124_(f_56012_, $$3)).m_61124_(f_56014_, this.m_56027_($$2, $$1, $$3));
    }

    @Override
    public void m_6807_(BlockState p_56062_, Level p_56063_, BlockPos p_56064_, BlockState p_56065_, boolean p_56066_) {
        if (!p_56063_.f_46443_) {
            p_56063_.m_186460_(p_56064_, this, 1);
        }
    }

    @Override
    public BlockState m_7417_(BlockState p_56044_, Direction p_56045_, BlockState p_56046_, LevelAccessor p_56047_, BlockPos p_56048_, BlockPos p_56049_) {
        if (p_56044_.m_61143_(f_56013_).booleanValue()) {
            p_56047_.m_186469_(p_56048_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_56047_));
        }
        if (!p_56047_.m_5776_()) {
            p_56047_.m_186460_(p_56048_, this, 1);
        }
        return p_56044_;
    }

    @Override
    public void m_213897_(BlockState p_222019_, ServerLevel p_222020_, BlockPos p_222021_, RandomSource p_222022_) {
        int $$4 = ScaffoldingBlock.m_56024_(p_222020_, p_222021_);
        BlockState $$5 = (BlockState)((BlockState)p_222019_.m_61124_(f_56012_, $$4)).m_61124_(f_56014_, this.m_56027_(p_222020_, p_222021_, $$4));
        if ($$5.m_61143_(f_56012_) == 7) {
            if (p_222019_.m_61143_(f_56012_) == 7) {
                FallingBlockEntity.m_201971_(p_222020_, p_222021_, $$5);
            } else {
                p_222020_.m_46961_(p_222021_, true);
            }
        } else if (p_222019_ != $$5) {
            p_222020_.m_7731_(p_222021_, $$5, 3);
        }
    }

    @Override
    public boolean m_7898_(BlockState p_56040_, LevelReader p_56041_, BlockPos p_56042_) {
        return ScaffoldingBlock.m_56024_(p_56041_, p_56042_) < 7;
    }

    @Override
    public VoxelShape m_5939_(BlockState p_56068_, BlockGetter p_56069_, BlockPos p_56070_, CollisionContext p_56071_) {
        if (!p_56071_.m_6513_(Shapes.m_83144_(), p_56070_, true) || p_56071_.m_6226_()) {
            if (p_56068_.m_61143_(f_56012_) != 0 && p_56068_.m_61143_(f_56014_).booleanValue() && p_56071_.m_6513_(f_56018_, p_56070_, true)) {
                return f_56017_;
            }
            return Shapes.m_83040_();
        }
        return f_56015_;
    }

    @Override
    public FluidState m_5888_(BlockState p_56073_) {
        if (p_56073_.m_61143_(f_56013_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_56073_);
    }

    private boolean m_56027_(BlockGetter p_56028_, BlockPos p_56029_, int p_56030_) {
        return p_56030_ > 0 && !p_56028_.m_8055_(p_56029_.m_7495_()).m_60713_(this);
    }

    public static int m_56024_(BlockGetter p_56025_, BlockPos p_56026_) {
        Direction $$5;
        BlockState $$6;
        BlockPos.MutableBlockPos $$2 = p_56026_.m_122032_().m_122173_(Direction.DOWN);
        BlockState $$3 = p_56025_.m_8055_($$2);
        int $$4 = 7;
        if ($$3.m_60713_(Blocks.f_50616_)) {
            $$4 = $$3.m_61143_(f_56012_);
        } else if ($$3.m_60783_(p_56025_, $$2, Direction.UP)) {
            return 0;
        }
        Iterator<Direction> iterator = Direction.Plane.HORIZONTAL.iterator();
        while (iterator.hasNext() && (!($$6 = p_56025_.m_8055_($$2.m_122159_(p_56026_, $$5 = iterator.next()))).m_60713_(Blocks.f_50616_) || ($$4 = Math.min($$4, $$6.m_61143_(f_56012_) + 1)) != 1)) {
        }
        return $$4;
    }

    static {
        f_56017_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
        f_56018_ = Shapes.m_83144_().m_83216_(0.0, -1.0, 0.0);
        f_56012_ = BlockStateProperties.f_61388_;
        f_56013_ = BlockStateProperties.f_61362_;
        f_56014_ = BlockStateProperties.f_61427_;
        VoxelShape $$0 = Block.m_49796_(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
        VoxelShape $$1 = Block.m_49796_(0.0, 0.0, 0.0, 2.0, 16.0, 2.0);
        VoxelShape $$2 = Block.m_49796_(14.0, 0.0, 0.0, 16.0, 16.0, 2.0);
        VoxelShape $$3 = Block.m_49796_(0.0, 0.0, 14.0, 2.0, 16.0, 16.0);
        VoxelShape $$4 = Block.m_49796_(14.0, 0.0, 14.0, 16.0, 16.0, 16.0);
        f_56015_ = Shapes.m_83124_($$0, $$1, $$2, $$3, $$4);
        VoxelShape $$5 = Block.m_49796_(0.0, 0.0, 0.0, 2.0, 2.0, 16.0);
        VoxelShape $$6 = Block.m_49796_(14.0, 0.0, 0.0, 16.0, 2.0, 16.0);
        VoxelShape $$7 = Block.m_49796_(0.0, 0.0, 14.0, 16.0, 2.0, 16.0);
        VoxelShape $$8 = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 2.0, 2.0);
        f_56016_ = Shapes.m_83124_(f_56017_, f_56015_, $$6, $$5, $$8, $$7);
    }
}


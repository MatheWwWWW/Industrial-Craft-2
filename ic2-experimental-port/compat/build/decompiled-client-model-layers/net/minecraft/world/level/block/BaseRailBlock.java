/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RailState;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BaseRailBlock
extends Block
implements SimpleWaterloggedBlock {
    protected static final VoxelShape f_49355_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
    protected static final VoxelShape f_49356_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    public static final BooleanProperty f_152149_ = BlockStateProperties.f_61362_;
    private final boolean f_49357_;

    public static boolean m_49364_(Level p_49365_, BlockPos p_49366_) {
        return BaseRailBlock.m_49416_(p_49365_.m_8055_(p_49366_));
    }

    public static boolean m_49416_(BlockState p_49417_) {
        return p_49417_.m_204336_(BlockTags.f_13034_) && p_49417_.m_60734_() instanceof BaseRailBlock;
    }

    protected BaseRailBlock(boolean p_49360_, BlockBehaviour.Properties p_49361_) {
        super(p_49361_);
        this.f_49357_ = p_49360_;
    }

    public boolean m_49413_() {
        return this.f_49357_;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_49403_, BlockGetter p_49404_, BlockPos p_49405_, CollisionContext p_49406_) {
        RailShape $$4;
        RailShape railShape = $$4 = p_49403_.m_60713_(this) ? p_49403_.m_61143_(this.m_7978_()) : null;
        if ($$4 != null && $$4.m_61745_()) {
            return f_49356_;
        }
        return f_49355_;
    }

    @Override
    public boolean m_7898_(BlockState p_49395_, LevelReader p_49396_, BlockPos p_49397_) {
        return BaseRailBlock.m_49936_(p_49396_, p_49397_.m_7495_());
    }

    @Override
    public void m_6807_(BlockState p_49408_, Level p_49409_, BlockPos p_49410_, BlockState p_49411_, boolean p_49412_) {
        if (p_49411_.m_60713_(p_49408_.m_60734_())) {
            return;
        }
        this.m_49389_(p_49408_, p_49409_, p_49410_, p_49412_);
    }

    protected BlockState m_49389_(BlockState p_49390_, Level p_49391_, BlockPos p_49392_, boolean p_49393_) {
        p_49390_ = this.m_49367_(p_49391_, p_49392_, p_49390_, true);
        if (this.f_49357_) {
            p_49391_.m_213960_(p_49390_, p_49392_, this, p_49392_, p_49393_);
        }
        return p_49390_;
    }

    @Override
    public void m_6861_(BlockState p_49377_, Level p_49378_, BlockPos p_49379_, Block p_49380_, BlockPos p_49381_, boolean p_49382_) {
        if (p_49378_.f_46443_ || !p_49378_.m_8055_(p_49379_).m_60713_(this)) {
            return;
        }
        RailShape $$6 = p_49377_.m_61143_(this.m_7978_());
        if (BaseRailBlock.m_49398_(p_49379_, p_49378_, $$6)) {
            BaseRailBlock.m_49950_(p_49377_, p_49378_, p_49379_);
            p_49378_.m_7471_(p_49379_, p_49382_);
        } else {
            this.m_6360_(p_49377_, p_49378_, p_49379_, p_49380_);
        }
    }

    private static boolean m_49398_(BlockPos p_49399_, Level p_49400_, RailShape p_49401_) {
        if (!BaseRailBlock.m_49936_(p_49400_, p_49399_.m_7495_())) {
            return true;
        }
        switch (p_49401_) {
            case ASCENDING_EAST: {
                return !BaseRailBlock.m_49936_(p_49400_, p_49399_.m_122029_());
            }
            case ASCENDING_WEST: {
                return !BaseRailBlock.m_49936_(p_49400_, p_49399_.m_122024_());
            }
            case ASCENDING_NORTH: {
                return !BaseRailBlock.m_49936_(p_49400_, p_49399_.m_122012_());
            }
            case ASCENDING_SOUTH: {
                return !BaseRailBlock.m_49936_(p_49400_, p_49399_.m_122019_());
            }
        }
        return false;
    }

    protected void m_6360_(BlockState p_49372_, Level p_49373_, BlockPos p_49374_, Block p_49375_) {
    }

    protected BlockState m_49367_(Level p_49368_, BlockPos p_49369_, BlockState p_49370_, boolean p_49371_) {
        if (p_49368_.f_46443_) {
            return p_49370_;
        }
        RailShape $$4 = p_49370_.m_61143_(this.m_7978_());
        return new RailState(p_49368_, p_49369_, p_49370_).m_55431_(p_49368_.m_46753_(p_49369_), p_49371_, $$4).m_55440_();
    }

    @Override
    public PushReaction m_5537_(BlockState p_49415_) {
        return PushReaction.NORMAL;
    }

    @Override
    public void m_6810_(BlockState p_49384_, Level p_49385_, BlockPos p_49386_, BlockState p_49387_, boolean p_49388_) {
        if (p_49388_) {
            return;
        }
        super.m_6810_(p_49384_, p_49385_, p_49386_, p_49387_, p_49388_);
        if (p_49384_.m_61143_(this.m_7978_()).m_61745_()) {
            p_49385_.m_46672_(p_49386_.m_7494_(), this);
        }
        if (this.f_49357_) {
            p_49385_.m_46672_(p_49386_, this);
            p_49385_.m_46672_(p_49386_.m_7495_(), this);
        }
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_49363_) {
        FluidState $$1 = p_49363_.m_43725_().m_6425_(p_49363_.m_8083_());
        boolean $$2 = $$1.m_76152_() == Fluids.f_76193_;
        BlockState $$3 = super.m_49966_();
        Direction $$4 = p_49363_.m_8125_();
        boolean $$5 = $$4 == Direction.EAST || $$4 == Direction.WEST;
        return (BlockState)((BlockState)$$3.m_61124_(this.m_7978_(), $$5 ? RailShape.EAST_WEST : RailShape.NORTH_SOUTH)).m_61124_(f_152149_, $$2);
    }

    public abstract Property<RailShape> m_7978_();

    @Override
    public BlockState m_7417_(BlockState p_152151_, Direction p_152152_, BlockState p_152153_, LevelAccessor p_152154_, BlockPos p_152155_, BlockPos p_152156_) {
        if (p_152151_.m_61143_(f_152149_).booleanValue()) {
            p_152154_.m_186469_(p_152155_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_152154_));
        }
        return super.m_7417_(p_152151_, p_152152_, p_152153_, p_152154_, p_152155_, p_152156_);
    }

    @Override
    public FluidState m_5888_(BlockState p_152158_) {
        if (p_152158_.m_61143_(f_152149_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_152158_);
    }
}


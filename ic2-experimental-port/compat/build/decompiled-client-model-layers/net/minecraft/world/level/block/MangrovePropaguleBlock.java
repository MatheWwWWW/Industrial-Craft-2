/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.grower.MangroveTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MangrovePropaguleBlock
extends SaplingBlock
implements SimpleWaterloggedBlock {
    public static final IntegerProperty f_221441_ = BlockStateProperties.f_222999_;
    public static final int f_221442_ = 4;
    private static final VoxelShape[] f_221444_ = new VoxelShape[]{Block.m_49796_(7.0, 13.0, 7.0, 9.0, 16.0, 9.0), Block.m_49796_(7.0, 10.0, 7.0, 9.0, 16.0, 9.0), Block.m_49796_(7.0, 7.0, 7.0, 9.0, 16.0, 9.0), Block.m_49796_(7.0, 3.0, 7.0, 9.0, 16.0, 9.0), Block.m_49796_(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)};
    private static final BooleanProperty f_221445_ = BlockStateProperties.f_61362_;
    public static final BooleanProperty f_221443_ = BlockStateProperties.f_61435_;
    private static final float f_221446_ = 0.85f;

    public MangrovePropaguleBlock(BlockBehaviour.Properties p_221449_) {
        super(new MangroveTreeGrower(0.85f), p_221449_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_55973_, 0)).m_61124_(f_221441_, 0)).m_61124_(f_221445_, false)).m_61124_(f_221443_, false));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_221484_) {
        p_221484_.m_61104_(f_55973_).m_61104_(f_221441_).m_61104_(f_221445_).m_61104_(f_221443_);
    }

    @Override
    protected boolean m_6266_(BlockState p_221496_, BlockGetter p_221497_, BlockPos p_221498_) {
        return super.m_6266_(p_221496_, p_221497_, p_221498_) || p_221496_.m_60713_(Blocks.f_50129_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_221456_) {
        FluidState $$1 = p_221456_.m_43725_().m_6425_(p_221456_.m_8083_());
        boolean $$2 = $$1.m_76152_() == Fluids.f_76193_;
        return (BlockState)((BlockState)super.m_5573_(p_221456_).m_61124_(f_221445_, $$2)).m_61124_(f_221441_, 4);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_221468_, BlockGetter p_221469_, BlockPos p_221470_, CollisionContext p_221471_) {
        VoxelShape $$6;
        Vec3 $$4 = p_221468_.m_60824_(p_221469_, p_221470_);
        if (!p_221468_.m_61143_(f_221443_).booleanValue()) {
            VoxelShape $$5 = f_221444_[4];
        } else {
            $$6 = f_221444_[p_221468_.m_61143_(f_221441_)];
        }
        return $$6.m_83216_($$4.f_82479_, $$4.f_82480_, $$4.f_82481_);
    }

    @Override
    public boolean m_7898_(BlockState p_221473_, LevelReader p_221474_, BlockPos p_221475_) {
        if (MangrovePropaguleBlock.m_221499_(p_221473_)) {
            return p_221474_.m_8055_(p_221475_.m_7494_()).m_60713_(Blocks.f_220838_);
        }
        return super.m_7898_(p_221473_, p_221474_, p_221475_);
    }

    @Override
    public BlockState m_7417_(BlockState p_221477_, Direction p_221478_, BlockState p_221479_, LevelAccessor p_221480_, BlockPos p_221481_, BlockPos p_221482_) {
        if (p_221477_.m_61143_(f_221445_).booleanValue()) {
            p_221480_.m_186469_(p_221481_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_221480_));
        }
        if (p_221478_ == Direction.UP && !p_221477_.m_60710_(p_221480_, p_221481_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_221477_, p_221478_, p_221479_, p_221480_, p_221481_, p_221482_);
    }

    @Override
    public FluidState m_5888_(BlockState p_221494_) {
        if (p_221494_.m_61143_(f_221445_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_221494_);
    }

    @Override
    public void m_213898_(BlockState p_221488_, ServerLevel p_221489_, BlockPos p_221490_, RandomSource p_221491_) {
        if (!MangrovePropaguleBlock.m_221499_(p_221488_)) {
            if (p_221491_.m_188503_(7) == 0) {
                this.m_222000_(p_221489_, p_221490_, p_221488_, p_221491_);
            }
            return;
        }
        if (!MangrovePropaguleBlock.m_221501_(p_221488_)) {
            p_221489_.m_7731_(p_221490_, (BlockState)p_221488_.m_61122_(f_221441_), 2);
        }
    }

    @Override
    public boolean m_7370_(BlockGetter p_221458_, BlockPos p_221459_, BlockState p_221460_, boolean p_221461_) {
        return !MangrovePropaguleBlock.m_221499_(p_221460_) || !MangrovePropaguleBlock.m_221501_(p_221460_);
    }

    @Override
    public boolean m_214167_(Level p_221463_, RandomSource p_221464_, BlockPos p_221465_, BlockState p_221466_) {
        return MangrovePropaguleBlock.m_221499_(p_221466_) ? !MangrovePropaguleBlock.m_221501_(p_221466_) : super.m_214167_(p_221463_, p_221464_, p_221465_, p_221466_);
    }

    @Override
    public void m_214148_(ServerLevel p_221451_, RandomSource p_221452_, BlockPos p_221453_, BlockState p_221454_) {
        if (MangrovePropaguleBlock.m_221499_(p_221454_) && !MangrovePropaguleBlock.m_221501_(p_221454_)) {
            p_221451_.m_7731_(p_221453_, (BlockState)p_221454_.m_61122_(f_221441_), 2);
        } else {
            super.m_214148_(p_221451_, p_221452_, p_221453_, p_221454_);
        }
    }

    private static boolean m_221499_(BlockState p_221500_) {
        return p_221500_.m_61143_(f_221443_);
    }

    private static boolean m_221501_(BlockState p_221502_) {
        return p_221502_.m_61143_(f_221441_) == 4;
    }

    public static BlockState m_221492_() {
        return MangrovePropaguleBlock.m_221485_(0);
    }

    public static BlockState m_221485_(int p_221486_) {
        return (BlockState)((BlockState)Blocks.f_220831_.m_49966_().m_61124_(f_221443_, true)).m_61124_(f_221441_, p_221486_);
    }
}


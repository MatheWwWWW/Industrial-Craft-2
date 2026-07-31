/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LeverBlock
extends FaceAttachedHorizontalDirectionalBlock {
    public static final BooleanProperty f_54622_ = BlockStateProperties.f_61448_;
    protected static final int f_153653_ = 6;
    protected static final int f_153654_ = 6;
    protected static final int f_153655_ = 8;
    protected static final VoxelShape f_54623_ = Block.m_49796_(5.0, 4.0, 10.0, 11.0, 12.0, 16.0);
    protected static final VoxelShape f_54624_ = Block.m_49796_(5.0, 4.0, 0.0, 11.0, 12.0, 6.0);
    protected static final VoxelShape f_54625_ = Block.m_49796_(10.0, 4.0, 5.0, 16.0, 12.0, 11.0);
    protected static final VoxelShape f_54626_ = Block.m_49796_(0.0, 4.0, 5.0, 6.0, 12.0, 11.0);
    protected static final VoxelShape f_54627_ = Block.m_49796_(5.0, 0.0, 4.0, 11.0, 6.0, 12.0);
    protected static final VoxelShape f_54628_ = Block.m_49796_(4.0, 0.0, 5.0, 12.0, 6.0, 11.0);
    protected static final VoxelShape f_54629_ = Block.m_49796_(5.0, 10.0, 4.0, 11.0, 16.0, 12.0);
    protected static final VoxelShape f_54630_ = Block.m_49796_(4.0, 10.0, 5.0, 12.0, 16.0, 11.0);

    protected LeverBlock(BlockBehaviour.Properties p_54633_) {
        super(p_54633_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54117_, Direction.NORTH)).m_61124_(f_54622_, false)).m_61124_(f_53179_, AttachFace.WALL));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_54665_, BlockGetter p_54666_, BlockPos p_54667_, CollisionContext p_54668_) {
        switch ((AttachFace)p_54665_.m_61143_(f_53179_)) {
            case FLOOR: {
                switch (p_54665_.m_61143_(f_54117_).m_122434_()) {
                    case X: {
                        return f_54628_;
                    }
                }
                return f_54627_;
            }
            case WALL: {
                switch (p_54665_.m_61143_(f_54117_)) {
                    case EAST: {
                        return f_54626_;
                    }
                    case WEST: {
                        return f_54625_;
                    }
                    case SOUTH: {
                        return f_54624_;
                    }
                }
                return f_54623_;
            }
        }
        switch (p_54665_.m_61143_(f_54117_).m_122434_()) {
            case X: {
                return f_54630_;
            }
        }
        return f_54629_;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_54640_, Level p_54641_, BlockPos p_54642_, Player p_54643_, InteractionHand p_54644_, BlockHitResult p_54645_) {
        if (p_54641_.f_46443_) {
            BlockState $$6 = (BlockState)p_54640_.m_61122_(f_54622_);
            if ($$6.m_61143_(f_54622_).booleanValue()) {
                LeverBlock.m_54657_($$6, p_54641_, p_54642_, 1.0f);
            }
            return InteractionResult.SUCCESS;
        }
        BlockState $$7 = this.m_54676_(p_54640_, p_54641_, p_54642_);
        float $$8 = $$7.m_61143_(f_54622_) != false ? 0.6f : 0.5f;
        p_54641_.m_5594_(null, p_54642_, SoundEvents.f_12088_, SoundSource.BLOCKS, 0.3f, $$8);
        p_54641_.m_142346_(p_54643_, $$7.m_61143_(f_54622_) != false ? GameEvent.f_223702_ : GameEvent.f_223703_, p_54642_);
        return InteractionResult.CONSUME;
    }

    public BlockState m_54676_(BlockState p_54677_, Level p_54678_, BlockPos p_54679_) {
        p_54677_ = (BlockState)p_54677_.m_61122_(f_54622_);
        p_54678_.m_7731_(p_54679_, p_54677_, 3);
        this.m_54680_(p_54677_, p_54678_, p_54679_);
        return p_54677_;
    }

    private static void m_54657_(BlockState p_54658_, LevelAccessor p_54659_, BlockPos p_54660_, float p_54661_) {
        Direction $$4 = p_54658_.m_61143_(f_54117_).m_122424_();
        Direction $$5 = LeverBlock.m_53200_(p_54658_).m_122424_();
        double $$6 = (double)p_54660_.m_123341_() + 0.5 + 0.1 * (double)$$4.m_122429_() + 0.2 * (double)$$5.m_122429_();
        double $$7 = (double)p_54660_.m_123342_() + 0.5 + 0.1 * (double)$$4.m_122430_() + 0.2 * (double)$$5.m_122430_();
        double $$8 = (double)p_54660_.m_123343_() + 0.5 + 0.1 * (double)$$4.m_122431_() + 0.2 * (double)$$5.m_122431_();
        p_54659_.m_7106_(new DustParticleOptions(DustParticleOptions.f_175788_, p_54661_), $$6, $$7, $$8, 0.0, 0.0, 0.0);
    }

    @Override
    public void m_214162_(BlockState p_221395_, Level p_221396_, BlockPos p_221397_, RandomSource p_221398_) {
        if (p_221395_.m_61143_(f_54622_).booleanValue() && p_221398_.m_188501_() < 0.25f) {
            LeverBlock.m_54657_(p_221395_, p_221396_, p_221397_, 0.5f);
        }
    }

    @Override
    public void m_6810_(BlockState p_54647_, Level p_54648_, BlockPos p_54649_, BlockState p_54650_, boolean p_54651_) {
        if (p_54651_ || p_54647_.m_60713_(p_54650_.m_60734_())) {
            return;
        }
        if (p_54647_.m_61143_(f_54622_).booleanValue()) {
            this.m_54680_(p_54647_, p_54648_, p_54649_);
        }
        super.m_6810_(p_54647_, p_54648_, p_54649_, p_54650_, p_54651_);
    }

    @Override
    public int m_6378_(BlockState p_54635_, BlockGetter p_54636_, BlockPos p_54637_, Direction p_54638_) {
        return p_54635_.m_61143_(f_54622_) != false ? 15 : 0;
    }

    @Override
    public int m_6376_(BlockState p_54670_, BlockGetter p_54671_, BlockPos p_54672_, Direction p_54673_) {
        if (p_54670_.m_61143_(f_54622_).booleanValue() && LeverBlock.m_53200_(p_54670_) == p_54673_) {
            return 15;
        }
        return 0;
    }

    @Override
    public boolean m_7899_(BlockState p_54675_) {
        return true;
    }

    private void m_54680_(BlockState p_54681_, Level p_54682_, BlockPos p_54683_) {
        p_54682_.m_46672_(p_54683_, this);
        p_54682_.m_46672_(p_54683_.m_121945_(LeverBlock.m_53200_(p_54681_).m_122424_()), this);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54663_) {
        p_54663_.m_61104_(f_53179_, f_54117_, f_54622_);
    }
}


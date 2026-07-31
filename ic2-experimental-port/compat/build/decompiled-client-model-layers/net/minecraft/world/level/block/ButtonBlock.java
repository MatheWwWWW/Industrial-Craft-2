/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
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

public abstract class ButtonBlock
extends FaceAttachedHorizontalDirectionalBlock {
    public static final BooleanProperty f_51045_ = BlockStateProperties.f_61448_;
    private static final int f_152736_ = 1;
    private static final int f_152737_ = 2;
    protected static final int f_152738_ = 2;
    protected static final int f_152739_ = 3;
    protected static final VoxelShape f_51046_ = Block.m_49796_(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    protected static final VoxelShape f_51047_ = Block.m_49796_(5.0, 14.0, 6.0, 11.0, 16.0, 10.0);
    protected static final VoxelShape f_51048_ = Block.m_49796_(6.0, 0.0, 5.0, 10.0, 2.0, 11.0);
    protected static final VoxelShape f_51049_ = Block.m_49796_(5.0, 0.0, 6.0, 11.0, 2.0, 10.0);
    protected static final VoxelShape f_51050_ = Block.m_49796_(5.0, 6.0, 14.0, 11.0, 10.0, 16.0);
    protected static final VoxelShape f_51051_ = Block.m_49796_(5.0, 6.0, 0.0, 11.0, 10.0, 2.0);
    protected static final VoxelShape f_51052_ = Block.m_49796_(14.0, 6.0, 5.0, 16.0, 10.0, 11.0);
    protected static final VoxelShape f_51053_ = Block.m_49796_(0.0, 6.0, 5.0, 2.0, 10.0, 11.0);
    protected static final VoxelShape f_51054_ = Block.m_49796_(6.0, 15.0, 5.0, 10.0, 16.0, 11.0);
    protected static final VoxelShape f_51055_ = Block.m_49796_(5.0, 15.0, 6.0, 11.0, 16.0, 10.0);
    protected static final VoxelShape f_51056_ = Block.m_49796_(6.0, 0.0, 5.0, 10.0, 1.0, 11.0);
    protected static final VoxelShape f_51057_ = Block.m_49796_(5.0, 0.0, 6.0, 11.0, 1.0, 10.0);
    protected static final VoxelShape f_51058_ = Block.m_49796_(5.0, 6.0, 15.0, 11.0, 10.0, 16.0);
    protected static final VoxelShape f_51059_ = Block.m_49796_(5.0, 6.0, 0.0, 11.0, 10.0, 1.0);
    protected static final VoxelShape f_51060_ = Block.m_49796_(15.0, 6.0, 5.0, 16.0, 10.0, 11.0);
    protected static final VoxelShape f_51061_ = Block.m_49796_(0.0, 6.0, 5.0, 1.0, 10.0, 11.0);
    private final boolean f_51062_;

    protected ButtonBlock(boolean p_51065_, BlockBehaviour.Properties p_51066_) {
        super(p_51066_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54117_, Direction.NORTH)).m_61124_(f_51045_, false)).m_61124_(f_53179_, AttachFace.WALL));
        this.f_51062_ = p_51065_;
    }

    private int m_51115_() {
        return this.f_51062_ ? 30 : 20;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_51104_, BlockGetter p_51105_, BlockPos p_51106_, CollisionContext p_51107_) {
        Direction $$4 = p_51104_.m_61143_(f_54117_);
        boolean $$5 = p_51104_.m_61143_(f_51045_);
        switch ((AttachFace)p_51104_.m_61143_(f_53179_)) {
            case FLOOR: {
                if ($$4.m_122434_() == Direction.Axis.X) {
                    return $$5 ? f_51056_ : f_51048_;
                }
                return $$5 ? f_51057_ : f_51049_;
            }
            case WALL: {
                switch ($$4) {
                    case EAST: {
                        return $$5 ? f_51061_ : f_51053_;
                    }
                    case WEST: {
                        return $$5 ? f_51060_ : f_51052_;
                    }
                    case SOUTH: {
                        return $$5 ? f_51059_ : f_51051_;
                    }
                }
                return $$5 ? f_51058_ : f_51050_;
            }
        }
        if ($$4.m_122434_() == Direction.Axis.X) {
            return $$5 ? f_51054_ : f_51046_;
        }
        return $$5 ? f_51055_ : f_51047_;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_51088_, Level p_51089_, BlockPos p_51090_, Player p_51091_, InteractionHand p_51092_, BlockHitResult p_51093_) {
        if (p_51088_.m_61143_(f_51045_).booleanValue()) {
            return InteractionResult.CONSUME;
        }
        this.m_51116_(p_51088_, p_51089_, p_51090_);
        this.m_51067_(p_51091_, p_51089_, p_51090_, true);
        p_51089_.m_142346_(p_51091_, GameEvent.f_223702_, p_51090_);
        return InteractionResult.m_19078_(p_51089_.f_46443_);
    }

    public void m_51116_(BlockState p_51117_, Level p_51118_, BlockPos p_51119_) {
        p_51118_.m_7731_(p_51119_, (BlockState)p_51117_.m_61124_(f_51045_, true), 3);
        this.m_51124_(p_51117_, p_51118_, p_51119_);
        p_51118_.m_186460_(p_51119_, this, this.m_51115_());
    }

    protected void m_51067_(@Nullable Player p_51068_, LevelAccessor p_51069_, BlockPos p_51070_, boolean p_51071_) {
        p_51069_.m_5594_(p_51071_ ? p_51068_ : null, p_51070_, this.m_5722_(p_51071_), SoundSource.BLOCKS, 0.3f, p_51071_ ? 0.6f : 0.5f);
    }

    protected abstract SoundEvent m_5722_(boolean var1);

    @Override
    public void m_6810_(BlockState p_51095_, Level p_51096_, BlockPos p_51097_, BlockState p_51098_, boolean p_51099_) {
        if (p_51099_ || p_51095_.m_60713_(p_51098_.m_60734_())) {
            return;
        }
        if (p_51095_.m_61143_(f_51045_).booleanValue()) {
            this.m_51124_(p_51095_, p_51096_, p_51097_);
        }
        super.m_6810_(p_51095_, p_51096_, p_51097_, p_51098_, p_51099_);
    }

    @Override
    public int m_6378_(BlockState p_51078_, BlockGetter p_51079_, BlockPos p_51080_, Direction p_51081_) {
        return p_51078_.m_61143_(f_51045_) != false ? 15 : 0;
    }

    @Override
    public int m_6376_(BlockState p_51109_, BlockGetter p_51110_, BlockPos p_51111_, Direction p_51112_) {
        if (p_51109_.m_61143_(f_51045_).booleanValue() && ButtonBlock.m_53200_(p_51109_) == p_51112_) {
            return 15;
        }
        return 0;
    }

    @Override
    public boolean m_7899_(BlockState p_51114_) {
        return true;
    }

    @Override
    public void m_213897_(BlockState p_220903_, ServerLevel p_220904_, BlockPos p_220905_, RandomSource p_220906_) {
        if (!p_220903_.m_61143_(f_51045_).booleanValue()) {
            return;
        }
        if (this.f_51062_) {
            this.m_51120_(p_220903_, p_220904_, p_220905_);
        } else {
            p_220904_.m_7731_(p_220905_, (BlockState)p_220903_.m_61124_(f_51045_, false), 3);
            this.m_51124_(p_220903_, p_220904_, p_220905_);
            this.m_51067_(null, p_220904_, p_220905_, false);
            p_220904_.m_142346_(null, GameEvent.f_223703_, p_220905_);
        }
    }

    @Override
    public void m_7892_(BlockState p_51083_, Level p_51084_, BlockPos p_51085_, Entity p_51086_) {
        if (p_51084_.f_46443_ || !this.f_51062_ || p_51083_.m_61143_(f_51045_).booleanValue()) {
            return;
        }
        this.m_51120_(p_51083_, p_51084_, p_51085_);
    }

    private void m_51120_(BlockState p_51121_, Level p_51122_, BlockPos p_51123_) {
        boolean $$5;
        List<AbstractArrow> $$3 = p_51122_.m_45976_(AbstractArrow.class, p_51121_.m_60808_(p_51122_, p_51123_).m_83215_().m_82338_(p_51123_));
        boolean $$4 = !$$3.isEmpty();
        if ($$4 != ($$5 = p_51121_.m_61143_(f_51045_).booleanValue())) {
            p_51122_.m_7731_(p_51123_, (BlockState)p_51121_.m_61124_(f_51045_, $$4), 3);
            this.m_51124_(p_51121_, p_51122_, p_51123_);
            this.m_51067_(null, p_51122_, p_51123_, $$4);
            p_51122_.m_142346_($$3.stream().findFirst().orElse(null), $$4 ? GameEvent.f_223702_ : GameEvent.f_223703_, p_51123_);
        }
        if ($$4) {
            p_51122_.m_186460_(new BlockPos(p_51123_), this, this.m_51115_());
        }
    }

    private void m_51124_(BlockState p_51125_, Level p_51126_, BlockPos p_51127_) {
        p_51126_.m_46672_(p_51127_, this);
        p_51126_.m_46672_(p_51127_.m_121945_(ButtonBlock.m_53200_(p_51125_).m_122424_()), this);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51101_) {
        p_51101_.m_61104_(f_54117_, f_51045_, f_53179_);
    }
}


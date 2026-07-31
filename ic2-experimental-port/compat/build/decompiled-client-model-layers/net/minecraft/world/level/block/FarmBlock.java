/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FarmBlock
extends Block {
    public static final IntegerProperty f_53243_ = BlockStateProperties.f_61423_;
    protected static final VoxelShape f_53244_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);
    public static final int f_153225_ = 7;

    protected FarmBlock(BlockBehaviour.Properties p_53247_) {
        super(p_53247_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_53243_, 0));
    }

    @Override
    public BlockState m_7417_(BlockState p_53276_, Direction p_53277_, BlockState p_53278_, LevelAccessor p_53279_, BlockPos p_53280_, BlockPos p_53281_) {
        if (p_53277_ == Direction.UP && !p_53276_.m_60710_(p_53279_, p_53280_)) {
            p_53279_.m_186460_(p_53280_, this, 1);
        }
        return super.m_7417_(p_53276_, p_53277_, p_53278_, p_53279_, p_53280_, p_53281_);
    }

    @Override
    public boolean m_7898_(BlockState p_53272_, LevelReader p_53273_, BlockPos p_53274_) {
        BlockState $$3 = p_53273_.m_8055_(p_53274_.m_7494_());
        return !$$3.m_60767_().m_76333_() || $$3.m_60734_() instanceof FenceGateBlock || $$3.m_60734_() instanceof MovingPistonBlock;
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_53249_) {
        if (!this.m_49966_().m_60710_(p_53249_.m_43725_(), p_53249_.m_8083_())) {
            return Blocks.f_50493_.m_49966_();
        }
        return super.m_5573_(p_53249_);
    }

    @Override
    public boolean m_7923_(BlockState p_53295_) {
        return true;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_53290_, BlockGetter p_53291_, BlockPos p_53292_, CollisionContext p_53293_) {
        return f_53244_;
    }

    @Override
    public void m_213897_(BlockState p_221134_, ServerLevel p_221135_, BlockPos p_221136_, RandomSource p_221137_) {
        if (!p_221134_.m_60710_(p_221135_, p_221136_)) {
            FarmBlock.m_53296_(p_221134_, p_221135_, p_221136_);
        }
    }

    @Override
    public void m_213898_(BlockState p_221139_, ServerLevel p_221140_, BlockPos p_221141_, RandomSource p_221142_) {
        int $$4 = p_221139_.m_61143_(f_53243_);
        if (FarmBlock.m_53258_(p_221140_, p_221141_) || p_221140_.m_46758_(p_221141_.m_7494_())) {
            if ($$4 < 7) {
                p_221140_.m_7731_(p_221141_, (BlockState)p_221139_.m_61124_(f_53243_, 7), 2);
            }
        } else if ($$4 > 0) {
            p_221140_.m_7731_(p_221141_, (BlockState)p_221139_.m_61124_(f_53243_, $$4 - 1), 2);
        } else if (!FarmBlock.m_53250_(p_221140_, p_221141_)) {
            FarmBlock.m_53296_(p_221139_, p_221140_, p_221141_);
        }
    }

    @Override
    public void m_142072_(Level p_153227_, BlockState p_153228_, BlockPos p_153229_, Entity p_153230_, float p_153231_) {
        if (!p_153227_.f_46443_ && p_153227_.f_46441_.m_188501_() < p_153231_ - 0.5f && p_153230_ instanceof LivingEntity && (p_153230_ instanceof Player || p_153227_.m_46469_().m_46207_(GameRules.f_46132_)) && p_153230_.m_20205_() * p_153230_.m_20205_() * p_153230_.m_20206_() > 0.512f) {
            FarmBlock.m_53296_(p_153228_, p_153227_, p_153229_);
        }
        super.m_142072_(p_153227_, p_153228_, p_153229_, p_153230_, p_153231_);
    }

    public static void m_53296_(BlockState p_53297_, Level p_53298_, BlockPos p_53299_) {
        p_53298_.m_46597_(p_53299_, FarmBlock.m_49897_(p_53297_, Blocks.f_50493_.m_49966_(), p_53298_, p_53299_));
    }

    private static boolean m_53250_(BlockGetter p_53251_, BlockPos p_53252_) {
        Block $$2 = p_53251_.m_8055_(p_53252_.m_7494_()).m_60734_();
        return $$2 instanceof CropBlock || $$2 instanceof StemBlock || $$2 instanceof AttachedStemBlock;
    }

    private static boolean m_53258_(LevelReader p_53259_, BlockPos p_53260_) {
        for (BlockPos $$2 : BlockPos.m_121940_(p_53260_.m_7918_(-4, 0, -4), p_53260_.m_7918_(4, 1, 4))) {
            if (!p_53259_.m_6425_($$2).m_205070_(FluidTags.f_13131_)) continue;
            return true;
        }
        return false;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53283_) {
        p_53283_.m_61104_(f_53243_);
    }

    @Override
    public boolean m_7357_(BlockState p_53267_, BlockGetter p_53268_, BlockPos p_53269_, PathComputationType p_53270_) {
        return false;
    }
}


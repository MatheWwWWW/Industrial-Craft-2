/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Collection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;

public class SculkVeinBlock
extends MultifaceBlock
implements SculkBehaviour,
SimpleWaterloggedBlock {
    private static final BooleanProperty f_222348_ = BlockStateProperties.f_61362_;
    private final MultifaceSpreader f_222349_ = new MultifaceSpreader(new SculkVeinSpreaderConfig(MultifaceSpreader.f_221586_));
    private final MultifaceSpreader f_222350_ = new MultifaceSpreader(new SculkVeinSpreaderConfig(MultifaceSpreader.SpreadType.SAME_POSITION));

    public SculkVeinBlock(BlockBehaviour.Properties p_222353_) {
        super(p_222353_);
        this.m_49959_((BlockState)this.m_49966_().m_61124_(f_222348_, false));
    }

    @Override
    public MultifaceSpreader m_213612_() {
        return this.f_222349_;
    }

    public MultifaceSpreader m_222395_() {
        return this.f_222350_;
    }

    public static boolean m_222363_(LevelAccessor p_222364_, BlockPos p_222365_, BlockState p_222366_, Collection<Direction> p_222367_) {
        boolean $$4 = false;
        BlockState $$5 = Blocks.f_220856_.m_49966_();
        for (Direction $$6 : p_222367_) {
            BlockPos $$7;
            if (!SculkVeinBlock.m_153829_(p_222364_, $$6, $$7 = p_222365_.m_121945_($$6), p_222364_.m_8055_($$7))) continue;
            $$5 = (BlockState)$$5.m_61124_(SculkVeinBlock.m_153933_($$6), true);
            $$4 = true;
        }
        if (!$$4) {
            return false;
        }
        if (!p_222366_.m_60819_().m_76178_()) {
            $$5 = (BlockState)$$5.m_61124_(f_222348_, true);
        }
        p_222364_.m_7731_(p_222365_, $$5, 3);
        return true;
    }

    @Override
    public void m_213805_(LevelAccessor p_222359_, BlockState p_222360_, BlockPos p_222361_, RandomSource p_222362_) {
        if (!p_222360_.m_60713_(this)) {
            return;
        }
        for (Direction $$4 : f_153806_) {
            BooleanProperty $$5 = SculkVeinBlock.m_153933_($$4);
            if (!p_222360_.m_61143_($$5).booleanValue() || !p_222359_.m_8055_(p_222361_.m_121945_($$4)).m_60713_(Blocks.f_220855_)) continue;
            p_222360_ = (BlockState)p_222360_.m_61124_($$5, false);
        }
        if (!SculkVeinBlock.m_153960_(p_222360_)) {
            FluidState $$6 = p_222359_.m_6425_(p_222361_);
            p_222360_ = ($$6.m_76178_() ? Blocks.f_50016_ : Blocks.f_49990_).m_49966_();
        }
        p_222359_.m_7731_(p_222361_, p_222360_, 3);
        SculkBehaviour.super.m_213805_(p_222359_, p_222360_, p_222361_, p_222362_);
    }

    @Override
    public int m_213628_(SculkSpreader.ChargeCursor p_222369_, LevelAccessor p_222370_, BlockPos p_222371_, RandomSource p_222372_, SculkSpreader p_222373_, boolean p_222374_) {
        if (p_222374_ && this.m_222375_(p_222373_, p_222370_, p_222369_.m_222304_(), p_222372_)) {
            return p_222369_.m_222341_() - 1;
        }
        return p_222372_.m_188503_(p_222373_.m_222280_()) == 0 ? Mth.m_14143_((float)p_222369_.m_222341_() * 0.5f) : p_222369_.m_222341_();
    }

    private boolean m_222375_(SculkSpreader p_222376_, LevelAccessor p_222377_, BlockPos p_222378_, RandomSource p_222379_) {
        BlockState $$4 = p_222377_.m_8055_(p_222378_);
        TagKey<Block> $$5 = p_222376_.m_222277_();
        for (Direction $$6 : Direction.m_235667_(p_222379_)) {
            BlockPos $$7;
            BlockState $$8;
            if (!SculkVeinBlock.m_153900_($$4, $$6) || !($$8 = p_222377_.m_8055_($$7 = p_222378_.m_121945_($$6))).m_204336_($$5)) continue;
            BlockState $$9 = Blocks.f_220855_.m_49966_();
            p_222377_.m_7731_($$7, $$9, 3);
            Block.m_49897_($$8, $$9, p_222377_, $$7);
            p_222377_.m_5594_(null, $$7, SoundEvents.f_215753_, SoundSource.BLOCKS, 1.0f, 1.0f);
            this.f_222349_.m_221657_($$9, p_222377_, $$7, p_222376_.m_222282_());
            Direction $$10 = $$6.m_122424_();
            for (Direction $$11 : f_153806_) {
                BlockPos $$12;
                BlockState $$13;
                if ($$11 == $$10 || !($$13 = p_222377_.m_8055_($$12 = $$7.m_121945_($$11))).m_60713_(this)) continue;
                this.m_213805_(p_222377_, $$13, $$12, p_222379_);
            }
            return true;
        }
        return false;
    }

    public static boolean m_222354_(LevelAccessor p_222355_, BlockState p_222356_, BlockPos p_222357_) {
        if (!p_222356_.m_60713_(Blocks.f_220856_)) {
            return false;
        }
        for (Direction $$3 : f_153806_) {
            if (!SculkVeinBlock.m_153900_(p_222356_, $$3) || !p_222355_.m_8055_(p_222357_.m_121945_($$3)).m_204336_(BlockTags.f_215823_)) continue;
            return true;
        }
        return false;
    }

    @Override
    public BlockState m_7417_(BlockState p_222384_, Direction p_222385_, BlockState p_222386_, LevelAccessor p_222387_, BlockPos p_222388_, BlockPos p_222389_) {
        if (p_222384_.m_61143_(f_222348_).booleanValue()) {
            p_222387_.m_186469_(p_222388_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_222387_));
        }
        return super.m_7417_(p_222384_, p_222385_, p_222386_, p_222387_, p_222388_, p_222389_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_222391_) {
        super.m_7926_(p_222391_);
        p_222391_.m_61104_(f_222348_);
    }

    @Override
    public boolean m_6864_(BlockState p_222381_, BlockPlaceContext p_222382_) {
        return !p_222382_.m_43722_().m_150930_(Items.f_220193_) || super.m_6864_(p_222381_, p_222382_);
    }

    @Override
    public FluidState m_5888_(BlockState p_222394_) {
        if (p_222394_.m_61143_(f_222348_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_222394_);
    }

    @Override
    public PushReaction m_5537_(BlockState p_222397_) {
        return PushReaction.DESTROY;
    }

    class SculkVeinSpreaderConfig
    extends MultifaceSpreader.DefaultSpreaderConfig {
        private final MultifaceSpreader.SpreadType[] f_222399_;

        public SculkVeinSpreaderConfig(MultifaceSpreader.SpreadType ... p_222402_) {
            super(SculkVeinBlock.this);
            this.f_222399_ = p_222402_;
        }

        @Override
        public boolean m_213938_(BlockGetter p_222405_, BlockPos p_222406_, BlockPos p_222407_, Direction p_222408_, BlockState p_222409_) {
            BlockPos $$6;
            BlockState $$5 = p_222405_.m_8055_(p_222407_.m_121945_(p_222408_));
            if ($$5.m_60713_(Blocks.f_220855_) || $$5.m_60713_(Blocks.f_220857_) || $$5.m_60713_(Blocks.f_50110_)) {
                return false;
            }
            if (p_222406_.m_123333_(p_222407_) == 2 && p_222405_.m_8055_($$6 = p_222406_.m_121945_(p_222408_.m_122424_())).m_60783_(p_222405_, $$6, p_222408_)) {
                return false;
            }
            FluidState $$7 = p_222409_.m_60819_();
            if (!$$7.m_76178_() && !$$7.m_192917_(Fluids.f_76193_)) {
                return false;
            }
            Material $$8 = p_222409_.m_60767_();
            if ($$8 == Material.f_76309_) {
                return false;
            }
            return $$8.m_76336_() || super.m_213938_(p_222405_, p_222406_, p_222407_, p_222408_, p_222409_);
        }

        @Override
        public MultifaceSpreader.SpreadType[] m_214109_() {
            return this.f_222399_;
        }

        @Override
        public boolean m_214107_(BlockState p_222411_) {
            return !p_222411_.m_60713_(Blocks.f_220856_);
        }
    }
}


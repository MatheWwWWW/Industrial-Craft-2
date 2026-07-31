/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class BlockAgeProcessor
extends StructureProcessor {
    public static final Codec<BlockAgeProcessor> f_74009_ = Codec.FLOAT.fieldOf("mossiness").xmap(BlockAgeProcessor::new, p_74023_ -> Float.valueOf(p_74023_.f_74010_)).codec();
    private static final float f_163720_ = 0.5f;
    private static final float f_163721_ = 0.5f;
    private static final float f_163722_ = 0.15f;
    private static final BlockState[] f_163723_ = new BlockState[]{Blocks.f_50404_.m_49966_(), Blocks.f_50411_.m_49966_()};
    private final float f_74010_;

    public BlockAgeProcessor(float p_74013_) {
        this.f_74010_ = p_74013_;
    }

    @Override
    @Nullable
    public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74016_, BlockPos p_74017_, BlockPos p_74018_, StructureTemplate.StructureBlockInfo p_74019_, StructureTemplate.StructureBlockInfo p_74020_, StructurePlaceSettings p_74021_) {
        RandomSource $$6 = p_74021_.m_230326_(p_74020_.f_74675_);
        BlockState $$7 = p_74020_.f_74676_;
        BlockPos $$8 = p_74020_.f_74675_;
        BlockState $$9 = null;
        if ($$7.m_60713_(Blocks.f_50222_) || $$7.m_60713_(Blocks.f_50069_) || $$7.m_60713_(Blocks.f_50225_)) {
            $$9 = this.m_230255_($$6);
        } else if ($$7.m_204336_(BlockTags.f_13030_)) {
            $$9 = this.m_230260_($$6, p_74020_.f_74676_);
        } else if ($$7.m_204336_(BlockTags.f_13031_)) {
            $$9 = this.m_230270_($$6);
        } else if ($$7.m_204336_(BlockTags.f_13032_)) {
            $$9 = this.m_230272_($$6);
        } else if ($$7.m_60713_(Blocks.f_50080_)) {
            $$9 = this.m_230274_($$6);
        }
        if ($$9 != null) {
            return new StructureTemplate.StructureBlockInfo($$8, $$9, p_74020_.f_74677_);
        }
        return p_74020_;
    }

    @Nullable
    private BlockState m_230255_(RandomSource p_230256_) {
        if (p_230256_.m_188501_() >= 0.5f) {
            return null;
        }
        BlockState[] $$1 = new BlockState[]{Blocks.f_50224_.m_49966_(), BlockAgeProcessor.m_230257_(p_230256_, Blocks.f_50194_)};
        BlockState[] $$2 = new BlockState[]{Blocks.f_50223_.m_49966_(), BlockAgeProcessor.m_230257_(p_230256_, Blocks.f_50631_)};
        return this.m_230266_(p_230256_, $$1, $$2);
    }

    @Nullable
    private BlockState m_230260_(RandomSource p_230261_, BlockState p_230262_) {
        Direction $$2 = p_230262_.m_61143_(StairBlock.f_56841_);
        Half $$3 = p_230262_.m_61143_(StairBlock.f_56842_);
        if (p_230261_.m_188501_() >= 0.5f) {
            return null;
        }
        BlockState[] $$4 = new BlockState[]{(BlockState)((BlockState)Blocks.f_50631_.m_49966_().m_61124_(StairBlock.f_56841_, $$2)).m_61124_(StairBlock.f_56842_, $$3), Blocks.f_50645_.m_49966_()};
        return this.m_230266_(p_230261_, f_163723_, $$4);
    }

    @Nullable
    private BlockState m_230270_(RandomSource p_230271_) {
        if (p_230271_.m_188501_() < this.f_74010_) {
            return Blocks.f_50645_.m_49966_();
        }
        return null;
    }

    @Nullable
    private BlockState m_230272_(RandomSource p_230273_) {
        if (p_230273_.m_188501_() < this.f_74010_) {
            return Blocks.f_50607_.m_49966_();
        }
        return null;
    }

    @Nullable
    private BlockState m_230274_(RandomSource p_230275_) {
        if (p_230275_.m_188501_() < 0.15f) {
            return Blocks.f_50723_.m_49966_();
        }
        return null;
    }

    private static BlockState m_230257_(RandomSource p_230258_, Block p_230259_) {
        return (BlockState)((BlockState)p_230259_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.Plane.HORIZONTAL.m_235690_(p_230258_))).m_61124_(StairBlock.f_56842_, Half.values()[p_230258_.m_188503_(Half.values().length)]);
    }

    private BlockState m_230266_(RandomSource p_230267_, BlockState[] p_230268_, BlockState[] p_230269_) {
        if (p_230267_.m_188501_() < this.f_74010_) {
            return BlockAgeProcessor.m_230263_(p_230267_, p_230269_);
        }
        return BlockAgeProcessor.m_230263_(p_230267_, p_230268_);
    }

    private static BlockState m_230263_(RandomSource p_230264_, BlockState[] p_230265_) {
        return p_230265_[p_230264_.m_188503_(p_230265_.length)];
    }

    @Override
    protected StructureProcessorType<?> m_6953_() {
        return StructureProcessorType.f_74462_;
    }
}


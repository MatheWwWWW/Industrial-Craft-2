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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class LavaSubmergedBlockProcessor
extends StructureProcessor {
    public static final Codec<LavaSubmergedBlockProcessor> f_74134_ = Codec.unit(() -> f_74135_);
    public static final LavaSubmergedBlockProcessor f_74135_ = new LavaSubmergedBlockProcessor();

    @Override
    @Nullable
    public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74140_, BlockPos p_74141_, BlockPos p_74142_, StructureTemplate.StructureBlockInfo p_74143_, StructureTemplate.StructureBlockInfo p_74144_, StructurePlaceSettings p_74145_) {
        BlockPos $$6 = p_74144_.f_74675_;
        boolean $$7 = p_74140_.m_8055_($$6).m_60713_(Blocks.f_49991_);
        if ($$7 && !Block.m_49916_(p_74144_.f_74676_.m_60808_(p_74140_, $$6))) {
            return new StructureTemplate.StructureBlockInfo($$6, Blocks.f_49991_.m_49966_(), p_74144_.f_74677_);
        }
        return p_74144_;
    }

    @Override
    protected StructureProcessorType<?> m_6953_() {
        return StructureProcessorType.f_74464_;
    }
}


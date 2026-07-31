/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class BlockRotProcessor
extends StructureProcessor {
    public static final Codec<BlockRotProcessor> f_74074_ = RecordCodecBuilder.create(p_230287_ -> p_230287_.group((App)RegistryCodecs.m_206277_(Registry.f_122901_).optionalFieldOf("rottable_blocks").forGetter(p_230291_ -> p_230291_.f_230279_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("integrity").forGetter(p_230289_ -> Float.valueOf(p_230289_.f_74075_))).apply((Applicative)p_230287_, BlockRotProcessor::new));
    private Optional<HolderSet<Block>> f_230279_;
    private final float f_74075_;

    public BlockRotProcessor(TagKey<Block> p_230281_, float p_230282_) {
        this(Optional.of(Registry.f_122824_.m_203561_(p_230281_)), p_230282_);
    }

    public BlockRotProcessor(float p_74078_) {
        this(Optional.empty(), p_74078_);
    }

    private BlockRotProcessor(Optional<HolderSet<Block>> p_230284_, float p_230285_) {
        this.f_74075_ = p_230285_;
        this.f_230279_ = p_230284_;
    }

    @Override
    @Nullable
    public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74081_, BlockPos p_74082_, BlockPos p_74083_, StructureTemplate.StructureBlockInfo p_74084_, StructureTemplate.StructureBlockInfo p_74085_, StructurePlaceSettings p_74086_) {
        RandomSource $$6 = p_74086_.m_230326_(p_74085_.f_74675_);
        if (this.f_230279_.isPresent() && !p_74084_.f_74676_.m_204341_(this.f_230279_.get()) || $$6.m_188501_() <= this.f_74075_) {
            return p_74085_;
        }
        return null;
    }

    @Override
    protected StructureProcessorType<?> m_6953_() {
        return StructureProcessorType.f_74457_;
    }
}


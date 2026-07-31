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
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class ProtectedBlockProcessor
extends StructureProcessor {
    public final TagKey<Block> f_163748_;
    public static final Codec<ProtectedBlockProcessor> f_163749_ = TagKey.m_203886_(Registry.f_122901_).xmap(ProtectedBlockProcessor::new, p_205053_ -> p_205053_.f_163748_);

    public ProtectedBlockProcessor(TagKey<Block> p_205051_) {
        this.f_163748_ = p_205051_;
    }

    @Override
    @Nullable
    public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_163755_, BlockPos p_163756_, BlockPos p_163757_, StructureTemplate.StructureBlockInfo p_163758_, StructureTemplate.StructureBlockInfo p_163759_, StructurePlaceSettings p_163760_) {
        if (Feature.m_204735_(this.f_163748_).test(p_163755_.m_8055_(p_163759_.f_74675_))) {
            return p_163759_;
        }
        return null;
    }

    @Override
    protected StructureProcessorType<?> m_6953_() {
        return StructureProcessorType.f_163784_;
    }
}


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
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class NopProcessor
extends StructureProcessor {
    public static final Codec<NopProcessor> f_74174_ = Codec.unit(() -> f_74175_);
    public static final NopProcessor f_74175_ = new NopProcessor();

    private NopProcessor() {
    }

    @Override
    @Nullable
    public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74180_, BlockPos p_74181_, BlockPos p_74182_, StructureTemplate.StructureBlockInfo p_74183_, StructureTemplate.StructureBlockInfo p_74184_, StructurePlaceSettings p_74185_) {
        return p_74184_;
    }

    @Override
    protected StructureProcessorType<?> m_6953_() {
        return StructureProcessorType.f_74461_;
    }
}


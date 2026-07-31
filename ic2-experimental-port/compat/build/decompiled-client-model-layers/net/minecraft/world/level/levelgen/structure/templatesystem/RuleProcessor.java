/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class RuleProcessor
extends StructureProcessor {
    public static final Codec<RuleProcessor> f_74292_ = ProcessorRule.f_74215_.listOf().fieldOf("rules").xmap(RuleProcessor::new, p_74306_ -> p_74306_.f_74293_).codec();
    private final ImmutableList<ProcessorRule> f_74293_;

    public RuleProcessor(List<? extends ProcessorRule> p_74296_) {
        this.f_74293_ = ImmutableList.copyOf(p_74296_);
    }

    @Override
    @Nullable
    public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74299_, BlockPos p_74300_, BlockPos p_74301_, StructureTemplate.StructureBlockInfo p_74302_, StructureTemplate.StructureBlockInfo p_74303_, StructurePlaceSettings p_74304_) {
        RandomSource $$6 = RandomSource.m_216335_(Mth.m_14057_(p_74303_.f_74675_));
        BlockState $$7 = p_74299_.m_8055_(p_74303_.f_74675_);
        for (ProcessorRule $$8 : this.f_74293_) {
            if (!$$8.m_230309_(p_74303_.f_74676_, $$7, p_74302_.f_74675_, p_74303_.f_74675_, p_74301_, $$6)) continue;
            return new StructureTemplate.StructureBlockInfo(p_74303_.f_74675_, $$8.m_74237_(), $$8.m_74249_());
        }
        return p_74303_;
    }

    @Override
    protected StructureProcessorType<?> m_6953_() {
        return StructureProcessorType.f_74460_;
    }
}


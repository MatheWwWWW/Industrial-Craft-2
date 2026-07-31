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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class BlockIgnoreProcessor
extends StructureProcessor {
    public static final Codec<BlockIgnoreProcessor> f_74045_ = BlockState.f_61039_.xmap(BlockBehaviour.BlockStateBase::m_60734_, Block::m_49966_).listOf().fieldOf("blocks").xmap(BlockIgnoreProcessor::new, p_74062_ -> p_74062_.f_74049_).codec();
    public static final BlockIgnoreProcessor f_74046_ = new BlockIgnoreProcessor((List<Block>)ImmutableList.of((Object)Blocks.f_50677_));
    public static final BlockIgnoreProcessor f_74047_ = new BlockIgnoreProcessor((List<Block>)ImmutableList.of((Object)Blocks.f_50016_));
    public static final BlockIgnoreProcessor f_74048_ = new BlockIgnoreProcessor((List<Block>)ImmutableList.of((Object)Blocks.f_50016_, (Object)Blocks.f_50677_));
    private final ImmutableList<Block> f_74049_;

    public BlockIgnoreProcessor(List<Block> p_74052_) {
        this.f_74049_ = ImmutableList.copyOf(p_74052_);
    }

    @Override
    @Nullable
    public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74055_, BlockPos p_74056_, BlockPos p_74057_, StructureTemplate.StructureBlockInfo p_74058_, StructureTemplate.StructureBlockInfo p_74059_, StructurePlaceSettings p_74060_) {
        if (this.f_74049_.contains((Object)p_74059_.f_74676_.m_60734_())) {
            return null;
        }
        return p_74059_;
    }

    @Override
    protected StructureProcessorType<?> m_6953_() {
        return StructureProcessorType.f_74456_;
    }
}


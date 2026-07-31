/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class JigsawReplacementProcessor
extends StructureProcessor {
    public static final Codec<JigsawReplacementProcessor> f_74121_ = Codec.unit(() -> f_74122_);
    public static final JigsawReplacementProcessor f_74122_ = new JigsawReplacementProcessor();

    private JigsawReplacementProcessor() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @Nullable
    public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74127_, BlockPos p_74128_, BlockPos p_74129_, StructureTemplate.StructureBlockInfo p_74130_, StructureTemplate.StructureBlockInfo p_74131_, StructurePlaceSettings p_74132_) {
        void $$11;
        BlockState $$6 = p_74131_.f_74676_;
        if (!$$6.m_60713_(Blocks.f_50678_)) {
            return p_74131_;
        }
        String $$7 = p_74131_.f_74677_.m_128461_("final_state");
        try {
            BlockStateParser.BlockResult $$8 = BlockStateParser.m_234704_(Registry.f_122824_, $$7, true);
            BlockState $$9 = $$8.f_234748_();
        }
        catch (CommandSyntaxException $$10) {
            throw new RuntimeException($$10);
        }
        if ($$11.m_60713_(Blocks.f_50454_)) {
            return null;
        }
        return new StructureTemplate.StructureBlockInfo(p_74131_.f_74675_, (BlockState)$$11, null);
    }

    @Override
    protected StructureProcessorType<?> m_6953_() {
        return StructureProcessorType.f_74459_;
    }
}


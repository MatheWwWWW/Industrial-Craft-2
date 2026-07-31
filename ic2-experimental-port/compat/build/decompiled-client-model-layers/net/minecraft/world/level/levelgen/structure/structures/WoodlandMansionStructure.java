/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.LinkedList;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionPieces;

public class WoodlandMansionStructure
extends Structure {
    public static final Codec<WoodlandMansionStructure> f_230222_ = WoodlandMansionStructure.m_226607_(WoodlandMansionStructure::new);

    public WoodlandMansionStructure(Structure.StructureSettings p_230225_) {
        super(p_230225_);
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_230235_) {
        Rotation $$1 = Rotation.m_221990_(p_230235_.f_226626_());
        BlockPos $$2 = this.m_226582_(p_230235_, $$1);
        if ($$2.m_123342_() < 60) {
            return Optional.empty();
        }
        return Optional.of(new Structure.GenerationStub($$2, p_230240_ -> this.m_230241_((StructurePiecesBuilder)p_230240_, p_230235_, $$2, $$1)));
    }

    private void m_230241_(StructurePiecesBuilder p_230242_, Structure.GenerationContext p_230243_, BlockPos p_230244_, Rotation p_230245_) {
        LinkedList $$4 = Lists.newLinkedList();
        WoodlandMansionPieces.m_229985_(p_230243_.f_226625_(), p_230244_, p_230245_, $$4, p_230243_.f_226626_());
        $$4.forEach(p_230242_::m_142679_);
    }

    @Override
    public void m_214110_(WorldGenLevel p_230227_, StructureManager p_230228_, ChunkGenerator p_230229_, RandomSource p_230230_, BoundingBox p_230231_, ChunkPos p_230232_, PiecesContainer p_230233_) {
        BlockPos.MutableBlockPos $$7 = new BlockPos.MutableBlockPos();
        int $$8 = p_230227_.m_141937_();
        BoundingBox $$9 = p_230233_.m_192756_();
        int $$10 = $$9.m_162396_();
        for (int $$11 = p_230231_.m_162395_(); $$11 <= p_230231_.m_162399_(); ++$$11) {
            block1: for (int $$12 = p_230231_.m_162398_(); $$12 <= p_230231_.m_162401_(); ++$$12) {
                $$7.m_122178_($$11, $$10, $$12);
                if (p_230227_.m_46859_($$7) || !$$9.m_71051_($$7) || !p_230233_.m_192751_($$7)) continue;
                for (int $$13 = $$10 - 1; $$13 > $$8; --$$13) {
                    $$7.m_142448_($$13);
                    if (!p_230227_.m_46859_($$7) && !p_230227_.m_8055_($$7).m_60767_().m_76332_()) continue block1;
                    p_230227_.m_7731_($$7, Blocks.f_50652_.m_49966_(), 2);
                }
            }
        }
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226877_;
    }
}


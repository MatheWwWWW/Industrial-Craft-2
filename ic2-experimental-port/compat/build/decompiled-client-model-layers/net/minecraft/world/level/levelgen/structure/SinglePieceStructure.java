/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure;

import java.util.Optional;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public abstract class SinglePieceStructure
extends Structure {
    private final PieceConstructor f_226533_;
    private int f_226534_;
    private int f_226535_;

    protected SinglePieceStructure(PieceConstructor p_226537_, int p_226538_, int p_226539_, Structure.StructureSettings p_226540_) {
        super(p_226540_);
        this.f_226533_ = p_226537_;
        this.f_226534_ = p_226538_;
        this.f_226535_ = p_226539_;
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_226542_) {
        if (SinglePieceStructure.m_226572_(p_226542_, this.f_226534_, this.f_226535_) < p_226542_.f_226622_().m_6337_()) {
            return Optional.empty();
        }
        return SinglePieceStructure.m_226585_(p_226542_, Heightmap.Types.WORLD_SURFACE_WG, p_226545_ -> this.m_226546_((StructurePiecesBuilder)p_226545_, p_226542_));
    }

    private void m_226546_(StructurePiecesBuilder p_226547_, Structure.GenerationContext p_226548_) {
        ChunkPos $$2 = p_226548_.f_226628_();
        p_226547_.m_142679_(this.f_226533_.m_226549_(p_226548_.f_226626_(), $$2.m_45604_(), $$2.m_45605_()));
    }

    @FunctionalInterface
    protected static interface PieceConstructor {
        public StructurePiece m_226549_(WorldgenRandom var1, int var2, int var3);
    }
}


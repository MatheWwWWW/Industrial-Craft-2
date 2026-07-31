/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdPieces;

public class StrongholdStructure
extends Structure {
    public static final Codec<StrongholdStructure> f_229936_ = StrongholdStructure.m_226607_(StrongholdStructure::new);

    public StrongholdStructure(Structure.StructureSettings p_229939_) {
        super(p_229939_);
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_229941_) {
        return Optional.of(new Structure.GenerationStub(p_229941_.f_226628_().m_45615_(), p_229944_ -> StrongholdStructure.m_229945_(p_229944_, p_229941_)));
    }

    private static void m_229945_(StructurePiecesBuilder p_229946_, Structure.GenerationContext p_229947_) {
        StrongholdPieces.StartPiece $$3;
        int $$2 = 0;
        do {
            p_229946_.m_192796_();
            p_229947_.f_226626_().m_190068_(p_229947_.f_226627_() + (long)$$2++, p_229947_.f_226628_().f_45578_, p_229947_.f_226628_().f_45579_);
            StrongholdPieces.m_229416_();
            $$3 = new StrongholdPieces.StartPiece(p_229947_.f_226626_(), p_229947_.f_226628_().m_151382_(2), p_229947_.f_226628_().m_151391_(2));
            p_229946_.m_142679_($$3);
            $$3.m_214092_($$3, p_229946_, p_229947_.f_226626_());
            List<StructurePiece> $$4 = $$3.f_229799_;
            while (!$$4.isEmpty()) {
                int $$5 = p_229947_.f_226626_().m_188503_($$4.size());
                StructurePiece $$6 = $$4.remove($$5);
                $$6.m_214092_($$3, p_229946_, p_229947_.f_226626_());
            }
            p_229946_.m_226965_(p_229947_.f_226622_().m_6337_(), p_229947_.f_226622_().m_142062_(), p_229947_.f_226626_(), 10);
        } while (p_229946_.m_192797_() || $$3.f_229798_ == null);
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226875_;
    }
}


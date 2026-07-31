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
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.EndCityPieces;

public class EndCityStructure
extends Structure {
    public static final Codec<EndCityStructure> f_227523_ = EndCityStructure.m_226607_(EndCityStructure::new);

    public EndCityStructure(Structure.StructureSettings p_227526_) {
        super(p_227526_);
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_227528_) {
        Rotation $$1 = Rotation.m_221990_(p_227528_.f_226626_());
        BlockPos $$2 = this.m_226582_(p_227528_, $$1);
        if ($$2.m_123342_() < 60) {
            return Optional.empty();
        }
        return Optional.of(new Structure.GenerationStub($$2, p_227538_ -> this.m_227529_((StructurePiecesBuilder)p_227538_, $$2, $$1, p_227528_)));
    }

    private void m_227529_(StructurePiecesBuilder p_227530_, BlockPos p_227531_, Rotation p_227532_, Structure.GenerationContext p_227533_) {
        ArrayList $$4 = Lists.newArrayList();
        EndCityPieces.m_227444_(p_227533_.f_226625_(), p_227531_, p_227532_, $$4, p_227533_.f_226626_());
        $$4.forEach(p_227530_::m_142679_);
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226864_;
    }
}


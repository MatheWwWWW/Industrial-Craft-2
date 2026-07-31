/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.IglooPieces;

public class IglooStructure
extends Structure {
    public static final Codec<IglooStructure> f_227590_ = IglooStructure.m_226607_(IglooStructure::new);

    public IglooStructure(Structure.StructureSettings p_227593_) {
        super(p_227593_);
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_227595_) {
        return IglooStructure.m_226585_(p_227595_, Heightmap.Types.WORLD_SURFACE_WG, p_227598_ -> this.m_227599_((StructurePiecesBuilder)p_227598_, p_227595_));
    }

    private void m_227599_(StructurePiecesBuilder p_227600_, Structure.GenerationContext p_227601_) {
        ChunkPos $$2 = p_227601_.f_226628_();
        WorldgenRandom $$3 = p_227601_.f_226626_();
        BlockPos $$4 = new BlockPos($$2.m_45604_(), 90, $$2.m_45605_());
        Rotation $$5 = Rotation.m_221990_($$3);
        IglooPieces.m_227548_(p_227601_.f_226625_(), $$4, $$5, p_227600_, $$3);
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226866_;
    }
}


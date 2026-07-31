/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentPieces;

public class OceanMonumentStructure
extends Structure {
    public static final Codec<OceanMonumentStructure> f_228952_ = OceanMonumentStructure.m_226607_(OceanMonumentStructure::new);

    public OceanMonumentStructure(Structure.StructureSettings p_228955_) {
        super(p_228955_);
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_228964_) {
        int $$1 = p_228964_.f_226628_().m_151382_(9);
        int $$2 = p_228964_.f_226628_().m_151391_(9);
        Set<Holder<Biome>> $$3 = p_228964_.f_226623_().m_183399_($$1, p_228964_.f_226622_().m_6337_(), $$2, 29, p_228964_.f_226624_().m_224579_());
        for (Holder<Biome> $$4 : $$3) {
            if ($$4.m_203656_(BiomeTags.f_215800_)) continue;
            return Optional.empty();
        }
        return OceanMonumentStructure.m_226585_(p_228964_, Heightmap.Types.OCEAN_FLOOR_WG, p_228967_ -> OceanMonumentStructure.m_228968_(p_228967_, p_228964_));
    }

    private static StructurePiece m_228960_(ChunkPos p_228961_, WorldgenRandom p_228962_) {
        int $$2 = p_228961_.m_45604_() - 29;
        int $$3 = p_228961_.m_45605_() - 29;
        Direction $$4 = Direction.Plane.HORIZONTAL.m_235690_(p_228962_);
        return new OceanMonumentPieces.MonumentBuilding(p_228962_, $$2, $$3, $$4);
    }

    private static void m_228968_(StructurePiecesBuilder p_228969_, Structure.GenerationContext p_228970_) {
        p_228969_.m_142679_(OceanMonumentStructure.m_228960_(p_228970_.f_226628_(), p_228970_.f_226626_()));
    }

    public static PiecesContainer m_228956_(ChunkPos p_228957_, long p_228958_, PiecesContainer p_228959_) {
        if (p_228959_.m_192748_()) {
            return p_228959_;
        }
        WorldgenRandom $$3 = new WorldgenRandom(new LegacyRandomSource(RandomSupport.m_224599_()));
        $$3.m_190068_(p_228958_, p_228957_.f_45578_, p_228957_.f_45579_);
        StructurePiece $$4 = p_228959_.f_192741_().get(0);
        BoundingBox $$5 = $$4.m_73547_();
        int $$6 = $$5.m_162395_();
        int $$7 = $$5.m_162398_();
        Direction $$8 = Direction.Plane.HORIZONTAL.m_235690_($$3);
        Direction $$9 = Objects.requireNonNullElse($$4.m_73549_(), $$8);
        OceanMonumentPieces.MonumentBuilding $$10 = new OceanMonumentPieces.MonumentBuilding($$3, $$6, $$7, $$9);
        StructurePiecesBuilder $$11 = new StructurePiecesBuilder();
        $$11.m_142679_($$10);
        return $$11.m_192780_();
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226871_;
    }
}


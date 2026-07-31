/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.SwampHutPiece;

public class SwampHutStructure
extends Structure {
    public static final Codec<SwampHutStructure> f_229971_ = SwampHutStructure.m_226607_(SwampHutStructure::new);

    public SwampHutStructure(Structure.StructureSettings p_229974_) {
        super(p_229974_);
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_229976_) {
        return SwampHutStructure.m_226585_(p_229976_, Heightmap.Types.WORLD_SURFACE_WG, p_229979_ -> SwampHutStructure.m_229980_(p_229979_, p_229976_));
    }

    private static void m_229980_(StructurePiecesBuilder p_229981_, Structure.GenerationContext p_229982_) {
        p_229981_.m_142679_(new SwampHutPiece(p_229982_.f_226626_(), p_229982_.f_226628_().m_45604_(), p_229982_.f_226628_().m_45605_()));
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226876_;
    }
}


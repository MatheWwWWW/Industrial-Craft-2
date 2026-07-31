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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.BuriedTreasurePieces;

public class BuriedTreasureStructure
extends Structure {
    public static final Codec<BuriedTreasureStructure> f_227382_ = BuriedTreasureStructure.m_226607_(BuriedTreasureStructure::new);

    public BuriedTreasureStructure(Structure.StructureSettings p_227385_) {
        super(p_227385_);
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_227387_) {
        return BuriedTreasureStructure.m_226585_(p_227387_, Heightmap.Types.OCEAN_FLOOR_WG, p_227390_ -> BuriedTreasureStructure.m_227391_(p_227390_, p_227387_));
    }

    private static void m_227391_(StructurePiecesBuilder p_227392_, Structure.GenerationContext p_227393_) {
        BlockPos $$2 = new BlockPos(p_227393_.f_226628_().m_151382_(9), 90, p_227393_.f_226628_().m_151391_(9));
        p_227392_.m_142679_(new BuriedTreasurePieces.BuriedTreasurePiece($$2));
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226862_;
    }
}


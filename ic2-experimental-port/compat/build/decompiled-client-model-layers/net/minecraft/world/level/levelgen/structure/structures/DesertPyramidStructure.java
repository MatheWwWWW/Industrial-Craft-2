/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidPiece;

public class DesertPyramidStructure
extends SinglePieceStructure {
    public static final Codec<DesertPyramidStructure> f_227415_ = DesertPyramidStructure.m_226607_(DesertPyramidStructure::new);

    public DesertPyramidStructure(Structure.StructureSettings p_227418_) {
        super(DesertPyramidPiece::new, 21, 21, p_227418_);
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226863_;
    }
}


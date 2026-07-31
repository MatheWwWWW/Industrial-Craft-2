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
import net.minecraft.world.level.levelgen.structure.structures.JungleTemplePiece;

public class JungleTempleStructure
extends SinglePieceStructure {
    public static final Codec<JungleTempleStructure> f_227691_ = JungleTempleStructure.m_226607_(JungleTempleStructure::new);

    public JungleTempleStructure(Structure.StructureSettings p_227694_) {
        super(JungleTemplePiece::new, 12, 15, p_227694_);
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226868_;
    }
}


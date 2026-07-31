/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public interface StructurePlacementType<SP extends StructurePlacement> {
    public static final StructurePlacementType<RandomSpreadStructurePlacement> f_205041_ = StructurePlacementType.m_205046_("random_spread", RandomSpreadStructurePlacement.f_204972_);
    public static final StructurePlacementType<ConcentricRingsStructurePlacement> f_205042_ = StructurePlacementType.m_205046_("concentric_rings", ConcentricRingsStructurePlacement.f_204949_);

    public Codec<SP> m_205049_();

    private static <SP extends StructurePlacement> StructurePlacementType<SP> m_205046_(String p_205047_, Codec<SP> p_205048_) {
        return Registry.m_122961_(Registry.f_205930_, p_205047_, () -> p_205048_);
    }
}


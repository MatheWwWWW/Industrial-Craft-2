/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.Products$P4
 *  com.mojang.datafixers.Products$P5
 *  com.mojang.datafixers.Products$P9
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Mu
 */
package net.minecraft.world.level.levelgen.structure.placement;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

public class ConcentricRingsStructurePlacement
extends StructurePlacement {
    public static final Codec<ConcentricRingsStructurePlacement> f_204949_ = RecordCodecBuilder.create(p_204960_ -> ConcentricRingsStructurePlacement.m_226996_((RecordCodecBuilder.Instance<ConcentricRingsStructurePlacement>)p_204960_).apply((Applicative)p_204960_, ConcentricRingsStructurePlacement::new));
    private final int f_204950_;
    private final int f_204951_;
    private final int f_204952_;
    private final HolderSet<Biome> f_226974_;

    private static Products.P9<RecordCodecBuilder.Mu<ConcentricRingsStructurePlacement>, Vec3i, StructurePlacement.FrequencyReductionMethod, Float, Integer, Optional<StructurePlacement.ExclusionZone>, Integer, Integer, Integer, HolderSet<Biome>> m_226996_(RecordCodecBuilder.Instance<ConcentricRingsStructurePlacement> p_226997_) {
        Products.P5<RecordCodecBuilder.Mu<ConcentricRingsStructurePlacement>, Vec3i, StructurePlacement.FrequencyReductionMethod, Float, Integer, Optional<StructurePlacement.ExclusionZone>> $$1 = ConcentricRingsStructurePlacement.m_227041_(p_226997_);
        Products.P4 $$2 = p_226997_.group((App)Codec.intRange((int)0, (int)1023).fieldOf("distance").forGetter(ConcentricRingsStructurePlacement::m_204965_), (App)Codec.intRange((int)0, (int)1023).fieldOf("spread").forGetter(ConcentricRingsStructurePlacement::m_204966_), (App)Codec.intRange((int)1, (int)4095).fieldOf("count").forGetter(ConcentricRingsStructurePlacement::m_204967_), (App)RegistryCodecs.m_206277_(Registry.f_122885_).fieldOf("preferred_biomes").forGetter(ConcentricRingsStructurePlacement::m_226998_));
        return new Products.P9($$1.t1(), $$1.t2(), $$1.t3(), $$1.t4(), $$1.t5(), $$2.t1(), $$2.t2(), $$2.t3(), $$2.t4());
    }

    public ConcentricRingsStructurePlacement(Vec3i p_226981_, StructurePlacement.FrequencyReductionMethod p_226982_, float p_226983_, int p_226984_, Optional<StructurePlacement.ExclusionZone> p_226985_, int p_226986_, int p_226987_, int p_226988_, HolderSet<Biome> p_226989_) {
        super(p_226981_, p_226982_, p_226983_, p_226984_, p_226985_);
        this.f_204950_ = p_226986_;
        this.f_204951_ = p_226987_;
        this.f_204952_ = p_226988_;
        this.f_226974_ = p_226989_;
    }

    public ConcentricRingsStructurePlacement(int p_226976_, int p_226977_, int p_226978_, HolderSet<Biome> p_226979_) {
        this(Vec3i.f_123288_, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0f, 0, Optional.empty(), p_226976_, p_226977_, p_226978_, p_226979_);
    }

    public int m_204965_() {
        return this.f_204950_;
    }

    public int m_204966_() {
        return this.f_204951_;
    }

    public int m_204967_() {
        return this.f_204952_;
    }

    public HolderSet<Biome> m_226998_() {
        return this.f_226974_;
    }

    @Override
    protected boolean m_214090_(ChunkGenerator p_226991_, RandomState p_226992_, long p_226993_, int p_226994_, int p_226995_) {
        List<ChunkPos> $$5 = p_226991_.m_223119_(this, p_226992_);
        if ($$5 == null) {
            return false;
        }
        return $$5.contains(new ChunkPos(p_226994_, p_226995_));
    }

    @Override
    public StructurePlacementType<?> m_203443_() {
        return StructurePlacementType.f_205042_;
    }
}


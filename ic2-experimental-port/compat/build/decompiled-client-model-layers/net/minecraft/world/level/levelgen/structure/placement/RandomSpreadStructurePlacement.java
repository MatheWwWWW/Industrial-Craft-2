/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.structure.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

public class RandomSpreadStructurePlacement
extends StructurePlacement {
    public static final Codec<RandomSpreadStructurePlacement> f_204972_ = RecordCodecBuilder.mapCodec(p_204996_ -> RandomSpreadStructurePlacement.m_227041_(p_204996_).and(p_204996_.group((App)Codec.intRange((int)0, (int)4096).fieldOf("spacing").forGetter(RandomSpreadStructurePlacement::m_205003_), (App)Codec.intRange((int)0, (int)4096).fieldOf("separation").forGetter(RandomSpreadStructurePlacement::m_205004_), (App)RandomSpreadType.f_205014_.optionalFieldOf("spread_type", (Object)RandomSpreadType.LINEAR).forGetter(RandomSpreadStructurePlacement::m_205005_))).apply((Applicative)p_204996_, RandomSpreadStructurePlacement::new)).flatXmap(p_205002_ -> {
        if (p_205002_.f_204973_ <= p_205002_.f_204974_) {
            return DataResult.error((String)"Spacing has to be larger than separation");
        }
        return DataResult.success((Object)p_205002_);
    }, DataResult::success).codec();
    private final int f_204973_;
    private final int f_204974_;
    private final RandomSpreadType f_204975_;

    public RandomSpreadStructurePlacement(Vec3i p_227000_, StructurePlacement.FrequencyReductionMethod p_227001_, float p_227002_, int p_227003_, Optional<StructurePlacement.ExclusionZone> p_227004_, int p_227005_, int p_227006_, RandomSpreadType p_227007_) {
        super(p_227000_, p_227001_, p_227002_, p_227003_, p_227004_);
        this.f_204973_ = p_227005_;
        this.f_204974_ = p_227006_;
        this.f_204975_ = p_227007_;
    }

    public RandomSpreadStructurePlacement(int p_204980_, int p_204981_, RandomSpreadType p_204982_, int p_204983_) {
        this(Vec3i.f_123288_, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0f, p_204983_, Optional.empty(), p_204980_, p_204981_, p_204982_);
    }

    public int m_205003_() {
        return this.f_204973_;
    }

    public int m_205004_() {
        return this.f_204974_;
    }

    public RandomSpreadType m_205005_() {
        return this.f_204975_;
    }

    public ChunkPos m_227008_(long p_227009_, int p_227010_, int p_227011_) {
        int $$3 = Math.floorDiv(p_227010_, this.f_204973_);
        int $$4 = Math.floorDiv(p_227011_, this.f_204973_);
        WorldgenRandom $$5 = new WorldgenRandom(new LegacyRandomSource(0L));
        $$5.m_190058_(p_227009_, $$3, $$4, this.m_227075_());
        int $$6 = this.f_204973_ - this.f_204974_;
        int $$7 = this.f_204975_.m_227018_($$5, $$6);
        int $$8 = this.f_204975_.m_227018_($$5, $$6);
        return new ChunkPos($$3 * this.f_204973_ + $$7, $$4 * this.f_204973_ + $$8);
    }

    @Override
    protected boolean m_214090_(ChunkGenerator p_227013_, RandomState p_227014_, long p_227015_, int p_227016_, int p_227017_) {
        ChunkPos $$5 = this.m_227008_(p_227015_, p_227016_, p_227017_);
        return $$5.f_45578_ == p_227016_ && $$5.f_45579_ == p_227017_;
    }

    @Override
    public StructurePlacementType<?> m_203443_() {
        return StructurePlacementType.f_205041_;
    }
}


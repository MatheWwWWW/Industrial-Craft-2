/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;

public class TheEndBiomeSource
extends BiomeSource {
    public static final Codec<TheEndBiomeSource> f_48617_ = RecordCodecBuilder.create(p_220686_ -> p_220686_.group((App)RegistryOps.m_206832_(Registry.f_122885_).forGetter(p_151890_ -> null)).apply((Applicative)p_220686_, p_220686_.stable(TheEndBiomeSource::new)));
    private final Holder<Biome> f_48621_;
    private final Holder<Biome> f_48622_;
    private final Holder<Biome> f_48623_;
    private final Holder<Biome> f_48624_;
    private final Holder<Biome> f_48625_;

    public TheEndBiomeSource(Registry<Biome> p_220684_) {
        this(p_220684_.m_214121_(Biomes.f_48210_), p_220684_.m_214121_(Biomes.f_48164_), p_220684_.m_214121_(Biomes.f_48163_), p_220684_.m_214121_(Biomes.f_48162_), p_220684_.m_214121_(Biomes.f_48165_));
    }

    private TheEndBiomeSource(Holder<Biome> p_220678_, Holder<Biome> p_220679_, Holder<Biome> p_220680_, Holder<Biome> p_220681_, Holder<Biome> p_220682_) {
        super((List<Holder<Biome>>)ImmutableList.of(p_220678_, p_220679_, p_220680_, p_220681_, p_220682_));
        this.f_48621_ = p_220678_;
        this.f_48622_ = p_220679_;
        this.f_48623_ = p_220680_;
        this.f_48624_ = p_220681_;
        this.f_48625_ = p_220682_;
    }

    @Override
    protected Codec<? extends BiomeSource> m_5820_() {
        return f_48617_;
    }

    @Override
    public Holder<Biome> m_203407_(int p_204292_, int p_204293_, int p_204294_, Climate.Sampler p_204295_) {
        int $$8;
        int $$4 = QuartPos.m_175402_(p_204292_);
        int $$5 = QuartPos.m_175402_(p_204293_);
        int $$6 = QuartPos.m_175402_(p_204294_);
        int $$7 = SectionPos.m_123171_($$4);
        if ((long)$$7 * (long)$$7 + (long)($$8 = SectionPos.m_123171_($$6)) * (long)$$8 <= 4096L) {
            return this.f_48621_;
        }
        int $$9 = (SectionPos.m_123171_($$4) * 2 + 1) * 8;
        int $$10 = (SectionPos.m_123171_($$6) * 2 + 1) * 8;
        double $$11 = p_204295_.f_207848_().m_207386_(new DensityFunction.SinglePointContext($$9, $$5, $$10));
        if ($$11 > 0.25) {
            return this.f_48622_;
        }
        if ($$11 >= -0.0625) {
            return this.f_48623_;
        }
        if ($$11 < -0.21875) {
            return this.f_48624_;
        }
        return this.f_48625_;
    }
}


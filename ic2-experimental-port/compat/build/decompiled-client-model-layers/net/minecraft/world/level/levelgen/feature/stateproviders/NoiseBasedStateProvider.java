/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.Products$P3
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Mu
 */
package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public abstract class NoiseBasedStateProvider
extends BlockStateProvider {
    protected final long f_191417_;
    protected final NormalNoise.NoiseParameters f_191418_;
    protected final float f_191419_;
    protected final NormalNoise f_191420_;

    protected static <P extends NoiseBasedStateProvider> Products.P3<RecordCodecBuilder.Mu<P>, Long, NormalNoise.NoiseParameters, Float> m_191425_(RecordCodecBuilder.Instance<P> p_191426_) {
        return p_191426_.group((App)Codec.LONG.fieldOf("seed").forGetter(p_191435_ -> p_191435_.f_191417_), (App)NormalNoise.NoiseParameters.f_192851_.fieldOf("noise").forGetter(p_191433_ -> p_191433_.f_191418_), (App)ExtraCodecs.f_184349_.fieldOf("scale").forGetter(p_191428_ -> Float.valueOf(p_191428_.f_191419_)));
    }

    protected NoiseBasedStateProvider(long p_191422_, NormalNoise.NoiseParameters p_191423_, float p_191424_) {
        this.f_191417_ = p_191422_;
        this.f_191418_ = p_191423_;
        this.f_191419_ = p_191424_;
        this.f_191420_ = NormalNoise.m_230511_(new WorldgenRandom(new LegacyRandomSource(p_191422_)), p_191423_);
    }

    protected double m_191429_(BlockPos p_191430_, double p_191431_) {
        return this.f_191420_.m_75380_((double)p_191430_.m_123341_() * p_191431_, (double)p_191430_.m_123342_() * p_191431_, (double)p_191430_.m_123343_() * p_191431_);
    }
}


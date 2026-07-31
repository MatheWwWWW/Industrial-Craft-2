/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class DualNoiseProvider
extends NoiseProvider {
    public static final Codec<DualNoiseProvider> f_191389_ = RecordCodecBuilder.create(p_191414_ -> p_191414_.group((App)InclusiveRange.m_184574_(Codec.INT, 1, 64).fieldOf("variety").forGetter(p_191416_ -> p_191416_.f_191390_), (App)NormalNoise.NoiseParameters.f_192851_.fieldOf("slow_noise").forGetter(p_191412_ -> p_191412_.f_191391_), (App)ExtraCodecs.f_184349_.fieldOf("slow_scale").forGetter(p_191405_ -> Float.valueOf(p_191405_.f_191392_))).and(DualNoiseProvider.m_191459_(p_191414_)).apply((Applicative)p_191414_, DualNoiseProvider::new));
    private final InclusiveRange<Integer> f_191390_;
    private final NormalNoise.NoiseParameters f_191391_;
    private final float f_191392_;
    private final NormalNoise f_191393_;

    public DualNoiseProvider(InclusiveRange<Integer> p_191396_, NormalNoise.NoiseParameters p_191397_, float p_191398_, long p_191399_, NormalNoise.NoiseParameters p_191400_, float p_191401_, List<BlockState> p_191402_) {
        super(p_191399_, p_191400_, p_191401_, p_191402_);
        this.f_191390_ = p_191396_;
        this.f_191391_ = p_191397_;
        this.f_191392_ = p_191398_;
        this.f_191393_ = NormalNoise.m_230511_(new WorldgenRandom(new LegacyRandomSource(p_191399_)), p_191397_);
    }

    @Override
    protected BlockStateProviderType<?> m_5923_() {
        return BlockStateProviderType.f_191388_;
    }

    @Override
    public BlockState m_213972_(RandomSource p_225910_, BlockPos p_225911_) {
        double $$2 = this.m_191406_(p_225911_);
        int $$3 = (int)Mth.m_144851_($$2, -1.0, 1.0, this.f_191390_.f_184563_().intValue(), this.f_191390_.f_184564_() + 1);
        ArrayList $$4 = Lists.newArrayListWithCapacity((int)$$3);
        for (int $$5 = 0; $$5 < $$3; ++$$5) {
            $$4.add(this.m_191449_(this.f_191439_, this.m_191406_(p_225911_.m_7918_($$5 * 54545, 0, $$5 * 34234))));
        }
        return this.m_191452_($$4, p_225911_, this.f_191419_);
    }

    protected double m_191406_(BlockPos p_191407_) {
        return this.f_191393_.m_75380_((float)p_191407_.m_123341_() * this.f_191392_, (float)p_191407_.m_123342_() * this.f_191392_, (float)p_191407_.m_123343_() * this.f_191392_);
    }
}


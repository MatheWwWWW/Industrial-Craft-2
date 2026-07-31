/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.Products$P4
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Mu
 */
package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseBasedStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class NoiseProvider
extends NoiseBasedStateProvider {
    public static final Codec<NoiseProvider> f_191438_ = RecordCodecBuilder.create(p_191462_ -> NoiseProvider.m_191459_(p_191462_).apply((Applicative)p_191462_, NoiseProvider::new));
    protected final List<BlockState> f_191439_;

    protected static <P extends NoiseProvider> Products.P4<RecordCodecBuilder.Mu<P>, Long, NormalNoise.NoiseParameters, Float, List<BlockState>> m_191459_(RecordCodecBuilder.Instance<P> p_191460_) {
        return NoiseProvider.m_191425_(p_191460_).and((App)Codec.list(BlockState.f_61039_).fieldOf("states").forGetter(p_191448_ -> p_191448_.f_191439_));
    }

    public NoiseProvider(long p_191442_, NormalNoise.NoiseParameters p_191443_, float p_191444_, List<BlockState> p_191445_) {
        super(p_191442_, p_191443_, p_191444_);
        this.f_191439_ = p_191445_;
    }

    @Override
    protected BlockStateProviderType<?> m_5923_() {
        return BlockStateProviderType.f_191387_;
    }

    @Override
    public BlockState m_213972_(RandomSource p_225913_, BlockPos p_225914_) {
        return this.m_191452_(this.f_191439_, p_225914_, this.f_191419_);
    }

    protected BlockState m_191452_(List<BlockState> p_191453_, BlockPos p_191454_, double p_191455_) {
        double $$3 = this.m_191429_(p_191454_, p_191455_);
        return this.m_191449_(p_191453_, $$3);
    }

    protected BlockState m_191449_(List<BlockState> p_191450_, double p_191451_) {
        double $$2 = Mth.m_14008_((1.0 + p_191451_) / 2.0, 0.0, 0.9999);
        return p_191450_.get((int)($$2 * (double)p_191450_.size()));
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseBasedStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class NoiseThresholdProvider
extends NoiseBasedStateProvider {
    public static final Codec<NoiseThresholdProvider> f_191463_ = RecordCodecBuilder.create(p_191486_ -> NoiseThresholdProvider.m_191425_(p_191486_).and(p_191486_.group((App)Codec.floatRange((float)-1.0f, (float)1.0f).fieldOf("threshold").forGetter(p_191494_ -> Float.valueOf(p_191494_.f_191464_)), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("high_chance").forGetter(p_191492_ -> Float.valueOf(p_191492_.f_191465_)), (App)BlockState.f_61039_.fieldOf("default_state").forGetter(p_191490_ -> p_191490_.f_191466_), (App)Codec.list(BlockState.f_61039_).fieldOf("low_states").forGetter(p_191488_ -> p_191488_.f_191467_), (App)Codec.list(BlockState.f_61039_).fieldOf("high_states").forGetter(p_191481_ -> p_191481_.f_191468_))).apply((Applicative)p_191486_, NoiseThresholdProvider::new));
    private final float f_191464_;
    private final float f_191465_;
    private final BlockState f_191466_;
    private final List<BlockState> f_191467_;
    private final List<BlockState> f_191468_;

    public NoiseThresholdProvider(long p_191471_, NormalNoise.NoiseParameters p_191472_, float p_191473_, float p_191474_, float p_191475_, BlockState p_191476_, List<BlockState> p_191477_, List<BlockState> p_191478_) {
        super(p_191471_, p_191472_, p_191473_);
        this.f_191464_ = p_191474_;
        this.f_191465_ = p_191475_;
        this.f_191466_ = p_191476_;
        this.f_191467_ = p_191477_;
        this.f_191468_ = p_191478_;
    }

    @Override
    protected BlockStateProviderType<?> m_5923_() {
        return BlockStateProviderType.f_191386_;
    }

    @Override
    public BlockState m_213972_(RandomSource p_225916_, BlockPos p_225917_) {
        double $$2 = this.m_191429_(p_225917_, this.f_191419_);
        if ($$2 < (double)this.f_191464_) {
            return Util.m_214621_(this.f_191467_, p_225916_);
        }
        if (p_225916_.m_188501_() < this.f_191465_) {
            return Util.m_214621_(this.f_191468_, p_225916_);
        }
        return this.f_191466_;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;

public class WeightedListHeight
extends HeightProvider {
    public static final Codec<WeightedListHeight> f_191532_ = RecordCodecBuilder.create(p_191539_ -> p_191539_.group((App)SimpleWeightedRandomList.m_146264_(HeightProvider.f_161970_).fieldOf("distribution").forGetter(p_191541_ -> p_191541_.f_191533_)).apply((Applicative)p_191539_, WeightedListHeight::new));
    private final SimpleWeightedRandomList<HeightProvider> f_191533_;

    public WeightedListHeight(SimpleWeightedRandomList<HeightProvider> p_191536_) {
        this.f_191533_ = p_191536_;
    }

    @Override
    public int m_213859_(RandomSource p_226314_, WorldGenerationContext p_226315_) {
        return this.f_191533_.m_216820_(p_226314_).orElseThrow(IllegalStateException::new).m_213859_(p_226314_, p_226315_);
    }

    @Override
    public HeightProviderType<?> m_142002_() {
        return HeightProviderType.f_191531_;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.util.valueproviders;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;

public class WeightedListInt
extends IntProvider {
    public static final Codec<WeightedListInt> f_185909_ = RecordCodecBuilder.create(p_185920_ -> p_185920_.group((App)SimpleWeightedRandomList.m_146264_(IntProvider.f_146531_).fieldOf("distribution").forGetter(p_185918_ -> p_185918_.f_185910_)).apply((Applicative)p_185920_, WeightedListInt::new));
    private final SimpleWeightedRandomList<IntProvider> f_185910_;
    private final int f_185911_;
    private final int f_185912_;

    public WeightedListInt(SimpleWeightedRandomList<IntProvider> p_185915_) {
        this.f_185910_ = p_185915_;
        List $$1 = p_185915_.m_146338_();
        int $$2 = Integer.MAX_VALUE;
        int $$3 = Integer.MIN_VALUE;
        for (WeightedEntry.Wrapper $$4 : $$1) {
            int $$5 = ((IntProvider)$$4.m_146310_()).m_142739_();
            int $$6 = ((IntProvider)$$4.m_146310_()).m_142737_();
            $$2 = Math.min($$2, $$5);
            $$3 = Math.max($$3, $$6);
        }
        this.f_185911_ = $$2;
        this.f_185912_ = $$3;
    }

    @Override
    public int m_214085_(RandomSource p_216870_) {
        return this.f_185910_.m_216820_(p_216870_).orElseThrow(IllegalStateException::new).m_214085_(p_216870_);
    }

    @Override
    public int m_142739_() {
        return this.f_185911_;
    }

    @Override
    public int m_142737_() {
        return this.f_185912_;
    }

    @Override
    public IntProviderType<?> m_141948_() {
        return IntProviderType.f_185907_;
    }
}


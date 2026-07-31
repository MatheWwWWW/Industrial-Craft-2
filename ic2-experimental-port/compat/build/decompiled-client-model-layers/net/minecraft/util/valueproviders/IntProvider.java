/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 */
package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProviderType;

public abstract class IntProvider {
    private static final Codec<Either<Integer, IntProvider>> f_146530_ = Codec.either((Codec)Codec.INT, (Codec)Registry.f_175417_.m_194605_().dispatch(IntProvider::m_141948_, IntProviderType::m_146560_));
    public static final Codec<IntProvider> f_146531_ = f_146530_.xmap(p_146543_ -> (IntProvider)p_146543_.map(ConstantInt::m_146483_, p_146549_ -> p_146549_), p_146541_ -> p_146541_.m_141948_() == IntProviderType.f_146550_ ? Either.left((Object)((ConstantInt)p_146541_).m_146499_()) : Either.right((Object)p_146541_));
    public static final Codec<IntProvider> f_146532_ = IntProvider.m_146545_(0, Integer.MAX_VALUE);
    public static final Codec<IntProvider> f_146533_ = IntProvider.m_146545_(1, Integer.MAX_VALUE);

    public static Codec<IntProvider> m_146545_(int p_146546_, int p_146547_) {
        Function<IntProvider, DataResult> $$2 = p_146539_ -> {
            if (p_146539_.m_142739_() < p_146546_) {
                return DataResult.error((String)("Value provider too low: " + p_146546_ + " [" + p_146539_.m_142739_() + "-" + p_146539_.m_142737_() + "]"));
            }
            if (p_146539_.m_142737_() > p_146547_) {
                return DataResult.error((String)("Value provider too high: " + p_146547_ + " [" + p_146539_.m_142739_() + "-" + p_146539_.m_142737_() + "]"));
            }
            return DataResult.success((Object)p_146539_);
        };
        return f_146531_.flatXmap($$2, $$2);
    }

    public abstract int m_214085_(RandomSource var1);

    public abstract int m_142739_();

    public abstract int m_142737_();

    public abstract IntProviderType<?> m_141948_();
}


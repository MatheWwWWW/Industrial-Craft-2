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
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.FloatProviderType;
import net.minecraft.util.valueproviders.SampledFloat;

public abstract class FloatProvider
implements SampledFloat {
    private static final Codec<Either<Float, FloatProvider>> f_146501_ = Codec.either((Codec)Codec.FLOAT, (Codec)Registry.f_175415_.m_194605_().dispatch(FloatProvider::m_141961_, FloatProviderType::m_146529_));
    public static final Codec<FloatProvider> f_146502_ = f_146501_.xmap(p_146515_ -> (FloatProvider)p_146515_.map(ConstantFloat::m_146458_, p_146518_ -> p_146518_), p_146513_ -> p_146513_.m_141961_() == FloatProviderType.f_146519_ ? Either.left((Object)Float.valueOf(((ConstantFloat)p_146513_).m_146474_())) : Either.right((Object)p_146513_));

    public static Codec<FloatProvider> m_146505_(float p_146506_, float p_146507_) {
        Function<FloatProvider, DataResult> $$2 = p_146511_ -> {
            if (p_146511_.m_142735_() < p_146506_) {
                return DataResult.error((String)("Value provider too low: " + p_146506_ + " [" + p_146511_.m_142735_() + "-" + p_146511_.m_142734_() + "]"));
            }
            if (p_146511_.m_142734_() > p_146507_) {
                return DataResult.error((String)("Value provider too high: " + p_146507_ + " [" + p_146511_.m_142735_() + "-" + p_146511_.m_142734_() + "]"));
            }
            return DataResult.success((Object)p_146511_);
        };
        return f_146502_.flatXmap($$2, $$2);
    }

    public abstract float m_142735_();

    public abstract float m_142734_();

    public abstract FloatProviderType<?> m_141961_();
}


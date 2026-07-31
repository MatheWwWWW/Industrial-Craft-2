/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.floats.Float2FloatFunction
 */
package net.minecraft.util;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.util.function.Function;

public interface ToFloatFunction<C> {
    public static final ToFloatFunction<Float> f_216471_ = ToFloatFunction.m_216475_(p_216474_ -> p_216474_);

    public float m_183321_(C var1);

    public float m_213850_();

    public float m_213849_();

    public static ToFloatFunction<Float> m_216475_(final Float2FloatFunction p_216476_) {
        return new ToFloatFunction<Float>(){

            @Override
            public float m_183321_(Float p_216483_) {
                return ((Float)p_216476_.apply((Object)p_216483_)).floatValue();
            }

            @Override
            public float m_213850_() {
                return Float.NEGATIVE_INFINITY;
            }

            @Override
            public float m_213849_() {
                return Float.POSITIVE_INFINITY;
            }
        };
    }

    default public <C2> ToFloatFunction<C2> m_216477_(final Function<C2, C> p_216478_) {
        final ToFloatFunction $$1 = this;
        return new ToFloatFunction<C2>(){

            @Override
            public float m_183321_(C2 p_216496_) {
                return $$1.m_183321_(p_216478_.apply(p_216496_));
            }

            @Override
            public float m_213850_() {
                return $$1.m_213850_();
            }

            @Override
            public float m_213849_() {
                return $$1.m_213849_();
            }
        };
    }
}


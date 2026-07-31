/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.animation;

import com.mojang.math.Vector3f;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public record AnimationChannel(Target f_232211_, Keyframe[] f_232212_) {
    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{AnimationChannel.class, "target;keyframes", "f_232211_", "f_232212_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{AnimationChannel.class, "target;keyframes", "f_232211_", "f_232212_"}, this);
    }

    @Override
    public final boolean equals(Object p_232219_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{AnimationChannel.class, "target;keyframes", "f_232211_", "f_232212_"}, this, p_232219_);
    }

    public static interface Target {
        public void m_232247_(ModelPart var1, Vector3f var2);
    }

    public static class Interpolations {
        public static final Interpolation f_232229_ = (p_232241_, p_232242_, p_232243_, p_232244_, p_232245_, p_232246_) -> {
            Vector3f $$6 = p_232243_[p_232244_].f_232284_();
            Vector3f $$7 = p_232243_[p_232245_].f_232284_();
            p_232241_.m_122245_(Mth.m_14179_(p_232242_, $$6.m_122239_(), $$7.m_122239_()) * p_232246_, Mth.m_14179_(p_232242_, $$6.m_122260_(), $$7.m_122260_()) * p_232246_, Mth.m_14179_(p_232242_, $$6.m_122269_(), $$7.m_122269_()) * p_232246_);
            return p_232241_;
        };
        public static final Interpolation f_232230_ = (p_232234_, p_232235_, p_232236_, p_232237_, p_232238_, p_232239_) -> {
            Vector3f $$6 = p_232236_[Math.max(0, p_232237_ - 1)].f_232284_();
            Vector3f $$7 = p_232236_[p_232237_].f_232284_();
            Vector3f $$8 = p_232236_[p_232238_].f_232284_();
            Vector3f $$9 = p_232236_[Math.min(p_232236_.length - 1, p_232238_ + 1)].f_232284_();
            p_232234_.m_122245_(Mth.m_216244_(p_232235_, $$6.m_122239_(), $$7.m_122239_(), $$8.m_122239_(), $$9.m_122239_()) * p_232239_, Mth.m_216244_(p_232235_, $$6.m_122260_(), $$7.m_122260_(), $$8.m_122260_(), $$9.m_122260_()) * p_232239_, Mth.m_216244_(p_232235_, $$6.m_122269_(), $$7.m_122269_(), $$8.m_122269_(), $$9.m_122269_()) * p_232239_);
            return p_232234_;
        };
    }

    public static class Targets {
        public static final Target f_232250_ = ModelPart::m_233564_;
        public static final Target f_232251_ = ModelPart::m_233567_;
        public static final Target f_232252_ = ModelPart::m_233570_;
    }

    public static interface Interpolation {
        public Vector3f m_232222_(Vector3f var1, float var2, Keyframe[] var3, int var4, int var5, float var6);
    }
}


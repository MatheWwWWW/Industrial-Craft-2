/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.apache.commons.compress.utils.Lists
 */
package net.minecraft.client.animation;

import com.google.common.collect.Maps;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Map;
import net.minecraft.client.animation.AnimationChannel;
import org.apache.commons.compress.utils.Lists;

public record AnimationDefinition(float f_232255_, boolean f_232256_, Map<String, List<AnimationChannel>> f_232257_) {
    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{AnimationDefinition.class, "lengthInSeconds;looping;boneAnimations", "f_232255_", "f_232256_", "f_232257_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{AnimationDefinition.class, "lengthInSeconds;looping;boneAnimations", "f_232255_", "f_232256_", "f_232257_"}, this);
    }

    @Override
    public final boolean equals(Object p_232266_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{AnimationDefinition.class, "lengthInSeconds;looping;boneAnimations", "f_232255_", "f_232256_", "f_232257_"}, this, p_232266_);
    }

    public static class Builder {
        private final float f_232269_;
        private final Map<String, List<AnimationChannel>> f_232270_ = Maps.newHashMap();
        private boolean f_232271_;

        public static Builder m_232275_(float p_232276_) {
            return new Builder(p_232276_);
        }

        private Builder(float p_232273_) {
            this.f_232269_ = p_232273_;
        }

        public Builder m_232274_() {
            this.f_232271_ = true;
            return this;
        }

        public Builder m_232279_(String p_232280_, AnimationChannel p_232281_) {
            this.f_232270_.computeIfAbsent(p_232280_, p_232278_ -> Lists.newArrayList()).add(p_232281_);
            return this;
        }

        public AnimationDefinition m_232282_() {
            return new AnimationDefinition(this.f_232269_, this.f_232271_, this.f_232270_);
        }
    }
}


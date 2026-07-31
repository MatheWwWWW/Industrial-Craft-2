/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.animation;

import com.mojang.math.Vector3f;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.animation.AnimationChannel;

public record Keyframe(float f_232283_, Vector3f f_232284_, AnimationChannel.Interpolation f_232285_) {
    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{Keyframe.class, "timestamp;target;interpolation", "f_232283_", "f_232284_", "f_232285_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Keyframe.class, "timestamp;target;interpolation", "f_232283_", "f_232284_", "f_232285_"}, this);
    }

    @Override
    public final boolean equals(Object p_232294_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Keyframe.class, "timestamp;target;interpolation", "f_232283_", "f_232284_", "f_232285_"}, this, p_232294_);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 */
package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.concurrent.atomic.AtomicLong;

public final class RandomSupport {
    public static final long f_189323_ = -7046029254386353131L;
    public static final long f_189324_ = 7640891576956012809L;
    private static final AtomicLong f_189325_ = new AtomicLong(8682522807148012L);

    @VisibleForTesting
    public static long m_189329_(long p_189330_) {
        p_189330_ = (p_189330_ ^ p_189330_ >>> 30) * -4658895280553007687L;
        p_189330_ = (p_189330_ ^ p_189330_ >>> 27) * -7723592293110705685L;
        return p_189330_ ^ p_189330_ >>> 31;
    }

    public static Seed128bit m_189331_(long p_189332_) {
        long $$1 = p_189332_ ^ 0x6A09E667F3BCC909L;
        long $$2 = $$1 + -7046029254386353131L;
        return new Seed128bit(RandomSupport.m_189329_($$1), RandomSupport.m_189329_($$2));
    }

    public static long m_224599_() {
        return f_189325_.updateAndGet(p_224601_ -> p_224601_ * 1181783497276652981L) ^ System.nanoTime();
    }

    public record Seed128bit(long f_189335_, long f_189336_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Seed128bit.class, "seedLo;seedHi", "f_189335_", "f_189336_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Seed128bit.class, "seedLo;seedHi", "f_189335_", "f_189336_"}, this);
        }

        @Override
        public final boolean equals(Object p_189343_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Seed128bit.class, "seedLo;seedHi", "f_189335_", "f_189336_"}, this, p_189343_);
        }
    }
}


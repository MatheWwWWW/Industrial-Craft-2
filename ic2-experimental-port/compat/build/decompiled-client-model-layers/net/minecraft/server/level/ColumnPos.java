/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.level;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;

public record ColumnPos(int f_140723_, int f_140724_) {
    private static final long f_143191_ = 32L;
    private static final long f_143192_ = 0xFFFFFFFFL;

    public ChunkPos m_143196_() {
        return new ChunkPos(SectionPos.m_123171_(this.f_140723_), SectionPos.m_123171_(this.f_140724_));
    }

    public long m_143200_() {
        return ColumnPos.m_143197_(this.f_140723_, this.f_140724_);
    }

    public static long m_143197_(int p_143198_, int p_143199_) {
        return (long)p_143198_ & 0xFFFFFFFFL | ((long)p_143199_ & 0xFFFFFFFFL) << 32;
    }

    public static int m_214969_(long p_214970_) {
        return (int)(p_214970_ & 0xFFFFFFFFL);
    }

    public static int m_214971_(long p_214972_) {
        return (int)(p_214972_ >>> 32 & 0xFFFFFFFFL);
    }

    @Override
    public String toString() {
        return "[" + this.f_140723_ + ", " + this.f_140724_ + "]";
    }

    @Override
    public int hashCode() {
        return ChunkPos.m_220343_(this.f_140723_, this.f_140724_);
    }

    @Override
    public final boolean equals(Object p_140731_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ColumnPos.class, "x;z", "f_140723_", "f_140724_"}, this, p_140731_);
    }
}


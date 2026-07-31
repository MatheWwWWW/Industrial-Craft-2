/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level;

import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.dimension.DimensionType;

public interface LevelTimeAccess
extends LevelReader {
    public long m_8044_();

    default public float m_46940_() {
        return DimensionType.f_63844_[this.m_6042_().m_63936_(this.m_8044_())];
    }

    default public float m_46942_(float p_46943_) {
        return this.m_6042_().m_63904_(this.m_8044_());
    }

    default public int m_46941_() {
        return this.m_6042_().m_63936_(this.m_8044_());
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt;

import net.minecraft.nbt.Tag;

public abstract class NumericTag
implements Tag {
    protected NumericTag() {
    }

    public abstract long m_7046_();

    public abstract int m_7047_();

    public abstract short m_7053_();

    public abstract byte m_7063_();

    public abstract double m_7061_();

    public abstract float m_7057_();

    public abstract Number m_8103_();

    @Override
    public String toString() {
        return this.m_7916_();
    }
}


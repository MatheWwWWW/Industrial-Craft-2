/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.core;

import javax.annotation.Nullable;

public interface IdMap<T>
extends Iterable<T> {
    public static final int f_194530_ = -1;

    public int m_7447_(T var1);

    @Nullable
    public T m_7942_(int var1);

    default public T m_200957_(int p_200958_) {
        T $$1 = this.m_7942_(p_200958_);
        if ($$1 == null) {
            throw new IllegalArgumentException("No value with id " + p_200958_);
        }
        return $$1;
    }

    public int m_13562_();
}


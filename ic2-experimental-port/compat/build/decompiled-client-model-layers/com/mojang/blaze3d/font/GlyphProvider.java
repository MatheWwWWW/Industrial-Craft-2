/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  javax.annotation.Nullable
 */
package com.mojang.blaze3d.font;

import com.mojang.blaze3d.font.GlyphInfo;
import it.unimi.dsi.fastutil.ints.IntSet;
import javax.annotation.Nullable;

public interface GlyphProvider
extends AutoCloseable {
    @Override
    default public void close() {
    }

    @Nullable
    default public GlyphInfo m_214022_(int p_231091_) {
        return null;
    }

    public IntSet m_6990_();
}


/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.font;

import com.mojang.blaze3d.font.SheetGlyphInfo;
import java.util.function.Function;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.EmptyGlyph;

public interface GlyphInfo {
    public float m_7403_();

    default public float m_83827_(boolean p_83828_) {
        return this.m_7403_() + (p_83828_ ? this.m_5619_() : 0.0f);
    }

    default public float m_5619_() {
        return 1.0f;
    }

    default public float m_5645_() {
        return 1.0f;
    }

    public BakedGlyph m_213604_(Function<SheetGlyphInfo, BakedGlyph> var1);

    public static interface SpaceGlyphInfo
    extends GlyphInfo {
        @Override
        default public BakedGlyph m_213604_(Function<SheetGlyphInfo, BakedGlyph> p_231090_) {
            return EmptyGlyph.f_232594_;
        }
    }
}


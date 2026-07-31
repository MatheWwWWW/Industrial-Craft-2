/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.font.glyphs;

import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.SheetGlyphInfo;
import com.mojang.blaze3d.platform.NativeImage;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;

public final class SpecialGlyphs
extends Enum<SpecialGlyphs>
implements GlyphInfo {
    public static final /* enum */ SpecialGlyphs WHITE = new SpecialGlyphs(() -> SpecialGlyphs.m_232608_(5, 8, (p_232613_, p_232614_) -> -1));
    public static final /* enum */ SpecialGlyphs MISSING = new SpecialGlyphs(() -> {
        int $$0 = 5;
        int $$1 = 8;
        return SpecialGlyphs.m_232608_(5, 8, (p_232606_, p_232607_) -> {
            boolean $$2 = p_232606_ == 0 || p_232606_ + 1 == 5 || p_232607_ == 0 || p_232607_ + 1 == 8;
            return $$2 ? -1 : 0;
        });
    });
    final NativeImage f_232598_;
    private static final /* synthetic */ SpecialGlyphs[] $VALUES;

    public static SpecialGlyphs[] values() {
        return (SpecialGlyphs[])$VALUES.clone();
    }

    public static SpecialGlyphs valueOf(String p_232622_) {
        return Enum.valueOf(SpecialGlyphs.class, p_232622_);
    }

    private static NativeImage m_232608_(int p_232609_, int p_232610_, PixelProvider p_232611_) {
        NativeImage $$3 = new NativeImage(NativeImage.Format.RGBA, p_232609_, p_232610_, false);
        for (int $$4 = 0; $$4 < p_232610_; ++$$4) {
            for (int $$5 = 0; $$5 < p_232609_; ++$$5) {
                $$3.m_84988_($$5, $$4, p_232611_.m_232634_($$5, $$4));
            }
        }
        $$3.m_85123_();
        return $$3;
    }

    private SpecialGlyphs(Supplier<NativeImage> p_232604_) {
        this.f_232598_ = p_232604_.get();
    }

    @Override
    public float m_7403_() {
        return this.f_232598_.m_84982_() + 1;
    }

    @Override
    public BakedGlyph m_213604_(Function<SheetGlyphInfo, BakedGlyph> p_232616_) {
        return p_232616_.apply(new SheetGlyphInfo(){

            @Override
            public int m_213962_() {
                return SpecialGlyphs.this.f_232598_.m_84982_();
            }

            @Override
            public int m_213961_() {
                return SpecialGlyphs.this.f_232598_.m_85084_();
            }

            @Override
            public float m_213963_() {
                return 1.0f;
            }

            @Override
            public void m_213958_(int p_232629_, int p_232630_) {
                SpecialGlyphs.this.f_232598_.m_85040_(0, p_232629_, p_232630_, false);
            }

            @Override
            public boolean m_213965_() {
                return true;
            }
        });
    }

    private static /* synthetic */ SpecialGlyphs[] m_232619_() {
        return new SpecialGlyphs[]{WHITE, MISSING};
    }

    static {
        $VALUES = SpecialGlyphs.m_232619_();
    }

    @FunctionalInterface
    static interface PixelProvider {
        public int m_232634_(int var1, int var2);
    }
}


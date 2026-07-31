/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArraySet
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  javax.annotation.Nullable
 *  org.lwjgl.stb.STBTTFontinfo
 *  org.lwjgl.stb.STBTruetype
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 */
package com.mojang.blaze3d.font;

import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.SheetGlyphInfo;
import com.mojang.blaze3d.platform.NativeImage;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.function.Function;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class TrueTypeGlyphProvider
implements GlyphProvider {
    private final ByteBuffer f_83837_;
    final STBTTFontinfo f_83838_;
    final float f_83839_;
    private final IntSet f_83840_ = new IntArraySet();
    final float f_83841_;
    final float f_83842_;
    final float f_83843_;
    final float f_83844_;

    public TrueTypeGlyphProvider(ByteBuffer p_83846_, STBTTFontinfo p_83847_, float p_83848_, float p_83849_, float p_83850_, float p_83851_, String p_83852_) {
        this.f_83837_ = p_83846_;
        this.f_83838_ = p_83847_;
        this.f_83839_ = p_83849_;
        p_83852_.codePoints().forEach(arg_0 -> ((IntSet)this.f_83840_).add(arg_0));
        this.f_83841_ = p_83850_ * p_83849_;
        this.f_83842_ = p_83851_ * p_83849_;
        this.f_83843_ = STBTruetype.stbtt_ScaleForPixelHeight((STBTTFontinfo)p_83847_, (float)(p_83848_ * p_83849_));
        try (MemoryStack $$7 = MemoryStack.stackPush();){
            IntBuffer $$8 = $$7.mallocInt(1);
            IntBuffer $$9 = $$7.mallocInt(1);
            IntBuffer $$10 = $$7.mallocInt(1);
            STBTruetype.stbtt_GetFontVMetrics((STBTTFontinfo)p_83847_, (IntBuffer)$$8, (IntBuffer)$$9, (IntBuffer)$$10);
            this.f_83844_ = (float)$$8.get(0) * this.f_83843_;
        }
    }

    @Override
    @Nullable
    public GlyphInfo m_214022_(int p_231116_) {
        if (this.f_83840_.contains(p_231116_)) {
            return null;
        }
        try (MemoryStack $$1 = MemoryStack.stackPush();){
            int $$2 = STBTruetype.stbtt_FindGlyphIndex((STBTTFontinfo)this.f_83838_, (int)p_231116_);
            if ($$2 == 0) {
                GlyphInfo glyphInfo = null;
                return glyphInfo;
            }
            IntBuffer $$3 = $$1.mallocInt(1);
            IntBuffer $$4 = $$1.mallocInt(1);
            IntBuffer $$5 = $$1.mallocInt(1);
            IntBuffer $$6 = $$1.mallocInt(1);
            IntBuffer $$7 = $$1.mallocInt(1);
            IntBuffer $$8 = $$1.mallocInt(1);
            STBTruetype.stbtt_GetGlyphHMetrics((STBTTFontinfo)this.f_83838_, (int)$$2, (IntBuffer)$$7, (IntBuffer)$$8);
            STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel((STBTTFontinfo)this.f_83838_, (int)$$2, (float)this.f_83843_, (float)this.f_83843_, (float)this.f_83841_, (float)this.f_83842_, (IntBuffer)$$3, (IntBuffer)$$4, (IntBuffer)$$5, (IntBuffer)$$6);
            float $$9 = (float)$$7.get(0) * this.f_83843_;
            int $$10 = $$5.get(0) - $$3.get(0);
            int $$11 = $$6.get(0) - $$4.get(0);
            if ($$10 <= 0 || $$11 <= 0) {
                GlyphInfo.SpaceGlyphInfo spaceGlyphInfo = () -> $$9 / this.f_83839_;
                return spaceGlyphInfo;
            }
            Glyph glyph = new Glyph($$3.get(0), $$5.get(0), -$$4.get(0), -$$6.get(0), $$9, (float)$$8.get(0) * this.f_83843_, $$2);
            return glyph;
        }
    }

    @Override
    public void close() {
        this.f_83838_.free();
        MemoryUtil.memFree((Buffer)this.f_83837_);
    }

    @Override
    public IntSet m_6990_() {
        return (IntSet)IntStream.range(0, 65535).filter(p_231118_ -> !this.f_83840_.contains(p_231118_)).collect(IntOpenHashSet::new, IntCollection::add, IntCollection::addAll);
    }

    class Glyph
    implements GlyphInfo {
        final int f_83874_;
        final int f_83875_;
        final float f_83876_;
        final float f_83877_;
        private final float f_83878_;
        final int f_83879_;

        Glyph(int p_83882_, int p_83883_, int p_83884_, int p_83885_, float p_83886_, float p_83887_, int p_83888_) {
            this.f_83874_ = p_83883_ - p_83882_;
            this.f_83875_ = p_83884_ - p_83885_;
            this.f_83878_ = p_83886_ / TrueTypeGlyphProvider.this.f_83839_;
            this.f_83876_ = (p_83887_ + (float)p_83882_ + TrueTypeGlyphProvider.this.f_83841_) / TrueTypeGlyphProvider.this.f_83839_;
            this.f_83877_ = (TrueTypeGlyphProvider.this.f_83844_ - (float)p_83884_ + TrueTypeGlyphProvider.this.f_83842_) / TrueTypeGlyphProvider.this.f_83839_;
            this.f_83879_ = p_83888_;
        }

        @Override
        public float m_7403_() {
            return this.f_83878_;
        }

        @Override
        public BakedGlyph m_213604_(Function<SheetGlyphInfo, BakedGlyph> p_231120_) {
            return p_231120_.apply(new SheetGlyphInfo(){

                @Override
                public int m_213962_() {
                    return Glyph.this.f_83874_;
                }

                @Override
                public int m_213961_() {
                    return Glyph.this.f_83875_;
                }

                @Override
                public float m_213963_() {
                    return TrueTypeGlyphProvider.this.f_83839_;
                }

                @Override
                public float m_213966_() {
                    return Glyph.this.f_83876_;
                }

                @Override
                public float m_213964_() {
                    return Glyph.this.f_83877_;
                }

                @Override
                public void m_213958_(int p_231126_, int p_231127_) {
                    NativeImage $$2 = new NativeImage(NativeImage.Format.LUMINANCE, Glyph.this.f_83874_, Glyph.this.f_83875_, false);
                    $$2.m_85068_(TrueTypeGlyphProvider.this.f_83838_, Glyph.this.f_83879_, Glyph.this.f_83874_, Glyph.this.f_83875_, TrueTypeGlyphProvider.this.f_83843_, TrueTypeGlyphProvider.this.f_83843_, TrueTypeGlyphProvider.this.f_83841_, TrueTypeGlyphProvider.this.f_83842_, 0, 0);
                    $$2.m_85003_(0, p_231126_, p_231127_, 0, 0, Glyph.this.f_83874_, Glyph.this.f_83875_, false, true);
                }

                @Override
                public boolean m_213965_() {
                    return false;
                }
            });
        }
    }
}


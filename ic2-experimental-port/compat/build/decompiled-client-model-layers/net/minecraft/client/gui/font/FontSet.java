/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 */
package net.minecraft.client.gui.font;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.SheetGlyphInfo;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.HashSet;
import java.util.List;
import net.minecraft.client.gui.font.FontTexture;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.SpecialGlyphs;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class FontSet
implements AutoCloseable {
    private static final RandomSource f_95050_ = RandomSource.m_216327_();
    private static final float f_242991_ = 32.0f;
    private final TextureManager f_95051_;
    private final ResourceLocation f_95052_;
    private BakedGlyph f_95053_;
    private BakedGlyph f_95054_;
    private final List<GlyphProvider> f_95055_ = Lists.newArrayList();
    private final Int2ObjectMap<BakedGlyph> f_95056_ = new Int2ObjectOpenHashMap();
    private final Int2ObjectMap<GlyphInfoFilter> f_95057_ = new Int2ObjectOpenHashMap();
    private final Int2ObjectMap<IntList> f_95058_ = new Int2ObjectOpenHashMap();
    private final List<FontTexture> f_95059_ = Lists.newArrayList();

    public FontSet(TextureManager p_95062_, ResourceLocation p_95063_) {
        this.f_95051_ = p_95062_;
        this.f_95052_ = p_95063_;
    }

    public void m_95071_(List<GlyphProvider> p_95072_) {
        this.m_95077_();
        this.m_95080_();
        this.f_95056_.clear();
        this.f_95057_.clear();
        this.f_95058_.clear();
        this.f_95053_ = SpecialGlyphs.MISSING.m_213604_(this::m_232556_);
        this.f_95054_ = SpecialGlyphs.WHITE.m_213604_(this::m_232556_);
        IntOpenHashSet $$1 = new IntOpenHashSet();
        for (GlyphProvider $$2 : p_95072_) {
            $$1.addAll((IntCollection)$$2.m_6990_());
        }
        HashSet $$3 = Sets.newHashSet();
        $$1.forEach(p_232561_ -> {
            for (GlyphProvider $$3 : p_95072_) {
                GlyphInfo $$4 = $$3.m_214022_(p_232561_);
                if ($$4 == null) continue;
                $$3.add($$3);
                if ($$4 == SpecialGlyphs.MISSING) break;
                ((IntList)this.f_95058_.computeIfAbsent(Mth.m_14167_($$4.m_83827_(false)), p_232567_ -> new IntArrayList())).add(p_232561_);
                break;
            }
        });
        p_95072_.stream().filter($$3::contains).forEach(this.f_95055_::add);
    }

    @Override
    public void close() {
        this.m_95077_();
        this.m_95080_();
    }

    private void m_95077_() {
        for (GlyphProvider $$0 : this.f_95055_) {
            $$0.close();
        }
        this.f_95055_.clear();
    }

    private void m_95080_() {
        for (FontTexture $$0 : this.f_95059_) {
            $$0.close();
        }
        this.f_95059_.clear();
    }

    private static boolean m_243068_(GlyphInfo p_243323_) {
        float $$1 = p_243323_.m_83827_(false);
        if ($$1 < 0.0f || $$1 > 32.0f) {
            return true;
        }
        float $$2 = p_243323_.m_83827_(true);
        return $$2 < 0.0f || $$2 > 32.0f;
    }

    private GlyphInfoFilter m_243121_(int p_243321_) {
        GlyphInfo $$1 = null;
        for (GlyphProvider $$2 : this.f_95055_) {
            GlyphInfo $$3 = $$2.m_214022_(p_243321_);
            if ($$3 == null) continue;
            if ($$1 == null) {
                $$1 = $$3;
            }
            if (FontSet.m_243068_($$3)) continue;
            return new GlyphInfoFilter($$1, $$3);
        }
        if ($$1 != null) {
            return new GlyphInfoFilter($$1, SpecialGlyphs.MISSING);
        }
        return GlyphInfoFilter.f_243023_;
    }

    public GlyphInfo m_243128_(int p_243235_, boolean p_243251_) {
        return ((GlyphInfoFilter)this.f_95057_.computeIfAbsent(p_243235_, this::m_243121_)).m_243099_(p_243251_);
    }

    private BakedGlyph m_232564_(int p_232565_) {
        for (GlyphProvider $$1 : this.f_95055_) {
            GlyphInfo $$2 = $$1.m_214022_(p_232565_);
            if ($$2 == null) continue;
            return $$2.m_213604_(this::m_232556_);
        }
        return this.f_95053_;
    }

    public BakedGlyph m_95078_(int p_95079_) {
        return (BakedGlyph)this.f_95056_.computeIfAbsent(p_95079_, this::m_232564_);
    }

    private BakedGlyph m_232556_(SheetGlyphInfo p_232557_) {
        for (FontTexture $$1 : this.f_95059_) {
            BakedGlyph $$2 = $$1.m_232568_(p_232557_);
            if ($$2 == null) continue;
            return $$2;
        }
        FontTexture $$3 = new FontTexture(new ResourceLocation(this.f_95052_.m_135827_(), this.f_95052_.m_135815_() + "/" + this.f_95059_.size()), p_232557_.m_213965_());
        this.f_95059_.add($$3);
        this.f_95051_.m_118495_($$3.m_95099_(), $$3);
        BakedGlyph $$4 = $$3.m_232568_(p_232557_);
        return $$4 == null ? this.f_95053_ : $$4;
    }

    public BakedGlyph m_95067_(GlyphInfo p_95068_) {
        IntList $$1 = (IntList)this.f_95058_.get(Mth.m_14167_(p_95068_.m_83827_(false)));
        if ($$1 != null && !$$1.isEmpty()) {
            return this.m_95078_($$1.getInt(f_95050_.m_188503_($$1.size())));
        }
        return this.f_95053_;
    }

    public BakedGlyph m_95064_() {
        return this.f_95054_;
    }

    record GlyphInfoFilter(GlyphInfo f_243013_, GlyphInfo f_243006_) {
        static final GlyphInfoFilter f_243023_ = new GlyphInfoFilter(SpecialGlyphs.MISSING, SpecialGlyphs.MISSING);

        GlyphInfo m_243099_(boolean p_243218_) {
            return p_243218_ ? this.f_243006_ : this.f_243013_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{GlyphInfoFilter.class, "glyphInfo;glyphInfoNotFishy", "f_243013_", "f_243006_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{GlyphInfoFilter.class, "glyphInfo;glyphInfoNotFishy", "f_243013_", "f_243006_"}, this);
        }

        @Override
        public final boolean equals(Object p_243310_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{GlyphInfoFilter.class, "glyphInfo;glyphInfoNotFishy", "f_243013_", "f_243006_"}, this, p_243310_);
        }
    }
}


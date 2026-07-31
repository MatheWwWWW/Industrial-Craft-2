/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.ints.IntSets
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.font.providers;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.SheetGlyphInfo;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.providers.GlyphProviderBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.slf4j.Logger;

public class BitmapProvider
implements GlyphProvider {
    static final Logger f_95328_ = LogUtils.getLogger();
    private final NativeImage f_95329_;
    private final Int2ObjectMap<Glyph> f_95330_;

    BitmapProvider(NativeImage p_95333_, Int2ObjectMap<Glyph> p_95334_) {
        this.f_95329_ = p_95333_;
        this.f_95330_ = p_95334_;
    }

    @Override
    public void close() {
        this.f_95329_.close();
    }

    @Override
    @Nullable
    public GlyphInfo m_214022_(int p_232638_) {
        return (GlyphInfo)this.f_95330_.get(p_232638_);
    }

    @Override
    public IntSet m_6990_() {
        return IntSets.unmodifiable((IntSet)this.f_95330_.keySet());
    }

    record Glyph(float f_95363_, NativeImage f_95364_, int f_95365_, int f_95366_, int f_95367_, int f_95368_, int f_95369_, int f_95370_) implements GlyphInfo
    {
        @Override
        public float m_7403_() {
            return this.f_95369_;
        }

        @Override
        public BakedGlyph m_213604_(Function<SheetGlyphInfo, BakedGlyph> p_232640_) {
            return p_232640_.apply(new SheetGlyphInfo(){

                @Override
                public float m_213963_() {
                    return 1.0f / f_95363_;
                }

                @Override
                public int m_213962_() {
                    return f_95367_;
                }

                @Override
                public int m_213961_() {
                    return f_95368_;
                }

                @Override
                public float m_213964_() {
                    return SheetGlyphInfo.super.m_213964_() + 7.0f - (float)f_95370_;
                }

                @Override
                public void m_213958_(int p_232658_, int p_232659_) {
                    f_95364_.m_85003_(0, p_232658_, p_232659_, f_95365_, f_95366_, f_95367_, f_95368_, false, false);
                }

                @Override
                public boolean m_213965_() {
                    return f_95364_.m_85102_().m_85161_() > 1;
                }
            });
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Glyph.class, "scale;image;offsetX;offsetY;width;height;advance;ascent", "f_95363_", "f_95364_", "f_95365_", "f_95366_", "f_95367_", "f_95368_", "f_95369_", "f_95370_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Glyph.class, "scale;image;offsetX;offsetY;width;height;advance;ascent", "f_95363_", "f_95364_", "f_95365_", "f_95366_", "f_95367_", "f_95368_", "f_95369_", "f_95370_"}, this);
        }

        @Override
        public final boolean equals(Object p_232645_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Glyph.class, "scale;image;offsetX;offsetY;width;height;advance;ascent", "f_95363_", "f_95364_", "f_95365_", "f_95366_", "f_95367_", "f_95368_", "f_95369_", "f_95370_"}, this, p_232645_);
        }
    }

    public static class Builder
    implements GlyphProviderBuilder {
        private final ResourceLocation f_95344_;
        private final List<int[]> f_95345_;
        private final int f_95346_;
        private final int f_95347_;

        public Builder(ResourceLocation p_95349_, int p_95350_, int p_95351_, List<int[]> p_95352_) {
            this.f_95344_ = new ResourceLocation(p_95349_.m_135827_(), "textures/" + p_95349_.m_135815_());
            this.f_95345_ = p_95352_;
            this.f_95346_ = p_95350_;
            this.f_95347_ = p_95351_;
        }

        public static Builder m_95355_(JsonObject p_95356_) {
            int $$1 = GsonHelper.m_13824_(p_95356_, "height", 8);
            int $$2 = GsonHelper.m_13927_(p_95356_, "ascent");
            if ($$2 > $$1) {
                throw new JsonParseException("Ascent " + $$2 + " higher than height " + $$1);
            }
            ArrayList $$3 = Lists.newArrayList();
            JsonArray $$4 = GsonHelper.m_13933_(p_95356_, "chars");
            for (int $$5 = 0; $$5 < $$4.size(); ++$$5) {
                int $$8;
                String $$6 = GsonHelper.m_13805_($$4.get($$5), "chars[" + $$5 + "]");
                int[] $$7 = $$6.codePoints().toArray();
                if ($$5 > 0 && $$7.length != ($$8 = ((int[])$$3.get(0)).length)) {
                    throw new JsonParseException("Elements of chars have to be the same length (found: " + $$7.length + ", expected: " + $$8 + "), pad with space or \\u0000");
                }
                $$3.add($$7);
            }
            if ($$3.isEmpty() || ((int[])$$3.get(0)).length == 0) {
                throw new JsonParseException("Expected to find data in chars, found none.");
            }
            return new Builder(new ResourceLocation(GsonHelper.m_13906_(p_95356_, "file")), $$1, $$2, $$3);
        }

        @Override
        @Nullable
        public GlyphProvider m_6762_(ResourceManager p_95354_) {
            BitmapProvider bitmapProvider;
            block10: {
                InputStream $$1 = p_95354_.m_215595_(this.f_95344_);
                try {
                    NativeImage $$2 = NativeImage.m_85048_(NativeImage.Format.RGBA, $$1);
                    int $$3 = $$2.m_84982_();
                    int $$4 = $$2.m_85084_();
                    int $$5 = $$3 / this.f_95345_.get(0).length;
                    int $$6 = $$4 / this.f_95345_.size();
                    float $$7 = (float)this.f_95346_ / (float)$$6;
                    Int2ObjectOpenHashMap $$8 = new Int2ObjectOpenHashMap();
                    for (int $$9 = 0; $$9 < this.f_95345_.size(); ++$$9) {
                        int $$10 = 0;
                        for (int $$11 : this.f_95345_.get($$9)) {
                            int $$13;
                            Glyph $$14;
                            int $$12 = $$10++;
                            if ($$11 == 0 || ($$14 = (Glyph)$$8.put($$11, (Object)new Glyph($$7, $$2, $$12 * $$5, $$9 * $$6, $$5, $$6, (int)(0.5 + (double)((float)($$13 = this.m_95357_($$2, $$5, $$6, $$12, $$9)) * $$7)) + 1, this.f_95347_))) == null) continue;
                            f_95328_.warn("Codepoint '{}' declared multiple times in {}", (Object)Integer.toHexString($$11), (Object)this.f_95344_);
                        }
                    }
                    bitmapProvider = new BitmapProvider($$2, (Int2ObjectMap<Glyph>)$$8);
                    if ($$1 == null) break block10;
                }
                catch (Throwable throwable) {
                    try {
                        if ($$1 != null) {
                            try {
                                $$1.close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                    catch (IOException $$15) {
                        throw new RuntimeException($$15.getMessage());
                    }
                }
                $$1.close();
            }
            return bitmapProvider;
        }

        private int m_95357_(NativeImage p_95358_, int p_95359_, int p_95360_, int p_95361_, int p_95362_) {
            int $$5;
            for ($$5 = p_95359_ - 1; $$5 >= 0; --$$5) {
                int $$6 = p_95361_ * p_95359_ + $$5;
                for (int $$7 = 0; $$7 < p_95360_; ++$$7) {
                    int $$8 = p_95362_ * p_95360_ + $$7;
                    if (p_95358_.m_85087_($$6, $$8) == 0) continue;
                    return $$5 + 1;
                }
            }
            return $$5 + 1;
        }
    }
}


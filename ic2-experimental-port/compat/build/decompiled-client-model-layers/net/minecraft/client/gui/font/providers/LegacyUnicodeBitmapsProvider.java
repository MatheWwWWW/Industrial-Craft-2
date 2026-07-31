/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.font.providers;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.SheetGlyphInfo;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.providers.GlyphProviderBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.slf4j.Logger;

public class LegacyUnicodeBitmapsProvider
implements GlyphProvider {
    static final Logger f_95422_ = LogUtils.getLogger();
    private static final int f_169109_ = 256;
    private static final int f_232665_ = 256;
    private static final int f_169111_ = 256;
    private static final byte f_232666_ = 0;
    private final ResourceManager f_95423_;
    private final byte[] f_95424_;
    private final String f_95425_;
    private final Map<ResourceLocation, NativeImage> f_95426_ = Maps.newHashMap();

    public LegacyUnicodeBitmapsProvider(ResourceManager p_95429_, byte[] p_95430_, String p_95431_) {
        this.f_95423_ = p_95429_;
        this.f_95424_ = p_95430_;
        this.f_95425_ = p_95431_;
        for (int $$3 = 0; $$3 < 256; ++$$3) {
            int $$4 = $$3 * 256;
            ResourceLocation $$5 = this.m_95442_($$4);
            try (InputStream $$6 = this.f_95423_.m_215595_($$5);
                 NativeImage $$7 = NativeImage.m_85048_(NativeImage.Format.RGBA, $$6);){
                if ($$7.m_84982_() == 256 && $$7.m_85084_() == 256) {
                    for (int $$8 = 0; $$8 < 256; ++$$8) {
                        byte $$9 = p_95430_[$$4 + $$8];
                        if ($$9 == 0 || LegacyUnicodeBitmapsProvider.m_95433_($$9) <= LegacyUnicodeBitmapsProvider.m_95440_($$9)) continue;
                        p_95430_[$$4 + $$8] = 0;
                    }
                    continue;
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
            Arrays.fill(p_95430_, $$4, $$4 + 256, (byte)0);
        }
    }

    @Override
    public void close() {
        this.f_95426_.values().forEach(NativeImage::close);
    }

    private ResourceLocation m_95442_(int p_95443_) {
        ResourceLocation $$1 = new ResourceLocation(String.format(Locale.ROOT, this.f_95425_, String.format(Locale.ROOT, "%02x", p_95443_ / 256)));
        return new ResourceLocation($$1.m_135827_(), "textures/" + $$1.m_135815_());
    }

    @Override
    @Nullable
    public GlyphInfo m_214022_(int p_232668_) {
        NativeImage $$2;
        if (p_232668_ < 0 || p_232668_ >= this.f_95424_.length) {
            return null;
        }
        byte $$1 = this.f_95424_[p_232668_];
        if ($$1 != 0 && ($$2 = this.f_95426_.computeIfAbsent(this.m_95442_(p_232668_), this::m_95437_)) != null) {
            int $$3 = LegacyUnicodeBitmapsProvider.m_95433_($$1);
            return new Glyph(p_232668_ % 16 * 16 + $$3, (p_232668_ & 0xFF) / 16 * 16, LegacyUnicodeBitmapsProvider.m_95440_($$1) - $$3, 16, $$2);
        }
        return null;
    }

    @Override
    public IntSet m_6990_() {
        IntOpenHashSet $$0 = new IntOpenHashSet();
        for (int $$1 = 0; $$1 < this.f_95424_.length; ++$$1) {
            if (this.f_95424_[$$1] == 0) continue;
            $$0.add($$1);
        }
        return $$0;
    }

    @Nullable
    private NativeImage m_95437_(ResourceLocation p_95438_) {
        NativeImage nativeImage;
        block8: {
            InputStream $$1 = this.f_95423_.m_215595_(p_95438_);
            try {
                nativeImage = NativeImage.m_85048_(NativeImage.Format.RGBA, $$1);
                if ($$1 == null) break block8;
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
                catch (IOException $$2) {
                    f_95422_.error("Couldn't load texture {}", (Object)p_95438_, (Object)$$2);
                    return null;
                }
            }
            $$1.close();
        }
        return nativeImage;
    }

    private static int m_95433_(byte p_95434_) {
        return p_95434_ >> 4 & 0xF;
    }

    private static int m_95440_(byte p_95441_) {
        return (p_95441_ & 0xF) + 1;
    }

    record Glyph(int f_95456_, int f_95457_, int f_95454_, int f_95455_, NativeImage f_95458_) implements GlyphInfo
    {
        @Override
        public float m_7403_() {
            return this.f_95454_ / 2 + 1;
        }

        @Override
        public float m_5645_() {
            return 0.5f;
        }

        @Override
        public float m_5619_() {
            return 0.5f;
        }

        @Override
        public BakedGlyph m_213604_(Function<SheetGlyphInfo, BakedGlyph> p_232670_) {
            return p_232670_.apply(new SheetGlyphInfo(){

                @Override
                public float m_213963_() {
                    return 2.0f;
                }

                @Override
                public int m_213962_() {
                    return f_95454_;
                }

                @Override
                public int m_213961_() {
                    return f_95455_;
                }

                @Override
                public void m_213958_(int p_232685_, int p_232686_) {
                    f_95458_.m_85003_(0, p_232685_, p_232686_, f_95456_, f_95457_, f_95454_, f_95455_, false, false);
                }

                @Override
                public boolean m_213965_() {
                    return f_95458_.m_85102_().m_85161_() > 1;
                }
            });
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Glyph.class, "sourceX;sourceY;width;height;source", "f_95456_", "f_95457_", "f_95454_", "f_95455_", "f_95458_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Glyph.class, "sourceX;sourceY;width;height;source", "f_95456_", "f_95457_", "f_95454_", "f_95455_", "f_95458_"}, this);
        }

        @Override
        public final boolean equals(Object p_232675_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Glyph.class, "sourceX;sourceY;width;height;source", "f_95456_", "f_95457_", "f_95454_", "f_95455_", "f_95458_"}, this, p_232675_);
        }
    }

    public static class Builder
    implements GlyphProviderBuilder {
        private final ResourceLocation f_95445_;
        private final String f_95446_;

        public Builder(ResourceLocation p_95448_, String p_95449_) {
            this.f_95445_ = p_95448_;
            this.f_95446_ = p_95449_;
        }

        public static GlyphProviderBuilder m_95452_(JsonObject p_95453_) {
            return new Builder(new ResourceLocation(GsonHelper.m_13906_(p_95453_, "sizes")), Builder.m_182569_(p_95453_));
        }

        private static String m_182569_(JsonObject p_182570_) {
            String $$1 = GsonHelper.m_13906_(p_182570_, "template");
            try {
                String.format(Locale.ROOT, $$1, "");
            }
            catch (IllegalFormatException $$2) {
                throw new JsonParseException("Invalid legacy unicode template supplied, expected single '%s': " + $$1);
            }
            return $$1;
        }

        @Override
        @Nullable
        public GlyphProvider m_6762_(ResourceManager p_95451_) {
            LegacyUnicodeBitmapsProvider legacyUnicodeBitmapsProvider;
            block8: {
                InputStream $$1 = Minecraft.m_91087_().m_91098_().m_215595_(this.f_95445_);
                try {
                    byte[] $$2 = $$1.readNBytes(65536);
                    legacyUnicodeBitmapsProvider = new LegacyUnicodeBitmapsProvider(p_95451_, $$2, this.f_95446_);
                    if ($$1 == null) break block8;
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
                    catch (IOException $$3) {
                        f_95422_.error("Cannot load {}, unicode glyphs will not render correctly", (Object)this.f_95445_);
                        return null;
                    }
                }
                $$1.close();
            }
            return legacyUnicodeBitmapsProvider;
        }
    }
}


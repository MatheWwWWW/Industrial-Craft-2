/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.lwjgl.stb.STBTTFontinfo
 *  org.lwjgl.stb.STBTruetype
 *  org.lwjgl.system.MemoryUtil
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.font.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.TrueTypeGlyphProvider;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.font.providers.GlyphProviderBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;

public class TrueTypeGlyphProviderBuilder
implements GlyphProviderBuilder {
    private static final Logger f_95482_ = LogUtils.getLogger();
    private final ResourceLocation f_95483_;
    private final float f_95484_;
    private final float f_95485_;
    private final float f_95486_;
    private final float f_95487_;
    private final String f_95488_;

    public TrueTypeGlyphProviderBuilder(ResourceLocation p_95491_, float p_95492_, float p_95493_, float p_95494_, float p_95495_, String p_95496_) {
        this.f_95483_ = p_95491_;
        this.f_95484_ = p_95492_;
        this.f_95485_ = p_95493_;
        this.f_95486_ = p_95494_;
        this.f_95487_ = p_95495_;
        this.f_95488_ = p_95496_;
    }

    public static GlyphProviderBuilder m_95499_(JsonObject p_95500_) {
        float $$1 = 0.0f;
        float $$2 = 0.0f;
        if (p_95500_.has("shift")) {
            JsonArray $$3 = p_95500_.getAsJsonArray("shift");
            if ($$3.size() != 2) {
                throw new JsonParseException("Expected 2 elements in 'shift', found " + $$3.size());
            }
            $$1 = GsonHelper.m_13888_($$3.get(0), "shift[0]");
            $$2 = GsonHelper.m_13888_($$3.get(1), "shift[1]");
        }
        StringBuilder $$4 = new StringBuilder();
        if (p_95500_.has("skip")) {
            JsonElement $$5 = p_95500_.get("skip");
            if ($$5.isJsonArray()) {
                JsonArray $$6 = GsonHelper.m_13924_($$5, "skip");
                for (int $$7 = 0; $$7 < $$6.size(); ++$$7) {
                    $$4.append(GsonHelper.m_13805_($$6.get($$7), "skip[" + $$7 + "]"));
                }
            } else {
                $$4.append(GsonHelper.m_13805_($$5, "skip"));
            }
        }
        return new TrueTypeGlyphProviderBuilder(new ResourceLocation(GsonHelper.m_13906_(p_95500_, "file")), GsonHelper.m_13820_(p_95500_, "size", 11.0f), GsonHelper.m_13820_(p_95500_, "oversample", 1.0f), $$1, $$2, $$4.toString());
    }

    @Override
    @Nullable
    public GlyphProvider m_6762_(ResourceManager p_95498_) {
        TrueTypeGlyphProvider trueTypeGlyphProvider;
        block10: {
            STBTTFontinfo $$1 = null;
            ByteBuffer $$2 = null;
            InputStream $$3 = p_95498_.m_215595_(new ResourceLocation(this.f_95483_.m_135827_(), "font/" + this.f_95483_.m_135815_()));
            try {
                f_95482_.debug("Loading font {}", (Object)this.f_95483_);
                $$1 = STBTTFontinfo.malloc();
                $$2 = TextureUtil.m_85303_($$3);
                $$2.flip();
                f_95482_.debug("Reading font {}", (Object)this.f_95483_);
                if (!STBTruetype.stbtt_InitFont((STBTTFontinfo)$$1, (ByteBuffer)$$2)) {
                    throw new IOException("Invalid ttf");
                }
                trueTypeGlyphProvider = new TrueTypeGlyphProvider($$2, $$1, this.f_95484_, this.f_95485_, this.f_95486_, this.f_95487_, this.f_95488_);
                if ($$3 == null) break block10;
            }
            catch (Throwable throwable) {
                try {
                    if ($$3 != null) {
                        try {
                            $$3.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception $$4) {
                    f_95482_.error("Couldn't load truetype font {}", (Object)this.f_95483_, (Object)$$4);
                    if ($$1 != null) {
                        $$1.free();
                    }
                    MemoryUtil.memFree($$2);
                    return null;
                }
            }
            $$3.close();
        }
        return trueTypeGlyphProvider;
    }
}


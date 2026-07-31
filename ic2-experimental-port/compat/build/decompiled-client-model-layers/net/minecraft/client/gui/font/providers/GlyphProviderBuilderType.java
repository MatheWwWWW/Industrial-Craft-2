/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonObject
 */
package net.minecraft.client.gui.font.providers;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.font.SpaceProvider;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.client.gui.font.providers.BitmapProvider;
import net.minecraft.client.gui.font.providers.GlyphProviderBuilder;
import net.minecraft.client.gui.font.providers.LegacyUnicodeBitmapsProvider;
import net.minecraft.client.gui.font.providers.TrueTypeGlyphProviderBuilder;

public final class GlyphProviderBuilderType
extends Enum<GlyphProviderBuilderType> {
    public static final /* enum */ GlyphProviderBuilderType BITMAP = new GlyphProviderBuilderType("bitmap", BitmapProvider.Builder::m_95355_);
    public static final /* enum */ GlyphProviderBuilderType TTF = new GlyphProviderBuilderType("ttf", TrueTypeGlyphProviderBuilder::m_95499_);
    public static final /* enum */ GlyphProviderBuilderType SPACE = new GlyphProviderBuilderType("space", SpaceProvider::m_231106_);
    public static final /* enum */ GlyphProviderBuilderType LEGACY_UNICODE = new GlyphProviderBuilderType("legacy_unicode", LegacyUnicodeBitmapsProvider.Builder::m_95452_);
    private static final Map<String, GlyphProviderBuilderType> f_95403_;
    private final String f_95404_;
    private final Function<JsonObject, GlyphProviderBuilder> f_95405_;
    private static final /* synthetic */ GlyphProviderBuilderType[] $VALUES;

    public static GlyphProviderBuilderType[] values() {
        return (GlyphProviderBuilderType[])$VALUES.clone();
    }

    public static GlyphProviderBuilderType valueOf(String p_95420_) {
        return Enum.valueOf(GlyphProviderBuilderType.class, p_95420_);
    }

    private GlyphProviderBuilderType(String p_95411_, Function<JsonObject, GlyphProviderBuilder> p_95412_) {
        this.f_95404_ = p_95411_;
        this.f_95405_ = p_95412_;
    }

    public static GlyphProviderBuilderType m_95415_(String p_95416_) {
        GlyphProviderBuilderType $$1 = f_95403_.get(p_95416_);
        if ($$1 == null) {
            throw new IllegalArgumentException("Invalid type: " + p_95416_);
        }
        return $$1;
    }

    public GlyphProviderBuilder m_95413_(JsonObject p_95414_) {
        return this.f_95405_.apply(p_95414_);
    }

    private static /* synthetic */ GlyphProviderBuilderType[] m_169108_() {
        return new GlyphProviderBuilderType[]{BITMAP, TTF, SPACE, LEGACY_UNICODE};
    }

    static {
        $VALUES = GlyphProviderBuilderType.m_169108_();
        f_95403_ = Util.m_137469_(Maps.newHashMap(), p_95418_ -> {
            for (GlyphProviderBuilderType $$1 : GlyphProviderBuilderType.values()) {
                p_95418_.put($$1.f_95404_, $$1);
            }
        });
    }
}


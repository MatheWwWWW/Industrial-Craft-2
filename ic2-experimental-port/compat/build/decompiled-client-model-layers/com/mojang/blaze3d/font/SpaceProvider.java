/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  it.unimi.dsi.fastutil.ints.Int2FloatMap
 *  it.unimi.dsi.fastutil.ints.Int2FloatMaps
 *  it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.ints.IntSets
 *  javax.annotation.Nullable
 */
package com.mojang.blaze3d.font;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatMaps;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.gui.font.providers.GlyphProviderBuilder;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;

public class SpaceProvider
implements GlyphProvider {
    private final Int2ObjectMap<GlyphInfo.SpaceGlyphInfo> f_231098_;

    public SpaceProvider(Int2FloatMap p_231100_) {
        this.f_231098_ = new Int2ObjectOpenHashMap(p_231100_.size());
        Int2FloatMaps.fastForEach((Int2FloatMap)p_231100_, p_231109_ -> {
            float $$1 = p_231109_.getFloatValue();
            this.f_231098_.put(p_231109_.getIntKey(), () -> $$1);
        });
    }

    @Override
    @Nullable
    public GlyphInfo m_214022_(int p_231105_) {
        return (GlyphInfo)this.f_231098_.get(p_231105_);
    }

    @Override
    public IntSet m_6990_() {
        return IntSets.unmodifiable((IntSet)this.f_231098_.keySet());
    }

    public static GlyphProviderBuilder m_231106_(JsonObject p_231107_) {
        Int2FloatOpenHashMap $$1 = new Int2FloatOpenHashMap();
        JsonObject $$2 = GsonHelper.m_13930_(p_231107_, "advances");
        for (Map.Entry $$3 : $$2.entrySet()) {
            int[] $$4 = ((String)$$3.getKey()).codePoints().toArray();
            if ($$4.length != 1) {
                throw new JsonParseException("Expected single codepoint, got " + Arrays.toString($$4));
            }
            float $$5 = GsonHelper.m_13888_((JsonElement)$$3.getValue(), "advance");
            $$1.put($$4[0], $$5);
        }
        return arg_0 -> SpaceProvider.m_231110_((Int2FloatMap)$$1, arg_0);
    }

    private static /* synthetic */ GlyphProvider m_231110_(Int2FloatMap p_231111_, ResourceManager p_231112_) {
        return new SpaceProvider(p_231111_);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.packs.resources;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

public abstract class SimpleJsonResourceReloadListener
extends SimplePreparableReloadListener<Map<ResourceLocation, JsonElement>> {
    private static final Logger f_10762_ = LogUtils.getLogger();
    private static final String f_143936_ = ".json";
    private static final int f_10763_ = ".json".length();
    private final Gson f_10764_;
    private final String f_10765_;

    public SimpleJsonResourceReloadListener(Gson p_10768_, String p_10769_) {
        this.f_10764_ = p_10768_;
        this.f_10765_ = p_10769_;
    }

    @Override
    protected Map<ResourceLocation, JsonElement> m_5944_(ResourceManager p_10771_, ProfilerFiller p_10772_) {
        HashMap $$2 = Maps.newHashMap();
        int $$3 = this.f_10765_.length() + 1;
        for (Map.Entry<ResourceLocation, Resource> $$4 : p_10771_.m_214159_(this.f_10765_, p_215600_ -> p_215600_.m_135815_().endsWith(f_143936_)).entrySet()) {
            ResourceLocation $$5 = $$4.getKey();
            String $$6 = $$5.m_135815_();
            ResourceLocation $$7 = new ResourceLocation($$5.m_135827_(), $$6.substring($$3, $$6.length() - f_10763_));
            try {
                BufferedReader $$8 = $$4.getValue().m_215508_();
                try {
                    JsonElement $$9 = GsonHelper.m_13776_(this.f_10764_, $$8, JsonElement.class);
                    if ($$9 != null) {
                        JsonElement $$10 = $$2.put($$7, $$9);
                        if ($$10 == null) continue;
                        throw new IllegalStateException("Duplicate data file ignored with ID " + $$7);
                    }
                    f_10762_.error("Couldn't load data file {} from {} as it's null or empty", (Object)$$7, (Object)$$5);
                }
                finally {
                    if ($$8 == null) continue;
                    ((Reader)$$8).close();
                }
            }
            catch (JsonParseException | IOException | IllegalArgumentException $$11) {
                f_10762_.error("Couldn't parse data file {} from {}", new Object[]{$$7, $$5, $$11});
            }
        }
        return $$2;
    }

    @Override
    protected /* synthetic */ Object m_5944_(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return this.m_5944_(resourceManager, profilerFiller);
    }
}


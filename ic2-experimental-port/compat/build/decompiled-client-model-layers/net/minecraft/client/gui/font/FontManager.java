/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.font;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.AllMissingGlyphProvider;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.providers.GlyphProviderBuilderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

public class FontManager
implements AutoCloseable {
    static final Logger f_94997_ = LogUtils.getLogger();
    private static final String f_169089_ = "fonts.json";
    public static final ResourceLocation f_94996_ = new ResourceLocation("minecraft", "missing");
    private final FontSet f_94998_;
    final Map<ResourceLocation, FontSet> f_94999_ = Maps.newHashMap();
    final TextureManager f_95000_;
    private Map<ResourceLocation, ResourceLocation> f_95001_ = ImmutableMap.of();
    private final PreparableReloadListener f_95002_ = new SimplePreparableReloadListener<Map<ResourceLocation, List<GlyphProvider>>>(){

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected Map<ResourceLocation, List<GlyphProvider>> m_5944_(ResourceManager p_95024_, ProfilerFiller p_95025_) {
            p_95025_.m_7242_();
            Gson $$2 = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            HashMap $$3 = Maps.newHashMap();
            for (Map.Entry<ResourceLocation, List<Resource>> $$4 : p_95024_.m_214160_("font", p_232555_ -> p_232555_.m_135815_().endsWith(".json")).entrySet()) {
                ResourceLocation $$5 = $$4.getKey();
                String $$6 = $$5.m_135815_();
                ResourceLocation $$7 = new ResourceLocation($$5.m_135827_(), $$6.substring("font/".length(), $$6.length() - ".json".length()));
                List $$8 = $$3.computeIfAbsent($$7, p_95040_ -> Lists.newArrayList((Object[])new GlyphProvider[]{new AllMissingGlyphProvider()}));
                p_95025_.m_6521_($$7::toString);
                for (Resource $$9 : $$4.getValue()) {
                    p_95025_.m_6180_($$9.m_215506_());
                    try (BufferedReader $$10 = $$9.m_215508_();){
                        try {
                            p_95025_.m_6180_("reading");
                            JsonArray $$11 = GsonHelper.m_13933_(GsonHelper.m_13776_($$2, $$10, JsonObject.class), "providers");
                            p_95025_.m_6182_("parsing");
                            for (int $$12 = $$11.size() - 1; $$12 >= 0; --$$12) {
                                JsonObject $$13 = GsonHelper.m_13918_($$11.get($$12), "providers[" + $$12 + "]");
                                String $$14 = GsonHelper.m_13906_($$13, "type");
                                GlyphProviderBuilderType $$15 = GlyphProviderBuilderType.m_95415_($$14);
                                try {
                                    p_95025_.m_6180_($$14);
                                    GlyphProvider $$16 = $$15.m_95413_($$13).m_6762_(p_95024_);
                                    if ($$16 == null) continue;
                                    $$8.add($$16);
                                    continue;
                                }
                                finally {
                                    p_95025_.m_7238_();
                                }
                            }
                        }
                        finally {
                            p_95025_.m_7238_();
                        }
                    }
                    catch (Exception $$17) {
                        f_94997_.warn("Unable to load font '{}' in {} in resourcepack: '{}'", new Object[]{$$7, FontManager.f_169089_, $$9.m_215506_(), $$17});
                    }
                    p_95025_.m_7238_();
                }
                p_95025_.m_6180_("caching");
                IntOpenHashSet $$18 = new IntOpenHashSet();
                for (GlyphProvider $$19 : $$8) {
                    $$18.addAll((IntCollection)$$19.m_6990_());
                }
                $$18.forEach(p_95034_ -> {
                    GlyphProvider $$2;
                    if (p_95034_ == 32) {
                        return;
                    }
                    Iterator iterator = Lists.reverse((List)$$8).iterator();
                    while (iterator.hasNext() && ($$2 = (GlyphProvider)iterator.next()).m_214022_(p_95034_) == null) {
                    }
                });
                p_95025_.m_7238_();
                p_95025_.m_7238_();
            }
            p_95025_.m_7241_();
            return $$3;
        }

        @Override
        protected void m_5787_(Map<ResourceLocation, List<GlyphProvider>> p_95036_, ResourceManager p_95037_, ProfilerFiller p_95038_) {
            p_95038_.m_7242_();
            p_95038_.m_6180_("closing");
            FontManager.this.f_94999_.values().forEach(FontSet::close);
            FontManager.this.f_94999_.clear();
            p_95038_.m_6182_("reloading");
            p_95036_.forEach((p_95042_, p_95043_) -> {
                FontSet $$2 = new FontSet(FontManager.this.f_95000_, (ResourceLocation)p_95042_);
                $$2.m_95071_(Lists.reverse((List)p_95043_));
                FontManager.this.f_94999_.put((ResourceLocation)p_95042_, $$2);
            });
            p_95038_.m_7238_();
            p_95038_.m_7241_();
        }

        @Override
        public String m_7812_() {
            return "FontManager";
        }

        @Override
        protected /* synthetic */ Object m_5944_(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
            return this.m_5944_(resourceManager, profilerFiller);
        }
    };

    public FontManager(TextureManager p_95005_) {
        this.f_95000_ = p_95005_;
        this.f_94998_ = Util.m_137469_(new FontSet(p_95005_, f_94996_), p_95010_ -> p_95010_.m_95071_(Lists.newArrayList((Object[])new GlyphProvider[]{new AllMissingGlyphProvider()})));
    }

    public void m_95011_(Map<ResourceLocation, ResourceLocation> p_95012_) {
        this.f_95001_ = p_95012_;
    }

    public Font m_95006_() {
        return new Font(p_95014_ -> this.f_94999_.getOrDefault(this.f_95001_.getOrDefault(p_95014_, (ResourceLocation)p_95014_), this.f_94998_), false);
    }

    public Font m_243082_() {
        return new Font(p_243145_ -> this.f_94999_.getOrDefault(this.f_95001_.getOrDefault(p_243145_, (ResourceLocation)p_243145_), this.f_94998_), true);
    }

    public PreparableReloadListener m_95015_() {
        return this.f_95002_;
    }

    @Override
    public void close() {
        this.f_94999_.values().forEach(FontSet::close);
        this.f_94998_.close();
    }
}


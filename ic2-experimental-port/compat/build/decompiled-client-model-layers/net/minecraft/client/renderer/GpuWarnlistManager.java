/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

public class GpuWarnlistManager
extends SimplePreparableReloadListener<Preparations> {
    private static final Logger f_109210_ = LogUtils.getLogger();
    private static final ResourceLocation f_109211_ = new ResourceLocation("gpu_warnlist.json");
    private ImmutableMap<String, String> f_109212_ = ImmutableMap.of();
    private boolean f_109213_;
    private boolean f_109214_;
    private boolean f_109215_;

    public boolean m_109218_() {
        return !this.f_109212_.isEmpty();
    }

    public boolean m_109240_() {
        return this.m_109218_() && !this.f_109214_;
    }

    public void m_109247_() {
        this.f_109213_ = true;
    }

    public void m_109248_() {
        this.f_109214_ = true;
    }

    public void m_109249_() {
        this.f_109214_ = true;
        this.f_109215_ = true;
    }

    public boolean m_109250_() {
        return this.f_109213_ && !this.f_109214_;
    }

    public boolean m_109251_() {
        return this.f_109215_;
    }

    public void m_109252_() {
        this.f_109213_ = false;
        this.f_109214_ = false;
        this.f_109215_ = false;
    }

    @Nullable
    public String m_109253_() {
        return (String)this.f_109212_.get((Object)"renderer");
    }

    @Nullable
    public String m_109254_() {
        return (String)this.f_109212_.get((Object)"version");
    }

    @Nullable
    public String m_109255_() {
        return (String)this.f_109212_.get((Object)"vendor");
    }

    @Nullable
    public String m_109256_() {
        StringBuilder $$0 = new StringBuilder();
        this.f_109212_.forEach((p_109235_, p_109236_) -> $$0.append((String)p_109235_).append(": ").append((String)p_109236_));
        return $$0.length() == 0 ? null : $$0.toString();
    }

    @Override
    protected Preparations m_5944_(ResourceManager p_109220_, ProfilerFiller p_109221_) {
        ArrayList $$2 = Lists.newArrayList();
        ArrayList $$3 = Lists.newArrayList();
        ArrayList $$4 = Lists.newArrayList();
        p_109221_.m_7242_();
        JsonObject $$5 = GpuWarnlistManager.m_109244_(p_109220_, p_109221_);
        if ($$5 != null) {
            p_109221_.m_6180_("compile_regex");
            GpuWarnlistManager.m_109222_($$5.getAsJsonArray("renderer"), $$2);
            GpuWarnlistManager.m_109222_($$5.getAsJsonArray("version"), $$3);
            GpuWarnlistManager.m_109222_($$5.getAsJsonArray("vendor"), $$4);
            p_109221_.m_7238_();
        }
        p_109221_.m_7241_();
        return new Preparations($$2, $$3, $$4);
    }

    @Override
    protected void m_5787_(Preparations p_109226_, ResourceManager p_109227_, ProfilerFiller p_109228_) {
        this.f_109212_ = p_109226_.m_109269_();
    }

    private static void m_109222_(JsonArray p_109223_, List<Pattern> p_109224_) {
        p_109223_.forEach(p_109239_ -> p_109224_.add(Pattern.compile(p_109239_.getAsString(), 2)));
    }

    @Nullable
    private static JsonObject m_109244_(ResourceManager p_109245_, ProfilerFiller p_109246_) {
        p_109246_.m_6180_("parse_json");
        JsonObject $$2 = null;
        try (BufferedReader $$3 = p_109245_.m_215597_(f_109211_);){
            $$2 = JsonParser.parseReader((Reader)$$3).getAsJsonObject();
        }
        catch (JsonSyntaxException | IOException $$4) {
            f_109210_.warn("Failed to load GPU warnlist");
        }
        p_109246_.m_7238_();
        return $$2;
    }

    @Override
    protected /* synthetic */ Object m_5944_(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return this.m_5944_(resourceManager, profilerFiller);
    }

    protected static final class Preparations {
        private final List<Pattern> f_109257_;
        private final List<Pattern> f_109258_;
        private final List<Pattern> f_109259_;

        Preparations(List<Pattern> p_109261_, List<Pattern> p_109262_, List<Pattern> p_109263_) {
            this.f_109257_ = p_109261_;
            this.f_109258_ = p_109262_;
            this.f_109259_ = p_109263_;
        }

        private static String m_109272_(List<Pattern> p_109273_, String p_109274_) {
            ArrayList $$2 = Lists.newArrayList();
            for (Pattern $$3 : p_109273_) {
                Matcher $$4 = $$3.matcher(p_109274_);
                while ($$4.find()) {
                    $$2.add($$4.group());
                }
            }
            return String.join((CharSequence)", ", $$2);
        }

        ImmutableMap<String, String> m_109269_() {
            String $$3;
            String $$2;
            ImmutableMap.Builder $$0 = new ImmutableMap.Builder();
            String $$1 = Preparations.m_109272_(this.f_109257_, GlUtil.m_84820_());
            if (!$$1.isEmpty()) {
                $$0.put((Object)"renderer", (Object)$$1);
            }
            if (!($$2 = Preparations.m_109272_(this.f_109258_, GlUtil.m_84821_())).isEmpty()) {
                $$0.put((Object)"version", (Object)$$2);
            }
            if (!($$3 = Preparations.m_109272_(this.f_109259_, GlUtil.m_84818_())).isEmpty()) {
                $$0.put((Object)"vendor", (Object)$$3);
            }
            return $$0.build();
        }
    }
}


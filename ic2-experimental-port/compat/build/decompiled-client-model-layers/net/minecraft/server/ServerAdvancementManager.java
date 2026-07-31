/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.TreeNodePosition;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.PredicateManager;
import org.slf4j.Logger;

public class ServerAdvancementManager
extends SimpleJsonResourceReloadListener {
    private static final Logger f_136021_ = LogUtils.getLogger();
    private static final Gson f_136022_ = new GsonBuilder().create();
    private AdvancementList f_136023_ = new AdvancementList();
    private final PredicateManager f_136024_;

    public ServerAdvancementManager(PredicateManager p_136027_) {
        super(f_136022_, "advancements");
        this.f_136024_ = p_136027_;
    }

    @Override
    protected void m_5787_(Map<ResourceLocation, JsonElement> p_136034_, ResourceManager p_136035_, ProfilerFiller p_136036_) {
        HashMap $$3 = Maps.newHashMap();
        p_136034_.forEach((p_136039_, p_136040_) -> {
            try {
                JsonObject $$3 = GsonHelper.m_13918_(p_136040_, "advancement");
                Advancement.Builder $$4 = Advancement.Builder.m_138380_($$3, new DeserializationContext((ResourceLocation)p_136039_, this.f_136024_));
                $$3.put(p_136039_, $$4);
            }
            catch (Exception $$5) {
                f_136021_.error("Parsing error loading custom advancement {}: {}", p_136039_, (Object)$$5.getMessage());
            }
        });
        AdvancementList $$4 = new AdvancementList();
        $$4.m_139333_($$3);
        for (Advancement $$5 : $$4.m_139343_()) {
            if ($$5.m_138320_() == null) continue;
            TreeNodePosition.m_16587_($$5);
        }
        this.f_136023_ = $$4;
    }

    @Nullable
    public Advancement m_136041_(ResourceLocation p_136042_) {
        return this.f_136023_.m_139337_(p_136042_);
    }

    public Collection<Advancement> m_136028_() {
        return this.f_136023_.m_139344_();
    }
}


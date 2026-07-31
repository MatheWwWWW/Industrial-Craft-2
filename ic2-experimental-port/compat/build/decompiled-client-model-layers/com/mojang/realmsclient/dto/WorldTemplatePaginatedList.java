/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;

public class WorldTemplatePaginatedList
extends ValueObject {
    private static final Logger f_87757_ = LogUtils.getLogger();
    public List<WorldTemplate> f_87753_;
    public int f_87754_;
    public int f_87755_;
    public int f_87756_;

    public WorldTemplatePaginatedList() {
    }

    public WorldTemplatePaginatedList(int p_87761_) {
        this.f_87753_ = Collections.emptyList();
        this.f_87754_ = 0;
        this.f_87755_ = p_87761_;
        this.f_87756_ = -1;
    }

    public boolean m_167327_() {
        return this.f_87754_ * this.f_87755_ >= this.f_87756_ && this.f_87754_ > 0 && this.f_87756_ > 0 && this.f_87755_ > 0;
    }

    public static WorldTemplatePaginatedList m_87762_(String p_87763_) {
        WorldTemplatePaginatedList $$1 = new WorldTemplatePaginatedList();
        $$1.f_87753_ = Lists.newArrayList();
        try {
            JsonParser $$2 = new JsonParser();
            JsonObject $$3 = $$2.parse(p_87763_).getAsJsonObject();
            if ($$3.get("templates").isJsonArray()) {
                Iterator $$4 = $$3.get("templates").getAsJsonArray().iterator();
                while ($$4.hasNext()) {
                    $$1.f_87753_.add(WorldTemplate.m_87738_(((JsonElement)$$4.next()).getAsJsonObject()));
                }
            }
            $$1.f_87754_ = JsonUtils.m_90153_("page", $$3, 0);
            $$1.f_87755_ = JsonUtils.m_90153_("size", $$3, 0);
            $$1.f_87756_ = JsonUtils.m_90153_("total", $$3, 0);
        }
        catch (Exception $$5) {
            f_87757_.error("Could not parse WorldTemplatePaginatedList: {}", (Object)$$5.getMessage());
        }
        return $$1;
    }
}


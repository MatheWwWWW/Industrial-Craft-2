/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

public class RealmsServerPlayerList
extends ValueObject {
    private static final Logger f_87584_ = LogUtils.getLogger();
    private static final JsonParser f_87585_ = new JsonParser();
    public long f_87582_;
    public List<String> f_87583_;

    public static RealmsServerPlayerList m_87590_(JsonObject p_87591_) {
        RealmsServerPlayerList $$1 = new RealmsServerPlayerList();
        try {
            JsonElement $$3;
            $$1.f_87582_ = JsonUtils.m_90157_("serverId", p_87591_, -1L);
            String $$2 = JsonUtils.m_90161_("playerList", p_87591_, null);
            $$1.f_87583_ = $$2 != null ? (($$3 = f_87585_.parse($$2)).isJsonArray() ? RealmsServerPlayerList.m_87588_($$3.getAsJsonArray()) : Lists.newArrayList()) : Lists.newArrayList();
        }
        catch (Exception $$4) {
            f_87584_.error("Could not parse RealmsServerPlayerList: {}", (Object)$$4.getMessage());
        }
        return $$1;
    }

    private static List<String> m_87588_(JsonArray p_87589_) {
        ArrayList $$1 = Lists.newArrayList();
        for (JsonElement $$2 : p_87589_) {
            try {
                $$1.add($$2.getAsString());
            }
            catch (Exception exception) {}
        }
        return $$1;
    }
}


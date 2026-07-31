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
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;

public class RealmsServerList
extends ValueObject {
    private static final Logger f_87574_ = LogUtils.getLogger();
    public List<RealmsServer> f_87573_;

    public static RealmsServerList m_87577_(String p_87578_) {
        RealmsServerList $$1 = new RealmsServerList();
        $$1.f_87573_ = Lists.newArrayList();
        try {
            JsonParser $$2 = new JsonParser();
            JsonObject $$3 = $$2.parse(p_87578_).getAsJsonObject();
            if ($$3.get("servers").isJsonArray()) {
                JsonArray $$4 = $$3.get("servers").getAsJsonArray();
                Iterator $$5 = $$4.iterator();
                while ($$5.hasNext()) {
                    $$1.f_87573_.add(RealmsServer.m_87499_(((JsonElement)$$5.next()).getAsJsonObject()));
                }
            }
        }
        catch (Exception $$6) {
            f_87574_.error("Could not parse McoServerList: {}", (Object)$$6.getMessage());
        }
        return $$1;
    }
}


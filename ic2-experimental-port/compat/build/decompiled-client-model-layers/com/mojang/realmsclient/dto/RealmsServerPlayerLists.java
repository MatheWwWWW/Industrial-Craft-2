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
import com.mojang.realmsclient.dto.RealmsServerPlayerList;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;

public class RealmsServerPlayerLists
extends ValueObject {
    private static final Logger f_87593_ = LogUtils.getLogger();
    public List<RealmsServerPlayerList> f_87592_;

    public static RealmsServerPlayerLists m_87596_(String p_87597_) {
        RealmsServerPlayerLists $$1 = new RealmsServerPlayerLists();
        $$1.f_87592_ = Lists.newArrayList();
        try {
            JsonParser $$2 = new JsonParser();
            JsonObject $$3 = $$2.parse(p_87597_).getAsJsonObject();
            if ($$3.get("lists").isJsonArray()) {
                JsonArray $$4 = $$3.get("lists").getAsJsonArray();
                Iterator $$5 = $$4.iterator();
                while ($$5.hasNext()) {
                    $$1.f_87592_.add(RealmsServerPlayerList.m_87590_(((JsonElement)$$5.next()).getAsJsonObject()));
                }
            }
        }
        catch (Exception $$6) {
            f_87593_.error("Could not parse RealmsServerPlayerLists: {}", (Object)$$6.getMessage());
        }
        return $$1;
    }
}


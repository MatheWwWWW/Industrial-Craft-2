/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.ServerActivity;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.List;

public class ServerActivityList
extends ValueObject {
    public long f_167318_;
    public List<ServerActivity> f_167319_ = Lists.newArrayList();

    public static ServerActivityList m_167321_(String p_167322_) {
        ServerActivityList $$1 = new ServerActivityList();
        JsonParser $$2 = new JsonParser();
        try {
            JsonElement $$3 = $$2.parse(p_167322_);
            JsonObject $$4 = $$3.getAsJsonObject();
            $$1.f_167318_ = JsonUtils.m_90157_("periodInMillis", $$4, -1L);
            JsonElement $$5 = $$4.get("playerActivityDto");
            if ($$5 != null && $$5.isJsonArray()) {
                JsonArray $$6 = $$5.getAsJsonArray();
                for (JsonElement $$7 : $$6) {
                    ServerActivity $$8 = ServerActivity.m_167316_($$7.getAsJsonObject());
                    $$1.f_167319_.add($$8);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return $$1;
    }
}


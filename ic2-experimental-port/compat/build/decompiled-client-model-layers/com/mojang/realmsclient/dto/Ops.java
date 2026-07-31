/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.Set;

public class Ops
extends ValueObject {
    public Set<String> f_87418_ = Sets.newHashSet();

    public static Ops m_87420_(String p_87421_) {
        Ops $$1 = new Ops();
        JsonParser $$2 = new JsonParser();
        try {
            JsonElement $$3 = $$2.parse(p_87421_);
            JsonObject $$4 = $$3.getAsJsonObject();
            JsonElement $$5 = $$4.get("ops");
            if ($$5.isJsonArray()) {
                for (JsonElement $$6 : $$5.getAsJsonArray()) {
                    $$1.f_87418_.add($$6.getAsString());
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return $$1;
    }
}


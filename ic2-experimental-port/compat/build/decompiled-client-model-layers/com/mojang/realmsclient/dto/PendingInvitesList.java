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
import com.mojang.realmsclient.dto.PendingInvite;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;

public class PendingInvitesList
extends ValueObject {
    private static final Logger f_87433_ = LogUtils.getLogger();
    public List<PendingInvite> f_87432_ = Lists.newArrayList();

    public static PendingInvitesList m_87436_(String p_87437_) {
        PendingInvitesList $$1 = new PendingInvitesList();
        try {
            JsonParser $$2 = new JsonParser();
            JsonObject $$3 = $$2.parse(p_87437_).getAsJsonObject();
            if ($$3.get("invites").isJsonArray()) {
                Iterator $$4 = $$3.get("invites").getAsJsonArray().iterator();
                while ($$4.hasNext()) {
                    $$1.f_87432_.add(PendingInvite.m_87430_(((JsonElement)$$4.next()).getAsJsonObject()));
                }
            }
        }
        catch (Exception $$5) {
            f_87433_.error("Could not parse PendingInvitesList: {}", (Object)$$5.getMessage());
        }
        return $$1;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import org.slf4j.Logger;

public class RealmsNews
extends ValueObject {
    private static final Logger f_87468_ = LogUtils.getLogger();
    public String f_87467_;

    public static RealmsNews m_87471_(String p_87472_) {
        RealmsNews $$1 = new RealmsNews();
        try {
            JsonParser $$2 = new JsonParser();
            JsonObject $$3 = $$2.parse(p_87472_).getAsJsonObject();
            $$1.f_87467_ = JsonUtils.m_90161_("newsLink", $$3, null);
        }
        catch (Exception $$4) {
            f_87468_.error("Could not parse RealmsNews: {}", (Object)$$4.getMessage());
        }
        return $$1;
    }
}


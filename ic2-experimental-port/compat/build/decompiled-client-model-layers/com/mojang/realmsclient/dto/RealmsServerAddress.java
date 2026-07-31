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

public class RealmsServerAddress
extends ValueObject {
    private static final Logger f_87568_ = LogUtils.getLogger();
    public String f_87565_;
    public String f_87566_;
    public String f_87567_;

    public static RealmsServerAddress m_87571_(String p_87572_) {
        JsonParser $$1 = new JsonParser();
        RealmsServerAddress $$2 = new RealmsServerAddress();
        try {
            JsonObject $$3 = $$1.parse(p_87572_).getAsJsonObject();
            $$2.f_87565_ = JsonUtils.m_90161_("address", $$3, null);
            $$2.f_87566_ = JsonUtils.m_90161_("resourcePackUrl", $$3, null);
            $$2.f_87567_ = JsonUtils.m_90161_("resourcePackHash", $$3, null);
        }
        catch (Exception $$4) {
            f_87568_.error("Could not parse RealmsServerAddress: {}", (Object)$$4.getMessage());
        }
        return $$2;
    }
}


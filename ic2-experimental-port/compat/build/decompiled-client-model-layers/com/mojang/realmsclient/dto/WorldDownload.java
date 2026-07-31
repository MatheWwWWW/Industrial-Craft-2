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

public class WorldDownload
extends ValueObject {
    private static final Logger f_87721_ = LogUtils.getLogger();
    public String f_87718_;
    public String f_87719_;
    public String f_87720_;

    public static WorldDownload m_87724_(String p_87725_) {
        JsonParser $$1 = new JsonParser();
        JsonObject $$2 = $$1.parse(p_87725_).getAsJsonObject();
        WorldDownload $$3 = new WorldDownload();
        try {
            $$3.f_87718_ = JsonUtils.m_90161_("downloadLink", $$2, "");
            $$3.f_87719_ = JsonUtils.m_90161_("resourcePackUrl", $$2, "");
            $$3.f_87720_ = JsonUtils.m_90161_("resourcePackHash", $$2, "");
        }
        catch (Exception $$4) {
            f_87721_.error("Could not parse WorldDownload: {}", (Object)$$4.getMessage());
        }
        return $$3;
    }
}


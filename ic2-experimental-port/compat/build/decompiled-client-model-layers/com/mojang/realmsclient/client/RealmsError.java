/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.client;

import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.util.JsonUtils;
import javax.annotation.Nullable;
import org.slf4j.Logger;

public class RealmsError {
    private static final Logger f_87295_ = LogUtils.getLogger();
    private final String f_87296_;
    private final int f_87297_;

    private RealmsError(String p_87300_, int p_87301_) {
        this.f_87296_ = p_87300_;
        this.f_87297_ = p_87301_;
    }

    @Nullable
    public static RealmsError m_87303_(String p_87304_) {
        if (Strings.isNullOrEmpty((String)p_87304_)) {
            return null;
        }
        try {
            JsonObject $$1 = JsonParser.parseString((String)p_87304_).getAsJsonObject();
            String $$2 = JsonUtils.m_90161_("errorMsg", $$1, "");
            int $$3 = JsonUtils.m_90153_("errorCode", $$1, -1);
            return new RealmsError($$2, $$3);
        }
        catch (Exception $$4) {
            f_87295_.error("Could not parse RealmsError: {}", (Object)$$4.getMessage());
            f_87295_.error("The error was: {}", (Object)p_87304_);
            return null;
        }
    }

    public String m_87302_() {
        return this.f_87296_;
    }

    public int m_87305_() {
        return this.f_87297_;
    }
}


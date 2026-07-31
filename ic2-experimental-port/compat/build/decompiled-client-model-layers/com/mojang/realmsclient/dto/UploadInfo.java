/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.dto;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.slf4j.Logger;

public class UploadInfo
extends ValueObject {
    private static final Logger f_87686_ = LogUtils.getLogger();
    private static final String f_167324_ = "http://";
    private static final int f_167325_ = 8080;
    private static final Pattern f_87687_ = Pattern.compile("^[a-zA-Z][-a-zA-Z0-9+.]+:");
    private final boolean f_87688_;
    @Nullable
    private final String f_87689_;
    private final URI f_87690_;

    private UploadInfo(boolean p_87693_, @Nullable String p_87694_, URI p_87695_) {
        this.f_87688_ = p_87693_;
        this.f_87689_ = p_87694_;
        this.f_87690_ = p_87695_;
    }

    @Nullable
    public static UploadInfo m_87700_(String p_87701_) {
        try {
            int $$4;
            URI $$5;
            JsonParser $$1 = new JsonParser();
            JsonObject $$2 = $$1.parse(p_87701_).getAsJsonObject();
            String $$3 = JsonUtils.m_90161_("uploadEndpoint", $$2, null);
            if ($$3 != null && ($$5 = UploadInfo.m_87702_($$3, $$4 = JsonUtils.m_90153_("port", $$2, -1))) != null) {
                boolean $$6 = JsonUtils.m_90165_("worldClosed", $$2, false);
                String $$7 = JsonUtils.m_90161_("token", $$2, null);
                return new UploadInfo($$6, $$7, $$5);
            }
        }
        catch (Exception $$8) {
            f_87686_.error("Could not parse UploadInfo: {}", (Object)$$8.getMessage());
        }
        return null;
    }

    @Nullable
    @VisibleForTesting
    public static URI m_87702_(String p_87703_, int p_87704_) {
        Matcher $$2 = f_87687_.matcher(p_87703_);
        String $$3 = UploadInfo.m_87705_(p_87703_, $$2);
        try {
            URI $$4 = new URI($$3);
            int $$5 = UploadInfo.m_87697_(p_87704_, $$4.getPort());
            if ($$5 != $$4.getPort()) {
                return new URI($$4.getScheme(), $$4.getUserInfo(), $$4.getHost(), $$5, $$4.getPath(), $$4.getQuery(), $$4.getFragment());
            }
            return $$4;
        }
        catch (URISyntaxException $$6) {
            f_87686_.warn("Failed to parse URI {}", (Object)$$3, (Object)$$6);
            return null;
        }
    }

    private static int m_87697_(int p_87698_, int p_87699_) {
        if (p_87698_ != -1) {
            return p_87698_;
        }
        if (p_87699_ != -1) {
            return p_87699_;
        }
        return 8080;
    }

    private static String m_87705_(String p_87706_, Matcher p_87707_) {
        if (p_87707_.find()) {
            return p_87706_;
        }
        return f_167324_ + p_87706_;
    }

    public static String m_87709_(@Nullable String p_87710_) {
        JsonObject $$1 = new JsonObject();
        if (p_87710_ != null) {
            $$1.addProperty("token", p_87710_);
        }
        return $$1.toString();
    }

    @Nullable
    public String m_87696_() {
        return this.f_87689_;
    }

    public URI m_87708_() {
        return this.f_87690_;
    }

    public boolean m_87711_() {
        return this.f_87688_;
    }
}


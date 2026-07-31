/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package com.mojang.realmsclient.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Date;

public class JsonUtils {
    public static String m_90161_(String p_90162_, JsonObject p_90163_, String p_90164_) {
        JsonElement $$3 = p_90163_.get(p_90162_);
        if ($$3 != null) {
            return $$3.isJsonNull() ? p_90164_ : $$3.getAsString();
        }
        return p_90164_;
    }

    public static int m_90153_(String p_90154_, JsonObject p_90155_, int p_90156_) {
        JsonElement $$3 = p_90155_.get(p_90154_);
        if ($$3 != null) {
            return $$3.isJsonNull() ? p_90156_ : $$3.getAsInt();
        }
        return p_90156_;
    }

    public static long m_90157_(String p_90158_, JsonObject p_90159_, long p_90160_) {
        JsonElement $$3 = p_90159_.get(p_90158_);
        if ($$3 != null) {
            return $$3.isJsonNull() ? p_90160_ : $$3.getAsLong();
        }
        return p_90160_;
    }

    public static boolean m_90165_(String p_90166_, JsonObject p_90167_, boolean p_90168_) {
        JsonElement $$3 = p_90167_.get(p_90166_);
        if ($$3 != null) {
            return $$3.isJsonNull() ? p_90168_ : $$3.getAsBoolean();
        }
        return p_90168_;
    }

    public static Date m_90150_(String p_90151_, JsonObject p_90152_) {
        JsonElement $$2 = p_90152_.get(p_90151_);
        if ($$2 != null) {
            return new Date(Long.parseLong($$2.getAsString()));
        }
        return new Date();
    }
}


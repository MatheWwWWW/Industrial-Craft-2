/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  javax.annotation.Nullable
 */
package com.mojang.realmsclient.dto;

import com.google.gson.Gson;
import com.mojang.realmsclient.dto.ReflectionBasedSerialization;
import javax.annotation.Nullable;

public class GuardedSerializer {
    private final Gson f_87411_ = new Gson();

    public String m_87413_(ReflectionBasedSerialization p_87414_) {
        return this.f_87411_.toJson((Object)p_87414_);
    }

    @Nullable
    public <T extends ReflectionBasedSerialization> T m_87415_(String p_87416_, Class<T> p_87417_) {
        return (T)((ReflectionBasedSerialization)this.f_87411_.fromJson(p_87416_, p_87417_));
    }
}


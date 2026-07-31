/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 */
package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.dto.ReflectionBasedSerialization;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.Locale;

public class RegionPingResult
extends ValueObject
implements ReflectionBasedSerialization {
    @SerializedName(value="regionName")
    private final String f_87647_;
    @SerializedName(value="ping")
    private final int f_87648_;

    public RegionPingResult(String p_87650_, int p_87651_) {
        this.f_87647_ = p_87650_;
        this.f_87648_ = p_87651_;
    }

    public int m_87652_() {
        return this.f_87648_;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s --> %.2f ms", this.f_87647_, Float.valueOf(this.f_87648_));
    }
}


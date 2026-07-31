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

public class RealmsWorldResetDto
extends ValueObject
implements ReflectionBasedSerialization {
    @SerializedName(value="seed")
    private final String f_87638_;
    @SerializedName(value="worldTemplateId")
    private final long f_87639_;
    @SerializedName(value="levelType")
    private final int f_87640_;
    @SerializedName(value="generateStructures")
    private final boolean f_87641_;

    public RealmsWorldResetDto(String p_87643_, long p_87644_, int p_87645_, boolean p_87646_) {
        this.f_87638_ = p_87643_;
        this.f_87639_ = p_87644_;
        this.f_87640_ = p_87645_;
        this.f_87641_ = p_87646_;
    }
}


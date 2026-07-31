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

public class RealmsDescriptionDto
extends ValueObject
implements ReflectionBasedSerialization {
    @SerializedName(value="name")
    public String f_87462_;
    @SerializedName(value="description")
    public String f_87463_;

    public RealmsDescriptionDto(String p_87465_, String p_87466_) {
        this.f_87462_ = p_87465_;
        this.f_87463_ = p_87466_;
    }
}


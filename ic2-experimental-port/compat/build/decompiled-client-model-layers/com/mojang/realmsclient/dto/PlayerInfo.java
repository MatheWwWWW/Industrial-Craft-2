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

public class PlayerInfo
extends ValueObject
implements ReflectionBasedSerialization {
    @SerializedName(value="name")
    private String f_87441_;
    @SerializedName(value="uuid")
    private String f_87442_;
    @SerializedName(value="operator")
    private boolean f_87443_;
    @SerializedName(value="accepted")
    private boolean f_87444_;
    @SerializedName(value="online")
    private boolean f_87445_;

    public String m_87447_() {
        return this.f_87441_;
    }

    public void m_87448_(String p_87449_) {
        this.f_87441_ = p_87449_;
    }

    public String m_87452_() {
        return this.f_87442_;
    }

    public void m_87453_(String p_87454_) {
        this.f_87442_ = p_87454_;
    }

    public boolean m_87457_() {
        return this.f_87443_;
    }

    public void m_87450_(boolean p_87451_) {
        this.f_87443_ = p_87451_;
    }

    public boolean m_87460_() {
        return this.f_87444_;
    }

    public void m_87455_(boolean p_87456_) {
        this.f_87444_ = p_87456_;
    }

    public boolean m_87461_() {
        return this.f_87445_;
    }

    public void m_87458_(boolean p_87459_) {
        this.f_87445_ = p_87459_;
    }
}


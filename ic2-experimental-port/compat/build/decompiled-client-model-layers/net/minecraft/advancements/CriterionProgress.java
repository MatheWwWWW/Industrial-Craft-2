/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;

public class CriterionProgress {
    private static final SimpleDateFormat f_12907_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ROOT);
    @Nullable
    private Date f_12908_;

    public boolean m_12911_() {
        return this.f_12908_ != null;
    }

    public void m_12916_() {
        this.f_12908_ = new Date();
    }

    public void m_12919_() {
        this.f_12908_ = null;
    }

    @Nullable
    public Date m_12920_() {
        return this.f_12908_;
    }

    public String toString() {
        return "CriterionProgress{obtained=" + (Serializable)(this.f_12908_ == null ? "false" : this.f_12908_) + "}";
    }

    public void m_12914_(FriendlyByteBuf p_12915_) {
        p_12915_.m_236821_(this.f_12908_, FriendlyByteBuf::m_130075_);
    }

    public JsonElement m_12921_() {
        if (this.f_12908_ != null) {
            return new JsonPrimitive(f_12907_.format(this.f_12908_));
        }
        return JsonNull.INSTANCE;
    }

    public static CriterionProgress m_12917_(FriendlyByteBuf p_12918_) {
        CriterionProgress $$1 = new CriterionProgress();
        $$1.f_12908_ = (Date)p_12918_.m_236868_(FriendlyByteBuf::m_130282_);
        return $$1;
    }

    public static CriterionProgress m_12912_(String p_12913_) {
        CriterionProgress $$1 = new CriterionProgress();
        try {
            $$1.f_12908_ = f_12907_.parse(p_12913_);
        }
        catch (ParseException $$2) {
            throw new JsonSyntaxException("Invalid datetime: " + p_12913_, (Throwable)$$2);
        }
        return $$1;
    }
}


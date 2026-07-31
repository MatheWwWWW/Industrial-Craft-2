/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Date;
import org.slf4j.Logger;

public class PendingInvite
extends ValueObject {
    private static final Logger f_87427_ = LogUtils.getLogger();
    public String f_87422_;
    public String f_87423_;
    public String f_87424_;
    public String f_87425_;
    public Date f_87426_;

    public static PendingInvite m_87430_(JsonObject p_87431_) {
        PendingInvite $$1 = new PendingInvite();
        try {
            $$1.f_87422_ = JsonUtils.m_90161_("invitationId", p_87431_, "");
            $$1.f_87423_ = JsonUtils.m_90161_("worldName", p_87431_, "");
            $$1.f_87424_ = JsonUtils.m_90161_("worldOwnerName", p_87431_, "");
            $$1.f_87425_ = JsonUtils.m_90161_("worldOwnerUuid", p_87431_, "");
            $$1.f_87426_ = JsonUtils.m_90150_("date", p_87431_);
        }
        catch (Exception $$2) {
            f_87427_.error("Could not parse PendingInvite: {}", (Object)$$2.getMessage());
        }
        return $$1;
    }
}


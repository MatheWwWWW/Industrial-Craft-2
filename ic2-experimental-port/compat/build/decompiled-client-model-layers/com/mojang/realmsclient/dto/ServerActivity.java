/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;

public class ServerActivity
extends ValueObject {
    public String f_167312_;
    public long f_167313_;
    public long f_167314_;

    public static ServerActivity m_167316_(JsonObject p_167317_) {
        ServerActivity $$1 = new ServerActivity();
        try {
            $$1.f_167312_ = JsonUtils.m_90161_("profileUuid", p_167317_, null);
            $$1.f_167313_ = JsonUtils.m_90157_("joinTime", p_167317_, Long.MIN_VALUE);
            $$1.f_167314_ = JsonUtils.m_90157_("leaveTime", p_167317_, Long.MIN_VALUE);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return $$1;
    }
}


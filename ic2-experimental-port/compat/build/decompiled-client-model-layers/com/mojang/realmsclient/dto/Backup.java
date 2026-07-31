/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;

public class Backup
extends ValueObject {
    private static final Logger f_87394_ = LogUtils.getLogger();
    public String f_87389_;
    public Date f_87390_;
    public long f_87391_;
    private boolean f_87395_;
    public Map<String, String> f_87392_ = Maps.newHashMap();
    public Map<String, String> f_87393_ = Maps.newHashMap();

    public static Backup m_87399_(JsonElement p_87400_) {
        JsonObject $$1 = p_87400_.getAsJsonObject();
        Backup $$2 = new Backup();
        try {
            $$2.f_87389_ = JsonUtils.m_90161_("backupId", $$1, "");
            $$2.f_87390_ = JsonUtils.m_90150_("lastModifiedDate", $$1);
            $$2.f_87391_ = JsonUtils.m_90157_("size", $$1, 0L);
            if ($$1.has("metadata")) {
                JsonObject $$3 = $$1.getAsJsonObject("metadata");
                Set $$4 = $$3.entrySet();
                for (Map.Entry $$5 : $$4) {
                    if (((JsonElement)$$5.getValue()).isJsonNull()) continue;
                    $$2.f_87392_.put(Backup.m_87401_((String)$$5.getKey()), ((JsonElement)$$5.getValue()).getAsString());
                }
            }
        }
        catch (Exception $$6) {
            f_87394_.error("Could not parse Backup: {}", (Object)$$6.getMessage());
        }
        return $$2;
    }

    private static String m_87401_(String p_87402_) {
        String[] $$1 = p_87402_.split("_");
        StringBuilder $$2 = new StringBuilder();
        for (String $$3 : $$1) {
            if ($$3 == null || $$3.length() < 1) continue;
            if ("of".equals($$3)) {
                $$2.append($$3).append(" ");
                continue;
            }
            char $$4 = Character.toUpperCase($$3.charAt(0));
            $$2.append($$4).append($$3.substring(1)).append(" ");
        }
        return $$2.toString();
    }

    public boolean m_87398_() {
        return this.f_87395_;
    }

    public void m_87403_(boolean p_87404_) {
        this.f_87395_ = p_87404_;
    }
}


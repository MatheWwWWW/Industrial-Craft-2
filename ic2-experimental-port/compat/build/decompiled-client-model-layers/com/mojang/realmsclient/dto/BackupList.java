/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParser
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.Backup;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;

public class BackupList
extends ValueObject {
    private static final Logger f_87406_ = LogUtils.getLogger();
    public List<Backup> f_87405_;

    public static BackupList m_87409_(String p_87410_) {
        JsonParser $$1 = new JsonParser();
        BackupList $$2 = new BackupList();
        $$2.f_87405_ = Lists.newArrayList();
        try {
            JsonElement $$3 = $$1.parse(p_87410_).getAsJsonObject().get("backups");
            if ($$3.isJsonArray()) {
                Iterator $$4 = $$3.getAsJsonArray().iterator();
                while ($$4.hasNext()) {
                    $$2.f_87405_.add(Backup.m_87399_((JsonElement)$$4.next()));
                }
            }
        }
        catch (Exception $$5) {
            f_87406_.error("Could not parse BackupList: {}", (Object)$$5.getMessage());
        }
        return $$2;
    }
}


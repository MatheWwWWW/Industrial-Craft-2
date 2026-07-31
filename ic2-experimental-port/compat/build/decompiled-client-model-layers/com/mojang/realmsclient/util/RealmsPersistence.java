/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 *  com.mojang.logging.LogUtils
 *  org.apache.commons.io.FileUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.util;

import com.google.gson.annotations.SerializedName;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.GuardedSerializer;
import com.mojang.realmsclient.dto.ReflectionBasedSerialization;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

public class RealmsPersistence {
    private static final String f_167613_ = "realms_persistence.json";
    private static final GuardedSerializer f_90169_ = new GuardedSerializer();
    private static final Logger f_240227_ = LogUtils.getLogger();

    public RealmsPersistenceData m_167615_() {
        return RealmsPersistence.m_90171_();
    }

    public void m_167616_(RealmsPersistenceData p_167617_) {
        RealmsPersistence.m_90172_(p_167617_);
    }

    public static RealmsPersistenceData m_90171_() {
        File $$0 = RealmsPersistence.m_90174_();
        try {
            String $$1 = FileUtils.readFileToString((File)$$0, (Charset)StandardCharsets.UTF_8);
            RealmsPersistenceData $$2 = f_90169_.m_87415_($$1, RealmsPersistenceData.class);
            if ($$2 != null) {
                return $$2;
            }
        }
        catch (FileNotFoundException $$1) {
        }
        catch (Exception $$3) {
            f_240227_.warn("Failed to read Realms storage {}", (Object)$$0, (Object)$$3);
        }
        return new RealmsPersistenceData();
    }

    public static void m_90172_(RealmsPersistenceData p_90173_) {
        File $$1 = RealmsPersistence.m_90174_();
        try {
            FileUtils.writeStringToFile((File)$$1, (String)f_90169_.m_87413_(p_90173_), (Charset)StandardCharsets.UTF_8);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private static File m_90174_() {
        return new File(Minecraft.m_91087_().f_91069_, f_167613_);
    }

    public static class RealmsPersistenceData
    implements ReflectionBasedSerialization {
        @SerializedName(value="newsLink")
        public String f_90175_;
        @SerializedName(value="hasUnreadNews")
        public boolean f_90176_;
    }
}


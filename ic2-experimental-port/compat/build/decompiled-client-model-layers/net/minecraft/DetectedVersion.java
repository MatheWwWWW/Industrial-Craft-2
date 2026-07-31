/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.mojang.bridge.game.PackType
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.bridge.game.PackType;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import net.minecraft.SharedConstants;
import net.minecraft.WorldVersion;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.DataVersion;
import org.slf4j.Logger;

public class DetectedVersion
implements WorldVersion {
    private static final Logger f_132477_ = LogUtils.getLogger();
    public static final WorldVersion f_132476_ = new DetectedVersion();
    private final String f_132478_;
    private final String f_132479_;
    private final boolean f_132480_;
    private final DataVersion f_132481_;
    private final int f_132482_;
    private final int f_179761_;
    private final int f_179762_;
    private final Date f_132484_;
    private final String f_132485_;

    private DetectedVersion() {
        this.f_132478_ = UUID.randomUUID().toString().replaceAll("-", "");
        this.f_132479_ = "1.19.2";
        this.f_132480_ = true;
        this.f_132481_ = new DataVersion(3120, "main");
        this.f_132482_ = SharedConstants.m_136192_();
        this.f_179761_ = 9;
        this.f_179762_ = 10;
        this.f_132484_ = new Date();
        this.f_132485_ = "1.19.2";
    }

    private DetectedVersion(JsonObject p_132489_) {
        this.f_132478_ = GsonHelper.m_13906_(p_132489_, "id");
        this.f_132479_ = GsonHelper.m_13906_(p_132489_, "name");
        this.f_132485_ = GsonHelper.m_13906_(p_132489_, "release_target");
        this.f_132480_ = GsonHelper.m_13912_(p_132489_, "stable");
        this.f_132481_ = new DataVersion(GsonHelper.m_13927_(p_132489_, "world_version"), GsonHelper.m_13851_(p_132489_, "series_id", DataVersion.f_192993_));
        this.f_132482_ = GsonHelper.m_13927_(p_132489_, "protocol_version");
        JsonObject $$1 = GsonHelper.m_13930_(p_132489_, "pack_version");
        this.f_179761_ = GsonHelper.m_13927_($$1, "resource");
        this.f_179762_ = GsonHelper.m_13927_($$1, "data");
        this.f_132484_ = Date.from(ZonedDateTime.parse(GsonHelper.m_13906_(p_132489_, "build_time")).toInstant());
    }

    /*
     * Enabled aggressive exception aggregation
     */
    public static WorldVersion m_195834_() {
        try (InputStream $$0 = DetectedVersion.class.getResourceAsStream("/version.json");){
            DetectedVersion detectedVersion;
            if ($$0 == null) {
                f_132477_.warn("Missing version information!");
                WorldVersion worldVersion = f_132476_;
                return worldVersion;
            }
            try (InputStreamReader $$1 = new InputStreamReader($$0);){
                detectedVersion = new DetectedVersion(GsonHelper.m_13859_($$1));
            }
            return detectedVersion;
        }
        catch (JsonParseException | IOException $$2) {
            throw new IllegalStateException("Game version information is corrupt", $$2);
        }
    }

    public String getId() {
        return this.f_132478_;
    }

    public String getName() {
        return this.f_132479_;
    }

    public String getReleaseTarget() {
        return this.f_132485_;
    }

    @Override
    public DataVersion m_183476_() {
        return this.f_132481_;
    }

    public int getProtocolVersion() {
        return this.f_132482_;
    }

    public int getPackVersion(PackType p_179764_) {
        return p_179764_ == PackType.DATA ? this.f_179762_ : this.f_179761_;
    }

    public Date getBuildTime() {
        return this.f_132484_;
    }

    public boolean isStable() {
        return this.f_132480_;
    }
}


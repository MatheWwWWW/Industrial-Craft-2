/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import javax.annotation.Nullable;
import org.slf4j.Logger;

public class WorldTemplate
extends ValueObject {
    private static final Logger f_87735_ = LogUtils.getLogger();
    public String f_87726_ = "";
    public String f_87727_ = "";
    public String f_87728_ = "";
    public String f_87729_ = "";
    public String f_87730_ = "";
    @Nullable
    public String f_87731_;
    public String f_87732_ = "";
    public String f_87733_ = "";
    public WorldTemplateType f_87734_ = WorldTemplateType.WORLD_TEMPLATE;

    public static WorldTemplate m_87738_(JsonObject p_87739_) {
        WorldTemplate $$1 = new WorldTemplate();
        try {
            $$1.f_87726_ = JsonUtils.m_90161_("id", p_87739_, "");
            $$1.f_87727_ = JsonUtils.m_90161_("name", p_87739_, "");
            $$1.f_87728_ = JsonUtils.m_90161_("version", p_87739_, "");
            $$1.f_87729_ = JsonUtils.m_90161_("author", p_87739_, "");
            $$1.f_87730_ = JsonUtils.m_90161_("link", p_87739_, "");
            $$1.f_87731_ = JsonUtils.m_90161_("image", p_87739_, null);
            $$1.f_87732_ = JsonUtils.m_90161_("trailer", p_87739_, "");
            $$1.f_87733_ = JsonUtils.m_90161_("recommendedPlayers", p_87739_, "");
            $$1.f_87734_ = WorldTemplateType.valueOf(JsonUtils.m_90161_("type", p_87739_, WorldTemplateType.WORLD_TEMPLATE.name()));
        }
        catch (Exception $$2) {
            f_87735_.error("Could not parse WorldTemplate: {}", (Object)$$2.getMessage());
        }
        return $$1;
    }

    public static final class WorldTemplateType
    extends Enum<WorldTemplateType> {
        public static final /* enum */ WorldTemplateType WORLD_TEMPLATE = new WorldTemplateType();
        public static final /* enum */ WorldTemplateType MINIGAME = new WorldTemplateType();
        public static final /* enum */ WorldTemplateType ADVENTUREMAP = new WorldTemplateType();
        public static final /* enum */ WorldTemplateType EXPERIENCE = new WorldTemplateType();
        public static final /* enum */ WorldTemplateType INSPIRATION = new WorldTemplateType();
        private static final /* synthetic */ WorldTemplateType[] $VALUES;

        public static WorldTemplateType[] values() {
            return (WorldTemplateType[])$VALUES.clone();
        }

        public static WorldTemplateType valueOf(String p_87751_) {
            return Enum.valueOf(WorldTemplateType.class, p_87751_);
        }

        private static /* synthetic */ WorldTemplateType[] m_167326_() {
            return new WorldTemplateType[]{WORLD_TEMPLATE, MINIGAME, ADVENTUREMAP, EXPERIENCE, INSPIRATION};
        }

        static {
            $VALUES = WorldTemplateType.m_167326_();
        }
    }
}


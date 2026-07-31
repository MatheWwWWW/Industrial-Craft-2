/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.gameevent.GameEvent;

public class GameEventTags {
    public static final TagKey<GameEvent> f_144302_ = GameEventTags.m_203852_("vibrations");
    public static final TagKey<GameEvent> f_215853_ = GameEventTags.m_203852_("warden_can_listen");
    public static final TagKey<GameEvent> f_215854_ = GameEventTags.m_203852_("shrieker_can_listen");
    public static final TagKey<GameEvent> f_144303_ = GameEventTags.m_203852_("ignore_vibrations_sneaking");
    public static final TagKey<GameEvent> f_215855_ = GameEventTags.m_203852_("allay_can_listen");

    private static TagKey<GameEvent> m_203852_(String p_203853_) {
        return TagKey.m_203882_(Registry.f_175423_, new ResourceLocation(p_203853_));
    }
}


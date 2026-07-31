/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.util;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;

public final class LevelType
extends Enum<LevelType> {
    public static final /* enum */ LevelType DEFAULT = new LevelType(0, WorldPresets.f_226437_);
    public static final /* enum */ LevelType FLAT = new LevelType(1, WorldPresets.f_226438_);
    public static final /* enum */ LevelType LARGE_BIOMES = new LevelType(2, WorldPresets.f_226439_);
    public static final /* enum */ LevelType AMPLIFIED = new LevelType(3, WorldPresets.f_226440_);
    private final int f_167598_;
    private final Component f_167599_;
    private static final /* synthetic */ LevelType[] $VALUES;

    public static LevelType[] values() {
        return (LevelType[])$VALUES.clone();
    }

    public static LevelType valueOf(String p_167611_) {
        return Enum.valueOf(LevelType.class, p_167611_);
    }

    private LevelType(int p_239483_, ResourceKey<WorldPreset> p_239484_) {
        this.f_167598_ = p_239483_;
        this.f_167599_ = Component.m_237115_(p_239484_.m_135782_().m_214296_("generator"));
    }

    public Component m_167607_() {
        return this.f_167599_;
    }

    public int m_167608_() {
        return this.f_167598_;
    }

    private static /* synthetic */ LevelType[] m_167609_() {
        return new LevelType[]{DEFAULT, FLAT, LARGE_BIOMES, AMPLIFIED};
    }

    static {
        $VALUES = LevelType.m_167609_();
    }
}


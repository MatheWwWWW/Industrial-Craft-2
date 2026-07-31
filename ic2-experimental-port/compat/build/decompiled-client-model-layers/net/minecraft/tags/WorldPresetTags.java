/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.presets.WorldPreset;

public class WorldPresetTags {
    public static final TagKey<WorldPreset> f_216053_ = WorldPresetTags.m_216057_("normal");
    public static final TagKey<WorldPreset> f_216054_ = WorldPresetTags.m_216057_("extended");

    private WorldPresetTags() {
    }

    private static TagKey<WorldPreset> m_216057_(String p_216058_) {
        return TagKey.m_203882_(Registry.f_235726_, new ResourceLocation(p_216058_));
    }
}


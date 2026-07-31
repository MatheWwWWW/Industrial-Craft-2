/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.tags;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.WorldPresetTags;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;

public class WorldPresetTagsProvider
extends TagsProvider<WorldPreset> {
    public WorldPresetTagsProvider(DataGenerator p_236457_) {
        super(p_236457_, BuiltinRegistries.f_235989_);
    }

    @Override
    protected void m_6577_() {
        this.m_206424_(WorldPresetTags.f_216053_).m_211101_(WorldPresets.f_226437_).m_211101_(WorldPresets.f_226438_).m_211101_(WorldPresets.f_226439_).m_211101_(WorldPresets.f_226440_).m_211101_(WorldPresets.f_226441_);
        this.m_206424_(WorldPresetTags.f_216054_).m_206428_(WorldPresetTags.f_216053_).m_211101_(WorldPresets.f_226442_);
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.tags;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;

public class PoiTypeTagsProvider
extends TagsProvider<PoiType> {
    public PoiTypeTagsProvider(DataGenerator p_236434_) {
        super(p_236434_, Registry.f_122870_);
    }

    @Override
    protected void m_6577_() {
        this.m_206424_(PoiTypeTags.f_215875_).m_211101_(PoiTypes.f_218047_, PoiTypes.f_218048_, PoiTypes.f_218049_, PoiTypes.f_218050_, PoiTypes.f_218051_, PoiTypes.f_218052_, PoiTypes.f_218053_, PoiTypes.f_218054_, PoiTypes.f_218055_, PoiTypes.f_218056_, PoiTypes.f_218057_, PoiTypes.f_218058_, PoiTypes.f_218059_);
        this.m_206424_(PoiTypeTags.f_215876_).m_206428_(PoiTypeTags.f_215875_).m_211101_(PoiTypes.f_218060_, PoiTypes.f_218061_);
        this.m_206424_(PoiTypeTags.f_215877_).m_211101_(PoiTypes.f_218062_, PoiTypes.f_218063_);
    }
}


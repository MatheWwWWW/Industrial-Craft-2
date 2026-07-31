/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Instrument;

public interface InstrumentTags {
    public static final TagKey<Instrument> f_215856_ = InstrumentTags.m_215860_("regular_goat_horns");
    public static final TagKey<Instrument> f_215857_ = InstrumentTags.m_215860_("screaming_goat_horns");
    public static final TagKey<Instrument> f_215858_ = InstrumentTags.m_215860_("goat_horns");

    private static TagKey<Instrument> m_215860_(String p_215861_) {
        return TagKey.m_203882_(Registry.f_235737_, new ResourceLocation(p_215861_));
    }
}


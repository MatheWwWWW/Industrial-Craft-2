/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class PoiTypeTags {
    public static final TagKey<PoiType> f_215875_ = PoiTypeTags.m_215880_("acquirable_job_site");
    public static final TagKey<PoiType> f_215876_ = PoiTypeTags.m_215880_("village");
    public static final TagKey<PoiType> f_215877_ = PoiTypeTags.m_215880_("bee_home");

    private PoiTypeTags() {
    }

    private static TagKey<PoiType> m_215880_(String p_215881_) {
        return TagKey.m_203882_(Registry.f_122810_, new ResourceLocation(p_215881_));
    }
}


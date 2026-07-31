/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.CatVariant;

public class CatVariantTags {
    public static final TagKey<CatVariant> f_215841_ = CatVariantTags.m_215845_("default_spawns");
    public static final TagKey<CatVariant> f_215842_ = CatVariantTags.m_215845_("full_moon_spawns");

    private CatVariantTags() {
    }

    private static TagKey<CatVariant> m_215845_(String p_215846_) {
        return TagKey.m_203882_(Registry.f_235731_, new ResourceLocation(p_215846_));
    }
}


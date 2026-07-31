/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class PaintingVariantTags {
    public static final TagKey<PaintingVariant> f_215870_ = PaintingVariantTags.m_215873_("placeable");

    private PaintingVariantTags() {
    }

    private static TagKey<PaintingVariant> m_215873_(String p_215874_) {
        return TagKey.m_203882_(Registry.f_235743_, new ResourceLocation(p_215874_));
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public final class FluidTags {
    public static final TagKey<Fluid> f_13131_ = FluidTags.m_203850_("water");
    public static final TagKey<Fluid> f_13132_ = FluidTags.m_203850_("lava");

    private FluidTags() {
    }

    private static TagKey<Fluid> m_203850_(String p_203851_) {
        return TagKey.m_203882_(Registry.f_122899_, new ResourceLocation(p_203851_));
    }
}


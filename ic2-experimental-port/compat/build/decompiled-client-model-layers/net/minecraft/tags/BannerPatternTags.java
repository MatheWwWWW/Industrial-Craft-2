/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class BannerPatternTags {
    public static final TagKey<BannerPattern> f_215788_ = BannerPatternTags.m_215797_("no_item_required");
    public static final TagKey<BannerPattern> f_215789_ = BannerPatternTags.m_215797_("pattern_item/flower");
    public static final TagKey<BannerPattern> f_215790_ = BannerPatternTags.m_215797_("pattern_item/creeper");
    public static final TagKey<BannerPattern> f_215791_ = BannerPatternTags.m_215797_("pattern_item/skull");
    public static final TagKey<BannerPattern> f_215792_ = BannerPatternTags.m_215797_("pattern_item/mojang");
    public static final TagKey<BannerPattern> f_215793_ = BannerPatternTags.m_215797_("pattern_item/globe");
    public static final TagKey<BannerPattern> f_215794_ = BannerPatternTags.m_215797_("pattern_item/piglin");

    private BannerPatternTags() {
    }

    private static TagKey<BannerPattern> m_215797_(String p_215798_) {
        return TagKey.m_203882_(Registry.f_235735_, new ResourceLocation(p_215798_));
    }
}


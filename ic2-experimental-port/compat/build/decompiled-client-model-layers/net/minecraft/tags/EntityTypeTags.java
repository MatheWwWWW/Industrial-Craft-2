/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public final class EntityTypeTags {
    public static final TagKey<EntityType<?>> f_13120_ = EntityTypeTags.m_203848_("skeletons");
    public static final TagKey<EntityType<?>> f_13121_ = EntityTypeTags.m_203848_("raiders");
    public static final TagKey<EntityType<?>> f_13122_ = EntityTypeTags.m_203848_("beehive_inhabitors");
    public static final TagKey<EntityType<?>> f_13123_ = EntityTypeTags.m_203848_("arrows");
    public static final TagKey<EntityType<?>> f_13124_ = EntityTypeTags.m_203848_("impact_projectiles");
    public static final TagKey<EntityType<?>> f_144291_ = EntityTypeTags.m_203848_("powder_snow_walkable_mobs");
    public static final TagKey<EntityType<?>> f_144292_ = EntityTypeTags.m_203848_("axolotl_always_hostiles");
    public static final TagKey<EntityType<?>> f_144293_ = EntityTypeTags.m_203848_("axolotl_hunt_targets");
    public static final TagKey<EntityType<?>> f_144294_ = EntityTypeTags.m_203848_("freeze_immune_entity_types");
    public static final TagKey<EntityType<?>> f_144295_ = EntityTypeTags.m_203848_("freeze_hurts_extra_types");
    public static final TagKey<EntityType<?>> f_215847_ = EntityTypeTags.m_203848_("frog_food");

    private EntityTypeTags() {
    }

    private static TagKey<EntityType<?>> m_203848_(String p_203849_) {
        return TagKey.m_203882_(Registry.f_122903_, new ResourceLocation(p_203849_));
    }
}


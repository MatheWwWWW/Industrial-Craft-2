/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public interface StructureTags {
    public static final TagKey<Structure> f_215882_ = StructureTags.m_215895_("eye_of_ender_located");
    public static final TagKey<Structure> f_215883_ = StructureTags.m_215895_("dolphin_located");
    public static final TagKey<Structure> f_215884_ = StructureTags.m_215895_("on_woodland_explorer_maps");
    public static final TagKey<Structure> f_215885_ = StructureTags.m_215895_("on_ocean_explorer_maps");
    public static final TagKey<Structure> f_215886_ = StructureTags.m_215895_("on_treasure_maps");
    public static final TagKey<Structure> f_215887_ = StructureTags.m_215895_("cats_spawn_in");
    public static final TagKey<Structure> f_215888_ = StructureTags.m_215895_("cats_spawn_as_black");
    public static final TagKey<Structure> f_215889_ = StructureTags.m_215895_("village");
    public static final TagKey<Structure> f_215890_ = StructureTags.m_215895_("mineshaft");
    public static final TagKey<Structure> f_215891_ = StructureTags.m_215895_("shipwreck");
    public static final TagKey<Structure> f_215892_ = StructureTags.m_215895_("ruined_portal");
    public static final TagKey<Structure> f_215893_ = StructureTags.m_215895_("ocean_ruin");

    private static TagKey<Structure> m_215895_(String p_215896_) {
        return TagKey.m_203882_(Registry.f_235725_, new ResourceLocation(p_215896_));
    }
}


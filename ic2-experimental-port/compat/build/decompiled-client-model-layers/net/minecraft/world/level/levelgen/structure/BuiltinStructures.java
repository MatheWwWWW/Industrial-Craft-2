/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;

public interface BuiltinStructures {
    public static final ResourceKey<Structure> f_209845_ = BuiltinStructures.m_209872_("pillager_outpost");
    public static final ResourceKey<Structure> f_209846_ = BuiltinStructures.m_209872_("mineshaft");
    public static final ResourceKey<Structure> f_209847_ = BuiltinStructures.m_209872_("mineshaft_mesa");
    public static final ResourceKey<Structure> f_209848_ = BuiltinStructures.m_209872_("mansion");
    public static final ResourceKey<Structure> f_209849_ = BuiltinStructures.m_209872_("jungle_pyramid");
    public static final ResourceKey<Structure> f_209850_ = BuiltinStructures.m_209872_("desert_pyramid");
    public static final ResourceKey<Structure> f_209851_ = BuiltinStructures.m_209872_("igloo");
    public static final ResourceKey<Structure> f_209852_ = BuiltinStructures.m_209872_("shipwreck");
    public static final ResourceKey<Structure> f_209853_ = BuiltinStructures.m_209872_("shipwreck_beached");
    public static final ResourceKey<Structure> f_209854_ = BuiltinStructures.m_209872_("swamp_hut");
    public static final ResourceKey<Structure> f_209855_ = BuiltinStructures.m_209872_("stronghold");
    public static final ResourceKey<Structure> f_209856_ = BuiltinStructures.m_209872_("monument");
    public static final ResourceKey<Structure> f_209857_ = BuiltinStructures.m_209872_("ocean_ruin_cold");
    public static final ResourceKey<Structure> f_209858_ = BuiltinStructures.m_209872_("ocean_ruin_warm");
    public static final ResourceKey<Structure> f_209859_ = BuiltinStructures.m_209872_("fortress");
    public static final ResourceKey<Structure> f_209860_ = BuiltinStructures.m_209872_("nether_fossil");
    public static final ResourceKey<Structure> f_209861_ = BuiltinStructures.m_209872_("end_city");
    public static final ResourceKey<Structure> f_209862_ = BuiltinStructures.m_209872_("buried_treasure");
    public static final ResourceKey<Structure> f_209863_ = BuiltinStructures.m_209872_("bastion_remnant");
    public static final ResourceKey<Structure> f_209864_ = BuiltinStructures.m_209872_("village_plains");
    public static final ResourceKey<Structure> f_209865_ = BuiltinStructures.m_209872_("village_desert");
    public static final ResourceKey<Structure> f_209866_ = BuiltinStructures.m_209872_("village_savanna");
    public static final ResourceKey<Structure> f_209867_ = BuiltinStructures.m_209872_("village_snowy");
    public static final ResourceKey<Structure> f_209868_ = BuiltinStructures.m_209872_("village_taiga");
    public static final ResourceKey<Structure> f_209869_ = BuiltinStructures.m_209872_("ruined_portal");
    public static final ResourceKey<Structure> f_209870_ = BuiltinStructures.m_209872_("ruined_portal_desert");
    public static final ResourceKey<Structure> f_209840_ = BuiltinStructures.m_209872_("ruined_portal_jungle");
    public static final ResourceKey<Structure> f_209841_ = BuiltinStructures.m_209872_("ruined_portal_swamp");
    public static final ResourceKey<Structure> f_209842_ = BuiltinStructures.m_209872_("ruined_portal_mountain");
    public static final ResourceKey<Structure> f_209843_ = BuiltinStructures.m_209872_("ruined_portal_ocean");
    public static final ResourceKey<Structure> f_209844_ = BuiltinStructures.m_209872_("ruined_portal_nether");
    public static final ResourceKey<Structure> f_226492_ = BuiltinStructures.m_209872_("ancient_city");

    private static ResourceKey<Structure> m_209872_(String p_209873_) {
        return ResourceKey.m_135785_(Registry.f_235725_, new ResourceLocation(p_209873_));
    }
}


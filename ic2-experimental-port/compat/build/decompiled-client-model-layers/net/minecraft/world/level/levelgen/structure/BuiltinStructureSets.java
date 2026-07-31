/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public interface BuiltinStructureSets {
    public static final ResourceKey<StructureSet> f_209820_ = BuiltinStructureSets.m_209838_("villages");
    public static final ResourceKey<StructureSet> f_209821_ = BuiltinStructureSets.m_209838_("desert_pyramids");
    public static final ResourceKey<StructureSet> f_209822_ = BuiltinStructureSets.m_209838_("igloos");
    public static final ResourceKey<StructureSet> f_209823_ = BuiltinStructureSets.m_209838_("jungle_temples");
    public static final ResourceKey<StructureSet> f_209824_ = BuiltinStructureSets.m_209838_("swamp_huts");
    public static final ResourceKey<StructureSet> f_209825_ = BuiltinStructureSets.m_209838_("pillager_outposts");
    public static final ResourceKey<StructureSet> f_209826_ = BuiltinStructureSets.m_209838_("ocean_monuments");
    public static final ResourceKey<StructureSet> f_209827_ = BuiltinStructureSets.m_209838_("woodland_mansions");
    public static final ResourceKey<StructureSet> f_209828_ = BuiltinStructureSets.m_209838_("buried_treasures");
    public static final ResourceKey<StructureSet> f_209829_ = BuiltinStructureSets.m_209838_("mineshafts");
    public static final ResourceKey<StructureSet> f_209830_ = BuiltinStructureSets.m_209838_("ruined_portals");
    public static final ResourceKey<StructureSet> f_209831_ = BuiltinStructureSets.m_209838_("shipwrecks");
    public static final ResourceKey<StructureSet> f_209832_ = BuiltinStructureSets.m_209838_("ocean_ruins");
    public static final ResourceKey<StructureSet> f_209833_ = BuiltinStructureSets.m_209838_("nether_complexes");
    public static final ResourceKey<StructureSet> f_209834_ = BuiltinStructureSets.m_209838_("nether_fossils");
    public static final ResourceKey<StructureSet> f_209835_ = BuiltinStructureSets.m_209838_("end_cities");
    public static final ResourceKey<StructureSet> f_226491_ = BuiltinStructureSets.m_209838_("ancient_cities");
    public static final ResourceKey<StructureSet> f_209836_ = BuiltinStructureSets.m_209838_("strongholds");

    private static ResourceKey<StructureSet> m_209838_(String p_209839_) {
        return ResourceKey.m_135785_(Registry.f_211073_, new ResourceLocation(p_209839_));
    }
}


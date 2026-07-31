/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.structures.BuriedTreasureStructure;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidStructure;
import net.minecraft.world.level.levelgen.structure.structures.EndCityStructure;
import net.minecraft.world.level.levelgen.structure.structures.IglooStructure;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.structures.JungleTempleStructure;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinStructure;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckStructure;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdStructure;
import net.minecraft.world.level.levelgen.structure.structures.SwampHutStructure;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionStructure;

public interface StructureType<S extends Structure> {
    public static final StructureType<BuriedTreasureStructure> f_226862_ = StructureType.m_226881_("buried_treasure", BuriedTreasureStructure.f_227382_);
    public static final StructureType<DesertPyramidStructure> f_226863_ = StructureType.m_226881_("desert_pyramid", DesertPyramidStructure.f_227415_);
    public static final StructureType<EndCityStructure> f_226864_ = StructureType.m_226881_("end_city", EndCityStructure.f_227523_);
    public static final StructureType<NetherFortressStructure> f_226865_ = StructureType.m_226881_("fortress", NetherFortressStructure.f_228518_);
    public static final StructureType<IglooStructure> f_226866_ = StructureType.m_226881_("igloo", IglooStructure.f_227590_);
    public static final StructureType<JigsawStructure> f_226867_ = StructureType.m_226881_("jigsaw", JigsawStructure.f_227604_);
    public static final StructureType<JungleTempleStructure> f_226868_ = StructureType.m_226881_("jungle_temple", JungleTempleStructure.f_227691_);
    public static final StructureType<MineshaftStructure> f_226869_ = StructureType.m_226881_("mineshaft", MineshaftStructure.f_227957_);
    public static final StructureType<NetherFossilStructure> f_226870_ = StructureType.m_226881_("nether_fossil", NetherFossilStructure.f_228569_);
    public static final StructureType<OceanMonumentStructure> f_226871_ = StructureType.m_226881_("ocean_monument", OceanMonumentStructure.f_228952_);
    public static final StructureType<OceanRuinStructure> f_226872_ = StructureType.m_226881_("ocean_ruin", OceanRuinStructure.f_229054_);
    public static final StructureType<RuinedPortalStructure> f_226873_ = StructureType.m_226881_("ruined_portal", RuinedPortalStructure.f_229249_);
    public static final StructureType<ShipwreckStructure> f_226874_ = StructureType.m_226881_("shipwreck", ShipwreckStructure.f_229384_);
    public static final StructureType<StrongholdStructure> f_226875_ = StructureType.m_226881_("stronghold", StrongholdStructure.f_229936_);
    public static final StructureType<SwampHutStructure> f_226876_ = StructureType.m_226881_("swamp_hut", SwampHutStructure.f_229971_);
    public static final StructureType<WoodlandMansionStructure> f_226877_ = StructureType.m_226881_("woodland_mansion", WoodlandMansionStructure.f_230222_);

    public Codec<S> m_226884_();

    private static <S extends Structure> StructureType<S> m_226881_(String p_226882_, Codec<S> p_226883_) {
        return Registry.m_122961_(Registry.f_235740_, p_226882_, () -> p_226883_);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BastionBridgePools;
import net.minecraft.data.worldgen.BastionHoglinStablePools;
import net.minecraft.data.worldgen.BastionHousingUnitsPools;
import net.minecraft.data.worldgen.BastionSharedPools;
import net.minecraft.data.worldgen.BastionTreasureRoomPools;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class BastionPieces {
    public static final Holder<StructureTemplatePool> f_126673_ = Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("bastion/starts"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210531_("bastion/units/air_base", ProcessorLists.f_127221_), (Object)1), (Object)Pair.of(StructurePoolElement.m_210531_("bastion/hoglin_stable/air_base", ProcessorLists.f_127221_), (Object)1), (Object)Pair.of(StructurePoolElement.m_210531_("bastion/treasure/big_air_full", ProcessorLists.f_127221_), (Object)1), (Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/starting_pieces/entrance_base", ProcessorLists.f_127221_), (Object)1)), StructureTemplatePool.Projection.RIGID));

    public static void m_126675_() {
        BastionHousingUnitsPools.m_126672_();
        BastionHoglinStablePools.m_126591_();
        BastionTreasureRoomPools.m_126679_();
        BastionBridgePools.m_126589_();
        BastionSharedPools.m_126677_();
    }
}


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
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class PillagerOutpostPools {
    public static final Holder<StructureTemplatePool> f_127180_ = Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("pillager_outpost/base_plates"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210507_("pillager_outpost/base_plate"), (Object)1)), StructureTemplatePool.Projection.RIGID));

    public static void m_127182_() {
    }

    static {
        Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("pillager_outpost/towers"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210519_((List<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>>)ImmutableList.of(StructurePoolElement.m_210507_("pillager_outpost/watchtower"), StructurePoolElement.m_210512_("pillager_outpost/watchtower_overgrown", ProcessorLists.f_127215_))), (Object)1)), StructureTemplatePool.Projection.RIGID));
        Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("pillager_outpost/feature_plates"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210507_("pillager_outpost/feature_plate"), (Object)1)), StructureTemplatePool.Projection.TERRAIN_MATCHING));
        Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("pillager_outpost/features"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210507_("pillager_outpost/feature_cage1"), (Object)1), (Object)Pair.of(StructurePoolElement.m_210507_("pillager_outpost/feature_cage2"), (Object)1), (Object)Pair.of(StructurePoolElement.m_210507_("pillager_outpost/feature_cage_with_allays"), (Object)1), (Object)Pair.of(StructurePoolElement.m_210507_("pillager_outpost/feature_logs"), (Object)1), (Object)Pair.of(StructurePoolElement.m_210507_("pillager_outpost/feature_tent1"), (Object)1), (Object)Pair.of(StructurePoolElement.m_210507_("pillager_outpost/feature_tent2"), (Object)1), (Object)Pair.of(StructurePoolElement.m_210507_("pillager_outpost/feature_targets"), (Object)1), (Object)Pair.of(StructurePoolElement.m_210541_(), (Object)6)), StructureTemplatePool.Projection.RIGID));
    }
}


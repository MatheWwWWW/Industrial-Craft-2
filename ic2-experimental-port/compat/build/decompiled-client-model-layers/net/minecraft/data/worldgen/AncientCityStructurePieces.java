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
import net.minecraft.data.worldgen.AncientCityStructurePools;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class AncientCityStructurePieces {
    public static final Holder<StructureTemplatePool> f_236459_ = Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("ancient_city/city_center"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210531_("ancient_city/city_center/city_center_1", ProcessorLists.f_236493_), (Object)1), (Object)Pair.of(StructurePoolElement.m_210531_("ancient_city/city_center/city_center_2", ProcessorLists.f_236493_), (Object)1), (Object)Pair.of(StructurePoolElement.m_210531_("ancient_city/city_center/city_center_3", ProcessorLists.f_236493_), (Object)1)), StructureTemplatePool.Projection.RIGID));

    public static void m_236462_() {
        AncientCityStructurePools.m_236465_();
    }
}


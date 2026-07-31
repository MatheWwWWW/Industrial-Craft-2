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
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class BastionBridgePools {
    public static void m_126589_() {
    }

    static {
        Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/starting_pieces"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/starting_pieces/entrance", ProcessorLists.f_127223_), (Object)1), (Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/starting_pieces/entrance_face", ProcessorLists.f_127221_), (Object)1)), StructureTemplatePool.Projection.RIGID));
        Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/bridge_pieces"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/bridge_pieces/bridge", ProcessorLists.f_127192_), (Object)1)), StructureTemplatePool.Projection.RIGID));
        Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/legs"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/legs/leg_0", ProcessorLists.f_127221_), (Object)1), (Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/legs/leg_1", ProcessorLists.f_127221_), (Object)1)), StructureTemplatePool.Projection.RIGID));
        Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/walls"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/walls/wall_base_0", ProcessorLists.f_127222_), (Object)1), (Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/walls/wall_base_1", ProcessorLists.f_127222_), (Object)1)), StructureTemplatePool.Projection.RIGID));
        Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/ramparts"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/ramparts/rampart_0", ProcessorLists.f_127222_), (Object)1), (Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/ramparts/rampart_1", ProcessorLists.f_127222_), (Object)1)), StructureTemplatePool.Projection.RIGID));
        Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/rampart_plates"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/rampart_plates/plate_0", ProcessorLists.f_127222_), (Object)1)), StructureTemplatePool.Projection.RIGID));
        Pools.m_211103_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/connectors"), new ResourceLocation("empty"), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of((Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/connectors/back_bridge_top", ProcessorLists.f_127221_), (Object)1), (Object)Pair.of(StructurePoolElement.m_210531_("bastion/bridge/connectors/back_bridge_bottom", ProcessorLists.f_127221_), (Object)1)), StructureTemplatePool.Projection.RIGID));
    }
}


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
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.AncientCityStructurePieces;
import net.minecraft.data.worldgen.BastionPieces;
import net.minecraft.data.worldgen.PillagerOutpostPools;
import net.minecraft.data.worldgen.VillagePools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class Pools {
    public static final ResourceKey<StructureTemplatePool> f_127186_ = ResourceKey.m_135785_(Registry.f_122884_, new ResourceLocation("empty"));
    private static final Holder<StructureTemplatePool> f_127187_ = Pools.m_211103_(new StructureTemplatePool(f_127186_.m_135782_(), f_127186_.m_135782_(), (List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>>)ImmutableList.of(), StructureTemplatePool.Projection.RIGID));

    public static Holder<StructureTemplatePool> m_211103_(StructureTemplatePool p_211104_) {
        return BuiltinRegistries.m_206388_(BuiltinRegistries.f_123864_, p_211104_.m_210587_(), p_211104_);
    }

    @Deprecated
    public static void m_236490_() {
        Pools.m_236491_(BuiltinRegistries.f_123864_);
    }

    public static Holder<StructureTemplatePool> m_236491_(Registry<StructureTemplatePool> p_236492_) {
        BastionPieces.m_126675_();
        PillagerOutpostPools.m_127182_();
        VillagePools.m_127306_();
        AncientCityStructurePieces.m_236462_();
        return f_127187_;
    }
}


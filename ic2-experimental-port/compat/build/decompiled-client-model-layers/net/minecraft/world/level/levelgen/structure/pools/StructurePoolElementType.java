/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.pools;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.FeaturePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.ListPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;

public interface StructurePoolElementType<P extends StructurePoolElement> {
    public static final StructurePoolElementType<SinglePoolElement> f_210542_ = StructurePoolElementType.m_210550_("single_pool_element", SinglePoolElement.f_210410_);
    public static final StructurePoolElementType<ListPoolElement> f_210543_ = StructurePoolElementType.m_210550_("list_pool_element", ListPoolElement.f_210359_);
    public static final StructurePoolElementType<FeaturePoolElement> f_210544_ = StructurePoolElementType.m_210550_("feature_pool_element", FeaturePoolElement.f_210204_);
    public static final StructurePoolElementType<EmptyPoolElement> f_210545_ = StructurePoolElementType.m_210550_("empty_pool_element", EmptyPoolElement.f_210174_);
    public static final StructurePoolElementType<LegacySinglePoolElement> f_210546_ = StructurePoolElementType.m_210550_("legacy_single_pool_element", LegacySinglePoolElement.f_210345_);

    public Codec<P> m_210553_();

    public static <P extends StructurePoolElement> StructurePoolElementType<P> m_210550_(String p_210551_, Codec<P> p_210552_) {
        return Registry.m_122961_(Registry.f_122892_, p_210551_, () -> p_210552_);
    }
}


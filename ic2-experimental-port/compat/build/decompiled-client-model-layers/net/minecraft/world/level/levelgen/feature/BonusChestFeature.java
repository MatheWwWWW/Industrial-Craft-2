/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.stream.IntStream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class BonusChestFeature
extends Feature<NoneFeatureConfiguration> {
    public BonusChestFeature(Codec<NoneFeatureConfiguration> p_65299_) {
        super(p_65299_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159477_) {
        RandomSource $$1 = p_159477_.m_225041_();
        WorldGenLevel $$2 = p_159477_.m_159774_();
        ChunkPos $$3 = new ChunkPos(p_159477_.m_159777_());
        IntArrayList $$4 = Util.m_214658_(IntStream.rangeClosed($$3.m_45604_(), $$3.m_45608_()), $$1);
        IntArrayList $$5 = Util.m_214658_(IntStream.rangeClosed($$3.m_45605_(), $$3.m_45609_()), $$1);
        BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
        for (Integer $$7 : $$4) {
            for (Integer $$8 : $$5) {
                $$6.m_122178_($$7, 0, $$8);
                BlockPos $$9 = $$2.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, $$6);
                if (!$$2.m_46859_($$9) && !$$2.m_8055_($$9).m_60812_($$2, $$9).m_83281_()) continue;
                $$2.m_7731_($$9, Blocks.f_50087_.m_49966_(), 2);
                RandomizableContainerBlockEntity.m_222766_($$2, $$1, $$9, BuiltInLootTables.f_78740_);
                BlockState $$10 = Blocks.f_50081_.m_49966_();
                for (Direction $$11 : Direction.Plane.HORIZONTAL) {
                    BlockPos $$12 = $$9.m_121945_($$11);
                    if (!$$10.m_60710_($$2, $$12)) continue;
                    $$2.m_7731_($$12, $$10, 2);
                }
                return true;
            }
        }
        return false;
    }
}


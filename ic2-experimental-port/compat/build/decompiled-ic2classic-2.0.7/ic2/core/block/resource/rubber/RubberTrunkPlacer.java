/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.tags.BiomeTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.CommonLevelAccessor
 *  net.minecraft.world.level.LevelSimulatedReader
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.levelgen.feature.TreeFeature
 *  net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
 *  net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer$FoliageAttachment
 *  net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer
 *  net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType
 *  net.minecraftforge.common.Tags$Biomes
 */
package ic2.core.block.resource.rubber;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ic2.api.util.DirectionList;
import ic2.core.block.resource.RubberwoodLogBlock;
import ic2.core.platform.events.WorldGenerator;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.CommonLevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.common.Tags;

public class RubberTrunkPlacer
extends TrunkPlacer {
    public static Predicate<BlockState> RUBBER_PLANT = i -> i.m_60734_() == IC2Blocks.RUBBER_SAPLING;
    public static final Codec<RubberTrunkPlacer> SERIALIZER = RecordCodecBuilder.create(p_236891_0_ -> RubberTrunkPlacer.m_70305_((RecordCodecBuilder.Instance)p_236891_0_).apply((Applicative)p_236891_0_, RubberTrunkPlacer::new));

    public RubberTrunkPlacer(int p_i232060_1_, int p_i232060_2_, int p_i232060_3_) {
        super(p_i232060_1_, p_i232060_2_, p_i232060_3_);
    }

    protected TrunkPlacerType<?> m_7362_() {
        return WorldGenerator.RUBBER_TRUNK;
    }

    public List<FoliagePlacer.FoliageAttachment> m_213934_(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> placer, RandomSource random, int maxHeight, BlockPos pos, TreeConfiguration config) {
        int i;
        int max = 0;
        if (world instanceof CommonLevelAccessor) {
            Holder cat = ((CommonLevelAccessor)world).m_204166_(pos);
            if (cat.m_203656_(BiomeTags.f_207611_) || cat.m_203656_(BiomeTags.f_207610_)) {
                max += 6;
            }
            if (cat.m_203656_(Tags.Biomes.IS_SWAMP)) {
                max += 15;
            }
            if (!(config.f_161215_ || max > 0 && random.m_188503_(100) > 100 - max * 2)) {
                return ObjectLists.emptyList();
            }
        } else if (!config.f_161215_) {
            return ObjectLists.emptyList();
        }
        RubberTrunkPlacer.m_226169_((LevelSimulatedReader)world, placer, (RandomSource)random, (BlockPos)pos.m_7495_(), (TreeConfiguration)config);
        int hole_Chance = 25;
        ObjectList spawningPlaces = CollectionUtils.createList();
        for (i = 0; i < maxHeight; ++i) {
            BlockState state = IC2Blocks.RUBBERWOOD_LOG.m_49966_();
            if (random.m_188503_(100) <= hole_Chance) {
                hole_Chance -= 10;
                state = (BlockState)((BlockState)((BlockState)state.m_61124_((Property)RubberwoodLogBlock.RESIN, (Comparable)Boolean.valueOf(true))).m_61124_((Property)RubberwoodLogBlock.COLLECTABLE, (Comparable)Boolean.valueOf(true))).m_61124_((Property)RubberwoodLogBlock.RESIN_FACING, (Comparable)DirectionList.HORIZONTAL.getRandomFacing());
            }
            RubberTrunkPlacer.placeBlocks(world, random, pos.m_6630_(i), placer, state);
            if (maxHeight >= 4 && (maxHeight >= 7 || i <= 1) && i <= 2) continue;
            int x = pos.m_123341_();
            int z = pos.m_123343_();
            for (int a = x - 2; a <= x + 2; ++a) {
                for (int b = z - 2; b <= z + 2; ++b) {
                    int c = i + 4 - maxHeight;
                    if (c < 1) {
                        c = 1;
                    }
                    boolean gen = a > x - 2 && a < x + 2 && b > z - 2 && b < z + 2 || a > x - 2 && a < x + 2 && random.m_188503_(c) == 0 || b > z - 2 && b < z + 2 && random.m_188503_(c) == 0;
                    BlockPos leavePos = new BlockPos(a, pos.m_123342_() + i, b);
                    if (!gen || !TreeFeature.m_67267_((LevelSimulatedReader)world, (BlockPos)leavePos)) continue;
                    spawningPlaces.add((FoliagePlacer.FoliageAttachment)new FoliagePlacer.FoliageAttachment(leavePos, 0, true));
                }
            }
        }
        for (i = 0; i <= 2; ++i) {
            BlockPos leavePos = new BlockPos(pos.m_123341_(), pos.m_123342_() + maxHeight + i, pos.m_123343_());
            if (!TreeFeature.m_67267_((LevelSimulatedReader)world, (BlockPos)leavePos)) continue;
            spawningPlaces.add((FoliagePlacer.FoliageAttachment)new FoliagePlacer.FoliageAttachment(leavePos, 0, true));
        }
        return ImmutableList.copyOf(spawningPlaces);
    }

    protected static boolean placeBlocks(LevelSimulatedReader world, RandomSource rand, BlockPos pos, BiConsumer<BlockPos, BlockState> placer, BlockState state) {
        if (TreeFeature.m_67272_((LevelSimulatedReader)world, (BlockPos)pos)) {
            placer.accept(pos, state);
            return true;
        }
        return false;
    }
}


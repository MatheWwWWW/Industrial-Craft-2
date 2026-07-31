/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

public class TreeFeature
extends Feature<TreeConfiguration> {
    private static final int f_160509_ = 19;

    public TreeFeature(Codec<TreeConfiguration> p_67201_) {
        super(p_67201_);
    }

    private static boolean m_67277_(LevelSimulatedReader p_67278_, BlockPos p_67279_) {
        return p_67278_.m_7433_(p_67279_, p_225299_ -> p_225299_.m_60713_(Blocks.f_50191_));
    }

    public static boolean m_67282_(LevelSimulatedReader p_67283_, BlockPos p_67284_) {
        return p_67283_.m_7433_(p_67284_, p_225297_ -> p_225297_.m_60713_(Blocks.f_49990_));
    }

    public static boolean m_67267_(LevelSimulatedReader p_67268_, BlockPos p_67269_) {
        return p_67268_.m_7433_(p_67269_, p_225295_ -> p_225295_.m_60795_() || p_225295_.m_204336_(BlockTags.f_13035_));
    }

    private static boolean m_67288_(LevelSimulatedReader p_67289_, BlockPos p_67290_) {
        return p_67289_.m_7433_(p_67290_, p_225293_ -> {
            Material $$1 = p_225293_.m_60767_();
            return $$1 == Material.f_76302_ || $$1 == Material.f_76304_ || $$1 == Material.f_76303_;
        });
    }

    private static void m_67256_(LevelWriter p_67257_, BlockPos p_67258_, BlockState p_67259_) {
        p_67257_.m_7731_(p_67258_, p_67259_, 19);
    }

    public static boolean m_67272_(LevelSimulatedReader p_67273_, BlockPos p_67274_) {
        return TreeFeature.m_67267_(p_67273_, p_67274_) || TreeFeature.m_67288_(p_67273_, p_67274_) || TreeFeature.m_67282_(p_67273_, p_67274_);
    }

    private boolean m_225257_(WorldGenLevel p_225258_, RandomSource p_225259_, BlockPos p_225260_, BiConsumer<BlockPos, BlockState> p_225261_, BiConsumer<BlockPos, BlockState> p_225262_, BiConsumer<BlockPos, BlockState> p_225263_, TreeConfiguration p_225264_) {
        int $$7 = p_225264_.f_68190_.m_226153_(p_225259_);
        int $$8 = p_225264_.f_68189_.m_214116_(p_225259_, $$7, p_225264_);
        int $$9 = $$7 - $$8;
        int $$10 = p_225264_.f_68189_.m_214117_(p_225259_, $$9);
        BlockPos $$11 = p_225264_.f_225455_.map(p_225286_ -> p_225286_.m_225891_(p_225260_, p_225259_)).orElse(p_225260_);
        int $$12 = Math.min(p_225260_.m_123342_(), $$11.m_123342_());
        int $$13 = Math.max(p_225260_.m_123342_(), $$11.m_123342_()) + $$7 + 1;
        if ($$12 < p_225258_.m_141937_() + 1 || $$13 > p_225258_.m_151558_()) {
            return false;
        }
        OptionalInt $$14 = p_225264_.f_68191_.m_68295_();
        int $$15 = this.m_67215_(p_225258_, $$7, $$11, p_225264_);
        if ($$15 < $$7 && ($$14.isEmpty() || $$15 < $$14.getAsInt())) {
            return false;
        }
        if (p_225264_.f_225455_.isPresent() && !p_225264_.f_225455_.get().m_213684_(p_225258_, p_225261_, p_225259_, p_225260_, $$11, p_225264_)) {
            return false;
        }
        List<FoliagePlacer.FoliageAttachment> $$16 = p_225264_.f_68190_.m_213934_(p_225258_, p_225262_, p_225259_, $$15, $$11, p_225264_);
        $$16.forEach(p_225279_ -> p_225272_.f_68189_.m_225604_(p_225258_, p_225263_, p_225259_, p_225264_, $$15, (FoliagePlacer.FoliageAttachment)p_225279_, $$8, $$10));
        return true;
    }

    private int m_67215_(LevelSimulatedReader p_67216_, int p_67217_, BlockPos p_67218_, TreeConfiguration p_67219_) {
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (int $$5 = 0; $$5 <= p_67217_ + 1; ++$$5) {
            int $$6 = p_67219_.f_68191_.m_6133_(p_67217_, $$5);
            for (int $$7 = -$$6; $$7 <= $$6; ++$$7) {
                for (int $$8 = -$$6; $$8 <= $$6; ++$$8) {
                    $$4.m_122154_(p_67218_, $$7, $$5, $$8);
                    if (p_67219_.f_68190_.m_226184_(p_67216_, $$4) && (p_67219_.f_68193_ || !TreeFeature.m_67277_(p_67216_, $$4))) continue;
                    return $$5 - 2;
                }
            }
        }
        return p_67217_;
    }

    @Override
    protected void m_5974_(LevelWriter p_67221_, BlockPos p_67222_, BlockState p_67223_) {
        TreeFeature.m_67256_(p_67221_, p_67222_, p_67223_);
    }

    @Override
    public final boolean m_142674_(FeaturePlaceContext<TreeConfiguration> p_160530_) {
        WorldGenLevel $$1 = p_160530_.m_159774_();
        RandomSource $$2 = p_160530_.m_225041_();
        BlockPos $$3 = p_160530_.m_159777_();
        TreeConfiguration $$4 = p_160530_.m_159778_();
        HashSet $$5 = Sets.newHashSet();
        HashSet $$6 = Sets.newHashSet();
        HashSet $$7 = Sets.newHashSet();
        HashSet $$8 = Sets.newHashSet();
        BiConsumer<BlockPos, BlockState> $$9 = (p_160555_, p_160556_) -> {
            $$5.add(p_160555_.m_7949_());
            $$1.m_7731_((BlockPos)p_160555_, (BlockState)p_160556_, 19);
        };
        BiConsumer<BlockPos, BlockState> $$10 = (p_160548_, p_160549_) -> {
            $$6.add(p_160548_.m_7949_());
            $$1.m_7731_((BlockPos)p_160548_, (BlockState)p_160549_, 19);
        };
        BiConsumer<BlockPos, BlockState> $$11 = (p_160543_, p_160544_) -> {
            $$7.add(p_160543_.m_7949_());
            $$1.m_7731_((BlockPos)p_160543_, (BlockState)p_160544_, 19);
        };
        BiConsumer<BlockPos, BlockState> $$12 = (p_225290_, p_225291_) -> {
            $$8.add(p_225290_.m_7949_());
            $$1.m_7731_((BlockPos)p_225290_, (BlockState)p_225291_, 19);
        };
        boolean $$13 = this.m_225257_($$1, $$2, $$3, $$9, $$10, $$11, $$4);
        if (!$$13 || $$6.isEmpty() && $$7.isEmpty()) {
            return false;
        }
        if (!$$4.f_68187_.isEmpty()) {
            TreeDecorator.Context $$14 = new TreeDecorator.Context($$1, $$12, $$2, $$6, $$7, $$5);
            $$4.f_68187_.forEach(p_225282_ -> p_225282_.m_214187_($$14));
        }
        return BoundingBox.m_162378_(Iterables.concat((Iterable)$$5, (Iterable)$$6, (Iterable)$$7, (Iterable)$$8)).map(p_225270_ -> {
            DiscreteVoxelShape $$5 = TreeFeature.m_225251_($$1, p_225270_, $$6, $$8, $$5);
            StructureTemplate.m_74510_($$1, 3, $$5, p_225270_.m_162395_(), p_225270_.m_162396_(), p_225270_.m_162398_());
            return true;
        }).orElse(false);
    }

    private static DiscreteVoxelShape m_225251_(LevelAccessor p_225252_, BoundingBox p_225253_, Set<BlockPos> p_225254_, Set<BlockPos> p_225255_, Set<BlockPos> p_225256_) {
        ArrayList $$5 = Lists.newArrayList();
        BitSetDiscreteVoxelShape $$6 = new BitSetDiscreteVoxelShape(p_225253_.m_71056_(), p_225253_.m_71057_(), p_225253_.m_71058_());
        int $$7 = 6;
        for (int $$8 = 0; $$8 < 6; ++$$8) {
            $$5.add(Sets.newHashSet());
        }
        BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
        for (BlockPos $$10 : Lists.newArrayList((Iterable)Sets.union(p_225255_, p_225256_))) {
            if (!p_225253_.m_71051_($$10)) continue;
            ((DiscreteVoxelShape)$$6).m_142703_($$10.m_123341_() - p_225253_.m_162395_(), $$10.m_123342_() - p_225253_.m_162396_(), $$10.m_123343_() - p_225253_.m_162398_());
        }
        for (BlockPos $$11 : Lists.newArrayList(p_225254_)) {
            if (p_225253_.m_71051_($$11)) {
                ((DiscreteVoxelShape)$$6).m_142703_($$11.m_123341_() - p_225253_.m_162395_(), $$11.m_123342_() - p_225253_.m_162396_(), $$11.m_123343_() - p_225253_.m_162398_());
            }
            for (Direction $$12 : Direction.values()) {
                BlockState $$13;
                $$9.m_122159_($$11, $$12);
                if (p_225254_.contains($$9) || !($$13 = p_225252_.m_8055_($$9)).m_61138_(BlockStateProperties.f_61414_)) continue;
                ((Set)$$5.get(0)).add($$9.m_7949_());
                TreeFeature.m_67256_(p_225252_, $$9, (BlockState)$$13.m_61124_(BlockStateProperties.f_61414_, 1));
                if (!p_225253_.m_71051_($$9)) continue;
                ((DiscreteVoxelShape)$$6).m_142703_($$9.m_123341_() - p_225253_.m_162395_(), $$9.m_123342_() - p_225253_.m_162396_(), $$9.m_123343_() - p_225253_.m_162398_());
            }
        }
        for (int $$14 = 1; $$14 < 6; ++$$14) {
            Set $$15 = (Set)$$5.get($$14 - 1);
            Set $$16 = (Set)$$5.get($$14);
            for (BlockPos $$17 : $$15) {
                if (p_225253_.m_71051_($$17)) {
                    ((DiscreteVoxelShape)$$6).m_142703_($$17.m_123341_() - p_225253_.m_162395_(), $$17.m_123342_() - p_225253_.m_162396_(), $$17.m_123343_() - p_225253_.m_162398_());
                }
                for (Direction $$18 : Direction.values()) {
                    int $$20;
                    BlockState $$19;
                    $$9.m_122159_($$17, $$18);
                    if ($$15.contains($$9) || $$16.contains($$9) || !($$19 = p_225252_.m_8055_($$9)).m_61138_(BlockStateProperties.f_61414_) || ($$20 = $$19.m_61143_(BlockStateProperties.f_61414_).intValue()) <= $$14 + 1) continue;
                    BlockState $$21 = (BlockState)$$19.m_61124_(BlockStateProperties.f_61414_, $$14 + 1);
                    TreeFeature.m_67256_(p_225252_, $$9, $$21);
                    if (p_225253_.m_71051_($$9)) {
                        ((DiscreteVoxelShape)$$6).m_142703_($$9.m_123341_() - p_225253_.m_162395_(), $$9.m_123342_() - p_225253_.m_162396_(), $$9.m_123343_() - p_225253_.m_162398_());
                    }
                    $$16.add($$9.m_7949_());
                }
            }
        }
        return $$6;
    }
}


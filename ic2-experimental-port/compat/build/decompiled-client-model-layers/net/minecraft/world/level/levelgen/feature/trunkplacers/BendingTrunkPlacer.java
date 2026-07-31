/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class BendingTrunkPlacer
extends TrunkPlacer {
    public static final Codec<BendingTrunkPlacer> f_161765_ = RecordCodecBuilder.create(p_161786_ -> BendingTrunkPlacer.m_70305_(p_161786_).and(p_161786_.group((App)ExtraCodecs.f_144629_.optionalFieldOf("min_height_for_leaves", (Object)1).forGetter(p_161788_ -> p_161788_.f_161766_), (App)IntProvider.m_146545_(1, 64).fieldOf("bend_length").forGetter(p_161784_ -> p_161784_.f_161767_))).apply((Applicative)p_161786_, BendingTrunkPlacer::new));
    private final int f_161766_;
    private final IntProvider f_161767_;

    public BendingTrunkPlacer(int p_161770_, int p_161771_, int p_161772_, int p_161773_, IntProvider p_161774_) {
        super(p_161770_, p_161771_, p_161772_);
        this.f_161766_ = p_161773_;
        this.f_161767_ = p_161774_;
    }

    @Override
    protected TrunkPlacerType<?> m_7362_() {
        return TrunkPlacerType.f_161899_;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> m_213934_(LevelSimulatedReader p_226079_, BiConsumer<BlockPos, BlockState> p_226080_, RandomSource p_226081_, int p_226082_, BlockPos p_226083_, TreeConfiguration p_226084_) {
        Direction $$6 = Direction.Plane.HORIZONTAL.m_235690_(p_226081_);
        int $$7 = p_226082_ - 1;
        BlockPos.MutableBlockPos $$8 = p_226083_.m_122032_();
        Vec3i $$9 = $$8.m_7495_();
        BendingTrunkPlacer.m_226169_(p_226079_, p_226080_, p_226081_, (BlockPos)$$9, p_226084_);
        ArrayList $$10 = Lists.newArrayList();
        for (int $$11 = 0; $$11 <= $$7; ++$$11) {
            if ($$11 + 1 >= $$7 + p_226081_.m_188503_(2)) {
                $$8.m_122173_($$6);
            }
            if (TreeFeature.m_67272_(p_226079_, $$8)) {
                this.m_226187_(p_226079_, p_226080_, p_226081_, $$8, p_226084_);
            }
            if ($$11 >= this.f_161766_) {
                $$10.add(new FoliagePlacer.FoliageAttachment($$8.m_7949_(), 0, false));
            }
            $$8.m_122173_(Direction.UP);
        }
        int $$12 = this.f_161767_.m_214085_(p_226081_);
        for (int $$13 = 0; $$13 <= $$12; ++$$13) {
            if (TreeFeature.m_67272_(p_226079_, $$8)) {
                this.m_226187_(p_226079_, p_226080_, p_226081_, $$8, p_226084_);
            }
            $$10.add(new FoliagePlacer.FoliageAttachment($$8.m_7949_(), 0, false));
            $$8.m_122173_($$6);
        }
        return $$10;
    }
}


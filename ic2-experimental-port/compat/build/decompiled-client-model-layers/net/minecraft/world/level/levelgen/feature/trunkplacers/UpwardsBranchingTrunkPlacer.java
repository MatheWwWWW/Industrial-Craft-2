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
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class UpwardsBranchingTrunkPlacer
extends TrunkPlacer {
    public static final Codec<UpwardsBranchingTrunkPlacer> f_226194_ = RecordCodecBuilder.create(p_226236_ -> UpwardsBranchingTrunkPlacer.m_70305_(p_226236_).and(p_226236_.group((App)IntProvider.f_146533_.fieldOf("extra_branch_steps").forGetter(p_226242_ -> p_226242_.f_226195_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("place_branch_per_log_probability").forGetter(p_226240_ -> Float.valueOf(p_226240_.f_226196_)), (App)IntProvider.f_146532_.fieldOf("extra_branch_length").forGetter(p_226238_ -> p_226238_.f_226197_), (App)RegistryCodecs.m_206277_(Registry.f_122901_).fieldOf("can_grow_through").forGetter(p_226234_ -> p_226234_.f_226198_))).apply((Applicative)p_226236_, UpwardsBranchingTrunkPlacer::new));
    private final IntProvider f_226195_;
    private final float f_226196_;
    private final IntProvider f_226197_;
    private final HolderSet<Block> f_226198_;

    public UpwardsBranchingTrunkPlacer(int p_226201_, int p_226202_, int p_226203_, IntProvider p_226204_, float p_226205_, IntProvider p_226206_, HolderSet<Block> p_226207_) {
        super(p_226201_, p_226202_, p_226203_);
        this.f_226195_ = p_226204_;
        this.f_226196_ = p_226205_;
        this.f_226197_ = p_226206_;
        this.f_226198_ = p_226207_;
    }

    @Override
    protected TrunkPlacerType<?> m_7362_() {
        return TrunkPlacerType.f_226193_;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> m_213934_(LevelSimulatedReader p_226225_, BiConsumer<BlockPos, BlockState> p_226226_, RandomSource p_226227_, int p_226228_, BlockPos p_226229_, TreeConfiguration p_226230_) {
        ArrayList $$6 = Lists.newArrayList();
        BlockPos.MutableBlockPos $$7 = new BlockPos.MutableBlockPos();
        for (int $$8 = 0; $$8 < p_226228_; ++$$8) {
            int $$9 = p_226229_.m_123342_() + $$8;
            if (this.m_226187_(p_226225_, p_226226_, p_226227_, $$7.m_122178_(p_226229_.m_123341_(), $$9, p_226229_.m_123343_()), p_226230_) && $$8 < p_226228_ - 1 && p_226227_.m_188501_() < this.f_226196_) {
                Direction $$10 = Direction.Plane.HORIZONTAL.m_235690_(p_226227_);
                int $$11 = this.f_226197_.m_214085_(p_226227_);
                int $$12 = Math.max(0, $$11 - this.f_226197_.m_214085_(p_226227_) - 1);
                int $$13 = this.f_226195_.m_214085_(p_226227_);
                this.m_226212_(p_226225_, p_226226_, p_226227_, p_226228_, p_226230_, $$6, $$7, $$9, $$10, $$12, $$13);
            }
            if ($$8 != p_226228_ - 1) continue;
            $$6.add(new FoliagePlacer.FoliageAttachment($$7.m_122178_(p_226229_.m_123341_(), $$9 + 1, p_226229_.m_123343_()), 0, false));
        }
        return $$6;
    }

    private void m_226212_(LevelSimulatedReader p_226213_, BiConsumer<BlockPos, BlockState> p_226214_, RandomSource p_226215_, int p_226216_, TreeConfiguration p_226217_, List<FoliagePlacer.FoliageAttachment> p_226218_, BlockPos.MutableBlockPos p_226219_, int p_226220_, Direction p_226221_, int p_226222_, int p_226223_) {
        int $$11 = p_226220_ + p_226222_;
        int $$12 = p_226219_.m_123341_();
        int $$13 = p_226219_.m_123343_();
        for (int $$14 = p_226222_; $$14 < p_226216_ && p_226223_ > 0; ++$$14, --p_226223_) {
            if ($$14 < 1) continue;
            int $$15 = p_226220_ + $$14;
            $$11 = $$15;
            if (this.m_226187_(p_226213_, p_226214_, p_226215_, p_226219_.m_122178_($$12 += p_226221_.m_122429_(), $$15, $$13 += p_226221_.m_122431_()), p_226217_)) {
                ++$$11;
            }
            p_226218_.add(new FoliagePlacer.FoliageAttachment(p_226219_.m_7949_(), 0, false));
        }
        if ($$11 - p_226220_ > 1) {
            BlockPos $$16 = new BlockPos($$12, $$11, $$13);
            p_226218_.add(new FoliagePlacer.FoliageAttachment($$16, 0, false));
            p_226218_.add(new FoliagePlacer.FoliageAttachment($$16.m_6625_(2), 0, false));
        }
    }

    @Override
    protected boolean m_213554_(LevelSimulatedReader p_226210_, BlockPos p_226211_) {
        return super.m_213554_(p_226210_, p_226211_) || p_226210_.m_7433_(p_226211_, p_226232_ -> p_226232_.m_204341_(this.f_226198_));
    }
}


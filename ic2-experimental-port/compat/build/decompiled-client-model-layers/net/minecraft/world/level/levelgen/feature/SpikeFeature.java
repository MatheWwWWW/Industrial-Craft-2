/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 */
package net.minecraft.world.level.levelgen.feature;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;
import net.minecraft.world.phys.AABB;

public class SpikeFeature
extends Feature<SpikeConfiguration> {
    public static final int f_160369_ = 10;
    private static final int f_160370_ = 42;
    private static final LoadingCache<Long, List<EndSpike>> f_66849_ = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).build((CacheLoader)new SpikeCacheLoader());

    public SpikeFeature(Codec<SpikeConfiguration> p_66852_) {
        super(p_66852_);
    }

    public static List<EndSpike> m_66858_(WorldGenLevel p_66859_) {
        RandomSource $$1 = RandomSource.m_216335_(p_66859_.m_7328_());
        long $$2 = $$1.m_188505_() & 0xFFFFL;
        return (List)f_66849_.getUnchecked((Object)$$2);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<SpikeConfiguration> p_160372_) {
        SpikeConfiguration $$1 = p_160372_.m_159778_();
        WorldGenLevel $$2 = p_160372_.m_159774_();
        RandomSource $$3 = p_160372_.m_225041_();
        BlockPos $$4 = p_160372_.m_159777_();
        List<EndSpike> $$5 = $$1.m_68119_();
        if ($$5.isEmpty()) {
            $$5 = SpikeFeature.m_66858_($$2);
        }
        for (EndSpike $$6 : $$5) {
            if (!$$6.m_66891_($$4)) continue;
            this.m_225246_($$2, $$3, $$1, $$6);
        }
        return true;
    }

    private void m_225246_(ServerLevelAccessor p_225247_, RandomSource p_225248_, SpikeConfiguration p_225249_, EndSpike p_225250_) {
        int $$4 = p_225250_.m_66896_();
        for (BlockPos $$5 : BlockPos.m_121940_(new BlockPos(p_225250_.m_66886_() - $$4, p_225247_.m_141937_(), p_225250_.m_66893_() - $$4), new BlockPos(p_225250_.m_66886_() + $$4, p_225250_.m_66899_() + 10, p_225250_.m_66893_() + $$4))) {
            if ($$5.m_203202_(p_225250_.m_66886_(), $$5.m_123342_(), p_225250_.m_66893_()) <= (double)($$4 * $$4 + 1) && $$5.m_123342_() < p_225250_.m_66899_()) {
                this.m_5974_(p_225247_, $$5, Blocks.f_50080_.m_49966_());
                continue;
            }
            if ($$5.m_123342_() <= 65) continue;
            this.m_5974_(p_225247_, $$5, Blocks.f_50016_.m_49966_());
        }
        if (p_225250_.m_66902_()) {
            int $$6 = -2;
            int $$7 = 2;
            int $$8 = 3;
            BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
            for (int $$10 = -2; $$10 <= 2; ++$$10) {
                for (int $$11 = -2; $$11 <= 2; ++$$11) {
                    for (int $$12 = 0; $$12 <= 3; ++$$12) {
                        boolean $$15;
                        boolean $$13 = Mth.m_14040_($$10) == 2;
                        boolean $$14 = Mth.m_14040_($$11) == 2;
                        boolean bl = $$15 = $$12 == 3;
                        if (!$$13 && !$$14 && !$$15) continue;
                        boolean $$16 = $$10 == -2 || $$10 == 2 || $$15;
                        boolean $$17 = $$11 == -2 || $$11 == 2 || $$15;
                        BlockState $$18 = (BlockState)((BlockState)((BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, $$16 && $$11 != -2)).m_61124_(IronBarsBlock.f_52311_, $$16 && $$11 != 2)).m_61124_(IronBarsBlock.f_52312_, $$17 && $$10 != -2)).m_61124_(IronBarsBlock.f_52310_, $$17 && $$10 != 2);
                        this.m_5974_(p_225247_, $$9.m_122178_(p_225250_.m_66886_() + $$10, p_225250_.m_66899_() + $$12, p_225250_.m_66893_() + $$11), $$18);
                    }
                }
            }
        }
        EndCrystal $$19 = EntityType.f_20564_.m_20615_(p_225247_.m_6018_());
        $$19.m_31052_(p_225249_.m_68122_());
        $$19.m_20331_(p_225249_.m_68116_());
        $$19.m_7678_((double)p_225250_.m_66886_() + 0.5, p_225250_.m_66899_() + 1, (double)p_225250_.m_66893_() + 0.5, p_225248_.m_188501_() * 360.0f, 0.0f);
        p_225247_.m_7967_($$19);
        this.m_5974_(p_225247_, new BlockPos(p_225250_.m_66886_(), p_225250_.m_66899_(), p_225250_.m_66893_()), Blocks.f_50752_.m_49966_());
    }

    public static class EndSpike {
        public static final Codec<EndSpike> f_66872_ = RecordCodecBuilder.create(p_66890_ -> p_66890_.group((App)Codec.INT.fieldOf("centerX").orElse((Object)0).forGetter(p_160382_ -> p_160382_.f_66873_), (App)Codec.INT.fieldOf("centerZ").orElse((Object)0).forGetter(p_160380_ -> p_160380_.f_66874_), (App)Codec.INT.fieldOf("radius").orElse((Object)0).forGetter(p_160378_ -> p_160378_.f_66875_), (App)Codec.INT.fieldOf("height").orElse((Object)0).forGetter(p_160376_ -> p_160376_.f_66876_), (App)Codec.BOOL.fieldOf("guarded").orElse((Object)false).forGetter(p_160374_ -> p_160374_.f_66877_)).apply((Applicative)p_66890_, EndSpike::new));
        private final int f_66873_;
        private final int f_66874_;
        private final int f_66875_;
        private final int f_66876_;
        private final boolean f_66877_;
        private final AABB f_66878_;

        public EndSpike(int p_66881_, int p_66882_, int p_66883_, int p_66884_, boolean p_66885_) {
            this.f_66873_ = p_66881_;
            this.f_66874_ = p_66882_;
            this.f_66875_ = p_66883_;
            this.f_66876_ = p_66884_;
            this.f_66877_ = p_66885_;
            this.f_66878_ = new AABB(p_66881_ - p_66883_, DimensionType.f_156653_, p_66882_ - p_66883_, p_66881_ + p_66883_, DimensionType.f_156652_, p_66882_ + p_66883_);
        }

        public boolean m_66891_(BlockPos p_66892_) {
            return SectionPos.m_123171_(p_66892_.m_123341_()) == SectionPos.m_123171_(this.f_66873_) && SectionPos.m_123171_(p_66892_.m_123343_()) == SectionPos.m_123171_(this.f_66874_);
        }

        public int m_66886_() {
            return this.f_66873_;
        }

        public int m_66893_() {
            return this.f_66874_;
        }

        public int m_66896_() {
            return this.f_66875_;
        }

        public int m_66899_() {
            return this.f_66876_;
        }

        public boolean m_66902_() {
            return this.f_66877_;
        }

        public AABB m_66905_() {
            return this.f_66878_;
        }
    }

    static class SpikeCacheLoader
    extends CacheLoader<Long, List<EndSpike>> {
        SpikeCacheLoader() {
        }

        public List<EndSpike> load(Long p_66910_) {
            IntArrayList $$1 = Util.m_214658_(IntStream.range(0, 10), RandomSource.m_216335_(p_66910_));
            ArrayList $$2 = Lists.newArrayList();
            for (int $$3 = 0; $$3 < 10; ++$$3) {
                int $$4 = Mth.m_14107_(42.0 * Math.cos(2.0 * (-Math.PI + 0.3141592653589793 * (double)$$3)));
                int $$5 = Mth.m_14107_(42.0 * Math.sin(2.0 * (-Math.PI + 0.3141592653589793 * (double)$$3)));
                int $$6 = $$1.get($$3);
                int $$7 = 2 + $$6 / 3;
                int $$8 = 76 + $$6 * 3;
                boolean $$9 = $$6 == 1 || $$6 == 2;
                $$2.add(new EndSpike($$4, $$5, $$7, $$8, $$9));
            }
            return $$2;
        }

        public /* synthetic */ Object load(Object object) throws Exception {
            return this.load((Long)object);
        }
    }
}


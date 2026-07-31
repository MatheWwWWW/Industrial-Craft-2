/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Material;

@Deprecated
public class LakeFeature
extends Feature<Configuration> {
    private static final BlockState f_66256_ = Blocks.f_50627_.m_49966_();

    public LakeFeature(Codec<Configuration> p_66259_) {
        super(p_66259_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<Configuration> p_159958_) {
        BlockPos $$1 = p_159958_.m_159777_();
        WorldGenLevel $$2 = p_159958_.m_159774_();
        RandomSource $$3 = p_159958_.m_225041_();
        Configuration $$4 = p_159958_.m_159778_();
        if ($$1.m_123342_() <= $$2.m_141937_() + 4) {
            return false;
        }
        $$1 = $$1.m_6625_(4);
        boolean[] $$5 = new boolean[2048];
        int $$6 = $$3.m_188503_(4) + 4;
        for (int $$7 = 0; $$7 < $$6; ++$$7) {
            double $$8 = $$3.m_188500_() * 6.0 + 3.0;
            double $$9 = $$3.m_188500_() * 4.0 + 2.0;
            double $$10 = $$3.m_188500_() * 6.0 + 3.0;
            double $$11 = $$3.m_188500_() * (16.0 - $$8 - 2.0) + 1.0 + $$8 / 2.0;
            double $$12 = $$3.m_188500_() * (8.0 - $$9 - 4.0) + 2.0 + $$9 / 2.0;
            double $$13 = $$3.m_188500_() * (16.0 - $$10 - 2.0) + 1.0 + $$10 / 2.0;
            for (int $$14 = 1; $$14 < 15; ++$$14) {
                for (int $$15 = 1; $$15 < 15; ++$$15) {
                    for (int $$16 = 1; $$16 < 7; ++$$16) {
                        double $$17 = ((double)$$14 - $$11) / ($$8 / 2.0);
                        double $$18 = ((double)$$16 - $$12) / ($$9 / 2.0);
                        double $$19 = ((double)$$15 - $$13) / ($$10 / 2.0);
                        double $$20 = $$17 * $$17 + $$18 * $$18 + $$19 * $$19;
                        if (!($$20 < 1.0)) continue;
                        $$5[($$14 * 16 + $$15) * 8 + $$16] = true;
                    }
                }
            }
        }
        BlockState $$21 = $$4.f_190954_().m_213972_($$3, $$1);
        for (int $$22 = 0; $$22 < 16; ++$$22) {
            for (int $$23 = 0; $$23 < 16; ++$$23) {
                for (int $$24 = 0; $$24 < 8; ++$$24) {
                    boolean $$25;
                    boolean bl = $$25 = !$$5[($$22 * 16 + $$23) * 8 + $$24] && ($$22 < 15 && $$5[(($$22 + 1) * 16 + $$23) * 8 + $$24] || $$22 > 0 && $$5[(($$22 - 1) * 16 + $$23) * 8 + $$24] || $$23 < 15 && $$5[($$22 * 16 + $$23 + 1) * 8 + $$24] || $$23 > 0 && $$5[($$22 * 16 + ($$23 - 1)) * 8 + $$24] || $$24 < 7 && $$5[($$22 * 16 + $$23) * 8 + $$24 + 1] || $$24 > 0 && $$5[($$22 * 16 + $$23) * 8 + ($$24 - 1)]);
                    if (!$$25) continue;
                    Material $$26 = $$2.m_8055_($$1.m_7918_($$22, $$24, $$23)).m_60767_();
                    if ($$24 >= 4 && $$26.m_76332_()) {
                        return false;
                    }
                    if ($$24 >= 4 || $$26.m_76333_() || $$2.m_8055_($$1.m_7918_($$22, $$24, $$23)) == $$21) continue;
                    return false;
                }
            }
        }
        for (int $$27 = 0; $$27 < 16; ++$$27) {
            for (int $$28 = 0; $$28 < 16; ++$$28) {
                for (int $$29 = 0; $$29 < 8; ++$$29) {
                    BlockPos $$30;
                    if (!$$5[($$27 * 16 + $$28) * 8 + $$29] || !this.m_190951_($$2.m_8055_($$30 = $$1.m_7918_($$27, $$29, $$28)))) continue;
                    boolean $$31 = $$29 >= 4;
                    $$2.m_7731_($$30, $$31 ? f_66256_ : $$21, 2);
                    if (!$$31) continue;
                    $$2.m_186460_($$30, f_66256_.m_60734_(), 0);
                    this.m_159739_($$2, $$30);
                }
            }
        }
        BlockState $$32 = $$4.f_190955_().m_213972_($$3, $$1);
        if (!$$32.m_60795_()) {
            for (int $$33 = 0; $$33 < 16; ++$$33) {
                for (int $$34 = 0; $$34 < 16; ++$$34) {
                    for (int $$35 = 0; $$35 < 8; ++$$35) {
                        BlockState $$37;
                        boolean $$36;
                        boolean bl = $$36 = !$$5[($$33 * 16 + $$34) * 8 + $$35] && ($$33 < 15 && $$5[(($$33 + 1) * 16 + $$34) * 8 + $$35] || $$33 > 0 && $$5[(($$33 - 1) * 16 + $$34) * 8 + $$35] || $$34 < 15 && $$5[($$33 * 16 + $$34 + 1) * 8 + $$35] || $$34 > 0 && $$5[($$33 * 16 + ($$34 - 1)) * 8 + $$35] || $$35 < 7 && $$5[($$33 * 16 + $$34) * 8 + $$35 + 1] || $$35 > 0 && $$5[($$33 * 16 + $$34) * 8 + ($$35 - 1)]);
                        if (!$$36 || $$35 >= 4 && $$3.m_188503_(2) == 0 || !($$37 = $$2.m_8055_($$1.m_7918_($$33, $$35, $$34))).m_60767_().m_76333_() || $$37.m_204336_(BlockTags.f_144288_)) continue;
                        BlockPos $$38 = $$1.m_7918_($$33, $$35, $$34);
                        $$2.m_7731_($$38, $$32, 2);
                        this.m_159739_($$2, $$38);
                    }
                }
            }
        }
        if ($$21.m_60819_().m_205070_(FluidTags.f_13131_)) {
            for (int $$39 = 0; $$39 < 16; ++$$39) {
                for (int $$40 = 0; $$40 < 16; ++$$40) {
                    int $$41 = 4;
                    BlockPos $$42 = $$1.m_7918_($$39, 4, $$40);
                    if (!$$2.m_204166_($$42).m_203334_().m_47480_($$2, $$42, false) || !this.m_190951_($$2.m_8055_($$42))) continue;
                    $$2.m_7731_($$42, Blocks.f_50126_.m_49966_(), 2);
                }
            }
        }
        return true;
    }

    private boolean m_190951_(BlockState p_190952_) {
        return !p_190952_.m_204336_(BlockTags.f_144287_);
    }

    public record Configuration(BlockStateProvider f_190954_, BlockStateProvider f_190955_) implements FeatureConfiguration
    {
        public static final Codec<Configuration> f_190953_ = RecordCodecBuilder.create(p_190962_ -> p_190962_.group((App)BlockStateProvider.f_68747_.fieldOf("fluid").forGetter(Configuration::f_190954_), (App)BlockStateProvider.f_68747_.fieldOf("barrier").forGetter(Configuration::f_190955_)).apply((Applicative)p_190962_, Configuration::new));

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Configuration.class, "fluid;barrier", "f_190954_", "f_190955_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Configuration.class, "fluid;barrier", "f_190954_", "f_190955_"}, this);
        }

        @Override
        public final boolean equals(Object p_190965_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Configuration.class, "fluid;barrier", "f_190954_", "f_190955_"}, this, p_190965_);
        }
    }
}


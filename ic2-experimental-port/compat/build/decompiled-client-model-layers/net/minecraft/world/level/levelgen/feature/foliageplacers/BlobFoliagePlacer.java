/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.Products$P3
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Mu
 */
package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class BlobFoliagePlacer
extends FoliagePlacer {
    public static final Codec<BlobFoliagePlacer> f_68392_ = RecordCodecBuilder.create(p_68427_ -> BlobFoliagePlacer.m_68413_(p_68427_).apply((Applicative)p_68427_, BlobFoliagePlacer::new));
    protected final int f_68393_;

    protected static <P extends BlobFoliagePlacer> Products.P3<RecordCodecBuilder.Mu<P>, IntProvider, IntProvider, Integer> m_68413_(RecordCodecBuilder.Instance<P> p_68414_) {
        return BlobFoliagePlacer.m_68573_(p_68414_).and((App)Codec.intRange((int)0, (int)16).fieldOf("height").forGetter(p_68412_ -> p_68412_.f_68393_));
    }

    public BlobFoliagePlacer(IntProvider p_161356_, IntProvider p_161357_, int p_161358_) {
        super(p_161356_, p_161357_);
        this.f_68393_ = p_161358_;
    }

    @Override
    protected FoliagePlacerType<?> m_5897_() {
        return FoliagePlacerType.f_68591_;
    }

    @Override
    protected void m_213633_(LevelSimulatedReader p_225520_, BiConsumer<BlockPos, BlockState> p_225521_, RandomSource p_225522_, TreeConfiguration p_225523_, int p_225524_, FoliagePlacer.FoliageAttachment p_225525_, int p_225526_, int p_225527_, int p_225528_) {
        for (int $$9 = p_225528_; $$9 >= p_225528_ - p_225526_; --$$9) {
            int $$10 = Math.max(p_225527_ + p_225525_.m_68589_() - 1 - $$9 / 2, 0);
            this.m_225628_(p_225520_, p_225521_, p_225522_, p_225523_, p_225525_.m_161451_(), $$10, $$9, p_225525_.m_68590_());
        }
    }

    @Override
    public int m_214116_(RandomSource p_225516_, int p_225517_, TreeConfiguration p_225518_) {
        return this.f_68393_;
    }

    @Override
    protected boolean m_214203_(RandomSource p_225509_, int p_225510_, int p_225511_, int p_225512_, int p_225513_, boolean p_225514_) {
        return p_225510_ == p_225513_ && p_225512_ == p_225513_ && (p_225509_.m_188503_(2) == 0 || p_225511_ == 0);
    }
}


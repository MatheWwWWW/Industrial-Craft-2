/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.foliageplacers;

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

public class MegaJungleFoliagePlacer
extends FoliagePlacer {
    public static final Codec<MegaJungleFoliagePlacer> f_68608_ = RecordCodecBuilder.create(p_68630_ -> MegaJungleFoliagePlacer.m_68573_(p_68630_).and((App)Codec.intRange((int)0, (int)16).fieldOf("height").forGetter(p_161468_ -> p_161468_.f_68609_)).apply((Applicative)p_68630_, MegaJungleFoliagePlacer::new));
    protected final int f_68609_;

    public MegaJungleFoliagePlacer(IntProvider p_161454_, IntProvider p_161455_, int p_161456_) {
        super(p_161454_, p_161455_);
        this.f_68609_ = p_161456_;
    }

    @Override
    protected FoliagePlacerType<?> m_5897_() {
        return FoliagePlacerType.f_68597_;
    }

    @Override
    protected void m_213633_(LevelSimulatedReader p_225657_, BiConsumer<BlockPos, BlockState> p_225658_, RandomSource p_225659_, TreeConfiguration p_225660_, int p_225661_, FoliagePlacer.FoliageAttachment p_225662_, int p_225663_, int p_225664_, int p_225665_) {
        int $$9 = p_225662_.m_68590_() ? p_225663_ : 1 + p_225659_.m_188503_(2);
        for (int $$10 = p_225665_; $$10 >= p_225665_ - $$9; --$$10) {
            int $$11 = p_225664_ + p_225662_.m_68589_() + 1 - $$10;
            this.m_225628_(p_225657_, p_225658_, p_225659_, p_225660_, p_225662_.m_161451_(), $$11, $$10, p_225662_.m_68590_());
        }
    }

    @Override
    public int m_214116_(RandomSource p_225653_, int p_225654_, TreeConfiguration p_225655_) {
        return this.f_68609_;
    }

    @Override
    protected boolean m_214203_(RandomSource p_225646_, int p_225647_, int p_225648_, int p_225649_, int p_225650_, boolean p_225651_) {
        if (p_225647_ + p_225649_ >= 7) {
            return true;
        }
        return p_225647_ * p_225647_ + p_225649_ * p_225649_ > p_225650_ * p_225650_;
    }
}


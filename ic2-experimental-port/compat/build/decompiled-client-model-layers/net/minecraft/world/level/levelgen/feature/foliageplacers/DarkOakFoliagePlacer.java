/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.foliageplacers;

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

public class DarkOakFoliagePlacer
extends FoliagePlacer {
    public static final Codec<DarkOakFoliagePlacer> f_68455_ = RecordCodecBuilder.create(p_68473_ -> DarkOakFoliagePlacer.m_68573_(p_68473_).apply((Applicative)p_68473_, DarkOakFoliagePlacer::new));

    public DarkOakFoliagePlacer(IntProvider p_161384_, IntProvider p_161385_) {
        super(p_161384_, p_161385_);
    }

    @Override
    protected FoliagePlacerType<?> m_5897_() {
        return FoliagePlacerType.f_68599_;
    }

    @Override
    protected void m_213633_(LevelSimulatedReader p_225558_, BiConsumer<BlockPos, BlockState> p_225559_, RandomSource p_225560_, TreeConfiguration p_225561_, int p_225562_, FoliagePlacer.FoliageAttachment p_225563_, int p_225564_, int p_225565_, int p_225566_) {
        BlockPos $$9 = p_225563_.m_161451_().m_6630_(p_225566_);
        boolean $$10 = p_225563_.m_68590_();
        if ($$10) {
            this.m_225628_(p_225558_, p_225559_, p_225560_, p_225561_, $$9, p_225565_ + 2, -1, $$10);
            this.m_225628_(p_225558_, p_225559_, p_225560_, p_225561_, $$9, p_225565_ + 3, 0, $$10);
            this.m_225628_(p_225558_, p_225559_, p_225560_, p_225561_, $$9, p_225565_ + 2, 1, $$10);
            if (p_225560_.m_188499_()) {
                this.m_225628_(p_225558_, p_225559_, p_225560_, p_225561_, $$9, p_225565_, 2, $$10);
            }
        } else {
            this.m_225628_(p_225558_, p_225559_, p_225560_, p_225561_, $$9, p_225565_ + 2, -1, $$10);
            this.m_225628_(p_225558_, p_225559_, p_225560_, p_225561_, $$9, p_225565_ + 1, 0, $$10);
        }
    }

    @Override
    public int m_214116_(RandomSource p_225554_, int p_225555_, TreeConfiguration p_225556_) {
        return 4;
    }

    @Override
    protected boolean m_214202_(RandomSource p_225568_, int p_225569_, int p_225570_, int p_225571_, int p_225572_, boolean p_225573_) {
        if (!(p_225570_ != 0 || !p_225573_ || p_225569_ != -p_225572_ && p_225569_ < p_225572_ || p_225571_ != -p_225572_ && p_225571_ < p_225572_)) {
            return true;
        }
        return super.m_214202_(p_225568_, p_225569_, p_225570_, p_225571_, p_225572_, p_225573_);
    }

    @Override
    protected boolean m_214203_(RandomSource p_225547_, int p_225548_, int p_225549_, int p_225550_, int p_225551_, boolean p_225552_) {
        if (p_225549_ == -1 && !p_225552_) {
            return p_225548_ == p_225551_ && p_225550_ == p_225551_;
        }
        if (p_225549_ == 1) {
            return p_225548_ + p_225550_ > p_225551_ * 2 - 2;
        }
        return false;
    }
}


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

public class PineFoliagePlacer
extends FoliagePlacer {
    public static final Codec<PineFoliagePlacer> f_68676_ = RecordCodecBuilder.create(p_68698_ -> PineFoliagePlacer.m_68573_(p_68698_).and((App)IntProvider.m_146545_(0, 24).fieldOf("height").forGetter(p_161500_ -> p_161500_.f_68677_)).apply((Applicative)p_68698_, PineFoliagePlacer::new));
    private final IntProvider f_68677_;

    public PineFoliagePlacer(IntProvider p_161486_, IntProvider p_161487_, IntProvider p_161488_) {
        super(p_161486_, p_161487_);
        this.f_68677_ = p_161488_;
    }

    @Override
    protected FoliagePlacerType<?> m_5897_() {
        return FoliagePlacerType.f_68593_;
    }

    @Override
    protected void m_213633_(LevelSimulatedReader p_225702_, BiConsumer<BlockPos, BlockState> p_225703_, RandomSource p_225704_, TreeConfiguration p_225705_, int p_225706_, FoliagePlacer.FoliageAttachment p_225707_, int p_225708_, int p_225709_, int p_225710_) {
        int $$9 = 0;
        for (int $$10 = p_225710_; $$10 >= p_225710_ - p_225708_; --$$10) {
            this.m_225628_(p_225702_, p_225703_, p_225704_, p_225705_, p_225707_.m_161451_(), $$9, $$10, p_225707_.m_68590_());
            if ($$9 >= 1 && $$10 == p_225710_ - p_225708_ + 1) {
                --$$9;
                continue;
            }
            if ($$9 >= p_225709_ + p_225707_.m_68589_()) continue;
            ++$$9;
        }
    }

    @Override
    public int m_214117_(RandomSource p_225688_, int p_225689_) {
        return super.m_214117_(p_225688_, p_225689_) + p_225688_.m_188503_(Math.max(p_225689_ + 1, 1));
    }

    @Override
    public int m_214116_(RandomSource p_225698_, int p_225699_, TreeConfiguration p_225700_) {
        return this.f_68677_.m_214085_(p_225698_);
    }

    @Override
    protected boolean m_214203_(RandomSource p_225691_, int p_225692_, int p_225693_, int p_225694_, int p_225695_, boolean p_225696_) {
        return p_225692_ == p_225695_ && p_225694_ == p_225695_ && p_225695_ > 0;
    }
}


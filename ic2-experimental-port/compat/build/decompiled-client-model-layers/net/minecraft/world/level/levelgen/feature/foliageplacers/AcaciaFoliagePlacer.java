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

public class AcaciaFoliagePlacer
extends FoliagePlacer {
    public static final Codec<AcaciaFoliagePlacer> f_68362_ = RecordCodecBuilder.create(p_68380_ -> AcaciaFoliagePlacer.m_68573_(p_68380_).apply((Applicative)p_68380_, AcaciaFoliagePlacer::new));

    public AcaciaFoliagePlacer(IntProvider p_161343_, IntProvider p_161344_) {
        super(p_161343_, p_161344_);
    }

    @Override
    protected FoliagePlacerType<?> m_5897_() {
        return FoliagePlacerType.f_68594_;
    }

    @Override
    protected void m_213633_(LevelSimulatedReader p_225499_, BiConsumer<BlockPos, BlockState> p_225500_, RandomSource p_225501_, TreeConfiguration p_225502_, int p_225503_, FoliagePlacer.FoliageAttachment p_225504_, int p_225505_, int p_225506_, int p_225507_) {
        boolean $$9 = p_225504_.m_68590_();
        BlockPos $$10 = p_225504_.m_161451_().m_6630_(p_225507_);
        this.m_225628_(p_225499_, p_225500_, p_225501_, p_225502_, $$10, p_225506_ + p_225504_.m_68589_(), -1 - p_225505_, $$9);
        this.m_225628_(p_225499_, p_225500_, p_225501_, p_225502_, $$10, p_225506_ - 1, -p_225505_, $$9);
        this.m_225628_(p_225499_, p_225500_, p_225501_, p_225502_, $$10, p_225506_ + p_225504_.m_68589_() - 1, 0, $$9);
    }

    @Override
    public int m_214116_(RandomSource p_225495_, int p_225496_, TreeConfiguration p_225497_) {
        return 0;
    }

    @Override
    protected boolean m_214203_(RandomSource p_225488_, int p_225489_, int p_225490_, int p_225491_, int p_225492_, boolean p_225493_) {
        if (p_225490_ == 0) {
            return (p_225489_ > 1 || p_225491_ > 1) && p_225489_ != 0 && p_225491_ != 0;
        }
        return p_225489_ == p_225492_ && p_225491_ == p_225492_ && p_225492_ > 0;
    }
}


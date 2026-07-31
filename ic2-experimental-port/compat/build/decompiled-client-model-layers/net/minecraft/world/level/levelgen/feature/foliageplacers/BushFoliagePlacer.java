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
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class BushFoliagePlacer
extends BlobFoliagePlacer {
    public static final Codec<BushFoliagePlacer> f_68428_ = RecordCodecBuilder.create(p_68454_ -> BushFoliagePlacer.m_68413_(p_68454_).apply((Applicative)p_68454_, BushFoliagePlacer::new));

    public BushFoliagePlacer(IntProvider p_161370_, IntProvider p_161371_, int p_161372_) {
        super(p_161370_, p_161371_, p_161372_);
    }

    @Override
    protected FoliagePlacerType<?> m_5897_() {
        return FoliagePlacerType.f_68595_;
    }

    @Override
    protected void m_213633_(LevelSimulatedReader p_225537_, BiConsumer<BlockPos, BlockState> p_225538_, RandomSource p_225539_, TreeConfiguration p_225540_, int p_225541_, FoliagePlacer.FoliageAttachment p_225542_, int p_225543_, int p_225544_, int p_225545_) {
        for (int $$9 = p_225545_; $$9 >= p_225545_ - p_225543_; --$$9) {
            int $$10 = p_225544_ + p_225542_.m_68589_() - 1 - $$9;
            this.m_225628_(p_225537_, p_225538_, p_225539_, p_225540_, p_225542_.m_161451_(), $$10, $$9, p_225542_.m_68590_());
        }
    }

    @Override
    protected boolean m_214203_(RandomSource p_225530_, int p_225531_, int p_225532_, int p_225533_, int p_225534_, boolean p_225535_) {
        return p_225531_ == p_225534_ && p_225533_ == p_225534_ && p_225530_.m_188503_(2) == 0;
    }
}


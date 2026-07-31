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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class FancyFoliagePlacer
extends BlobFoliagePlacer {
    public static final Codec<FancyFoliagePlacer> f_68492_ = RecordCodecBuilder.create(p_68518_ -> FancyFoliagePlacer.m_68413_(p_68518_).apply((Applicative)p_68518_, FancyFoliagePlacer::new));

    public FancyFoliagePlacer(IntProvider p_161397_, IntProvider p_161398_, int p_161399_) {
        super(p_161397_, p_161398_, p_161399_);
    }

    @Override
    protected FoliagePlacerType<?> m_5897_() {
        return FoliagePlacerType.f_68596_;
    }

    @Override
    protected void m_213633_(LevelSimulatedReader p_225582_, BiConsumer<BlockPos, BlockState> p_225583_, RandomSource p_225584_, TreeConfiguration p_225585_, int p_225586_, FoliagePlacer.FoliageAttachment p_225587_, int p_225588_, int p_225589_, int p_225590_) {
        for (int $$9 = p_225590_; $$9 >= p_225590_ - p_225588_; --$$9) {
            int $$10 = p_225589_ + ($$9 == p_225590_ || $$9 == p_225590_ - p_225588_ ? 0 : 1);
            this.m_225628_(p_225582_, p_225583_, p_225584_, p_225585_, p_225587_.m_161451_(), $$10, $$9, p_225587_.m_68590_());
        }
    }

    @Override
    protected boolean m_214203_(RandomSource p_225575_, int p_225576_, int p_225577_, int p_225578_, int p_225579_, boolean p_225580_) {
        return Mth.m_14207_((float)p_225576_ + 0.5f) + Mth.m_14207_((float)p_225578_ + 0.5f) > (float)(p_225579_ * p_225579_);
    }
}


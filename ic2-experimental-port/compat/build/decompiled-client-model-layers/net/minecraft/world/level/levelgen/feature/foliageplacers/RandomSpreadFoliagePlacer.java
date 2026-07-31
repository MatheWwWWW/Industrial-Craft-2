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

public class RandomSpreadFoliagePlacer
extends FoliagePlacer {
    public static final Codec<RandomSpreadFoliagePlacer> f_161501_ = RecordCodecBuilder.create(p_161522_ -> RandomSpreadFoliagePlacer.m_68573_(p_161522_).and(p_161522_.group((App)IntProvider.m_146545_(1, 512).fieldOf("foliage_height").forGetter(p_161537_ -> p_161537_.f_161502_), (App)Codec.intRange((int)0, (int)256).fieldOf("leaf_placement_attempts").forGetter(p_161524_ -> p_161524_.f_161503_))).apply((Applicative)p_161522_, RandomSpreadFoliagePlacer::new));
    private final IntProvider f_161502_;
    private final int f_161503_;

    public RandomSpreadFoliagePlacer(IntProvider p_161506_, IntProvider p_161507_, IntProvider p_161508_, int p_161509_) {
        super(p_161506_, p_161507_);
        this.f_161502_ = p_161508_;
        this.f_161503_ = p_161509_;
    }

    @Override
    protected FoliagePlacerType<?> m_5897_() {
        return FoliagePlacerType.f_161452_;
    }

    @Override
    protected void m_213633_(LevelSimulatedReader p_225723_, BiConsumer<BlockPos, BlockState> p_225724_, RandomSource p_225725_, TreeConfiguration p_225726_, int p_225727_, FoliagePlacer.FoliageAttachment p_225728_, int p_225729_, int p_225730_, int p_225731_) {
        BlockPos $$9 = p_225728_.m_161451_();
        BlockPos.MutableBlockPos $$10 = $$9.m_122032_();
        for (int $$11 = 0; $$11 < this.f_161503_; ++$$11) {
            $$10.m_122154_($$9, p_225725_.m_188503_(p_225730_) - p_225725_.m_188503_(p_225730_), p_225725_.m_188503_(p_225729_) - p_225725_.m_188503_(p_225729_), p_225725_.m_188503_(p_225730_) - p_225725_.m_188503_(p_225730_));
            RandomSpreadFoliagePlacer.m_225622_(p_225723_, p_225724_, p_225725_, p_225726_, $$10);
        }
    }

    @Override
    public int m_214116_(RandomSource p_225719_, int p_225720_, TreeConfiguration p_225721_) {
        return this.f_161502_.m_214085_(p_225719_);
    }

    @Override
    protected boolean m_214203_(RandomSource p_225712_, int p_225713_, int p_225714_, int p_225715_, int p_225716_, boolean p_225717_) {
        return false;
    }
}


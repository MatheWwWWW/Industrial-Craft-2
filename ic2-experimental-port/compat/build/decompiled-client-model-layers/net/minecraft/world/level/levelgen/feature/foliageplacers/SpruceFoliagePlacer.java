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

public class SpruceFoliagePlacer
extends FoliagePlacer {
    public static final Codec<SpruceFoliagePlacer> f_68713_ = RecordCodecBuilder.create(p_68735_ -> SpruceFoliagePlacer.m_68573_(p_68735_).and((App)IntProvider.m_146545_(0, 24).fieldOf("trunk_height").forGetter(p_161553_ -> p_161553_.f_68714_)).apply((Applicative)p_68735_, SpruceFoliagePlacer::new));
    private final IntProvider f_68714_;

    public SpruceFoliagePlacer(IntProvider p_161539_, IntProvider p_161540_, IntProvider p_161541_) {
        super(p_161539_, p_161540_);
        this.f_68714_ = p_161541_;
    }

    @Override
    protected FoliagePlacerType<?> m_5897_() {
        return FoliagePlacerType.f_68592_;
    }

    @Override
    protected void m_213633_(LevelSimulatedReader p_225744_, BiConsumer<BlockPos, BlockState> p_225745_, RandomSource p_225746_, TreeConfiguration p_225747_, int p_225748_, FoliagePlacer.FoliageAttachment p_225749_, int p_225750_, int p_225751_, int p_225752_) {
        BlockPos $$9 = p_225749_.m_161451_();
        int $$10 = p_225746_.m_188503_(2);
        int $$11 = 1;
        int $$12 = 0;
        for (int $$13 = p_225752_; $$13 >= -p_225750_; --$$13) {
            this.m_225628_(p_225744_, p_225745_, p_225746_, p_225747_, $$9, $$10, $$13, p_225749_.m_68590_());
            if ($$10 >= $$11) {
                $$10 = $$12;
                $$12 = 1;
                $$11 = Math.min($$11 + 1, p_225751_ + p_225749_.m_68589_());
                continue;
            }
            ++$$10;
        }
    }

    @Override
    public int m_214116_(RandomSource p_225740_, int p_225741_, TreeConfiguration p_225742_) {
        return Math.max(4, p_225741_ - this.f_68714_.m_214085_(p_225740_));
    }

    @Override
    protected boolean m_214203_(RandomSource p_225733_, int p_225734_, int p_225735_, int p_225736_, int p_225737_, boolean p_225738_) {
        return p_225734_ == p_225737_ && p_225736_ == p_225737_ && p_225737_ > 0;
    }
}


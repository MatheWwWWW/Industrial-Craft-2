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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class MegaPineFoliagePlacer
extends FoliagePlacer {
    public static final Codec<MegaPineFoliagePlacer> f_68642_ = RecordCodecBuilder.create(p_68664_ -> MegaPineFoliagePlacer.m_68573_(p_68664_).and((App)IntProvider.m_146545_(0, 24).fieldOf("crown_height").forGetter(p_161484_ -> p_161484_.f_68643_)).apply((Applicative)p_68664_, MegaPineFoliagePlacer::new));
    private final IntProvider f_68643_;

    public MegaPineFoliagePlacer(IntProvider p_161470_, IntProvider p_161471_, IntProvider p_161472_) {
        super(p_161470_, p_161471_);
        this.f_68643_ = p_161472_;
    }

    @Override
    protected FoliagePlacerType<?> m_5897_() {
        return FoliagePlacerType.f_68598_;
    }

    @Override
    protected void m_213633_(LevelSimulatedReader p_225678_, BiConsumer<BlockPos, BlockState> p_225679_, RandomSource p_225680_, TreeConfiguration p_225681_, int p_225682_, FoliagePlacer.FoliageAttachment p_225683_, int p_225684_, int p_225685_, int p_225686_) {
        BlockPos $$9 = p_225683_.m_161451_();
        int $$10 = 0;
        for (int $$11 = $$9.m_123342_() - p_225684_ + p_225686_; $$11 <= $$9.m_123342_() + p_225686_; ++$$11) {
            int $$15;
            int $$12 = $$9.m_123342_() - $$11;
            int $$13 = p_225685_ + p_225683_.m_68589_() + Mth.m_14143_((float)$$12 / (float)p_225684_ * 3.5f);
            if ($$12 > 0 && $$13 == $$10 && ($$11 & 1) == 0) {
                int $$14 = $$13 + 1;
            } else {
                $$15 = $$13;
            }
            this.m_225628_(p_225678_, p_225679_, p_225680_, p_225681_, new BlockPos($$9.m_123341_(), $$11, $$9.m_123343_()), $$15, 0, p_225683_.m_68590_());
            $$10 = $$13;
        }
    }

    @Override
    public int m_214116_(RandomSource p_225674_, int p_225675_, TreeConfiguration p_225676_) {
        return this.f_68643_.m_214085_(p_225674_);
    }

    @Override
    protected boolean m_214203_(RandomSource p_225667_, int p_225668_, int p_225669_, int p_225670_, int p_225671_, boolean p_225672_) {
        if (p_225668_ + p_225670_ >= 7) {
            return true;
        }
        return p_225668_ * p_225668_ + p_225670_ * p_225670_ > p_225671_ * p_225671_;
    }
}


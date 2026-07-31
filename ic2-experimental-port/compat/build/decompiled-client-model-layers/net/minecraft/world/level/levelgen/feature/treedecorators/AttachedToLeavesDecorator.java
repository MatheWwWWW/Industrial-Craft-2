/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashSet;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class AttachedToLeavesDecorator
extends TreeDecorator {
    public static final Codec<AttachedToLeavesDecorator> f_225979_ = RecordCodecBuilder.create(p_225996_ -> p_225996_.group((App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("probability").forGetter(p_226014_ -> Float.valueOf(p_226014_.f_225980_)), (App)Codec.intRange((int)0, (int)16).fieldOf("exclusion_radius_xz").forGetter(p_226012_ -> p_226012_.f_225981_), (App)Codec.intRange((int)0, (int)16).fieldOf("exclusion_radius_y").forGetter(p_226010_ -> p_226010_.f_225982_), (App)BlockStateProvider.f_68747_.fieldOf("block_provider").forGetter(p_226008_ -> p_226008_.f_225983_), (App)Codec.intRange((int)1, (int)16).fieldOf("required_empty_blocks").forGetter(p_226006_ -> p_226006_.f_225984_), (App)ExtraCodecs.m_144637_(Direction.f_175356_.listOf()).fieldOf("directions").forGetter(p_225998_ -> p_225998_.f_225985_)).apply((Applicative)p_225996_, AttachedToLeavesDecorator::new));
    protected final float f_225980_;
    protected final int f_225981_;
    protected final int f_225982_;
    protected final BlockStateProvider f_225983_;
    protected final int f_225984_;
    protected final List<Direction> f_225985_;

    public AttachedToLeavesDecorator(float p_225988_, int p_225989_, int p_225990_, BlockStateProvider p_225991_, int p_225992_, List<Direction> p_225993_) {
        this.f_225980_ = p_225988_;
        this.f_225981_ = p_225989_;
        this.f_225982_ = p_225990_;
        this.f_225983_ = p_225991_;
        this.f_225984_ = p_225992_;
        this.f_225985_ = p_225993_;
    }

    @Override
    public void m_214187_(TreeDecorator.Context p_226000_) {
        HashSet<BlockPos> $$1 = new HashSet<BlockPos>();
        RandomSource $$2 = p_226000_.m_226067_();
        for (BlockPos $$3 : Util.m_214611_(p_226000_.m_226069_(), $$2)) {
            Direction $$4;
            BlockPos $$5 = $$3.m_121945_($$4 = Util.m_214621_(this.f_225985_, $$2));
            if ($$1.contains($$5) || !($$2.m_188501_() < this.f_225980_) || !this.m_226001_(p_226000_, $$3, $$4)) continue;
            BlockPos $$6 = $$5.m_7918_(-this.f_225981_, -this.f_225982_, -this.f_225981_);
            BlockPos $$7 = $$5.m_7918_(this.f_225981_, this.f_225982_, this.f_225981_);
            for (BlockPos $$8 : BlockPos.m_121940_($$6, $$7)) {
                $$1.add($$8.m_7949_());
            }
            p_226000_.m_226061_($$5, this.f_225983_.m_213972_($$2, $$5));
        }
    }

    private boolean m_226001_(TreeDecorator.Context p_226002_, BlockPos p_226003_, Direction p_226004_) {
        for (int $$3 = 1; $$3 <= this.f_225984_; ++$$3) {
            BlockPos $$4 = p_226003_.m_5484_(p_226004_, $$3);
            if (p_226002_.m_226059_($$4)) continue;
            return false;
        }
        return true;
    }

    @Override
    protected TreeDecoratorType<?> m_6663_() {
        return TreeDecoratorType.f_226071_;
    }
}


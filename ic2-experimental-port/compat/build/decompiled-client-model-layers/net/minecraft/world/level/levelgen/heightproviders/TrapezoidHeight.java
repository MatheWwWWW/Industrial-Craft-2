/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;
import org.slf4j.Logger;

public class TrapezoidHeight
extends HeightProvider {
    public static final Codec<TrapezoidHeight> f_161993_ = RecordCodecBuilder.create(p_162005_ -> p_162005_.group((App)VerticalAnchor.f_158914_.fieldOf("min_inclusive").forGetter(p_162021_ -> p_162021_.f_161995_), (App)VerticalAnchor.f_158914_.fieldOf("max_inclusive").forGetter(p_162019_ -> p_162019_.f_161996_), (App)Codec.INT.optionalFieldOf("plateau", (Object)0).forGetter(p_162014_ -> p_162014_.f_161997_)).apply((Applicative)p_162005_, TrapezoidHeight::new));
    private static final Logger f_161994_ = LogUtils.getLogger();
    private final VerticalAnchor f_161995_;
    private final VerticalAnchor f_161996_;
    private final int f_161997_;

    private TrapezoidHeight(VerticalAnchor p_162000_, VerticalAnchor p_162001_, int p_162002_) {
        this.f_161995_ = p_162000_;
        this.f_161996_ = p_162001_;
        this.f_161997_ = p_162002_;
    }

    public static TrapezoidHeight m_162009_(VerticalAnchor p_162010_, VerticalAnchor p_162011_, int p_162012_) {
        return new TrapezoidHeight(p_162010_, p_162011_, p_162012_);
    }

    public static TrapezoidHeight m_162006_(VerticalAnchor p_162007_, VerticalAnchor p_162008_) {
        return TrapezoidHeight.m_162009_(p_162007_, p_162008_, 0);
    }

    @Override
    public int m_213859_(RandomSource p_226305_, WorldGenerationContext p_226306_) {
        int $$3;
        int $$2 = this.f_161995_.m_142322_(p_226306_);
        if ($$2 > ($$3 = this.f_161996_.m_142322_(p_226306_))) {
            f_161994_.warn("Empty height range: {}", (Object)this);
            return $$2;
        }
        int $$4 = $$3 - $$2;
        if (this.f_161997_ >= $$4) {
            return Mth.m_216287_(p_226305_, $$2, $$3);
        }
        int $$5 = ($$4 - this.f_161997_) / 2;
        int $$6 = $$4 - $$5;
        return $$2 + Mth.m_216287_(p_226305_, 0, $$6) + Mth.m_216287_(p_226305_, 0, $$5);
    }

    @Override
    public HeightProviderType<?> m_142002_() {
        return HeightProviderType.f_161985_;
    }

    public String toString() {
        if (this.f_161997_ == 0) {
            return "triangle (" + this.f_161995_ + "-" + this.f_161996_ + ")";
        }
        return "trapezoid(" + this.f_161997_ + ") in [" + this.f_161995_ + "-" + this.f_161996_ + "]";
    }
}


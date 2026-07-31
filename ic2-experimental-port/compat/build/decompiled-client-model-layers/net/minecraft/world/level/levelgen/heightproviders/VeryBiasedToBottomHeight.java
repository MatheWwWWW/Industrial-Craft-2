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

public class VeryBiasedToBottomHeight
extends HeightProvider {
    public static final Codec<VeryBiasedToBottomHeight> f_162045_ = RecordCodecBuilder.create(p_162057_ -> p_162057_.group((App)VerticalAnchor.f_158914_.fieldOf("min_inclusive").forGetter(p_162070_ -> p_162070_.f_162047_), (App)VerticalAnchor.f_158914_.fieldOf("max_inclusive").forGetter(p_162068_ -> p_162068_.f_162048_), (App)Codec.intRange((int)1, (int)Integer.MAX_VALUE).optionalFieldOf("inner", (Object)1).forGetter(p_162063_ -> p_162063_.f_162049_)).apply((Applicative)p_162057_, VeryBiasedToBottomHeight::new));
    private static final Logger f_162046_ = LogUtils.getLogger();
    private final VerticalAnchor f_162047_;
    private final VerticalAnchor f_162048_;
    private final int f_162049_;

    private VeryBiasedToBottomHeight(VerticalAnchor p_162052_, VerticalAnchor p_162053_, int p_162054_) {
        this.f_162047_ = p_162052_;
        this.f_162048_ = p_162053_;
        this.f_162049_ = p_162054_;
    }

    public static VeryBiasedToBottomHeight m_162058_(VerticalAnchor p_162059_, VerticalAnchor p_162060_, int p_162061_) {
        return new VeryBiasedToBottomHeight(p_162059_, p_162060_, p_162061_);
    }

    @Override
    public int m_213859_(RandomSource p_226311_, WorldGenerationContext p_226312_) {
        int $$2 = this.f_162047_.m_142322_(p_226312_);
        int $$3 = this.f_162048_.m_142322_(p_226312_);
        if ($$3 - $$2 - this.f_162049_ + 1 <= 0) {
            f_162046_.warn("Empty height range: {}", (Object)this);
            return $$2;
        }
        int $$4 = Mth.m_216271_(p_226311_, $$2 + this.f_162049_, $$3);
        int $$5 = Mth.m_216271_(p_226311_, $$2, $$4 - 1);
        return Mth.m_216271_(p_226311_, $$2, $$5 - 1 + this.f_162049_);
    }

    @Override
    public HeightProviderType<?> m_142002_() {
        return HeightProviderType.f_161984_;
    }

    public String toString() {
        return "biased[" + this.f_162047_ + "-" + this.f_162048_ + " inner: " + this.f_162049_ + "]";
    }
}


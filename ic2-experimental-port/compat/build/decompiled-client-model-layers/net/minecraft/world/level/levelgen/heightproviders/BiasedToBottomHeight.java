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
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;
import org.slf4j.Logger;

public class BiasedToBottomHeight
extends HeightProvider {
    public static final Codec<BiasedToBottomHeight> f_161918_ = RecordCodecBuilder.create(p_161930_ -> p_161930_.group((App)VerticalAnchor.f_158914_.fieldOf("min_inclusive").forGetter(p_161943_ -> p_161943_.f_161920_), (App)VerticalAnchor.f_158914_.fieldOf("max_inclusive").forGetter(p_161941_ -> p_161941_.f_161921_), (App)Codec.intRange((int)1, (int)Integer.MAX_VALUE).optionalFieldOf("inner", (Object)1).forGetter(p_161936_ -> p_161936_.f_161922_)).apply((Applicative)p_161930_, BiasedToBottomHeight::new));
    private static final Logger f_161919_ = LogUtils.getLogger();
    private final VerticalAnchor f_161920_;
    private final VerticalAnchor f_161921_;
    private final int f_161922_;

    private BiasedToBottomHeight(VerticalAnchor p_161925_, VerticalAnchor p_161926_, int p_161927_) {
        this.f_161920_ = p_161925_;
        this.f_161921_ = p_161926_;
        this.f_161922_ = p_161927_;
    }

    public static BiasedToBottomHeight m_161931_(VerticalAnchor p_161932_, VerticalAnchor p_161933_, int p_161934_) {
        return new BiasedToBottomHeight(p_161932_, p_161933_, p_161934_);
    }

    @Override
    public int m_213859_(RandomSource p_226297_, WorldGenerationContext p_226298_) {
        int $$2 = this.f_161920_.m_142322_(p_226298_);
        int $$3 = this.f_161921_.m_142322_(p_226298_);
        if ($$3 - $$2 - this.f_161922_ + 1 <= 0) {
            f_161919_.warn("Empty height range: {}", (Object)this);
            return $$2;
        }
        int $$4 = p_226297_.m_188503_($$3 - $$2 - this.f_161922_ + 1);
        return p_226297_.m_188503_($$4 + this.f_161922_) + $$2;
    }

    @Override
    public HeightProviderType<?> m_142002_() {
        return HeightProviderType.f_161983_;
    }

    public String toString() {
        return "biased[" + this.f_161920_ + "-" + this.f_161921_ + " inner: " + this.f_161922_ + "]";
    }
}


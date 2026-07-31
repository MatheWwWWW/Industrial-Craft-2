/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;
import org.slf4j.Logger;

public class UniformHeight
extends HeightProvider {
    public static final Codec<UniformHeight> f_162023_ = RecordCodecBuilder.create(p_162033_ -> p_162033_.group((App)VerticalAnchor.f_158914_.fieldOf("min_inclusive").forGetter(p_162043_ -> p_162043_.f_162025_), (App)VerticalAnchor.f_158914_.fieldOf("max_inclusive").forGetter(p_162038_ -> p_162038_.f_162026_)).apply((Applicative)p_162033_, UniformHeight::new));
    private static final Logger f_162024_ = LogUtils.getLogger();
    private final VerticalAnchor f_162025_;
    private final VerticalAnchor f_162026_;
    private final LongSet f_198374_ = new LongOpenHashSet();

    private UniformHeight(VerticalAnchor p_162029_, VerticalAnchor p_162030_) {
        this.f_162025_ = p_162029_;
        this.f_162026_ = p_162030_;
    }

    public static UniformHeight m_162034_(VerticalAnchor p_162035_, VerticalAnchor p_162036_) {
        return new UniformHeight(p_162035_, p_162036_);
    }

    @Override
    public int m_213859_(RandomSource p_226308_, WorldGenerationContext p_226309_) {
        int $$3;
        int $$2 = this.f_162025_.m_142322_(p_226309_);
        if ($$2 > ($$3 = this.f_162026_.m_142322_(p_226309_))) {
            if (this.f_198374_.add((long)$$2 << 32 | (long)$$3)) {
                f_162024_.warn("Empty height range: {}", (Object)this);
            }
            return $$2;
        }
        return Mth.m_216287_(p_226308_, $$2, $$3);
    }

    @Override
    public HeightProviderType<?> m_142002_() {
        return HeightProviderType.f_161982_;
    }

    public String toString() {
        return "[" + this.f_162025_ + "-" + this.f_162026_ + "]";
    }
}


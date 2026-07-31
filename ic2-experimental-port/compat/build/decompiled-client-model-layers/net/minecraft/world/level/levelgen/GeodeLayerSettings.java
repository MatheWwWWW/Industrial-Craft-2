/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class GeodeLayerSettings {
    private static final Codec<Double> f_158346_ = Codec.doubleRange((double)0.01, (double)50.0);
    public static final Codec<GeodeLayerSettings> f_158341_ = RecordCodecBuilder.create(p_158354_ -> p_158354_.group((App)f_158346_.fieldOf("filling").orElse((Object)1.7).forGetter(p_158362_ -> p_158362_.f_158342_), (App)f_158346_.fieldOf("inner_layer").orElse((Object)2.2).forGetter(p_158360_ -> p_158360_.f_158343_), (App)f_158346_.fieldOf("middle_layer").orElse((Object)3.2).forGetter(p_158358_ -> p_158358_.f_158344_), (App)f_158346_.fieldOf("outer_layer").orElse((Object)4.2).forGetter(p_158356_ -> p_158356_.f_158345_)).apply((Applicative)p_158354_, GeodeLayerSettings::new));
    public final double f_158342_;
    public final double f_158343_;
    public final double f_158344_;
    public final double f_158345_;

    public GeodeLayerSettings(double p_158349_, double p_158350_, double p_158351_, double p_158352_) {
        this.f_158342_ = p_158349_;
        this.f_158343_ = p_158350_;
        this.f_158344_ = p_158351_;
        this.f_158345_ = p_158352_;
    }
}


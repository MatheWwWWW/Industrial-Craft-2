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
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;

public class GeodeCrackSettings {
    public static final Codec<GeodeCrackSettings> f_158324_ = RecordCodecBuilder.create(p_158334_ -> p_158334_.group((App)GeodeConfiguration.f_160811_.fieldOf("generate_crack_chance").orElse((Object)1.0).forGetter(p_158340_ -> p_158340_.f_158325_), (App)Codec.doubleRange((double)0.0, (double)5.0).fieldOf("base_crack_size").orElse((Object)2.0).forGetter(p_158338_ -> p_158338_.f_158326_), (App)Codec.intRange((int)0, (int)10).fieldOf("crack_point_offset").orElse((Object)2).forGetter(p_158336_ -> p_158336_.f_158327_)).apply((Applicative)p_158334_, GeodeCrackSettings::new));
    public final double f_158325_;
    public final double f_158326_;
    public final int f_158327_;

    public GeodeCrackSettings(double p_158330_, double p_158331_, int p_158332_) {
        this.f_158325_ = p_158330_;
        this.f_158326_ = p_158331_;
        this.f_158327_ = p_158332_;
    }
}


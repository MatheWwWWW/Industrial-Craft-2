/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class UnderwaterMagmaConfiguration
implements FeatureConfiguration {
    public static final Codec<UnderwaterMagmaConfiguration> f_161263_ = RecordCodecBuilder.create(p_161273_ -> p_161273_.group((App)Codec.intRange((int)0, (int)512).fieldOf("floor_search_range").forGetter(p_161279_ -> p_161279_.f_161264_), (App)Codec.intRange((int)0, (int)64).fieldOf("placement_radius_around_floor").forGetter(p_161277_ -> p_161277_.f_161265_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("placement_probability_per_valid_position").forGetter(p_161275_ -> Float.valueOf(p_161275_.f_161266_))).apply((Applicative)p_161273_, UnderwaterMagmaConfiguration::new));
    public final int f_161264_;
    public final int f_161265_;
    public final float f_161266_;

    public UnderwaterMagmaConfiguration(int p_161269_, int p_161270_, float p_161271_) {
        this.f_161264_ = p_161269_;
        this.f_161265_ = p_161270_;
        this.f_161266_ = p_161271_;
    }
}


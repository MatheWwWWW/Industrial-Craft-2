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
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class LargeDripstoneConfiguration
implements FeatureConfiguration {
    public static final Codec<LargeDripstoneConfiguration> f_160944_ = RecordCodecBuilder.create(p_160966_ -> p_160966_.group((App)Codec.intRange((int)1, (int)512).fieldOf("floor_to_ceiling_search_range").orElse((Object)30).forGetter(p_160984_ -> p_160984_.f_160945_), (App)IntProvider.m_146545_(1, 60).fieldOf("column_radius").forGetter(p_160982_ -> p_160982_.f_160946_), (App)FloatProvider.m_146505_(0.0f, 20.0f).fieldOf("height_scale").forGetter(p_160980_ -> p_160980_.f_160947_), (App)Codec.floatRange((float)0.1f, (float)1.0f).fieldOf("max_column_radius_to_cave_height_ratio").forGetter(p_160978_ -> Float.valueOf(p_160978_.f_160948_)), (App)FloatProvider.m_146505_(0.1f, 10.0f).fieldOf("stalactite_bluntness").forGetter(p_160976_ -> p_160976_.f_160949_), (App)FloatProvider.m_146505_(0.1f, 10.0f).fieldOf("stalagmite_bluntness").forGetter(p_160974_ -> p_160974_.f_160950_), (App)FloatProvider.m_146505_(0.0f, 2.0f).fieldOf("wind_speed").forGetter(p_160972_ -> p_160972_.f_160951_), (App)Codec.intRange((int)0, (int)100).fieldOf("min_radius_for_wind").forGetter(p_160970_ -> p_160970_.f_160952_), (App)Codec.floatRange((float)0.0f, (float)5.0f).fieldOf("min_bluntness_for_wind").forGetter(p_160968_ -> Float.valueOf(p_160968_.f_160953_))).apply((Applicative)p_160966_, LargeDripstoneConfiguration::new));
    public final int f_160945_;
    public final IntProvider f_160946_;
    public final FloatProvider f_160947_;
    public final float f_160948_;
    public final FloatProvider f_160949_;
    public final FloatProvider f_160950_;
    public final FloatProvider f_160951_;
    public final int f_160952_;
    public final float f_160953_;

    public LargeDripstoneConfiguration(int p_160956_, IntProvider p_160957_, FloatProvider p_160958_, float p_160959_, FloatProvider p_160960_, FloatProvider p_160961_, FloatProvider p_160962_, int p_160963_, float p_160964_) {
        this.f_160945_ = p_160956_;
        this.f_160946_ = p_160957_;
        this.f_160947_ = p_160958_;
        this.f_160948_ = p_160959_;
        this.f_160949_ = p_160960_;
        this.f_160950_ = p_160961_;
        this.f_160951_ = p_160962_;
        this.f_160952_ = p_160963_;
        this.f_160953_ = p_160964_;
    }
}


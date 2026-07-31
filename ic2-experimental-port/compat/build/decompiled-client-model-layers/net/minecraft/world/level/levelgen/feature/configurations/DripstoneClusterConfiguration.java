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

public class DripstoneClusterConfiguration
implements FeatureConfiguration {
    public static final Codec<DripstoneClusterConfiguration> f_160758_ = RecordCodecBuilder.create(p_160784_ -> p_160784_.group((App)Codec.intRange((int)1, (int)512).fieldOf("floor_to_ceiling_search_range").forGetter(p_160806_ -> p_160806_.f_160759_), (App)IntProvider.m_146545_(1, 128).fieldOf("height").forGetter(p_160804_ -> p_160804_.f_160760_), (App)IntProvider.m_146545_(1, 128).fieldOf("radius").forGetter(p_160802_ -> p_160802_.f_160761_), (App)Codec.intRange((int)0, (int)64).fieldOf("max_stalagmite_stalactite_height_diff").forGetter(p_160800_ -> p_160800_.f_160762_), (App)Codec.intRange((int)1, (int)64).fieldOf("height_deviation").forGetter(p_160798_ -> p_160798_.f_160763_), (App)IntProvider.m_146545_(0, 128).fieldOf("dripstone_block_layer_thickness").forGetter(p_160796_ -> p_160796_.f_160764_), (App)FloatProvider.m_146505_(0.0f, 2.0f).fieldOf("density").forGetter(p_160794_ -> p_160794_.f_160765_), (App)FloatProvider.m_146505_(0.0f, 2.0f).fieldOf("wetness").forGetter(p_160792_ -> p_160792_.f_160766_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("chance_of_dripstone_column_at_max_distance_from_center").forGetter(p_160790_ -> Float.valueOf(p_160790_.f_160767_)), (App)Codec.intRange((int)1, (int)64).fieldOf("max_distance_from_edge_affecting_chance_of_dripstone_column").forGetter(p_160788_ -> p_160788_.f_160768_), (App)Codec.intRange((int)1, (int)64).fieldOf("max_distance_from_center_affecting_height_bias").forGetter(p_160786_ -> p_160786_.f_160769_)).apply((Applicative)p_160784_, DripstoneClusterConfiguration::new));
    public final int f_160759_;
    public final IntProvider f_160760_;
    public final IntProvider f_160761_;
    public final int f_160762_;
    public final int f_160763_;
    public final IntProvider f_160764_;
    public final FloatProvider f_160765_;
    public final FloatProvider f_160766_;
    public final float f_160767_;
    public final int f_160768_;
    public final int f_160769_;

    public DripstoneClusterConfiguration(int p_160772_, IntProvider p_160773_, IntProvider p_160774_, int p_160775_, int p_160776_, IntProvider p_160777_, FloatProvider p_160778_, FloatProvider p_160779_, float p_160780_, int p_160781_, int p_160782_) {
        this.f_160759_ = p_160772_;
        this.f_160760_ = p_160773_;
        this.f_160761_ = p_160774_;
        this.f_160762_ = p_160775_;
        this.f_160763_ = p_160776_;
        this.f_160764_ = p_160777_;
        this.f_160765_ = p_160778_;
        this.f_160766_ = p_160779_;
        this.f_160767_ = p_160780_;
        this.f_160768_ = p_160781_;
        this.f_160769_ = p_160782_;
    }
}


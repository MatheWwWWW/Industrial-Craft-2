/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.data.worldgen.features;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;

public class EndFeatures {
    public static final Holder<ConfiguredFeature<SpikeConfiguration, ?>> f_194982_ = FeatureUtils.m_206488_("end_spike", Feature.f_65732_, new SpikeConfiguration(false, (List<SpikeFeature.EndSpike>)ImmutableList.of(), null));
    public static final Holder<ConfiguredFeature<EndGatewayConfiguration, ?>> f_194983_ = FeatureUtils.m_206488_("end_gateway_return", Feature.f_65734_, EndGatewayConfiguration.m_67650_(ServerLevel.f_8562_, true));
    public static final Holder<ConfiguredFeature<EndGatewayConfiguration, ?>> f_194984_ = FeatureUtils.m_206488_("end_gateway_delayed", Feature.f_65734_, EndGatewayConfiguration.m_67653_());
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_194985_ = FeatureUtils.m_206485_("chorus_plant", Feature.f_65766_);
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> f_194986_ = FeatureUtils.m_206485_("end_island", Feature.f_65733_);
}


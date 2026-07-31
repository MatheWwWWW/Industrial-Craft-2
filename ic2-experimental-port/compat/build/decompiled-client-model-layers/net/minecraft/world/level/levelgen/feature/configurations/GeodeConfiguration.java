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
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class GeodeConfiguration
implements FeatureConfiguration {
    public static final Codec<Double> f_160811_ = Codec.doubleRange((double)0.0, (double)1.0);
    public static final Codec<GeodeConfiguration> f_160812_ = RecordCodecBuilder.create(p_160842_ -> p_160842_.group((App)GeodeBlockSettings.f_158295_.fieldOf("blocks").forGetter(p_160868_ -> p_160868_.f_160813_), (App)GeodeLayerSettings.f_158341_.fieldOf("layers").forGetter(p_160866_ -> p_160866_.f_160814_), (App)GeodeCrackSettings.f_158324_.fieldOf("crack").forGetter(p_160864_ -> p_160864_.f_160815_), (App)f_160811_.fieldOf("use_potential_placements_chance").orElse((Object)0.35).forGetter(p_160862_ -> p_160862_.f_160816_), (App)f_160811_.fieldOf("use_alternate_layer0_chance").orElse((Object)0.0).forGetter(p_160860_ -> p_160860_.f_160817_), (App)Codec.BOOL.fieldOf("placements_require_layer0_alternate").orElse((Object)true).forGetter(p_160858_ -> p_160858_.f_160818_), (App)IntProvider.m_146545_(1, 20).fieldOf("outer_wall_distance").orElse((Object)UniformInt.m_146622_(4, 5)).forGetter(p_160856_ -> p_160856_.f_160819_), (App)IntProvider.m_146545_(1, 20).fieldOf("distribution_points").orElse((Object)UniformInt.m_146622_(3, 4)).forGetter(p_160854_ -> p_160854_.f_160820_), (App)IntProvider.m_146545_(0, 10).fieldOf("point_offset").orElse((Object)UniformInt.m_146622_(1, 2)).forGetter(p_160852_ -> p_160852_.f_160821_), (App)Codec.INT.fieldOf("min_gen_offset").orElse((Object)-16).forGetter(p_160850_ -> p_160850_.f_160822_), (App)Codec.INT.fieldOf("max_gen_offset").orElse((Object)16).forGetter(p_160848_ -> p_160848_.f_160823_), (App)f_160811_.fieldOf("noise_multiplier").orElse((Object)0.05).forGetter(p_160846_ -> p_160846_.f_160824_), (App)Codec.INT.fieldOf("invalid_blocks_threshold").forGetter(p_160844_ -> p_160844_.f_160825_)).apply((Applicative)p_160842_, GeodeConfiguration::new));
    public final GeodeBlockSettings f_160813_;
    public final GeodeLayerSettings f_160814_;
    public final GeodeCrackSettings f_160815_;
    public final double f_160816_;
    public final double f_160817_;
    public final boolean f_160818_;
    public final IntProvider f_160819_;
    public final IntProvider f_160820_;
    public final IntProvider f_160821_;
    public final int f_160822_;
    public final int f_160823_;
    public final double f_160824_;
    public final int f_160825_;

    public GeodeConfiguration(GeodeBlockSettings p_160828_, GeodeLayerSettings p_160829_, GeodeCrackSettings p_160830_, double p_160831_, double p_160832_, boolean p_160833_, IntProvider p_160834_, IntProvider p_160835_, IntProvider p_160836_, int p_160837_, int p_160838_, double p_160839_, int p_160840_) {
        this.f_160813_ = p_160828_;
        this.f_160814_ = p_160829_;
        this.f_160815_ = p_160830_;
        this.f_160816_ = p_160831_;
        this.f_160817_ = p_160832_;
        this.f_160818_ = p_160833_;
        this.f_160819_ = p_160834_;
        this.f_160820_ = p_160835_;
        this.f_160821_ = p_160836_;
        this.f_160822_ = p_160837_;
        this.f_160823_ = p_160838_;
        this.f_160824_ = p_160839_;
        this.f_160825_ = p_160840_;
    }
}


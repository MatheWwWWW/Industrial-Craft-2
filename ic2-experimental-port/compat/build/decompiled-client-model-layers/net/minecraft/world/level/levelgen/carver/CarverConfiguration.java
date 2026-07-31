/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.carver;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class CarverConfiguration
extends ProbabilityFeatureConfiguration {
    public static final MapCodec<CarverConfiguration> f_159087_ = RecordCodecBuilder.mapCodec(p_224839_ -> p_224839_.group((App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("probability").forGetter(p_159113_ -> Float.valueOf(p_159113_.f_67859_)), (App)HeightProvider.f_161970_.fieldOf("y").forGetter(p_159111_ -> p_159111_.f_159088_), (App)FloatProvider.f_146502_.fieldOf("yScale").forGetter(p_159109_ -> p_159109_.f_159089_), (App)VerticalAnchor.f_158914_.fieldOf("lava_level").forGetter(p_159107_ -> p_159107_.f_159090_), (App)CarverDebugSettings.f_159115_.optionalFieldOf("debug_settings", (Object)CarverDebugSettings.f_159114_).forGetter(p_190637_ -> p_190637_.f_159092_), (App)RegistryCodecs.m_206277_(Registry.f_122901_).fieldOf("replaceable").forGetter(p_224841_ -> p_224841_.f_224830_)).apply((Applicative)p_224839_, CarverConfiguration::new));
    public final HeightProvider f_159088_;
    public final FloatProvider f_159089_;
    public final VerticalAnchor f_159090_;
    public final CarverDebugSettings f_159092_;
    public final HolderSet<Block> f_224830_;

    public CarverConfiguration(float p_224832_, HeightProvider p_224833_, FloatProvider p_224834_, VerticalAnchor p_224835_, CarverDebugSettings p_224836_, HolderSet<Block> p_224837_) {
        super(p_224832_);
        this.f_159088_ = p_224833_;
        this.f_159089_ = p_224834_;
        this.f_159090_ = p_224835_;
        this.f_159092_ = p_224836_;
        this.f_224830_ = p_224837_;
    }
}


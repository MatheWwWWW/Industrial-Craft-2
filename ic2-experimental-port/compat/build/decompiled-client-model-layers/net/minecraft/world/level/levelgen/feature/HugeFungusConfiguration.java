/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class HugeFungusConfiguration
implements FeatureConfiguration {
    public static final Codec<HugeFungusConfiguration> f_65892_ = RecordCodecBuilder.create(p_65912_ -> p_65912_.group((App)BlockState.f_61039_.fieldOf("valid_base_block").forGetter(p_159875_ -> p_159875_.f_65897_), (App)BlockState.f_61039_.fieldOf("stem_state").forGetter(p_159873_ -> p_159873_.f_65898_), (App)BlockState.f_61039_.fieldOf("hat_state").forGetter(p_159871_ -> p_159871_.f_65899_), (App)BlockState.f_61039_.fieldOf("decor_state").forGetter(p_159869_ -> p_159869_.f_65900_), (App)Codec.BOOL.fieldOf("planted").orElse((Object)false).forGetter(p_159867_ -> p_159867_.f_65901_)).apply((Applicative)p_65912_, HugeFungusConfiguration::new));
    public final BlockState f_65897_;
    public final BlockState f_65898_;
    public final BlockState f_65899_;
    public final BlockState f_65900_;
    public final boolean f_65901_;

    public HugeFungusConfiguration(BlockState p_65904_, BlockState p_65905_, BlockState p_65906_, BlockState p_65907_, boolean p_65908_) {
        this.f_65897_ = p_65904_;
        this.f_65898_ = p_65905_;
        this.f_65899_ = p_65906_;
        this.f_65900_ = p_65907_;
        this.f_65901_ = p_65908_;
    }
}


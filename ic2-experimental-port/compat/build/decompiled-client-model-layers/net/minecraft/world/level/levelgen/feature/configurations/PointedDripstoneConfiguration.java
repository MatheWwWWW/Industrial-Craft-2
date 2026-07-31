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

public class PointedDripstoneConfiguration
implements FeatureConfiguration {
    public static final Codec<PointedDripstoneConfiguration> f_191274_ = RecordCodecBuilder.create(p_191286_ -> p_191286_.group((App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("chance_of_taller_dripstone").orElse((Object)Float.valueOf(0.2f)).forGetter(p_191294_ -> Float.valueOf(p_191294_.f_191275_)), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("chance_of_directional_spread").orElse((Object)Float.valueOf(0.7f)).forGetter(p_191292_ -> Float.valueOf(p_191292_.f_191276_)), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("chance_of_spread_radius2").orElse((Object)Float.valueOf(0.5f)).forGetter(p_191290_ -> Float.valueOf(p_191290_.f_191277_)), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("chance_of_spread_radius3").orElse((Object)Float.valueOf(0.5f)).forGetter(p_191288_ -> Float.valueOf(p_191288_.f_191278_))).apply((Applicative)p_191286_, PointedDripstoneConfiguration::new));
    public final float f_191275_;
    public final float f_191276_;
    public final float f_191277_;
    public final float f_191278_;

    public PointedDripstoneConfiguration(float p_191281_, float p_191282_, float p_191283_, float p_191284_) {
        this.f_191275_ = p_191281_;
        this.f_191276_ = p_191282_;
        this.f_191277_ = p_191283_;
        this.f_191278_ = p_191284_;
    }
}


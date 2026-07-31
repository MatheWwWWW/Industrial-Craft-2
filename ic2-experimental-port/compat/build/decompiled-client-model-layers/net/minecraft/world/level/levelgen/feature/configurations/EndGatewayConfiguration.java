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
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class EndGatewayConfiguration
implements FeatureConfiguration {
    public static final Codec<EndGatewayConfiguration> f_67639_ = RecordCodecBuilder.create(p_67649_ -> p_67649_.group((App)BlockPos.f_121852_.optionalFieldOf("exit").forGetter(p_160810_ -> p_160810_.f_67640_), (App)Codec.BOOL.fieldOf("exact").forGetter(p_160808_ -> p_160808_.f_67641_)).apply((Applicative)p_67649_, EndGatewayConfiguration::new));
    private final Optional<BlockPos> f_67640_;
    private final boolean f_67641_;

    private EndGatewayConfiguration(Optional<BlockPos> p_67644_, boolean p_67645_) {
        this.f_67640_ = p_67644_;
        this.f_67641_ = p_67645_;
    }

    public static EndGatewayConfiguration m_67650_(BlockPos p_67651_, boolean p_67652_) {
        return new EndGatewayConfiguration(Optional.of(p_67651_), p_67652_);
    }

    public static EndGatewayConfiguration m_67653_() {
        return new EndGatewayConfiguration(Optional.empty(), false);
    }

    public Optional<BlockPos> m_67656_() {
        return this.f_67640_;
    }

    public boolean m_67657_() {
        return this.f_67641_;
    }
}


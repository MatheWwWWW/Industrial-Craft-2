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
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class NetherForestVegetationConfig
extends BlockPileConfiguration {
    public static final Codec<NetherForestVegetationConfig> f_191258_ = RecordCodecBuilder.create(p_191267_ -> p_191267_.group((App)BlockStateProvider.f_68747_.fieldOf("state_provider").forGetter(p_191273_ -> p_191273_.f_67540_), (App)ExtraCodecs.f_144629_.fieldOf("spread_width").forGetter(p_191271_ -> p_191271_.f_191259_), (App)ExtraCodecs.f_144629_.fieldOf("spread_height").forGetter(p_191269_ -> p_191269_.f_191260_)).apply((Applicative)p_191267_, NetherForestVegetationConfig::new));
    public final int f_191259_;
    public final int f_191260_;

    public NetherForestVegetationConfig(BlockStateProvider p_191263_, int p_191264_, int p_191265_) {
        super(p_191263_);
        this.f_191259_ = p_191264_;
        this.f_191260_ = p_191265_;
    }
}


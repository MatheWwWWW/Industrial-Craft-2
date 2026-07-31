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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WeightedPlacedFeature {
    public static final Codec<WeightedPlacedFeature> f_191171_ = RecordCodecBuilder.create(p_191187_ -> p_191187_.group((App)PlacedFeature.f_191773_.fieldOf("feature").forGetter(p_204789_ -> p_204789_.f_191172_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("chance").forGetter(p_191189_ -> Float.valueOf(p_191189_.f_191173_))).apply((Applicative)p_191187_, WeightedPlacedFeature::new));
    public final Holder<PlacedFeature> f_191172_;
    public final float f_191173_;

    public WeightedPlacedFeature(Holder<PlacedFeature> p_204786_, float p_204787_) {
        this.f_191172_ = p_204786_;
        this.f_191173_ = p_204787_;
    }

    public boolean m_225367_(WorldGenLevel p_225368_, ChunkGenerator p_225369_, RandomSource p_225370_, BlockPos p_225371_) {
        return this.f_191172_.m_203334_().m_226357_(p_225368_, p_225369_, p_225370_, p_225371_);
    }
}


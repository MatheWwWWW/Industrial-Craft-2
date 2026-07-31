/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.biome;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

public class CheckerboardColumnBiomeSource
extends BiomeSource {
    public static final Codec<CheckerboardColumnBiomeSource> f_48230_ = RecordCodecBuilder.create(p_48244_ -> p_48244_.group((App)Biome.f_47432_.fieldOf("biomes").forGetter(p_204246_ -> p_204246_.f_48231_), (App)Codec.intRange((int)0, (int)62).fieldOf("scale").orElse((Object)2).forGetter(p_151788_ -> p_151788_.f_48233_)).apply((Applicative)p_48244_, CheckerboardColumnBiomeSource::new));
    private final HolderSet<Biome> f_48231_;
    private final int f_48232_;
    private final int f_48233_;

    public CheckerboardColumnBiomeSource(HolderSet<Biome> p_204243_, int p_204244_) {
        super(p_204243_.m_203614_());
        this.f_48231_ = p_204243_;
        this.f_48232_ = p_204244_ + 2;
        this.f_48233_ = p_204244_;
    }

    @Override
    protected Codec<? extends BiomeSource> m_5820_() {
        return f_48230_;
    }

    @Override
    public Holder<Biome> m_203407_(int p_204248_, int p_204249_, int p_204250_, Climate.Sampler p_204251_) {
        return this.f_48231_.m_203662_(Math.floorMod((p_204248_ >> this.f_48232_) + (p_204250_ >> this.f_48232_), this.f_48231_.m_203632_()));
    }
}


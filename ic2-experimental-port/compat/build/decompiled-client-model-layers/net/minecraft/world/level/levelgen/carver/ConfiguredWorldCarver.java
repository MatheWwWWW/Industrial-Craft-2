/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.function.Function;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

public record ConfiguredWorldCarver<WC extends CarverConfiguration>(WorldCarver<WC> f_64849_, WC f_64850_) {
    public static final Codec<ConfiguredWorldCarver<?>> f_64846_ = Registry.f_122837_.m_194605_().dispatch(p_64867_ -> p_64867_.f_64849_, WorldCarver::m_65072_);
    public static final Codec<Holder<ConfiguredWorldCarver<?>>> f_64847_ = RegistryFileCodec.m_135589_(Registry.f_122880_, f_64846_);
    public static final Codec<HolderSet<ConfiguredWorldCarver<?>>> f_64848_ = RegistryCodecs.m_206279_(Registry.f_122880_, f_64846_);

    public boolean m_224896_(RandomSource p_224897_) {
        return this.f_64849_.m_214133_(this.f_64850_, p_224897_);
    }

    public boolean m_224898_(CarvingContext p_224899_, ChunkAccess p_224900_, Function<BlockPos, Holder<Biome>> p_224901_, RandomSource p_224902_, Aquifer p_224903_, ChunkPos p_224904_, CarvingMask p_224905_) {
        if (SharedConstants.m_183707_(p_224900_.m_7697_())) {
            return false;
        }
        return this.f_64849_.m_213788_(p_224899_, this.f_64850_, p_224900_, p_224901_, p_224902_, p_224903_, p_224904_, p_224905_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ConfiguredWorldCarver.class, "worldCarver;config", "f_64849_", "f_64850_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ConfiguredWorldCarver.class, "worldCarver;config", "f_64849_", "f_64850_"}, this);
    }

    @Override
    public final boolean equals(Object p_204701_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ConfiguredWorldCarver.class, "worldCarver;config", "f_64849_", "f_64850_"}, this, p_204701_);
    }
}


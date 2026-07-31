/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;

public abstract class HeightProvider {
    private static final Codec<Either<VerticalAnchor, HeightProvider>> f_161969_ = Codec.either(VerticalAnchor.f_158914_, (Codec)Registry.f_175419_.m_194605_().dispatch(HeightProvider::m_142002_, HeightProviderType::m_161992_));
    public static final Codec<HeightProvider> f_161970_ = f_161969_.xmap(p_161974_ -> (HeightProvider)p_161974_.map(ConstantHeight::m_161956_, p_161980_ -> p_161980_), p_161976_ -> p_161976_.m_142002_() == HeightProviderType.f_161981_ ? Either.left((Object)((ConstantHeight)p_161976_).m_161963_()) : Either.right((Object)p_161976_));

    public abstract int m_213859_(RandomSource var1, WorldGenerationContext var2);

    public abstract HeightProviderType<?> m_142002_();
}


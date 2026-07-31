/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;

public class ConstantHeight
extends HeightProvider {
    public static final ConstantHeight f_161945_ = new ConstantHeight(VerticalAnchor.m_158922_(0));
    public static final Codec<ConstantHeight> f_161946_ = Codec.either(VerticalAnchor.f_158914_, (Codec)RecordCodecBuilder.create(p_161955_ -> p_161955_.group((App)VerticalAnchor.f_158914_.fieldOf("value").forGetter(p_161967_ -> p_161967_.f_161947_)).apply((Applicative)p_161955_, ConstantHeight::new))).xmap(p_161953_ -> (ConstantHeight)p_161953_.map(ConstantHeight::m_161956_, p_161965_ -> p_161965_), p_161959_ -> Either.left((Object)p_161959_.f_161947_));
    private final VerticalAnchor f_161947_;

    public static ConstantHeight m_161956_(VerticalAnchor p_161957_) {
        return new ConstantHeight(p_161957_);
    }

    private ConstantHeight(VerticalAnchor p_161950_) {
        this.f_161947_ = p_161950_;
    }

    public VerticalAnchor m_161963_() {
        return this.f_161947_;
    }

    @Override
    public int m_213859_(RandomSource p_226300_, WorldGenerationContext p_226301_) {
        return this.f_161947_.m_142322_(p_226301_);
    }

    @Override
    public HeightProviderType<?> m_142002_() {
        return HeightProviderType.f_161981_;
    }

    public String toString() {
        return this.f_161947_.toString();
    }
}


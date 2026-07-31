/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;

public interface FloatProviderType<P extends FloatProvider> {
    public static final FloatProviderType<ConstantFloat> f_146519_ = FloatProviderType.m_146526_("constant", ConstantFloat.f_146452_);
    public static final FloatProviderType<UniformFloat> f_146520_ = FloatProviderType.m_146526_("uniform", UniformFloat.f_146590_);
    public static final FloatProviderType<ClampedNormalFloat> f_146521_ = FloatProviderType.m_146526_("clamped_normal", ClampedNormalFloat.f_146411_);
    public static final FloatProviderType<TrapezoidFloat> f_146522_ = FloatProviderType.m_146526_("trapezoid", TrapezoidFloat.f_146561_);

    public Codec<P> m_146529_();

    public static <P extends FloatProvider> FloatProviderType<P> m_146526_(String p_146527_, Codec<P> p_146528_) {
        return Registry.m_122961_(Registry.f_175415_, p_146527_, () -> p_146528_);
    }
}


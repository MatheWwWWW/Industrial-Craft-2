/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;

public interface IntProviderType<P extends IntProvider> {
    public static final IntProviderType<ConstantInt> f_146550_ = IntProviderType.m_146557_("constant", ConstantInt.f_146477_);
    public static final IntProviderType<UniformInt> f_146551_ = IntProviderType.m_146557_("uniform", UniformInt.f_146614_);
    public static final IntProviderType<BiasedToBottomInt> f_146552_ = IntProviderType.m_146557_("biased_to_bottom", BiasedToBottomInt.f_146359_);
    public static final IntProviderType<ClampedInt> f_146553_ = IntProviderType.m_146557_("clamped", ClampedInt.f_146383_);
    public static final IntProviderType<WeightedListInt> f_185907_ = IntProviderType.m_146557_("weighted_list", WeightedListInt.f_185909_);
    public static final IntProviderType<ClampedNormalInt> f_185908_ = IntProviderType.m_146557_("clamped_normal", ClampedNormalInt.f_185867_);

    public Codec<P> m_146560_();

    public static <P extends IntProvider> IntProviderType<P> m_146557_(String p_146558_, Codec<P> p_146559_) {
        return Registry.m_122961_(Registry.f_175417_, p_146558_, () -> p_146559_);
    }
}


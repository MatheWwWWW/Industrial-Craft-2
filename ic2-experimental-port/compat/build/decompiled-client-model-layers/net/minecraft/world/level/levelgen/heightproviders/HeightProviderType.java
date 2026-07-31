/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
import net.minecraft.world.level.levelgen.heightproviders.WeightedListHeight;

public interface HeightProviderType<P extends HeightProvider> {
    public static final HeightProviderType<ConstantHeight> f_161981_ = HeightProviderType.m_161989_("constant", ConstantHeight.f_161946_);
    public static final HeightProviderType<UniformHeight> f_161982_ = HeightProviderType.m_161989_("uniform", UniformHeight.f_162023_);
    public static final HeightProviderType<BiasedToBottomHeight> f_161983_ = HeightProviderType.m_161989_("biased_to_bottom", BiasedToBottomHeight.f_161918_);
    public static final HeightProviderType<VeryBiasedToBottomHeight> f_161984_ = HeightProviderType.m_161989_("very_biased_to_bottom", VeryBiasedToBottomHeight.f_162045_);
    public static final HeightProviderType<TrapezoidHeight> f_161985_ = HeightProviderType.m_161989_("trapezoid", TrapezoidHeight.f_161993_);
    public static final HeightProviderType<WeightedListHeight> f_191531_ = HeightProviderType.m_161989_("weighted_list", WeightedListHeight.f_191532_);

    public Codec<P> m_161992_();

    private static <P extends HeightProvider> HeightProviderType<P> m_161989_(String p_161990_, Codec<P> p_161991_) {
        return Registry.m_122961_(Registry.f_175419_, p_161990_, () -> p_161991_);
    }
}


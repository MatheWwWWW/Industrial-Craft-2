/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.grower;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class JungleTreeGrower
extends AbstractMegaTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> m_213888_(RandomSource p_222929_, boolean p_222930_) {
        return TreeFeatures.f_195131_;
    }

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> m_213566_(RandomSource p_222927_) {
        return TreeFeatures.f_195132_;
    }
}


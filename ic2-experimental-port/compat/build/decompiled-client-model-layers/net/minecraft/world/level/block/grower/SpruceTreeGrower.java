/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.grower;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SpruceTreeGrower
extends AbstractMegaTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> m_213888_(RandomSource p_222943_, boolean p_222944_) {
        return TreeFeatures.f_195127_;
    }

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> m_213566_(RandomSource p_222941_) {
        return p_222941_.m_188499_() ? TreeFeatures.f_195133_ : TreeFeatures.f_195134_;
    }
}


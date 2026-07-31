/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.grower;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class BirchTreeGrower
extends AbstractTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> m_213888_(RandomSource p_222919_, boolean p_222920_) {
        return p_222920_ ? TreeFeatures.f_195108_ : TreeFeatures.f_195125_;
    }
}


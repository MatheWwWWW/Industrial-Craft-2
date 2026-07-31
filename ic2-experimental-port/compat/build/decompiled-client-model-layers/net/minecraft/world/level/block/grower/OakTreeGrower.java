/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.grower;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class OakTreeGrower
extends AbstractTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> m_213888_(RandomSource p_222938_, boolean p_222939_) {
        if (p_222938_.m_188503_(10) == 0) {
            return p_222939_ ? TreeFeatures.f_195111_ : TreeFeatures.f_195130_;
        }
        return p_222939_ ? TreeFeatures.f_195142_ : TreeFeatures.f_195123_;
    }
}


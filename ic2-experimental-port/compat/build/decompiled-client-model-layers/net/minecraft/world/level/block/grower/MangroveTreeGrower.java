/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.grower;

import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class MangroveTreeGrower
extends AbstractTreeGrower {
    private final float f_222931_;

    public MangroveTreeGrower(float p_222933_) {
        this.f_222931_ = p_222933_;
    }

    @Override
    @Nullable
    protected Holder<? extends ConfiguredFeature<?, ?>> m_213888_(RandomSource p_222935_, boolean p_222936_) {
        if (p_222935_.m_188501_() < this.f_222931_) {
            return TreeFeatures.f_236763_;
        }
        return TreeFeatures.f_236762_;
    }
}


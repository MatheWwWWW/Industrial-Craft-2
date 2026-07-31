/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class RandomPatchFeature
extends Feature<RandomPatchConfiguration> {
    public RandomPatchFeature(Codec<RandomPatchConfiguration> p_66605_) {
        super(p_66605_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<RandomPatchConfiguration> p_160210_) {
        RandomPatchConfiguration $$1 = p_160210_.m_159778_();
        RandomSource $$2 = p_160210_.m_225041_();
        BlockPos $$3 = p_160210_.m_159777_();
        WorldGenLevel $$4 = p_160210_.m_159774_();
        int $$5 = 0;
        BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
        int $$7 = $$1.f_191302_() + 1;
        int $$8 = $$1.f_191303_() + 1;
        for (int $$9 = 0; $$9 < $$1.f_67907_(); ++$$9) {
            $$6.m_122154_($$3, $$2.m_188503_($$7) - $$2.m_188503_($$7), $$2.m_188503_($$8) - $$2.m_188503_($$8), $$2.m_188503_($$7) - $$2.m_188503_($$7));
            if (!$$1.f_191304_().m_203334_().m_226357_($$4, p_160210_.m_159775_(), $$2, $$6)) continue;
            ++$$5;
        }
        return $$5 > 0;
    }
}


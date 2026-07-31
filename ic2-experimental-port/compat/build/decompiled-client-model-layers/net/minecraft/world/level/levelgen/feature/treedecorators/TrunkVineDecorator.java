/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class TrunkVineDecorator
extends TreeDecorator {
    public static final Codec<TrunkVineDecorator> f_70055_ = Codec.unit(() -> f_70056_);
    public static final TrunkVineDecorator f_70056_ = new TrunkVineDecorator();

    @Override
    protected TreeDecoratorType<?> m_6663_() {
        return TreeDecoratorType.f_70042_;
    }

    @Override
    public void m_214187_(TreeDecorator.Context p_226077_) {
        RandomSource $$1 = p_226077_.m_226067_();
        p_226077_.m_226068_().forEach(p_226075_ -> {
            BlockPos $$6;
            BlockPos $$5;
            BlockPos $$4;
            BlockPos $$3;
            if ($$1.m_188503_(3) > 0 && p_226077_.m_226059_($$3 = p_226075_.m_122024_())) {
                p_226077_.m_226064_($$3, VineBlock.f_57835_);
            }
            if ($$1.m_188503_(3) > 0 && p_226077_.m_226059_($$4 = p_226075_.m_122029_())) {
                p_226077_.m_226064_($$4, VineBlock.f_57837_);
            }
            if ($$1.m_188503_(3) > 0 && p_226077_.m_226059_($$5 = p_226075_.m_122012_())) {
                p_226077_.m_226064_($$5, VineBlock.f_57836_);
            }
            if ($$1.m_188503_(3) > 0 && p_226077_.m_226059_($$6 = p_226075_.m_122019_())) {
                p_226077_.m_226064_($$6, VineBlock.f_57834_);
            }
        });
    }
}


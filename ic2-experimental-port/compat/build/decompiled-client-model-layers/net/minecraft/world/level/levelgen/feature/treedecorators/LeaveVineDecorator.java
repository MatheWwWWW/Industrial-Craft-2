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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class LeaveVineDecorator
extends TreeDecorator {
    public static final Codec<LeaveVineDecorator> f_69996_ = Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("probability").xmap(LeaveVineDecorator::new, p_226037_ -> Float.valueOf(p_226037_.f_226029_)).codec();
    private final float f_226029_;

    @Override
    protected TreeDecoratorType<?> m_6663_() {
        return TreeDecoratorType.f_70043_;
    }

    public LeaveVineDecorator(float p_226031_) {
        this.f_226029_ = p_226031_;
    }

    @Override
    public void m_214187_(TreeDecorator.Context p_226039_) {
        RandomSource $$1 = p_226039_.m_226067_();
        p_226039_.m_226069_().forEach(p_226035_ -> {
            BlockPos $$6;
            BlockPos $$5;
            BlockPos $$4;
            BlockPos $$3;
            if ($$1.m_188501_() < this.f_226029_ && p_226039_.m_226059_($$3 = p_226035_.m_122024_())) {
                LeaveVineDecorator.m_226040_($$3, VineBlock.f_57835_, p_226039_);
            }
            if ($$1.m_188501_() < this.f_226029_ && p_226039_.m_226059_($$4 = p_226035_.m_122029_())) {
                LeaveVineDecorator.m_226040_($$4, VineBlock.f_57837_, p_226039_);
            }
            if ($$1.m_188501_() < this.f_226029_ && p_226039_.m_226059_($$5 = p_226035_.m_122012_())) {
                LeaveVineDecorator.m_226040_($$5, VineBlock.f_57836_, p_226039_);
            }
            if ($$1.m_188501_() < this.f_226029_ && p_226039_.m_226059_($$6 = p_226035_.m_122019_())) {
                LeaveVineDecorator.m_226040_($$6, VineBlock.f_57834_, p_226039_);
            }
        });
    }

    private static void m_226040_(BlockPos p_226041_, BooleanProperty p_226042_, TreeDecorator.Context p_226043_) {
        p_226043_.m_226064_(p_226041_, p_226042_);
        p_226041_ = p_226041_.m_7495_();
        for (int $$3 = 4; p_226043_.m_226059_(p_226041_) && $$3 > 0; --$$3) {
            p_226043_.m_226064_(p_226041_, p_226042_);
            p_226041_ = p_226041_.m_7495_();
        }
    }
}


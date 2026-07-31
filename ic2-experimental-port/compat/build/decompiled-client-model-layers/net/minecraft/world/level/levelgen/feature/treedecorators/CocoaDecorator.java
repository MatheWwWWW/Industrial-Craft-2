/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class CocoaDecorator
extends TreeDecorator {
    public static final Codec<CocoaDecorator> f_69972_ = Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("probability").xmap(CocoaDecorator::new, p_69989_ -> Float.valueOf(p_69989_.f_69973_)).codec();
    private final float f_69973_;

    public CocoaDecorator(float p_69976_) {
        this.f_69973_ = p_69976_;
    }

    @Override
    protected TreeDecoratorType<?> m_6663_() {
        return TreeDecoratorType.f_70044_;
    }

    @Override
    public void m_214187_(TreeDecorator.Context p_226028_) {
        RandomSource $$1 = p_226028_.m_226067_();
        if ($$1.m_188501_() >= this.f_69973_) {
            return;
        }
        ObjectArrayList<BlockPos> $$2 = p_226028_.m_226068_();
        int $$3 = ((BlockPos)$$2.get(0)).m_123342_();
        $$2.stream().filter(p_69980_ -> p_69980_.m_123342_() - $$3 <= 2).forEach(p_226026_ -> {
            for (Direction $$3 : Direction.Plane.HORIZONTAL) {
                Direction $$4;
                BlockPos $$5;
                if (!($$1.m_188501_() <= 0.25f) || !p_226028_.m_226059_($$5 = p_226026_.m_7918_(($$4 = $$3.m_122424_()).m_122429_(), 0, $$4.m_122431_()))) continue;
                p_226028_.m_226061_($$5, (BlockState)((BlockState)Blocks.f_50262_.m_49966_().m_61124_(CocoaBlock.f_51736_, $$1.m_188503_(3))).m_61124_(CocoaBlock.f_54117_, $$3));
            }
        });
    }
}


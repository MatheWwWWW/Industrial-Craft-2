/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Iterator;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface ChangeOverTimeBlock<T extends Enum<T>> {
    public static final int f_153035_ = 4;

    public Optional<BlockState> m_142123_(BlockState var1);

    public float m_142377_();

    default public void m_220947_(BlockState p_220948_, ServerLevel p_220949_, BlockPos p_220950_, RandomSource p_220951_) {
        float $$4 = 0.05688889f;
        if (p_220951_.m_188501_() < 0.05688889f) {
            this.m_220952_(p_220948_, p_220949_, p_220950_, p_220951_);
        }
    }

    public T m_142297_();

    default public void m_220952_(BlockState p_220953_, ServerLevel p_220954_, BlockPos p_220955_, RandomSource p_220956_) {
        BlockPos $$7;
        int $$8;
        int $$4 = ((Enum)this.m_142297_()).ordinal();
        int $$5 = 0;
        int $$6 = 0;
        Iterator<BlockPos> iterator = BlockPos.m_121925_(p_220955_, 4, 4, 4).iterator();
        while (iterator.hasNext() && ($$8 = ($$7 = iterator.next()).m_123333_(p_220955_)) <= 4) {
            BlockState $$9;
            Block $$10;
            if ($$7.equals(p_220955_) || !(($$10 = ($$9 = p_220954_.m_8055_($$7)).m_60734_()) instanceof ChangeOverTimeBlock)) continue;
            T $$11 = ((ChangeOverTimeBlock)((Object)$$10)).m_142297_();
            if (this.m_142297_().getClass() != $$11.getClass()) continue;
            int $$12 = ((Enum)$$11).ordinal();
            if ($$12 < $$4) {
                return;
            }
            if ($$12 > $$4) {
                ++$$6;
                continue;
            }
            ++$$5;
        }
        float $$13 = (float)($$6 + 1) / (float)($$6 + $$5 + 1);
        float $$14 = $$13 * $$13 * this.m_142377_();
        if (p_220956_.m_188501_() < $$14) {
            this.m_142123_(p_220953_).ifPresent(p_153039_ -> p_220954_.m_46597_(p_220955_, (BlockState)p_153039_));
        }
    }
}


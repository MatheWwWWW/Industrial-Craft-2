/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 */
package net.minecraft;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockUtil {
    public static FoundRectangle m_124334_(BlockPos p_124335_, Direction.Axis p_124336_, int p_124337_, Direction.Axis p_124338_, int p_124339_, Predicate<BlockPos> p_124340_) {
        BlockPos.MutableBlockPos $$6 = p_124335_.m_122032_();
        Direction $$7 = Direction.m_122390_(Direction.AxisDirection.NEGATIVE, p_124336_);
        Direction $$8 = $$7.m_122424_();
        Direction $$9 = Direction.m_122390_(Direction.AxisDirection.NEGATIVE, p_124338_);
        Direction $$10 = $$9.m_122424_();
        int $$11 = BlockUtil.m_124341_(p_124340_, $$6.m_122190_(p_124335_), $$7, p_124337_);
        int $$12 = BlockUtil.m_124341_(p_124340_, $$6.m_122190_(p_124335_), $$8, p_124337_);
        int $$13 = $$11;
        IntBounds[] $$14 = new IntBounds[$$13 + 1 + $$12];
        $$14[$$13] = new IntBounds(BlockUtil.m_124341_(p_124340_, $$6.m_122190_(p_124335_), $$9, p_124339_), BlockUtil.m_124341_(p_124340_, $$6.m_122190_(p_124335_), $$10, p_124339_));
        int $$15 = $$14[$$13].f_124355_;
        for (int $$16 = 1; $$16 <= $$11; ++$$16) {
            IntBounds $$17 = $$14[$$13 - ($$16 - 1)];
            $$14[$$13 - $$16] = new IntBounds(BlockUtil.m_124341_(p_124340_, $$6.m_122190_(p_124335_).m_122175_($$7, $$16), $$9, $$17.f_124355_), BlockUtil.m_124341_(p_124340_, $$6.m_122190_(p_124335_).m_122175_($$7, $$16), $$10, $$17.f_124356_));
        }
        for (int $$18 = 1; $$18 <= $$12; ++$$18) {
            IntBounds $$19 = $$14[$$13 + $$18 - 1];
            $$14[$$13 + $$18] = new IntBounds(BlockUtil.m_124341_(p_124340_, $$6.m_122190_(p_124335_).m_122175_($$8, $$18), $$9, $$19.f_124355_), BlockUtil.m_124341_(p_124340_, $$6.m_122190_(p_124335_).m_122175_($$8, $$18), $$10, $$19.f_124356_));
        }
        int $$20 = 0;
        int $$21 = 0;
        int $$22 = 0;
        int $$23 = 0;
        int[] $$24 = new int[$$14.length];
        for (int $$25 = $$15; $$25 >= 0; --$$25) {
            for (int $$26 = 0; $$26 < $$14.length; ++$$26) {
                IntBounds $$27 = $$14[$$26];
                int $$28 = $$15 - $$27.f_124355_;
                int $$29 = $$15 + $$27.f_124356_;
                $$24[$$26] = $$25 >= $$28 && $$25 <= $$29 ? $$29 + 1 - $$25 : 0;
            }
            Pair<IntBounds, Integer> $$30 = BlockUtil.m_124346_($$24);
            IntBounds $$31 = (IntBounds)$$30.getFirst();
            int $$32 = 1 + $$31.f_124356_ - $$31.f_124355_;
            int $$33 = (Integer)$$30.getSecond();
            if ($$32 * $$33 <= $$22 * $$23) continue;
            $$20 = $$31.f_124355_;
            $$21 = $$25;
            $$22 = $$32;
            $$23 = $$33;
        }
        return new FoundRectangle(p_124335_.m_5487_(p_124336_, $$20 - $$13).m_5487_(p_124338_, $$21 - $$15), $$22, $$23);
    }

    private static int m_124341_(Predicate<BlockPos> p_124342_, BlockPos.MutableBlockPos p_124343_, Direction p_124344_, int p_124345_) {
        int $$4;
        for ($$4 = 0; $$4 < p_124345_ && p_124342_.test(p_124343_.m_122173_(p_124344_)); ++$$4) {
        }
        return $$4;
    }

    @VisibleForTesting
    static Pair<IntBounds, Integer> m_124346_(int[] p_124347_) {
        int $$1 = 0;
        int $$2 = 0;
        int $$3 = 0;
        IntArrayList $$4 = new IntArrayList();
        $$4.push(0);
        for (int $$5 = 1; $$5 <= p_124347_.length; ++$$5) {
            int $$6;
            int n = $$6 = $$5 == p_124347_.length ? 0 : p_124347_[$$5];
            while (!$$4.isEmpty()) {
                int $$7 = p_124347_[$$4.topInt()];
                if ($$6 >= $$7) {
                    $$4.push($$5);
                    break;
                }
                $$4.popInt();
                int $$8 = $$4.isEmpty() ? 0 : $$4.topInt() + 1;
                if ($$7 * ($$5 - $$8) <= $$3 * ($$2 - $$1)) continue;
                $$2 = $$5;
                $$1 = $$8;
                $$3 = $$7;
            }
            if (!$$4.isEmpty()) continue;
            $$4.push($$5);
        }
        return new Pair((Object)new IntBounds($$1, $$2 - 1), (Object)$$3);
    }

    public static Optional<BlockPos> m_177845_(BlockGetter p_177846_, BlockPos p_177847_, Block p_177848_, Direction p_177849_, Block p_177850_) {
        BlockState $$6;
        BlockPos.MutableBlockPos $$5 = p_177847_.m_122032_();
        do {
            $$5.m_122173_(p_177849_);
        } while (($$6 = p_177846_.m_8055_($$5)).m_60713_(p_177848_));
        if ($$6.m_60713_(p_177850_)) {
            return Optional.of($$5);
        }
        return Optional.empty();
    }

    public static class IntBounds {
        public final int f_124355_;
        public final int f_124356_;

        public IntBounds(int p_124358_, int p_124359_) {
            this.f_124355_ = p_124358_;
            this.f_124356_ = p_124359_;
        }

        public String toString() {
            return "IntBounds{min=" + this.f_124355_ + ", max=" + this.f_124356_ + "}";
        }
    }

    public static class FoundRectangle {
        public final BlockPos f_124348_;
        public final int f_124349_;
        public final int f_124350_;

        public FoundRectangle(BlockPos p_124352_, int p_124353_, int p_124354_) {
            this.f_124348_ = p_124352_;
            this.f_124349_ = p_124353_;
            this.f_124350_ = p_124354_;
        }
    }
}


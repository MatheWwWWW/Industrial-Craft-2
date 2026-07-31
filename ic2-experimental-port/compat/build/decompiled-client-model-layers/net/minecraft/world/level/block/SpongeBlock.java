/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;

public class SpongeBlock
extends Block {
    public static final int f_154689_ = 6;
    public static final int f_154690_ = 64;

    protected SpongeBlock(BlockBehaviour.Properties p_56796_) {
        super(p_56796_);
    }

    @Override
    public void m_6807_(BlockState p_56811_, Level p_56812_, BlockPos p_56813_, BlockState p_56814_, boolean p_56815_) {
        if (p_56814_.m_60713_(p_56811_.m_60734_())) {
            return;
        }
        this.m_56797_(p_56812_, p_56813_);
    }

    @Override
    public void m_6861_(BlockState p_56801_, Level p_56802_, BlockPos p_56803_, Block p_56804_, BlockPos p_56805_, boolean p_56806_) {
        this.m_56797_(p_56802_, p_56803_);
        super.m_6861_(p_56801_, p_56802_, p_56803_, p_56804_, p_56805_, p_56806_);
    }

    protected void m_56797_(Level p_56798_, BlockPos p_56799_) {
        if (this.m_56807_(p_56798_, p_56799_)) {
            p_56798_.m_7731_(p_56799_, Blocks.f_50057_.m_49966_(), 2);
            p_56798_.m_46796_(2001, p_56799_, Block.m_49956_(Blocks.f_49990_.m_49966_()));
        }
    }

    private boolean m_56807_(Level p_56808_, BlockPos p_56809_) {
        LinkedList $$2 = Lists.newLinkedList();
        $$2.add(new Tuple<BlockPos, Integer>(p_56809_, 0));
        int $$3 = 0;
        while (!$$2.isEmpty()) {
            Tuple $$4 = (Tuple)$$2.poll();
            BlockPos $$5 = (BlockPos)$$4.m_14418_();
            int $$6 = (Integer)$$4.m_14419_();
            for (Direction $$7 : Direction.values()) {
                BlockPos $$8 = $$5.m_121945_($$7);
                BlockState $$9 = p_56808_.m_8055_($$8);
                FluidState $$10 = p_56808_.m_6425_($$8);
                Material $$11 = $$9.m_60767_();
                if (!$$10.m_205070_(FluidTags.f_13131_)) continue;
                if ($$9.m_60734_() instanceof BucketPickup && !((BucketPickup)((Object)$$9.m_60734_())).m_142598_(p_56808_, $$8, $$9).m_41619_()) {
                    ++$$3;
                    if ($$6 >= 6) continue;
                    $$2.add(new Tuple<BlockPos, Integer>($$8, $$6 + 1));
                    continue;
                }
                if ($$9.m_60734_() instanceof LiquidBlock) {
                    p_56808_.m_7731_($$8, Blocks.f_50016_.m_49966_(), 3);
                    ++$$3;
                    if ($$6 >= 6) continue;
                    $$2.add(new Tuple<BlockPos, Integer>($$8, $$6 + 1));
                    continue;
                }
                if ($$11 != Material.f_76301_ && $$11 != Material.f_76304_) continue;
                BlockEntity $$12 = $$9.m_155947_() ? p_56808_.m_7702_($$8) : null;
                SpongeBlock.m_49892_($$9, p_56808_, $$8, $$12);
                p_56808_.m_7731_($$8, Blocks.f_50016_.m_49966_(), 3);
                ++$$3;
                if ($$6 >= 6) continue;
                $$2.add(new Tuple<BlockPos, Integer>($$8, $$6 + 1));
            }
            if ($$3 <= 64) continue;
            break;
        }
        return $$3 > 0;
    }
}


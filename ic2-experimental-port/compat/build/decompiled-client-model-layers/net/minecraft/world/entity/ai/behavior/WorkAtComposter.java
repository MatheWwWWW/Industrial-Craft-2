/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.behavior.WorkAtPoi;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WorkAtComposter
extends WorkAtPoi {
    private static final List<Item> f_24786_ = ImmutableList.of((Object)Items.f_42404_, (Object)Items.f_42733_);

    @Override
    protected void m_5628_(ServerLevel p_24790_, Villager p_24791_) {
        Optional<GlobalPos> $$2 = p_24791_.m_6274_().m_21952_(MemoryModuleType.f_26360_);
        if (!$$2.isPresent()) {
            return;
        }
        GlobalPos $$3 = $$2.get();
        BlockState $$4 = p_24790_.m_8055_($$3.m_122646_());
        if ($$4.m_60713_(Blocks.f_50715_)) {
            this.m_24802_(p_24791_);
            this.m_24792_(p_24790_, p_24791_, $$3, $$4);
        }
    }

    private void m_24792_(ServerLevel p_24793_, Villager p_24794_, GlobalPos p_24795_, BlockState p_24796_) {
        BlockPos $$4 = p_24795_.m_122646_();
        if (p_24796_.m_61143_(ComposterBlock.f_51913_) == 8) {
            p_24796_ = ComposterBlock.m_51998_(p_24796_, p_24793_, $$4);
        }
        int $$5 = 20;
        int $$6 = 10;
        int[] $$7 = new int[f_24786_.size()];
        SimpleContainer $$8 = p_24794_.m_35311_();
        int $$9 = $$8.m_6643_();
        BlockState $$10 = p_24796_;
        for (int $$11 = $$9 - 1; $$11 >= 0 && $$5 > 0; --$$11) {
            int $$15;
            ItemStack $$12 = $$8.m_8020_($$11);
            int $$13 = f_24786_.indexOf($$12.m_41720_());
            if ($$13 == -1) continue;
            int $$14 = $$12.m_41613_();
            $$7[$$13] = $$15 = $$7[$$13] + $$14;
            int $$16 = Math.min(Math.min($$15 - 10, $$5), $$14);
            if ($$16 <= 0) continue;
            $$5 -= $$16;
            for (int $$17 = 0; $$17 < $$16; ++$$17) {
                if (($$10 = ComposterBlock.m_51929_($$10, p_24793_, $$12, $$4)).m_61143_(ComposterBlock.f_51913_) != 7) continue;
                this.m_24797_(p_24793_, p_24796_, $$4, $$10);
                return;
            }
        }
        this.m_24797_(p_24793_, p_24796_, $$4, $$10);
    }

    private void m_24797_(ServerLevel p_24798_, BlockState p_24799_, BlockPos p_24800_, BlockState p_24801_) {
        p_24798_.m_46796_(1500, p_24800_, p_24801_ != p_24799_ ? 1 : 0);
    }

    private void m_24802_(Villager p_24803_) {
        SimpleContainer $$1 = p_24803_.m_35311_();
        if ($$1.m_18947_(Items.f_42406_) > 36) {
            return;
        }
        int $$2 = $$1.m_18947_(Items.f_42405_);
        int $$3 = 3;
        int $$4 = 3;
        int $$5 = Math.min(3, $$2 / 3);
        if ($$5 == 0) {
            return;
        }
        int $$6 = $$5 * 3;
        $$1.m_19170_(Items.f_42405_, $$6);
        ItemStack $$7 = $$1.m_19173_(new ItemStack(Items.f_42406_, $$5));
        if (!$$7.m_41619_()) {
            p_24803_.m_5552_($$7, 0.5f);
        }
    }
}


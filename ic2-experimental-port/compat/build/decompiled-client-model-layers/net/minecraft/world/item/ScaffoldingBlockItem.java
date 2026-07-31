/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ScaffoldingBlockItem
extends BlockItem {
    public ScaffoldingBlockItem(Block p_43060_, Item.Properties p_43061_) {
        super(p_43060_, p_43061_);
    }

    @Override
    @Nullable
    public BlockPlaceContext m_7732_(BlockPlaceContext p_43063_) {
        Block $$4;
        BlockPos $$1 = p_43063_.m_8083_();
        Level $$2 = p_43063_.m_43725_();
        BlockState $$3 = $$2.m_8055_($$1);
        if ($$3.m_60713_($$4 = this.m_40614_())) {
            Direction $$6;
            if (p_43063_.m_7078_()) {
                Direction $$5 = p_43063_.m_43721_() ? p_43063_.m_43719_().m_122424_() : p_43063_.m_43719_();
            } else {
                $$6 = p_43063_.m_43719_() == Direction.UP ? p_43063_.m_8125_() : Direction.UP;
            }
            int $$7 = 0;
            BlockPos.MutableBlockPos $$8 = $$1.m_122032_().m_122173_($$6);
            while ($$7 < 7) {
                if (!$$2.f_46443_ && !$$2.m_46739_($$8)) {
                    Player $$9 = p_43063_.m_43723_();
                    int $$10 = $$2.m_151558_();
                    if (!($$9 instanceof ServerPlayer) || $$8.m_123342_() < $$10) break;
                    ((ServerPlayer)$$9).m_240418_(Component.m_237110_("build.tooHigh", $$10 - 1).m_130940_(ChatFormatting.RED), true);
                    break;
                }
                $$3 = $$2.m_8055_($$8);
                if (!$$3.m_60713_(this.m_40614_())) {
                    if (!$$3.m_60629_(p_43063_)) break;
                    return BlockPlaceContext.m_43644_(p_43063_, $$8, $$6);
                }
                $$8.m_122173_($$6);
                if (!$$6.m_122434_().m_122479_()) continue;
                ++$$7;
            }
            return null;
        }
        if (ScaffoldingBlock.m_56024_($$2, $$1) == 7) {
            return null;
        }
        return p_43063_;
    }

    @Override
    protected boolean m_6652_() {
        return false;
    }
}


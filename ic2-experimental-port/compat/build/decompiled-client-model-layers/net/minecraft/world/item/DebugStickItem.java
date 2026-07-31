/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class DebugStickItem
extends Item {
    public DebugStickItem(Item.Properties p_40948_) {
        super(p_40948_);
    }

    @Override
    public boolean m_5812_(ItemStack p_40978_) {
        return true;
    }

    @Override
    public boolean m_6777_(BlockState p_40962_, Level p_40963_, BlockPos p_40964_, Player p_40965_) {
        if (!p_40963_.f_46443_) {
            this.m_150802_(p_40965_, p_40962_, p_40963_, p_40964_, false, p_40965_.m_21120_(InteractionHand.MAIN_HAND));
        }
        return false;
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_40960_) {
        BlockPos $$3;
        Player $$1 = p_40960_.m_43723_();
        Level $$2 = p_40960_.m_43725_();
        if (!$$2.f_46443_ && $$1 != null && !this.m_150802_($$1, $$2.m_8055_($$3 = p_40960_.m_8083_()), $$2, $$3, true, p_40960_.m_43722_())) {
            return InteractionResult.FAIL;
        }
        return InteractionResult.m_19078_($$2.f_46443_);
    }

    private boolean m_150802_(Player p_150803_, BlockState p_150804_, LevelAccessor p_150805_, BlockPos p_150806_, boolean p_150807_, ItemStack p_150808_) {
        if (!p_150803_.m_36337_()) {
            return false;
        }
        Block $$6 = p_150804_.m_60734_();
        StateDefinition<Block, BlockState> $$7 = $$6.m_49965_();
        Collection<Property<?>> $$8 = $$7.m_61092_();
        String $$9 = Registry.f_122824_.m_7981_($$6).toString();
        if ($$8.isEmpty()) {
            DebugStickItem.m_40956_(p_150803_, Component.m_237110_(this.m_5524_() + ".empty", $$9));
            return false;
        }
        CompoundTag $$10 = p_150808_.m_41698_("DebugProperty");
        String $$11 = $$10.m_128461_($$9);
        Property<?> $$12 = $$7.m_61081_($$11);
        if (p_150807_) {
            if ($$12 == null) {
                $$12 = $$8.iterator().next();
            }
            BlockState $$13 = DebugStickItem.m_40969_(p_150804_, $$12, p_150803_.m_36341_());
            p_150805_.m_7731_(p_150806_, $$13, 18);
            DebugStickItem.m_40956_(p_150803_, Component.m_237110_(this.m_5524_() + ".update", $$12.m_61708_(), DebugStickItem.m_40966_($$13, $$12)));
        } else {
            $$12 = DebugStickItem.m_40973_($$8, $$12, p_150803_.m_36341_());
            String $$14 = $$12.m_61708_();
            $$10.m_128359_($$9, $$14);
            DebugStickItem.m_40956_(p_150803_, Component.m_237110_(this.m_5524_() + ".select", $$14, DebugStickItem.m_40966_(p_150804_, $$12)));
        }
        return true;
    }

    private static <T extends Comparable<T>> BlockState m_40969_(BlockState p_40970_, Property<T> p_40971_, boolean p_40972_) {
        return (BlockState)p_40970_.m_61124_(p_40971_, (Comparable)DebugStickItem.m_40973_(p_40971_.m_6908_(), p_40970_.m_61143_(p_40971_), p_40972_));
    }

    private static <T> T m_40973_(Iterable<T> p_40974_, @Nullable T p_40975_, boolean p_40976_) {
        return p_40976_ ? Util.m_137554_(p_40974_, p_40975_) : Util.m_137466_(p_40974_, p_40975_);
    }

    private static void m_40956_(Player p_40957_, Component p_40958_) {
        ((ServerPlayer)p_40957_).m_240418_(p_40958_, true);
    }

    private static <T extends Comparable<T>> String m_40966_(BlockState p_40967_, Property<T> p_40968_) {
        return p_40968_.m_6940_(p_40967_.m_61143_(p_40968_));
    }
}


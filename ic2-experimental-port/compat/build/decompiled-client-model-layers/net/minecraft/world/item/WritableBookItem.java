/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WritableBookItem
extends Item {
    public WritableBookItem(Item.Properties p_43445_) {
        super(p_43445_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_43447_) {
        BlockPos $$2;
        Level $$1 = p_43447_.m_43725_();
        BlockState $$3 = $$1.m_8055_($$2 = p_43447_.m_8083_());
        if ($$3.m_60713_(Blocks.f_50624_)) {
            return LecternBlock.m_153566_(p_43447_.m_43723_(), $$1, $$2, $$3, p_43447_.m_43722_()) ? InteractionResult.m_19078_($$1.f_46443_) : InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_43449_, Player p_43450_, InteractionHand p_43451_) {
        ItemStack $$3 = p_43450_.m_21120_(p_43451_);
        p_43450_.m_6986_($$3, p_43451_);
        p_43450_.m_36246_(Stats.f_12982_.m_12902_(this));
        return InteractionResultHolder.m_19092_($$3, p_43449_.m_5776_());
    }

    public static boolean m_43452_(@Nullable CompoundTag p_43453_) {
        if (p_43453_ == null) {
            return false;
        }
        if (!p_43453_.m_128425_("pages", 9)) {
            return false;
        }
        ListTag $$1 = p_43453_.m_128437_("pages", 8);
        for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
            String $$3 = $$1.m_128778_($$2);
            if ($$3.length() <= Short.MAX_VALUE) continue;
            return false;
        }
        return true;
    }
}


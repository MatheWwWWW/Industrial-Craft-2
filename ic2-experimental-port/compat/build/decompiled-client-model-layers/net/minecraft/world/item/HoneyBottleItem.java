/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class HoneyBottleItem
extends Item {
    private static final int f_150862_ = 40;

    public HoneyBottleItem(Item.Properties p_41346_) {
        super(p_41346_);
    }

    @Override
    public ItemStack m_5922_(ItemStack p_41348_, Level p_41349_, LivingEntity p_41350_) {
        super.m_5922_(p_41348_, p_41349_, p_41350_);
        if (p_41350_ instanceof ServerPlayer) {
            ServerPlayer $$3 = (ServerPlayer)p_41350_;
            CriteriaTriggers.f_10592_.m_23682_($$3, p_41348_);
            $$3.m_36246_(Stats.f_12982_.m_12902_(this));
        }
        if (!p_41349_.f_46443_) {
            p_41350_.m_21195_(MobEffects.f_19614_);
        }
        if (p_41348_.m_41619_()) {
            return new ItemStack(Items.f_42590_);
        }
        if (p_41350_ instanceof Player && !((Player)p_41350_).m_150110_().f_35937_) {
            ItemStack $$4 = new ItemStack(Items.f_42590_);
            Player $$5 = (Player)p_41350_;
            if (!$$5.m_150109_().m_36054_($$4)) {
                $$5.m_36176_($$4, false);
            }
        }
        return p_41348_;
    }

    @Override
    public int m_8105_(ItemStack p_41360_) {
        return 40;
    }

    @Override
    public UseAnim m_6164_(ItemStack p_41358_) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent m_6023_() {
        return SoundEvents.f_11970_;
    }

    @Override
    public SoundEvent m_6061_() {
        return SoundEvents.f_11970_;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_41352_, Player p_41353_, InteractionHand p_41354_) {
        return ItemUtils.m_150959_(p_41352_, p_41353_, p_41354_);
    }
}


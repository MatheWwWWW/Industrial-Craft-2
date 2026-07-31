/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class SpyglassItem
extends Item {
    public static final int f_151202_ = 1200;
    public static final float f_151203_ = 0.1f;

    public SpyglassItem(Item.Properties p_151205_) {
        super(p_151205_);
    }

    @Override
    public int m_8105_(ItemStack p_151222_) {
        return 1200;
    }

    @Override
    public UseAnim m_6164_(ItemStack p_151224_) {
        return UseAnim.SPYGLASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_151218_, Player p_151219_, InteractionHand p_151220_) {
        p_151219_.m_5496_(SoundEvents.f_144231_, 1.0f, 1.0f);
        p_151219_.m_36246_(Stats.f_12982_.m_12902_(this));
        return ItemUtils.m_150959_(p_151218_, p_151219_, p_151220_);
    }

    @Override
    public ItemStack m_5922_(ItemStack p_151209_, Level p_151210_, LivingEntity p_151211_) {
        this.m_151206_(p_151211_);
        return p_151209_;
    }

    @Override
    public void m_5551_(ItemStack p_151213_, Level p_151214_, LivingEntity p_151215_, int p_151216_) {
        this.m_151206_(p_151215_);
    }

    private void m_151206_(LivingEntity p_151207_) {
        p_151207_.m_5496_(SoundEvents.f_144232_, 1.0f, 1.0f);
    }
}


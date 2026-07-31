/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.level.Level;

public class SplashPotionItem
extends ThrowablePotionItem {
    public SplashPotionItem(Item.Properties p_43241_) {
        super(p_43241_);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_43243_, Player p_43244_, InteractionHand p_43245_) {
        p_43243_.m_6263_(null, p_43244_.m_20185_(), p_43244_.m_20186_(), p_43244_.m_20189_(), SoundEvents.f_12437_, SoundSource.PLAYERS, 0.5f, 0.4f / (p_43243_.m_213780_().m_188501_() * 0.4f + 0.8f));
        return super.m_7203_(p_43243_, p_43244_, p_43245_);
    }
}


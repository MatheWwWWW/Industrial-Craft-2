/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SnowballItem
extends Item {
    public SnowballItem(Item.Properties p_43140_) {
        super(p_43140_);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_43142_, Player p_43143_, InteractionHand p_43144_) {
        ItemStack $$3 = p_43143_.m_21120_(p_43144_);
        p_43142_.m_6263_(null, p_43143_.m_20185_(), p_43143_.m_20186_(), p_43143_.m_20189_(), SoundEvents.f_12473_, SoundSource.NEUTRAL, 0.5f, 0.4f / (p_43142_.m_213780_().m_188501_() * 0.4f + 0.8f));
        if (!p_43142_.f_46443_) {
            Snowball $$4 = new Snowball(p_43142_, p_43143_);
            $$4.m_37446_($$3);
            $$4.m_37251_(p_43143_, p_43143_.m_146909_(), p_43143_.m_146908_(), 0.0f, 1.5f, 1.0f);
            p_43142_.m_7967_($$4);
        }
        p_43143_.m_36246_(Stats.f_12982_.m_12902_(this));
        if (!p_43143_.m_150110_().f_35937_) {
            $$3.m_41774_(1);
        }
        return InteractionResultHolder.m_19092_($$3, p_43142_.m_5776_());
    }
}


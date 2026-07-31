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
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EnderpearlItem
extends Item {
    public EnderpearlItem(Item.Properties p_41188_) {
        super(p_41188_);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_41190_, Player p_41191_, InteractionHand p_41192_) {
        ItemStack $$3 = p_41191_.m_21120_(p_41192_);
        p_41190_.m_6263_(null, p_41191_.m_20185_(), p_41191_.m_20186_(), p_41191_.m_20189_(), SoundEvents.f_11857_, SoundSource.NEUTRAL, 0.5f, 0.4f / (p_41190_.m_213780_().m_188501_() * 0.4f + 0.8f));
        p_41191_.m_36335_().m_41524_(this, 20);
        if (!p_41190_.f_46443_) {
            ThrownEnderpearl $$4 = new ThrownEnderpearl(p_41190_, p_41191_);
            $$4.m_37446_($$3);
            $$4.m_37251_(p_41191_, p_41191_.m_146909_(), p_41191_.m_146908_(), 0.0f, 1.5f, 1.0f);
            p_41190_.m_7967_($$4);
        }
        p_41191_.m_36246_(Stats.f_12982_.m_12902_(this));
        if (!p_41191_.m_150110_().f_35937_) {
            $$3.m_41774_(1);
        }
        return InteractionResultHolder.m_19092_($$3, p_41190_.m_5776_());
    }
}


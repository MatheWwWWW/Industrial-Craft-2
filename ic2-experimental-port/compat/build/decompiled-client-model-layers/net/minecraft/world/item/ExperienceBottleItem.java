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
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ExperienceBottleItem
extends Item {
    public ExperienceBottleItem(Item.Properties p_41194_) {
        super(p_41194_);
    }

    @Override
    public boolean m_5812_(ItemStack p_41200_) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_41196_, Player p_41197_, InteractionHand p_41198_) {
        ItemStack $$3 = p_41197_.m_21120_(p_41198_);
        p_41196_.m_6263_(null, p_41197_.m_20185_(), p_41197_.m_20186_(), p_41197_.m_20189_(), SoundEvents.f_11870_, SoundSource.NEUTRAL, 0.5f, 0.4f / (p_41196_.m_213780_().m_188501_() * 0.4f + 0.8f));
        if (!p_41196_.f_46443_) {
            ThrownExperienceBottle $$4 = new ThrownExperienceBottle(p_41196_, p_41197_);
            $$4.m_37446_($$3);
            $$4.m_37251_(p_41197_, p_41197_.m_146909_(), p_41197_.m_146908_(), -20.0f, 0.7f, 1.0f);
            p_41196_.m_7967_($$4);
        }
        p_41197_.m_36246_(Stats.f_12982_.m_12902_(this));
        if (!p_41197_.m_150110_().f_35937_) {
            $$3.m_41774_(1);
        }
        return InteractionResultHolder.m_19092_($$3, p_41196_.m_5776_());
    }
}


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
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EggItem
extends Item {
    public EggItem(Item.Properties p_41126_) {
        super(p_41126_);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_41128_, Player p_41129_, InteractionHand p_41130_) {
        ItemStack $$3 = p_41129_.m_21120_(p_41130_);
        p_41128_.m_6263_(null, p_41129_.m_20185_(), p_41129_.m_20186_(), p_41129_.m_20189_(), SoundEvents.f_11877_, SoundSource.PLAYERS, 0.5f, 0.4f / (p_41128_.m_213780_().m_188501_() * 0.4f + 0.8f));
        if (!p_41128_.f_46443_) {
            ThrownEgg $$4 = new ThrownEgg(p_41128_, p_41129_);
            $$4.m_37446_($$3);
            $$4.m_37251_(p_41129_, p_41129_.m_146909_(), p_41129_.m_146908_(), 0.0f, 1.5f, 1.0f);
            p_41128_.m_7967_($$4);
        }
        p_41129_.m_36246_(Stats.f_12982_.m_12902_(this));
        if (!p_41129_.m_150110_().f_35937_) {
            $$3.m_41774_(1);
        }
        return InteractionResultHolder.m_19092_($$3, p_41128_.m_5776_());
    }
}


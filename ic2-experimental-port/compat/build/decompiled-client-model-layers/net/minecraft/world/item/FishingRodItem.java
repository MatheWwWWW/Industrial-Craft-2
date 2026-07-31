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
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class FishingRodItem
extends Item
implements Vanishable {
    public FishingRodItem(Item.Properties p_41285_) {
        super(p_41285_);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_41290_, Player p_41291_, InteractionHand p_41292_) {
        ItemStack $$3 = p_41291_.m_21120_(p_41292_);
        if (p_41291_.f_36083_ != null) {
            if (!p_41290_.f_46443_) {
                int $$4 = p_41291_.f_36083_.m_37156_($$3);
                $$3.m_41622_($$4, p_41291_, p_41288_ -> p_41288_.m_21190_(p_41292_));
            }
            p_41290_.m_6263_(null, p_41291_.m_20185_(), p_41291_.m_20186_(), p_41291_.m_20189_(), SoundEvents.f_11939_, SoundSource.NEUTRAL, 1.0f, 0.4f / (p_41290_.m_213780_().m_188501_() * 0.4f + 0.8f));
            p_41291_.m_146850_(GameEvent.f_223697_);
        } else {
            p_41290_.m_6263_(null, p_41291_.m_20185_(), p_41291_.m_20186_(), p_41291_.m_20189_(), SoundEvents.f_11941_, SoundSource.NEUTRAL, 0.5f, 0.4f / (p_41290_.m_213780_().m_188501_() * 0.4f + 0.8f));
            if (!p_41290_.f_46443_) {
                int $$5 = EnchantmentHelper.m_44916_($$3);
                int $$6 = EnchantmentHelper.m_44904_($$3);
                p_41290_.m_7967_(new FishingHook(p_41291_, p_41290_, $$6, $$5));
            }
            p_41291_.m_36246_(Stats.f_12982_.m_12902_(this));
            p_41291_.m_146850_(GameEvent.f_223698_);
        }
        return InteractionResultHolder.m_19092_($$3, p_41290_.m_5776_());
    }

    @Override
    public int m_6473_() {
        return 1;
    }
}


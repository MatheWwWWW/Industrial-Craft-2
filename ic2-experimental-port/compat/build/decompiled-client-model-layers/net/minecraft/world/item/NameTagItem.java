/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class NameTagItem
extends Item {
    public NameTagItem(Item.Properties p_42952_) {
        super(p_42952_);
    }

    @Override
    public InteractionResult m_6880_(ItemStack p_42954_, Player p_42955_, LivingEntity p_42956_, InteractionHand p_42957_) {
        if (p_42954_.m_41788_() && !(p_42956_ instanceof Player)) {
            if (!p_42955_.f_19853_.f_46443_ && p_42956_.m_6084_()) {
                p_42956_.m_6593_(p_42954_.m_41786_());
                if (p_42956_ instanceof Mob) {
                    ((Mob)p_42956_).m_21530_();
                }
                p_42954_.m_41774_(1);
            }
            return InteractionResult.m_19078_(p_42955_.f_19853_.f_46443_);
        }
        return InteractionResult.PASS;
    }
}


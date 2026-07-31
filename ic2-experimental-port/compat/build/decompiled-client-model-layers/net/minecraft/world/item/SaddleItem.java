/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;

public class SaddleItem
extends Item {
    public SaddleItem(Item.Properties p_43053_) {
        super(p_43053_);
    }

    @Override
    public InteractionResult m_6880_(ItemStack p_43055_, Player p_43056_, LivingEntity p_43057_, InteractionHand p_43058_) {
        Saddleable $$4;
        if (p_43057_ instanceof Saddleable && p_43057_.m_6084_() && !($$4 = (Saddleable)((Object)p_43057_)).m_6254_() && $$4.m_6741_()) {
            if (!p_43056_.f_19853_.f_46443_) {
                $$4.m_5853_(SoundSource.NEUTRAL);
                p_43057_.f_19853_.m_220400_(p_43057_, GameEvent.f_157811_, p_43057_.m_20182_());
                p_43055_.m_41774_(1);
            }
            return InteractionResult.m_19078_(p_43056_.f_19853_.f_46443_);
        }
        return InteractionResult.PASS;
    }
}


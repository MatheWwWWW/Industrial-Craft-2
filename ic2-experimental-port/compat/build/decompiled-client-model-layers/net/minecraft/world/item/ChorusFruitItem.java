/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class ChorusFruitItem
extends Item {
    public ChorusFruitItem(Item.Properties p_40710_) {
        super(p_40710_);
    }

    @Override
    public ItemStack m_5922_(ItemStack p_40712_, Level p_40713_, LivingEntity p_40714_) {
        ItemStack $$3 = super.m_5922_(p_40712_, p_40713_, p_40714_);
        if (!p_40713_.f_46443_) {
            double $$4 = p_40714_.m_20185_();
            double $$5 = p_40714_.m_20186_();
            double $$6 = p_40714_.m_20189_();
            for (int $$7 = 0; $$7 < 16; ++$$7) {
                double $$8 = p_40714_.m_20185_() + (p_40714_.m_217043_().m_188500_() - 0.5) * 16.0;
                double $$9 = Mth.m_14008_(p_40714_.m_20186_() + (double)(p_40714_.m_217043_().m_188503_(16) - 8), p_40713_.m_141937_(), p_40713_.m_141937_() + ((ServerLevel)p_40713_).m_143344_() - 1);
                double $$10 = p_40714_.m_20189_() + (p_40714_.m_217043_().m_188500_() - 0.5) * 16.0;
                if (p_40714_.m_20159_()) {
                    p_40714_.m_8127_();
                }
                Vec3 $$11 = p_40714_.m_20182_();
                if (!p_40714_.m_20984_($$8, $$9, $$10, true)) continue;
                p_40713_.m_214171_(GameEvent.f_238175_, $$11, GameEvent.Context.m_223717_(p_40714_));
                SoundEvent $$12 = p_40714_ instanceof Fox ? SoundEvents.f_11953_ : SoundEvents.f_11757_;
                p_40713_.m_6263_(null, $$4, $$5, $$6, $$12, SoundSource.PLAYERS, 1.0f, 1.0f);
                p_40714_.m_5496_($$12, 1.0f, 1.0f);
                break;
            }
            if (p_40714_ instanceof Player) {
                ((Player)p_40714_).m_36335_().m_41524_(this, 20);
            }
        }
        return $$3;
    }
}


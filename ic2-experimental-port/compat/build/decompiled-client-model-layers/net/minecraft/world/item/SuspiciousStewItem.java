/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SuspiciousStewItem
extends Item {
    public static final String f_151225_ = "Effects";
    public static final String f_151226_ = "EffectId";
    public static final String f_151227_ = "EffectDuration";

    public SuspiciousStewItem(Item.Properties p_43257_) {
        super(p_43257_);
    }

    public static void m_43258_(ItemStack p_43259_, MobEffect p_43260_, int p_43261_) {
        CompoundTag $$3 = p_43259_.m_41784_();
        ListTag $$4 = $$3.m_128437_(f_151225_, 9);
        CompoundTag $$5 = new CompoundTag();
        $$5.m_128405_(f_151226_, MobEffect.m_19459_(p_43260_));
        $$5.m_128405_(f_151227_, p_43261_);
        $$4.add($$5);
        $$3.m_128365_(f_151225_, $$4);
    }

    @Override
    public ItemStack m_5922_(ItemStack p_43263_, Level p_43264_, LivingEntity p_43265_) {
        ItemStack $$3 = super.m_5922_(p_43263_, p_43264_, p_43265_);
        CompoundTag $$4 = p_43263_.m_41783_();
        if ($$4 != null && $$4.m_128425_(f_151225_, 9)) {
            ListTag $$5 = $$4.m_128437_(f_151225_, 10);
            for (int $$6 = 0; $$6 < $$5.size(); ++$$6) {
                MobEffect $$9;
                int $$7 = 160;
                CompoundTag $$8 = $$5.m_128728_($$6);
                if ($$8.m_128425_(f_151227_, 3)) {
                    $$7 = $$8.m_128451_(f_151227_);
                }
                if (($$9 = MobEffect.m_19453_($$8.m_128451_(f_151226_))) == null) continue;
                p_43265_.m_7292_(new MobEffectInstance($$9, $$7));
            }
        }
        if (p_43265_ instanceof Player && ((Player)p_43265_).m_150110_().f_35937_) {
            return $$3;
        }
        return new ItemStack(Items.f_42399_);
    }
}


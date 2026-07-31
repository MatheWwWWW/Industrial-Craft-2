/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.SkeletonTrapGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SkeletonHorse
extends AbstractHorse {
    private final SkeletonTrapGoal f_30890_ = new SkeletonTrapGoal(this);
    private static final int f_149551_ = 18000;
    private boolean f_30891_;
    private int f_30892_;

    public SkeletonHorse(EntityType<? extends SkeletonHorse> p_30894_, Level p_30895_) {
        super((EntityType<? extends AbstractHorse>)p_30894_, p_30895_);
    }

    public static AttributeSupplier.Builder m_30918_() {
        return SkeletonHorse.m_30627_().m_22268_(Attributes.f_22276_, 15.0).m_22268_(Attributes.f_22279_, 0.2f);
    }

    @Override
    protected void m_214179_(RandomSource p_218821_) {
        this.m_21051_(Attributes.f_22288_).m_22100_(this.m_218809_(p_218821_));
    }

    @Override
    protected void m_7509_() {
    }

    @Override
    protected SoundEvent m_7515_() {
        super.m_7515_();
        if (this.m_204029_(FluidTags.f_13131_)) {
            return SoundEvents.f_12429_;
        }
        return SoundEvents.f_12425_;
    }

    @Override
    protected SoundEvent m_5592_() {
        super.m_5592_();
        return SoundEvents.f_12426_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_30916_) {
        super.m_7975_(p_30916_);
        return SoundEvents.f_12427_;
    }

    @Override
    protected SoundEvent m_5501_() {
        if (this.f_19861_) {
            if (this.m_20160_()) {
                ++this.f_30524_;
                if (this.f_30524_ > 5 && this.f_30524_ % 3 == 0) {
                    return SoundEvents.f_12430_;
                }
                if (this.f_30524_ <= 5) {
                    return SoundEvents.f_12380_;
                }
            } else {
                return SoundEvents.f_12380_;
            }
        }
        return SoundEvents.f_12428_;
    }

    @Override
    protected void m_5625_(float p_30911_) {
        if (this.f_19861_) {
            super.m_5625_(0.3f);
        } else {
            super.m_5625_(Math.min(0.1f, p_30911_ * 25.0f));
        }
    }

    @Override
    protected void m_7486_() {
        if (this.m_20069_()) {
            this.m_5496_(SoundEvents.f_12379_, 0.4f, 1.0f);
        } else {
            super.m_7486_();
        }
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21641_;
    }

    @Override
    public double m_6048_() {
        return super.m_6048_() - 0.1875;
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        if (this.m_30919_() && this.f_30892_++ >= 18000) {
            this.m_146870_();
        }
    }

    @Override
    public void m_7380_(CompoundTag p_30907_) {
        super.m_7380_(p_30907_);
        p_30907_.m_128379_("SkeletonTrap", this.m_30919_());
        p_30907_.m_128405_("SkeletonTrapTime", this.f_30892_);
    }

    @Override
    public void m_7378_(CompoundTag p_30901_) {
        super.m_7378_(p_30901_);
        this.m_30923_(p_30901_.m_128471_("SkeletonTrap"));
        this.f_30892_ = p_30901_.m_128451_("SkeletonTrapTime");
    }

    @Override
    public boolean m_6146_() {
        return true;
    }

    @Override
    protected float m_6108_() {
        return 0.96f;
    }

    public boolean m_30919_() {
        return this.f_30891_;
    }

    public void m_30923_(boolean p_30924_) {
        if (p_30924_ == this.f_30891_) {
            return;
        }
        this.f_30891_ = p_30924_;
        if (p_30924_) {
            this.f_21345_.m_25352_(1, this.f_30890_);
        } else {
            this.f_21345_.m_25363_(this.f_30890_);
        }
    }

    @Override
    @Nullable
    public AgeableMob m_142606_(ServerLevel p_149553_, AgeableMob p_149554_) {
        return EntityType.f_20525_.m_20615_(p_149553_);
    }

    @Override
    public InteractionResult m_6071_(Player p_30904_, InteractionHand p_30905_) {
        ItemStack $$2 = p_30904_.m_21120_(p_30905_);
        if (!this.m_30614_()) {
            return InteractionResult.PASS;
        }
        if (this.m_6162_()) {
            return super.m_6071_(p_30904_, p_30905_);
        }
        if (p_30904_.m_36341_()) {
            this.m_213583_(p_30904_);
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        if (this.m_20160_()) {
            return super.m_6071_(p_30904_, p_30905_);
        }
        if (!$$2.m_41619_()) {
            if ($$2.m_150930_(Items.f_42450_) && !this.m_6254_()) {
                this.m_213583_(p_30904_);
                return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            }
            InteractionResult $$3 = $$2.m_41647_(p_30904_, this, p_30905_);
            if ($$3.m_19077_()) {
                return $$3;
            }
        }
        this.m_6835_(p_30904_);
        return InteractionResult.m_19078_(this.f_19853_.f_46443_);
    }
}


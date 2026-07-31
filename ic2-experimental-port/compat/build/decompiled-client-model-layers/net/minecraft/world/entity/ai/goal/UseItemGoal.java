/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;

public class UseItemGoal<T extends Mob>
extends Goal {
    private final T f_25967_;
    private final ItemStack f_25968_;
    private final Predicate<? super T> f_25969_;
    @Nullable
    private final SoundEvent f_25970_;

    public UseItemGoal(T p_25972_, ItemStack p_25973_, @Nullable SoundEvent p_25974_, Predicate<? super T> p_25975_) {
        this.f_25967_ = p_25972_;
        this.f_25968_ = p_25973_;
        this.f_25970_ = p_25974_;
        this.f_25969_ = p_25975_;
    }

    @Override
    public boolean m_8036_() {
        return this.f_25969_.test(this.f_25967_);
    }

    @Override
    public boolean m_8045_() {
        return ((LivingEntity)this.f_25967_).m_6117_();
    }

    @Override
    public void m_8056_() {
        ((Mob)this.f_25967_).m_8061_(EquipmentSlot.MAINHAND, this.f_25968_.m_41777_());
        ((LivingEntity)this.f_25967_).m_6672_(InteractionHand.MAIN_HAND);
    }

    @Override
    public void m_8041_() {
        ((Mob)this.f_25967_).m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
        if (this.f_25970_ != null) {
            ((Entity)this.f_25967_).m_5496_(this.f_25970_, 1.0f, ((LivingEntity)this.f_25967_).m_217043_().m_188501_() * 0.2f + 0.9f);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal.target;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.raid.Raider;

public class NearestAttackableWitchTargetGoal<T extends LivingEntity>
extends NearestAttackableTargetGoal<T> {
    private boolean f_26074_ = true;

    public NearestAttackableWitchTargetGoal(Raider p_26076_, Class<T> p_26077_, int p_26078_, boolean p_26079_, boolean p_26080_, @Nullable Predicate<LivingEntity> p_26081_) {
        super(p_26076_, p_26077_, p_26078_, p_26079_, p_26080_, p_26081_);
    }

    public void m_26083_(boolean p_26084_) {
        this.f_26074_ = p_26084_;
    }

    @Override
    public boolean m_8036_() {
        return this.f_26074_ && super.m_8036_();
    }
}


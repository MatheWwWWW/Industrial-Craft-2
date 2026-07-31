/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Zombie;

public class ZombieAttackGoal
extends MeleeAttackGoal {
    private final Zombie f_26016_;
    private int f_26017_;

    public ZombieAttackGoal(Zombie p_26019_, double p_26020_, boolean p_26021_) {
        super(p_26019_, p_26020_, p_26021_);
        this.f_26016_ = p_26019_;
    }

    @Override
    public void m_8056_() {
        super.m_8056_();
        this.f_26017_ = 0;
    }

    @Override
    public void m_8041_() {
        super.m_8041_();
        this.f_26016_.m_21561_(false);
    }

    @Override
    public void m_8037_() {
        super.m_8037_();
        ++this.f_26017_;
        if (this.f_26017_ >= 5 && this.m_25565_() < this.m_25566_() / 2) {
            this.f_26016_.m_21561_(true);
        } else {
            this.f_26016_.m_21561_(false);
        }
    }
}


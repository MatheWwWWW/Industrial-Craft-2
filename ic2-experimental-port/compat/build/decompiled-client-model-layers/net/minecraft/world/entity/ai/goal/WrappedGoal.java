/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.ai.goal.Goal;

public class WrappedGoal
extends Goal {
    private final Goal f_25994_;
    private final int f_25995_;
    private boolean f_25996_;

    public WrappedGoal(int p_25998_, Goal p_25999_) {
        this.f_25995_ = p_25998_;
        this.f_25994_ = p_25999_;
    }

    public boolean m_26002_(WrappedGoal p_26003_) {
        return this.m_6767_() && p_26003_.m_26012_() < this.m_26012_();
    }

    @Override
    public boolean m_8036_() {
        return this.f_25994_.m_8036_();
    }

    @Override
    public boolean m_8045_() {
        return this.f_25994_.m_8045_();
    }

    @Override
    public boolean m_6767_() {
        return this.f_25994_.m_6767_();
    }

    @Override
    public void m_8056_() {
        if (this.f_25996_) {
            return;
        }
        this.f_25996_ = true;
        this.f_25994_.m_8056_();
    }

    @Override
    public void m_8041_() {
        if (!this.f_25996_) {
            return;
        }
        this.f_25996_ = false;
        this.f_25994_.m_8041_();
    }

    @Override
    public boolean m_183429_() {
        return this.f_25994_.m_183429_();
    }

    @Override
    protected int m_183277_(int p_186092_) {
        return this.f_25994_.m_183277_(p_186092_);
    }

    @Override
    public void m_8037_() {
        this.f_25994_.m_8037_();
    }

    @Override
    public void m_7021_(EnumSet<Goal.Flag> p_26005_) {
        this.f_25994_.m_7021_(p_26005_);
    }

    @Override
    public EnumSet<Goal.Flag> m_7684_() {
        return this.f_25994_.m_7684_();
    }

    public boolean m_7620_() {
        return this.f_25996_;
    }

    public int m_26012_() {
        return this.f_25995_;
    }

    public Goal m_26015_() {
        return this.f_25994_;
    }

    public boolean equals(@Nullable Object p_26011_) {
        if (this == p_26011_) {
            return true;
        }
        if (p_26011_ == null || this.getClass() != p_26011_.getClass()) {
            return false;
        }
        return this.f_25994_.equals(((WrappedGoal)p_26011_).f_25994_);
    }

    public int hashCode() {
        return this.f_25994_.hashCode();
    }
}


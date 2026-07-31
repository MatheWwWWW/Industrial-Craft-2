/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityDimensions {
    public final float f_20377_;
    public final float f_20378_;
    public final boolean f_20379_;

    public EntityDimensions(float p_20381_, float p_20382_, boolean p_20383_) {
        this.f_20377_ = p_20381_;
        this.f_20378_ = p_20382_;
        this.f_20379_ = p_20383_;
    }

    public AABB m_20393_(Vec3 p_20394_) {
        return this.m_20384_(p_20394_.f_82479_, p_20394_.f_82480_, p_20394_.f_82481_);
    }

    public AABB m_20384_(double p_20385_, double p_20386_, double p_20387_) {
        float $$3 = this.f_20377_ / 2.0f;
        float $$4 = this.f_20378_;
        return new AABB(p_20385_ - (double)$$3, p_20386_, p_20387_ - (double)$$3, p_20385_ + (double)$$3, p_20386_ + (double)$$4, p_20387_ + (double)$$3);
    }

    public EntityDimensions m_20388_(float p_20389_) {
        return this.m_20390_(p_20389_, p_20389_);
    }

    public EntityDimensions m_20390_(float p_20391_, float p_20392_) {
        if (this.f_20379_ || p_20391_ == 1.0f && p_20392_ == 1.0f) {
            return this;
        }
        return EntityDimensions.m_20395_(this.f_20377_ * p_20391_, this.f_20378_ * p_20392_);
    }

    public static EntityDimensions m_20395_(float p_20396_, float p_20397_) {
        return new EntityDimensions(p_20396_, p_20397_, false);
    }

    public static EntityDimensions m_20398_(float p_20399_, float p_20400_) {
        return new EntityDimensions(p_20399_, p_20400_, true);
    }

    public String toString() {
        return "EntityDimensions w=" + this.f_20377_ + ", h=" + this.f_20378_ + ", fixed=" + this.f_20379_;
    }
}


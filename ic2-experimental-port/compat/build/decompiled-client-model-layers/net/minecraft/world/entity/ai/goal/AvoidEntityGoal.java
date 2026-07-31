/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class AvoidEntityGoal<T extends LivingEntity>
extends Goal {
    protected final PathfinderMob f_25015_;
    private final double f_25023_;
    private final double f_25024_;
    @Nullable
    protected T f_25016_;
    protected final float f_25017_;
    @Nullable
    protected Path f_25018_;
    protected final PathNavigation f_25019_;
    protected final Class<T> f_25020_;
    protected final Predicate<LivingEntity> f_25021_;
    protected final Predicate<LivingEntity> f_25022_;
    private final TargetingConditions f_25025_;

    public AvoidEntityGoal(PathfinderMob p_25027_, Class<T> p_25028_, float p_25029_, double p_25030_, double p_25031_) {
        this(p_25027_, p_25028_, p_25052_ -> true, p_25029_, p_25030_, p_25031_, EntitySelector.f_20406_::test);
    }

    public AvoidEntityGoal(PathfinderMob p_25040_, Class<T> p_25041_, Predicate<LivingEntity> p_25042_, float p_25043_, double p_25044_, double p_25045_, Predicate<LivingEntity> p_25046_) {
        this.f_25015_ = p_25040_;
        this.f_25020_ = p_25041_;
        this.f_25021_ = p_25042_;
        this.f_25017_ = p_25043_;
        this.f_25023_ = p_25044_;
        this.f_25024_ = p_25045_;
        this.f_25022_ = p_25046_;
        this.f_25019_ = p_25040_.m_21573_();
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        this.f_25025_ = TargetingConditions.m_148352_().m_26883_(p_25043_).m_26888_(p_25046_.and(p_25042_));
    }

    public AvoidEntityGoal(PathfinderMob p_25033_, Class<T> p_25034_, float p_25035_, double p_25036_, double p_25037_, Predicate<LivingEntity> p_25038_) {
        this(p_25033_, p_25034_, p_25049_ -> true, p_25035_, p_25036_, p_25037_, p_25038_);
    }

    @Override
    public boolean m_8036_() {
        this.f_25016_ = this.f_25015_.f_19853_.m_45982_(this.f_25015_.f_19853_.m_6443_(this.f_25020_, this.f_25015_.m_20191_().m_82377_(this.f_25017_, 3.0, this.f_25017_), p_148078_ -> true), this.f_25025_, this.f_25015_, this.f_25015_.m_20185_(), this.f_25015_.m_20186_(), this.f_25015_.m_20189_());
        if (this.f_25016_ == null) {
            return false;
        }
        Vec3 $$0 = DefaultRandomPos.m_148407_(this.f_25015_, 16, 7, ((Entity)this.f_25016_).m_20182_());
        if ($$0 == null) {
            return false;
        }
        if (((Entity)this.f_25016_).m_20275_($$0.f_82479_, $$0.f_82480_, $$0.f_82481_) < ((Entity)this.f_25016_).m_20280_(this.f_25015_)) {
            return false;
        }
        this.f_25018_ = this.f_25019_.m_26524_($$0.f_82479_, $$0.f_82480_, $$0.f_82481_, 0);
        return this.f_25018_ != null;
    }

    @Override
    public boolean m_8045_() {
        return !this.f_25019_.m_26571_();
    }

    @Override
    public void m_8056_() {
        this.f_25019_.m_26536_(this.f_25018_, this.f_25023_);
    }

    @Override
    public void m_8041_() {
        this.f_25016_ = null;
    }

    @Override
    public void m_8037_() {
        if (this.f_25015_.m_20280_((Entity)this.f_25016_) < 49.0) {
            this.f_25015_.m_21573_().m_26517_(this.f_25024_);
        } else {
            this.f_25015_.m_21573_().m_26517_(this.f_25023_);
        }
    }
}


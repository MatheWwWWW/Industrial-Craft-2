/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class NearestAttackableTargetGoal<T extends LivingEntity>
extends TargetGoal {
    private static final int f_199889_ = 10;
    protected final Class<T> f_26048_;
    protected final int f_26049_;
    @Nullable
    protected LivingEntity f_26050_;
    protected TargetingConditions f_26051_;

    public NearestAttackableTargetGoal(Mob p_26060_, Class<T> p_26061_, boolean p_26062_) {
        this(p_26060_, p_26061_, 10, p_26062_, false, null);
    }

    public NearestAttackableTargetGoal(Mob p_199891_, Class<T> p_199892_, boolean p_199893_, Predicate<LivingEntity> p_199894_) {
        this(p_199891_, p_199892_, 10, p_199893_, false, p_199894_);
    }

    public NearestAttackableTargetGoal(Mob p_26064_, Class<T> p_26065_, boolean p_26066_, boolean p_26067_) {
        this(p_26064_, p_26065_, 10, p_26066_, p_26067_, null);
    }

    public NearestAttackableTargetGoal(Mob p_26053_, Class<T> p_26054_, int p_26055_, boolean p_26056_, boolean p_26057_, @Nullable Predicate<LivingEntity> p_26058_) {
        super(p_26053_, p_26056_, p_26057_);
        this.f_26048_ = p_26054_;
        this.f_26049_ = NearestAttackableTargetGoal.m_186073_(p_26055_);
        this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
        this.f_26051_ = TargetingConditions.m_148352_().m_26883_(this.m_7623_()).m_26888_(p_26058_);
    }

    @Override
    public boolean m_8036_() {
        if (this.f_26049_ > 0 && this.f_26135_.m_217043_().m_188503_(this.f_26049_) != 0) {
            return false;
        }
        this.m_26073_();
        return this.f_26050_ != null;
    }

    protected AABB m_7255_(double p_26069_) {
        return this.f_26135_.m_20191_().m_82377_(p_26069_, 4.0, p_26069_);
    }

    protected void m_26073_() {
        this.f_26050_ = this.f_26048_ == Player.class || this.f_26048_ == ServerPlayer.class ? this.f_26135_.f_19853_.m_45949_(this.f_26051_, this.f_26135_, this.f_26135_.m_20185_(), this.f_26135_.m_20188_(), this.f_26135_.m_20189_()) : this.f_26135_.f_19853_.m_45982_(this.f_26135_.f_19853_.m_6443_(this.f_26048_, this.m_7255_(this.m_7623_()), p_148152_ -> true), this.f_26051_, this.f_26135_, this.f_26135_.m_20185_(), this.f_26135_.m_20188_(), this.f_26135_.m_20189_());
    }

    @Override
    public void m_8056_() {
        this.f_26135_.m_6710_(this.f_26050_);
        super.m_8056_();
    }

    public void m_26070_(@Nullable LivingEntity p_26071_) {
        this.f_26050_ = p_26071_;
    }
}


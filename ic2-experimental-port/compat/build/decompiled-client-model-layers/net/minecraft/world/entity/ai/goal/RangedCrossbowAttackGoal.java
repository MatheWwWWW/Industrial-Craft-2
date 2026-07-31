/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RangedCrossbowAttackGoal<T extends Monster & CrossbowAttackMob>
extends Goal {
    public static final UniformInt f_25804_ = TimeUtil.m_145020_(1, 2);
    private final T f_25805_;
    private CrossbowState f_25806_ = CrossbowState.UNCHARGED;
    private final double f_25807_;
    private final float f_25808_;
    private int f_25809_;
    private int f_25810_;
    private int f_25811_;

    public RangedCrossbowAttackGoal(T p_25814_, double p_25815_, float p_25816_) {
        this.f_25805_ = p_25814_;
        this.f_25807_ = p_25815_;
        this.f_25808_ = p_25816_ * p_25816_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean m_8036_() {
        return this.m_25822_() && this.m_25821_();
    }

    private boolean m_25821_() {
        return ((LivingEntity)this.f_25805_).m_21055_(Items.f_42717_);
    }

    @Override
    public boolean m_8045_() {
        return this.m_25822_() && (this.m_8036_() || !((Mob)this.f_25805_).m_21573_().m_26571_()) && this.m_25821_();
    }

    private boolean m_25822_() {
        return ((Mob)this.f_25805_).m_5448_() != null && ((Mob)this.f_25805_).m_5448_().m_6084_();
    }

    @Override
    public void m_8041_() {
        super.m_8041_();
        ((Mob)this.f_25805_).m_21561_(false);
        ((Mob)this.f_25805_).m_6710_(null);
        this.f_25809_ = 0;
        if (((LivingEntity)this.f_25805_).m_6117_()) {
            ((LivingEntity)this.f_25805_).m_5810_();
            ((CrossbowAttackMob)this.f_25805_).m_6136_(false);
            CrossbowItem.m_40884_(((LivingEntity)this.f_25805_).m_21211_(), false);
        }
    }

    @Override
    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        boolean $$4;
        boolean $$2;
        LivingEntity $$0 = ((Mob)this.f_25805_).m_5448_();
        if ($$0 == null) {
            return;
        }
        boolean $$1 = ((Mob)this.f_25805_).m_21574_().m_148306_($$0);
        boolean bl = $$2 = this.f_25809_ > 0;
        if ($$1 != $$2) {
            this.f_25809_ = 0;
        }
        this.f_25809_ = $$1 ? ++this.f_25809_ : --this.f_25809_;
        double $$3 = ((Entity)this.f_25805_).m_20280_($$0);
        boolean bl2 = $$4 = ($$3 > (double)this.f_25808_ || this.f_25809_ < 5) && this.f_25810_ == 0;
        if ($$4) {
            --this.f_25811_;
            if (this.f_25811_ <= 0) {
                ((Mob)this.f_25805_).m_21573_().m_5624_($$0, this.m_25823_() ? this.f_25807_ : this.f_25807_ * 0.5);
                this.f_25811_ = f_25804_.m_214085_(((LivingEntity)this.f_25805_).m_217043_());
            }
        } else {
            this.f_25811_ = 0;
            ((Mob)this.f_25805_).m_21573_().m_26573_();
        }
        ((Mob)this.f_25805_).m_21563_().m_24960_($$0, 30.0f, 30.0f);
        if (this.f_25806_ == CrossbowState.UNCHARGED) {
            if (!$$4) {
                ((LivingEntity)this.f_25805_).m_6672_(ProjectileUtil.m_37297_(this.f_25805_, Items.f_42717_));
                this.f_25806_ = CrossbowState.CHARGING;
                ((CrossbowAttackMob)this.f_25805_).m_6136_(true);
            }
        } else if (this.f_25806_ == CrossbowState.CHARGING) {
            ItemStack $$6;
            int $$5;
            if (!((LivingEntity)this.f_25805_).m_6117_()) {
                this.f_25806_ = CrossbowState.UNCHARGED;
            }
            if (($$5 = ((LivingEntity)this.f_25805_).m_21252_()) >= CrossbowItem.m_40939_($$6 = ((LivingEntity)this.f_25805_).m_21211_())) {
                ((LivingEntity)this.f_25805_).m_21253_();
                this.f_25806_ = CrossbowState.CHARGED;
                this.f_25810_ = 20 + ((LivingEntity)this.f_25805_).m_217043_().m_188503_(20);
                ((CrossbowAttackMob)this.f_25805_).m_6136_(false);
            }
        } else if (this.f_25806_ == CrossbowState.CHARGED) {
            --this.f_25810_;
            if (this.f_25810_ == 0) {
                this.f_25806_ = CrossbowState.READY_TO_ATTACK;
            }
        } else if (this.f_25806_ == CrossbowState.READY_TO_ATTACK && $$1) {
            ((RangedAttackMob)this.f_25805_).m_6504_($$0, 1.0f);
            ItemStack $$7 = ((LivingEntity)this.f_25805_).m_21120_(ProjectileUtil.m_37297_(this.f_25805_, Items.f_42717_));
            CrossbowItem.m_40884_($$7, false);
            this.f_25806_ = CrossbowState.UNCHARGED;
        }
    }

    private boolean m_25823_() {
        return this.f_25806_ == CrossbowState.UNCHARGED;
    }

    static final class CrossbowState
    extends Enum<CrossbowState> {
        public static final /* enum */ CrossbowState UNCHARGED = new CrossbowState();
        public static final /* enum */ CrossbowState CHARGING = new CrossbowState();
        public static final /* enum */ CrossbowState CHARGED = new CrossbowState();
        public static final /* enum */ CrossbowState READY_TO_ATTACK = new CrossbowState();
        private static final /* synthetic */ CrossbowState[] $VALUES;

        public static CrossbowState[] values() {
            return (CrossbowState[])$VALUES.clone();
        }

        public static CrossbowState valueOf(String p_25834_) {
            return Enum.valueOf(CrossbowState.class, p_25834_);
        }

        private static /* synthetic */ CrossbowState[] m_148134_() {
            return new CrossbowState[]{UNCHARGED, CHARGING, CHARGED, READY_TO_ATTACK};
        }

        static {
            $VALUES = CrossbowState.m_148134_();
        }
    }
}


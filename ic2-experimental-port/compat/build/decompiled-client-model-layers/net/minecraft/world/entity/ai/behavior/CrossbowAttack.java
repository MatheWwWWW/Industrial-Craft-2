/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CrossbowAttack<E extends Mob, T extends LivingEntity>
extends Behavior<E> {
    private static final int f_147479_ = 1200;
    private int f_22771_;
    private CrossbowState f_22772_ = CrossbowState.UNCHARGED;

    public CrossbowAttack() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_PRESENT)), 1200);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_22778_, E p_22779_) {
        LivingEntity $$2 = CrossbowAttack.m_22784_(p_22779_);
        return ((LivingEntity)p_22779_).m_21055_(Items.f_42717_) && BehaviorUtils.m_22667_(p_22779_, $$2) && BehaviorUtils.m_22632_(p_22779_, $$2, 0);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_22781_, E p_22782_, long p_22783_) {
        return ((LivingEntity)p_22782_).m_6274_().m_21874_(MemoryModuleType.f_26372_) && this.m_6114_(p_22781_, p_22782_);
    }

    @Override
    protected void m_6725_(ServerLevel p_22794_, E p_22795_, long p_22796_) {
        LivingEntity $$3 = CrossbowAttack.m_22784_(p_22795_);
        this.m_22797_((Mob)p_22795_, $$3);
        this.m_22786_(p_22795_, $$3);
    }

    @Override
    protected void m_6732_(ServerLevel p_22805_, E p_22806_, long p_22807_) {
        if (((LivingEntity)p_22806_).m_6117_()) {
            ((LivingEntity)p_22806_).m_5810_();
        }
        if (((LivingEntity)p_22806_).m_21055_(Items.f_42717_)) {
            ((CrossbowAttackMob)p_22806_).m_6136_(false);
            CrossbowItem.m_40884_(((LivingEntity)p_22806_).m_21211_(), false);
        }
    }

    private void m_22786_(E p_22787_, LivingEntity p_22788_) {
        if (this.f_22772_ == CrossbowState.UNCHARGED) {
            ((LivingEntity)p_22787_).m_6672_(ProjectileUtil.m_37297_(p_22787_, Items.f_42717_));
            this.f_22772_ = CrossbowState.CHARGING;
            ((CrossbowAttackMob)p_22787_).m_6136_(true);
        } else if (this.f_22772_ == CrossbowState.CHARGING) {
            ItemStack $$3;
            int $$2;
            if (!((LivingEntity)p_22787_).m_6117_()) {
                this.f_22772_ = CrossbowState.UNCHARGED;
            }
            if (($$2 = ((LivingEntity)p_22787_).m_21252_()) >= CrossbowItem.m_40939_($$3 = ((LivingEntity)p_22787_).m_21211_())) {
                ((LivingEntity)p_22787_).m_21253_();
                this.f_22772_ = CrossbowState.CHARGED;
                this.f_22771_ = 20 + ((LivingEntity)p_22787_).m_217043_().m_188503_(20);
                ((CrossbowAttackMob)p_22787_).m_6136_(false);
            }
        } else if (this.f_22772_ == CrossbowState.CHARGED) {
            --this.f_22771_;
            if (this.f_22771_ == 0) {
                this.f_22772_ = CrossbowState.READY_TO_ATTACK;
            }
        } else if (this.f_22772_ == CrossbowState.READY_TO_ATTACK) {
            ((RangedAttackMob)p_22787_).m_6504_(p_22788_, 1.0f);
            ItemStack $$4 = ((LivingEntity)p_22787_).m_21120_(ProjectileUtil.m_37297_(p_22787_, Items.f_42717_));
            CrossbowItem.m_40884_($$4, false);
            this.f_22772_ = CrossbowState.UNCHARGED;
        }
    }

    private void m_22797_(Mob p_22798_, LivingEntity p_22799_) {
        p_22798_.m_6274_().m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_22799_, true));
    }

    private static LivingEntity m_22784_(LivingEntity p_22785_) {
        return p_22785_.m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (E)((Mob)livingEntity), l);
    }

    @Override
    protected /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (E)((Mob)livingEntity), l);
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

        public static CrossbowState valueOf(String p_22822_) {
            return Enum.valueOf(CrossbowState.class, p_22822_);
        }

        private static /* synthetic */ CrossbowState[] m_147480_() {
            return new CrossbowState[]{UNCHARGED, CHARGING, CHARGED, READY_TO_ATTACK};
        }

        static {
            $VALUES = CrossbowState.m_147480_();
        }
    }
}


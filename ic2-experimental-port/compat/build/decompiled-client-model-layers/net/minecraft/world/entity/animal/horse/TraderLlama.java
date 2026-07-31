/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal.horse;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class TraderLlama
extends Llama {
    private int f_30937_ = 47999;

    public TraderLlama(EntityType<? extends TraderLlama> p_30939_, Level p_30940_) {
        super((EntityType<? extends Llama>)p_30939_, p_30940_);
    }

    @Override
    public boolean m_7565_() {
        return true;
    }

    @Override
    protected Llama m_7127_() {
        return EntityType.f_20488_.m_20615_(this.f_19853_);
    }

    @Override
    public void m_7380_(CompoundTag p_30950_) {
        super.m_7380_(p_30950_);
        p_30950_.m_128405_("DespawnDelay", this.f_30937_);
    }

    @Override
    public void m_7378_(CompoundTag p_30948_) {
        super.m_7378_(p_30948_);
        if (p_30948_.m_128425_("DespawnDelay", 99)) {
            this.f_30937_ = p_30948_.m_128451_("DespawnDelay");
        }
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
        this.f_21345_.m_25352_(1, new PanicGoal(this, 2.0));
        this.f_21346_.m_25352_(1, new TraderLlamaDefendWanderingTraderGoal(this));
    }

    public void m_149555_(int p_149556_) {
        this.f_30937_ = p_149556_;
    }

    @Override
    protected void m_6835_(Player p_30958_) {
        Entity $$1 = this.m_21524_();
        if ($$1 instanceof WanderingTrader) {
            return;
        }
        super.m_6835_(p_30958_);
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        if (!this.f_19853_.f_46443_) {
            this.m_30951_();
        }
    }

    private void m_30951_() {
        if (!this.m_30952_()) {
            return;
        }
        int n = this.f_30937_ = this.m_30953_() ? ((WanderingTrader)this.m_21524_()).m_35876_() - 1 : this.f_30937_ - 1;
        if (this.f_30937_ <= 0) {
            this.m_21455_(true, false);
            this.m_146870_();
        }
    }

    private boolean m_30952_() {
        return !this.m_30614_() && !this.m_30954_() && !this.m_146898_();
    }

    private boolean m_30953_() {
        return this.m_21524_() instanceof WanderingTrader;
    }

    private boolean m_30954_() {
        return this.m_21523_() && !this.m_30953_();
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_30942_, DifficultyInstance p_30943_, MobSpawnType p_30944_, @Nullable SpawnGroupData p_30945_, @Nullable CompoundTag p_30946_) {
        if (p_30944_ == MobSpawnType.EVENT) {
            this.m_146762_(0);
        }
        if (p_30945_ == null) {
            p_30945_ = new AgeableMob.AgeableMobGroupData(false);
        }
        return super.m_6518_(p_30942_, p_30943_, p_30944_, p_30945_, p_30946_);
    }

    protected static class TraderLlamaDefendWanderingTraderGoal
    extends TargetGoal {
        private final Llama f_30962_;
        private LivingEntity f_30963_;
        private int f_30964_;

        public TraderLlamaDefendWanderingTraderGoal(Llama p_149558_) {
            super(p_149558_, false);
            this.f_30962_ = p_149558_;
            this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
        }

        @Override
        public boolean m_8036_() {
            if (!this.f_30962_.m_21523_()) {
                return false;
            }
            Entity $$0 = this.f_30962_.m_21524_();
            if (!($$0 instanceof WanderingTrader)) {
                return false;
            }
            WanderingTrader $$1 = (WanderingTrader)$$0;
            this.f_30963_ = $$1.m_21188_();
            int $$2 = $$1.m_21213_();
            return $$2 != this.f_30964_ && this.m_26150_(this.f_30963_, TargetingConditions.f_26872_);
        }

        @Override
        public void m_8056_() {
            this.f_26135_.m_6710_(this.f_30963_);
            Entity $$0 = this.f_30962_.m_21524_();
            if ($$0 instanceof WanderingTrader) {
                this.f_30964_ = ((WanderingTrader)$$0).m_21213_();
            }
            super.m_8056_();
        }
    }
}


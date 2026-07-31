/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster.piglin;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public abstract class AbstractPiglin
extends Monster {
    protected static final EntityDataAccessor<Boolean> f_34648_ = SynchedEntityData.m_135353_(AbstractPiglin.class, EntityDataSerializers.f_135035_);
    protected static final int f_149917_ = 300;
    protected int f_34649_;

    public AbstractPiglin(EntityType<? extends AbstractPiglin> p_34652_, Level p_34653_) {
        super((EntityType<? extends Monster>)p_34652_, p_34653_);
        this.m_21553_(true);
        this.m_34669_();
        this.m_21441_(BlockPathTypes.DANGER_FIRE, 16.0f);
        this.m_21441_(BlockPathTypes.DAMAGE_FIRE, -1.0f);
    }

    private void m_34669_() {
        if (GoalUtils.m_26894_(this)) {
            ((GroundPathNavigation)this.m_21573_()).m_26477_(true);
        }
    }

    protected abstract boolean m_7121_();

    public void m_34670_(boolean p_34671_) {
        this.m_20088_().m_135381_(f_34648_, p_34671_);
    }

    protected boolean m_34665_() {
        return this.m_20088_().m_135370_(f_34648_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_34648_, false);
    }

    @Override
    public void m_7380_(CompoundTag p_34661_) {
        super.m_7380_(p_34661_);
        if (this.m_34665_()) {
            p_34661_.m_128379_("IsImmuneToZombification", true);
        }
        p_34661_.m_128405_("TimeInOverworld", this.f_34649_);
    }

    @Override
    public double m_6049_() {
        return this.m_6162_() ? -0.05 : -0.45;
    }

    @Override
    public void m_7378_(CompoundTag p_34659_) {
        super.m_7378_(p_34659_);
        this.m_34670_(p_34659_.m_128471_("IsImmuneToZombification"));
        this.f_34649_ = p_34659_.m_128451_("TimeInOverworld");
    }

    @Override
    protected void m_8024_() {
        super.m_8024_();
        this.f_34649_ = this.m_34666_() ? ++this.f_34649_ : 0;
        if (this.f_34649_ > 300) {
            this.m_7580_();
            this.m_8063_((ServerLevel)this.f_19853_);
        }
    }

    public boolean m_34666_() {
        return !this.f_19853_.m_6042_().m_63960_() && !this.m_34665_() && !this.m_21525_();
    }

    protected void m_8063_(ServerLevel p_34663_) {
        ZombifiedPiglin $$1 = this.m_21406_(EntityType.f_20531_, true);
        if ($$1 != null) {
            $$1.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 200, 0));
        }
    }

    public boolean m_34667_() {
        return !this.m_6162_();
    }

    public abstract PiglinArmPose m_6389_();

    @Override
    @Nullable
    public LivingEntity m_5448_() {
        return this.f_20939_.m_21952_(MemoryModuleType.f_26372_).orElse(null);
    }

    protected boolean m_34668_() {
        return this.m_21205_().m_41720_() instanceof TieredItem;
    }

    @Override
    public void m_8032_() {
        if (PiglinAi.m_34942_(this)) {
            super.m_8032_();
        }
    }

    @Override
    protected void m_8025_() {
        super.m_8025_();
        DebugPackets.m_133695_(this);
    }

    protected abstract void m_7580_();
}


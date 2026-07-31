/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class AbstractSchoolingFish
extends AbstractFish {
    @Nullable
    private AbstractSchoolingFish f_27520_;
    private int f_27521_ = 1;

    public AbstractSchoolingFish(EntityType<? extends AbstractSchoolingFish> p_27523_, Level p_27524_) {
        super((EntityType<? extends AbstractFish>)p_27523_, p_27524_);
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
        this.f_21345_.m_25352_(5, new FollowFlockLeaderGoal(this));
    }

    @Override
    public int m_5792_() {
        return this.m_6031_();
    }

    public int m_6031_() {
        return super.m_5792_();
    }

    @Override
    protected boolean m_6004_() {
        return !this.m_27540_();
    }

    public boolean m_27540_() {
        return this.f_27520_ != null && this.f_27520_.m_6084_();
    }

    public AbstractSchoolingFish m_27525_(AbstractSchoolingFish p_27526_) {
        this.f_27520_ = p_27526_;
        p_27526_.m_27546_();
        return p_27526_;
    }

    public void m_27541_() {
        this.f_27520_.m_27547_();
        this.f_27520_ = null;
    }

    private void m_27546_() {
        ++this.f_27521_;
    }

    private void m_27547_() {
        --this.f_27521_;
    }

    public boolean m_27542_() {
        return this.m_27543_() && this.f_27521_ < this.m_6031_();
    }

    @Override
    public void m_8119_() {
        List<?> $$0;
        super.m_8119_();
        if (this.m_27543_() && this.f_19853_.f_46441_.m_188503_(200) == 1 && ($$0 = this.f_19853_.m_45976_(this.getClass(), this.m_20191_().m_82377_(8.0, 8.0, 8.0))).size() <= 1) {
            this.f_27521_ = 1;
        }
    }

    public boolean m_27543_() {
        return this.f_27521_ > 1;
    }

    public boolean m_27544_() {
        return this.m_20280_(this.f_27520_) <= 121.0;
    }

    public void m_27545_() {
        if (this.m_27540_()) {
            this.m_21573_().m_5624_(this.f_27520_, 1.0);
        }
    }

    public void m_27533_(Stream<? extends AbstractSchoolingFish> p_27534_) {
        p_27534_.limit(this.m_6031_() - this.f_27521_).filter(p_27538_ -> p_27538_ != this).forEach(p_27536_ -> p_27536_.m_27525_(this));
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_27528_, DifficultyInstance p_27529_, MobSpawnType p_27530_, @Nullable SpawnGroupData p_27531_, @Nullable CompoundTag p_27532_) {
        super.m_6518_(p_27528_, p_27529_, p_27530_, p_27531_, p_27532_);
        if (p_27531_ == null) {
            p_27531_ = new SchoolSpawnGroupData(this);
        } else {
            this.m_27525_(((SchoolSpawnGroupData)p_27531_).f_27551_);
        }
        return p_27531_;
    }

    public static class SchoolSpawnGroupData
    implements SpawnGroupData {
        public final AbstractSchoolingFish f_27551_;

        public SchoolSpawnGroupData(AbstractSchoolingFish p_27553_) {
            this.f_27551_ = p_27553_;
        }
    }
}


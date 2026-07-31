/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;

public abstract class PathfinderMob
extends Mob {
    protected static final float f_186010_ = 0.0f;

    protected PathfinderMob(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super((EntityType<? extends Mob>)p_21683_, p_21684_);
    }

    public float m_21692_(BlockPos p_21693_) {
        return this.m_5610_(p_21693_, this.f_19853_);
    }

    public float m_5610_(BlockPos p_21688_, LevelReader p_21689_) {
        return 0.0f;
    }

    @Override
    public boolean m_5545_(LevelAccessor p_21686_, MobSpawnType p_21687_) {
        return this.m_5610_(this.m_20183_(), p_21686_) >= 0.0f;
    }

    public boolean m_21691_() {
        return !this.m_21573_().m_26571_();
    }

    @Override
    protected void m_6119_() {
        super.m_6119_();
        Entity $$0 = this.m_21524_();
        if ($$0 != null && $$0.f_19853_ == this.f_19853_) {
            this.m_21446_($$0.m_20183_(), 5);
            float $$1 = this.m_20270_($$0);
            if (this instanceof TamableAnimal && ((TamableAnimal)this).m_21825_()) {
                if ($$1 > 10.0f) {
                    this.m_21455_(true, true);
                }
                return;
            }
            this.m_7880_($$1);
            if ($$1 > 10.0f) {
                this.m_21455_(true, true);
                this.f_21345_.m_25355_(Goal.Flag.MOVE);
            } else if ($$1 > 6.0f) {
                double $$2 = ($$0.m_20185_() - this.m_20185_()) / (double)$$1;
                double $$3 = ($$0.m_20186_() - this.m_20186_()) / (double)$$1;
                double $$4 = ($$0.m_20189_() - this.m_20189_()) / (double)$$1;
                this.m_20256_(this.m_20184_().m_82520_(Math.copySign($$2 * $$2 * 0.4, $$2), Math.copySign($$3 * $$3 * 0.4, $$3), Math.copySign($$4 * $$4 * 0.4, $$4)));
            } else if (this.m_213814_()) {
                this.f_21345_.m_25374_(Goal.Flag.MOVE);
                float $$5 = 2.0f;
                Vec3 $$6 = new Vec3($$0.m_20185_() - this.m_20185_(), $$0.m_20186_() - this.m_20186_(), $$0.m_20189_() - this.m_20189_()).m_82541_().m_82490_(Math.max($$1 - 2.0f, 0.0f));
                this.m_21573_().m_26519_(this.m_20185_() + $$6.f_82479_, this.m_20186_() + $$6.f_82480_, this.m_20189_() + $$6.f_82481_, this.m_5823_());
            }
        }
    }

    protected boolean m_213814_() {
        return true;
    }

    protected double m_5823_() {
        return 1.0;
    }

    protected void m_7880_(float p_21694_) {
    }
}


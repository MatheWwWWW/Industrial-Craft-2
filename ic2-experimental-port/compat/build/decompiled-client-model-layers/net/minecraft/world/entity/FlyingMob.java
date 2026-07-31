/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class FlyingMob
extends Mob {
    protected FlyingMob(EntityType<? extends FlyingMob> p_20806_, Level p_20807_) {
        super((EntityType<? extends Mob>)p_20806_, p_20807_);
    }

    @Override
    public boolean m_142535_(float p_147105_, float p_147106_, DamageSource p_147107_) {
        return false;
    }

    @Override
    protected void m_7840_(double p_20809_, boolean p_20810_, BlockState p_20811_, BlockPos p_20812_) {
    }

    @Override
    public void m_7023_(Vec3 p_20818_) {
        if (this.m_6142_() || this.m_6109_()) {
            if (this.m_20069_()) {
                this.m_19920_(0.02f, p_20818_);
                this.m_6478_(MoverType.SELF, this.m_20184_());
                this.m_20256_(this.m_20184_().m_82490_(0.8f));
            } else if (this.m_20077_()) {
                this.m_19920_(0.02f, p_20818_);
                this.m_6478_(MoverType.SELF, this.m_20184_());
                this.m_20256_(this.m_20184_().m_82490_(0.5));
            } else {
                float $$1 = 0.91f;
                if (this.f_19861_) {
                    $$1 = this.f_19853_.m_8055_(new BlockPos(this.m_20185_(), this.m_20186_() - 1.0, this.m_20189_())).m_60734_().m_49958_() * 0.91f;
                }
                float $$2 = 0.16277137f / ($$1 * $$1 * $$1);
                $$1 = 0.91f;
                if (this.f_19861_) {
                    $$1 = this.f_19853_.m_8055_(new BlockPos(this.m_20185_(), this.m_20186_() - 1.0, this.m_20189_())).m_60734_().m_49958_() * 0.91f;
                }
                this.m_19920_(this.f_19861_ ? 0.1f * $$2 : 0.02f, p_20818_);
                this.m_6478_(MoverType.SELF, this.m_20184_());
                this.m_20256_(this.m_20184_().m_82490_($$1));
            }
        }
        this.m_21043_(this, false);
    }

    @Override
    public boolean m_6147_() {
        return false;
    }
}


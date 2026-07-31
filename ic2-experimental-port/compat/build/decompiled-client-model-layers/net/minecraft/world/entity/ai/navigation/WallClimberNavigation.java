/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.navigation;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;

public class WallClimberNavigation
extends GroundPathNavigation {
    @Nullable
    private BlockPos f_26578_;

    public WallClimberNavigation(Mob p_26580_, Level p_26581_) {
        super(p_26580_, p_26581_);
    }

    @Override
    public Path m_7864_(BlockPos p_26589_, int p_26590_) {
        this.f_26578_ = p_26589_;
        return super.m_7864_(p_26589_, p_26590_);
    }

    @Override
    public Path m_6570_(Entity p_26586_, int p_26587_) {
        this.f_26578_ = p_26586_.m_20183_();
        return super.m_6570_(p_26586_, p_26587_);
    }

    @Override
    public boolean m_5624_(Entity p_26583_, double p_26584_) {
        Path $$2 = this.m_6570_(p_26583_, 0);
        if ($$2 != null) {
            return this.m_26536_($$2, p_26584_);
        }
        this.f_26578_ = p_26583_.m_20183_();
        this.f_26497_ = p_26584_;
        return true;
    }

    @Override
    public void m_7638_() {
        if (this.m_26571_()) {
            if (this.f_26578_ != null) {
                if (this.f_26578_.m_203195_(this.f_26494_.m_20182_(), this.f_26494_.m_20205_()) || this.f_26494_.m_20186_() > (double)this.f_26578_.m_123342_() && new BlockPos((double)this.f_26578_.m_123341_(), this.f_26494_.m_20186_(), (double)this.f_26578_.m_123343_()).m_203195_(this.f_26494_.m_20182_(), this.f_26494_.m_20205_())) {
                    this.f_26578_ = null;
                } else {
                    this.f_26494_.m_21566_().m_6849_(this.f_26578_.m_123341_(), this.f_26578_.m_123342_(), this.f_26578_.m_123343_(), this.f_26497_);
                }
            }
            return;
        }
        super.m_7638_();
    }
}


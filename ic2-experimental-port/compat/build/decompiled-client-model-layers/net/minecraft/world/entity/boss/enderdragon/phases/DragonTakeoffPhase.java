/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class DragonTakeoffPhase
extends AbstractDragonPhaseInstance {
    private boolean f_31366_;
    @Nullable
    private Path f_31367_;
    @Nullable
    private Vec3 f_31368_;

    public DragonTakeoffPhase(EnderDragon p_31370_) {
        super(p_31370_);
    }

    @Override
    public void m_6989_() {
        if (this.f_31366_ || this.f_31367_ == null) {
            this.f_31366_ = false;
            this.m_31375_();
        } else {
            BlockPos $$0 = this.f_31176_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.f_65714_);
            if (!$$0.m_203195_(this.f_31176_.m_20182_(), 10.0)) {
                this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31377_);
            }
        }
    }

    @Override
    public void m_7083_() {
        this.f_31366_ = true;
        this.f_31367_ = null;
        this.f_31368_ = null;
    }

    private void m_31375_() {
        int $$0 = this.f_31176_.m_31155_();
        Vec3 $$1 = this.f_31176_.m_31174_(1.0f);
        int $$2 = this.f_31176_.m_31170_(-$$1.f_82479_ * 40.0, 105.0, -$$1.f_82481_ * 40.0);
        if (this.f_31176_.m_31158_() == null || this.f_31176_.m_31158_().m_64098_() <= 0) {
            $$2 -= 12;
            $$2 &= 7;
            $$2 += 12;
        } else if (($$2 %= 12) < 0) {
            $$2 += 12;
        }
        this.f_31367_ = this.f_31176_.m_31104_($$0, $$2, null);
        this.m_31376_();
    }

    private void m_31376_() {
        if (this.f_31367_ != null) {
            this.f_31367_.m_77374_();
            if (!this.f_31367_.m_77392_()) {
                double $$1;
                BlockPos $$0 = this.f_31367_.m_77400_();
                this.f_31367_.m_77374_();
                while (($$1 = (double)((float)$$0.m_123342_() + this.f_31176_.m_217043_().m_188501_() * 20.0f)) < (double)$$0.m_123342_()) {
                }
                this.f_31368_ = new Vec3($$0.m_123341_(), $$1, $$0.m_123343_());
            }
        }
    }

    @Override
    @Nullable
    public Vec3 m_5535_() {
        return this.f_31368_;
    }

    public EnderDragonPhase<DragonTakeoffPhase> m_7309_() {
        return EnderDragonPhase.f_31381_;
    }
}


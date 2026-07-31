/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class DragonLandingApproachPhase
extends AbstractDragonPhaseInstance {
    private static final TargetingConditions f_31253_ = TargetingConditions.m_148352_().m_148355_();
    @Nullable
    private Path f_31254_;
    @Nullable
    private Vec3 f_31255_;

    public DragonLandingApproachPhase(EnderDragon p_31258_) {
        super(p_31258_);
    }

    public EnderDragonPhase<DragonLandingApproachPhase> m_7309_() {
        return EnderDragonPhase.f_31379_;
    }

    @Override
    public void m_7083_() {
        this.f_31254_ = null;
        this.f_31255_ = null;
    }

    @Override
    public void m_6989_() {
        double $$0;
        double d = $$0 = this.f_31255_ == null ? 0.0 : this.f_31255_.m_82531_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
        if ($$0 < 100.0 || $$0 > 22500.0 || this.f_31176_.f_19862_ || this.f_31176_.f_19863_) {
            this.m_31263_();
        }
    }

    @Override
    @Nullable
    public Vec3 m_5535_() {
        return this.f_31255_;
    }

    private void m_31263_() {
        if (this.f_31254_ == null || this.f_31254_.m_77392_()) {
            int $$5;
            int $$0 = this.f_31176_.m_31155_();
            BlockPos $$1 = this.f_31176_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.f_65714_);
            Player $$2 = this.f_31176_.f_19853_.m_45949_(f_31253_, this.f_31176_, $$1.m_123341_(), $$1.m_123342_(), $$1.m_123343_());
            if ($$2 != null) {
                Vec3 $$3 = new Vec3($$2.m_20185_(), 0.0, $$2.m_20189_()).m_82541_();
                int $$4 = this.f_31176_.m_31170_(-$$3.f_82479_ * 40.0, 105.0, -$$3.f_82481_ * 40.0);
            } else {
                $$5 = this.f_31176_.m_31170_(40.0, $$1.m_123342_(), 0.0);
            }
            Node $$6 = new Node($$1.m_123341_(), $$1.m_123342_(), $$1.m_123343_());
            this.f_31254_ = this.f_31176_.m_31104_($$0, $$5, $$6);
            if (this.f_31254_ != null) {
                this.f_31254_.m_77374_();
            }
        }
        this.m_31264_();
        if (this.f_31254_ != null && this.f_31254_.m_77392_()) {
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31380_);
        }
    }

    private void m_31264_() {
        if (this.f_31254_ != null && !this.f_31254_.m_77392_()) {
            double $$3;
            BlockPos $$0 = this.f_31254_.m_77400_();
            this.f_31254_.m_77374_();
            double $$1 = $$0.m_123341_();
            double $$2 = $$0.m_123343_();
            while (($$3 = (double)((float)$$0.m_123342_() + this.f_31176_.m_217043_().m_188501_() * 20.0f)) < (double)$$0.m_123342_()) {
            }
            this.f_31255_ = new Vec3($$1, $$3, $$2);
        }
    }
}


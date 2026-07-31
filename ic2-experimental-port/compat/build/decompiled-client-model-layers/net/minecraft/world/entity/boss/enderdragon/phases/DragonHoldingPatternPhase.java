/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class DragonHoldingPatternPhase
extends AbstractDragonPhaseInstance {
    private static final TargetingConditions f_31224_ = TargetingConditions.m_148352_().m_148355_();
    @Nullable
    private Path f_31225_;
    @Nullable
    private Vec3 f_31226_;
    private boolean f_31227_;

    public DragonHoldingPatternPhase(EnderDragon p_31230_) {
        super(p_31230_);
    }

    public EnderDragonPhase<DragonHoldingPatternPhase> m_7309_() {
        return EnderDragonPhase.f_31377_;
    }

    @Override
    public void m_6989_() {
        double $$0;
        double d = $$0 = this.f_31226_ == null ? 0.0 : this.f_31226_.m_82531_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
        if ($$0 < 100.0 || $$0 > 22500.0 || this.f_31176_.f_19862_ || this.f_31176_.f_19863_) {
            this.m_31242_();
        }
    }

    @Override
    public void m_7083_() {
        this.f_31225_ = null;
        this.f_31226_ = null;
    }

    @Override
    @Nullable
    public Vec3 m_5535_() {
        return this.f_31226_;
    }

    private void m_31242_() {
        if (this.f_31225_ != null && this.f_31225_.m_77392_()) {
            int $$1;
            BlockPos $$0 = this.f_31176_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPodiumFeature.f_65714_));
            int n = $$1 = this.f_31176_.m_31158_() == null ? 0 : this.f_31176_.m_31158_().m_64098_();
            if (this.f_31176_.m_217043_().m_188503_($$1 + 3) == 0) {
                this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31379_);
                return;
            }
            double $$2 = 64.0;
            Player $$3 = this.f_31176_.f_19853_.m_45949_(f_31224_, this.f_31176_, $$0.m_123341_(), $$0.m_123342_(), $$0.m_123343_());
            if ($$3 != null) {
                $$2 = $$0.m_203193_($$3.m_20182_()) / 512.0;
            }
            if ($$3 != null && (this.f_31176_.m_217043_().m_188503_(Mth.m_14040_((int)$$2) + 2) == 0 || this.f_31176_.m_217043_().m_188503_($$1 + 2) == 0)) {
                this.m_31236_($$3);
                return;
            }
        }
        if (this.f_31225_ == null || this.f_31225_.m_77392_()) {
            int $$4;
            int $$5 = $$4 = this.f_31176_.m_31155_();
            if (this.f_31176_.m_217043_().m_188503_(8) == 0) {
                this.f_31227_ = !this.f_31227_;
                $$5 += 6;
            }
            $$5 = this.f_31227_ ? ++$$5 : --$$5;
            if (this.f_31176_.m_31158_() == null || this.f_31176_.m_31158_().m_64098_() < 0) {
                $$5 -= 12;
                $$5 &= 7;
                $$5 += 12;
            } else if (($$5 %= 12) < 0) {
                $$5 += 12;
            }
            this.f_31225_ = this.f_31176_.m_31104_($$4, $$5, null);
            if (this.f_31225_ != null) {
                this.f_31225_.m_77374_();
            }
        }
        this.m_31243_();
    }

    private void m_31236_(Player p_31237_) {
        this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31378_);
        this.f_31176_.m_31157_().m_31418_(EnderDragonPhase.f_31378_).m_31358_(p_31237_);
    }

    private void m_31243_() {
        if (this.f_31225_ != null && !this.f_31225_.m_77392_()) {
            double $$3;
            BlockPos $$0 = this.f_31225_.m_77400_();
            this.f_31225_.m_77374_();
            double $$1 = $$0.m_123341_();
            double $$2 = $$0.m_123343_();
            while (($$3 = (double)((float)$$0.m_123342_() + this.f_31176_.m_217043_().m_188501_() * 20.0f)) < (double)$$0.m_123342_()) {
            }
            this.f_31226_ = new Vec3($$1, $$3, $$2);
        }
    }

    @Override
    public void m_8059_(EndCrystal p_31232_, BlockPos p_31233_, DamageSource p_31234_, @Nullable Player p_31235_) {
        if (p_31235_ != null && this.f_31176_.m_6779_(p_31235_)) {
            this.m_31236_(p_31235_);
        }
    }
}


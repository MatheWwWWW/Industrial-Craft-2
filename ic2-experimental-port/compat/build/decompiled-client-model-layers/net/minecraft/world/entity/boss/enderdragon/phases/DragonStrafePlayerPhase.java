/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class DragonStrafePlayerPhase
extends AbstractDragonPhaseInstance {
    private static final Logger f_31349_ = LogUtils.getLogger();
    private static final int f_149586_ = 5;
    private int f_31350_;
    @Nullable
    private Path f_31351_;
    @Nullable
    private Vec3 f_31352_;
    @Nullable
    private LivingEntity f_31353_;
    private boolean f_31354_;

    public DragonStrafePlayerPhase(EnderDragon p_31357_) {
        super(p_31357_);
    }

    @Override
    public void m_6989_() {
        double $$6;
        if (this.f_31353_ == null) {
            f_31349_.warn("Skipping player strafe phase because no player was found");
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31377_);
            return;
        }
        if (this.f_31351_ != null && this.f_31351_.m_77392_()) {
            double $$0 = this.f_31353_.m_20185_();
            double $$1 = this.f_31353_.m_20189_();
            double $$2 = $$0 - this.f_31176_.m_20185_();
            double $$3 = $$1 - this.f_31176_.m_20189_();
            double $$4 = Math.sqrt($$2 * $$2 + $$3 * $$3);
            double $$5 = Math.min((double)0.4f + $$4 / 80.0 - 1.0, 10.0);
            this.f_31352_ = new Vec3($$0, this.f_31353_.m_20186_() + $$5, $$1);
        }
        double d = $$6 = this.f_31352_ == null ? 0.0 : this.f_31352_.m_82531_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
        if ($$6 < 100.0 || $$6 > 22500.0) {
            this.m_31364_();
        }
        double $$7 = 64.0;
        if (this.f_31353_.m_20280_(this.f_31176_) < 4096.0) {
            if (this.f_31176_.m_142582_(this.f_31353_)) {
                ++this.f_31350_;
                Vec3 $$8 = new Vec3(this.f_31353_.m_20185_() - this.f_31176_.m_20185_(), 0.0, this.f_31353_.m_20189_() - this.f_31176_.m_20189_()).m_82541_();
                Vec3 $$9 = new Vec3(Mth.m_14031_(this.f_31176_.m_146908_() * ((float)Math.PI / 180)), 0.0, -Mth.m_14089_(this.f_31176_.m_146908_() * ((float)Math.PI / 180))).m_82541_();
                float $$10 = (float)$$9.m_82526_($$8);
                float $$11 = (float)(Math.acos($$10) * 57.2957763671875);
                $$11 += 0.5f;
                if (this.f_31350_ >= 5 && $$11 >= 0.0f && $$11 < 10.0f) {
                    double $$12 = 1.0;
                    Vec3 $$13 = this.f_31176_.m_20252_(1.0f);
                    double $$14 = this.f_31176_.f_31080_.m_20185_() - $$13.f_82479_ * 1.0;
                    double $$15 = this.f_31176_.f_31080_.m_20227_(0.5) + 0.5;
                    double $$16 = this.f_31176_.f_31080_.m_20189_() - $$13.f_82481_ * 1.0;
                    double $$17 = this.f_31353_.m_20185_() - $$14;
                    double $$18 = this.f_31353_.m_20227_(0.5) - $$15;
                    double $$19 = this.f_31353_.m_20189_() - $$16;
                    if (!this.f_31176_.m_20067_()) {
                        this.f_31176_.f_19853_.m_5898_(null, 1017, this.f_31176_.m_20183_(), 0);
                    }
                    DragonFireball $$20 = new DragonFireball(this.f_31176_.f_19853_, this.f_31176_, $$17, $$18, $$19);
                    $$20.m_7678_($$14, $$15, $$16, 0.0f, 0.0f);
                    this.f_31176_.f_19853_.m_7967_($$20);
                    this.f_31350_ = 0;
                    if (this.f_31351_ != null) {
                        while (!this.f_31351_.m_77392_()) {
                            this.f_31351_.m_77374_();
                        }
                    }
                    this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31377_);
                }
            } else if (this.f_31350_ > 0) {
                --this.f_31350_;
            }
        } else if (this.f_31350_ > 0) {
            --this.f_31350_;
        }
    }

    private void m_31364_() {
        if (this.f_31351_ == null || this.f_31351_.m_77392_()) {
            int $$0;
            int $$1 = $$0 = this.f_31176_.m_31155_();
            if (this.f_31176_.m_217043_().m_188503_(8) == 0) {
                this.f_31354_ = !this.f_31354_;
                $$1 += 6;
            }
            $$1 = this.f_31354_ ? ++$$1 : --$$1;
            if (this.f_31176_.m_31158_() == null || this.f_31176_.m_31158_().m_64098_() <= 0) {
                $$1 -= 12;
                $$1 &= 7;
                $$1 += 12;
            } else if (($$1 %= 12) < 0) {
                $$1 += 12;
            }
            this.f_31351_ = this.f_31176_.m_31104_($$0, $$1, null);
            if (this.f_31351_ != null) {
                this.f_31351_.m_77374_();
            }
        }
        this.m_31365_();
    }

    private void m_31365_() {
        if (this.f_31351_ != null && !this.f_31351_.m_77392_()) {
            double $$3;
            BlockPos $$0 = this.f_31351_.m_77400_();
            this.f_31351_.m_77374_();
            double $$1 = $$0.m_123341_();
            double $$2 = $$0.m_123343_();
            while (($$3 = (double)((float)$$0.m_123342_() + this.f_31176_.m_217043_().m_188501_() * 20.0f)) < (double)$$0.m_123342_()) {
            }
            this.f_31352_ = new Vec3($$1, $$3, $$2);
        }
    }

    @Override
    public void m_7083_() {
        this.f_31350_ = 0;
        this.f_31352_ = null;
        this.f_31351_ = null;
        this.f_31353_ = null;
    }

    public void m_31358_(LivingEntity p_31359_) {
        this.f_31353_ = p_31359_;
        int $$1 = this.f_31176_.m_31155_();
        int $$2 = this.f_31176_.m_31170_(this.f_31353_.m_20185_(), this.f_31353_.m_20186_(), this.f_31353_.m_20189_());
        int $$3 = this.f_31353_.m_146903_();
        int $$4 = this.f_31353_.m_146907_();
        double $$5 = (double)$$3 - this.f_31176_.m_20185_();
        double $$6 = (double)$$4 - this.f_31176_.m_20189_();
        double $$7 = Math.sqrt($$5 * $$5 + $$6 * $$6);
        double $$8 = Math.min((double)0.4f + $$7 / 80.0 - 1.0, 10.0);
        int $$9 = Mth.m_14107_(this.f_31353_.m_20186_() + $$8);
        Node $$10 = new Node($$3, $$9, $$4);
        this.f_31351_ = this.f_31176_.m_31104_($$1, $$2, $$10);
        if (this.f_31351_ != null) {
            this.f_31351_.m_77374_();
            this.m_31365_();
        }
    }

    @Override
    @Nullable
    public Vec3 m_5535_() {
        return this.f_31352_;
    }

    public EnderDragonPhase<DragonStrafePlayerPhase> m_7309_() {
        return EnderDragonPhase.f_31378_;
    }
}


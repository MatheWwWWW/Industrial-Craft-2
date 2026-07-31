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
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class DragonChargePlayerPhase
extends AbstractDragonPhaseInstance {
    private static final Logger f_31201_ = LogUtils.getLogger();
    private static final int f_149577_ = 10;
    @Nullable
    private Vec3 f_31202_;
    private int f_31203_;

    public DragonChargePlayerPhase(EnderDragon p_31206_) {
        super(p_31206_);
    }

    @Override
    public void m_6989_() {
        if (this.f_31202_ == null) {
            f_31201_.warn("Aborting charge player as no target was set.");
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31377_);
            return;
        }
        if (this.f_31203_ > 0 && this.f_31203_++ >= 10) {
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31377_);
            return;
        }
        double $$0 = this.f_31202_.m_82531_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
        if ($$0 < 100.0 || $$0 > 22500.0 || this.f_31176_.f_19862_ || this.f_31176_.f_19863_) {
            ++this.f_31203_;
        }
    }

    @Override
    public void m_7083_() {
        this.f_31202_ = null;
        this.f_31203_ = 0;
    }

    public void m_31207_(Vec3 p_31208_) {
        this.f_31202_ = p_31208_;
    }

    @Override
    public float m_7072_() {
        return 3.0f;
    }

    @Override
    @Nullable
    public Vec3 m_5535_() {
        return this.f_31202_;
    }

    public EnderDragonPhase<DragonChargePlayerPhase> m_7309_() {
        return EnderDragonPhase.f_31385_;
    }
}


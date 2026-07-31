/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FleeSunGoal
extends Goal {
    protected final PathfinderMob f_25214_;
    private double f_25215_;
    private double f_25216_;
    private double f_25217_;
    private final double f_25218_;
    private final Level f_25219_;

    public FleeSunGoal(PathfinderMob p_25221_, double p_25222_) {
        this.f_25214_ = p_25221_;
        this.f_25218_ = p_25222_;
        this.f_25219_ = p_25221_.f_19853_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8036_() {
        if (this.f_25214_.m_5448_() != null) {
            return false;
        }
        if (!this.f_25219_.m_46461_()) {
            return false;
        }
        if (!this.f_25214_.m_6060_()) {
            return false;
        }
        if (!this.f_25219_.m_45527_(this.f_25214_.m_20183_())) {
            return false;
        }
        if (!this.f_25214_.m_6844_(EquipmentSlot.HEAD).m_41619_()) {
            return false;
        }
        return this.m_25226_();
    }

    protected boolean m_25226_() {
        Vec3 $$0 = this.m_25227_();
        if ($$0 == null) {
            return false;
        }
        this.f_25215_ = $$0.f_82479_;
        this.f_25216_ = $$0.f_82480_;
        this.f_25217_ = $$0.f_82481_;
        return true;
    }

    @Override
    public boolean m_8045_() {
        return !this.f_25214_.m_21573_().m_26571_();
    }

    @Override
    public void m_8056_() {
        this.f_25214_.m_21573_().m_26519_(this.f_25215_, this.f_25216_, this.f_25217_, this.f_25218_);
    }

    @Nullable
    protected Vec3 m_25227_() {
        RandomSource $$0 = this.f_25214_.m_217043_();
        BlockPos $$1 = this.f_25214_.m_20183_();
        for (int $$2 = 0; $$2 < 10; ++$$2) {
            BlockPos $$3 = $$1.m_7918_($$0.m_188503_(20) - 10, $$0.m_188503_(6) - 3, $$0.m_188503_(20) - 10);
            if (this.f_25219_.m_45527_($$3) || !(this.f_25214_.m_21692_($$3) < 0.0f)) continue;
            return Vec3.m_82539_($$3);
        }
        return null;
    }
}


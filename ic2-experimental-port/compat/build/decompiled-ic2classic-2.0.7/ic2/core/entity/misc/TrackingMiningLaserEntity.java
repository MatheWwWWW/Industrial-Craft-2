/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.entity.misc;

import ic2.core.IC2;
import ic2.core.entity.misc.MiningLaserEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TrackingMiningLaserEntity
extends MiningLaserEntity {
    Entity tracked;

    public TrackingMiningLaserEntity(EntityType<?> type, Level worldIn) {
        super(type, worldIn);
    }

    public TrackingMiningLaserEntity(EntityType<?> type, Level world, LivingEntity entityLiving, float range, float power, int blockBreaks, Entity toReach) {
        super(type, world, entityLiving, range, power, blockBreaks, false);
        this.tracked = toReach;
    }

    @Override
    public void m_8119_() {
        boolean simulating = IC2.PLATFORM.isSimulating();
        if (simulating) {
            if (this.tracked == null) {
                if (this.explosive) {
                    this.explode();
                }
                this.m_142687_(Entity.RemovalReason.DISCARDED);
                return;
            }
            Vec3 targetPosition = this.getTargetVector();
            double d0 = targetPosition.f_82479_ - this.m_20185_();
            double d1 = targetPosition.f_82480_ - this.m_20186_();
            double d2 = targetPosition.f_82481_ - this.m_20189_();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            float f = (float)(Mth.m_14136_((double)d2, (double)d0) * 57.29577951308232) - 90.0f;
            float f1 = (float)(-(Mth.m_14136_((double)d1, (double)d3) * 57.29577951308232));
            this.m_146926_(this.updateRotation(this.m_146909_(), f1));
            this.m_146922_(this.updateRotation(this.m_146908_(), f));
            double yaw = Math.toRadians(this.m_146908_());
            double pitch = Math.toRadians(this.m_146909_());
            double startMotionX = -Math.sin(yaw) * Math.cos(pitch);
            double startMotionY = -Math.sin(pitch);
            double startMotionZ = Math.cos(yaw) * Math.cos(pitch);
            this.setLaserHeading(startMotionX, startMotionY, startMotionZ, 1.0);
        }
        super.m_8119_();
    }

    protected float updateRotation(float from, float to) {
        float f = Mth.m_14177_((float)(to - from));
        if (f > 360.0f) {
            f = 360.0f;
        }
        if (f < -360.0f) {
            f = -360.0f;
        }
        return from + f;
    }

    public Vec3 getTargetVector() {
        AABB box = this.tracked.m_20191_();
        return (box == null ? new Vec3(0.0, 0.0, 0.0) : new Vec3((box.f_82291_ - box.f_82288_) / 2.0, (box.f_82292_ - box.f_82289_) / 2.0, (box.f_82293_ - box.f_82290_) / 2.0)).m_82549_(this.tracked.m_20182_());
    }

    @Override
    protected void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (this.f_19853_ instanceof ServerLevel) {
            this.tracked = ((ServerLevel)this.f_19853_).m_8791_(compound.m_128342_("target"));
        }
    }

    @Override
    protected void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128362_("target", this.tracked.m_20148_());
    }
}


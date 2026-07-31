/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.behavior;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.phys.Vec3;

public class EntityTracker
implements PositionTracker {
    private final Entity f_22846_;
    private final boolean f_22847_;

    public EntityTracker(Entity p_22849_, boolean p_22850_) {
        this.f_22846_ = p_22849_;
        this.f_22847_ = p_22850_;
    }

    @Override
    public Vec3 m_7024_() {
        return this.f_22847_ ? this.f_22846_.m_20182_().m_82520_(0.0, this.f_22846_.m_20192_(), 0.0) : this.f_22846_.m_20182_();
    }

    @Override
    public BlockPos m_6675_() {
        return this.f_22846_.m_20183_();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean m_6826_(LivingEntity p_22853_) {
        void $$2;
        Entity entity = this.f_22846_;
        if (!(entity instanceof LivingEntity)) {
            return true;
        }
        LivingEntity $$1 = (LivingEntity)entity;
        if (!$$2.m_6084_()) {
            return false;
        }
        Optional<NearestVisibleLivingEntities> $$3 = p_22853_.m_6274_().m_21952_(MemoryModuleType.f_148205_);
        return $$3.isPresent() && $$3.get().m_186107_((LivingEntity)$$2);
    }

    public Entity m_147481_() {
        return this.f_22846_;
    }

    public String toString() {
        return "EntityTracker for " + this.f_22846_;
    }
}


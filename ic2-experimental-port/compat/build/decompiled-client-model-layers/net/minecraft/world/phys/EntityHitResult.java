/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.phys;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityHitResult
extends HitResult {
    private final Entity f_82437_;

    public EntityHitResult(Entity p_82439_) {
        this(p_82439_, p_82439_.m_20182_());
    }

    public EntityHitResult(Entity p_82441_, Vec3 p_82442_) {
        super(p_82442_);
        this.f_82437_ = p_82441_;
    }

    public Entity m_82443_() {
        return this.f_82437_;
    }

    @Override
    public HitResult.Type m_6662_() {
        return HitResult.Type.ENTITY;
    }
}


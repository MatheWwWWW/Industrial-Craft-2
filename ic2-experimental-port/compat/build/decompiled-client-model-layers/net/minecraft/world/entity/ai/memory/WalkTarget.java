/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.memory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.phys.Vec3;

public class WalkTarget {
    private final PositionTracker f_26405_;
    private final float f_26406_;
    private final int f_26407_;

    public WalkTarget(BlockPos p_26417_, float p_26418_, int p_26419_) {
        this(new BlockPosTracker(p_26417_), p_26418_, p_26419_);
    }

    public WalkTarget(Vec3 p_26413_, float p_26414_, int p_26415_) {
        this(new BlockPosTracker(new BlockPos(p_26413_)), p_26414_, p_26415_);
    }

    public WalkTarget(Entity p_148209_, float p_148210_, int p_148211_) {
        this(new EntityTracker(p_148209_, false), p_148210_, p_148211_);
    }

    public WalkTarget(PositionTracker p_26409_, float p_26410_, int p_26411_) {
        this.f_26405_ = p_26409_;
        this.f_26406_ = p_26410_;
        this.f_26407_ = p_26411_;
    }

    public PositionTracker m_26420_() {
        return this.f_26405_;
    }

    public float m_26421_() {
        return this.f_26406_;
    }

    public int m_26422_() {
        return this.f_26407_;
    }
}


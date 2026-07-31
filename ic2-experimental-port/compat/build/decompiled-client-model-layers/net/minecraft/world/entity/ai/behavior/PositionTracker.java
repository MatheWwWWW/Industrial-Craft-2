/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public interface PositionTracker {
    public Vec3 m_7024_();

    public BlockPos m_6675_();

    public boolean m_6826_(LivingEntity var1);
}


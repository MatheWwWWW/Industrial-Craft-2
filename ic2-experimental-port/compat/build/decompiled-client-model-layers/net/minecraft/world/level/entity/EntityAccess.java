/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.entity;

import java.util.UUID;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import net.minecraft.world.phys.AABB;

public interface EntityAccess {
    public int m_19879_();

    public UUID m_20148_();

    public BlockPos m_20183_();

    public AABB m_20191_();

    public void m_141960_(EntityInLevelCallback var1);

    public Stream<? extends EntityAccess> m_20199_();

    public Stream<? extends EntityAccess> m_142429_();

    public void m_142467_(Entity.RemovalReason var1);

    public boolean m_142391_();

    public boolean m_142389_();
}


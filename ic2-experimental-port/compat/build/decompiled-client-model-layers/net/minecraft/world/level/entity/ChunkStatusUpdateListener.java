/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.entity;

import net.minecraft.server.level.ChunkHolder;
import net.minecraft.world.level.ChunkPos;

@FunctionalInterface
public interface ChunkStatusUpdateListener {
    public void m_156794_(ChunkPos var1, ChunkHolder.FullChunkStatus var2);
}


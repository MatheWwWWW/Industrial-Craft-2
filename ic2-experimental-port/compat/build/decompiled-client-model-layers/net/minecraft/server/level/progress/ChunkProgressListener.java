/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.server.level.progress;

import javax.annotation.Nullable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;

public interface ChunkProgressListener {
    public void m_7647_(ChunkPos var1);

    public void m_5511_(ChunkPos var1, @Nullable ChunkStatus var2);

    public void m_9662_();

    public void m_7646_();
}


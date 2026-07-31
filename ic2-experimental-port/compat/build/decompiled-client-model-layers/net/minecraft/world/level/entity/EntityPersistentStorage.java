/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.entity;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.entity.ChunkEntities;

public interface EntityPersistentStorage<T>
extends AutoCloseable {
    public CompletableFuture<ChunkEntities<T>> m_141930_(ChunkPos var1);

    public void m_141971_(ChunkEntities<T> var1);

    public void m_182219_(boolean var1);

    @Override
    default public void close() throws IOException {
    }
}


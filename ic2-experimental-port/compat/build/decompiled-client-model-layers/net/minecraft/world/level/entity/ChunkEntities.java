/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.entity;

import java.util.List;
import java.util.stream.Stream;
import net.minecraft.world.level.ChunkPos;

public class ChunkEntities<T> {
    private final ChunkPos f_156786_;
    private final List<T> f_156787_;

    public ChunkEntities(ChunkPos p_156789_, List<T> p_156790_) {
        this.f_156786_ = p_156789_;
        this.f_156787_ = p_156790_;
    }

    public ChunkPos m_156791_() {
        return this.f_156786_;
    }

    public Stream<T> m_156792_() {
        return this.f_156787_.stream();
    }

    public boolean m_156793_() {
        return this.f_156787_.isEmpty();
    }
}


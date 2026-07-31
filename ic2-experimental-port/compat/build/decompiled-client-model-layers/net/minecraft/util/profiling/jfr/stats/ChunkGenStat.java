/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.jfr.stats;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import jdk.jfr.consumer.RecordedEvent;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.util.profiling.jfr.stats.TimedStat;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;

public final class ChunkGenStat
extends Record
implements TimedStat {
    private final Duration f_185592_;
    private final ChunkPos f_185593_;
    private final ColumnPos f_185594_;
    private final ChunkStatus f_185595_;
    private final String f_185596_;

    public ChunkGenStat(Duration f_185592_, ChunkPos f_185593_, ColumnPos f_185594_, ChunkStatus f_185595_, String f_185596_) {
        this.f_185592_ = f_185592_;
        this.f_185593_ = f_185593_;
        this.f_185594_ = f_185594_;
        this.f_185595_ = f_185595_;
        this.f_185596_ = f_185596_;
    }

    public static ChunkGenStat m_185604_(RecordedEvent p_185605_) {
        return new ChunkGenStat(p_185605_.getDuration(), new ChunkPos(p_185605_.getInt("chunkPosX"), p_185605_.getInt("chunkPosX")), new ColumnPos(p_185605_.getInt("worldPosX"), p_185605_.getInt("worldPosZ")), ChunkStatus.m_62397_(p_185605_.getString("status")), p_185605_.getString("level"));
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ChunkGenStat.class, "duration;chunkPos;worldPos;status;level", "f_185592_", "f_185593_", "f_185594_", "f_185595_", "f_185596_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ChunkGenStat.class, "duration;chunkPos;worldPos;status;level", "f_185592_", "f_185593_", "f_185594_", "f_185595_", "f_185596_"}, this);
    }

    @Override
    public final boolean equals(Object p_185611_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ChunkGenStat.class, "duration;chunkPos;worldPos;status;level", "f_185592_", "f_185593_", "f_185594_", "f_185595_", "f_185596_"}, this, p_185611_);
    }

    @Override
    public Duration m_183571_() {
        return this.f_185592_;
    }

    public ChunkPos f_185593_() {
        return this.f_185593_;
    }

    public ColumnPos f_185594_() {
        return this.f_185594_;
    }

    public ChunkStatus f_185595_() {
        return this.f_185595_;
    }

    public String f_185596_() {
        return this.f_185596_;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.server.level.progress;

import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;

public class ProcessorChunkProgressListener
implements ChunkProgressListener {
    private final ChunkProgressListener f_9637_;
    private final ProcessorMailbox<Runnable> f_9638_;

    private ProcessorChunkProgressListener(ChunkProgressListener p_9640_, Executor p_9641_) {
        this.f_9637_ = p_9640_;
        this.f_9638_ = ProcessorMailbox.m_18751_(p_9641_, "progressListener");
    }

    public static ProcessorChunkProgressListener m_143583_(ChunkProgressListener p_143584_, Executor p_143585_) {
        ProcessorChunkProgressListener $$2 = new ProcessorChunkProgressListener(p_143584_, p_143585_);
        $$2.m_9662_();
        return $$2;
    }

    @Override
    public void m_7647_(ChunkPos p_9643_) {
        this.f_9638_.m_6937_(() -> this.f_9637_.m_7647_(p_9643_));
    }

    @Override
    public void m_5511_(ChunkPos p_9645_, @Nullable ChunkStatus p_9646_) {
        this.f_9638_.m_6937_(() -> this.f_9637_.m_5511_(p_9645_, p_9646_));
    }

    @Override
    public void m_9662_() {
        this.f_9638_.m_6937_(this.f_9637_::m_9662_);
    }

    @Override
    public void m_7646_() {
        this.f_9638_.m_6937_(this.f_9637_::m_7646_);
    }
}


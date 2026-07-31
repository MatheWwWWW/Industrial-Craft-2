/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.server.level.progress;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.level.progress.LoggerChunkProgressListener;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;

public class StoringChunkProgressListener
implements ChunkProgressListener {
    private final LoggerChunkProgressListener f_9653_;
    private final Long2ObjectOpenHashMap<ChunkStatus> f_9654_;
    private ChunkPos f_9655_ = new ChunkPos(0, 0);
    private final int f_9656_;
    private final int f_9657_;
    private final int f_9658_;
    private boolean f_9659_;

    public StoringChunkProgressListener(int p_9661_) {
        this.f_9653_ = new LoggerChunkProgressListener(p_9661_);
        this.f_9656_ = p_9661_ * 2 + 1;
        this.f_9657_ = p_9661_ + ChunkStatus.m_62421_();
        this.f_9658_ = this.f_9657_ * 2 + 1;
        this.f_9654_ = new Long2ObjectOpenHashMap();
    }

    @Override
    public void m_7647_(ChunkPos p_9667_) {
        if (!this.f_9659_) {
            return;
        }
        this.f_9653_.m_7647_(p_9667_);
        this.f_9655_ = p_9667_;
    }

    @Override
    public void m_5511_(ChunkPos p_9669_, @Nullable ChunkStatus p_9670_) {
        if (!this.f_9659_) {
            return;
        }
        this.f_9653_.m_5511_(p_9669_, p_9670_);
        if (p_9670_ == null) {
            this.f_9654_.remove(p_9669_.m_45588_());
        } else {
            this.f_9654_.put(p_9669_.m_45588_(), (Object)p_9670_);
        }
    }

    @Override
    public void m_9662_() {
        this.f_9659_ = true;
        this.f_9654_.clear();
        this.f_9653_.m_9662_();
    }

    @Override
    public void m_7646_() {
        this.f_9659_ = false;
        this.f_9653_.m_7646_();
    }

    public int m_9672_() {
        return this.f_9656_;
    }

    public int m_9673_() {
        return this.f_9658_;
    }

    public int m_9674_() {
        return this.f_9653_.m_9636_();
    }

    @Nullable
    public ChunkStatus m_9663_(int p_9664_, int p_9665_) {
        return (ChunkStatus)this.f_9654_.get(ChunkPos.m_45589_(p_9664_ + this.f_9655_.f_45578_ - this.f_9657_, p_9665_ + this.f_9655_.f_45579_ - this.f_9657_));
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package com.mojang.blaze3d.pipeline;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.pipeline.RenderCall;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderPipeline {
    private final List<ConcurrentLinkedQueue<RenderCall>> f_83910_ = ImmutableList.of(new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue());
    private volatile boolean f_166180_;
    private volatile int f_83911_ = this.f_83912_ = this.f_83913_ + 1;
    private volatile boolean f_166181_;
    private volatile int f_83912_;
    private volatile int f_83913_;

    public boolean m_166182_() {
        return !this.f_166180_ && this.f_83911_ == this.f_83912_;
    }

    public boolean m_166185_() {
        if (this.f_166180_) {
            throw new RuntimeException("ALREADY RECORDING !!!");
        }
        if (this.m_166182_()) {
            this.f_83911_ = (this.f_83912_ + 1) % this.f_83910_.size();
            this.f_166180_ = true;
            return true;
        }
        return false;
    }

    public void m_166183_(RenderCall p_166184_) {
        if (!this.f_166180_) {
            throw new RuntimeException("NOT RECORDING !!!");
        }
        ConcurrentLinkedQueue<RenderCall> $$1 = this.m_166192_();
        $$1.add(p_166184_);
    }

    public void m_166186_() {
        if (!this.f_166180_) {
            throw new RuntimeException("NOT RECORDING !!!");
        }
        this.f_166180_ = false;
    }

    public boolean m_166187_() {
        return !this.f_166181_ && this.f_83911_ != this.f_83912_;
    }

    public boolean m_166188_() {
        if (this.f_166181_) {
            throw new RuntimeException("ALREADY PROCESSING !!!");
        }
        if (this.m_166187_()) {
            this.f_166181_ = true;
            return true;
        }
        return false;
    }

    public void m_166189_() {
        if (!this.f_166181_) {
            throw new RuntimeException("NOT PROCESSING !!!");
        }
    }

    public void m_166190_() {
        if (!this.f_166181_) {
            throw new RuntimeException("NOT PROCESSING !!!");
        }
        this.f_166181_ = false;
        this.f_83913_ = this.f_83912_;
        this.f_83912_ = this.f_83911_;
    }

    public ConcurrentLinkedQueue<RenderCall> m_166191_() {
        return this.f_83910_.get(this.f_83913_);
    }

    public ConcurrentLinkedQueue<RenderCall> m_166192_() {
        return this.f_83910_.get(this.f_83911_);
    }

    public ConcurrentLinkedQueue<RenderCall> m_166193_() {
        return this.f_83910_.get(this.f_83912_);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 */
package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class DebugBuffer<T> {
    private final AtomicReferenceArray<T> f_144620_;
    private final AtomicInteger f_144621_;

    public DebugBuffer(int p_144623_) {
        this.f_144620_ = new AtomicReferenceArray(p_144623_);
        this.f_144621_ = new AtomicInteger(0);
    }

    public void m_144625_(T p_144626_) {
        int $$3;
        int $$2;
        int $$1 = this.f_144620_.length();
        while (!this.f_144621_.compareAndSet($$2 = this.f_144621_.get(), $$3 = ($$2 + 1) % $$1)) {
        }
        this.f_144620_.set($$3, p_144626_);
    }

    public List<T> m_144624_() {
        int $$0 = this.f_144621_.get();
        ImmutableList.Builder $$1 = ImmutableList.builder();
        for (int $$2 = 0; $$2 < this.f_144620_.length(); ++$$2) {
            int $$3 = Math.floorMod($$0 - $$2, this.f_144620_.length());
            T $$4 = this.f_144620_.get($$3);
            if ($$4 == null) continue;
            $$1.add($$4);
        }
        return $$1.build();
    }
}


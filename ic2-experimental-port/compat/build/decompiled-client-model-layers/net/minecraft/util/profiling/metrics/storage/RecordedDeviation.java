/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.metrics.storage;

import java.time.Instant;
import net.minecraft.util.profiling.ProfileResults;

public final class RecordedDeviation {
    public final Instant f_146254_;
    public final int f_146255_;
    public final ProfileResults f_146256_;

    public RecordedDeviation(Instant p_146258_, int p_146259_, ProfileResults p_146260_) {
        this.f_146254_ = p_146258_;
        this.f_146255_ = p_146259_;
        this.f_146256_ = p_146260_;
    }
}


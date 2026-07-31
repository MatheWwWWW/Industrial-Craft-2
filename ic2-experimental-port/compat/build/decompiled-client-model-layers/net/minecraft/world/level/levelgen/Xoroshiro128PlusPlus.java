/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import net.minecraft.world.level.levelgen.RandomSupport;

public class Xoroshiro128PlusPlus {
    private long f_190089_;
    private long f_190090_;

    public Xoroshiro128PlusPlus(RandomSupport.Seed128bit p_190095_) {
        this(p_190095_.f_189335_(), p_190095_.f_189336_());
    }

    public Xoroshiro128PlusPlus(long p_190092_, long p_190093_) {
        this.f_190089_ = p_190092_;
        this.f_190090_ = p_190093_;
        if ((this.f_190089_ | this.f_190090_) == 0L) {
            this.f_190089_ = -7046029254386353131L;
            this.f_190090_ = 7640891576956012809L;
        }
    }

    public long m_190096_() {
        long $$0 = this.f_190089_;
        long $$1 = this.f_190090_;
        long $$2 = Long.rotateLeft($$0 + $$1, 17) + $$0;
        this.f_190089_ = Long.rotateLeft($$0, 49) ^ ($$1 ^= $$0) ^ $$1 << 21;
        this.f_190090_ = Long.rotateLeft($$1, 28);
        return $$2;
    }
}


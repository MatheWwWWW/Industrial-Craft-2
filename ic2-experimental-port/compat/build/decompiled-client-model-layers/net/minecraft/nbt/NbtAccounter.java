/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt;

public class NbtAccounter {
    public static final NbtAccounter f_128917_ = new NbtAccounter(0L){

        @Override
        public void m_6800_(long p_128927_) {
        }
    };
    private final long f_128918_;
    private long f_128919_;

    public NbtAccounter(long p_128922_) {
        this.f_128918_ = p_128922_;
    }

    public void m_6800_(long p_128923_) {
        this.f_128919_ += p_128923_ / 8L;
        if (this.f_128919_ > this.f_128918_) {
            throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.f_128919_ + "bytes where max allowed: " + this.f_128918_);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.chunk;

import java.util.BitSet;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

public class CarvingMask {
    private final int f_187576_;
    private final BitSet f_187577_;
    private Mask f_196706_ = (p_196713_, p_196714_, p_196715_) -> false;

    public CarvingMask(int p_187579_, int p_187580_) {
        this.f_187576_ = p_187580_;
        this.f_187577_ = new BitSet(256 * p_187579_);
    }

    public void m_196710_(Mask p_196711_) {
        this.f_196706_ = p_196711_;
    }

    public CarvingMask(long[] p_187582_, int p_187583_) {
        this.f_187576_ = p_187583_;
        this.f_187577_ = BitSet.valueOf(p_187582_);
    }

    private int m_187598_(int p_187599_, int p_187600_, int p_187601_) {
        return p_187599_ & 0xF | (p_187601_ & 0xF) << 4 | p_187600_ - this.f_187576_ << 8;
    }

    public void m_187585_(int p_187586_, int p_187587_, int p_187588_) {
        this.f_187577_.set(this.m_187598_(p_187586_, p_187587_, p_187588_));
    }

    public boolean m_187594_(int p_187595_, int p_187596_, int p_187597_) {
        return this.f_196706_.m_196716_(p_187595_, p_187596_, p_187597_) || this.f_187577_.get(this.m_187598_(p_187595_, p_187596_, p_187597_));
    }

    public Stream<BlockPos> m_187589_(ChunkPos p_187590_) {
        return this.f_187577_.stream().mapToObj(p_196709_ -> {
            int $$2 = p_196709_ & 0xF;
            int $$3 = p_196709_ >> 4 & 0xF;
            int $$4 = p_196709_ >> 8;
            return p_187590_.m_151384_($$2, $$4 + this.f_187576_, $$3);
        });
    }

    public long[] m_187584_() {
        return this.f_187577_.toLongArray();
    }

    public static interface Mask {
        public boolean m_196716_(int var1, int var2, int var3);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;

public class ChunkPos {
    private static final int f_199440_ = 1056;
    public static final long f_45577_ = ChunkPos.m_45589_(1875066, 1875066);
    public static final ChunkPos f_186419_ = new ChunkPos(0, 0);
    private static final long f_151375_ = 32L;
    private static final long f_151376_ = 0xFFFFFFFFL;
    private static final int f_151377_ = 5;
    public static final int f_220335_ = 32;
    private static final int f_151378_ = 31;
    public static final int f_220336_ = 31;
    public final int f_45578_;
    public final int f_45579_;
    private static final int f_151379_ = 1664525;
    private static final int f_151380_ = 1013904223;
    private static final int f_151381_ = -559038737;

    public ChunkPos(int p_45582_, int p_45583_) {
        this.f_45578_ = p_45582_;
        this.f_45579_ = p_45583_;
    }

    public ChunkPos(BlockPos p_45587_) {
        this.f_45578_ = SectionPos.m_123171_(p_45587_.m_123341_());
        this.f_45579_ = SectionPos.m_123171_(p_45587_.m_123343_());
    }

    public ChunkPos(long p_45585_) {
        this.f_45578_ = (int)p_45585_;
        this.f_45579_ = (int)(p_45585_ >> 32);
    }

    public static ChunkPos m_220337_(int p_220338_, int p_220339_) {
        return new ChunkPos(p_220338_ << 5, p_220339_ << 5);
    }

    public static ChunkPos m_220340_(int p_220341_, int p_220342_) {
        return new ChunkPos((p_220341_ << 5) + 31, (p_220342_ << 5) + 31);
    }

    public long m_45588_() {
        return ChunkPos.m_45589_(this.f_45578_, this.f_45579_);
    }

    public static long m_45589_(int p_45590_, int p_45591_) {
        return (long)p_45590_ & 0xFFFFFFFFL | ((long)p_45591_ & 0xFFFFFFFFL) << 32;
    }

    public static long m_151388_(BlockPos p_151389_) {
        return ChunkPos.m_45589_(SectionPos.m_123171_(p_151389_.m_123341_()), SectionPos.m_123171_(p_151389_.m_123343_()));
    }

    public static int m_45592_(long p_45593_) {
        return (int)(p_45593_ & 0xFFFFFFFFL);
    }

    public static int m_45602_(long p_45603_) {
        return (int)(p_45603_ >>> 32 & 0xFFFFFFFFL);
    }

    public int hashCode() {
        return ChunkPos.m_220343_(this.f_45578_, this.f_45579_);
    }

    public static int m_220343_(int p_220344_, int p_220345_) {
        int $$2 = 1664525 * p_220344_ + 1013904223;
        int $$3 = 1664525 * (p_220345_ ^ 0xDEADBEEF) + 1013904223;
        return $$2 ^ $$3;
    }

    public boolean equals(Object p_45607_) {
        if (this == p_45607_) {
            return true;
        }
        if (p_45607_ instanceof ChunkPos) {
            ChunkPos $$1 = (ChunkPos)p_45607_;
            return this.f_45578_ == $$1.f_45578_ && this.f_45579_ == $$1.f_45579_;
        }
        return false;
    }

    public int m_151390_() {
        return this.m_151382_(8);
    }

    public int m_151393_() {
        return this.m_151391_(8);
    }

    public int m_45604_() {
        return SectionPos.m_123223_(this.f_45578_);
    }

    public int m_45605_() {
        return SectionPos.m_123223_(this.f_45579_);
    }

    public int m_45608_() {
        return this.m_151382_(15);
    }

    public int m_45609_() {
        return this.m_151391_(15);
    }

    public int m_45610_() {
        return this.f_45578_ >> 5;
    }

    public int m_45612_() {
        return this.f_45579_ >> 5;
    }

    public int m_45613_() {
        return this.f_45578_ & 0x1F;
    }

    public int m_45614_() {
        return this.f_45579_ & 0x1F;
    }

    public BlockPos m_151384_(int p_151385_, int p_151386_, int p_151387_) {
        return new BlockPos(this.m_151382_(p_151385_), p_151386_, this.m_151391_(p_151387_));
    }

    public int m_151382_(int p_151383_) {
        return SectionPos.m_175554_(this.f_45578_, p_151383_);
    }

    public int m_151391_(int p_151392_) {
        return SectionPos.m_175554_(this.f_45579_, p_151392_);
    }

    public BlockPos m_151394_(int p_151395_) {
        return new BlockPos(this.m_151390_(), p_151395_, this.m_151393_());
    }

    public String toString() {
        return "[" + this.f_45578_ + ", " + this.f_45579_ + "]";
    }

    public BlockPos m_45615_() {
        return new BlockPos(this.m_45604_(), 0, this.m_45605_());
    }

    public int m_45594_(ChunkPos p_45595_) {
        return Math.max(Math.abs(this.f_45578_ - p_45595_.f_45578_), Math.abs(this.f_45579_ - p_45595_.f_45579_));
    }

    public static Stream<ChunkPos> m_45596_(ChunkPos p_45597_, int p_45598_) {
        return ChunkPos.m_45599_(new ChunkPos(p_45597_.f_45578_ - p_45598_, p_45597_.f_45579_ - p_45598_), new ChunkPos(p_45597_.f_45578_ + p_45598_, p_45597_.f_45579_ + p_45598_));
    }

    public static Stream<ChunkPos> m_45599_(final ChunkPos p_45600_, final ChunkPos p_45601_) {
        int $$2 = Math.abs(p_45600_.f_45578_ - p_45601_.f_45578_) + 1;
        int $$3 = Math.abs(p_45600_.f_45579_ - p_45601_.f_45579_) + 1;
        final int $$4 = p_45600_.f_45578_ < p_45601_.f_45578_ ? 1 : -1;
        final int $$5 = p_45600_.f_45579_ < p_45601_.f_45579_ ? 1 : -1;
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<ChunkPos>((long)($$2 * $$3), 64){
            @Nullable
            private ChunkPos f_45621_;

            @Override
            public boolean tryAdvance(Consumer<? super ChunkPos> p_45630_) {
                if (this.f_45621_ == null) {
                    this.f_45621_ = p_45600_;
                } else {
                    int $$1 = this.f_45621_.f_45578_;
                    int $$2 = this.f_45621_.f_45579_;
                    if ($$1 == p_45601_.f_45578_) {
                        if ($$2 == p_45601_.f_45579_) {
                            return false;
                        }
                        this.f_45621_ = new ChunkPos(p_45600_.f_45578_, $$2 + $$5);
                    } else {
                        this.f_45621_ = new ChunkPos($$1 + $$4, $$2);
                    }
                }
                p_45630_.accept(this.f_45621_);
                return true;
            }
        }, false);
    }
}


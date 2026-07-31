/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagVisitor;

public class LongTag
extends NumericTag {
    private static final int f_177996_ = 128;
    public static final TagType<LongTag> f_128873_ = new TagType.StaticSize<LongTag>(){

        @Override
        public LongTag m_7300_(DataInput p_128906_, int p_128907_, NbtAccounter p_128908_) throws IOException {
            p_128908_.m_6800_(128L);
            return LongTag.m_128882_(p_128906_.readLong());
        }

        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197506_, StreamTagVisitor p_197507_) throws IOException {
            return p_197507_.m_196295_(p_197506_.readLong());
        }

        @Override
        public int m_196292_() {
            return 8;
        }

        @Override
        public String m_5987_() {
            return "LONG";
        }

        @Override
        public String m_5986_() {
            return "TAG_Long";
        }

        @Override
        public boolean m_7064_() {
            return true;
        }

        @Override
        public /* synthetic */ Tag m_7300_(DataInput dataInput, int n, NbtAccounter nbtAccounter) throws IOException {
            return this.m_7300_(dataInput, n, nbtAccounter);
        }
    };
    private final long f_128874_;

    LongTag(long p_128877_) {
        this.f_128874_ = p_128877_;
    }

    public static LongTag m_128882_(long p_128883_) {
        if (p_128883_ >= -128L && p_128883_ <= 1024L) {
            return Cache.f_128915_[(int)p_128883_ - -128];
        }
        return new LongTag(p_128883_);
    }

    @Override
    public void m_6434_(DataOutput p_128885_) throws IOException {
        p_128885_.writeLong(this.f_128874_);
    }

    @Override
    public byte m_7060_() {
        return 4;
    }

    public TagType<LongTag> m_6458_() {
        return f_128873_;
    }

    @Override
    public LongTag m_6426_() {
        return this;
    }

    public boolean equals(Object p_128894_) {
        if (this == p_128894_) {
            return true;
        }
        return p_128894_ instanceof LongTag && this.f_128874_ == ((LongTag)p_128894_).f_128874_;
    }

    public int hashCode() {
        return (int)(this.f_128874_ ^ this.f_128874_ >>> 32);
    }

    @Override
    public void m_142327_(TagVisitor p_177998_) {
        p_177998_.m_142046_(this);
    }

    @Override
    public long m_7046_() {
        return this.f_128874_;
    }

    @Override
    public int m_7047_() {
        return (int)(this.f_128874_ & 0xFFFFFFFFFFFFFFFFL);
    }

    @Override
    public short m_7053_() {
        return (short)(this.f_128874_ & 0xFFFFL);
    }

    @Override
    public byte m_7063_() {
        return (byte)(this.f_128874_ & 0xFFL);
    }

    @Override
    public double m_7061_() {
        return this.f_128874_;
    }

    @Override
    public float m_7057_() {
        return this.f_128874_;
    }

    @Override
    public Number m_8103_() {
        return this.f_128874_;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197504_) {
        return p_197504_.m_196295_(this.f_128874_);
    }

    @Override
    public /* synthetic */ Tag m_6426_() {
        return this.m_6426_();
    }

    static class Cache {
        private static final int f_177999_ = 1024;
        private static final int f_178000_ = -128;
        static final LongTag[] f_128915_ = new LongTag[1153];

        private Cache() {
        }

        static {
            for (int $$0 = 0; $$0 < f_128915_.length; ++$$0) {
                Cache.f_128915_[$$0] = new LongTag(-128 + $$0);
            }
        }
    }
}


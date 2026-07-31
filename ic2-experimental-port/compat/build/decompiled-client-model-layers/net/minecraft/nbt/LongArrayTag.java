/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.nbt;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagVisitor;
import org.apache.commons.lang3.ArrayUtils;

public class LongArrayTag
extends CollectionTag<LongTag> {
    private static final int f_177993_ = 192;
    public static final TagType<LongArrayTag> f_128800_ = new TagType.VariableSize<LongArrayTag>(){

        @Override
        public LongArrayTag m_7300_(DataInput p_128865_, int p_128866_, NbtAccounter p_128867_) throws IOException {
            p_128867_.m_6800_(192L);
            int $$3 = p_128865_.readInt();
            p_128867_.m_6800_(64L * (long)$$3);
            long[] $$4 = new long[$$3];
            for (int $$5 = 0; $$5 < $$3; ++$$5) {
                $$4[$$5] = p_128865_.readLong();
            }
            return new LongArrayTag($$4);
        }

        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197501_, StreamTagVisitor p_197502_) throws IOException {
            int $$2 = p_197501_.readInt();
            long[] $$3 = new long[$$2];
            for (int $$4 = 0; $$4 < $$2; ++$$4) {
                $$3[$$4] = p_197501_.readLong();
            }
            return p_197502_.m_196280_($$3);
        }

        @Override
        public void m_196159_(DataInput p_197499_) throws IOException {
            p_197499_.skipBytes(p_197499_.readInt() * 8);
        }

        @Override
        public String m_5987_() {
            return "LONG[]";
        }

        @Override
        public String m_5986_() {
            return "TAG_Long_Array";
        }

        @Override
        public /* synthetic */ Tag m_7300_(DataInput dataInput, int n, NbtAccounter nbtAccounter) throws IOException {
            return this.m_7300_(dataInput, n, nbtAccounter);
        }
    };
    private long[] f_128801_;

    public LongArrayTag(long[] p_128808_) {
        this.f_128801_ = p_128808_;
    }

    public LongArrayTag(LongSet p_128804_) {
        this.f_128801_ = p_128804_.toLongArray();
    }

    public LongArrayTag(List<Long> p_128806_) {
        this(LongArrayTag.m_128823_(p_128806_));
    }

    private static long[] m_128823_(List<Long> p_128824_) {
        long[] $$1 = new long[p_128824_.size()];
        for (int $$2 = 0; $$2 < p_128824_.size(); ++$$2) {
            Long $$3 = p_128824_.get($$2);
            $$1[$$2] = $$3 == null ? 0L : $$3;
        }
        return $$1;
    }

    @Override
    public void m_6434_(DataOutput p_128819_) throws IOException {
        p_128819_.writeInt(this.f_128801_.length);
        for (long $$1 : this.f_128801_) {
            p_128819_.writeLong($$1);
        }
    }

    @Override
    public byte m_7060_() {
        return 12;
    }

    public TagType<LongArrayTag> m_6458_() {
        return f_128800_;
    }

    @Override
    public String toString() {
        return this.m_7916_();
    }

    @Override
    public LongArrayTag m_6426_() {
        long[] $$0 = new long[this.f_128801_.length];
        System.arraycopy(this.f_128801_, 0, $$0, 0, this.f_128801_.length);
        return new LongArrayTag($$0);
    }

    @Override
    public boolean equals(Object p_128850_) {
        if (this == p_128850_) {
            return true;
        }
        return p_128850_ instanceof LongArrayTag && Arrays.equals(this.f_128801_, ((LongArrayTag)p_128850_).f_128801_);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.f_128801_);
    }

    @Override
    public void m_142327_(TagVisitor p_177995_) {
        p_177995_.m_142309_(this);
    }

    public long[] m_128851_() {
        return this.f_128801_;
    }

    @Override
    public int size() {
        return this.f_128801_.length;
    }

    @Override
    public LongTag get(int p_128811_) {
        return LongTag.m_128882_(this.f_128801_[p_128811_]);
    }

    @Override
    public LongTag set(int p_128813_, LongTag p_128814_) {
        long $$2 = this.f_128801_[p_128813_];
        this.f_128801_[p_128813_] = p_128814_.m_7046_();
        return LongTag.m_128882_($$2);
    }

    @Override
    public void add(int p_128832_, LongTag p_128833_) {
        this.f_128801_ = ArrayUtils.add((long[])this.f_128801_, (int)p_128832_, (long)p_128833_.m_7046_());
    }

    @Override
    public boolean m_7615_(int p_128816_, Tag p_128817_) {
        if (p_128817_ instanceof NumericTag) {
            this.f_128801_[p_128816_] = ((NumericTag)p_128817_).m_7046_();
            return true;
        }
        return false;
    }

    @Override
    public boolean m_7614_(int p_128835_, Tag p_128836_) {
        if (p_128836_ instanceof NumericTag) {
            this.f_128801_ = ArrayUtils.add((long[])this.f_128801_, (int)p_128835_, (long)((NumericTag)p_128836_).m_7046_());
            return true;
        }
        return false;
    }

    @Override
    public LongTag remove(int p_128830_) {
        long $$1 = this.f_128801_[p_128830_];
        this.f_128801_ = ArrayUtils.remove((long[])this.f_128801_, (int)p_128830_);
        return LongTag.m_128882_($$1);
    }

    @Override
    public byte m_7264_() {
        return 4;
    }

    @Override
    public void clear() {
        this.f_128801_ = new long[0];
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197497_) {
        return p_197497_.m_196280_(this.f_128801_);
    }

    @Override
    public /* synthetic */ Tag remove(int n) {
        return this.remove(n);
    }

    @Override
    public /* synthetic */ void add(int n, Tag tag) {
        this.add(n, (LongTag)tag);
    }

    @Override
    public /* synthetic */ Tag set(int n, Tag tag) {
        return this.set(n, (LongTag)tag);
    }

    @Override
    public /* synthetic */ Tag m_6426_() {
        return this.m_6426_();
    }

    @Override
    public /* synthetic */ Object remove(int n) {
        return this.remove(n);
    }

    @Override
    public /* synthetic */ void add(int n, Object object) {
        this.add(n, (LongTag)object);
    }

    @Override
    public /* synthetic */ Object set(int n, Object object) {
        return this.set(n, (LongTag)object);
    }

    @Override
    public /* synthetic */ Object get(int n) {
        return this.get(n);
    }
}


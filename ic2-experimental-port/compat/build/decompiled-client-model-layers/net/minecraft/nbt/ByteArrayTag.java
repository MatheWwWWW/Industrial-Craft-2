/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagVisitor;
import org.apache.commons.lang3.ArrayUtils;

public class ByteArrayTag
extends CollectionTag<ByteTag> {
    private static final int f_177837_ = 192;
    public static final TagType<ByteArrayTag> f_128185_ = new TagType.VariableSize<ByteArrayTag>(){

        @Override
        public ByteArrayTag m_7300_(DataInput p_128247_, int p_128248_, NbtAccounter p_128249_) throws IOException {
            p_128249_.m_6800_(192L);
            int $$3 = p_128247_.readInt();
            p_128249_.m_6800_(8L * (long)$$3);
            byte[] $$4 = new byte[$$3];
            p_128247_.readFully($$4);
            return new ByteArrayTag($$4);
        }

        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197433_, StreamTagVisitor p_197434_) throws IOException {
            int $$2 = p_197433_.readInt();
            byte[] $$3 = new byte[$$2];
            p_197433_.readFully($$3);
            return p_197434_.m_196152_($$3);
        }

        @Override
        public void m_196159_(DataInput p_197431_) throws IOException {
            p_197431_.skipBytes(p_197431_.readInt() * 1);
        }

        @Override
        public String m_5987_() {
            return "BYTE[]";
        }

        @Override
        public String m_5986_() {
            return "TAG_Byte_Array";
        }

        @Override
        public /* synthetic */ Tag m_7300_(DataInput dataInput, int n, NbtAccounter nbtAccounter) throws IOException {
            return this.m_7300_(dataInput, n, nbtAccounter);
        }
    };
    private byte[] f_128186_;

    public ByteArrayTag(byte[] p_128191_) {
        this.f_128186_ = p_128191_;
    }

    public ByteArrayTag(List<Byte> p_128189_) {
        this(ByteArrayTag.m_128206_(p_128189_));
    }

    private static byte[] m_128206_(List<Byte> p_128207_) {
        byte[] $$1 = new byte[p_128207_.size()];
        for (int $$2 = 0; $$2 < p_128207_.size(); ++$$2) {
            Byte $$3 = p_128207_.get($$2);
            $$1[$$2] = $$3 == null ? (byte)0 : $$3;
        }
        return $$1;
    }

    @Override
    public void m_6434_(DataOutput p_128202_) throws IOException {
        p_128202_.writeInt(this.f_128186_.length);
        p_128202_.write(this.f_128186_);
    }

    @Override
    public byte m_7060_() {
        return 7;
    }

    public TagType<ByteArrayTag> m_6458_() {
        return f_128185_;
    }

    @Override
    public String toString() {
        return this.m_7916_();
    }

    @Override
    public Tag m_6426_() {
        byte[] $$0 = new byte[this.f_128186_.length];
        System.arraycopy(this.f_128186_, 0, $$0, 0, this.f_128186_.length);
        return new ByteArrayTag($$0);
    }

    @Override
    public boolean equals(Object p_128233_) {
        if (this == p_128233_) {
            return true;
        }
        return p_128233_ instanceof ByteArrayTag && Arrays.equals(this.f_128186_, ((ByteArrayTag)p_128233_).f_128186_);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.f_128186_);
    }

    @Override
    public void m_142327_(TagVisitor p_177839_) {
        p_177839_.m_142154_(this);
    }

    public byte[] m_128227_() {
        return this.f_128186_;
    }

    @Override
    public int size() {
        return this.f_128186_.length;
    }

    @Override
    public ByteTag get(int p_128194_) {
        return ByteTag.m_128266_(this.f_128186_[p_128194_]);
    }

    @Override
    public ByteTag set(int p_128196_, ByteTag p_128197_) {
        byte $$2 = this.f_128186_[p_128196_];
        this.f_128186_[p_128196_] = p_128197_.m_7063_();
        return ByteTag.m_128266_($$2);
    }

    @Override
    public void add(int p_128215_, ByteTag p_128216_) {
        this.f_128186_ = ArrayUtils.add((byte[])this.f_128186_, (int)p_128215_, (byte)p_128216_.m_7063_());
    }

    @Override
    public boolean m_7615_(int p_128199_, Tag p_128200_) {
        if (p_128200_ instanceof NumericTag) {
            this.f_128186_[p_128199_] = ((NumericTag)p_128200_).m_7063_();
            return true;
        }
        return false;
    }

    @Override
    public boolean m_7614_(int p_128218_, Tag p_128219_) {
        if (p_128219_ instanceof NumericTag) {
            this.f_128186_ = ArrayUtils.add((byte[])this.f_128186_, (int)p_128218_, (byte)((NumericTag)p_128219_).m_7063_());
            return true;
        }
        return false;
    }

    @Override
    public ByteTag remove(int p_128213_) {
        byte $$1 = this.f_128186_[p_128213_];
        this.f_128186_ = ArrayUtils.remove((byte[])this.f_128186_, (int)p_128213_);
        return ByteTag.m_128266_($$1);
    }

    @Override
    public byte m_7264_() {
        return 1;
    }

    @Override
    public void clear() {
        this.f_128186_ = new byte[0];
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197429_) {
        return p_197429_.m_196152_(this.f_128186_);
    }

    @Override
    public /* synthetic */ Tag remove(int n) {
        return this.remove(n);
    }

    @Override
    public /* synthetic */ void add(int n, Tag tag) {
        this.add(n, (ByteTag)tag);
    }

    @Override
    public /* synthetic */ Tag set(int n, Tag tag) {
        return this.set(n, (ByteTag)tag);
    }

    @Override
    public /* synthetic */ Object remove(int n) {
        return this.remove(n);
    }

    @Override
    public /* synthetic */ void add(int n, Object object) {
        this.add(n, (ByteTag)object);
    }

    @Override
    public /* synthetic */ Object set(int n, Object object) {
        return this.set(n, (ByteTag)object);
    }

    @Override
    public /* synthetic */ Object get(int n) {
        return this.get(n);
    }
}


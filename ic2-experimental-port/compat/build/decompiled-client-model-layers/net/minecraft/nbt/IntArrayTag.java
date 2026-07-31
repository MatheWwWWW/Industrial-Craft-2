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
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagVisitor;
import org.apache.commons.lang3.ArrayUtils;

public class IntArrayTag
extends CollectionTag<IntTag> {
    private static final int f_177867_ = 192;
    public static final TagType<IntArrayTag> f_128599_ = new TagType.VariableSize<IntArrayTag>(){

        @Override
        public IntArrayTag m_7300_(DataInput p_128662_, int p_128663_, NbtAccounter p_128664_) throws IOException {
            p_128664_.m_6800_(192L);
            int $$3 = p_128662_.readInt();
            p_128664_.m_6800_(32L * (long)$$3);
            int[] $$4 = new int[$$3];
            for (int $$5 = 0; $$5 < $$3; ++$$5) {
                $$4[$$5] = p_128662_.readInt();
            }
            return new IntArrayTag($$4);
        }

        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197478_, StreamTagVisitor p_197479_) throws IOException {
            int $$2 = p_197478_.readInt();
            int[] $$3 = new int[$$2];
            for (int $$4 = 0; $$4 < $$2; ++$$4) {
                $$3[$$4] = p_197478_.readInt();
            }
            return p_197479_.m_196376_($$3);
        }

        @Override
        public void m_196159_(DataInput p_197476_) throws IOException {
            p_197476_.skipBytes(p_197476_.readInt() * 4);
        }

        @Override
        public String m_5987_() {
            return "INT[]";
        }

        @Override
        public String m_5986_() {
            return "TAG_Int_Array";
        }

        @Override
        public /* synthetic */ Tag m_7300_(DataInput dataInput, int n, NbtAccounter nbtAccounter) throws IOException {
            return this.m_7300_(dataInput, n, nbtAccounter);
        }
    };
    private int[] f_128600_;

    public IntArrayTag(int[] p_128605_) {
        this.f_128600_ = p_128605_;
    }

    public IntArrayTag(List<Integer> p_128603_) {
        this(IntArrayTag.m_128620_(p_128603_));
    }

    private static int[] m_128620_(List<Integer> p_128621_) {
        int[] $$1 = new int[p_128621_.size()];
        for (int $$2 = 0; $$2 < p_128621_.size(); ++$$2) {
            Integer $$3 = p_128621_.get($$2);
            $$1[$$2] = $$3 == null ? 0 : $$3;
        }
        return $$1;
    }

    @Override
    public void m_6434_(DataOutput p_128616_) throws IOException {
        p_128616_.writeInt(this.f_128600_.length);
        for (int $$1 : this.f_128600_) {
            p_128616_.writeInt($$1);
        }
    }

    @Override
    public byte m_7060_() {
        return 11;
    }

    public TagType<IntArrayTag> m_6458_() {
        return f_128599_;
    }

    @Override
    public String toString() {
        return this.m_7916_();
    }

    @Override
    public IntArrayTag m_6426_() {
        int[] $$0 = new int[this.f_128600_.length];
        System.arraycopy(this.f_128600_, 0, $$0, 0, this.f_128600_.length);
        return new IntArrayTag($$0);
    }

    @Override
    public boolean equals(Object p_128647_) {
        if (this == p_128647_) {
            return true;
        }
        return p_128647_ instanceof IntArrayTag && Arrays.equals(this.f_128600_, ((IntArrayTag)p_128647_).f_128600_);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.f_128600_);
    }

    public int[] m_128648_() {
        return this.f_128600_;
    }

    @Override
    public void m_142327_(TagVisitor p_177869_) {
        p_177869_.m_142251_(this);
    }

    @Override
    public int size() {
        return this.f_128600_.length;
    }

    @Override
    public IntTag get(int p_128608_) {
        return IntTag.m_128679_(this.f_128600_[p_128608_]);
    }

    @Override
    public IntTag set(int p_128610_, IntTag p_128611_) {
        int $$2 = this.f_128600_[p_128610_];
        this.f_128600_[p_128610_] = p_128611_.m_7047_();
        return IntTag.m_128679_($$2);
    }

    @Override
    public void add(int p_128629_, IntTag p_128630_) {
        this.f_128600_ = ArrayUtils.add((int[])this.f_128600_, (int)p_128629_, (int)p_128630_.m_7047_());
    }

    @Override
    public boolean m_7615_(int p_128613_, Tag p_128614_) {
        if (p_128614_ instanceof NumericTag) {
            this.f_128600_[p_128613_] = ((NumericTag)p_128614_).m_7047_();
            return true;
        }
        return false;
    }

    @Override
    public boolean m_7614_(int p_128632_, Tag p_128633_) {
        if (p_128633_ instanceof NumericTag) {
            this.f_128600_ = ArrayUtils.add((int[])this.f_128600_, (int)p_128632_, (int)((NumericTag)p_128633_).m_7047_());
            return true;
        }
        return false;
    }

    @Override
    public IntTag remove(int p_128627_) {
        int $$1 = this.f_128600_[p_128627_];
        this.f_128600_ = ArrayUtils.remove((int[])this.f_128600_, (int)p_128627_);
        return IntTag.m_128679_($$1);
    }

    @Override
    public byte m_7264_() {
        return 3;
    }

    @Override
    public void clear() {
        this.f_128600_ = new int[0];
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197474_) {
        return p_197474_.m_196376_(this.f_128600_);
    }

    @Override
    public /* synthetic */ Tag remove(int n) {
        return this.remove(n);
    }

    @Override
    public /* synthetic */ void add(int n, Tag tag) {
        this.add(n, (IntTag)tag);
    }

    @Override
    public /* synthetic */ Tag set(int n, Tag tag) {
        return this.set(n, (IntTag)tag);
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
        this.add(n, (IntTag)object);
    }

    @Override
    public /* synthetic */ Object set(int n, Object object) {
        return this.set(n, (IntTag)object);
    }

    @Override
    public /* synthetic */ Object get(int n) {
        return this.get(n);
    }
}


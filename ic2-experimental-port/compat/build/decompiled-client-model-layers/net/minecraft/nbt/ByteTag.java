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

public class ByteTag
extends NumericTag {
    private static final int f_177840_ = 72;
    public static final TagType<ByteTag> f_128255_ = new TagType.StaticSize<ByteTag>(){

        @Override
        public ByteTag m_7300_(DataInput p_128292_, int p_128293_, NbtAccounter p_128294_) throws IOException {
            p_128294_.m_6800_(72L);
            return ByteTag.m_128266_(p_128292_.readByte());
        }

        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197438_, StreamTagVisitor p_197439_) throws IOException {
            return p_197439_.m_196209_(p_197438_.readByte());
        }

        @Override
        public int m_196292_() {
            return 1;
        }

        @Override
        public String m_5987_() {
            return "BYTE";
        }

        @Override
        public String m_5986_() {
            return "TAG_Byte";
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
    public static final ByteTag f_128256_ = ByteTag.m_128266_((byte)0);
    public static final ByteTag f_128257_ = ByteTag.m_128266_((byte)1);
    private final byte f_128258_;

    ByteTag(byte p_128261_) {
        this.f_128258_ = p_128261_;
    }

    public static ByteTag m_128266_(byte p_128267_) {
        return Cache.f_128301_[128 + p_128267_];
    }

    public static ByteTag m_128273_(boolean p_128274_) {
        return p_128274_ ? f_128257_ : f_128256_;
    }

    @Override
    public void m_6434_(DataOutput p_128269_) throws IOException {
        p_128269_.writeByte(this.f_128258_);
    }

    @Override
    public byte m_7060_() {
        return 1;
    }

    public TagType<ByteTag> m_6458_() {
        return f_128255_;
    }

    @Override
    public ByteTag m_6426_() {
        return this;
    }

    public boolean equals(Object p_128280_) {
        if (this == p_128280_) {
            return true;
        }
        return p_128280_ instanceof ByteTag && this.f_128258_ == ((ByteTag)p_128280_).f_128258_;
    }

    public int hashCode() {
        return this.f_128258_;
    }

    @Override
    public void m_142327_(TagVisitor p_177842_) {
        p_177842_.m_141946_(this);
    }

    @Override
    public long m_7046_() {
        return this.f_128258_;
    }

    @Override
    public int m_7047_() {
        return this.f_128258_;
    }

    @Override
    public short m_7053_() {
        return this.f_128258_;
    }

    @Override
    public byte m_7063_() {
        return this.f_128258_;
    }

    @Override
    public double m_7061_() {
        return this.f_128258_;
    }

    @Override
    public float m_7057_() {
        return this.f_128258_;
    }

    @Override
    public Number m_8103_() {
        return this.f_128258_;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197436_) {
        return p_197436_.m_196209_(this.f_128258_);
    }

    @Override
    public /* synthetic */ Tag m_6426_() {
        return this.m_6426_();
    }

    static class Cache {
        static final ByteTag[] f_128301_ = new ByteTag[256];

        private Cache() {
        }

        static {
            for (int $$0 = 0; $$0 < f_128301_.length; ++$$0) {
                Cache.f_128301_[$$0] = new ByteTag((byte)($$0 - 128));
            }
        }
    }
}


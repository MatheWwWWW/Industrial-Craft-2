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

public class ShortTag
extends NumericTag {
    private static final int f_178082_ = 80;
    public static final TagType<ShortTag> f_129244_ = new TagType.StaticSize<ShortTag>(){

        @Override
        public ShortTag m_7300_(DataInput p_129277_, int p_129278_, NbtAccounter p_129279_) throws IOException {
            p_129279_.m_6800_(80L);
            return ShortTag.m_129258_(p_129277_.readShort());
        }

        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197517_, StreamTagVisitor p_197518_) throws IOException {
            return p_197518_.m_196553_(p_197517_.readShort());
        }

        @Override
        public int m_196292_() {
            return 2;
        }

        @Override
        public String m_5987_() {
            return "SHORT";
        }

        @Override
        public String m_5986_() {
            return "TAG_Short";
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
    private final short f_129245_;

    ShortTag(short p_129248_) {
        this.f_129245_ = p_129248_;
    }

    public static ShortTag m_129258_(short p_129259_) {
        if (p_129259_ >= -128 && p_129259_ <= 1024) {
            return Cache.f_129286_[p_129259_ - -128];
        }
        return new ShortTag(p_129259_);
    }

    @Override
    public void m_6434_(DataOutput p_129254_) throws IOException {
        p_129254_.writeShort(this.f_129245_);
    }

    @Override
    public byte m_7060_() {
        return 2;
    }

    public TagType<ShortTag> m_6458_() {
        return f_129244_;
    }

    @Override
    public ShortTag m_6426_() {
        return this;
    }

    public boolean equals(Object p_129265_) {
        if (this == p_129265_) {
            return true;
        }
        return p_129265_ instanceof ShortTag && this.f_129245_ == ((ShortTag)p_129265_).f_129245_;
    }

    public int hashCode() {
        return this.f_129245_;
    }

    @Override
    public void m_142327_(TagVisitor p_178084_) {
        p_178084_.m_142183_(this);
    }

    @Override
    public long m_7046_() {
        return this.f_129245_;
    }

    @Override
    public int m_7047_() {
        return this.f_129245_;
    }

    @Override
    public short m_7053_() {
        return this.f_129245_;
    }

    @Override
    public byte m_7063_() {
        return (byte)(this.f_129245_ & 0xFF);
    }

    @Override
    public double m_7061_() {
        return this.f_129245_;
    }

    @Override
    public float m_7057_() {
        return this.f_129245_;
    }

    @Override
    public Number m_8103_() {
        return this.f_129245_;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197515_) {
        return p_197515_.m_196553_(this.f_129245_);
    }

    @Override
    public /* synthetic */ Tag m_6426_() {
        return this.m_6426_();
    }

    static class Cache {
        private static final int f_178085_ = 1024;
        private static final int f_178086_ = -128;
        static final ShortTag[] f_129286_ = new ShortTag[1153];

        private Cache() {
        }

        static {
            for (int $$0 = 0; $$0 < f_129286_.length; ++$$0) {
                Cache.f_129286_[$$0] = new ShortTag((short)(-128 + $$0));
            }
        }
    }
}


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
import net.minecraft.util.Mth;

public class DoubleTag
extends NumericTag {
    private static final int f_177858_ = 128;
    public static final DoubleTag f_128493_ = new DoubleTag(0.0);
    public static final TagType<DoubleTag> f_128494_ = new TagType.StaticSize<DoubleTag>(){

        @Override
        public DoubleTag m_7300_(DataInput p_128524_, int p_128525_, NbtAccounter p_128526_) throws IOException {
            p_128526_.m_6800_(128L);
            return DoubleTag.m_128500_(p_128524_.readDouble());
        }

        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197454_, StreamTagVisitor p_197455_) throws IOException {
            return p_197455_.m_196455_(p_197454_.readDouble());
        }

        @Override
        public int m_196292_() {
            return 8;
        }

        @Override
        public String m_5987_() {
            return "DOUBLE";
        }

        @Override
        public String m_5986_() {
            return "TAG_Double";
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
    private final double f_128495_;

    private DoubleTag(double p_128498_) {
        this.f_128495_ = p_128498_;
    }

    public static DoubleTag m_128500_(double p_128501_) {
        if (p_128501_ == 0.0) {
            return f_128493_;
        }
        return new DoubleTag(p_128501_);
    }

    @Override
    public void m_6434_(DataOutput p_128503_) throws IOException {
        p_128503_.writeDouble(this.f_128495_);
    }

    @Override
    public byte m_7060_() {
        return 6;
    }

    public TagType<DoubleTag> m_6458_() {
        return f_128494_;
    }

    @Override
    public DoubleTag m_6426_() {
        return this;
    }

    public boolean equals(Object p_128512_) {
        if (this == p_128512_) {
            return true;
        }
        return p_128512_ instanceof DoubleTag && this.f_128495_ == ((DoubleTag)p_128512_).f_128495_;
    }

    public int hashCode() {
        long $$0 = Double.doubleToLongBits(this.f_128495_);
        return (int)($$0 ^ $$0 >>> 32);
    }

    @Override
    public void m_142327_(TagVisitor p_177860_) {
        p_177860_.m_142121_(this);
    }

    @Override
    public long m_7046_() {
        return (long)Math.floor(this.f_128495_);
    }

    @Override
    public int m_7047_() {
        return Mth.m_14107_(this.f_128495_);
    }

    @Override
    public short m_7053_() {
        return (short)(Mth.m_14107_(this.f_128495_) & 0xFFFF);
    }

    @Override
    public byte m_7063_() {
        return (byte)(Mth.m_14107_(this.f_128495_) & 0xFF);
    }

    @Override
    public double m_7061_() {
        return this.f_128495_;
    }

    @Override
    public float m_7057_() {
        return (float)this.f_128495_;
    }

    @Override
    public Number m_8103_() {
        return this.f_128495_;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197452_) {
        return p_197452_.m_196455_(this.f_128495_);
    }

    @Override
    public /* synthetic */ Tag m_6426_() {
        return this.m_6426_();
    }
}


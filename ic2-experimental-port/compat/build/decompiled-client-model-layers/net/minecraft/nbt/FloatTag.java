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

public class FloatTag
extends NumericTag {
    private static final int f_177864_ = 96;
    public static final FloatTag f_128559_ = new FloatTag(0.0f);
    public static final TagType<FloatTag> f_128560_ = new TagType.StaticSize<FloatTag>(){

        @Override
        public FloatTag m_7300_(DataInput p_128590_, int p_128591_, NbtAccounter p_128592_) throws IOException {
            p_128592_.m_6800_(96L);
            return FloatTag.m_128566_(p_128590_.readFloat());
        }

        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197470_, StreamTagVisitor p_197471_) throws IOException {
            return p_197471_.m_196532_(p_197470_.readFloat());
        }

        @Override
        public int m_196292_() {
            return 4;
        }

        @Override
        public String m_5987_() {
            return "FLOAT";
        }

        @Override
        public String m_5986_() {
            return "TAG_Float";
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
    private final float f_128561_;

    private FloatTag(float p_128564_) {
        this.f_128561_ = p_128564_;
    }

    public static FloatTag m_128566_(float p_128567_) {
        if (p_128567_ == 0.0f) {
            return f_128559_;
        }
        return new FloatTag(p_128567_);
    }

    @Override
    public void m_6434_(DataOutput p_128569_) throws IOException {
        p_128569_.writeFloat(this.f_128561_);
    }

    @Override
    public byte m_7060_() {
        return 5;
    }

    public TagType<FloatTag> m_6458_() {
        return f_128560_;
    }

    @Override
    public FloatTag m_6426_() {
        return this;
    }

    public boolean equals(Object p_128578_) {
        if (this == p_128578_) {
            return true;
        }
        return p_128578_ instanceof FloatTag && this.f_128561_ == ((FloatTag)p_128578_).f_128561_;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.f_128561_);
    }

    @Override
    public void m_142327_(TagVisitor p_177866_) {
        p_177866_.m_142181_(this);
    }

    @Override
    public long m_7046_() {
        return (long)this.f_128561_;
    }

    @Override
    public int m_7047_() {
        return Mth.m_14143_(this.f_128561_);
    }

    @Override
    public short m_7053_() {
        return (short)(Mth.m_14143_(this.f_128561_) & 0xFFFF);
    }

    @Override
    public byte m_7063_() {
        return (byte)(Mth.m_14143_(this.f_128561_) & 0xFF);
    }

    @Override
    public double m_7061_() {
        return this.f_128561_;
    }

    @Override
    public float m_7057_() {
        return this.f_128561_;
    }

    @Override
    public Number m_8103_() {
        return Float.valueOf(this.f_128561_);
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197468_) {
        return p_197468_.m_196532_(this.f_128561_);
    }

    @Override
    public /* synthetic */ Tag m_6426_() {
        return this.m_6426_();
    }
}


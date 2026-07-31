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

public class IntTag
extends NumericTag {
    private static final int f_177982_ = 96;
    public static final TagType<IntTag> f_128670_ = new TagType.StaticSize<IntTag>(){

        @Override
        public IntTag m_7300_(DataInput p_128703_, int p_128704_, NbtAccounter p_128705_) throws IOException {
            p_128705_.m_6800_(96L);
            return IntTag.m_128679_(p_128703_.readInt());
        }

        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197483_, StreamTagVisitor p_197484_) throws IOException {
            return p_197484_.m_196353_(p_197483_.readInt());
        }

        @Override
        public int m_196292_() {
            return 4;
        }

        @Override
        public String m_5987_() {
            return "INT";
        }

        @Override
        public String m_5986_() {
            return "TAG_Int";
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
    private final int f_128671_;

    IntTag(int p_128674_) {
        this.f_128671_ = p_128674_;
    }

    public static IntTag m_128679_(int p_128680_) {
        if (p_128680_ >= -128 && p_128680_ <= 1024) {
            return Cache.f_128712_[p_128680_ - -128];
        }
        return new IntTag(p_128680_);
    }

    @Override
    public void m_6434_(DataOutput p_128682_) throws IOException {
        p_128682_.writeInt(this.f_128671_);
    }

    @Override
    public byte m_7060_() {
        return 3;
    }

    public TagType<IntTag> m_6458_() {
        return f_128670_;
    }

    @Override
    public IntTag m_6426_() {
        return this;
    }

    public boolean equals(Object p_128691_) {
        if (this == p_128691_) {
            return true;
        }
        return p_128691_ instanceof IntTag && this.f_128671_ == ((IntTag)p_128691_).f_128671_;
    }

    public int hashCode() {
        return this.f_128671_;
    }

    @Override
    public void m_142327_(TagVisitor p_177984_) {
        p_177984_.m_142045_(this);
    }

    @Override
    public long m_7046_() {
        return this.f_128671_;
    }

    @Override
    public int m_7047_() {
        return this.f_128671_;
    }

    @Override
    public short m_7053_() {
        return (short)(this.f_128671_ & 0xFFFF);
    }

    @Override
    public byte m_7063_() {
        return (byte)(this.f_128671_ & 0xFF);
    }

    @Override
    public double m_7061_() {
        return this.f_128671_;
    }

    @Override
    public float m_7057_() {
        return this.f_128671_;
    }

    @Override
    public Number m_8103_() {
        return this.f_128671_;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197481_) {
        return p_197481_.m_196353_(this.f_128671_);
    }

    @Override
    public /* synthetic */ Tag m_6426_() {
        return this.m_6426_();
    }

    static class Cache {
        private static final int f_177985_ = 1024;
        private static final int f_177986_ = -128;
        static final IntTag[] f_128712_ = new IntTag[1153];

        private Cache() {
        }

        static {
            for (int $$0 = 0; $$0 < f_128712_.length; ++$$0) {
                Cache.f_128712_[$$0] = new IntTag(-128 + $$0);
            }
        }
    }
}


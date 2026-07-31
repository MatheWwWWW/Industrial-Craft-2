/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.util.Objects;
import net.minecraft.Util;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagVisitor;

public class StringTag
implements Tag {
    private static final int f_178148_ = 288;
    public static final TagType<StringTag> f_129288_ = new TagType.VariableSize<StringTag>(){

        @Override
        public StringTag m_7300_(DataInput p_129315_, int p_129316_, NbtAccounter p_129317_) throws IOException {
            p_129317_.m_6800_(288L);
            String $$3 = p_129315_.readUTF();
            p_129317_.m_6800_(16 * $$3.length());
            return StringTag.m_129297_($$3);
        }

        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197570_, StreamTagVisitor p_197571_) throws IOException {
            return p_197571_.m_196458_(p_197570_.readUTF());
        }

        @Override
        public void m_196159_(DataInput p_197568_) throws IOException {
            StringTag.m_197563_(p_197568_);
        }

        @Override
        public String m_5987_() {
            return "STRING";
        }

        @Override
        public String m_5986_() {
            return "TAG_String";
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
    private static final StringTag f_129289_ = new StringTag("");
    private static final char f_178149_ = '\"';
    private static final char f_178150_ = '\'';
    private static final char f_178151_ = '\\';
    private static final char f_178152_ = '\u0000';
    private final String f_129290_;

    public static void m_197563_(DataInput p_197564_) throws IOException {
        p_197564_.skipBytes(p_197564_.readUnsignedShort());
    }

    private StringTag(String p_129293_) {
        Objects.requireNonNull(p_129293_, "Null string not allowed");
        this.f_129290_ = p_129293_;
    }

    public static StringTag m_129297_(String p_129298_) {
        if (p_129298_.isEmpty()) {
            return f_129289_;
        }
        return new StringTag(p_129298_);
    }

    @Override
    public void m_6434_(DataOutput p_129296_) throws IOException {
        try {
            p_129296_.writeUTF(this.f_129290_);
        }
        catch (UTFDataFormatException $$1) {
            Util.m_200890_("Failed to write NBT String", $$1);
            p_129296_.writeUTF("");
        }
    }

    @Override
    public byte m_7060_() {
        return 8;
    }

    public TagType<StringTag> m_6458_() {
        return f_129288_;
    }

    @Override
    public String toString() {
        return Tag.super.m_7916_();
    }

    @Override
    public StringTag m_6426_() {
        return this;
    }

    public boolean equals(Object p_129308_) {
        if (this == p_129308_) {
            return true;
        }
        return p_129308_ instanceof StringTag && Objects.equals(this.f_129290_, ((StringTag)p_129308_).f_129290_);
    }

    public int hashCode() {
        return this.f_129290_.hashCode();
    }

    @Override
    public String m_7916_() {
        return this.f_129290_;
    }

    @Override
    public void m_142327_(TagVisitor p_178154_) {
        p_178154_.m_142614_(this);
    }

    public static String m_129303_(String p_129304_) {
        StringBuilder $$1 = new StringBuilder(" ");
        int $$2 = 0;
        for (int $$3 = 0; $$3 < p_129304_.length(); ++$$3) {
            int $$4 = p_129304_.charAt($$3);
            if ($$4 == 92) {
                $$1.append('\\');
            } else if ($$4 == 34 || $$4 == 39) {
                if ($$2 == 0) {
                    int n = $$2 = $$4 == 34 ? 39 : 34;
                }
                if ($$2 == $$4) {
                    $$1.append('\\');
                }
            }
            $$1.append((char)$$4);
        }
        if ($$2 == 0) {
            $$2 = 34;
        }
        $$1.setCharAt(0, (char)$$2);
        $$1.append((char)$$2);
        return $$1.toString();
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197566_) {
        return p_197566_.m_196458_(this.f_129290_);
    }

    @Override
    public /* synthetic */ Tag m_6426_() {
        return this.m_6426_();
    }
}


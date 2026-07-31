/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt;

import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.StringTagVisitor;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagVisitor;

public interface Tag {
    public static final int f_178189_ = 64;
    public static final int f_178190_ = 96;
    public static final int f_178191_ = 32;
    public static final int f_178192_ = 224;
    public static final byte f_178193_ = 0;
    public static final byte f_178194_ = 1;
    public static final byte f_178195_ = 2;
    public static final byte f_178196_ = 3;
    public static final byte f_178197_ = 4;
    public static final byte f_178198_ = 5;
    public static final byte f_178199_ = 6;
    public static final byte f_178200_ = 7;
    public static final byte f_178201_ = 8;
    public static final byte f_178202_ = 9;
    public static final byte f_178203_ = 10;
    public static final byte f_178204_ = 11;
    public static final byte f_178205_ = 12;
    public static final byte f_178206_ = 99;
    public static final int f_178207_ = 512;

    public void m_6434_(DataOutput var1) throws IOException;

    public String toString();

    public byte m_7060_();

    public TagType<?> m_6458_();

    public Tag m_6426_();

    default public String m_7916_() {
        return new StringTagVisitor().m_178187_(this);
    }

    public void m_142327_(TagVisitor var1);

    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor var1);

    default public void m_197573_(StreamTagVisitor p_197574_) {
        StreamTagVisitor.ValueResult $$1 = p_197574_.m_196213_(this.m_6458_());
        if ($$1 == StreamTagVisitor.ValueResult.CONTINUE) {
            this.m_196533_(p_197574_);
        }
    }
}


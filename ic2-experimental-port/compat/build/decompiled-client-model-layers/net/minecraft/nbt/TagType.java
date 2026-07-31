/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.IOException;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.Tag;

public interface TagType<T extends Tag> {
    public T m_7300_(DataInput var1, int var2, NbtAccounter var3) throws IOException;

    public StreamTagVisitor.ValueResult m_196511_(DataInput var1, StreamTagVisitor var2) throws IOException;

    default public void m_197580_(DataInput p_197581_, StreamTagVisitor p_197582_) throws IOException {
        switch (p_197582_.m_196213_(this)) {
            case CONTINUE: {
                this.m_196511_(p_197581_, p_197582_);
                break;
            }
            case HALT: {
                break;
            }
            case BREAK: {
                this.m_196159_(p_197581_);
            }
        }
    }

    public void m_196189_(DataInput var1, int var2) throws IOException;

    public void m_196159_(DataInput var1) throws IOException;

    default public boolean m_7064_() {
        return false;
    }

    public String m_5987_();

    public String m_5986_();

    public static TagType<EndTag> m_129377_(final int p_129378_) {
        return new TagType<EndTag>(){

            private IOException m_197591_() {
                return new IOException("Invalid tag id: " + p_129378_);
            }

            @Override
            public EndTag m_7300_(DataInput p_129387_, int p_129388_, NbtAccounter p_129389_) throws IOException {
                throw this.m_197591_();
            }

            @Override
            public StreamTagVisitor.ValueResult m_196511_(DataInput p_197589_, StreamTagVisitor p_197590_) throws IOException {
                throw this.m_197591_();
            }

            @Override
            public void m_196189_(DataInput p_197586_, int p_197587_) throws IOException {
                throw this.m_197591_();
            }

            @Override
            public void m_196159_(DataInput p_197584_) throws IOException {
                throw this.m_197591_();
            }

            @Override
            public String m_5987_() {
                return "INVALID[" + p_129378_ + "]";
            }

            @Override
            public String m_5986_() {
                return "UNKNOWN_" + p_129378_;
            }

            @Override
            public /* synthetic */ Tag m_7300_(DataInput dataInput, int n, NbtAccounter nbtAccounter) throws IOException {
                return this.m_7300_(dataInput, n, nbtAccounter);
            }
        };
    }

    public static interface VariableSize<T extends Tag>
    extends TagType<T> {
        @Override
        default public void m_196189_(DataInput p_197600_, int p_197601_) throws IOException {
            for (int $$2 = 0; $$2 < p_197601_; ++$$2) {
                this.m_196159_(p_197600_);
            }
        }
    }

    public static interface StaticSize<T extends Tag>
    extends TagType<T> {
        @Override
        default public void m_196159_(DataInput p_197595_) throws IOException {
            p_197595_.skipBytes(this.m_196292_());
        }

        @Override
        default public void m_196189_(DataInput p_197597_, int p_197598_) throws IOException {
            p_197597_.skipBytes(this.m_196292_() * p_197598_);
        }

        public int m_196292_();
    }
}


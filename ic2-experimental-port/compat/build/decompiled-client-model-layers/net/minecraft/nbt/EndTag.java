/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagVisitor;

public class EndTag
implements Tag {
    private static final int f_177861_ = 64;
    public static final TagType<EndTag> f_128533_ = new TagType<EndTag>(){

        @Override
        public EndTag m_7300_(DataInput p_128550_, int p_128551_, NbtAccounter p_128552_) {
            p_128552_.m_6800_(64L);
            return f_128534_;
        }

        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197465_, StreamTagVisitor p_197466_) {
            return p_197466_.m_196525_();
        }

        @Override
        public void m_196189_(DataInput p_197462_, int p_197463_) {
        }

        @Override
        public void m_196159_(DataInput p_197460_) {
        }

        @Override
        public String m_5987_() {
            return "END";
        }

        @Override
        public String m_5986_() {
            return "TAG_End";
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
    public static final EndTag f_128534_ = new EndTag();

    private EndTag() {
    }

    @Override
    public void m_6434_(DataOutput p_128539_) throws IOException {
    }

    @Override
    public byte m_7060_() {
        return 0;
    }

    public TagType<EndTag> m_6458_() {
        return f_128533_;
    }

    @Override
    public String toString() {
        return this.m_7916_();
    }

    @Override
    public EndTag m_6426_() {
        return this;
    }

    @Override
    public void m_142327_(TagVisitor p_177863_) {
        p_177863_.m_142384_(this);
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197458_) {
        return p_197458_.m_196525_();
    }

    @Override
    public /* synthetic */ Tag m_6426_() {
        return this.m_6426_();
    }
}


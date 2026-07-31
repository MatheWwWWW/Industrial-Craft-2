/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 */
package net.minecraft.nbt;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagTypes;
import net.minecraft.nbt.TagVisitor;

public class ListTag
extends CollectionTag<Tag> {
    private static final int f_177988_ = 296;
    public static final TagType<ListTag> f_128714_ = new TagType.VariableSize<ListTag>(){

        @Override
        public ListTag m_7300_(DataInput p_128792_, int p_128793_, NbtAccounter p_128794_) throws IOException {
            p_128794_.m_6800_(296L);
            if (p_128793_ > 512) {
                throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
            }
            byte $$3 = p_128792_.readByte();
            int $$4 = p_128792_.readInt();
            if ($$3 == 0 && $$4 > 0) {
                throw new RuntimeException("Missing type on ListTag");
            }
            p_128794_.m_6800_(32L * (long)$$4);
            TagType<?> $$5 = TagTypes.m_129397_($$3);
            ArrayList $$6 = Lists.newArrayListWithCapacity((int)$$4);
            for (int $$7 = 0; $$7 < $$4; ++$$7) {
                $$6.add($$5.m_7300_(p_128792_, p_128793_ + 1, p_128794_));
            }
            return new ListTag($$6, $$3);
        }

        /*
         * Exception decompiling
         */
        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197491_, StreamTagVisitor p_197492_) throws IOException {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[SWITCH], 8[CASE]], but top level block is 9[SWITCH]
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        @Override
        public void m_196159_(DataInput p_197489_) throws IOException {
            TagType<?> $$1 = TagTypes.m_129397_(p_197489_.readByte());
            int $$2 = p_197489_.readInt();
            $$1.m_196189_(p_197489_, $$2);
        }

        @Override
        public String m_5987_() {
            return "LIST";
        }

        @Override
        public String m_5986_() {
            return "TAG_List";
        }

        @Override
        public /* synthetic */ Tag m_7300_(DataInput dataInput, int n, NbtAccounter nbtAccounter) throws IOException {
            return this.m_7300_(dataInput, n, nbtAccounter);
        }
    };
    private final List<Tag> f_128716_;
    private byte f_128717_;

    ListTag(List<Tag> p_128721_, byte p_128722_) {
        this.f_128716_ = p_128721_;
        this.f_128717_ = p_128722_;
    }

    public ListTag() {
        this(Lists.newArrayList(), 0);
    }

    @Override
    public void m_6434_(DataOutput p_128734_) throws IOException {
        this.f_128717_ = this.f_128716_.isEmpty() ? (byte)0 : this.f_128716_.get(0).m_7060_();
        p_128734_.writeByte(this.f_128717_);
        p_128734_.writeInt(this.f_128716_.size());
        for (Tag $$1 : this.f_128716_) {
            $$1.m_6434_(p_128734_);
        }
    }

    @Override
    public byte m_7060_() {
        return 9;
    }

    public TagType<ListTag> m_6458_() {
        return f_128714_;
    }

    @Override
    public String toString() {
        return this.m_7916_();
    }

    private void m_128769_() {
        if (this.f_128716_.isEmpty()) {
            this.f_128717_ = 0;
        }
    }

    @Override
    public Tag remove(int p_128751_) {
        Tag $$1 = this.f_128716_.remove(p_128751_);
        this.m_128769_();
        return $$1;
    }

    @Override
    public boolean isEmpty() {
        return this.f_128716_.isEmpty();
    }

    public CompoundTag m_128728_(int p_128729_) {
        Tag $$1;
        if (p_128729_ >= 0 && p_128729_ < this.f_128716_.size() && ($$1 = this.f_128716_.get(p_128729_)).m_7060_() == 10) {
            return (CompoundTag)$$1;
        }
        return new CompoundTag();
    }

    public ListTag m_128744_(int p_128745_) {
        Tag $$1;
        if (p_128745_ >= 0 && p_128745_ < this.f_128716_.size() && ($$1 = this.f_128716_.get(p_128745_)).m_7060_() == 9) {
            return (ListTag)$$1;
        }
        return new ListTag();
    }

    public short m_128757_(int p_128758_) {
        Tag $$1;
        if (p_128758_ >= 0 && p_128758_ < this.f_128716_.size() && ($$1 = this.f_128716_.get(p_128758_)).m_7060_() == 2) {
            return ((ShortTag)$$1).m_7053_();
        }
        return 0;
    }

    public int m_128763_(int p_128764_) {
        Tag $$1;
        if (p_128764_ >= 0 && p_128764_ < this.f_128716_.size() && ($$1 = this.f_128716_.get(p_128764_)).m_7060_() == 3) {
            return ((IntTag)$$1).m_7047_();
        }
        return 0;
    }

    public int[] m_128767_(int p_128768_) {
        Tag $$1;
        if (p_128768_ >= 0 && p_128768_ < this.f_128716_.size() && ($$1 = this.f_128716_.get(p_128768_)).m_7060_() == 11) {
            return ((IntArrayTag)$$1).m_128648_();
        }
        return new int[0];
    }

    public long[] m_177991_(int p_177992_) {
        Tag $$1;
        if (p_177992_ >= 0 && p_177992_ < this.f_128716_.size() && ($$1 = this.f_128716_.get(p_177992_)).m_7060_() == 11) {
            return ((LongArrayTag)$$1).m_128851_();
        }
        return new long[0];
    }

    public double m_128772_(int p_128773_) {
        Tag $$1;
        if (p_128773_ >= 0 && p_128773_ < this.f_128716_.size() && ($$1 = this.f_128716_.get(p_128773_)).m_7060_() == 6) {
            return ((DoubleTag)$$1).m_7061_();
        }
        return 0.0;
    }

    public float m_128775_(int p_128776_) {
        Tag $$1;
        if (p_128776_ >= 0 && p_128776_ < this.f_128716_.size() && ($$1 = this.f_128716_.get(p_128776_)).m_7060_() == 5) {
            return ((FloatTag)$$1).m_7057_();
        }
        return 0.0f;
    }

    public String m_128778_(int p_128779_) {
        if (p_128779_ < 0 || p_128779_ >= this.f_128716_.size()) {
            return "";
        }
        Tag $$1 = this.f_128716_.get(p_128779_);
        if ($$1.m_7060_() == 8) {
            return $$1.m_7916_();
        }
        return $$1.toString();
    }

    @Override
    public int size() {
        return this.f_128716_.size();
    }

    @Override
    public Tag get(int p_128781_) {
        return this.f_128716_.get(p_128781_);
    }

    @Override
    public Tag set(int p_128760_, Tag p_128761_) {
        Tag $$2 = this.get(p_128760_);
        if (!this.m_7615_(p_128760_, p_128761_)) {
            throw new UnsupportedOperationException(String.format(Locale.ROOT, "Trying to add tag of type %d to list of %d", p_128761_.m_7060_(), this.f_128717_));
        }
        return $$2;
    }

    @Override
    public void add(int p_128753_, Tag p_128754_) {
        if (!this.m_7614_(p_128753_, p_128754_)) {
            throw new UnsupportedOperationException(String.format(Locale.ROOT, "Trying to add tag of type %d to list of %d", p_128754_.m_7060_(), this.f_128717_));
        }
    }

    @Override
    public boolean m_7615_(int p_128731_, Tag p_128732_) {
        if (this.m_128738_(p_128732_)) {
            this.f_128716_.set(p_128731_, p_128732_);
            return true;
        }
        return false;
    }

    @Override
    public boolean m_7614_(int p_128747_, Tag p_128748_) {
        if (this.m_128738_(p_128748_)) {
            this.f_128716_.add(p_128747_, p_128748_);
            return true;
        }
        return false;
    }

    private boolean m_128738_(Tag p_128739_) {
        if (p_128739_.m_7060_() == 0) {
            return false;
        }
        if (this.f_128717_ == 0) {
            this.f_128717_ = p_128739_.m_7060_();
            return true;
        }
        return this.f_128717_ == p_128739_.m_7060_();
    }

    @Override
    public ListTag m_6426_() {
        List<Tag> $$0 = TagTypes.m_129397_(this.f_128717_).m_7064_() ? this.f_128716_ : Iterables.transform(this.f_128716_, Tag::m_6426_);
        ArrayList $$1 = Lists.newArrayList($$0);
        return new ListTag($$1, this.f_128717_);
    }

    @Override
    public boolean equals(Object p_128766_) {
        if (this == p_128766_) {
            return true;
        }
        return p_128766_ instanceof ListTag && Objects.equals(this.f_128716_, ((ListTag)p_128766_).f_128716_);
    }

    @Override
    public int hashCode() {
        return this.f_128716_.hashCode();
    }

    @Override
    public void m_142327_(TagVisitor p_177990_) {
        p_177990_.m_142447_(this);
    }

    @Override
    public byte m_7264_() {
        return this.f_128717_;
    }

    @Override
    public void clear() {
        this.f_128716_.clear();
        this.f_128717_ = 0;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197487_) {
        switch (p_197487_.m_196339_(TagTypes.m_129397_(this.f_128717_), this.f_128716_.size())) {
            case HALT: {
                return StreamTagVisitor.ValueResult.HALT;
            }
            case BREAK: {
                return p_197487_.m_196527_();
            }
        }
        block13: for (int $$1 = 0; $$1 < this.f_128716_.size(); ++$$1) {
            Tag $$2 = this.f_128716_.get($$1);
            switch (p_197487_.m_196338_($$2.m_6458_(), $$1)) {
                case HALT: {
                    return StreamTagVisitor.ValueResult.HALT;
                }
                case SKIP: {
                    continue block13;
                }
                case BREAK: {
                    return p_197487_.m_196527_();
                }
                default: {
                    switch ($$2.m_196533_(p_197487_)) {
                        case HALT: {
                            return StreamTagVisitor.ValueResult.HALT;
                        }
                        case BREAK: {
                            return p_197487_.m_196527_();
                        }
                    }
                }
            }
        }
        return p_197487_.m_196527_();
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
        this.add(n, (Tag)object);
    }

    @Override
    public /* synthetic */ Object set(int n, Object object) {
        return this.set(n, (Tag)object);
    }

    @Override
    public /* synthetic */ Object get(int n) {
        return this.get(n);
    }
}


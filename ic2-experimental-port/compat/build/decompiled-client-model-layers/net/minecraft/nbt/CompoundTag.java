/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 */
package net.minecraft.nbt;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagTypes;
import net.minecraft.nbt.TagVisitor;

public class CompoundTag
implements Tag {
    public static final Codec<CompoundTag> f_128325_ = Codec.PASSTHROUGH.comapFlatMap(p_128336_ -> {
        Tag $$1 = (Tag)p_128336_.convert((DynamicOps)NbtOps.f_128958_).getValue();
        if ($$1 instanceof CompoundTag) {
            return DataResult.success((Object)((CompoundTag)$$1));
        }
        return DataResult.error((String)("Not a compound tag: " + $$1));
    }, p_128412_ -> new Dynamic((DynamicOps)NbtOps.f_128958_, p_128412_));
    private static final int f_177851_ = 384;
    private static final int f_177852_ = 256;
    public static final TagType<CompoundTag> f_128326_ = new TagType.VariableSize<CompoundTag>(){

        @Override
        public CompoundTag m_7300_(DataInput p_128485_, int p_128486_, NbtAccounter p_128487_) throws IOException {
            byte $$4;
            p_128487_.m_6800_(384L);
            if (p_128486_ > 512) {
                throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
            }
            HashMap $$3 = Maps.newHashMap();
            while (($$4 = CompoundTag.m_128420_(p_128485_, p_128487_)) != 0) {
                String $$5 = CompoundTag.m_128432_(p_128485_, p_128487_);
                p_128487_.m_6800_(224 + 16 * $$5.length());
                Tag $$6 = CompoundTag.m_128413_(TagTypes.m_129397_($$4), $$5, p_128485_, p_128486_ + 1, p_128487_);
                if ($$3.put($$5, $$6) == null) continue;
                p_128487_.m_6800_(288L);
            }
            return new CompoundTag($$3);
        }

        @Override
        public StreamTagVisitor.ValueResult m_196511_(DataInput p_197446_, StreamTagVisitor p_197447_) throws IOException {
            byte $$2;
            block13: while (($$2 = p_197446_.readByte()) != 0) {
                TagType<?> $$3 = TagTypes.m_129397_($$2);
                switch (p_197447_.m_196214_($$3)) {
                    case HALT: {
                        return StreamTagVisitor.ValueResult.HALT;
                    }
                    case BREAK: {
                        StringTag.m_197563_(p_197446_);
                        $$3.m_196159_(p_197446_);
                        break block13;
                    }
                    case SKIP: {
                        StringTag.m_197563_(p_197446_);
                        $$3.m_196159_(p_197446_);
                        continue block13;
                    }
                    default: {
                        String $$4 = p_197446_.readUTF();
                        switch (p_197447_.m_196425_($$3, $$4)) {
                            case HALT: {
                                return StreamTagVisitor.ValueResult.HALT;
                            }
                            case BREAK: {
                                $$3.m_196159_(p_197446_);
                                break block13;
                            }
                            case SKIP: {
                                $$3.m_196159_(p_197446_);
                                continue block13;
                            }
                        }
                        switch ($$3.m_196511_(p_197446_, p_197447_)) {
                            case HALT: {
                                return StreamTagVisitor.ValueResult.HALT;
                            }
                        }
                        continue block13;
                    }
                }
            }
            if ($$2 != 0) {
                while (($$2 = p_197446_.readByte()) != 0) {
                    StringTag.m_197563_(p_197446_);
                    TagTypes.m_129397_($$2).m_196159_(p_197446_);
                }
            }
            return p_197447_.m_196527_();
        }

        @Override
        public void m_196159_(DataInput p_197444_) throws IOException {
            byte $$1;
            while (($$1 = p_197444_.readByte()) != 0) {
                StringTag.m_197563_(p_197444_);
                TagTypes.m_129397_($$1).m_196159_(p_197444_);
            }
        }

        @Override
        public String m_5987_() {
            return "COMPOUND";
        }

        @Override
        public String m_5986_() {
            return "TAG_Compound";
        }

        @Override
        public /* synthetic */ Tag m_7300_(DataInput dataInput, int n, NbtAccounter nbtAccounter) throws IOException {
            return this.m_7300_(dataInput, n, nbtAccounter);
        }
    };
    private final Map<String, Tag> f_128329_;

    protected CompoundTag(Map<String, Tag> p_128333_) {
        this.f_128329_ = p_128333_;
    }

    public CompoundTag() {
        this(Maps.newHashMap());
    }

    @Override
    public void m_6434_(DataOutput p_128341_) throws IOException {
        for (String $$1 : this.f_128329_.keySet()) {
            Tag $$2 = this.f_128329_.get($$1);
            CompoundTag.m_128368_($$1, $$2, p_128341_);
        }
        p_128341_.writeByte(0);
    }

    public Set<String> m_128431_() {
        return this.f_128329_.keySet();
    }

    @Override
    public byte m_7060_() {
        return 10;
    }

    public TagType<CompoundTag> m_6458_() {
        return f_128326_;
    }

    public int m_128440_() {
        return this.f_128329_.size();
    }

    @Nullable
    public Tag m_128365_(String p_128366_, Tag p_128367_) {
        return this.f_128329_.put(p_128366_, p_128367_);
    }

    public void m_128344_(String p_128345_, byte p_128346_) {
        this.f_128329_.put(p_128345_, ByteTag.m_128266_(p_128346_));
    }

    public void m_128376_(String p_128377_, short p_128378_) {
        this.f_128329_.put(p_128377_, ShortTag.m_129258_(p_128378_));
    }

    public void m_128405_(String p_128406_, int p_128407_) {
        this.f_128329_.put(p_128406_, IntTag.m_128679_(p_128407_));
    }

    public void m_128356_(String p_128357_, long p_128358_) {
        this.f_128329_.put(p_128357_, LongTag.m_128882_(p_128358_));
    }

    public void m_128362_(String p_128363_, UUID p_128364_) {
        this.f_128329_.put(p_128363_, NbtUtils.m_129226_(p_128364_));
    }

    public UUID m_128342_(String p_128343_) {
        return NbtUtils.m_129233_(this.m_128423_(p_128343_));
    }

    public boolean m_128403_(String p_128404_) {
        Tag $$1 = this.m_128423_(p_128404_);
        return $$1 != null && $$1.m_6458_() == IntArrayTag.f_128599_ && ((IntArrayTag)$$1).m_128648_().length == 4;
    }

    public void m_128350_(String p_128351_, float p_128352_) {
        this.f_128329_.put(p_128351_, FloatTag.m_128566_(p_128352_));
    }

    public void m_128347_(String p_128348_, double p_128349_) {
        this.f_128329_.put(p_128348_, DoubleTag.m_128500_(p_128349_));
    }

    public void m_128359_(String p_128360_, String p_128361_) {
        this.f_128329_.put(p_128360_, StringTag.m_129297_(p_128361_));
    }

    public void m_128382_(String p_128383_, byte[] p_128384_) {
        this.f_128329_.put(p_128383_, new ByteArrayTag(p_128384_));
    }

    public void m_177853_(String p_177854_, List<Byte> p_177855_) {
        this.f_128329_.put(p_177854_, new ByteArrayTag(p_177855_));
    }

    public void m_128385_(String p_128386_, int[] p_128387_) {
        this.f_128329_.put(p_128386_, new IntArrayTag(p_128387_));
    }

    public void m_128408_(String p_128409_, List<Integer> p_128410_) {
        this.f_128329_.put(p_128409_, new IntArrayTag(p_128410_));
    }

    public void m_128388_(String p_128389_, long[] p_128390_) {
        this.f_128329_.put(p_128389_, new LongArrayTag(p_128390_));
    }

    public void m_128428_(String p_128429_, List<Long> p_128430_) {
        this.f_128329_.put(p_128429_, new LongArrayTag(p_128430_));
    }

    public void m_128379_(String p_128380_, boolean p_128381_) {
        this.f_128329_.put(p_128380_, ByteTag.m_128273_(p_128381_));
    }

    @Nullable
    public Tag m_128423_(String p_128424_) {
        return this.f_128329_.get(p_128424_);
    }

    public byte m_128435_(String p_128436_) {
        Tag $$1 = this.f_128329_.get(p_128436_);
        if ($$1 == null) {
            return 0;
        }
        return $$1.m_7060_();
    }

    public boolean m_128441_(String p_128442_) {
        return this.f_128329_.containsKey(p_128442_);
    }

    public boolean m_128425_(String p_128426_, int p_128427_) {
        byte $$2 = this.m_128435_(p_128426_);
        if ($$2 == p_128427_) {
            return true;
        }
        if (p_128427_ == 99) {
            return $$2 == 1 || $$2 == 2 || $$2 == 3 || $$2 == 4 || $$2 == 5 || $$2 == 6;
        }
        return false;
    }

    public byte m_128445_(String p_128446_) {
        try {
            if (this.m_128425_(p_128446_, 99)) {
                return ((NumericTag)this.f_128329_.get(p_128446_)).m_7063_();
            }
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        return 0;
    }

    public short m_128448_(String p_128449_) {
        try {
            if (this.m_128425_(p_128449_, 99)) {
                return ((NumericTag)this.f_128329_.get(p_128449_)).m_7053_();
            }
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        return 0;
    }

    public int m_128451_(String p_128452_) {
        try {
            if (this.m_128425_(p_128452_, 99)) {
                return ((NumericTag)this.f_128329_.get(p_128452_)).m_7047_();
            }
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        return 0;
    }

    public long m_128454_(String p_128455_) {
        try {
            if (this.m_128425_(p_128455_, 99)) {
                return ((NumericTag)this.f_128329_.get(p_128455_)).m_7046_();
            }
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        return 0L;
    }

    public float m_128457_(String p_128458_) {
        try {
            if (this.m_128425_(p_128458_, 99)) {
                return ((NumericTag)this.f_128329_.get(p_128458_)).m_7057_();
            }
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        return 0.0f;
    }

    public double m_128459_(String p_128460_) {
        try {
            if (this.m_128425_(p_128460_, 99)) {
                return ((NumericTag)this.f_128329_.get(p_128460_)).m_7061_();
            }
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        return 0.0;
    }

    public String m_128461_(String p_128462_) {
        try {
            if (this.m_128425_(p_128462_, 8)) {
                return this.f_128329_.get(p_128462_).m_7916_();
            }
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        return "";
    }

    public byte[] m_128463_(String p_128464_) {
        try {
            if (this.m_128425_(p_128464_, 7)) {
                return ((ByteArrayTag)this.f_128329_.get(p_128464_)).m_128227_();
            }
        }
        catch (ClassCastException $$1) {
            throw new ReportedException(this.m_128372_(p_128464_, ByteArrayTag.f_128185_, $$1));
        }
        return new byte[0];
    }

    public int[] m_128465_(String p_128466_) {
        try {
            if (this.m_128425_(p_128466_, 11)) {
                return ((IntArrayTag)this.f_128329_.get(p_128466_)).m_128648_();
            }
        }
        catch (ClassCastException $$1) {
            throw new ReportedException(this.m_128372_(p_128466_, IntArrayTag.f_128599_, $$1));
        }
        return new int[0];
    }

    public long[] m_128467_(String p_128468_) {
        try {
            if (this.m_128425_(p_128468_, 12)) {
                return ((LongArrayTag)this.f_128329_.get(p_128468_)).m_128851_();
            }
        }
        catch (ClassCastException $$1) {
            throw new ReportedException(this.m_128372_(p_128468_, LongArrayTag.f_128800_, $$1));
        }
        return new long[0];
    }

    public CompoundTag m_128469_(String p_128470_) {
        try {
            if (this.m_128425_(p_128470_, 10)) {
                return (CompoundTag)this.f_128329_.get(p_128470_);
            }
        }
        catch (ClassCastException $$1) {
            throw new ReportedException(this.m_128372_(p_128470_, f_128326_, $$1));
        }
        return new CompoundTag();
    }

    public ListTag m_128437_(String p_128438_, int p_128439_) {
        try {
            if (this.m_128435_(p_128438_) == 9) {
                ListTag $$2 = (ListTag)this.f_128329_.get(p_128438_);
                if ($$2.isEmpty() || $$2.m_7264_() == p_128439_) {
                    return $$2;
                }
                return new ListTag();
            }
        }
        catch (ClassCastException $$3) {
            throw new ReportedException(this.m_128372_(p_128438_, ListTag.f_128714_, $$3));
        }
        return new ListTag();
    }

    public boolean m_128471_(String p_128472_) {
        return this.m_128445_(p_128472_) != 0;
    }

    public void m_128473_(String p_128474_) {
        this.f_128329_.remove(p_128474_);
    }

    @Override
    public String toString() {
        return this.m_7916_();
    }

    public boolean m_128456_() {
        return this.f_128329_.isEmpty();
    }

    private CrashReport m_128372_(String p_128373_, TagType<?> p_128374_, ClassCastException p_128375_) {
        CrashReport $$3 = CrashReport.m_127521_(p_128375_, "Reading NBT data");
        CrashReportCategory $$4 = $$3.m_127516_("Corrupt NBT tag", 1);
        $$4.m_128165_("Tag type found", () -> this.f_128329_.get(p_128373_).m_6458_().m_5987_());
        $$4.m_128165_("Tag type expected", p_128374_::m_5987_);
        $$4.m_128159_("Tag name", p_128373_);
        return $$3;
    }

    @Override
    public CompoundTag m_6426_() {
        HashMap $$0 = Maps.newHashMap((Map)Maps.transformValues(this.f_128329_, Tag::m_6426_));
        return new CompoundTag($$0);
    }

    public boolean equals(Object p_128444_) {
        if (this == p_128444_) {
            return true;
        }
        return p_128444_ instanceof CompoundTag && Objects.equals(this.f_128329_, ((CompoundTag)p_128444_).f_128329_);
    }

    public int hashCode() {
        return this.f_128329_.hashCode();
    }

    private static void m_128368_(String p_128369_, Tag p_128370_, DataOutput p_128371_) throws IOException {
        p_128371_.writeByte(p_128370_.m_7060_());
        if (p_128370_.m_7060_() == 0) {
            return;
        }
        p_128371_.writeUTF(p_128369_);
        p_128370_.m_6434_(p_128371_);
    }

    static byte m_128420_(DataInput p_128421_, NbtAccounter p_128422_) throws IOException {
        return p_128421_.readByte();
    }

    static String m_128432_(DataInput p_128433_, NbtAccounter p_128434_) throws IOException {
        return p_128433_.readUTF();
    }

    static Tag m_128413_(TagType<?> p_128414_, String p_128415_, DataInput p_128416_, int p_128417_, NbtAccounter p_128418_) {
        try {
            return p_128414_.m_7300_(p_128416_, p_128417_, p_128418_);
        }
        catch (IOException $$5) {
            CrashReport $$6 = CrashReport.m_127521_($$5, "Loading NBT data");
            CrashReportCategory $$7 = $$6.m_127514_("NBT Tag");
            $$7.m_128159_("Tag name", p_128415_);
            $$7.m_128159_("Tag type", p_128414_.m_5987_());
            throw new ReportedException($$6);
        }
    }

    public CompoundTag m_128391_(CompoundTag p_128392_) {
        for (String $$1 : p_128392_.f_128329_.keySet()) {
            Tag $$2 = p_128392_.f_128329_.get($$1);
            if ($$2.m_7060_() == 10) {
                if (this.m_128425_($$1, 10)) {
                    CompoundTag $$3 = this.m_128469_($$1);
                    $$3.m_128391_((CompoundTag)$$2);
                    continue;
                }
                this.m_128365_($$1, $$2.m_6426_());
                continue;
            }
            this.m_128365_($$1, $$2.m_6426_());
        }
        return this;
    }

    @Override
    public void m_142327_(TagVisitor p_177857_) {
        p_177857_.m_142303_(this);
    }

    protected Map<String, Tag> m_128450_() {
        return Collections.unmodifiableMap(this.f_128329_);
    }

    @Override
    public StreamTagVisitor.ValueResult m_196533_(StreamTagVisitor p_197442_) {
        block14: for (Map.Entry<String, Tag> $$1 : this.f_128329_.entrySet()) {
            Tag $$2 = $$1.getValue();
            TagType<?> $$3 = $$2.m_6458_();
            StreamTagVisitor.EntryResult $$4 = p_197442_.m_196214_($$3);
            switch ($$4) {
                case HALT: {
                    return StreamTagVisitor.ValueResult.HALT;
                }
                case BREAK: {
                    return p_197442_.m_196527_();
                }
                case SKIP: {
                    continue block14;
                }
            }
            $$4 = p_197442_.m_196425_($$3, $$1.getKey());
            switch ($$4) {
                case HALT: {
                    return StreamTagVisitor.ValueResult.HALT;
                }
                case BREAK: {
                    return p_197442_.m_196527_();
                }
                case SKIP: {
                    continue block14;
                }
            }
            StreamTagVisitor.ValueResult $$5 = $$2.m_196533_(p_197442_);
            switch ($$5) {
                case HALT: {
                    return StreamTagVisitor.ValueResult.HALT;
                }
                case BREAK: {
                    return p_197442_.m_196527_();
                }
            }
        }
        return p_197442_.m_196527_();
    }

    @Override
    public /* synthetic */ Tag m_6426_() {
        return this.m_6426_();
    }
}


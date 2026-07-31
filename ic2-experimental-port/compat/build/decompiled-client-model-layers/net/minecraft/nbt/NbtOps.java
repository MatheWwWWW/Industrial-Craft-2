/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.PeekingIterator
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.MapLike
 *  com.mojang.serialization.RecordBuilder
 *  com.mojang.serialization.RecordBuilder$AbstractStringBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.nbt;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.PeekingIterator;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

public class NbtOps
implements DynamicOps<Tag> {
    public static final NbtOps f_128958_ = new NbtOps();

    protected NbtOps() {
    }

    public Tag empty() {
        return EndTag.f_128534_;
    }

    public <U> U convertTo(DynamicOps<U> p_128980_, Tag p_128981_) {
        switch (p_128981_.m_7060_()) {
            case 0: {
                return (U)p_128980_.empty();
            }
            case 1: {
                return (U)p_128980_.createByte(((NumericTag)p_128981_).m_7063_());
            }
            case 2: {
                return (U)p_128980_.createShort(((NumericTag)p_128981_).m_7053_());
            }
            case 3: {
                return (U)p_128980_.createInt(((NumericTag)p_128981_).m_7047_());
            }
            case 4: {
                return (U)p_128980_.createLong(((NumericTag)p_128981_).m_7046_());
            }
            case 5: {
                return (U)p_128980_.createFloat(((NumericTag)p_128981_).m_7057_());
            }
            case 6: {
                return (U)p_128980_.createDouble(((NumericTag)p_128981_).m_7061_());
            }
            case 7: {
                return (U)p_128980_.createByteList(ByteBuffer.wrap(((ByteArrayTag)p_128981_).m_128227_()));
            }
            case 8: {
                return (U)p_128980_.createString(p_128981_.m_7916_());
            }
            case 9: {
                return (U)this.convertList(p_128980_, p_128981_);
            }
            case 10: {
                return (U)this.convertMap(p_128980_, p_128981_);
            }
            case 11: {
                return (U)p_128980_.createIntList(Arrays.stream(((IntArrayTag)p_128981_).m_128648_()));
            }
            case 12: {
                return (U)p_128980_.createLongList(Arrays.stream(((LongArrayTag)p_128981_).m_128851_()));
            }
        }
        throw new IllegalStateException("Unknown tag type: " + p_128981_);
    }

    public DataResult<Number> getNumberValue(Tag p_129030_) {
        if (p_129030_ instanceof NumericTag) {
            return DataResult.success((Object)((NumericTag)p_129030_).m_8103_());
        }
        return DataResult.error((String)"Not a number");
    }

    public Tag createNumeric(Number p_128983_) {
        return DoubleTag.m_128500_(p_128983_.doubleValue());
    }

    public Tag createByte(byte p_128963_) {
        return ByteTag.m_128266_(p_128963_);
    }

    public Tag createShort(short p_129048_) {
        return ShortTag.m_129258_(p_129048_);
    }

    public Tag createInt(int p_128976_) {
        return IntTag.m_128679_(p_128976_);
    }

    public Tag createLong(long p_128978_) {
        return LongTag.m_128882_(p_128978_);
    }

    public Tag createFloat(float p_128974_) {
        return FloatTag.m_128566_(p_128974_);
    }

    public Tag createDouble(double p_128972_) {
        return DoubleTag.m_128500_(p_128972_);
    }

    public Tag createBoolean(boolean p_129050_) {
        return ByteTag.m_128273_(p_129050_);
    }

    public DataResult<String> getStringValue(Tag p_129061_) {
        if (p_129061_ instanceof StringTag) {
            return DataResult.success((Object)p_129061_.m_7916_());
        }
        return DataResult.error((String)"Not a string");
    }

    public Tag createString(String p_128985_) {
        return StringTag.m_129297_(p_128985_);
    }

    private static CollectionTag<?> m_128964_(byte p_128965_, byte p_128966_) {
        if (NbtOps.m_128967_(p_128965_, p_128966_, (byte)4)) {
            return new LongArrayTag(new long[0]);
        }
        if (NbtOps.m_128967_(p_128965_, p_128966_, (byte)1)) {
            return new ByteArrayTag(new byte[0]);
        }
        if (NbtOps.m_128967_(p_128965_, p_128966_, (byte)3)) {
            return new IntArrayTag(new int[0]);
        }
        return new ListTag();
    }

    private static boolean m_128967_(byte p_128968_, byte p_128969_, byte p_128970_) {
        return p_128968_ == p_128970_ && (p_128969_ == p_128970_ || p_128969_ == 0);
    }

    private static <T extends Tag> void m_129012_(CollectionTag<T> p_129013_, Tag p_129014_, Tag p_129015_) {
        if (p_129014_ instanceof CollectionTag) {
            CollectionTag $$3 = (CollectionTag)p_129014_;
            $$3.forEach(p_129064_ -> p_129013_.add(p_129064_));
        }
        p_129013_.add(p_129015_);
    }

    private static <T extends Tag> void m_129008_(CollectionTag<T> p_129009_, Tag p_129010_, List<Tag> p_129011_) {
        if (p_129010_ instanceof CollectionTag) {
            CollectionTag $$3 = (CollectionTag)p_129010_;
            $$3.forEach(p_129055_ -> p_129009_.add(p_129055_));
        }
        p_129011_.forEach(p_129007_ -> p_129009_.add(p_129007_));
    }

    public DataResult<Tag> mergeToList(Tag p_129041_, Tag p_129042_) {
        if (!(p_129041_ instanceof CollectionTag) && !(p_129041_ instanceof EndTag)) {
            return DataResult.error((String)("mergeToList called with not a list: " + p_129041_), (Object)p_129041_);
        }
        CollectionTag<?> $$2 = NbtOps.m_128964_(p_129041_ instanceof CollectionTag ? ((CollectionTag)p_129041_).m_7264_() : (byte)0, p_129042_.m_7060_());
        NbtOps.m_129012_($$2, p_129041_, p_129042_);
        return DataResult.success($$2);
    }

    public DataResult<Tag> mergeToList(Tag p_129038_, List<Tag> p_129039_) {
        if (!(p_129038_ instanceof CollectionTag) && !(p_129038_ instanceof EndTag)) {
            return DataResult.error((String)("mergeToList called with not a list: " + p_129038_), (Object)p_129038_);
        }
        CollectionTag<?> $$2 = NbtOps.m_128964_(p_129038_ instanceof CollectionTag ? ((CollectionTag)p_129038_).m_7264_() : (byte)0, p_129039_.stream().findFirst().map(Tag::m_7060_).orElse((byte)0));
        NbtOps.m_129008_($$2, p_129038_, p_129039_);
        return DataResult.success($$2);
    }

    public DataResult<Tag> mergeToMap(Tag p_129044_, Tag p_129045_, Tag p_129046_) {
        if (!(p_129044_ instanceof CompoundTag) && !(p_129044_ instanceof EndTag)) {
            return DataResult.error((String)("mergeToMap called with not a map: " + p_129044_), (Object)p_129044_);
        }
        if (!(p_129045_ instanceof StringTag)) {
            return DataResult.error((String)("key is not a string: " + p_129045_), (Object)p_129044_);
        }
        CompoundTag $$3 = new CompoundTag();
        if (p_129044_ instanceof CompoundTag) {
            CompoundTag $$4 = (CompoundTag)p_129044_;
            $$4.m_128431_().forEach(p_129068_ -> $$3.m_128365_((String)p_129068_, $$4.m_128423_((String)p_129068_)));
        }
        $$3.m_128365_(p_129045_.m_7916_(), p_129046_);
        return DataResult.success((Object)$$3);
    }

    public DataResult<Tag> mergeToMap(Tag p_129032_, MapLike<Tag> p_129033_) {
        if (!(p_129032_ instanceof CompoundTag) && !(p_129032_ instanceof EndTag)) {
            return DataResult.error((String)("mergeToMap called with not a map: " + p_129032_), (Object)p_129032_);
        }
        CompoundTag $$2 = new CompoundTag();
        if (p_129032_ instanceof CompoundTag) {
            CompoundTag $$3 = (CompoundTag)p_129032_;
            $$3.m_128431_().forEach(p_129059_ -> $$2.m_128365_((String)p_129059_, $$3.m_128423_((String)p_129059_)));
        }
        ArrayList $$4 = Lists.newArrayList();
        p_129033_.entries().forEach(p_128994_ -> {
            Tag $$3 = (Tag)p_128994_.getFirst();
            if (!($$3 instanceof StringTag)) {
                $$4.add($$3);
                return;
            }
            $$2.m_128365_($$3.m_7916_(), (Tag)p_128994_.getSecond());
        });
        if (!$$4.isEmpty()) {
            return DataResult.error((String)("some keys are not strings: " + $$4), (Object)$$2);
        }
        return DataResult.success((Object)$$2);
    }

    public DataResult<Stream<Pair<Tag, Tag>>> getMapValues(Tag p_129070_) {
        if (!(p_129070_ instanceof CompoundTag)) {
            return DataResult.error((String)("Not a map: " + p_129070_));
        }
        CompoundTag $$1 = (CompoundTag)p_129070_;
        return DataResult.success($$1.m_128431_().stream().map(p_129021_ -> Pair.of((Object)this.createString((String)p_129021_), (Object)$$1.m_128423_((String)p_129021_))));
    }

    public DataResult<Consumer<BiConsumer<Tag, Tag>>> getMapEntries(Tag p_129103_) {
        if (!(p_129103_ instanceof CompoundTag)) {
            return DataResult.error((String)("Not a map: " + p_129103_));
        }
        CompoundTag $$1 = (CompoundTag)p_129103_;
        return DataResult.success(p_129024_ -> $$1.m_128431_().forEach(p_178006_ -> p_129024_.accept(this.createString((String)p_178006_), $$1.m_128423_((String)p_178006_))));
    }

    public DataResult<MapLike<Tag>> getMap(Tag p_129105_) {
        if (!(p_129105_ instanceof CompoundTag)) {
            return DataResult.error((String)("Not a map: " + p_129105_));
        }
        final CompoundTag $$1 = (CompoundTag)p_129105_;
        return DataResult.success((Object)new MapLike<Tag>(){

            @Nullable
            public Tag get(Tag p_129174_) {
                return $$1.m_128423_(p_129174_.m_7916_());
            }

            @Nullable
            public Tag get(String p_129169_) {
                return $$1.m_128423_(p_129169_);
            }

            public Stream<Pair<Tag, Tag>> entries() {
                return $$1.m_128431_().stream().map(p_129172_ -> Pair.of((Object)NbtOps.this.createString((String)p_129172_), (Object)$$1.m_128423_((String)p_129172_)));
            }

            public String toString() {
                return "MapLike[" + $$1 + "]";
            }

            @Nullable
            public /* synthetic */ Object get(String string) {
                return this.get(string);
            }

            @Nullable
            public /* synthetic */ Object get(Object object) {
                return this.get((Tag)object);
            }
        });
    }

    public Tag createMap(Stream<Pair<Tag, Tag>> p_129004_) {
        CompoundTag $$1 = new CompoundTag();
        p_129004_.forEach(p_129018_ -> $$1.m_128365_(((Tag)p_129018_.getFirst()).m_7916_(), (Tag)p_129018_.getSecond()));
        return $$1;
    }

    public DataResult<Stream<Tag>> getStream(Tag p_129108_) {
        if (p_129108_ instanceof CollectionTag) {
            return DataResult.success(((CollectionTag)p_129108_).stream().map(p_129158_ -> p_129158_));
        }
        return DataResult.error((String)"Not a list");
    }

    public DataResult<Consumer<Consumer<Tag>>> getList(Tag p_129110_) {
        if (p_129110_ instanceof CollectionTag) {
            CollectionTag $$1 = (CollectionTag)p_129110_;
            return DataResult.success($$1::forEach);
        }
        return DataResult.error((String)("Not a list: " + p_129110_));
    }

    public DataResult<ByteBuffer> getByteBuffer(Tag p_129132_) {
        if (p_129132_ instanceof ByteArrayTag) {
            return DataResult.success((Object)ByteBuffer.wrap(((ByteArrayTag)p_129132_).m_128227_()));
        }
        return super.getByteBuffer((Object)p_129132_);
    }

    public Tag createByteList(ByteBuffer p_128990_) {
        return new ByteArrayTag(DataFixUtils.toArray((ByteBuffer)p_128990_));
    }

    public DataResult<IntStream> getIntStream(Tag p_129134_) {
        if (p_129134_ instanceof IntArrayTag) {
            return DataResult.success((Object)Arrays.stream(((IntArrayTag)p_129134_).m_128648_()));
        }
        return super.getIntStream((Object)p_129134_);
    }

    public Tag createIntList(IntStream p_129000_) {
        return new IntArrayTag(p_129000_.toArray());
    }

    public DataResult<LongStream> getLongStream(Tag p_129136_) {
        if (p_129136_ instanceof LongArrayTag) {
            return DataResult.success((Object)Arrays.stream(((LongArrayTag)p_129136_).m_128851_()));
        }
        return super.getLongStream((Object)p_129136_);
    }

    public Tag createLongList(LongStream p_129002_) {
        return new LongArrayTag(p_129002_.toArray());
    }

    public Tag createList(Stream<Tag> p_129052_) {
        PeekingIterator $$1 = Iterators.peekingIterator(p_129052_.iterator());
        if (!$$1.hasNext()) {
            return new ListTag();
        }
        Tag $$2 = (Tag)$$1.peek();
        if ($$2 instanceof ByteTag) {
            ArrayList $$3 = Lists.newArrayList((Iterator)Iterators.transform((Iterator)$$1, p_129142_ -> ((ByteTag)p_129142_).m_7063_()));
            return new ByteArrayTag($$3);
        }
        if ($$2 instanceof IntTag) {
            ArrayList $$4 = Lists.newArrayList((Iterator)Iterators.transform((Iterator)$$1, p_129140_ -> ((IntTag)p_129140_).m_7047_()));
            return new IntArrayTag($$4);
        }
        if ($$2 instanceof LongTag) {
            ArrayList $$5 = Lists.newArrayList((Iterator)Iterators.transform((Iterator)$$1, p_129138_ -> ((LongTag)p_129138_).m_7046_()));
            return new LongArrayTag($$5);
        }
        ListTag $$6 = new ListTag();
        while ($$1.hasNext()) {
            Tag $$7 = (Tag)$$1.next();
            if ($$7 instanceof EndTag) continue;
            $$6.add($$7);
        }
        return $$6;
    }

    public Tag remove(Tag p_129035_, String p_129036_) {
        if (p_129035_ instanceof CompoundTag) {
            CompoundTag $$2 = (CompoundTag)p_129035_;
            CompoundTag $$3 = new CompoundTag();
            $$2.m_128431_().stream().filter(p_128988_ -> !Objects.equals(p_128988_, p_129036_)).forEach(p_129028_ -> $$3.m_128365_((String)p_129028_, $$2.m_128423_((String)p_129028_)));
            return $$3;
        }
        return p_129035_;
    }

    public String toString() {
        return "NBT";
    }

    public RecordBuilder<Tag> mapBuilder() {
        return new NbtRecordBuilder();
    }

    public /* synthetic */ Object remove(Object object, String string) {
        return this.remove((Tag)object, string);
    }

    public /* synthetic */ Object createLongList(LongStream longStream) {
        return this.createLongList(longStream);
    }

    public /* synthetic */ DataResult getLongStream(Object object) {
        return this.getLongStream((Tag)object);
    }

    public /* synthetic */ Object createIntList(IntStream intStream) {
        return this.createIntList(intStream);
    }

    public /* synthetic */ DataResult getIntStream(Object object) {
        return this.getIntStream((Tag)object);
    }

    public /* synthetic */ Object createByteList(ByteBuffer byteBuffer) {
        return this.createByteList(byteBuffer);
    }

    public /* synthetic */ DataResult getByteBuffer(Object object) {
        return this.getByteBuffer((Tag)object);
    }

    public /* synthetic */ Object createList(Stream stream) {
        return this.createList(stream);
    }

    public /* synthetic */ DataResult getList(Object object) {
        return this.getList((Tag)object);
    }

    public /* synthetic */ DataResult getStream(Object object) {
        return this.getStream((Tag)object);
    }

    public /* synthetic */ DataResult getMap(Object object) {
        return this.getMap((Tag)object);
    }

    public /* synthetic */ Object createMap(Stream stream) {
        return this.createMap(stream);
    }

    public /* synthetic */ DataResult getMapEntries(Object object) {
        return this.getMapEntries((Tag)object);
    }

    public /* synthetic */ DataResult getMapValues(Object object) {
        return this.getMapValues((Tag)object);
    }

    public /* synthetic */ DataResult mergeToMap(Object object, MapLike mapLike) {
        return this.mergeToMap((Tag)object, (MapLike<Tag>)mapLike);
    }

    public /* synthetic */ DataResult mergeToMap(Object object, Object object2, Object object3) {
        return this.mergeToMap((Tag)object, (Tag)object2, (Tag)object3);
    }

    public /* synthetic */ DataResult mergeToList(Object object, List list) {
        return this.mergeToList((Tag)object, (List<Tag>)list);
    }

    public /* synthetic */ DataResult mergeToList(Object object, Object object2) {
        return this.mergeToList((Tag)object, (Tag)object2);
    }

    public /* synthetic */ Object createString(String string) {
        return this.createString(string);
    }

    public /* synthetic */ DataResult getStringValue(Object object) {
        return this.getStringValue((Tag)object);
    }

    public /* synthetic */ Object createBoolean(boolean bl) {
        return this.createBoolean(bl);
    }

    public /* synthetic */ Object createDouble(double d) {
        return this.createDouble(d);
    }

    public /* synthetic */ Object createFloat(float f) {
        return this.createFloat(f);
    }

    public /* synthetic */ Object createLong(long l) {
        return this.createLong(l);
    }

    public /* synthetic */ Object createInt(int n) {
        return this.createInt(n);
    }

    public /* synthetic */ Object createShort(short s) {
        return this.createShort(s);
    }

    public /* synthetic */ Object createByte(byte by) {
        return this.createByte(by);
    }

    public /* synthetic */ Object createNumeric(Number number) {
        return this.createNumeric(number);
    }

    public /* synthetic */ DataResult getNumberValue(Object object) {
        return this.getNumberValue((Tag)object);
    }

    public /* synthetic */ Object convertTo(DynamicOps dynamicOps, Object object) {
        return this.convertTo(dynamicOps, (Tag)object);
    }

    public /* synthetic */ Object empty() {
        return this.empty();
    }

    class NbtRecordBuilder
    extends RecordBuilder.AbstractStringBuilder<Tag, CompoundTag> {
        protected NbtRecordBuilder() {
            super((DynamicOps)NbtOps.this);
        }

        protected CompoundTag initBuilder() {
            return new CompoundTag();
        }

        protected CompoundTag append(String p_129186_, Tag p_129187_, CompoundTag p_129188_) {
            p_129188_.m_128365_(p_129186_, p_129187_);
            return p_129188_;
        }

        protected DataResult<Tag> build(CompoundTag p_129190_, Tag p_129191_) {
            if (p_129191_ == null || p_129191_ == EndTag.f_128534_) {
                return DataResult.success((Object)p_129190_);
            }
            if (p_129191_ instanceof CompoundTag) {
                CompoundTag $$2 = new CompoundTag(Maps.newHashMap(((CompoundTag)p_129191_).m_128450_()));
                for (Map.Entry<String, Tag> $$3 : p_129190_.m_128450_().entrySet()) {
                    $$2.m_128365_($$3.getKey(), $$3.getValue());
                }
                return DataResult.success((Object)$$2);
            }
            return DataResult.error((String)("mergeToMap called with not a map: " + p_129191_), (Object)p_129191_);
        }

        protected /* synthetic */ Object append(String string, Object object, Object object2) {
            return this.append(string, (Tag)object, (CompoundTag)object2);
        }

        protected /* synthetic */ DataResult build(Object object, Object object2) {
            return this.build((CompoundTag)object, (Tag)object2);
        }

        protected /* synthetic */ Object initBuilder() {
            return this.initBuilder();
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.ListBuilder
 *  com.mojang.serialization.ListBuilder$Builder
 *  com.mojang.serialization.MapLike
 *  com.mojang.serialization.RecordBuilder
 *  com.mojang.serialization.RecordBuilder$MapBuilder
 */
package net.minecraft.resources;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public abstract class DelegatingOps<T>
implements DynamicOps<T> {
    protected final DynamicOps<T> f_135465_;

    protected DelegatingOps(DynamicOps<T> p_135467_) {
        this.f_135465_ = p_135467_;
    }

    public T empty() {
        return (T)this.f_135465_.empty();
    }

    public <U> U convertTo(DynamicOps<U> p_135470_, T p_135471_) {
        return (U)this.f_135465_.convertTo(p_135470_, p_135471_);
    }

    public DataResult<Number> getNumberValue(T p_135518_) {
        return this.f_135465_.getNumberValue(p_135518_);
    }

    public T createNumeric(Number p_135495_) {
        return (T)this.f_135465_.createNumeric(p_135495_);
    }

    public T createByte(byte p_135475_) {
        return (T)this.f_135465_.createByte(p_135475_);
    }

    public T createShort(short p_135497_) {
        return (T)this.f_135465_.createShort(p_135497_);
    }

    public T createInt(int p_135483_) {
        return (T)this.f_135465_.createInt(p_135483_);
    }

    public T createLong(long p_135489_) {
        return (T)this.f_135465_.createLong(p_135489_);
    }

    public T createFloat(float p_135481_) {
        return (T)this.f_135465_.createFloat(p_135481_);
    }

    public T createDouble(double p_135479_) {
        return (T)this.f_135465_.createDouble(p_135479_);
    }

    public DataResult<Boolean> getBooleanValue(T p_135502_) {
        return this.f_135465_.getBooleanValue(p_135502_);
    }

    public T createBoolean(boolean p_135473_) {
        return (T)this.f_135465_.createBoolean(p_135473_);
    }

    public DataResult<String> getStringValue(T p_135522_) {
        return this.f_135465_.getStringValue(p_135522_);
    }

    public T createString(String p_135499_) {
        return (T)this.f_135465_.createString(p_135499_);
    }

    public DataResult<T> mergeToList(T p_135526_, T p_135527_) {
        return this.f_135465_.mergeToList(p_135526_, p_135527_);
    }

    public DataResult<T> mergeToList(T p_135529_, List<T> p_135530_) {
        return this.f_135465_.mergeToList(p_135529_, p_135530_);
    }

    public DataResult<T> mergeToMap(T p_135535_, T p_135536_, T p_135537_) {
        return this.f_135465_.mergeToMap(p_135535_, p_135536_, p_135537_);
    }

    public DataResult<T> mergeToMap(T p_135532_, MapLike<T> p_135533_) {
        return this.f_135465_.mergeToMap(p_135532_, p_135533_);
    }

    public DataResult<Stream<Pair<T, T>>> getMapValues(T p_135516_) {
        return this.f_135465_.getMapValues(p_135516_);
    }

    public DataResult<Consumer<BiConsumer<T, T>>> getMapEntries(T p_135514_) {
        return this.f_135465_.getMapEntries(p_135514_);
    }

    public T createMap(Stream<Pair<T, T>> p_135493_) {
        return (T)this.f_135465_.createMap(p_135493_);
    }

    public DataResult<MapLike<T>> getMap(T p_135512_) {
        return this.f_135465_.getMap(p_135512_);
    }

    public DataResult<Stream<T>> getStream(T p_135520_) {
        return this.f_135465_.getStream(p_135520_);
    }

    public DataResult<Consumer<Consumer<T>>> getList(T p_135508_) {
        return this.f_135465_.getList(p_135508_);
    }

    public T createList(Stream<T> p_135487_) {
        return (T)this.f_135465_.createList(p_135487_);
    }

    public DataResult<ByteBuffer> getByteBuffer(T p_135504_) {
        return this.f_135465_.getByteBuffer(p_135504_);
    }

    public T createByteList(ByteBuffer p_135477_) {
        return (T)this.f_135465_.createByteList(p_135477_);
    }

    public DataResult<IntStream> getIntStream(T p_135506_) {
        return this.f_135465_.getIntStream(p_135506_);
    }

    public T createIntList(IntStream p_135485_) {
        return (T)this.f_135465_.createIntList(p_135485_);
    }

    public DataResult<LongStream> getLongStream(T p_135510_) {
        return this.f_135465_.getLongStream(p_135510_);
    }

    public T createLongList(LongStream p_135491_) {
        return (T)this.f_135465_.createLongList(p_135491_);
    }

    public T remove(T p_135539_, String p_135540_) {
        return (T)this.f_135465_.remove(p_135539_, p_135540_);
    }

    public boolean compressMaps() {
        return this.f_135465_.compressMaps();
    }

    public ListBuilder<T> listBuilder() {
        return new ListBuilder.Builder((DynamicOps)this);
    }

    public RecordBuilder<T> mapBuilder() {
        return new RecordBuilder.MapBuilder((DynamicOps)this);
    }
}


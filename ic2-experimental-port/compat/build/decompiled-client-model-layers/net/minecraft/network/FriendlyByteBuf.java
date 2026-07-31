/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.Property
 *  com.mojang.authlib.properties.PropertyMap
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufAllocator
 *  io.netty.buffer.ByteBufInputStream
 *  io.netty.buffer.ByteBufOutputStream
 *  io.netty.handler.codec.DecoderException
 *  io.netty.handler.codec.EncoderException
 *  io.netty.util.ByteProcessor
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  javax.annotation.Nullable
 */
package net.minecraft.network;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.time.Instant;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.IdMap;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class FriendlyByteBuf
extends ByteBuf {
    private static final int f_178335_ = 5;
    private static final int f_178336_ = 10;
    private static final int f_178337_ = 0x200000;
    private final ByteBuf f_130049_;
    public static final short f_178333_ = Short.MAX_VALUE;
    public static final int f_178334_ = 262144;
    private static final int f_236798_ = 256;
    private static final int f_236799_ = 256;
    private static final int f_236800_ = 512;

    public FriendlyByteBuf(ByteBuf p_130051_) {
        this.f_130049_ = p_130051_;
    }

    public static int m_130053_(int p_130054_) {
        for (int $$1 = 1; $$1 < 5; ++$$1) {
            if ((p_130054_ & -1 << $$1 * 7) != 0) continue;
            return $$1;
        }
        return 5;
    }

    public static int m_178339_(long p_178340_) {
        for (int $$1 = 1; $$1 < 10; ++$$1) {
            if ((p_178340_ & -1L << $$1 * 7) != 0L) continue;
            return $$1;
        }
        return 10;
    }

    public <T> T m_130057_(Codec<T> p_130058_) {
        CompoundTag $$1 = this.m_130261_();
        DataResult $$2 = p_130058_.parse((DynamicOps)NbtOps.f_128958_, (Object)$$1);
        $$2.error().ifPresent(p_178380_ -> {
            throw new EncoderException("Failed to decode: " + p_178380_.message() + " " + $$1);
        });
        return $$2.result().get();
    }

    public <T> void m_130059_(Codec<T> p_130060_, T p_130061_) {
        DataResult $$2 = p_130060_.encodeStart((DynamicOps)NbtOps.f_128958_, p_130061_);
        $$2.error().ifPresent(p_178349_ -> {
            throw new EncoderException("Failed to encode: " + p_178349_.message() + " " + p_130061_);
        });
        this.m_130079_((CompoundTag)$$2.result().get());
    }

    public <T> void m_236818_(IdMap<T> p_236819_, T p_236820_) {
        int $$2 = p_236819_.m_7447_(p_236820_);
        if ($$2 == -1) {
            throw new IllegalArgumentException("Can't find id for '" + p_236820_ + "' in map " + p_236819_);
        }
        this.m_130130_($$2);
    }

    @Nullable
    public <T> T m_236816_(IdMap<T> p_236817_) {
        int $$1 = this.m_130242_();
        return p_236817_.m_7942_($$1);
    }

    public static <T> IntFunction<T> m_182695_(IntFunction<T> p_182696_, int p_182697_) {
        return p_182686_ -> {
            if (p_182686_ > p_182697_) {
                throw new DecoderException("Value " + p_182686_ + " is larger than limit " + p_182697_);
            }
            return p_182696_.apply(p_182686_);
        };
    }

    public <T, C extends Collection<T>> C m_236838_(IntFunction<C> p_236839_, Reader<T> p_236840_) {
        int $$2 = this.m_130242_();
        Collection $$3 = (Collection)p_236839_.apply($$2);
        for (int $$4 = 0; $$4 < $$2; ++$$4) {
            $$3.add(p_236840_.apply(this));
        }
        return (C)$$3;
    }

    public <T> void m_236828_(Collection<T> p_236829_, Writer<T> p_236830_) {
        this.m_130130_(p_236829_.size());
        for (T $$2 : p_236829_) {
            p_236830_.accept(this, $$2);
        }
    }

    public <T> List<T> m_236845_(Reader<T> p_236846_) {
        return this.m_236838_(Lists::newArrayListWithCapacity, p_236846_);
    }

    public IntList m_178338_() {
        int $$0 = this.m_130242_();
        IntArrayList $$1 = new IntArrayList();
        for (int $$2 = 0; $$2 < $$0; ++$$2) {
            $$1.add(this.m_130242_());
        }
        return $$1;
    }

    public void m_178345_(IntList p_178346_) {
        this.m_130130_(p_178346_.size());
        p_178346_.forEach(this::m_130130_);
    }

    public <K, V, M extends Map<K, V>> M m_236841_(IntFunction<M> p_236842_, Reader<K> p_236843_, Reader<V> p_236844_) {
        int $$3 = this.m_130242_();
        Map $$4 = (Map)p_236842_.apply($$3);
        for (int $$5 = 0; $$5 < $$3; ++$$5) {
            Object $$6 = p_236843_.apply((K)((Object)this));
            Object $$7 = p_236844_.apply((V)((Object)this));
            $$4.put($$6, $$7);
        }
        return (M)$$4;
    }

    public <K, V> Map<K, V> m_236847_(Reader<K> p_236848_, Reader<V> p_236849_) {
        return this.m_236841_(Maps::newHashMapWithExpectedSize, p_236848_, p_236849_);
    }

    public <K, V> void m_236831_(Map<K, V> p_236832_, Writer<K> p_236833_, Writer<V> p_236834_) {
        this.m_130130_(p_236832_.size());
        p_236832_.forEach((p_236856_, p_236857_) -> {
            p_236833_.accept((Object)((Object)this), p_236856_);
            p_236834_.accept((Object)((Object)this), p_236857_);
        });
    }

    public void m_178364_(Consumer<FriendlyByteBuf> p_178365_) {
        int $$1 = this.m_130242_();
        for (int $$2 = 0; $$2 < $$1; ++$$2) {
            p_178365_.accept(this);
        }
    }

    public <T> void m_236835_(Optional<T> p_236836_, Writer<T> p_236837_) {
        if (p_236836_.isPresent()) {
            this.writeBoolean(true);
            p_236837_.accept(this, p_236836_.get());
        } else {
            this.writeBoolean(false);
        }
    }

    public <T> Optional<T> m_236860_(Reader<T> p_236861_) {
        if (this.readBoolean()) {
            return Optional.of(p_236861_.apply(this));
        }
        return Optional.empty();
    }

    @Nullable
    public <T> T m_236868_(Reader<T> p_236869_) {
        if (this.readBoolean()) {
            return (T)p_236869_.apply(this);
        }
        return null;
    }

    public <T> void m_236821_(@Nullable T p_236822_, Writer<T> p_236823_) {
        if (p_236822_ != null) {
            this.writeBoolean(true);
            p_236823_.accept(this, p_236822_);
        } else {
            this.writeBoolean(false);
        }
    }

    public <L, R> void m_236810_(Either<L, R> p_236811_, Writer<L> p_236812_, Writer<R> p_236813_) {
        p_236811_.ifLeft(p_236867_ -> {
            this.writeBoolean(true);
            p_236812_.accept((Object)((Object)this), p_236867_);
        }).ifRight(p_236852_ -> {
            this.writeBoolean(false);
            p_236813_.accept((Object)((Object)this), p_236852_);
        });
    }

    public <L, R> Either<L, R> m_236862_(Reader<L> p_236863_, Reader<R> p_236864_) {
        if (this.readBoolean()) {
            return Either.left(p_236863_.apply((L)((Object)this)));
        }
        return Either.right(p_236864_.apply((R)((Object)this)));
    }

    public byte[] m_130052_() {
        return this.m_130101_(this.readableBytes());
    }

    public FriendlyByteBuf m_130087_(byte[] p_130088_) {
        this.m_130130_(p_130088_.length);
        this.writeBytes(p_130088_);
        return this;
    }

    public byte[] m_130101_(int p_130102_) {
        int $$1 = this.m_130242_();
        if ($$1 > p_130102_) {
            throw new DecoderException("ByteArray with size " + $$1 + " is bigger than allowed " + p_130102_);
        }
        byte[] $$2 = new byte[$$1];
        this.readBytes($$2);
        return $$2;
    }

    public FriendlyByteBuf m_130089_(int[] p_130090_) {
        this.m_130130_(p_130090_.length);
        for (int $$1 : p_130090_) {
            this.m_130130_($$1);
        }
        return this;
    }

    public int[] m_130100_() {
        return this.m_130116_(this.readableBytes());
    }

    public int[] m_130116_(int p_130117_) {
        int $$1 = this.m_130242_();
        if ($$1 > p_130117_) {
            throw new DecoderException("VarIntArray with size " + $$1 + " is bigger than allowed " + p_130117_);
        }
        int[] $$2 = new int[$$1];
        for (int $$3 = 0; $$3 < $$2.length; ++$$3) {
            $$2[$$3] = this.m_130242_();
        }
        return $$2;
    }

    public FriendlyByteBuf m_130091_(long[] p_130092_) {
        this.m_130130_(p_130092_.length);
        for (long $$1 : p_130092_) {
            this.writeLong($$1);
        }
        return this;
    }

    public long[] m_178381_() {
        return this.m_130105_(null);
    }

    public long[] m_130105_(@Nullable long[] p_130106_) {
        return this.m_130093_(p_130106_, this.readableBytes() / 8);
    }

    public long[] m_130093_(@Nullable long[] p_130094_, int p_130095_) {
        int $$2 = this.m_130242_();
        if (p_130094_ == null || p_130094_.length != $$2) {
            if ($$2 > p_130095_) {
                throw new DecoderException("LongArray with size " + $$2 + " is bigger than allowed " + p_130095_);
            }
            p_130094_ = new long[$$2];
        }
        for (int $$3 = 0; $$3 < p_130094_.length; ++$$3) {
            p_130094_[$$3] = this.readLong();
        }
        return p_130094_;
    }

    @VisibleForTesting
    public byte[] m_178382_() {
        int $$0 = this.writerIndex();
        byte[] $$1 = new byte[$$0];
        this.getBytes(0, $$1);
        return $$1;
    }

    public BlockPos m_130135_() {
        return BlockPos.m_122022_(this.readLong());
    }

    public FriendlyByteBuf m_130064_(BlockPos p_130065_) {
        this.writeLong(p_130065_.m_121878_());
        return this;
    }

    public ChunkPos m_178383_() {
        return new ChunkPos(this.readLong());
    }

    public FriendlyByteBuf m_178341_(ChunkPos p_178342_) {
        this.writeLong(p_178342_.m_45588_());
        return this;
    }

    public SectionPos m_130157_() {
        return SectionPos.m_123184_(this.readLong());
    }

    public FriendlyByteBuf m_178343_(SectionPos p_178344_) {
        this.writeLong(p_178344_.m_123252_());
        return this;
    }

    public GlobalPos m_236872_() {
        ResourceKey<Level> $$0 = this.m_236801_(Registry.f_122819_);
        BlockPos $$1 = this.m_130135_();
        return GlobalPos.m_122643_($$0, $$1);
    }

    public void m_236814_(GlobalPos p_236815_) {
        this.m_236858_(p_236815_.m_122640_());
        this.m_130064_(p_236815_.m_122646_());
    }

    public Component m_130238_() {
        MutableComponent $$0 = Component.Serializer.m_130701_(this.m_130136_(262144));
        if ($$0 == null) {
            throw new DecoderException("Received unexpected null component");
        }
        return $$0;
    }

    public FriendlyByteBuf m_130083_(Component p_130084_) {
        return this.m_130072_(Component.Serializer.m_130703_(p_130084_), 262144);
    }

    public <T extends Enum<T>> T m_130066_(Class<T> p_130067_) {
        return (T)((Enum[])p_130067_.getEnumConstants())[this.m_130242_()];
    }

    public FriendlyByteBuf m_130068_(Enum<?> p_130069_) {
        return this.m_130130_(p_130069_.ordinal());
    }

    public int m_130242_() {
        byte $$2;
        int $$0 = 0;
        int $$1 = 0;
        do {
            $$2 = this.readByte();
            $$0 |= ($$2 & 0x7F) << $$1++ * 7;
            if ($$1 <= 5) continue;
            throw new RuntimeException("VarInt too big");
        } while (($$2 & 0x80) == 128);
        return $$0;
    }

    public long m_130258_() {
        byte $$2;
        long $$0 = 0L;
        int $$1 = 0;
        do {
            $$2 = this.readByte();
            $$0 |= (long)($$2 & 0x7F) << $$1++ * 7;
            if ($$1 <= 10) continue;
            throw new RuntimeException("VarLong too big");
        } while (($$2 & 0x80) == 128);
        return $$0;
    }

    public FriendlyByteBuf m_130077_(UUID p_130078_) {
        this.writeLong(p_130078_.getMostSignificantBits());
        this.writeLong(p_130078_.getLeastSignificantBits());
        return this;
    }

    public UUID m_130259_() {
        return new UUID(this.readLong(), this.readLong());
    }

    public FriendlyByteBuf m_130130_(int p_130131_) {
        while (true) {
            if ((p_130131_ & 0xFFFFFF80) == 0) {
                this.writeByte(p_130131_);
                return this;
            }
            this.writeByte(p_130131_ & 0x7F | 0x80);
            p_130131_ >>>= 7;
        }
    }

    public FriendlyByteBuf m_130103_(long p_130104_) {
        while (true) {
            if ((p_130104_ & 0xFFFFFFFFFFFFFF80L) == 0L) {
                this.writeByte((int)p_130104_);
                return this;
            }
            this.writeByte((int)(p_130104_ & 0x7FL) | 0x80);
            p_130104_ >>>= 7;
        }
    }

    public FriendlyByteBuf m_130079_(@Nullable CompoundTag p_130080_) {
        if (p_130080_ == null) {
            this.writeByte(0);
        } else {
            try {
                NbtIo.m_128941_(p_130080_, (DataOutput)new ByteBufOutputStream((ByteBuf)this));
            }
            catch (IOException $$1) {
                throw new EncoderException((Throwable)$$1);
            }
        }
        return this;
    }

    @Nullable
    public CompoundTag m_130260_() {
        return this.m_130081_(new NbtAccounter(0x200000L));
    }

    @Nullable
    public CompoundTag m_130261_() {
        return this.m_130081_(NbtAccounter.f_128917_);
    }

    @Nullable
    public CompoundTag m_130081_(NbtAccounter p_130082_) {
        int $$1 = this.readerIndex();
        byte $$2 = this.readByte();
        if ($$2 == 0) {
            return null;
        }
        this.readerIndex($$1);
        try {
            return NbtIo.m_128934_((DataInput)new ByteBufInputStream((ByteBuf)this), p_130082_);
        }
        catch (IOException $$3) {
            throw new EncoderException((Throwable)$$3);
        }
    }

    public FriendlyByteBuf m_130055_(ItemStack p_130056_) {
        if (p_130056_.m_41619_()) {
            this.writeBoolean(false);
        } else {
            this.writeBoolean(true);
            Item $$1 = p_130056_.m_41720_();
            this.m_236818_(Registry.f_122827_, $$1);
            this.writeByte(p_130056_.m_41613_());
            CompoundTag $$2 = null;
            if ($$1.m_41465_() || $$1.m_41468_()) {
                $$2 = p_130056_.m_41783_();
            }
            this.m_130079_($$2);
        }
        return this;
    }

    public ItemStack m_130267_() {
        if (!this.readBoolean()) {
            return ItemStack.f_41583_;
        }
        Item $$0 = this.m_236816_(Registry.f_122827_);
        byte $$1 = this.readByte();
        ItemStack $$2 = new ItemStack($$0, (int)$$1);
        $$2.m_41751_(this.m_130260_());
        return $$2;
    }

    public String m_130277_() {
        return this.m_130136_(Short.MAX_VALUE);
    }

    public String m_130136_(int p_130137_) {
        int $$1 = FriendlyByteBuf.m_236870_(p_130137_);
        int $$2 = this.m_130242_();
        if ($$2 > $$1) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + $$2 + " > " + $$1 + ")");
        }
        if ($$2 < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        String $$3 = this.toString(this.readerIndex(), $$2, StandardCharsets.UTF_8);
        this.readerIndex(this.readerIndex() + $$2);
        if ($$3.length() > p_130137_) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + $$3.length() + " > " + p_130137_ + ")");
        }
        return $$3;
    }

    public FriendlyByteBuf m_130070_(String p_130071_) {
        return this.m_130072_(p_130071_, Short.MAX_VALUE);
    }

    public FriendlyByteBuf m_130072_(String p_130073_, int p_130074_) {
        int $$3;
        if (p_130073_.length() > p_130074_) {
            throw new EncoderException("String too big (was " + p_130073_.length() + " characters, max " + p_130074_ + ")");
        }
        byte[] $$2 = p_130073_.getBytes(StandardCharsets.UTF_8);
        if ($$2.length > ($$3 = FriendlyByteBuf.m_236870_(p_130074_))) {
            throw new EncoderException("String too big (was " + $$2.length + " bytes encoded, max " + $$3 + ")");
        }
        this.m_130130_($$2.length);
        this.writeBytes($$2);
        return this;
    }

    private static int m_236870_(int p_236871_) {
        return p_236871_ * 3;
    }

    public ResourceLocation m_130281_() {
        return new ResourceLocation(this.m_130136_(Short.MAX_VALUE));
    }

    public FriendlyByteBuf m_130085_(ResourceLocation p_130086_) {
        this.m_130070_(p_130086_.toString());
        return this;
    }

    public <T> ResourceKey<T> m_236801_(ResourceKey<? extends Registry<T>> p_236802_) {
        ResourceLocation $$1 = this.m_130281_();
        return ResourceKey.m_135785_(p_236802_, $$1);
    }

    public void m_236858_(ResourceKey<?> p_236859_) {
        this.m_130085_(p_236859_.m_135782_());
    }

    public Date m_130282_() {
        return new Date(this.readLong());
    }

    public FriendlyByteBuf m_130075_(Date p_130076_) {
        this.writeLong(p_130076_.getTime());
        return this;
    }

    public Instant m_236873_() {
        return Instant.ofEpochMilli(this.readLong());
    }

    public void m_236826_(Instant p_236827_) {
        this.writeLong(p_236827_.toEpochMilli());
    }

    public PublicKey m_236874_() {
        try {
            return Crypt.m_13600_(this.m_130101_(512));
        }
        catch (CryptException $$0) {
            throw new DecoderException("Malformed public key bytes", (Throwable)$$0);
        }
    }

    public FriendlyByteBuf m_236824_(PublicKey p_236825_) {
        this.m_130087_(p_236825_.getEncoded());
        return this;
    }

    public BlockHitResult m_130283_() {
        BlockPos $$0 = this.m_130135_();
        Direction $$1 = this.m_130066_(Direction.class);
        float $$2 = this.readFloat();
        float $$3 = this.readFloat();
        float $$4 = this.readFloat();
        boolean $$5 = this.readBoolean();
        return new BlockHitResult(new Vec3((double)$$0.m_123341_() + (double)$$2, (double)$$0.m_123342_() + (double)$$3, (double)$$0.m_123343_() + (double)$$4), $$1, $$0, $$5);
    }

    public void m_130062_(BlockHitResult p_130063_) {
        BlockPos $$1 = p_130063_.m_82425_();
        this.m_130064_($$1);
        this.m_130068_(p_130063_.m_82434_());
        Vec3 $$2 = p_130063_.m_82450_();
        this.writeFloat((float)($$2.f_82479_ - (double)$$1.m_123341_()));
        this.writeFloat((float)($$2.f_82480_ - (double)$$1.m_123342_()));
        this.writeFloat((float)($$2.f_82481_ - (double)$$1.m_123343_()));
        this.writeBoolean(p_130063_.m_82436_());
    }

    public BitSet m_178384_() {
        return BitSet.valueOf(this.m_178381_());
    }

    public void m_178350_(BitSet p_178351_) {
        this.m_130091_(p_178351_.toLongArray());
    }

    public GameProfile m_236875_() {
        UUID $$0 = this.m_130259_();
        String $$1 = this.m_130136_(16);
        GameProfile $$2 = new GameProfile($$0, $$1);
        PropertyMap $$3 = $$2.getProperties();
        this.m_178364_(p_236809_ -> {
            Property $$2 = this.m_236876_();
            $$3.put((Object)$$2.getName(), (Object)$$2);
        });
        return $$2;
    }

    public void m_236803_(GameProfile p_236804_) {
        this.m_130077_(p_236804_.getId());
        this.m_130070_(p_236804_.getName());
        this.m_236828_(p_236804_.getProperties().values(), FriendlyByteBuf::m_236805_);
    }

    public Property m_236876_() {
        String $$0 = this.m_130277_();
        String $$1 = this.m_130277_();
        if (this.readBoolean()) {
            String $$2 = this.m_130277_();
            return new Property($$0, $$1, $$2);
        }
        return new Property($$0, $$1);
    }

    public void m_236805_(Property p_236806_) {
        this.m_130070_(p_236806_.getName());
        this.m_130070_(p_236806_.getValue());
        if (p_236806_.hasSignature()) {
            this.writeBoolean(true);
            this.m_130070_(p_236806_.getSignature());
        } else {
            this.writeBoolean(false);
        }
    }

    public int capacity() {
        return this.f_130049_.capacity();
    }

    public ByteBuf capacity(int p_130120_) {
        return this.f_130049_.capacity(p_130120_);
    }

    public int maxCapacity() {
        return this.f_130049_.maxCapacity();
    }

    public ByteBufAllocator alloc() {
        return this.f_130049_.alloc();
    }

    public ByteOrder order() {
        return this.f_130049_.order();
    }

    public ByteBuf order(ByteOrder p_130280_) {
        return this.f_130049_.order(p_130280_);
    }

    public ByteBuf unwrap() {
        return this.f_130049_.unwrap();
    }

    public boolean isDirect() {
        return this.f_130049_.isDirect();
    }

    public boolean isReadOnly() {
        return this.f_130049_.isReadOnly();
    }

    public ByteBuf asReadOnly() {
        return this.f_130049_.asReadOnly();
    }

    public int readerIndex() {
        return this.f_130049_.readerIndex();
    }

    public ByteBuf readerIndex(int p_130343_) {
        return this.f_130049_.readerIndex(p_130343_);
    }

    public int writerIndex() {
        return this.f_130049_.writerIndex();
    }

    public ByteBuf writerIndex(int p_130527_) {
        return this.f_130049_.writerIndex(p_130527_);
    }

    public ByteBuf setIndex(int p_130417_, int p_130418_) {
        return this.f_130049_.setIndex(p_130417_, p_130418_);
    }

    public int readableBytes() {
        return this.f_130049_.readableBytes();
    }

    public int writableBytes() {
        return this.f_130049_.writableBytes();
    }

    public int maxWritableBytes() {
        return this.f_130049_.maxWritableBytes();
    }

    public boolean isReadable() {
        return this.f_130049_.isReadable();
    }

    public boolean isReadable(int p_130254_) {
        return this.f_130049_.isReadable(p_130254_);
    }

    public boolean isWritable() {
        return this.f_130049_.isWritable();
    }

    public boolean isWritable(int p_130257_) {
        return this.f_130049_.isWritable(p_130257_);
    }

    public ByteBuf clear() {
        return this.f_130049_.clear();
    }

    public ByteBuf markReaderIndex() {
        return this.f_130049_.markReaderIndex();
    }

    public ByteBuf resetReaderIndex() {
        return this.f_130049_.resetReaderIndex();
    }

    public ByteBuf markWriterIndex() {
        return this.f_130049_.markWriterIndex();
    }

    public ByteBuf resetWriterIndex() {
        return this.f_130049_.resetWriterIndex();
    }

    public ByteBuf discardReadBytes() {
        return this.f_130049_.discardReadBytes();
    }

    public ByteBuf discardSomeReadBytes() {
        return this.f_130049_.discardSomeReadBytes();
    }

    public ByteBuf ensureWritable(int p_130139_) {
        return this.f_130049_.ensureWritable(p_130139_);
    }

    public int ensureWritable(int p_130141_, boolean p_130142_) {
        return this.f_130049_.ensureWritable(p_130141_, p_130142_);
    }

    public boolean getBoolean(int p_130159_) {
        return this.f_130049_.getBoolean(p_130159_);
    }

    public byte getByte(int p_130161_) {
        return this.f_130049_.getByte(p_130161_);
    }

    public short getUnsignedByte(int p_130225_) {
        return this.f_130049_.getUnsignedByte(p_130225_);
    }

    public short getShort(int p_130221_) {
        return this.f_130049_.getShort(p_130221_);
    }

    public short getShortLE(int p_130223_) {
        return this.f_130049_.getShortLE(p_130223_);
    }

    public int getUnsignedShort(int p_130235_) {
        return this.f_130049_.getUnsignedShort(p_130235_);
    }

    public int getUnsignedShortLE(int p_130237_) {
        return this.f_130049_.getUnsignedShortLE(p_130237_);
    }

    public int getMedium(int p_130217_) {
        return this.f_130049_.getMedium(p_130217_);
    }

    public int getMediumLE(int p_130219_) {
        return this.f_130049_.getMediumLE(p_130219_);
    }

    public int getUnsignedMedium(int p_130231_) {
        return this.f_130049_.getUnsignedMedium(p_130231_);
    }

    public int getUnsignedMediumLE(int p_130233_) {
        return this.f_130049_.getUnsignedMediumLE(p_130233_);
    }

    public int getInt(int p_130209_) {
        return this.f_130049_.getInt(p_130209_);
    }

    public int getIntLE(int p_130211_) {
        return this.f_130049_.getIntLE(p_130211_);
    }

    public long getUnsignedInt(int p_130227_) {
        return this.f_130049_.getUnsignedInt(p_130227_);
    }

    public long getUnsignedIntLE(int p_130229_) {
        return this.f_130049_.getUnsignedIntLE(p_130229_);
    }

    public long getLong(int p_130213_) {
        return this.f_130049_.getLong(p_130213_);
    }

    public long getLongLE(int p_130215_) {
        return this.f_130049_.getLongLE(p_130215_);
    }

    public char getChar(int p_130199_) {
        return this.f_130049_.getChar(p_130199_);
    }

    public float getFloat(int p_130207_) {
        return this.f_130049_.getFloat(p_130207_);
    }

    public double getDouble(int p_130205_) {
        return this.f_130049_.getDouble(p_130205_);
    }

    public ByteBuf getBytes(int p_130163_, ByteBuf p_130164_) {
        return this.f_130049_.getBytes(p_130163_, p_130164_);
    }

    public ByteBuf getBytes(int p_130166_, ByteBuf p_130167_, int p_130168_) {
        return this.f_130049_.getBytes(p_130166_, p_130167_, p_130168_);
    }

    public ByteBuf getBytes(int p_130170_, ByteBuf p_130171_, int p_130172_, int p_130173_) {
        return this.f_130049_.getBytes(p_130170_, p_130171_, p_130172_, p_130173_);
    }

    public ByteBuf getBytes(int p_130191_, byte[] p_130192_) {
        return this.f_130049_.getBytes(p_130191_, p_130192_);
    }

    public ByteBuf getBytes(int p_130194_, byte[] p_130195_, int p_130196_, int p_130197_) {
        return this.f_130049_.getBytes(p_130194_, p_130195_, p_130196_, p_130197_);
    }

    public ByteBuf getBytes(int p_130179_, ByteBuffer p_130180_) {
        return this.f_130049_.getBytes(p_130179_, p_130180_);
    }

    public ByteBuf getBytes(int p_130175_, OutputStream p_130176_, int p_130177_) throws IOException {
        return this.f_130049_.getBytes(p_130175_, p_130176_, p_130177_);
    }

    public int getBytes(int p_130187_, GatheringByteChannel p_130188_, int p_130189_) throws IOException {
        return this.f_130049_.getBytes(p_130187_, p_130188_, p_130189_);
    }

    public int getBytes(int p_130182_, FileChannel p_130183_, long p_130184_, int p_130185_) throws IOException {
        return this.f_130049_.getBytes(p_130182_, p_130183_, p_130184_, p_130185_);
    }

    public CharSequence getCharSequence(int p_130201_, int p_130202_, Charset p_130203_) {
        return this.f_130049_.getCharSequence(p_130201_, p_130202_, p_130203_);
    }

    public ByteBuf setBoolean(int p_130362_, boolean p_130363_) {
        return this.f_130049_.setBoolean(p_130362_, p_130363_);
    }

    public ByteBuf setByte(int p_130365_, int p_130366_) {
        return this.f_130049_.setByte(p_130365_, p_130366_);
    }

    public ByteBuf setShort(int p_130438_, int p_130439_) {
        return this.f_130049_.setShort(p_130438_, p_130439_);
    }

    public ByteBuf setShortLE(int p_130441_, int p_130442_) {
        return this.f_130049_.setShortLE(p_130441_, p_130442_);
    }

    public ByteBuf setMedium(int p_130432_, int p_130433_) {
        return this.f_130049_.setMedium(p_130432_, p_130433_);
    }

    public ByteBuf setMediumLE(int p_130435_, int p_130436_) {
        return this.f_130049_.setMediumLE(p_130435_, p_130436_);
    }

    public ByteBuf setInt(int p_130420_, int p_130421_) {
        return this.f_130049_.setInt(p_130420_, p_130421_);
    }

    public ByteBuf setIntLE(int p_130423_, int p_130424_) {
        return this.f_130049_.setIntLE(p_130423_, p_130424_);
    }

    public ByteBuf setLong(int p_130426_, long p_130427_) {
        return this.f_130049_.setLong(p_130426_, p_130427_);
    }

    public ByteBuf setLongLE(int p_130429_, long p_130430_) {
        return this.f_130049_.setLongLE(p_130429_, p_130430_);
    }

    public ByteBuf setChar(int p_130404_, int p_130405_) {
        return this.f_130049_.setChar(p_130404_, p_130405_);
    }

    public ByteBuf setFloat(int p_130414_, float p_130415_) {
        return this.f_130049_.setFloat(p_130414_, p_130415_);
    }

    public ByteBuf setDouble(int p_130411_, double p_130412_) {
        return this.f_130049_.setDouble(p_130411_, p_130412_);
    }

    public ByteBuf setBytes(int p_130368_, ByteBuf p_130369_) {
        return this.f_130049_.setBytes(p_130368_, p_130369_);
    }

    public ByteBuf setBytes(int p_130371_, ByteBuf p_130372_, int p_130373_) {
        return this.f_130049_.setBytes(p_130371_, p_130372_, p_130373_);
    }

    public ByteBuf setBytes(int p_130375_, ByteBuf p_130376_, int p_130377_, int p_130378_) {
        return this.f_130049_.setBytes(p_130375_, p_130376_, p_130377_, p_130378_);
    }

    public ByteBuf setBytes(int p_130396_, byte[] p_130397_) {
        return this.f_130049_.setBytes(p_130396_, p_130397_);
    }

    public ByteBuf setBytes(int p_130399_, byte[] p_130400_, int p_130401_, int p_130402_) {
        return this.f_130049_.setBytes(p_130399_, p_130400_, p_130401_, p_130402_);
    }

    public ByteBuf setBytes(int p_130384_, ByteBuffer p_130385_) {
        return this.f_130049_.setBytes(p_130384_, p_130385_);
    }

    public int setBytes(int p_130380_, InputStream p_130381_, int p_130382_) throws IOException {
        return this.f_130049_.setBytes(p_130380_, p_130381_, p_130382_);
    }

    public int setBytes(int p_130392_, ScatteringByteChannel p_130393_, int p_130394_) throws IOException {
        return this.f_130049_.setBytes(p_130392_, p_130393_, p_130394_);
    }

    public int setBytes(int p_130387_, FileChannel p_130388_, long p_130389_, int p_130390_) throws IOException {
        return this.f_130049_.setBytes(p_130387_, p_130388_, p_130389_, p_130390_);
    }

    public ByteBuf setZero(int p_130444_, int p_130445_) {
        return this.f_130049_.setZero(p_130444_, p_130445_);
    }

    public int setCharSequence(int p_130407_, CharSequence p_130408_, Charset p_130409_) {
        return this.f_130049_.setCharSequence(p_130407_, p_130408_, p_130409_);
    }

    public boolean readBoolean() {
        return this.f_130049_.readBoolean();
    }

    public byte readByte() {
        return this.f_130049_.readByte();
    }

    public short readUnsignedByte() {
        return this.f_130049_.readUnsignedByte();
    }

    public short readShort() {
        return this.f_130049_.readShort();
    }

    public short readShortLE() {
        return this.f_130049_.readShortLE();
    }

    public int readUnsignedShort() {
        return this.f_130049_.readUnsignedShort();
    }

    public int readUnsignedShortLE() {
        return this.f_130049_.readUnsignedShortLE();
    }

    public int readMedium() {
        return this.f_130049_.readMedium();
    }

    public int readMediumLE() {
        return this.f_130049_.readMediumLE();
    }

    public int readUnsignedMedium() {
        return this.f_130049_.readUnsignedMedium();
    }

    public int readUnsignedMediumLE() {
        return this.f_130049_.readUnsignedMediumLE();
    }

    public int readInt() {
        return this.f_130049_.readInt();
    }

    public int readIntLE() {
        return this.f_130049_.readIntLE();
    }

    public long readUnsignedInt() {
        return this.f_130049_.readUnsignedInt();
    }

    public long readUnsignedIntLE() {
        return this.f_130049_.readUnsignedIntLE();
    }

    public long readLong() {
        return this.f_130049_.readLong();
    }

    public long readLongLE() {
        return this.f_130049_.readLongLE();
    }

    public char readChar() {
        return this.f_130049_.readChar();
    }

    public float readFloat() {
        return this.f_130049_.readFloat();
    }

    public double readDouble() {
        return this.f_130049_.readDouble();
    }

    public ByteBuf readBytes(int p_130287_) {
        return this.f_130049_.readBytes(p_130287_);
    }

    public ByteBuf readSlice(int p_130332_) {
        return this.f_130049_.readSlice(p_130332_);
    }

    public ByteBuf readRetainedSlice(int p_130328_) {
        return this.f_130049_.readRetainedSlice(p_130328_);
    }

    public ByteBuf readBytes(ByteBuf p_130289_) {
        return this.f_130049_.readBytes(p_130289_);
    }

    public ByteBuf readBytes(ByteBuf p_130291_, int p_130292_) {
        return this.f_130049_.readBytes(p_130291_, p_130292_);
    }

    public ByteBuf readBytes(ByteBuf p_130294_, int p_130295_, int p_130296_) {
        return this.f_130049_.readBytes(p_130294_, p_130295_, p_130296_);
    }

    public ByteBuf readBytes(byte[] p_130310_) {
        return this.f_130049_.readBytes(p_130310_);
    }

    public ByteBuf readBytes(byte[] p_130312_, int p_130313_, int p_130314_) {
        return this.f_130049_.readBytes(p_130312_, p_130313_, p_130314_);
    }

    public ByteBuf readBytes(ByteBuffer p_130301_) {
        return this.f_130049_.readBytes(p_130301_);
    }

    public ByteBuf readBytes(OutputStream p_130298_, int p_130299_) throws IOException {
        return this.f_130049_.readBytes(p_130298_, p_130299_);
    }

    public int readBytes(GatheringByteChannel p_130307_, int p_130308_) throws IOException {
        return this.f_130049_.readBytes(p_130307_, p_130308_);
    }

    public CharSequence readCharSequence(int p_130317_, Charset p_130318_) {
        return this.f_130049_.readCharSequence(p_130317_, p_130318_);
    }

    public int readBytes(FileChannel p_130303_, long p_130304_, int p_130305_) throws IOException {
        return this.f_130049_.readBytes(p_130303_, p_130304_, p_130305_);
    }

    public ByteBuf skipBytes(int p_130447_) {
        return this.f_130049_.skipBytes(p_130447_);
    }

    public ByteBuf writeBoolean(boolean p_130468_) {
        return this.f_130049_.writeBoolean(p_130468_);
    }

    public ByteBuf writeByte(int p_130470_) {
        return this.f_130049_.writeByte(p_130470_);
    }

    public ByteBuf writeShort(int p_130520_) {
        return this.f_130049_.writeShort(p_130520_);
    }

    public ByteBuf writeShortLE(int p_130522_) {
        return this.f_130049_.writeShortLE(p_130522_);
    }

    public ByteBuf writeMedium(int p_130516_) {
        return this.f_130049_.writeMedium(p_130516_);
    }

    public ByteBuf writeMediumLE(int p_130518_) {
        return this.f_130049_.writeMediumLE(p_130518_);
    }

    public ByteBuf writeInt(int p_130508_) {
        return this.f_130049_.writeInt(p_130508_);
    }

    public ByteBuf writeIntLE(int p_130510_) {
        return this.f_130049_.writeIntLE(p_130510_);
    }

    public ByteBuf writeLong(long p_130512_) {
        return this.f_130049_.writeLong(p_130512_);
    }

    public ByteBuf writeLongLE(long p_130514_) {
        return this.f_130049_.writeLongLE(p_130514_);
    }

    public ByteBuf writeChar(int p_130499_) {
        return this.f_130049_.writeChar(p_130499_);
    }

    public ByteBuf writeFloat(float p_130506_) {
        return this.f_130049_.writeFloat(p_130506_);
    }

    public ByteBuf writeDouble(double p_130504_) {
        return this.f_130049_.writeDouble(p_130504_);
    }

    public ByteBuf writeBytes(ByteBuf p_130472_) {
        return this.f_130049_.writeBytes(p_130472_);
    }

    public ByteBuf writeBytes(ByteBuf p_130474_, int p_130475_) {
        return this.f_130049_.writeBytes(p_130474_, p_130475_);
    }

    public ByteBuf writeBytes(ByteBuf p_130477_, int p_130478_, int p_130479_) {
        return this.f_130049_.writeBytes(p_130477_, p_130478_, p_130479_);
    }

    public ByteBuf writeBytes(byte[] p_130493_) {
        return this.f_130049_.writeBytes(p_130493_);
    }

    public ByteBuf writeBytes(byte[] p_130495_, int p_130496_, int p_130497_) {
        return this.f_130049_.writeBytes(p_130495_, p_130496_, p_130497_);
    }

    public ByteBuf writeBytes(ByteBuffer p_130484_) {
        return this.f_130049_.writeBytes(p_130484_);
    }

    public int writeBytes(InputStream p_130481_, int p_130482_) throws IOException {
        return this.f_130049_.writeBytes(p_130481_, p_130482_);
    }

    public int writeBytes(ScatteringByteChannel p_130490_, int p_130491_) throws IOException {
        return this.f_130049_.writeBytes(p_130490_, p_130491_);
    }

    public int writeBytes(FileChannel p_130486_, long p_130487_, int p_130488_) throws IOException {
        return this.f_130049_.writeBytes(p_130486_, p_130487_, p_130488_);
    }

    public ByteBuf writeZero(int p_130524_) {
        return this.f_130049_.writeZero(p_130524_);
    }

    public int writeCharSequence(CharSequence p_130501_, Charset p_130502_) {
        return this.f_130049_.writeCharSequence(p_130501_, p_130502_);
    }

    public int indexOf(int p_130244_, int p_130245_, byte p_130246_) {
        return this.f_130049_.indexOf(p_130244_, p_130245_, p_130246_);
    }

    public int bytesBefore(byte p_130108_) {
        return this.f_130049_.bytesBefore(p_130108_);
    }

    public int bytesBefore(int p_130110_, byte p_130111_) {
        return this.f_130049_.bytesBefore(p_130110_, p_130111_);
    }

    public int bytesBefore(int p_130113_, int p_130114_, byte p_130115_) {
        return this.f_130049_.bytesBefore(p_130113_, p_130114_, p_130115_);
    }

    public int forEachByte(ByteProcessor p_130150_) {
        return this.f_130049_.forEachByte(p_130150_);
    }

    public int forEachByte(int p_130146_, int p_130147_, ByteProcessor p_130148_) {
        return this.f_130049_.forEachByte(p_130146_, p_130147_, p_130148_);
    }

    public int forEachByteDesc(ByteProcessor p_130156_) {
        return this.f_130049_.forEachByteDesc(p_130156_);
    }

    public int forEachByteDesc(int p_130152_, int p_130153_, ByteProcessor p_130154_) {
        return this.f_130049_.forEachByteDesc(p_130152_, p_130153_, p_130154_);
    }

    public ByteBuf copy() {
        return this.f_130049_.copy();
    }

    public ByteBuf copy(int p_130128_, int p_130129_) {
        return this.f_130049_.copy(p_130128_, p_130129_);
    }

    public ByteBuf slice() {
        return this.f_130049_.slice();
    }

    public ByteBuf retainedSlice() {
        return this.f_130049_.retainedSlice();
    }

    public ByteBuf slice(int p_130450_, int p_130451_) {
        return this.f_130049_.slice(p_130450_, p_130451_);
    }

    public ByteBuf retainedSlice(int p_130359_, int p_130360_) {
        return this.f_130049_.retainedSlice(p_130359_, p_130360_);
    }

    public ByteBuf duplicate() {
        return this.f_130049_.duplicate();
    }

    public ByteBuf retainedDuplicate() {
        return this.f_130049_.retainedDuplicate();
    }

    public int nioBufferCount() {
        return this.f_130049_.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        return this.f_130049_.nioBuffer();
    }

    public ByteBuffer nioBuffer(int p_130270_, int p_130271_) {
        return this.f_130049_.nioBuffer(p_130270_, p_130271_);
    }

    public ByteBuffer internalNioBuffer(int p_130248_, int p_130249_) {
        return this.f_130049_.internalNioBuffer(p_130248_, p_130249_);
    }

    public ByteBuffer[] nioBuffers() {
        return this.f_130049_.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int p_130275_, int p_130276_) {
        return this.f_130049_.nioBuffers(p_130275_, p_130276_);
    }

    public boolean hasArray() {
        return this.f_130049_.hasArray();
    }

    public byte[] array() {
        return this.f_130049_.array();
    }

    public int arrayOffset() {
        return this.f_130049_.arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return this.f_130049_.hasMemoryAddress();
    }

    public long memoryAddress() {
        return this.f_130049_.memoryAddress();
    }

    public String toString(Charset p_130458_) {
        return this.f_130049_.toString(p_130458_);
    }

    public String toString(int p_130454_, int p_130455_, Charset p_130456_) {
        return this.f_130049_.toString(p_130454_, p_130455_, p_130456_);
    }

    public int hashCode() {
        return this.f_130049_.hashCode();
    }

    public boolean equals(Object p_130144_) {
        return this.f_130049_.equals(p_130144_);
    }

    public int compareTo(ByteBuf p_130123_) {
        return this.f_130049_.compareTo(p_130123_);
    }

    public String toString() {
        return this.f_130049_.toString();
    }

    public ByteBuf retain(int p_130353_) {
        return this.f_130049_.retain(p_130353_);
    }

    public ByteBuf retain() {
        return this.f_130049_.retain();
    }

    public ByteBuf touch() {
        return this.f_130049_.touch();
    }

    public ByteBuf touch(Object p_130462_) {
        return this.f_130049_.touch(p_130462_);
    }

    public int refCnt() {
        return this.f_130049_.refCnt();
    }

    public boolean release() {
        return this.f_130049_.release();
    }

    public boolean release(int p_130347_) {
        return this.f_130049_.release(p_130347_);
    }

    @FunctionalInterface
    public static interface Reader<T>
    extends Function<FriendlyByteBuf, T> {
        default public Reader<Optional<T>> m_236879_() {
            return p_236878_ -> p_236878_.m_236860_(this);
        }
    }

    @FunctionalInterface
    public static interface Writer<T>
    extends BiConsumer<FriendlyByteBuf, T> {
        default public Writer<Optional<T>> m_236883_() {
            return (p_236881_, p_236882_) -> p_236881_.m_236835_(p_236882_, this);
        }
    }
}


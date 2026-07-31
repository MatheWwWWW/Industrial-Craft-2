/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;

public class ClientboundLevelChunkPacketData {
    private static final int f_195646_ = 0x200000;
    private final CompoundTag f_195647_;
    private final byte[] f_195648_;
    private final List<BlockEntityInfo> f_195649_;

    public ClientboundLevelChunkPacketData(LevelChunk p_195651_) {
        this.f_195647_ = new CompoundTag();
        for (Map.Entry<Heightmap.Types, Heightmap> entry : p_195651_.m_6890_()) {
            if (!entry.getKey().m_64297_()) continue;
            this.f_195647_.m_128365_(entry.getKey().m_64294_(), new LongArrayTag(entry.getValue().m_64239_()));
        }
        this.f_195648_ = new byte[ClientboundLevelChunkPacketData.m_195664_(p_195651_)];
        ClientboundLevelChunkPacketData.m_195668_(new FriendlyByteBuf(this.m_195679_()), p_195651_);
        this.f_195649_ = Lists.newArrayList();
        for (Map.Entry<Object, Object> entry : p_195651_.m_62954_().entrySet()) {
            this.f_195649_.add(BlockEntityInfo.m_195691_((BlockEntity)entry.getValue()));
        }
    }

    public ClientboundLevelChunkPacketData(FriendlyByteBuf p_195653_, int p_195654_, int p_195655_) {
        this.f_195647_ = p_195653_.m_130260_();
        if (this.f_195647_ == null) {
            throw new RuntimeException("Can't read heightmap in packet for [" + p_195654_ + ", " + p_195655_ + "]");
        }
        int $$3 = p_195653_.m_130242_();
        if ($$3 > 0x200000) {
            throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
        }
        this.f_195648_ = new byte[$$3];
        p_195653_.readBytes(this.f_195648_);
        this.f_195649_ = p_195653_.m_236845_(BlockEntityInfo::new);
    }

    public void m_195666_(FriendlyByteBuf p_195667_) {
        p_195667_.m_130079_(this.f_195647_);
        p_195667_.m_130130_(this.f_195648_.length);
        p_195667_.writeBytes(this.f_195648_);
        p_195667_.m_236828_(this.f_195649_, (p_195672_, p_195673_) -> p_195673_.m_195693_((FriendlyByteBuf)((Object)p_195672_)));
    }

    private static int m_195664_(LevelChunk p_195665_) {
        int $$1 = 0;
        for (LevelChunkSection $$2 : p_195665_.m_7103_()) {
            $$1 += $$2.m_63020_();
        }
        return $$1;
    }

    private ByteBuf m_195679_() {
        ByteBuf $$0 = Unpooled.wrappedBuffer((byte[])this.f_195648_);
        $$0.writerIndex(0);
        return $$0;
    }

    public static void m_195668_(FriendlyByteBuf p_195669_, LevelChunk p_195670_) {
        for (LevelChunkSection $$2 : p_195670_.m_7103_()) {
            $$2.m_63011_(p_195669_);
        }
    }

    public Consumer<BlockEntityTagOutput> m_195657_(int p_195658_, int p_195659_) {
        return p_195663_ -> this.m_195674_((BlockEntityTagOutput)p_195663_, p_195658_, p_195659_);
    }

    private void m_195674_(BlockEntityTagOutput p_195675_, int p_195676_, int p_195677_) {
        int $$3 = 16 * p_195676_;
        int $$4 = 16 * p_195677_;
        BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
        for (BlockEntityInfo $$6 : this.f_195649_) {
            int $$7 = $$3 + SectionPos.m_123207_($$6.f_195680_ >> 4);
            int $$8 = $$4 + SectionPos.m_123207_($$6.f_195680_);
            $$5.m_122178_($$7, $$6.f_195681_, $$8);
            p_195675_.m_195695_($$5, $$6.f_195682_, $$6.f_195683_);
        }
    }

    public FriendlyByteBuf m_195656_() {
        return new FriendlyByteBuf(Unpooled.wrappedBuffer((byte[])this.f_195648_));
    }

    public CompoundTag m_195678_() {
        return this.f_195647_;
    }

    static class BlockEntityInfo {
        final int f_195680_;
        final int f_195681_;
        final BlockEntityType<?> f_195682_;
        @Nullable
        final CompoundTag f_195683_;

        private BlockEntityInfo(int p_195685_, int p_195686_, BlockEntityType<?> p_195687_, @Nullable CompoundTag p_195688_) {
            this.f_195680_ = p_195685_;
            this.f_195681_ = p_195686_;
            this.f_195682_ = p_195687_;
            this.f_195683_ = p_195688_;
        }

        private BlockEntityInfo(FriendlyByteBuf p_195690_) {
            this.f_195680_ = p_195690_.readByte();
            this.f_195681_ = p_195690_.readShort();
            this.f_195682_ = p_195690_.m_236816_(Registry.f_122830_);
            this.f_195683_ = p_195690_.m_130260_();
        }

        void m_195693_(FriendlyByteBuf p_195694_) {
            p_195694_.writeByte(this.f_195680_);
            p_195694_.writeShort(this.f_195681_);
            p_195694_.m_236818_(Registry.f_122830_, this.f_195682_);
            p_195694_.m_130079_(this.f_195683_);
        }

        static BlockEntityInfo m_195691_(BlockEntity p_195692_) {
            CompoundTag $$1 = p_195692_.m_5995_();
            BlockPos $$2 = p_195692_.m_58899_();
            int $$3 = SectionPos.m_123207_($$2.m_123341_()) << 4 | SectionPos.m_123207_($$2.m_123343_());
            return new BlockEntityInfo($$3, $$2.m_123342_(), p_195692_.m_58903_(), $$1.m_128456_() ? null : $$1);
        }
    }

    @FunctionalInterface
    public static interface BlockEntityTagOutput {
        public void m_195695_(BlockPos var1, BlockEntityType<?> var2, @Nullable CompoundTag var3);
    }
}


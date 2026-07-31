/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ClientboundBlockEntityDataPacket
implements Packet<ClientGamePacketListener> {
    private final BlockPos f_131690_;
    private final BlockEntityType<?> f_131691_;
    @Nullable
    private final CompoundTag f_131692_;

    public static ClientboundBlockEntityDataPacket m_195642_(BlockEntity p_195643_, Function<BlockEntity, CompoundTag> p_195644_) {
        return new ClientboundBlockEntityDataPacket(p_195643_.m_58899_(), p_195643_.m_58903_(), p_195644_.apply(p_195643_));
    }

    public static ClientboundBlockEntityDataPacket m_195640_(BlockEntity p_195641_) {
        return ClientboundBlockEntityDataPacket.m_195642_(p_195641_, BlockEntity::m_5995_);
    }

    private ClientboundBlockEntityDataPacket(BlockPos p_195637_, BlockEntityType<?> p_195638_, CompoundTag p_195639_) {
        this.f_131690_ = p_195637_;
        this.f_131691_ = p_195638_;
        this.f_131692_ = p_195639_.m_128456_() ? null : p_195639_;
    }

    public ClientboundBlockEntityDataPacket(FriendlyByteBuf p_178621_) {
        this.f_131690_ = p_178621_.m_130135_();
        this.f_131691_ = p_178621_.m_236816_(Registry.f_122830_);
        this.f_131692_ = p_178621_.m_130260_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131706_) {
        p_131706_.m_130064_(this.f_131690_);
        p_131706_.m_236818_(Registry.f_122830_, this.f_131691_);
        p_131706_.m_130079_(this.f_131692_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131703_) {
        p_131703_.m_7545_(this);
    }

    public BlockPos m_131704_() {
        return this.f_131690_;
    }

    public BlockEntityType<?> m_195645_() {
        return this.f_131691_;
    }

    @Nullable
    public CompoundTag m_131708_() {
        return this.f_131692_;
    }
}


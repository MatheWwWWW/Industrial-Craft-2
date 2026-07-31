/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import java.util.BitSet;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;

public class ClientboundLevelChunkWithLightPacket
implements Packet<ClientGamePacketListener> {
    private final int f_195699_;
    private final int f_195700_;
    private final ClientboundLevelChunkPacketData f_195701_;
    private final ClientboundLightUpdatePacketData f_195702_;

    public ClientboundLevelChunkWithLightPacket(LevelChunk p_195704_, LevelLightEngine p_195705_, @Nullable BitSet p_195706_, @Nullable BitSet p_195707_, boolean p_195708_) {
        ChunkPos $$5 = p_195704_.m_7697_();
        this.f_195699_ = $$5.f_45578_;
        this.f_195700_ = $$5.f_45579_;
        this.f_195701_ = new ClientboundLevelChunkPacketData(p_195704_);
        this.f_195702_ = new ClientboundLightUpdatePacketData($$5, p_195705_, p_195706_, p_195707_, p_195708_);
    }

    public ClientboundLevelChunkWithLightPacket(FriendlyByteBuf p_195710_) {
        this.f_195699_ = p_195710_.readInt();
        this.f_195700_ = p_195710_.readInt();
        this.f_195701_ = new ClientboundLevelChunkPacketData(p_195710_, this.f_195699_, this.f_195700_);
        this.f_195702_ = new ClientboundLightUpdatePacketData(p_195710_, this.f_195699_, this.f_195700_);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_195712_) {
        p_195712_.writeInt(this.f_195699_);
        p_195712_.writeInt(this.f_195700_);
        this.f_195701_.m_195666_(p_195712_);
        this.f_195702_.m_195749_(p_195712_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_195716_) {
        p_195716_.m_183388_(this);
    }

    public int m_195717_() {
        return this.f_195699_;
    }

    public int m_195718_() {
        return this.f_195700_;
    }

    public ClientboundLevelChunkPacketData m_195719_() {
        return this.f_195701_;
    }

    public ClientboundLightUpdatePacketData m_195720_() {
        return this.f_195702_;
    }
}


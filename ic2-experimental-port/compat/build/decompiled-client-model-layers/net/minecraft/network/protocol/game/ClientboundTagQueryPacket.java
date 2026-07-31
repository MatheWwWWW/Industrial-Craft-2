/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundTagQueryPacket
implements Packet<ClientGamePacketListener> {
    private final int f_133493_;
    @Nullable
    private final CompoundTag f_133494_;

    public ClientboundTagQueryPacket(int p_133497_, @Nullable CompoundTag p_133498_) {
        this.f_133493_ = p_133497_;
        this.f_133494_ = p_133498_;
    }

    public ClientboundTagQueryPacket(FriendlyByteBuf p_179433_) {
        this.f_133493_ = p_179433_.m_130242_();
        this.f_133494_ = p_179433_.m_130260_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133508_) {
        p_133508_.m_130130_(this.f_133493_);
        p_133508_.m_130079_(this.f_133494_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133505_) {
        p_133505_.m_6148_(this);
    }

    public int m_133506_() {
        return this.f_133493_;
    }

    @Nullable
    public CompoundTag m_133509_() {
        return this.f_133494_;
    }

    @Override
    public boolean m_6588_() {
        return true;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ClientboundSetCameraPacket
implements Packet<ClientGamePacketListener> {
    private final int f_133055_;

    public ClientboundSetCameraPacket(Entity p_133058_) {
        this.f_133055_ = p_133058_.m_19879_();
    }

    public ClientboundSetCameraPacket(FriendlyByteBuf p_179278_) {
        this.f_133055_ = p_179278_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133068_) {
        p_133068_.m_130130_(this.f_133055_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133066_) {
        p_133066_.m_6447_(this);
    }

    @Nullable
    public Entity m_133059_(Level p_133060_) {
        return p_133060_.m_6815_(this.f_133055_);
    }
}


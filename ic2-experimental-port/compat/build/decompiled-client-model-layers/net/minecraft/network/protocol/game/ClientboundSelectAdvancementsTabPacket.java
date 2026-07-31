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
import net.minecraft.resources.ResourceLocation;

public class ClientboundSelectAdvancementsTabPacket
implements Packet<ClientGamePacketListener> {
    @Nullable
    private final ResourceLocation f_133003_;

    public ClientboundSelectAdvancementsTabPacket(@Nullable ResourceLocation p_133006_) {
        this.f_133003_ = p_133006_;
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133012_) {
        p_133012_.m_7553_(this);
    }

    public ClientboundSelectAdvancementsTabPacket(FriendlyByteBuf p_179198_) {
        this.f_133003_ = (ResourceLocation)p_179198_.m_236868_(FriendlyByteBuf::m_130281_);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133015_) {
        p_133015_.m_236821_(this.f_133003_, FriendlyByteBuf::m_130085_);
    }

    @Nullable
    public ResourceLocation m_133013_() {
        return this.f_133003_;
    }
}


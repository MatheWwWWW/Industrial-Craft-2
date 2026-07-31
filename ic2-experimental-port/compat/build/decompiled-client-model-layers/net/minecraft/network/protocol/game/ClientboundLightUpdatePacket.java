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
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.lighting.LevelLightEngine;

public class ClientboundLightUpdatePacket
implements Packet<ClientGamePacketListener> {
    private final int f_132323_;
    private final int f_132324_;
    private final ClientboundLightUpdatePacketData f_195721_;

    public ClientboundLightUpdatePacket(ChunkPos p_178912_, LevelLightEngine p_178913_, @Nullable BitSet p_178914_, @Nullable BitSet p_178915_, boolean p_178916_) {
        this.f_132323_ = p_178912_.f_45578_;
        this.f_132324_ = p_178912_.f_45579_;
        this.f_195721_ = new ClientboundLightUpdatePacketData(p_178912_, p_178913_, p_178914_, p_178915_, p_178916_);
    }

    public ClientboundLightUpdatePacket(FriendlyByteBuf p_178918_) {
        this.f_132323_ = p_178918_.m_130242_();
        this.f_132324_ = p_178918_.m_130242_();
        this.f_195721_ = new ClientboundLightUpdatePacketData(p_178918_, this.f_132323_, this.f_132324_);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132351_) {
        p_132351_.m_130130_(this.f_132323_);
        p_132351_.m_130130_(this.f_132324_);
        this.f_195721_.m_195749_(p_132351_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132348_) {
        p_132348_.m_183514_(this);
    }

    public int m_132349_() {
        return this.f_132323_;
    }

    public int m_132352_() {
        return this.f_132324_;
    }

    public ClientboundLightUpdatePacketData m_195722_() {
        return this.f_195721_;
    }
}


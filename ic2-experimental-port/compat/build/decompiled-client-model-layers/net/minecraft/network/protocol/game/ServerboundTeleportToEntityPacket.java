/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class ServerboundTeleportToEntityPacket
implements Packet<ServerGamePacketListener> {
    private final UUID f_134677_;

    public ServerboundTeleportToEntityPacket(UUID p_134680_) {
        this.f_134677_ = p_134680_;
    }

    public ServerboundTeleportToEntityPacket(FriendlyByteBuf p_179794_) {
        this.f_134677_ = p_179794_.m_130259_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134690_) {
        p_134690_.m_130077_(this.f_134677_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134688_) {
        p_134688_.m_6936_(this);
    }

    @Nullable
    public Entity m_134681_(ServerLevel p_134682_) {
        return p_134682_.m_8791_(this.f_134677_);
    }
}


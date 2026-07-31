/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.scores.Objective;

public class ClientboundSetDisplayObjectivePacket
implements Packet<ClientGamePacketListener> {
    private final int f_133127_;
    private final String f_133128_;

    public ClientboundSetDisplayObjectivePacket(int p_133131_, @Nullable Objective p_133132_) {
        this.f_133127_ = p_133131_;
        this.f_133128_ = p_133132_ == null ? "" : p_133132_.m_83320_();
    }

    public ClientboundSetDisplayObjectivePacket(FriendlyByteBuf p_179288_) {
        this.f_133127_ = p_179288_.readByte();
        this.f_133128_ = p_179288_.m_130277_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133141_) {
        p_133141_.writeByte(this.f_133127_);
        p_133141_.m_130070_(this.f_133128_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133138_) {
        p_133138_.m_5556_(this);
    }

    public int m_133139_() {
        return this.f_133127_;
    }

    @Nullable
    public String m_133142_() {
        return Objects.equals(this.f_133128_, "") ? null : this.f_133128_;
    }
}


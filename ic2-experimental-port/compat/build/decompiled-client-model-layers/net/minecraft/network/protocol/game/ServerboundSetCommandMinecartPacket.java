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
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.Level;

public class ServerboundSetCommandMinecartPacket
implements Packet<ServerGamePacketListener> {
    private final int f_134529_;
    private final String f_134530_;
    private final boolean f_134531_;

    public ServerboundSetCommandMinecartPacket(int p_134534_, String p_134535_, boolean p_134536_) {
        this.f_134529_ = p_134534_;
        this.f_134530_ = p_134535_;
        this.f_134531_ = p_134536_;
    }

    public ServerboundSetCommandMinecartPacket(FriendlyByteBuf p_179758_) {
        this.f_134529_ = p_179758_.m_130242_();
        this.f_134530_ = p_179758_.m_130277_();
        this.f_134531_ = p_179758_.readBoolean();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134547_) {
        p_134547_.m_130130_(this.f_134529_);
        p_134547_.m_130070_(this.f_134530_);
        p_134547_.writeBoolean(this.f_134531_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134544_) {
        p_134544_.m_6629_(this);
    }

    @Nullable
    public BaseCommandBlock m_134537_(Level p_134538_) {
        Entity $$1 = p_134538_.m_6815_(this.f_134529_);
        if ($$1 instanceof MinecartCommandBlock) {
            return ((MinecartCommandBlock)$$1).m_38534_();
        }
        return null;
    }

    public String m_134545_() {
        return this.f_134530_;
    }

    public boolean m_134548_() {
        return this.f_134531_;
    }
}


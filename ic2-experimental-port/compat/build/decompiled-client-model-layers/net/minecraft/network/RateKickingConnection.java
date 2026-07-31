/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.network;

import com.mojang.logging.LogUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import org.slf4j.Logger;

public class RateKickingConnection
extends Connection {
    private static final Logger f_130553_ = LogUtils.getLogger();
    private static final Component f_130554_ = Component.m_237115_("disconnect.exceeded_packet_rate");
    private final int f_130555_;

    public RateKickingConnection(int p_130558_) {
        super(PacketFlow.SERVERBOUND);
        this.f_130555_ = p_130558_;
    }

    @Override
    protected void m_7073_() {
        super.m_7073_();
        float $$0 = this.m_129542_();
        if ($$0 > (float)this.f_130555_) {
            f_130553_.warn("Player exceeded rate-limit (sent {} packets per second)", (Object)Float.valueOf($$0));
            this.m_243124_(new ClientboundDisconnectPacket(f_130554_), PacketSendListener.m_243092_(() -> this.m_129507_(f_130554_)));
            this.m_129540_();
        }
    }
}


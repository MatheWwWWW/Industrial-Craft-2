/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;

public class ServerItemCooldowns
extends ItemCooldowns {
    private final ServerPlayer f_43065_;

    public ServerItemCooldowns(ServerPlayer p_43067_) {
        this.f_43065_ = p_43067_;
    }

    @Override
    protected void m_6899_(Item p_43069_, int p_43070_) {
        super.m_6899_(p_43069_, p_43070_);
        this.f_43065_.f_8906_.m_9829_(new ClientboundCooldownPacket(p_43069_, p_43070_));
    }

    @Override
    protected void m_7432_(Item p_43072_) {
        super.m_7432_(p_43072_);
        this.f_43065_.f_8906_.m_9829_(new ClientboundCooldownPacket(p_43072_, 0));
    }
}


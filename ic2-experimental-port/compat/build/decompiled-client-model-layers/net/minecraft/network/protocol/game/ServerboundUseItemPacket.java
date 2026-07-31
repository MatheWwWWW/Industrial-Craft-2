/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.InteractionHand;

public class ServerboundUseItemPacket
implements Packet<ServerGamePacketListener> {
    private final InteractionHand f_134707_;
    private final int f_238009_;

    public ServerboundUseItemPacket(InteractionHand p_238011_, int p_238012_) {
        this.f_134707_ = p_238011_;
        this.f_238009_ = p_238012_;
    }

    public ServerboundUseItemPacket(FriendlyByteBuf p_179798_) {
        this.f_134707_ = p_179798_.m_130066_(InteractionHand.class);
        this.f_238009_ = p_179798_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134719_) {
        p_134719_.m_130068_(this.f_134707_);
        p_134719_.m_130130_(this.f_238009_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134716_) {
        p_134716_.m_5760_(this);
    }

    public InteractionHand m_134717_() {
        return this.f_134707_;
    }

    public int m_238013_() {
        return this.f_238009_;
    }
}


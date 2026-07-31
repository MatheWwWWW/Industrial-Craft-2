/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;

public class ServerboundUseItemOnPacket
implements Packet<ServerGamePacketListener> {
    private final BlockHitResult f_134691_;
    private final InteractionHand f_134692_;
    private final int f_238003_;

    public ServerboundUseItemOnPacket(InteractionHand p_238005_, BlockHitResult p_238006_, int p_238007_) {
        this.f_134692_ = p_238005_;
        this.f_134691_ = p_238006_;
        this.f_238003_ = p_238007_;
    }

    public ServerboundUseItemOnPacket(FriendlyByteBuf p_179796_) {
        this.f_134692_ = p_179796_.m_130066_(InteractionHand.class);
        this.f_134691_ = p_179796_.m_130283_();
        this.f_238003_ = p_179796_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134705_) {
        p_134705_.m_130068_(this.f_134692_);
        p_134705_.m_130062_(this.f_134691_);
        p_134705_.m_130130_(this.f_238003_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134702_) {
        p_134702_.m_6371_(this);
    }

    public InteractionHand m_134703_() {
        return this.f_134692_;
    }

    public BlockHitResult m_134706_() {
        return this.f_134691_;
    }

    public int m_238008_() {
        return this.f_238003_;
    }
}


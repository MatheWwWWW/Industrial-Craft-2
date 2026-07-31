/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.item.ItemStack;

public class ServerboundSetCreativeModeSlotPacket
implements Packet<ServerGamePacketListener> {
    private final int f_134549_;
    private final ItemStack f_134550_;

    public ServerboundSetCreativeModeSlotPacket(int p_134553_, ItemStack p_134554_) {
        this.f_134549_ = p_134553_;
        this.f_134550_ = p_134554_.m_41777_();
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134560_) {
        p_134560_.m_5964_(this);
    }

    public ServerboundSetCreativeModeSlotPacket(FriendlyByteBuf p_179760_) {
        this.f_134549_ = p_179760_.readShort();
        this.f_134550_ = p_179760_.m_130267_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134563_) {
        p_134563_.writeShort(this.f_134549_);
        p_134563_.m_130055_(this.f_134550_);
    }

    public int m_134561_() {
        return this.f_134549_;
    }

    public ItemStack m_134564_() {
        return this.f_134550_;
    }
}


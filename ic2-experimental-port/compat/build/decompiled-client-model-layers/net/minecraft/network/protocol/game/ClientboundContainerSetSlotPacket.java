/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.item.ItemStack;

public class ClientboundContainerSetSlotPacket
implements Packet<ClientGamePacketListener> {
    public static final int f_178826_ = -1;
    public static final int f_178827_ = -2;
    private final int f_131977_;
    private final int f_182710_;
    private final int f_131978_;
    private final ItemStack f_131979_;

    public ClientboundContainerSetSlotPacket(int p_131982_, int p_182713_, int p_131983_, ItemStack p_131984_) {
        this.f_131977_ = p_131982_;
        this.f_182710_ = p_182713_;
        this.f_131978_ = p_131983_;
        this.f_131979_ = p_131984_.m_41777_();
    }

    public ClientboundContainerSetSlotPacket(FriendlyByteBuf p_178829_) {
        this.f_131977_ = p_178829_.readByte();
        this.f_182710_ = p_178829_.m_130242_();
        this.f_131978_ = p_178829_.readShort();
        this.f_131979_ = p_178829_.m_130267_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131993_) {
        p_131993_.writeByte(this.f_131977_);
        p_131993_.m_130130_(this.f_182710_);
        p_131993_.writeShort(this.f_131978_);
        p_131993_.m_130055_(this.f_131979_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131990_) {
        p_131990_.m_5735_(this);
    }

    public int m_131991_() {
        return this.f_131977_;
    }

    public int m_131994_() {
        return this.f_131978_;
    }

    public ItemStack m_131995_() {
        return this.f_131979_;
    }

    public int m_182716_() {
        return this.f_182710_;
    }
}


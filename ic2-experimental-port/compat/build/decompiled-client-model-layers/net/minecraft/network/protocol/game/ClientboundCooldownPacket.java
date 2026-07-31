/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.item.Item;

public class ClientboundCooldownPacket
implements Packet<ClientGamePacketListener> {
    private final Item f_131996_;
    private final int f_131997_;

    public ClientboundCooldownPacket(Item p_132000_, int p_132001_) {
        this.f_131996_ = p_132000_;
        this.f_131997_ = p_132001_;
    }

    public ClientboundCooldownPacket(FriendlyByteBuf p_178831_) {
        this.f_131996_ = p_178831_.m_236816_(Registry.f_122827_);
        this.f_131997_ = p_178831_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132010_) {
        p_132010_.m_236818_(Registry.f_122827_, this.f_131996_);
        p_132010_.m_130130_(this.f_131997_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132007_) {
        p_132007_.m_7701_(this);
    }

    public Item m_132008_() {
        return this.f_131996_;
    }

    public int m_132011_() {
        return this.f_131997_;
    }
}


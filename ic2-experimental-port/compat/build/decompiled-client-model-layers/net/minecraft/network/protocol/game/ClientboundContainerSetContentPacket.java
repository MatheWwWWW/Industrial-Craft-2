/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.item.ItemStack;

public class ClientboundContainerSetContentPacket
implements Packet<ClientGamePacketListener> {
    private final int f_131942_;
    private final int f_182701_;
    private final List<ItemStack> f_131943_;
    private final ItemStack f_182702_;

    public ClientboundContainerSetContentPacket(int p_182704_, int p_182705_, NonNullList<ItemStack> p_182706_, ItemStack p_182707_) {
        this.f_131942_ = p_182704_;
        this.f_182701_ = p_182705_;
        this.f_131943_ = NonNullList.m_122780_(p_182706_.size(), ItemStack.f_41583_);
        for (int $$4 = 0; $$4 < p_182706_.size(); ++$$4) {
            this.f_131943_.set($$4, p_182706_.get($$4).m_41777_());
        }
        this.f_182702_ = p_182707_.m_41777_();
    }

    public ClientboundContainerSetContentPacket(FriendlyByteBuf p_178823_) {
        this.f_131942_ = p_178823_.readUnsignedByte();
        this.f_182701_ = p_178823_.m_130242_();
        this.f_131943_ = p_178823_.m_236838_(NonNullList::m_182647_, FriendlyByteBuf::m_130267_);
        this.f_182702_ = p_178823_.m_130267_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131956_) {
        p_131956_.writeByte(this.f_131942_);
        p_131956_.m_130130_(this.f_182701_);
        p_131956_.m_236828_(this.f_131943_, FriendlyByteBuf::m_130055_);
        p_131956_.m_130055_(this.f_182702_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131953_) {
        p_131953_.m_6837_(this);
    }

    public int m_131954_() {
        return this.f_131942_;
    }

    public List<ItemStack> m_131957_() {
        return this.f_131943_;
    }

    public ItemStack m_182708_() {
        return this.f_182702_;
    }

    public int m_182709_() {
        return this.f_182701_;
    }
}


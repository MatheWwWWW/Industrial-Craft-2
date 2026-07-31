/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 */
package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundRemoveEntitiesPacket
implements Packet<ClientGamePacketListener> {
    private final IntList f_182717_;

    public ClientboundRemoveEntitiesPacket(IntList p_182719_) {
        this.f_182717_ = new IntArrayList(p_182719_);
    }

    public ClientboundRemoveEntitiesPacket(int ... p_182723_) {
        this.f_182717_ = new IntArrayList(p_182723_);
    }

    public ClientboundRemoveEntitiesPacket(FriendlyByteBuf p_182721_) {
        this.f_182717_ = p_182721_.m_178338_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_182725_) {
        p_182725_.m_178345_(this.f_182717_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_182729_) {
        p_182729_.m_182047_(this);
    }

    public IntList m_182730_() {
        return this.f_182717_;
    }
}


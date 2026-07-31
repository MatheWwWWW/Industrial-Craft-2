/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public class ClientboundPlaceGhostRecipePacket
implements Packet<ClientGamePacketListener> {
    private final int f_132643_;
    private final ResourceLocation f_132644_;

    public ClientboundPlaceGhostRecipePacket(int p_132647_, Recipe<?> p_132648_) {
        this.f_132643_ = p_132647_;
        this.f_132644_ = p_132648_.m_6423_();
    }

    public ClientboundPlaceGhostRecipePacket(FriendlyByteBuf p_179027_) {
        this.f_132643_ = p_179027_.readByte();
        this.f_132644_ = p_179027_.m_130281_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132657_) {
        p_132657_.writeByte(this.f_132643_);
        p_132657_.m_130085_(this.f_132644_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132654_) {
        p_132654_.m_7339_(this);
    }

    public ResourceLocation m_132655_() {
        return this.f_132644_;
    }

    public int m_132658_() {
        return this.f_132643_;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public class ServerboundPlaceRecipePacket
implements Packet<ServerGamePacketListener> {
    private final int f_134235_;
    private final ResourceLocation f_134236_;
    private final boolean f_134237_;

    public ServerboundPlaceRecipePacket(int p_134240_, Recipe<?> p_134241_, boolean p_134242_) {
        this.f_134235_ = p_134240_;
        this.f_134236_ = p_134241_.m_6423_();
        this.f_134237_ = p_134242_;
    }

    public ServerboundPlaceRecipePacket(FriendlyByteBuf p_179706_) {
        this.f_134235_ = p_179706_.readByte();
        this.f_134236_ = p_179706_.m_130281_();
        this.f_134237_ = p_179706_.readBoolean();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134251_) {
        p_134251_.writeByte(this.f_134235_);
        p_134251_.m_130085_(this.f_134236_);
        p_134251_.writeBoolean(this.f_134237_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134248_) {
        p_134248_.m_7191_(this);
    }

    public int m_134249_() {
        return this.f_134235_;
    }

    public ResourceLocation m_134252_() {
        return this.f_134236_;
    }

    public boolean m_134253_() {
        return this.f_134237_;
    }
}


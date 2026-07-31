/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.inventory.RecipeBookType;

public class ServerboundRecipeBookChangeSettingsPacket
implements Packet<ServerGamePacketListener> {
    private final RecipeBookType f_134361_;
    private final boolean f_134362_;
    private final boolean f_134363_;

    public ServerboundRecipeBookChangeSettingsPacket(RecipeBookType p_134366_, boolean p_134367_, boolean p_134368_) {
        this.f_134361_ = p_134366_;
        this.f_134362_ = p_134367_;
        this.f_134363_ = p_134368_;
    }

    public ServerboundRecipeBookChangeSettingsPacket(FriendlyByteBuf p_179734_) {
        this.f_134361_ = p_179734_.m_130066_(RecipeBookType.class);
        this.f_134362_ = p_179734_.readBoolean();
        this.f_134363_ = p_179734_.readBoolean();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134377_) {
        p_134377_.m_130068_(this.f_134361_);
        p_134377_.writeBoolean(this.f_134362_);
        p_134377_.writeBoolean(this.f_134363_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134374_) {
        p_134374_.m_7982_(this);
    }

    public RecipeBookType m_134375_() {
        return this.f_134361_;
    }

    public boolean m_134378_() {
        return this.f_134362_;
    }

    public boolean m_134379_() {
        return this.f_134363_;
    }
}


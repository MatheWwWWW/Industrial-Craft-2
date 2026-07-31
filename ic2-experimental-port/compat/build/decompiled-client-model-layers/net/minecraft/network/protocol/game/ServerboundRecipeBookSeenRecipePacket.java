/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public class ServerboundRecipeBookSeenRecipePacket
implements Packet<ServerGamePacketListener> {
    private final ResourceLocation f_134380_;

    public ServerboundRecipeBookSeenRecipePacket(Recipe<?> p_134383_) {
        this.f_134380_ = p_134383_.m_6423_();
    }

    public ServerboundRecipeBookSeenRecipePacket(FriendlyByteBuf p_179736_) {
        this.f_134380_ = p_179736_.m_130281_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134392_) {
        p_134392_.m_130085_(this.f_134380_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134389_) {
        p_134389_.m_7411_(this);
    }

    public ResourceLocation m_134390_() {
        return this.f_134380_;
    }
}


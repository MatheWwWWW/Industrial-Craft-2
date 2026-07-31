/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import org.jetbrains.annotations.Nullable;

public record ClientboundChatPreviewPacket(int f_237594_, @Nullable Component f_237595_) implements Packet<ClientGamePacketListener>
{
    public ClientboundChatPreviewPacket(FriendlyByteBuf p_237600_) {
        this(p_237600_.readInt(), (Component)p_237600_.m_236868_(FriendlyByteBuf::m_130238_));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_237602_) {
        p_237602_.writeInt(this.f_237594_);
        p_237602_.m_236821_(this.f_237595_, FriendlyByteBuf::m_130083_);
    }

    @Override
    public boolean m_6588_() {
        return true;
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_237606_) {
        p_237606_.m_213565_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ClientboundChatPreviewPacket.class, "queryId;preview", "f_237594_", "f_237595_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ClientboundChatPreviewPacket.class, "queryId;preview", "f_237594_", "f_237595_"}, this);
    }

    @Override
    public final boolean equals(Object p_237610_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ClientboundChatPreviewPacket.class, "queryId;preview", "f_237594_", "f_237595_"}, this, p_237610_);
    }
}


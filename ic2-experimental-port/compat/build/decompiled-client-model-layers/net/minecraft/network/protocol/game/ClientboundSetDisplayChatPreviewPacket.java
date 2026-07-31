/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public record ClientboundSetDisplayChatPreviewPacket(boolean f_237813_) implements Packet<ClientGamePacketListener>
{
    public ClientboundSetDisplayChatPreviewPacket(FriendlyByteBuf p_237815_) {
        this(p_237815_.readBoolean());
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_237819_) {
        p_237819_.writeBoolean(this.f_237813_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_237823_) {
        p_237823_.m_214045_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ClientboundSetDisplayChatPreviewPacket.class, "enabled", "f_237813_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ClientboundSetDisplayChatPreviewPacket.class, "enabled", "f_237813_"}, this);
    }

    @Override
    public final boolean equals(Object p_237826_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ClientboundSetDisplayChatPreviewPacket.class, "enabled", "f_237813_"}, this, p_237826_);
    }
}


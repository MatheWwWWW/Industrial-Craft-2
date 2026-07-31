/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public record ServerboundChatAckPacket(LastSeenMessages.Update f_241632_) implements Packet<ServerGamePacketListener>
{
    public ServerboundChatAckPacket(FriendlyByteBuf p_242339_) {
        this(new LastSeenMessages.Update(p_242339_));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_242345_) {
        this.f_241632_.m_242008_(p_242345_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_242391_) {
        p_242391_.m_241885_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ServerboundChatAckPacket.class, "lastSeenMessages", "f_241632_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ServerboundChatAckPacket.class, "lastSeenMessages", "f_241632_"}, this);
    }

    @Override
    public final boolean equals(Object p_242425_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ServerboundChatAckPacket.class, "lastSeenMessages", "f_241632_"}, this, p_242425_);
    }
}


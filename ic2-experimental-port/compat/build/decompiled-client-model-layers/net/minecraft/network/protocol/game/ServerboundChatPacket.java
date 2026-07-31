/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Instant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MessageSigner;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.level.ServerPlayer;

public record ServerboundChatPacket(String f_133827_, Instant f_237950_, long f_240906_, MessageSignature f_240898_, boolean f_237952_, LastSeenMessages.Update f_241662_) implements Packet<ServerGamePacketListener>
{
    public ServerboundChatPacket(FriendlyByteBuf p_179545_) {
        this(p_179545_.m_130136_(256), p_179545_.m_236873_(), p_179545_.readLong(), new MessageSignature(p_179545_), p_179545_.readBoolean(), new LastSeenMessages.Update(p_179545_));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133839_) {
        p_133839_.m_130072_(this.f_133827_, 256);
        p_133839_.m_236826_(this.f_237950_);
        p_133839_.writeLong(this.f_240906_);
        this.f_240898_.m_241011_(p_133839_);
        p_133839_.writeBoolean(this.f_237952_);
        this.f_241662_.m_242008_(p_133839_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_133836_) {
        p_133836_.m_7388_(this);
    }

    public MessageSigner m_240947_(ServerPlayer p_241405_) {
        return new MessageSigner(p_241405_.m_20148_(), this.f_237950_, this.f_240906_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ServerboundChatPacket.class, "message;timeStamp;salt;signature;signedPreview;lastSeenMessages", "f_133827_", "f_237950_", "f_240906_", "f_240898_", "f_237952_", "f_241662_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ServerboundChatPacket.class, "message;timeStamp;salt;signature;signedPreview;lastSeenMessages", "f_133827_", "f_237950_", "f_240906_", "f_240898_", "f_237952_", "f_241662_"}, this);
    }

    @Override
    public final boolean equals(Object p_241485_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ServerboundChatPacket.class, "message;timeStamp;salt;signature;signedPreview;lastSeenMessages", "f_133827_", "f_237950_", "f_240906_", "f_240898_", "f_237952_", "f_241662_"}, this, p_241485_);
    }
}


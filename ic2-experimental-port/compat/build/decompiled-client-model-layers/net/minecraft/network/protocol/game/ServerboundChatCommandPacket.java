/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Instant;
import net.minecraft.commands.arguments.ArgumentSignatures;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.util.StringUtil;

public record ServerboundChatCommandPacket(String f_237922_, Instant f_237923_, long f_240858_, ArgumentSignatures f_237924_, boolean f_237925_, LastSeenMessages.Update f_241638_) implements Packet<ServerGamePacketListener>
{
    public ServerboundChatCommandPacket(String f_237922_, Instant f_237923_, long f_240858_, ArgumentSignatures f_237924_, boolean f_237925_, LastSeenMessages.Update f_241638_) {
        this.f_237922_ = f_237922_ = StringUtil.m_216469_(f_237922_);
        this.f_237923_ = f_237923_;
        this.f_240858_ = f_240858_;
        this.f_237924_ = f_237924_;
        this.f_237925_ = f_237925_;
        this.f_241638_ = f_241638_;
    }

    public ServerboundChatCommandPacket(FriendlyByteBuf p_237932_) {
        this(p_237932_.m_130136_(256), p_237932_.m_236873_(), p_237932_.readLong(), new ArgumentSignatures(p_237932_), p_237932_.readBoolean(), new LastSeenMessages.Update(p_237932_));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_237936_) {
        p_237936_.m_130072_(this.f_237922_, 256);
        p_237936_.m_236826_(this.f_237923_);
        p_237936_.writeLong(this.f_240858_);
        this.f_237924_.m_231061_(p_237936_);
        p_237936_.writeBoolean(this.f_237925_);
        this.f_241638_.m_242008_(p_237936_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_237940_) {
        p_237940_.m_214047_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ServerboundChatCommandPacket.class, "command;timeStamp;salt;argumentSignatures;signedPreview;lastSeenMessages", "f_237922_", "f_237923_", "f_240858_", "f_237924_", "f_237925_", "f_241638_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ServerboundChatCommandPacket.class, "command;timeStamp;salt;argumentSignatures;signedPreview;lastSeenMessages", "f_237922_", "f_237923_", "f_240858_", "f_237924_", "f_237925_", "f_241638_"}, this);
    }

    @Override
    public final boolean equals(Object p_237946_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ServerboundChatCommandPacket.class, "command;timeStamp;salt;argumentSignatures;signedPreview;lastSeenMessages", "f_237922_", "f_237923_", "f_240858_", "f_237924_", "f_237925_", "f_241638_"}, this, p_237946_);
    }
}


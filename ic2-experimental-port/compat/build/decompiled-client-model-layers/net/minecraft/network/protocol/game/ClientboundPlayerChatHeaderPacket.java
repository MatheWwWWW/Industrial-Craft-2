/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageHeader;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public record ClientboundPlayerChatHeaderPacket(SignedMessageHeader f_240876_, MessageSignature f_240909_, byte[] f_240888_) implements Packet<ClientGamePacketListener>
{
    public ClientboundPlayerChatHeaderPacket(PlayerChatMessage p_243270_) {
        this(p_243270_.f_240875_(), p_243270_.f_240893_(), p_243270_.f_240885_().m_241131_().asBytes());
    }

    public ClientboundPlayerChatHeaderPacket(FriendlyByteBuf p_241327_) {
        this(new SignedMessageHeader(p_241327_), new MessageSignature(p_241327_), p_241327_.m_130052_());
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_241388_) {
        this.f_240876_.m_240942_(p_241388_);
        this.f_240909_.m_241011_(p_241388_);
        p_241388_.m_130087_(this.f_240888_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_241550_) {
        p_241550_.m_240948_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ClientboundPlayerChatHeaderPacket.class, "header;headerSignature;bodyDigest", "f_240876_", "f_240909_", "f_240888_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ClientboundPlayerChatHeaderPacket.class, "header;headerSignature;bodyDigest", "f_240876_", "f_240909_", "f_240888_"}, this);
    }

    @Override
    public final boolean equals(Object p_241330_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ClientboundPlayerChatHeaderPacket.class, "header;headerSignature;bodyDigest", "f_240876_", "f_240909_", "f_240888_"}, this, p_241330_);
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public record ClientboundPlayerChatPacket(PlayerChatMessage f_240869_, ChatType.BoundNetwork f_240897_) implements Packet<ClientGamePacketListener>
{
    public ClientboundPlayerChatPacket(FriendlyByteBuf p_237741_) {
        this(new PlayerChatMessage(p_237741_), new ChatType.BoundNetwork(p_237741_));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_237755_) {
        this.f_240869_.m_240965_(p_237755_);
        this.f_240897_.m_240969_(p_237755_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_237759_) {
        p_237759_.m_213629_(this);
    }

    @Override
    public boolean m_6588_() {
        return true;
    }

    public Optional<ChatType.Bound> m_242662_(RegistryAccess p_242874_) {
        return this.f_240897_.m_242652_(p_242874_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ClientboundPlayerChatPacket.class, "message;chatType", "f_240869_", "f_240897_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ClientboundPlayerChatPacket.class, "message;chatType", "f_240869_", "f_240897_"}, this);
    }

    @Override
    public final boolean equals(Object p_237765_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ClientboundPlayerChatPacket.class, "message;chatType", "f_240869_", "f_240897_"}, this, p_237765_);
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.util.StringUtil;

public record ServerboundChatPreviewPacket(int f_237962_, String f_237963_) implements Packet<ServerGamePacketListener>
{
    public ServerboundChatPreviewPacket {
        f_237963_ = StringUtil.m_216469_(f_237963_);
    }

    public ServerboundChatPreviewPacket(FriendlyByteBuf p_237968_) {
        this(p_237968_.readInt(), p_237968_.m_130136_(256));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_237970_) {
        p_237970_.writeInt(this.f_237962_);
        p_237970_.m_130072_(this.f_237963_, 256);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_237974_) {
        p_237974_.m_213866_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ServerboundChatPreviewPacket.class, "queryId;query", "f_237962_", "f_237963_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ServerboundChatPreviewPacket.class, "queryId;query", "f_237962_", "f_237963_"}, this);
    }

    @Override
    public final boolean equals(Object p_237978_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ServerboundChatPreviewPacket.class, "queryId;query", "f_237962_", "f_237963_"}, this, p_237978_);
    }
}


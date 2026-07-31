/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public record ClientboundCustomChatCompletionsPacket(Action f_240661_, List<String> f_240663_) implements Packet<ClientGamePacketListener>
{
    public ClientboundCustomChatCompletionsPacket(FriendlyByteBuf p_243340_) {
        this(p_243340_.m_130066_(Action.class), p_243340_.m_236845_(FriendlyByteBuf::m_130277_));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_240782_) {
        p_240782_.m_130068_(this.f_240661_);
        p_240782_.m_236828_(this.f_240663_, FriendlyByteBuf::m_130070_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_240794_) {
        p_240794_.m_240695_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ClientboundCustomChatCompletionsPacket.class, "action;entries", "f_240661_", "f_240663_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ClientboundCustomChatCompletionsPacket.class, "action;entries", "f_240661_", "f_240663_"}, this);
    }

    @Override
    public final boolean equals(Object p_240819_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ClientboundCustomChatCompletionsPacket.class, "action;entries", "f_240661_", "f_240663_"}, this, p_240819_);
    }

    public static final class Action
    extends Enum<Action> {
        public static final /* enum */ Action ADD = new Action();
        public static final /* enum */ Action REMOVE = new Action();
        public static final /* enum */ Action SET = new Action();
        private static final /* synthetic */ Action[] $VALUES;

        public static Action[] values() {
            return (Action[])$VALUES.clone();
        }

        public static Action valueOf(String p_240779_) {
            return Enum.valueOf(Action.class, p_240779_);
        }

        private static /* synthetic */ Action[] m_240733_() {
            return new Action[]{ADD, REMOVE, SET};
        }

        static {
            $VALUES = Action.m_240733_();
        }
    }
}


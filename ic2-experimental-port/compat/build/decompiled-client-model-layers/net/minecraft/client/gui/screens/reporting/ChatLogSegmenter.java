/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.reporting;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage;

public class ChatLogSegmenter<T extends LoggedChatMessage> {
    private final Function<ChatLog.Entry<T>, MessageType> f_238782_;
    private final List<ChatLog.Entry<T>> f_238752_ = new ArrayList<ChatLog.Entry<T>>();
    @Nullable
    private MessageType f_238788_;

    public ChatLogSegmenter(Function<ChatLog.Entry<T>, MessageType> p_239532_) {
        this.f_238782_ = p_239532_;
    }

    public boolean m_239047_(ChatLog.Entry<T> p_242182_) {
        MessageType $$1 = this.f_238782_.apply(p_242182_);
        if (this.f_238788_ == null || $$1 == this.f_238788_) {
            this.f_238788_ = $$1;
            this.f_238752_.add(p_242182_);
            return true;
        }
        return false;
    }

    @Nullable
    public Results<T> m_240212_() {
        if (!this.f_238752_.isEmpty() && this.f_238788_ != null) {
            return new Results<T>(this.f_238752_, this.f_238788_);
        }
        return null;
    }

    public static final class MessageType
    extends Enum<MessageType> {
        public static final /* enum */ MessageType REPORTABLE = new MessageType();
        public static final /* enum */ MessageType CONTEXT = new MessageType();
        private static final /* synthetic */ MessageType[] $VALUES;

        public static MessageType[] values() {
            return (MessageType[])$VALUES.clone();
        }

        public static MessageType valueOf(String p_239496_) {
            return Enum.valueOf(MessageType.class, p_239496_);
        }

        public boolean m_239737_() {
            return this == CONTEXT;
        }

        private static /* synthetic */ MessageType[] m_240139_() {
            return new MessageType[]{REPORTABLE, CONTEXT};
        }

        static {
            $VALUES = MessageType.m_240139_();
        }
    }

    public record Results<T extends LoggedChatMessage>(List<ChatLog.Entry<T>> f_238707_, MessageType f_238517_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Results.class, "messages;type", "f_238707_", "f_238517_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Results.class, "messages;type", "f_238707_", "f_238517_"}, this);
        }

        @Override
        public final boolean equals(Object p_239801_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Results.class, "messages;type", "f_238707_", "f_238517_"}, this, p_239801_);
        }
    }
}


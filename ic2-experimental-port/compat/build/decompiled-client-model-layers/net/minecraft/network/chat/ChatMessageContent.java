/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.chat;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

public record ChatMessageContent(String f_241656_, Component f_241671_) {
    public ChatMessageContent(String p_242420_) {
        this(p_242420_, Component.m_237113_(p_242420_));
    }

    public boolean m_241978_() {
        return !this.f_241671_.equals(Component.m_237113_(this.f_241656_));
    }

    public static ChatMessageContent m_241957_(FriendlyByteBuf p_242370_) {
        String $$1 = p_242370_.m_130136_(256);
        Component $$2 = (Component)p_242370_.m_236868_(FriendlyByteBuf::m_130238_);
        return new ChatMessageContent($$1, Objects.requireNonNullElse($$2, Component.m_237113_($$1)));
    }

    public static void m_242020_(FriendlyByteBuf p_242211_, ChatMessageContent p_242235_) {
        p_242211_.m_130072_(p_242235_.f_241656_(), 256);
        Component $$2 = p_242235_.m_241978_() ? p_242235_.f_241671_() : null;
        p_242211_.m_236821_($$2, FriendlyByteBuf::m_130083_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ChatMessageContent.class, "plain;decorated", "f_241656_", "f_241671_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ChatMessageContent.class, "plain;decorated", "f_241656_", "f_241671_"}, this);
    }

    @Override
    public final boolean equals(Object p_242389_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ChatMessageContent.class, "plain;decorated", "f_241656_", "f_241671_"}, this, p_242389_);
    }
}


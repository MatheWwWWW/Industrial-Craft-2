/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;

@FunctionalInterface
public interface ChatDecorator {
    public static final ChatDecorator f_236947_ = (p_236950_, p_236951_) -> CompletableFuture.completedFuture(p_236951_);

    public CompletableFuture<Component> m_236961_(@Nullable ServerPlayer var1, Component var2);

    default public CompletableFuture<PlayerChatMessage> m_243107_(@Nullable ServerPlayer p_243328_, PlayerChatMessage p_243294_) {
        if (p_243294_.m_241775_().m_241978_()) {
            return CompletableFuture.completedFuture(p_243294_);
        }
        return this.m_236961_(p_243328_, p_243294_.m_237220_()).thenApply(p_243294_::m_241956_);
    }

    public static PlayerChatMessage m_243125_(PlayerChatMessage p_243303_, Component p_243232_) {
        if (!p_243303_.m_241775_().m_241978_()) {
            return p_243303_.m_241956_(p_243232_);
        }
        return p_243303_;
    }
}


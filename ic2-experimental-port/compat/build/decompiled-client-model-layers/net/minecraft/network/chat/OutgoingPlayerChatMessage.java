/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package net.minecraft.network.chat;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.protocol.game.ClientboundPlayerChatHeaderPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;

public interface OutgoingPlayerChatMessage {
    public Component m_240962_();

    public void m_241045_(ServerPlayer var1, boolean var2, ChatType.Bound var3);

    public void m_241051_(PlayerList var1);

    public static OutgoingPlayerChatMessage m_242676_(PlayerChatMessage p_242935_) {
        if (p_242935_.m_241067_().m_241005_()) {
            return new NotTracked(p_242935_);
        }
        return new Tracked(p_242935_);
    }

    public static class NotTracked
    implements OutgoingPlayerChatMessage {
        private final PlayerChatMessage f_240900_;

        public NotTracked(PlayerChatMessage p_241413_) {
            this.f_240900_ = p_241413_;
        }

        @Override
        public Component m_240962_() {
            return this.f_240900_.m_237220_();
        }

        @Override
        public void m_241045_(ServerPlayer p_243208_, boolean p_243217_, ChatType.Bound p_243207_) {
            PlayerChatMessage $$3 = this.f_240900_.m_243098_(p_243217_);
            if (!$$3.m_243059_()) {
                RegistryAccess $$4 = p_243208_.f_19853_.m_5962_();
                ChatType.BoundNetwork $$5 = p_243207_.m_240987_($$4);
                p_243208_.f_8906_.m_9829_(new ClientboundPlayerChatPacket($$3, $$5));
                p_243208_.f_8906_.m_241992_($$3);
            }
        }

        @Override
        public void m_241051_(PlayerList p_241443_) {
        }
    }

    public static class Tracked
    implements OutgoingPlayerChatMessage {
        private final PlayerChatMessage f_240882_;
        private final Set<ServerPlayer> f_240877_ = Sets.newIdentityHashSet();

        public Tracked(PlayerChatMessage p_241558_) {
            this.f_240882_ = p_241558_;
        }

        @Override
        public Component m_240962_() {
            return this.f_240882_.m_237220_();
        }

        @Override
        public void m_241045_(ServerPlayer p_243241_, boolean p_243304_, ChatType.Bound p_243225_) {
            PlayerChatMessage $$3 = this.f_240882_.m_243098_(p_243304_);
            if (!$$3.m_243059_()) {
                this.f_240877_.add(p_243241_);
                RegistryAccess $$4 = p_243241_.f_19853_.m_5962_();
                ChatType.BoundNetwork $$5 = p_243225_.m_240987_($$4);
                p_243241_.f_8906_.m_243119_(new ClientboundPlayerChatPacket($$3, $$5), PacketSendListener.m_243073_(() -> new ClientboundPlayerChatHeaderPacket(this.f_240882_)));
                p_243241_.f_8906_.m_241992_($$3);
            }
        }

        @Override
        public void m_241051_(PlayerList p_241386_) {
            p_241386_.m_241163_(this.f_240882_, this.f_240877_);
        }
    }
}


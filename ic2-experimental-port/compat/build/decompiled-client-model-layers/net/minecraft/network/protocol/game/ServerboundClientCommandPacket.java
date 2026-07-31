/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundClientCommandPacket
implements Packet<ServerGamePacketListener> {
    private final Action f_133840_;

    public ServerboundClientCommandPacket(Action p_133843_) {
        this.f_133840_ = p_133843_;
    }

    public ServerboundClientCommandPacket(FriendlyByteBuf p_179547_) {
        this.f_133840_ = p_179547_.m_130066_(Action.class);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133852_) {
        p_133852_.m_130068_(this.f_133840_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_133849_) {
        p_133849_.m_6272_(this);
    }

    public Action m_133850_() {
        return this.f_133840_;
    }

    public static final class Action
    extends Enum<Action> {
        public static final /* enum */ Action PERFORM_RESPAWN = new Action();
        public static final /* enum */ Action REQUEST_STATS = new Action();
        private static final /* synthetic */ Action[] $VALUES;

        public static Action[] values() {
            return (Action[])$VALUES.clone();
        }

        public static Action valueOf(String p_133861_) {
            return Enum.valueOf(Action.class, p_133861_);
        }

        private static /* synthetic */ Action[] m_179548_() {
            return new Action[]{PERFORM_RESPAWN, REQUEST_STATS};
        }

        static {
            $VALUES = Action.m_179548_();
        }
    }
}


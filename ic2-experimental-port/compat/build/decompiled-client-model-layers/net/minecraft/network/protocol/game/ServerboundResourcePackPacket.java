/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundResourcePackPacket
implements Packet<ServerGamePacketListener> {
    private final Action f_134406_;

    public ServerboundResourcePackPacket(Action p_134409_) {
        this.f_134406_ = p_134409_;
    }

    public ServerboundResourcePackPacket(FriendlyByteBuf p_179740_) {
        this.f_134406_ = p_179740_.m_130066_(Action.class);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134417_) {
        p_134417_.m_130068_(this.f_134406_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134415_) {
        p_134415_.m_7529_(this);
    }

    public Action m_179741_() {
        return this.f_134406_;
    }

    public static final class Action
    extends Enum<Action> {
        public static final /* enum */ Action SUCCESSFULLY_LOADED = new Action();
        public static final /* enum */ Action DECLINED = new Action();
        public static final /* enum */ Action FAILED_DOWNLOAD = new Action();
        public static final /* enum */ Action ACCEPTED = new Action();
        private static final /* synthetic */ Action[] $VALUES;

        public static Action[] values() {
            return (Action[])$VALUES.clone();
        }

        public static Action valueOf(String p_134428_) {
            return Enum.valueOf(Action.class, p_134428_);
        }

        private static /* synthetic */ Action[] m_179742_() {
            return new Action[]{SUCCESSFULLY_LOADED, DECLINED, FAILED_DOWNLOAD, ACCEPTED};
        }

        static {
            $VALUES = Action.m_179742_();
        }
    }
}


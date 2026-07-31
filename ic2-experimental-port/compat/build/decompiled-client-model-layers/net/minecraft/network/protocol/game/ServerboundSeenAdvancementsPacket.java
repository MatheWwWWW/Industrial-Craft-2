/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.resources.ResourceLocation;

public class ServerboundSeenAdvancementsPacket
implements Packet<ServerGamePacketListener> {
    private final Action f_134430_;
    @Nullable
    private final ResourceLocation f_134431_;

    public ServerboundSeenAdvancementsPacket(Action p_134434_, @Nullable ResourceLocation p_134435_) {
        this.f_134430_ = p_134434_;
        this.f_134431_ = p_134435_;
    }

    public static ServerboundSeenAdvancementsPacket m_134442_(Advancement p_134443_) {
        return new ServerboundSeenAdvancementsPacket(Action.OPENED_TAB, p_134443_.m_138327_());
    }

    public static ServerboundSeenAdvancementsPacket m_134444_() {
        return new ServerboundSeenAdvancementsPacket(Action.CLOSED_SCREEN, null);
    }

    public ServerboundSeenAdvancementsPacket(FriendlyByteBuf p_179744_) {
        this.f_134430_ = p_179744_.m_130066_(Action.class);
        this.f_134431_ = this.f_134430_ == Action.OPENED_TAB ? p_179744_.m_130281_() : null;
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134446_) {
        p_134446_.m_130068_(this.f_134430_);
        if (this.f_134430_ == Action.OPENED_TAB) {
            p_134446_.m_130085_(this.f_134431_);
        }
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134441_) {
        p_134441_.m_6947_(this);
    }

    public Action m_134447_() {
        return this.f_134430_;
    }

    @Nullable
    public ResourceLocation m_134448_() {
        return this.f_134431_;
    }

    public static final class Action
    extends Enum<Action> {
        public static final /* enum */ Action OPENED_TAB = new Action();
        public static final /* enum */ Action CLOSED_SCREEN = new Action();
        private static final /* synthetic */ Action[] $VALUES;

        public static Action[] values() {
            return (Action[])$VALUES.clone();
        }

        public static Action valueOf(String p_134457_) {
            return Enum.valueOf(Action.class, p_134457_);
        }

        private static /* synthetic */ Action[] m_179745_() {
            return new Action[]{OPENED_TAB, CLOSED_SCREEN};
        }

        static {
            $VALUES = Action.m_179745_();
        }
    }
}


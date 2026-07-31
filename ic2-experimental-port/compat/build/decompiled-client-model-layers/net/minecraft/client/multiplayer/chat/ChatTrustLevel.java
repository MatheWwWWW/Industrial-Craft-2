/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.multiplayer.chat;

import java.time.Instant;
import javax.annotation.Nullable;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageValidator;

public final class ChatTrustLevel
extends Enum<ChatTrustLevel> {
    public static final /* enum */ ChatTrustLevel SECURE = new ChatTrustLevel();
    public static final /* enum */ ChatTrustLevel MODIFIED = new ChatTrustLevel();
    public static final /* enum */ ChatTrustLevel FILTERED = new ChatTrustLevel();
    public static final /* enum */ ChatTrustLevel NOT_SECURE = new ChatTrustLevel();
    public static final /* enum */ ChatTrustLevel BROKEN_CHAIN = new ChatTrustLevel();
    private static final /* synthetic */ ChatTrustLevel[] $VALUES;

    public static ChatTrustLevel[] values() {
        return (ChatTrustLevel[])$VALUES.clone();
    }

    public static ChatTrustLevel valueOf(String p_240640_) {
        return Enum.valueOf(ChatTrustLevel.class, p_240640_);
    }

    public static ChatTrustLevel m_240455_(PlayerChatMessage p_240613_, Component p_240570_, @Nullable PlayerInfo p_240623_, Instant p_242386_) {
        if (p_240623_ == null) {
            return NOT_SECURE;
        }
        SignedMessageValidator.State $$4 = p_240623_.m_241043_().m_241093_(p_240613_);
        if ($$4 == SignedMessageValidator.State.BROKEN_CHAIN) {
            return BROKEN_CHAIN;
        }
        if ($$4 == SignedMessageValidator.State.NOT_SECURE) {
            return NOT_SECURE;
        }
        if (p_240613_.m_240414_(p_242386_)) {
            return NOT_SECURE;
        }
        if (!p_240613_.f_242992_().m_243095_()) {
            return FILTERED;
        }
        if (p_240613_.f_237215_().isPresent()) {
            return MODIFIED;
        }
        if (!p_240570_.m_240452_(p_240613_.m_241775_().f_241671_())) {
            return MODIFIED;
        }
        return SECURE;
    }

    public boolean m_240450_() {
        return this == NOT_SECURE || this == BROKEN_CHAIN;
    }

    @Nullable
    public GuiMessageTag m_240405_(PlayerChatMessage p_240632_) {
        return switch (this) {
            case MODIFIED -> GuiMessageTag.m_240466_(p_240632_.m_241775_().f_241656_());
            case FILTERED -> GuiMessageTag.m_243051_();
            case NOT_SECURE -> GuiMessageTag.m_240400_();
            default -> null;
        };
    }

    private static /* synthetic */ ChatTrustLevel[] m_240444_() {
        return new ChatTrustLevel[]{SECURE, MODIFIED, FILTERED, NOT_SECURE, BROKEN_CHAIN};
    }

    static {
        $VALUES = ChatTrustLevel.m_240444_();
    }
}


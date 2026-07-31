/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.multiplayer.chat;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraft.util.OptionEnum;

public final class ChatPreviewStatus
extends Enum<ChatPreviewStatus>
implements OptionEnum {
    public static final /* enum */ ChatPreviewStatus OFF = new ChatPreviewStatus(0, "options.off");
    public static final /* enum */ ChatPreviewStatus LIVE = new ChatPreviewStatus(1, "options.chatPreview.live");
    public static final /* enum */ ChatPreviewStatus CONFIRM = new ChatPreviewStatus(2, "options.chatPreview.confirm");
    private static final ChatPreviewStatus[] f_241653_;
    private final int f_241689_;
    private final String f_241649_;
    private static final /* synthetic */ ChatPreviewStatus[] $VALUES;

    public static ChatPreviewStatus[] values() {
        return (ChatPreviewStatus[])$VALUES.clone();
    }

    public static ChatPreviewStatus valueOf(String p_242312_) {
        return Enum.valueOf(ChatPreviewStatus.class, p_242312_);
    }

    private ChatPreviewStatus(int p_242464_, String p_242202_) {
        this.f_241689_ = p_242464_;
        this.f_241649_ = p_242202_;
    }

    @Override
    public String m_35968_() {
        return this.f_241649_;
    }

    @Override
    public int m_35965_() {
        return this.f_241689_;
    }

    public static ChatPreviewStatus m_241779_(int p_242383_) {
        return f_241653_[Mth.m_14100_(p_242383_, f_241653_.length)];
    }

    private static /* synthetic */ ChatPreviewStatus[] m_241769_() {
        return new ChatPreviewStatus[]{OFF, LIVE, CONFIRM};
    }

    static {
        $VALUES = ChatPreviewStatus.m_241769_();
        f_241653_ = (ChatPreviewStatus[])Arrays.stream(ChatPreviewStatus.values()).sorted(Comparator.comparingInt(ChatPreviewStatus::m_35965_)).toArray(ChatPreviewStatus[]::new);
    }
}


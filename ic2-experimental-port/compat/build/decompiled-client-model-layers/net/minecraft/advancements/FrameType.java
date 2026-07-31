/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.advancements;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public final class FrameType
extends Enum<FrameType> {
    public static final /* enum */ FrameType TASK = new FrameType("task", 0, ChatFormatting.GREEN);
    public static final /* enum */ FrameType CHALLENGE = new FrameType("challenge", 26, ChatFormatting.DARK_PURPLE);
    public static final /* enum */ FrameType GOAL = new FrameType("goal", 52, ChatFormatting.GREEN);
    private final String f_15536_;
    private final int f_15537_;
    private final ChatFormatting f_15538_;
    private final Component f_15539_;
    private static final /* synthetic */ FrameType[] $VALUES;

    public static FrameType[] values() {
        return (FrameType[])$VALUES.clone();
    }

    public static FrameType valueOf(String p_15555_) {
        return Enum.valueOf(FrameType.class, p_15555_);
    }

    private FrameType(String p_15545_, int p_15546_, ChatFormatting p_15547_) {
        this.f_15536_ = p_15545_;
        this.f_15537_ = p_15546_;
        this.f_15538_ = p_15547_;
        this.f_15539_ = Component.m_237115_("advancements.toast." + p_15545_);
    }

    public String m_15548_() {
        return this.f_15536_;
    }

    public int m_15551_() {
        return this.f_15537_;
    }

    public static FrameType m_15549_(String p_15550_) {
        for (FrameType $$1 : FrameType.values()) {
            if (!$$1.f_15536_.equals(p_15550_)) continue;
            return $$1;
        }
        throw new IllegalArgumentException("Unknown frame type '" + p_15550_ + "'");
    }

    public ChatFormatting m_15552_() {
        return this.f_15538_;
    }

    public Component m_15553_() {
        return this.f_15539_;
    }

    private static /* synthetic */ FrameType[] m_145833_() {
        return new FrameType[]{TASK, CHALLENGE, GOAL};
    }

    static {
        $VALUES = FrameType.m_145833_();
    }
}


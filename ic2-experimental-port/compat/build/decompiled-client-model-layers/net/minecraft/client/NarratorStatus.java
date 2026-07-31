/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public final class NarratorStatus
extends Enum<NarratorStatus> {
    public static final /* enum */ NarratorStatus OFF = new NarratorStatus(0, "options.narrator.off");
    public static final /* enum */ NarratorStatus ALL = new NarratorStatus(1, "options.narrator.all");
    public static final /* enum */ NarratorStatus CHAT = new NarratorStatus(2, "options.narrator.chat");
    public static final /* enum */ NarratorStatus SYSTEM = new NarratorStatus(3, "options.narrator.system");
    private static final NarratorStatus[] f_91608_;
    private final int f_91609_;
    private final Component f_91610_;
    private static final /* synthetic */ NarratorStatus[] $VALUES;

    public static NarratorStatus[] values() {
        return (NarratorStatus[])$VALUES.clone();
    }

    public static NarratorStatus valueOf(String p_91625_) {
        return Enum.valueOf(NarratorStatus.class, p_91625_);
    }

    private NarratorStatus(int p_91616_, String p_91617_) {
        this.f_91609_ = p_91616_;
        this.f_91610_ = Component.m_237115_(p_91617_);
    }

    public int m_91618_() {
        return this.f_91609_;
    }

    public Component m_91621_() {
        return this.f_91610_;
    }

    public static NarratorStatus m_91619_(int p_91620_) {
        return f_91608_[Mth.m_14100_(p_91620_, f_91608_.length)];
    }

    public boolean m_240504_() {
        return this == ALL || this == CHAT;
    }

    public boolean m_240472_() {
        return this == ALL || this == SYSTEM;
    }

    private static /* synthetic */ NarratorStatus[] m_168104_() {
        return new NarratorStatus[]{OFF, ALL, CHAT, SYSTEM};
    }

    static {
        $VALUES = NarratorStatus.m_168104_();
        f_91608_ = (NarratorStatus[])Arrays.stream(NarratorStatus.values()).sorted(Comparator.comparingInt(NarratorStatus::m_91618_)).toArray(NarratorStatus[]::new);
    }
}


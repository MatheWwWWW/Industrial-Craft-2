/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.multiplayer.chat;

import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.client.multiplayer.chat.LoggedChatEvent;

public class RollingMemoryChatLog
implements ChatLog {
    private final LoggedChatEvent[] f_238601_;
    private int f_238757_ = -1;
    private int f_238823_ = -1;

    public RollingMemoryChatLog(int p_239903_) {
        this.f_238601_ = new LoggedChatEvent[p_239903_];
    }

    @Override
    public void m_239651_(LoggedChatEvent p_242377_) {
        int $$1 = this.m_238961_();
        this.f_238601_[this.m_239510_((int)$$1)] = p_242377_;
    }

    private int m_238961_() {
        int $$0;
        this.f_238823_ = ($$0 = ++this.f_238757_) >= this.f_238601_.length ? ++this.f_238823_ : 0;
        return $$0;
    }

    @Override
    @Nullable
    public LoggedChatEvent m_239049_(int p_242175_) {
        return this.m_238950_(p_242175_) ? this.f_238601_[this.m_239510_(p_242175_)] : null;
    }

    private int m_239510_(int p_239511_) {
        return p_239511_ % this.f_238601_.length;
    }

    @Override
    public boolean m_238950_(int p_239977_) {
        return p_239977_ >= this.f_238823_ && p_239977_ <= this.f_238757_;
    }

    @Override
    public int m_239141_(int p_240086_, int p_240087_) {
        int $$2 = p_240086_ + p_240087_;
        return this.m_238950_($$2) ? $$2 : -1;
    }

    @Override
    public int m_239178_() {
        return this.f_238757_;
    }

    @Override
    public int m_239389_() {
        return this.f_238823_;
    }
}


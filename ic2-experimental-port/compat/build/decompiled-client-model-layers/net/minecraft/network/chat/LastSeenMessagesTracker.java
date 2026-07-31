/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.chat;

import java.util.Arrays;
import net.minecraft.network.chat.LastSeenMessages;

public class LastSeenMessagesTracker {
    private final LastSeenMessages.Entry[] f_241645_;
    private int f_241603_;
    private LastSeenMessages f_241605_ = LastSeenMessages.f_241634_;

    public LastSeenMessagesTracker(int p_242388_) {
        this.f_241645_ = new LastSeenMessages.Entry[p_242388_];
    }

    public void m_241911_(LastSeenMessages.Entry p_242466_) {
        LastSeenMessages.Entry $$1 = p_242466_;
        for (int $$2 = 0; $$2 < this.f_241603_; ++$$2) {
            LastSeenMessages.Entry $$3 = this.f_241645_[$$2];
            this.f_241645_[$$2] = $$1;
            $$1 = $$3;
            if (!$$3.f_241648_().equals(p_242466_.f_241648_())) continue;
            $$1 = null;
            break;
        }
        if ($$1 != null && this.f_241603_ < this.f_241645_.length) {
            this.f_241645_[this.f_241603_++] = $$1;
        }
        this.f_241605_ = new LastSeenMessages(Arrays.asList(Arrays.copyOf(this.f_241645_, this.f_241603_)));
    }

    public LastSeenMessages m_242022_() {
        return this.f_241605_;
    }
}


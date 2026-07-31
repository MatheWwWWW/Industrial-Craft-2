/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import javax.annotation.Nullable;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageHeader;
import net.minecraft.util.SignatureValidator;
import net.minecraft.world.entity.player.ProfilePublicKey;

public interface SignedMessageValidator {
    public static SignedMessageValidator m_242959_(@Nullable ProfilePublicKey p_242975_, boolean p_242976_) {
        if (p_242975_ != null) {
            return new KeyBased(p_242975_.m_219785_());
        }
        return new Unsigned(p_242976_);
    }

    public State m_241126_(SignedMessageHeader var1, MessageSignature var2, byte[] var3);

    public State m_241093_(PlayerChatMessage var1);

    public static class KeyBased
    implements SignedMessageValidator {
        private final SignatureValidator f_240903_;
        @Nullable
        private MessageSignature f_240857_;
        private boolean f_240880_ = true;

        public KeyBased(SignatureValidator p_241517_) {
            this.f_240903_ = p_241517_;
        }

        private boolean m_243138_(SignedMessageHeader p_243280_, MessageSignature p_243215_, boolean p_243312_) {
            if (p_243215_.m_241004_()) {
                return false;
            }
            if (p_243312_ && p_243215_.equals(this.f_240857_)) {
                return true;
            }
            return this.f_240857_ == null || this.f_240857_.equals(p_243280_.f_240892_());
        }

        private boolean m_243055_(SignedMessageHeader p_243269_, MessageSignature p_243259_, byte[] p_243265_, boolean p_243221_) {
            return this.m_243138_(p_243269_, p_243259_, p_243221_) && p_243259_.m_241124_(this.f_240903_, p_243269_, p_243265_);
        }

        private State m_243116_(SignedMessageHeader p_243211_, MessageSignature p_243274_, byte[] p_243209_, boolean p_243324_) {
            boolean bl = this.f_240880_ = this.f_240880_ && this.m_243055_(p_243211_, p_243274_, p_243209_, p_243324_);
            if (!this.f_240880_) {
                return State.BROKEN_CHAIN;
            }
            this.f_240857_ = p_243274_;
            return State.SECURE;
        }

        @Override
        public State m_241126_(SignedMessageHeader p_242886_, MessageSignature p_242853_, byte[] p_242869_) {
            return this.m_243116_(p_242886_, p_242853_, p_242869_, false);
        }

        @Override
        public State m_241093_(PlayerChatMessage p_242943_) {
            byte[] $$1 = p_242943_.f_240885_().m_241131_().asBytes();
            return this.m_243116_(p_242943_.f_240875_(), p_242943_.f_240893_(), $$1, true);
        }
    }

    public static class Unsigned
    implements SignedMessageValidator {
        private final boolean f_242987_;

        public Unsigned(boolean p_243256_) {
            this.f_242987_ = p_243256_;
        }

        private State m_243127_(MessageSignature p_243292_) {
            if (!p_243292_.m_241004_()) {
                return State.BROKEN_CHAIN;
            }
            return this.f_242987_ ? State.BROKEN_CHAIN : State.NOT_SECURE;
        }

        @Override
        public State m_241126_(SignedMessageHeader p_243299_, MessageSignature p_243315_, byte[] p_243252_) {
            return this.m_243127_(p_243315_);
        }

        @Override
        public State m_241093_(PlayerChatMessage p_243296_) {
            return this.m_243127_(p_243296_.f_240893_());
        }
    }

    public static final class State
    extends Enum<State> {
        public static final /* enum */ State SECURE = new State();
        public static final /* enum */ State NOT_SECURE = new State();
        public static final /* enum */ State BROKEN_CHAIN = new State();
        private static final /* synthetic */ State[] $VALUES;

        public static State[] values() {
            return (State[])$VALUES.clone();
        }

        public static State valueOf(String p_242924_) {
            return Enum.valueOf(State.class, p_242924_);
        }

        private static /* synthetic */ State[] m_242635_() {
            return new State[]{SECURE, NOT_SECURE, BROKEN_CHAIN};
        }

        static {
            $VALUES = State.m_242635_();
        }
    }
}


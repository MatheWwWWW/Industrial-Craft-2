/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.client.gui.screens.reporting;

import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.client.gui.screens.reporting.ChatLogSegmenter;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class ChatSelectionLogFiller<T extends LoggedChatMessage> {
    private static final int f_238662_ = 4;
    private final ChatLog f_238669_;
    private final Predicate<T> f_238535_;
    private int f_238801_;
    final Class<T> f_242501_;

    public ChatSelectionLogFiller(ChatLog p_242897_, Predicate<T> p_242845_, Class<T> p_242904_) {
        this.f_238669_ = p_242897_;
        this.f_238535_ = p_242845_;
        this.f_238801_ = p_242897_.m_239178_();
        this.f_242501_ = p_242904_;
    }

    public void m_239015_(int p_239016_, Output<T> p_239017_) {
        ChatLogSegmenter.Results<T> $$3;
        int $$2 = 0;
        while ($$2 < p_239016_ && ($$3 = this.m_239462_()) != null) {
            if ($$3.f_238517_().m_239737_()) {
                $$2 += this.m_239252_($$3.f_238707_(), p_239017_);
                continue;
            }
            p_239017_.m_240090_($$3.f_238707_());
            $$2 += $$3.f_238707_().size();
        }
    }

    private int m_239252_(List<ChatLog.Entry<T>> p_239253_, Output<T> p_239254_) {
        int $$2 = 8;
        if (p_239253_.size() > 8) {
            int $$3 = p_239253_.size() - 8;
            p_239254_.m_240090_(p_239253_.subList(0, 4));
            p_239254_.m_239556_(Component.m_237110_("gui.chatSelection.fold", $$3));
            p_239254_.m_240090_(p_239253_.subList(p_239253_.size() - 4, p_239253_.size()));
            return 9;
        }
        p_239254_.m_240090_(p_239253_);
        return p_239253_.size();
    }

    @Nullable
    private ChatLogSegmenter.Results<T> m_239462_() {
        ChatLogSegmenter $$0 = new ChatLogSegmenter(p_242051_ -> this.m_239502_((LoggedChatMessage)p_242051_.f_241599_()));
        OptionalInt $$1 = this.f_238669_.m_238953_(this.f_238801_).m_240148_().map(p_242687_ -> p_242687_.m_241867_(this.f_242501_)).filter(Objects::nonNull).takeWhile($$0::m_239047_).mapToInt(ChatLog.Entry::f_241698_).reduce((p_240038_, p_240039_) -> p_240039_);
        if ($$1.isPresent()) {
            this.f_238801_ = this.f_238669_.m_239679_($$1.getAsInt());
        }
        return $$0.m_240212_();
    }

    private ChatLogSegmenter.MessageType m_239502_(T p_242252_) {
        return this.f_238535_.test(p_242252_) ? ChatLogSegmenter.MessageType.REPORTABLE : ChatLogSegmenter.MessageType.CONTEXT;
    }

    public static interface Output<T extends LoggedChatMessage> {
        default public void m_240090_(Iterable<ChatLog.Entry<T>> p_240091_) {
            for (ChatLog.Entry<T> $$1 : p_240091_) {
                this.m_239246_($$1.f_241698_(), (LoggedChatMessage)$$1.f_241599_());
            }
        }

        public void m_239246_(int var1, T var2);

        public void m_239556_(Component var1);
    }
}


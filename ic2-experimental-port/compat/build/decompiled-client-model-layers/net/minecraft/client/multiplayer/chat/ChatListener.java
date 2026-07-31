/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Queues
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.client.multiplayer.chat;

import com.google.common.collect.Queues;
import com.mojang.authlib.GameProfile;
import java.time.Instant;
import java.util.Deque;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.client.multiplayer.chat.ChatTrustLevel;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
import net.minecraft.client.multiplayer.chat.LoggedChatMessageLink;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FilterMask;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MessageSigner;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageHeader;
import net.minecraft.network.chat.SignedMessageValidator;
import net.minecraft.util.StringDecomposer;
import org.apache.commons.lang3.StringUtils;

public class ChatListener {
    private static final Component f_242497_ = Component.m_237115_("multiplayer.disconnect.chat_validation_failed");
    private final Minecraft f_240348_;
    private final Deque<Message> f_240677_ = Queues.newArrayDeque();
    private long f_240660_;
    private long f_240659_;

    public ChatListener(Minecraft p_240569_) {
        this.f_240348_ = p_240569_;
    }

    public void m_240688_() {
        if (this.f_240660_ == 0L) {
            return;
        }
        if (Util.m_137550_() >= this.f_240659_ + this.f_240660_) {
            Message $$0 = this.f_240677_.poll();
            while ($$0 != null && !$$0.m_240698_()) {
                $$0 = this.f_240677_.poll();
            }
        }
    }

    public void m_240692_(double p_240785_) {
        long $$1 = (long)(p_240785_ * 1000.0);
        if ($$1 == 0L && this.f_240660_ > 0L) {
            this.f_240677_.forEach(Message::m_240698_);
            this.f_240677_.clear();
        }
        this.f_240660_ = $$1;
    }

    public void m_240711_() {
        this.f_240677_.remove().m_240698_();
    }

    public long m_242024_() {
        return this.f_240677_.stream().filter(Message::m_241855_).count();
    }

    public void m_241954_() {
        this.f_240677_.forEach(p_242052_ -> {
            p_242052_.m_241853_();
            p_242052_.m_240698_();
        });
        this.f_240677_.clear();
    }

    public boolean m_240956_(MessageSignature p_241445_) {
        for (Message $$1 : this.f_240677_) {
            if (!$$1.m_241786_(p_241445_)) continue;
            return true;
        }
        return false;
    }

    private boolean m_240706_() {
        return this.f_240660_ > 0L && Util.m_137550_() < this.f_240659_ + this.f_240660_;
    }

    private void m_241112_(Message p_241312_) {
        if (this.m_240706_()) {
            this.f_240677_.add(p_241312_);
        } else {
            p_241312_.m_240698_();
        }
    }

    public void m_240975_(final PlayerChatMessage p_241568_, final ChatType.Bound p_241361_) {
        final boolean $$2 = this.f_240348_.f_91066_.m_231836_().m_231551_();
        final PlayerChatMessage $$3 = $$2 ? p_241568_.m_239022_() : p_241568_;
        final Component $$4 = p_241361_.m_240977_($$3.m_237220_());
        MessageSigner $$5 = p_241568_.m_241067_();
        if (!$$5.m_241005_()) {
            final PlayerInfo $$6 = this.m_241129_($$5.f_240864_());
            final Instant $$7 = Instant.now();
            this.m_241112_(new Message(){
                private boolean f_241627_;

                @Override
                public boolean m_240698_() {
                    if (this.f_241627_) {
                        byte[] $$0 = p_241568_.f_240885_().m_241131_().asBytes();
                        ChatListener.this.m_241023_(p_241568_.f_240875_(), p_241568_.f_240893_(), $$0);
                        return false;
                    }
                    return ChatListener.this.m_241955_(p_241361_, p_241568_, $$4, $$6, $$2, $$7);
                }

                @Override
                public boolean m_241786_(MessageSignature p_242335_) {
                    if (p_241568_.f_240893_().equals(p_242335_)) {
                        this.f_241627_ = true;
                        return true;
                    }
                    return false;
                }

                @Override
                public void m_241853_() {
                    this.f_241627_ = true;
                }

                @Override
                public boolean m_241855_() {
                    return !this.f_241627_;
                }
            });
        } else {
            this.m_241112_(new Message(){

                @Override
                public boolean m_240698_() {
                    return ChatListener.this.m_241171_(p_241361_, $$3, $$4);
                }

                @Override
                public boolean m_241855_() {
                    return true;
                }
            });
        }
    }

    public void m_241138_(SignedMessageHeader p_241319_, MessageSignature p_241390_, byte[] p_241463_) {
        this.m_241112_(() -> this.m_241023_(p_241319_, p_241390_, p_241463_));
    }

    boolean m_241955_(ChatType.Bound p_242406_, PlayerChatMessage p_242174_, Component p_242417_, @Nullable PlayerInfo p_242459_, boolean p_242346_, Instant p_242392_) {
        boolean $$6 = this.m_241794_(p_242406_, p_242174_, p_242417_, p_242459_, p_242346_, p_242392_);
        ClientPacketListener $$7 = this.f_240348_.m_91403_();
        if ($$7 != null) {
            $$7.m_242011_(p_242174_, $$6);
        }
        return $$6;
    }

    private boolean m_241794_(ChatType.Bound p_242290_, PlayerChatMessage p_242317_, Component p_243337_, @Nullable PlayerInfo p_242267_, boolean p_242247_, Instant p_242230_) {
        ChatTrustLevel $$6 = this.m_241839_(p_242317_, p_243337_, p_242267_, p_242230_);
        if ($$6 == ChatTrustLevel.BROKEN_CHAIN) {
            this.m_242665_();
            return true;
        }
        if (p_242247_ && $$6.m_240450_()) {
            return false;
        }
        if (this.f_240348_.m_91246_(p_242317_.m_241067_().f_240864_()) || p_242317_.m_243059_()) {
            return false;
        }
        GuiMessageTag $$7 = $$6.m_240405_(p_242317_);
        MessageSignature $$8 = p_242317_.f_240893_();
        FilterMask $$9 = p_242317_.f_242992_();
        if ($$9.m_243095_()) {
            this.f_240348_.f_91065_.m_93076_().m_240964_(p_243337_, $$8, $$7);
            this.m_241119_(p_242290_, p_242317_.m_237220_());
        } else {
            Component $$10 = $$9.m_243081_(p_242317_.m_241775_());
            if ($$10 != null) {
                this.f_240348_.f_91065_.m_93076_().m_240964_(p_242290_.m_240977_($$10), $$8, $$7);
                this.m_241119_(p_242290_, $$10);
            }
        }
        this.m_240957_(p_242317_, p_242290_, p_242267_, $$6);
        this.f_240659_ = Util.m_137550_();
        return true;
    }

    boolean m_241171_(ChatType.Bound p_241518_, PlayerChatMessage p_241542_, Component p_241510_) {
        this.f_240348_.f_91065_.m_93076_().m_93785_(p_241510_);
        this.m_241119_(p_241518_, p_241542_.m_237220_());
        this.m_240498_(p_241510_, p_241542_.m_241109_());
        this.f_240659_ = Util.m_137550_();
        return true;
    }

    boolean m_241023_(SignedMessageHeader p_241363_, MessageSignature p_241535_, byte[] p_241500_) {
        SignedMessageValidator.State $$4;
        PlayerInfo $$3 = this.m_241129_(p_241363_.f_240866_());
        if ($$3 != null && ($$4 = $$3.m_241043_().m_241126_(p_241363_, p_241535_, p_241500_)) == SignedMessageValidator.State.BROKEN_CHAIN) {
            this.m_242665_();
            return true;
        }
        this.m_241013_(p_241363_, p_241535_, p_241500_);
        return false;
    }

    private void m_242665_() {
        ClientPacketListener $$0 = this.f_240348_.m_91403_();
        if ($$0 != null) {
            $$0.m_6198_().m_129507_(f_242497_);
        }
    }

    private void m_241119_(ChatType.Bound p_241352_, Component p_243262_) {
        this.f_240348_.m_240477_().m_240462_(() -> p_241352_.m_240941_(p_243262_));
    }

    private ChatTrustLevel m_241839_(PlayerChatMessage p_242369_, Component p_242452_, @Nullable PlayerInfo p_242405_, Instant p_242401_) {
        if (this.m_240963_(p_242369_.m_241067_().f_240864_())) {
            return ChatTrustLevel.SECURE;
        }
        return ChatTrustLevel.m_240455_(p_242369_, p_242452_, p_242405_, p_242401_);
    }

    private void m_240957_(PlayerChatMessage p_241337_, ChatType.Bound p_241355_, @Nullable PlayerInfo p_241489_, ChatTrustLevel p_241528_) {
        GameProfile $$5;
        if (p_241489_ != null) {
            GameProfile $$4 = p_241489_.m_105312_();
        } else {
            $$5 = new GameProfile(p_241337_.m_241067_().f_240864_(), p_241355_.f_240886_().getString());
        }
        ChatLog $$6 = this.f_240348_.m_239211_().f_238743_();
        $$6.m_239651_(LoggedChatMessage.m_241912_($$5, p_241355_.f_240886_(), p_241337_, p_241528_));
    }

    private void m_240498_(Component p_240609_, Instant p_240541_) {
        ChatLog $$2 = this.f_240348_.m_239211_().f_238743_();
        $$2.m_239651_(LoggedChatMessage.m_241821_(p_240609_, p_240541_));
    }

    private void m_241013_(SignedMessageHeader p_241328_, MessageSignature p_241317_, byte[] p_241565_) {
        ChatLog $$3 = this.f_240348_.m_239211_().f_238743_();
        $$3.m_239651_(LoggedChatMessageLink.m_241782_(p_241328_, p_241317_, p_241565_));
    }

    @Nullable
    private PlayerInfo m_241129_(UUID p_241471_) {
        ClientPacketListener $$1 = this.f_240348_.m_91403_();
        return $$1 != null ? $$1.m_104949_(p_241471_) : null;
    }

    public void m_240494_(Component p_240522_, boolean p_240642_) {
        if (this.f_240348_.f_91066_.m_231833_().m_231551_().booleanValue() && this.f_240348_.m_91246_(this.m_240473_(p_240522_))) {
            return;
        }
        if (p_240642_) {
            this.f_240348_.f_91065_.m_93063_(p_240522_, false);
        } else {
            this.f_240348_.f_91065_.m_93076_().m_93785_(p_240522_);
            this.m_240498_(p_240522_, Instant.now());
        }
        this.f_240348_.m_240477_().m_168785_(p_240522_);
    }

    private UUID m_240473_(Component p_240595_) {
        String $$1 = StringDecomposer.m_14326_(p_240595_);
        String $$2 = StringUtils.substringBetween((String)$$1, (String)"<", (String)">");
        if ($$2 == null) {
            return Util.f_137441_;
        }
        return this.f_240348_.m_91266_().m_100678_($$2);
    }

    private boolean m_240963_(UUID p_241343_) {
        if (this.f_240348_.m_91090_() && this.f_240348_.f_91074_ != null) {
            UUID $$1 = this.f_240348_.f_91074_.m_36316_().getId();
            return $$1.equals(p_241343_);
        }
        return false;
    }

    static interface Message {
        default public boolean m_241786_(MessageSignature p_242379_) {
            return false;
        }

        default public void m_241853_() {
        }

        public boolean m_240698_();

        default public boolean m_241855_() {
            return false;
        }
    }
}


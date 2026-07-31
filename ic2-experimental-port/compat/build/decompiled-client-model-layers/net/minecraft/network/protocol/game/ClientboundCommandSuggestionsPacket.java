/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.context.StringRange
 *  com.mojang.brigadier.suggestion.Suggestion
 *  com.mojang.brigadier.suggestion.Suggestions
 */
package net.minecraft.network.protocol.game;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundCommandSuggestionsPacket
implements Packet<ClientGamePacketListener> {
    private final int f_131842_;
    private final Suggestions f_131843_;

    public ClientboundCommandSuggestionsPacket(int p_131846_, Suggestions p_131847_) {
        this.f_131842_ = p_131846_;
        this.f_131843_ = p_131847_;
    }

    public ClientboundCommandSuggestionsPacket(FriendlyByteBuf p_178790_) {
        this.f_131842_ = p_178790_.m_130242_();
        int $$1 = p_178790_.m_130242_();
        int $$2 = p_178790_.m_130242_();
        StringRange $$3 = StringRange.between((int)$$1, (int)($$1 + $$2));
        List $$4 = p_178790_.m_236845_(p_178793_ -> {
            String $$2 = p_178793_.m_130277_();
            Component $$3 = (Component)p_178793_.m_236868_(FriendlyByteBuf::m_130238_);
            return new Suggestion($$3, $$2, (Message)$$3);
        });
        this.f_131843_ = new Suggestions($$3, $$4);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131856_) {
        p_131856_.m_130130_(this.f_131842_);
        p_131856_.m_130130_(this.f_131843_.getRange().getStart());
        p_131856_.m_130130_(this.f_131843_.getRange().getLength());
        p_131856_.m_236828_(this.f_131843_.getList(), (p_237617_, p_237618_) -> {
            p_237617_.m_130070_(p_237618_.getText());
            p_237617_.m_236821_(p_237618_.getTooltip(), (p_237614_, p_237615_) -> p_237614_.m_130083_(ComponentUtils.m_130729_(p_237615_)));
        });
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131853_) {
        p_131853_.m_7589_(this);
    }

    public int m_131854_() {
        return this.f_131842_;
    }

    public Suggestions m_131857_() {
        return this.f_131843_;
    }
}


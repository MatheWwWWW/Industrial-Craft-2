/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.player;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraft.util.OptionEnum;

public final class ChatVisiblity
extends Enum<ChatVisiblity>
implements OptionEnum {
    public static final /* enum */ ChatVisiblity FULL = new ChatVisiblity(0, "options.chat.visibility.full");
    public static final /* enum */ ChatVisiblity SYSTEM = new ChatVisiblity(1, "options.chat.visibility.system");
    public static final /* enum */ ChatVisiblity HIDDEN = new ChatVisiblity(2, "options.chat.visibility.hidden");
    private static final ChatVisiblity[] f_35955_;
    private final int f_35956_;
    private final String f_35957_;
    private static final /* synthetic */ ChatVisiblity[] $VALUES;

    public static ChatVisiblity[] values() {
        return (ChatVisiblity[])$VALUES.clone();
    }

    public static ChatVisiblity valueOf(String p_35972_) {
        return Enum.valueOf(ChatVisiblity.class, p_35972_);
    }

    private ChatVisiblity(int p_35963_, String p_35964_) {
        this.f_35956_ = p_35963_;
        this.f_35957_ = p_35964_;
    }

    @Override
    public int m_35965_() {
        return this.f_35956_;
    }

    @Override
    public String m_35968_() {
        return this.f_35957_;
    }

    public static ChatVisiblity m_35966_(int p_35967_) {
        return f_35955_[Mth.m_14100_(p_35967_, f_35955_.length)];
    }

    private static /* synthetic */ ChatVisiblity[] m_150063_() {
        return new ChatVisiblity[]{FULL, SYSTEM, HIDDEN};
    }

    static {
        $VALUES = ChatVisiblity.m_150063_();
        f_35955_ = (ChatVisiblity[])Arrays.stream(ChatVisiblity.values()).sorted(Comparator.comparingInt(ChatVisiblity::m_35965_)).toArray(ChatVisiblity[]::new);
    }
}


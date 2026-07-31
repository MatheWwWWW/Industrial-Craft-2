/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.packs.repository;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public interface PackSource {
    public static final PackSource f_10527_ = PackSource.m_10532_();
    public static final PackSource f_10528_ = PackSource.m_10533_("pack.source.builtin");
    public static final PackSource f_10529_ = PackSource.m_10533_("pack.source.world");
    public static final PackSource f_10530_ = PackSource.m_10533_("pack.source.server");

    public Component m_10540_(Component var1);

    public static PackSource m_10532_() {
        return p_10536_ -> p_10536_;
    }

    public static PackSource m_10533_(String p_10534_) {
        MutableComponent $$1 = Component.m_237115_(p_10534_);
        return p_10539_ -> Component.m_237110_("pack.nameAndSource", p_10539_, $$1).m_130940_(ChatFormatting.GRAY);
    }
}


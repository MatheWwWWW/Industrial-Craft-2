/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import net.minecraft.network.chat.Component;

public interface OptionEnum {
    public int m_35965_();

    public String m_35968_();

    default public Component m_216301_() {
        return Component.m_237115_(this.m_35968_());
    }
}


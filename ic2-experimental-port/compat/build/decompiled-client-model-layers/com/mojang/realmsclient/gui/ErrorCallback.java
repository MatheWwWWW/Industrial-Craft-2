/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.gui;

import net.minecraft.network.chat.Component;

public interface ErrorCallback {
    public void m_5673_(Component var1);

    default public void m_87791_(String p_87792_) {
        this.m_5673_(Component.m_237113_(p_87792_));
    }
}


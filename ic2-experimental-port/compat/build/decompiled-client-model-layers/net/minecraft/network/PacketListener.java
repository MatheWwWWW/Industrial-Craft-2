/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;

public interface PacketListener {
    public void m_7026_(Component var1);

    public Connection m_6198_();

    default public boolean m_201767_() {
        return true;
    }
}


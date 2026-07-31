/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;

public interface Packet<T extends PacketListener> {
    public void m_5779_(FriendlyByteBuf var1);

    public void m_5797_(T var1);

    default public boolean m_6588_() {
        return false;
    }
}


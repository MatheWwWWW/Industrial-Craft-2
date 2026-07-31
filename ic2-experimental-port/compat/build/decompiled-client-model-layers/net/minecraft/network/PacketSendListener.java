/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.network.protocol.Packet;

public interface PacketSendListener {
    public static PacketSendListener m_243092_(final Runnable p_243267_) {
        return new PacketSendListener(){

            @Override
            public void m_243096_() {
                p_243267_.run();
            }

            @Override
            @Nullable
            public Packet<?> m_243103_() {
                p_243267_.run();
                return null;
            }
        };
    }

    public static PacketSendListener m_243073_(final Supplier<Packet<?>> p_243289_) {
        return new PacketSendListener(){

            @Override
            @Nullable
            public Packet<?> m_243103_() {
                return (Packet)p_243289_.get();
            }
        };
    }

    default public void m_243096_() {
    }

    @Nullable
    default public Packet<?> m_243103_() {
        return null;
    }
}


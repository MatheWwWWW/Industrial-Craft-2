/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.multiplayer.resolver;

import java.net.InetSocketAddress;

public interface ResolvedServerAddress {
    public String m_142727_();

    public String m_142728_();

    public int m_142599_();

    public InetSocketAddress m_142641_();

    public static ResolvedServerAddress m_171845_(final InetSocketAddress p_171846_) {
        return new ResolvedServerAddress(){

            @Override
            public String m_142727_() {
                return p_171846_.getAddress().getHostName();
            }

            @Override
            public String m_142728_() {
                return p_171846_.getAddress().getHostAddress();
            }

            @Override
            public int m_142599_() {
                return p_171846_.getPort();
            }

            @Override
            public InetSocketAddress m_142641_() {
                return p_171846_;
            }
        };
    }
}


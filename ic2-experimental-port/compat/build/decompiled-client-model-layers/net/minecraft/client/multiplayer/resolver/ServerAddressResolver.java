/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer.resolver;

import com.mojang.logging.LogUtils;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import org.slf4j.Logger;

@FunctionalInterface
public interface ServerAddressResolver {
    public static final Logger f_171874_ = LogUtils.getLogger();
    public static final ServerAddressResolver f_171875_ = p_171878_ -> {
        try {
            InetAddress $$1 = InetAddress.getByName(p_171878_.m_171863_());
            return Optional.of(ResolvedServerAddress.m_171845_(new InetSocketAddress($$1, p_171878_.m_171866_())));
        }
        catch (UnknownHostException $$2) {
            f_171874_.debug("Couldn't resolve server {} address", (Object)p_171878_.m_171863_(), (Object)$$2);
            return Optional.empty();
        }
    };

    public Optional<ResolvedServerAddress> m_171879_(ServerAddress var1);
}


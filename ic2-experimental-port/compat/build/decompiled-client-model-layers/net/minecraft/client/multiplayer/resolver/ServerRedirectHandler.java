/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer.resolver;

import com.mojang.logging.LogUtils;
import java.util.Hashtable;
import java.util.Optional;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import org.slf4j.Logger;

@FunctionalInterface
public interface ServerRedirectHandler {
    public static final Logger f_171892_ = LogUtils.getLogger();
    public static final ServerRedirectHandler f_171893_ = p_171897_ -> Optional.empty();

    public Optional<ServerAddress> m_171901_(ServerAddress var1);

    /*
     * WARNING - void declaration
     */
    public static ServerRedirectHandler m_171895_() {
        void $$4;
        try {
            String $$0 = "com.sun.jndi.dns.DnsContextFactory";
            Class.forName("com.sun.jndi.dns.DnsContextFactory");
            Hashtable<String, String> $$1 = new Hashtable<String, String>();
            $$1.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            $$1.put("java.naming.provider.url", "dns:");
            $$1.put("com.sun.jndi.dns.timeout.retries", "1");
            InitialDirContext $$2 = new InitialDirContext($$1);
        }
        catch (Throwable $$3) {
            f_171892_.error("Failed to initialize SRV redirect resolved, some servers might not work", $$3);
            return f_171893_;
        }
        return arg_0 -> ServerRedirectHandler.m_171898_((DirContext)$$4, arg_0);
    }

    private static /* synthetic */ Optional m_171898_(DirContext p_171899_, ServerAddress p_171900_) {
        if (p_171900_.m_171866_() == 25565) {
            try {
                Attributes $$2 = p_171899_.getAttributes("_minecraft._tcp." + p_171900_.m_171863_(), new String[]{"SRV"});
                Attribute $$3 = $$2.get("srv");
                if ($$3 != null) {
                    String[] $$4 = $$3.get().toString().split(" ", 4);
                    return Optional.of(new ServerAddress($$4[3], ServerAddress.m_171869_($$4[2])));
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return Optional.empty();
    }
}


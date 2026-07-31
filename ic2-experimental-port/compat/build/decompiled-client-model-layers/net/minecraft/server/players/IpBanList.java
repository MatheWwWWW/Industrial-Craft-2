/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;
import javax.annotation.Nullable;
import net.minecraft.server.players.IpBanListEntry;
import net.minecraft.server.players.StoredUserEntry;
import net.minecraft.server.players.StoredUserList;

public class IpBanList
extends StoredUserList<String, IpBanListEntry> {
    public IpBanList(File p_11036_) {
        super(p_11036_);
    }

    @Override
    protected StoredUserEntry<String> m_6666_(JsonObject p_11038_) {
        return new IpBanListEntry(p_11038_);
    }

    public boolean m_11041_(SocketAddress p_11042_) {
        String $$1 = this.m_11045_(p_11042_);
        return this.m_11396_($$1);
    }

    public boolean m_11039_(String p_11040_) {
        return this.m_11396_(p_11040_);
    }

    @Nullable
    public IpBanListEntry m_11043_(SocketAddress p_11044_) {
        String $$1 = this.m_11045_(p_11044_);
        return (IpBanListEntry)this.m_11388_($$1);
    }

    private String m_11045_(SocketAddress p_11046_) {
        String $$1 = p_11046_.toString();
        if ($$1.contains("/")) {
            $$1 = $$1.substring($$1.indexOf(47) + 1);
        }
        if ($$1.contains(":")) {
            $$1 = $$1.substring(0, $$1.indexOf(58));
        }
        return $$1;
    }
}


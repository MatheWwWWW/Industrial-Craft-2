/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.server.players.BanListEntry;

public class IpBanListEntry
extends BanListEntry<String> {
    public IpBanListEntry(String p_11050_) {
        this(p_11050_, (Date)null, (String)null, (Date)null, (String)null);
    }

    public IpBanListEntry(String p_11052_, @Nullable Date p_11053_, @Nullable String p_11054_, @Nullable Date p_11055_, @Nullable String p_11056_) {
        super(p_11052_, p_11053_, p_11054_, p_11055_, p_11056_);
    }

    @Override
    public Component m_8003_() {
        return Component.m_237113_(String.valueOf(this.m_11373_()));
    }

    public IpBanListEntry(JsonObject p_11048_) {
        super(IpBanListEntry.m_11059_(p_11048_), p_11048_);
    }

    private static String m_11059_(JsonObject p_11060_) {
        return p_11060_.has("ip") ? p_11060_.get("ip").getAsString() : null;
    }

    @Override
    protected void m_6009_(JsonObject p_11058_) {
        if (this.m_11373_() == null) {
            return;
        }
        p_11058_.addProperty("ip", (String)this.m_11373_());
        super.m_6009_(p_11058_);
    }
}


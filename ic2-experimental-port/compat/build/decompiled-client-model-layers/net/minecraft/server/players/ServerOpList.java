/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import java.util.Objects;
import net.minecraft.server.players.ServerOpListEntry;
import net.minecraft.server.players.StoredUserEntry;
import net.minecraft.server.players.StoredUserList;

public class ServerOpList
extends StoredUserList<GameProfile, ServerOpListEntry> {
    public ServerOpList(File p_11345_) {
        super(p_11345_);
    }

    @Override
    protected StoredUserEntry<GameProfile> m_6666_(JsonObject p_11348_) {
        return new ServerOpListEntry(p_11348_);
    }

    @Override
    public String[] m_5875_() {
        return (String[])this.m_11395_().stream().map(StoredUserEntry::m_11373_).filter(Objects::nonNull).map(GameProfile::getName).toArray(String[]::new);
    }

    public boolean m_11351_(GameProfile p_11352_) {
        ServerOpListEntry $$1 = (ServerOpListEntry)this.m_11388_(p_11352_);
        if ($$1 != null) {
            return $$1.m_11366_();
        }
        return false;
    }

    @Override
    protected String m_5981_(GameProfile p_11354_) {
        return p_11354_.getId().toString();
    }

    @Override
    protected /* synthetic */ String m_5981_(Object object) {
        return this.m_5981_((GameProfile)object);
    }
}


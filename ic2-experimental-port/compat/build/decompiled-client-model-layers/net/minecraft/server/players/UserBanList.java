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
import net.minecraft.server.players.StoredUserEntry;
import net.minecraft.server.players.StoredUserList;
import net.minecraft.server.players.UserBanListEntry;

public class UserBanList
extends StoredUserList<GameProfile, UserBanListEntry> {
    public UserBanList(File p_11402_) {
        super(p_11402_);
    }

    @Override
    protected StoredUserEntry<GameProfile> m_6666_(JsonObject p_11405_) {
        return new UserBanListEntry(p_11405_);
    }

    public boolean m_11406_(GameProfile p_11407_) {
        return this.m_11396_(p_11407_);
    }

    @Override
    public String[] m_5875_() {
        return (String[])this.m_11395_().stream().map(StoredUserEntry::m_11373_).filter(Objects::nonNull).map(GameProfile::getName).toArray(String[]::new);
    }

    @Override
    protected String m_5981_(GameProfile p_11411_) {
        return p_11411_.getId().toString();
    }

    @Override
    protected /* synthetic */ String m_5981_(Object object) {
        return this.m_5981_((GameProfile)object);
    }
}


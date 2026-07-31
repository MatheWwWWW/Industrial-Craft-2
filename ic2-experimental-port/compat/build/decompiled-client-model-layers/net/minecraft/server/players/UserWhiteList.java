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
import net.minecraft.server.players.UserWhiteListEntry;

public class UserWhiteList
extends StoredUserList<GameProfile, UserWhiteListEntry> {
    public UserWhiteList(File p_11449_) {
        super(p_11449_);
    }

    @Override
    protected StoredUserEntry<GameProfile> m_6666_(JsonObject p_11452_) {
        return new UserWhiteListEntry(p_11452_);
    }

    public boolean m_11453_(GameProfile p_11454_) {
        return this.m_11396_(p_11454_);
    }

    @Override
    public String[] m_5875_() {
        return (String[])this.m_11395_().stream().map(StoredUserEntry::m_11373_).filter(Objects::nonNull).map(GameProfile::getName).toArray(String[]::new);
    }

    @Override
    protected String m_5981_(GameProfile p_11458_) {
        return p_11458_.getId().toString();
    }

    @Override
    protected /* synthetic */ String m_5981_(Object object) {
        return this.m_5981_((GameProfile)object);
    }
}


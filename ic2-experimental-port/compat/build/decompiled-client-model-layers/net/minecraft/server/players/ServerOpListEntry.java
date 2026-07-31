/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 */
package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.server.players.StoredUserEntry;

public class ServerOpListEntry
extends StoredUserEntry<GameProfile> {
    private final int f_11355_;
    private final boolean f_11356_;

    public ServerOpListEntry(GameProfile p_11360_, int p_11361_, boolean p_11362_) {
        super(p_11360_);
        this.f_11355_ = p_11361_;
        this.f_11356_ = p_11362_;
    }

    public ServerOpListEntry(JsonObject p_11358_) {
        super(ServerOpListEntry.m_11367_(p_11358_));
        this.f_11355_ = p_11358_.has("level") ? p_11358_.get("level").getAsInt() : 0;
        this.f_11356_ = p_11358_.has("bypassesPlayerLimit") && p_11358_.get("bypassesPlayerLimit").getAsBoolean();
    }

    public int m_11363_() {
        return this.f_11355_;
    }

    public boolean m_11366_() {
        return this.f_11356_;
    }

    @Override
    protected void m_6009_(JsonObject p_11365_) {
        if (this.m_11373_() == null) {
            return;
        }
        p_11365_.addProperty("uuid", ((GameProfile)this.m_11373_()).getId() == null ? "" : ((GameProfile)this.m_11373_()).getId().toString());
        p_11365_.addProperty("name", ((GameProfile)this.m_11373_()).getName());
        p_11365_.addProperty("level", (Number)this.f_11355_);
        p_11365_.addProperty("bypassesPlayerLimit", Boolean.valueOf(this.f_11356_));
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    private static GameProfile m_11367_(JsonObject p_11368_) {
        void $$4;
        if (!p_11368_.has("uuid") || !p_11368_.has("name")) {
            return null;
        }
        String $$1 = p_11368_.get("uuid").getAsString();
        try {
            UUID $$2 = UUID.fromString($$1);
        }
        catch (Throwable $$3) {
            return null;
        }
        return new GameProfile((UUID)$$4, p_11368_.get("name").getAsString());
    }
}


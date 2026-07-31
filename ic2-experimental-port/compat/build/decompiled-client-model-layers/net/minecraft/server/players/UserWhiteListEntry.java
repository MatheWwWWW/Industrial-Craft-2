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
import java.util.UUID;
import net.minecraft.server.players.StoredUserEntry;

public class UserWhiteListEntry
extends StoredUserEntry<GameProfile> {
    public UserWhiteListEntry(GameProfile p_11462_) {
        super(p_11462_);
    }

    public UserWhiteListEntry(JsonObject p_11460_) {
        super(UserWhiteListEntry.m_11465_(p_11460_));
    }

    @Override
    protected void m_6009_(JsonObject p_11464_) {
        if (this.m_11373_() == null) {
            return;
        }
        p_11464_.addProperty("uuid", ((GameProfile)this.m_11373_()).getId() == null ? "" : ((GameProfile)this.m_11373_()).getId().toString());
        p_11464_.addProperty("name", ((GameProfile)this.m_11373_()).getName());
    }

    /*
     * WARNING - void declaration
     */
    private static GameProfile m_11465_(JsonObject p_11466_) {
        void $$4;
        if (!p_11466_.has("uuid") || !p_11466_.has("name")) {
            return null;
        }
        String $$1 = p_11466_.get("uuid").getAsString();
        try {
            UUID $$2 = UUID.fromString($$1);
        }
        catch (Throwable $$3) {
            return null;
        }
        return new GameProfile((UUID)$$4, p_11466_.get("name").getAsString());
    }
}


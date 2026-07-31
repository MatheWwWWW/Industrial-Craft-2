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
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.server.players.BanListEntry;

public class UserBanListEntry
extends BanListEntry<GameProfile> {
    public UserBanListEntry(GameProfile p_11436_) {
        this(p_11436_, (Date)null, (String)null, (Date)null, (String)null);
    }

    public UserBanListEntry(GameProfile p_11438_, @Nullable Date p_11439_, @Nullable String p_11440_, @Nullable Date p_11441_, @Nullable String p_11442_) {
        super(p_11438_, p_11439_, p_11440_, p_11441_, p_11442_);
    }

    public UserBanListEntry(JsonObject p_11434_) {
        super(UserBanListEntry.m_11445_(p_11434_), p_11434_);
    }

    @Override
    protected void m_6009_(JsonObject p_11444_) {
        if (this.m_11373_() == null) {
            return;
        }
        p_11444_.addProperty("uuid", ((GameProfile)this.m_11373_()).getId() == null ? "" : ((GameProfile)this.m_11373_()).getId().toString());
        p_11444_.addProperty("name", ((GameProfile)this.m_11373_()).getName());
        super.m_6009_(p_11444_);
    }

    @Override
    public Component m_8003_() {
        GameProfile $$0 = (GameProfile)this.m_11373_();
        return Component.m_237113_($$0.getName() != null ? $$0.getName() : Objects.toString($$0.getId(), "(Unknown)"));
    }

    /*
     * WARNING - void declaration
     */
    private static GameProfile m_11445_(JsonObject p_11446_) {
        void $$4;
        if (!p_11446_.has("uuid") || !p_11446_.has("name")) {
            return null;
        }
        String $$1 = p_11446_.get("uuid").getAsString();
        try {
            UUID $$2 = UUID.fromString($$1);
        }
        catch (Throwable $$3) {
            return null;
        }
        return new GameProfile((UUID)$$4, p_11446_.get("name").getAsString());
    }
}


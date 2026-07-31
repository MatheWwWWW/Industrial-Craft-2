/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.bridge.game.GameSession
 */
package net.minecraft.client;

import com.mojang.bridge.game.GameSession;
import java.util.UUID;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;

public class Session
implements GameSession {
    private final int f_92319_;
    private final boolean f_92320_;
    private final String f_92321_;
    private final String f_92322_;
    private final UUID f_92323_;

    public Session(ClientLevel p_92325_, LocalPlayer p_92326_, ClientPacketListener p_92327_) {
        this.f_92319_ = p_92327_.m_105142_().size();
        this.f_92320_ = !p_92327_.m_6198_().m_129531_();
        this.f_92321_ = p_92325_.m_46791_().m_19036_();
        PlayerInfo $$3 = p_92327_.m_104949_(p_92326_.m_20148_());
        this.f_92322_ = $$3 != null ? $$3.m_105325_().m_46405_() : "unknown";
        this.f_92323_ = p_92327_.m_105150_();
    }

    public int getPlayerCount() {
        return this.f_92319_;
    }

    public boolean isRemoteServer() {
        return this.f_92320_;
    }

    public String getDifficulty() {
        return this.f_92321_;
    }

    public String getGameMode() {
        return this.f_92322_;
    }

    public UUID getSessionId() {
        return this.f_92323_;
    }
}


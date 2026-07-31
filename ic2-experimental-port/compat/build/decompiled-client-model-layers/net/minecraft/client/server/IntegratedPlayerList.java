/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.client.server;

import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.storage.PlayerDataStorage;

public class IntegratedPlayerList
extends PlayerList {
    private CompoundTag f_120001_;

    public IntegratedPlayerList(IntegratedServer p_205649_, RegistryAccess.Frozen p_205650_, PlayerDataStorage p_205651_) {
        super(p_205649_, p_205650_, p_205651_, 8);
        this.m_11217_(10);
    }

    @Override
    protected void m_6765_(ServerPlayer p_120011_) {
        if (this.m_7873_().m_7779_(p_120011_.m_36316_())) {
            this.f_120001_ = p_120011_.m_20240_(new CompoundTag());
        }
        super.m_6765_(p_120011_);
    }

    @Override
    public Component m_6418_(SocketAddress p_120007_, GameProfile p_120008_) {
        if (this.m_7873_().m_7779_(p_120008_) && this.m_11255_(p_120008_.getName()) != null) {
            return Component.m_237115_("multiplayer.disconnect.name_taken");
        }
        return super.m_6418_(p_120007_, p_120008_);
    }

    @Override
    public IntegratedServer m_7873_() {
        return (IntegratedServer)super.m_7873_();
    }

    @Override
    public CompoundTag m_6960_() {
        return this.f_120001_;
    }

    @Override
    public /* synthetic */ MinecraftServer m_7873_() {
        return this.m_7873_();
    }
}


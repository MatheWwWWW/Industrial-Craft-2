/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.dedicated;

import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.storage.PlayerDataStorage;
import org.slf4j.Logger;

public class DedicatedPlayerList
extends PlayerList {
    private static final Logger f_139571_ = LogUtils.getLogger();

    public DedicatedPlayerList(DedicatedServer p_203709_, RegistryAccess.Frozen p_203710_, PlayerDataStorage p_203711_) {
        super(p_203709_, p_203710_, p_203711_, p_203709_.m_7913_().f_139715_);
        DedicatedServerProperties $$3 = p_203709_.m_7913_();
        this.m_11217_($$3.f_139714_);
        this.m_184211_($$3.f_183715_);
        super.m_6628_($$3.f_139726_.get());
        this.m_139596_();
        this.m_139594_();
        this.m_139595_();
        this.m_139593_();
        this.m_139597_();
        this.m_139578_();
        this.m_139577_();
        if (!this.m_11305_().m_11385_().exists()) {
            this.m_139579_();
        }
    }

    @Override
    public void m_6628_(boolean p_139584_) {
        super.m_6628_(p_139584_);
        this.m_7873_().m_139688_(p_139584_);
    }

    @Override
    public void m_5749_(GameProfile p_139582_) {
        super.m_5749_(p_139582_);
        this.m_139577_();
    }

    @Override
    public void m_5750_(GameProfile p_139587_) {
        super.m_5750_(p_139587_);
        this.m_139577_();
    }

    @Override
    public void m_7542_() {
        this.m_139578_();
    }

    private void m_139593_() {
        try {
            this.m_11299_().m_11398_();
        }
        catch (IOException $$0) {
            f_139571_.warn("Failed to save ip banlist: ", (Throwable)$$0);
        }
    }

    private void m_139594_() {
        try {
            this.m_11295_().m_11398_();
        }
        catch (IOException $$0) {
            f_139571_.warn("Failed to save user banlist: ", (Throwable)$$0);
        }
    }

    private void m_139595_() {
        try {
            this.m_11299_().m_11399_();
        }
        catch (IOException $$0) {
            f_139571_.warn("Failed to load ip banlist: ", (Throwable)$$0);
        }
    }

    private void m_139596_() {
        try {
            this.m_11295_().m_11399_();
        }
        catch (IOException $$0) {
            f_139571_.warn("Failed to load user banlist: ", (Throwable)$$0);
        }
    }

    private void m_139597_() {
        try {
            this.m_11307_().m_11399_();
        }
        catch (Exception $$0) {
            f_139571_.warn("Failed to load operators list: ", (Throwable)$$0);
        }
    }

    private void m_139577_() {
        try {
            this.m_11307_().m_11398_();
        }
        catch (Exception $$0) {
            f_139571_.warn("Failed to save operators list: ", (Throwable)$$0);
        }
    }

    private void m_139578_() {
        try {
            this.m_11305_().m_11399_();
        }
        catch (Exception $$0) {
            f_139571_.warn("Failed to load white-list: ", (Throwable)$$0);
        }
    }

    private void m_139579_() {
        try {
            this.m_11305_().m_11398_();
        }
        catch (Exception $$0) {
            f_139571_.warn("Failed to save white-list: ", (Throwable)$$0);
        }
    }

    @Override
    public boolean m_5764_(GameProfile p_139590_) {
        return !this.m_11311_() || this.m_11303_(p_139590_) || this.m_11305_().m_11453_(p_139590_);
    }

    @Override
    public DedicatedServer m_7873_() {
        return (DedicatedServer)super.m_7873_();
    }

    @Override
    public boolean m_5765_(GameProfile p_139592_) {
        return this.m_11307_().m_11351_(p_139592_);
    }

    @Override
    public /* synthetic */ MinecraftServer m_7873_() {
        return this.m_7873_();
    }
}


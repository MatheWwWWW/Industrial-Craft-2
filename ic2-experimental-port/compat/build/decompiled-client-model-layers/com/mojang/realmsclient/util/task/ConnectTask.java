/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerAddress;
import com.mojang.realmsclient.util.task.LongRunningTask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsConnect;

public class ConnectTask
extends LongRunningTask {
    private final RealmsConnect f_90305_;
    private final RealmsServer f_90306_;
    private final RealmsServerAddress f_90307_;

    public ConnectTask(Screen p_90309_, RealmsServer p_90310_, RealmsServerAddress p_90311_) {
        this.f_90306_ = p_90310_;
        this.f_90307_ = p_90311_;
        this.f_90305_ = new RealmsConnect(p_90309_);
    }

    @Override
    public void run() {
        this.m_90409_(Component.m_237115_("mco.connect.connecting"));
        this.f_90305_.m_175031_(this.f_90306_, ServerAddress.m_171864_(this.f_90307_.f_87565_));
    }

    @Override
    public void m_5520_() {
        this.f_90305_.m_120694_();
        Minecraft.m_91087_().m_91100_().m_235009_();
    }

    @Override
    public void m_5519_() {
        this.f_90305_.m_120704_();
    }
}


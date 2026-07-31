/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.ScreenManager;
import com.mojang.blaze3d.platform.Window;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;

public final class VirtualScreen
implements AutoCloseable {
    private final Minecraft f_110868_;
    private final ScreenManager f_110869_;

    public VirtualScreen(Minecraft p_110871_) {
        this.f_110868_ = p_110871_;
        this.f_110869_ = new ScreenManager(Monitor::new);
    }

    public Window m_110872_(DisplayData p_110873_, @Nullable String p_110874_, String p_110875_) {
        return new Window(this.f_110868_, this.f_110869_, p_110873_, p_110874_, p_110875_);
    }

    @Override
    public void close() {
        this.f_110869_.m_85266_();
    }
}


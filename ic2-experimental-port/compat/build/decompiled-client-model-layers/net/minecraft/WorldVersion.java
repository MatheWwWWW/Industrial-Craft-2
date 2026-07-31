/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.bridge.game.GameVersion
 */
package net.minecraft;

import com.mojang.bridge.game.GameVersion;
import net.minecraft.world.level.storage.DataVersion;

public interface WorldVersion
extends GameVersion {
    @Deprecated
    default public int getWorldVersion() {
        return this.m_183476_().m_193006_();
    }

    @Deprecated
    default public String getSeriesId() {
        return this.m_183476_().m_193005_();
    }

    public DataVersion m_183476_();
}


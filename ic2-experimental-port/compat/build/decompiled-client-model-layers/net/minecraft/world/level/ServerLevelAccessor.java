/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public interface ServerLevelAccessor
extends LevelAccessor {
    public ServerLevel m_6018_();

    default public void m_47205_(Entity p_47206_) {
        p_47206_.m_20199_().forEach(this::m_7967_);
    }
}


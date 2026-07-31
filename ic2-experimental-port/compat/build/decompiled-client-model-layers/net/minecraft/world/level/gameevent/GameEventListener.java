/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.gameevent;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;

public interface GameEventListener {
    default public boolean m_214054_() {
        return false;
    }

    public PositionSource m_142460_();

    public int m_142078_();

    public boolean m_214068_(ServerLevel var1, GameEvent.Message var2);
}


/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.ticks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.ticks.ScheduledTick;

public interface TickAccess<T> {
    public void m_183393_(ScheduledTick<T> var1);

    public boolean m_183582_(BlockPos var1, T var2);

    public int m_183574_();
}


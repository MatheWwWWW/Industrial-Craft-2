/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.gametest.framework;

import net.minecraft.gametest.framework.GameTestInfo;

class ExhaustedAttemptsException
extends Throwable {
    public ExhaustedAttemptsException(int p_177039_, int p_177040_, GameTestInfo p_177041_) {
        super("Not enough successes: " + p_177040_ + " out of " + p_177039_ + " attempts. Required successes: " + p_177041_.m_177493_() + ". max attempts: " + p_177041_.m_177492_() + ".", p_177041_.m_127642_());
    }
}


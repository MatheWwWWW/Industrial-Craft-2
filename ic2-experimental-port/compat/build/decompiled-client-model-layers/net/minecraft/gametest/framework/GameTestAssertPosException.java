/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.gametest.framework;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestAssertException;

public class GameTestAssertPosException
extends GameTestAssertException {
    private final BlockPos f_127493_;
    private final BlockPos f_127494_;
    private final long f_127495_;

    public GameTestAssertPosException(String p_177051_, BlockPos p_177052_, BlockPos p_177053_, long p_177054_) {
        super(p_177051_);
        this.f_127493_ = p_177052_;
        this.f_127494_ = p_177053_;
        this.f_127495_ = p_177054_;
    }

    @Override
    public String getMessage() {
        String $$0 = this.f_127493_.m_123341_() + "," + this.f_127493_.m_123342_() + "," + this.f_127493_.m_123343_() + " (relative: " + this.f_127494_.m_123341_() + "," + this.f_127494_.m_123342_() + "," + this.f_127494_.m_123343_() + ")";
        return super.getMessage() + " at " + $$0 + " (t=" + this.f_127495_ + ")";
    }

    @Nullable
    public String m_127496_() {
        return super.getMessage();
    }

    @Nullable
    public BlockPos m_177055_() {
        return this.f_127494_;
    }

    @Nullable
    public BlockPos m_127497_() {
        return this.f_127493_;
    }
}


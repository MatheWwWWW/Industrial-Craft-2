/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.gametest.framework;

import javax.annotation.Nullable;

class GameTestEvent {
    @Nullable
    public final Long f_127593_;
    public final Runnable f_127594_;

    private GameTestEvent(@Nullable Long p_177092_, Runnable p_177093_) {
        this.f_127593_ = p_177092_;
        this.f_127594_ = p_177093_;
    }

    static GameTestEvent m_177097_(Runnable p_177098_) {
        return new GameTestEvent(null, p_177098_);
    }

    static GameTestEvent m_177094_(long p_177095_, Runnable p_177096_) {
        return new GameTestEvent(p_177095_, p_177096_);
    }
}


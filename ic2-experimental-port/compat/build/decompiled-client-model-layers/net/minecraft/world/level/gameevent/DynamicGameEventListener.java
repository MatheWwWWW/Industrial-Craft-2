/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.gameevent;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.level.gameevent.GameEventListener;

public class DynamicGameEventListener<T extends GameEventListener> {
    private T f_223612_;
    @Nullable
    private SectionPos f_223613_;

    public DynamicGameEventListener(T p_223615_) {
        this.f_223612_ = p_223615_;
    }

    public void m_223617_(ServerLevel p_223618_) {
        this.m_223641_(p_223618_);
    }

    public void m_223628_(T p_223629_, @Nullable Level p_223630_) {
        Object $$2 = this.f_223612_;
        if ($$2 == p_223629_) {
            return;
        }
        if (p_223630_ instanceof ServerLevel) {
            ServerLevel $$3 = (ServerLevel)p_223630_;
            DynamicGameEventListener.m_223622_($$3, this.f_223613_, p_223640_ -> p_223640_.m_142500_((GameEventListener)$$2));
            DynamicGameEventListener.m_223622_($$3, this.f_223613_, p_223633_ -> p_223633_.m_142501_((GameEventListener)p_223629_));
        }
        this.f_223612_ = p_223629_;
    }

    public T m_223616_() {
        return this.f_223612_;
    }

    public void m_223634_(ServerLevel p_223635_) {
        DynamicGameEventListener.m_223622_(p_223635_, this.f_223613_, p_223644_ -> p_223644_.m_142500_((GameEventListener)this.f_223612_));
    }

    public void m_223641_(ServerLevel p_223642_) {
        this.f_223612_.m_142460_().m_142502_(p_223642_).map(SectionPos::m_235863_).ifPresent(p_223621_ -> {
            if (this.f_223613_ == null || !this.f_223613_.equals(p_223621_)) {
                DynamicGameEventListener.m_223622_(p_223642_, this.f_223613_, p_223637_ -> p_223637_.m_142500_((GameEventListener)this.f_223612_));
                this.f_223613_ = p_223621_;
                DynamicGameEventListener.m_223622_(p_223642_, this.f_223613_, p_223627_ -> p_223627_.m_142501_((GameEventListener)this.f_223612_));
            }
        });
    }

    private static void m_223622_(LevelReader p_223623_, @Nullable SectionPos p_223624_, Consumer<GameEventDispatcher> p_223625_) {
        if (p_223624_ == null) {
            return;
        }
        ChunkAccess $$3 = p_223623_.m_6522_(p_223624_.m_123170_(), p_223624_.m_123222_(), ChunkStatus.f_62326_, false);
        if ($$3 != null) {
            p_223625_.accept($$3.m_142336_(p_223624_.m_123206_()));
        }
    }
}


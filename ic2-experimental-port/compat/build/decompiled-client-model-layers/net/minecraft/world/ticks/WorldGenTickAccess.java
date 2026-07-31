/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.ticks;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraft.world.ticks.TickContainerAccess;

public class WorldGenTickAccess<T>
implements LevelTickAccess<T> {
    private final Function<BlockPos, TickContainerAccess<T>> f_193452_;

    public WorldGenTickAccess(Function<BlockPos, TickContainerAccess<T>> p_193454_) {
        this.f_193452_ = p_193454_;
    }

    @Override
    public boolean m_183582_(BlockPos p_193459_, T p_193460_) {
        return this.f_193452_.apply(p_193459_).m_183582_(p_193459_, p_193460_);
    }

    @Override
    public void m_183393_(ScheduledTick<T> p_193457_) {
        this.f_193452_.apply(p_193457_.f_193377_()).m_183393_(p_193457_);
    }

    @Override
    public boolean m_183588_(BlockPos p_193462_, T p_193463_) {
        return false;
    }

    @Override
    public int m_183574_() {
        return 0;
    }
}


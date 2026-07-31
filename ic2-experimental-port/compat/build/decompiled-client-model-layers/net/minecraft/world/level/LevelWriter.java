/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

public interface LevelWriter {
    public boolean m_6933_(BlockPos var1, BlockState var2, int var3, int var4);

    default public boolean m_7731_(BlockPos p_46944_, BlockState p_46945_, int p_46946_) {
        return this.m_6933_(p_46944_, p_46945_, p_46946_, 512);
    }

    public boolean m_7471_(BlockPos var1, boolean var2);

    default public boolean m_46961_(BlockPos p_46962_, boolean p_46963_) {
        return this.m_46953_(p_46962_, p_46963_, null);
    }

    default public boolean m_46953_(BlockPos p_46954_, boolean p_46955_, @Nullable Entity p_46956_) {
        return this.m_7740_(p_46954_, p_46955_, p_46956_, 512);
    }

    public boolean m_7740_(BlockPos var1, boolean var2, @Nullable Entity var3, int var4);

    default public boolean m_7967_(Entity p_46964_) {
        return false;
    }
}


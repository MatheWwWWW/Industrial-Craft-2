/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;

public interface WorldGenLevel
extends ServerLevelAccessor {
    public long m_7328_();

    default public boolean m_180807_(BlockPos p_181157_) {
        return true;
    }

    default public void m_143497_(@Nullable Supplier<String> p_186618_) {
    }
}


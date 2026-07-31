/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BlockColumn;

public final class NoiseColumn
implements BlockColumn {
    private final int f_151621_;
    private final BlockState[] f_47149_;

    public NoiseColumn(int p_151623_, BlockState[] p_151624_) {
        this.f_151621_ = p_151623_;
        this.f_47149_ = p_151624_;
    }

    @Override
    public BlockState m_183556_(int p_186552_) {
        int $$1 = p_186552_ - this.f_151621_;
        if ($$1 < 0 || $$1 >= this.f_47149_.length) {
            return Blocks.f_50016_.m_49966_();
        }
        return this.f_47149_[$$1];
    }

    @Override
    public void m_183639_(int p_186554_, BlockState p_186555_) {
        int $$2 = p_186554_ - this.f_151621_;
        if ($$2 < 0 || $$2 >= this.f_47149_.length) {
            throw new IllegalArgumentException("Outside of column height: " + p_186554_);
        }
        this.f_47149_[$$2] = p_186555_;
    }
}


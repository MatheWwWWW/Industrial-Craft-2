/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SandBlock
extends FallingBlock {
    private final int f_55965_;

    public SandBlock(int p_55967_, BlockBehaviour.Properties p_55968_) {
        super(p_55968_);
        this.f_55965_ = p_55967_;
    }

    @Override
    public int m_6248_(BlockState p_55970_, BlockGetter p_55971_, BlockPos p_55972_) {
        return this.f_55965_;
    }
}


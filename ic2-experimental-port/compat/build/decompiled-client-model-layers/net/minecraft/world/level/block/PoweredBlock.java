/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class PoweredBlock
extends Block {
    public PoweredBlock(BlockBehaviour.Properties p_55206_) {
        super(p_55206_);
    }

    @Override
    public boolean m_7899_(BlockState p_55213_) {
        return true;
    }

    @Override
    public int m_6378_(BlockState p_55208_, BlockGetter p_55209_, BlockPos p_55210_, Direction p_55211_) {
        return 15;
    }
}


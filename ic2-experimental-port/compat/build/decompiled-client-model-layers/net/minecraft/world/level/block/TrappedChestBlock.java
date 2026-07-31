/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TrappedChestBlock
extends ChestBlock {
    public TrappedChestBlock(BlockBehaviour.Properties p_57573_) {
        super(p_57573_, () -> BlockEntityType.f_58919_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_154834_, BlockState p_154835_) {
        return new TrappedChestBlockEntity(p_154834_, p_154835_);
    }

    @Override
    protected Stat<ResourceLocation> m_7699_() {
        return Stats.f_12988_.m_12902_(Stats.f_12962_);
    }

    @Override
    public boolean m_7899_(BlockState p_57587_) {
        return true;
    }

    @Override
    public int m_6378_(BlockState p_57577_, BlockGetter p_57578_, BlockPos p_57579_, Direction p_57580_) {
        return Mth.m_14045_(ChestBlockEntity.m_59086_(p_57578_, p_57579_), 0, 15);
    }

    @Override
    public int m_6376_(BlockState p_57582_, BlockGetter p_57583_, BlockPos p_57584_, Direction p_57585_) {
        if (p_57585_ == Direction.UP) {
            return p_57582_.m_60746_(p_57583_, p_57584_, p_57585_);
        }
        return 0;
    }
}


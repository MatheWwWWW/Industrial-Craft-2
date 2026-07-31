/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SpawnerBlock
extends BaseEntityBlock {
    protected SpawnerBlock(BlockBehaviour.Properties p_56781_) {
        super(p_56781_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_154687_, BlockState p_154688_) {
        return new SpawnerBlockEntity(p_154687_, p_154688_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_154683_, BlockState p_154684_, BlockEntityType<T> p_154685_) {
        return SpawnerBlock.m_152132_(p_154685_, BlockEntityType.f_58925_, p_154683_.f_46443_ ? SpawnerBlockEntity::m_155754_ : SpawnerBlockEntity::m_155761_);
    }

    @Override
    public void m_213646_(BlockState p_222477_, ServerLevel p_222478_, BlockPos p_222479_, ItemStack p_222480_, boolean p_222481_) {
        super.m_213646_(p_222477_, p_222478_, p_222479_, p_222480_, p_222481_);
        if (p_222481_) {
            int $$5 = 15 + p_222478_.f_46441_.m_188503_(15) + p_222478_.f_46441_.m_188503_(15);
            this.m_49805_(p_222478_, p_222479_, $$5);
        }
    }

    @Override
    public RenderShape m_7514_(BlockState p_56794_) {
        return RenderShape.MODEL;
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_56785_, BlockPos p_56786_, BlockState p_56787_) {
        return ItemStack.f_41583_;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.entity.block.ExplosiveEntity;
import ic2.core.block.machine.tileentity.TileEntityExplosive;
import ic2.core.entity.block.ITntEntity;
import ic2.core.ref.Ic2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityITnt
extends TileEntityExplosive {
    public TileEntityITnt(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityExplosive>)Ic2BlockEntities.ITNT, blockPos, blockState);
    }

    @Override
    protected boolean explodeOnRemoval() {
        return true;
    }

    @Override
    protected ExplosiveEntity getEntity(LivingEntity livingEntity) {
        return new ITntEntity(this.m_58904_(), (double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 0.5, (double)this.f_58858_.m_123343_() + 0.5);
    }
}


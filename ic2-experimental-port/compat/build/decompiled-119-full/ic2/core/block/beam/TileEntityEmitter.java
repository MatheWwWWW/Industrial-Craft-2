/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.beam;

import ic2.core.block.beam.ParticleEntity;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityEmitter
extends TileEntityElectricMachine {
    private int progress;

    public TileEntityEmitter(BlockEntityType<? extends TileEntityEmitter> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState, 5000, 1);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.progress < 100) {
            ++this.progress;
        }
        if (this.progress == 100 && this.m_58904_().m_46753_(this.f_58858_)) {
            this.progress = 0;
            this.m_58904_().m_7967_((Entity)new ParticleEntity(this));
        }
    }
}


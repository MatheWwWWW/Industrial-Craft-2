/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.api.energy.prefab;

import ic2.api.energy.prefab.BasicEnergyTile;
import ic2.api.energy.prefab.BasicSink;
import ic2.api.energy.prefab.BasicSource;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BasicEnergyTe<T extends BasicEnergyTile>
extends BlockEntity {
    protected T energyBuffer;

    protected BasicEnergyTe(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public T getEnergyBuffer() {
        return this.energyBuffer;
    }

    public void m_7651_() {
        super.m_7651_();
        ((BasicEnergyTile)this.energyBuffer).invalidate();
    }

    public void m_142466_(CompoundTag compoundTag) {
        ((BasicEnergyTile)this.energyBuffer).readFromNBT(compoundTag);
    }

    protected void m_183515_(CompoundTag compoundTag) {
        ((BasicEnergyTile)this.energyBuffer).writeToNBT(compoundTag);
    }

    public static class Source
    extends BasicEnergyTe<BasicSource> {
        public Source(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, int n, int n2) {
            super(blockEntityType, blockPos, blockState);
            this.energyBuffer = new BasicSource(this, (double)n, n2);
        }
    }

    public static class Sink
    extends BasicEnergyTe<BasicSink> {
        public Sink(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, int n, int n2) {
            super(blockEntityType, blockPos, blockState);
            this.energyBuffer = new BasicSink(this, (double)n, n2);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.api.energy.prefab;

import ic2.api.energy.prefab.BasicEnergyTile;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.info.ILocatable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BasicSink
extends BasicEnergyTile
implements IEnergySink {
    protected int tier;

    public BasicSink(BlockEntity blockEntity, double d, int n) {
        super(blockEntity, d);
        if (n < 0) {
            throw new IllegalArgumentException("invalid tier: " + n);
        }
        this.tier = n;
    }

    public BasicSink(ILocatable iLocatable, double d, int n) {
        super(iLocatable, d);
        if (n < 0) {
            throw new IllegalArgumentException("invalid tier: " + n);
        }
        this.tier = n;
    }

    public BasicSink(Level level, BlockPos blockPos, double d, int n) {
        super(level, blockPos, d);
        if (n < 0) {
            throw new IllegalArgumentException("invalid tier: " + n);
        }
        this.tier = n;
    }

    public void setSinkTier(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("invalid tier: " + n);
        }
        this.tier = n;
    }

    @Override
    public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, Direction direction) {
        return true;
    }

    @Override
    public double getDemandedEnergy() {
        return Math.max(0.0, this.getCapacity() - this.getEnergyStored());
    }

    @Override
    public double injectEnergy(Direction direction, double d, double d2) {
        this.setEnergyStored(this.getEnergyStored() + d);
        return 0.0;
    }

    @Override
    public int getSinkTier() {
        return this.tier;
    }

    @Override
    protected String getNbtTagName() {
        return "IC2BasicSink";
    }
}


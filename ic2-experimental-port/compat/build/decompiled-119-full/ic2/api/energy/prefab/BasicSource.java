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

import ic2.api.energy.EnergyNet;
import ic2.api.energy.prefab.BasicEnergyTile;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.info.ILocatable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BasicSource
extends BasicEnergyTile
implements IEnergySource {
    protected int tier;

    public BasicSource(BlockEntity blockEntity, double d, int n) {
        super(blockEntity, d);
        if (n < 0) {
            throw new IllegalArgumentException("invalid tier: " + n);
        }
        this.tier = n;
        double d2 = EnergyNet.instance.getPowerFromTier(n);
        if (this.getCapacity() < d2) {
            this.setCapacity(d2);
        }
    }

    public BasicSource(ILocatable iLocatable, double d, int n) {
        super(iLocatable, d);
        if (n < 0) {
            throw new IllegalArgumentException("invalid tier: " + n);
        }
        this.tier = n;
        double d2 = EnergyNet.instance.getPowerFromTier(n);
        if (this.getCapacity() < d2) {
            this.setCapacity(d2);
        }
    }

    public BasicSource(Level level, BlockPos blockPos, double d, int n) {
        super(level, blockPos, d);
        if (n < 0) {
            throw new IllegalArgumentException("invalid tier: " + n);
        }
        this.tier = n;
        double d2 = EnergyNet.instance.getPowerFromTier(n);
        if (this.getCapacity() < d2) {
            this.setCapacity(d2);
        }
    }

    public void setSourceTier(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("invalid tier: " + n);
        }
        double d = EnergyNet.instance.getPowerFromTier(n);
        if (this.getCapacity() < d) {
            this.setCapacity(d);
        }
        this.tier = n;
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor iEnergyAcceptor, Direction direction) {
        return true;
    }

    @Override
    public double getOfferedEnergy() {
        return this.getEnergyStored();
    }

    @Override
    public void drawEnergy(double d) {
        this.setEnergyStored(this.getEnergyStored() - d);
    }

    @Override
    public int getSourceTier() {
        return this.tier;
    }

    @Override
    protected String getNbtTagName() {
        return "IC2BasicSource";
    }
}


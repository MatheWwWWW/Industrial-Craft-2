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
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.info.ILocatable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class BasicSinkSource
extends BasicEnergyTile
implements IEnergySink,
IEnergySource {
    protected int sinkTier;
    protected int sourceTier;

    public BasicSinkSource(BlockEntity blockEntity, double d, int n, int n2) {
        super(blockEntity, d);
        if (n < 0) {
            throw new IllegalArgumentException("invalid sink tier: " + n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("invalid source tier: " + n2);
        }
        this.sinkTier = n;
        this.sourceTier = n2;
        double d2 = EnergyNet.instance.getPowerFromTier(n2);
        if (this.getCapacity() < d2) {
            this.setCapacity(d2);
        }
    }

    public BasicSinkSource(ILocatable iLocatable, double d, int n, int n2) {
        super(iLocatable, d);
        if (n < 0) {
            throw new IllegalArgumentException("invalid sink tier: " + n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("invalid source tier: " + n2);
        }
        this.sinkTier = n;
        this.sourceTier = n2;
        double d2 = EnergyNet.instance.getPowerFromTier(n2);
        if (this.getCapacity() < d2) {
            this.setCapacity(d2);
        }
    }

    public BasicSinkSource(Level level, BlockPos blockPos, double d, int n, int n2) {
        super(level, blockPos, d);
        if (n < 0) {
            throw new IllegalArgumentException("invalid sink tier: " + n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("invalid source tier: " + n2);
        }
        this.sinkTier = n;
        this.sourceTier = n2;
        double d2 = EnergyNet.instance.getPowerFromTier(n2);
        if (this.getCapacity() < d2) {
            this.setCapacity(d2);
        }
    }

    public void setSinkTier(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("invalid tier: " + n);
        }
        this.sinkTier = n;
    }

    public void setSourceTier(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("invalid tier: " + n);
        }
        double d = EnergyNet.instance.getPowerFromTier(n);
        if (this.getCapacity() < d) {
            this.setCapacity(d);
        }
        this.sourceTier = n;
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
        return this.sinkTier;
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
        return this.sourceTier;
    }

    @Override
    protected String getNbtTagName() {
        return "IC2BasicSinkSource";
    }
}


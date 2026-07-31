/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumFacing
 */
package ic2.core.block;

import ic2.api.energy.tile.IHeatSource;
import ic2.core.IC2;
import ic2.core.block.TileEntityInventory;
import ic2.core.network.GuiSynced;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityHeatSourceInventory
extends TileEntityInventory
implements IHeatSource {
    @GuiSynced
    protected int transmitHeat;
    @GuiSynced
    protected int maxHeatEmitpeerTick;
    protected int HeatBuffer;

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        int amount = this.getMaxHeatEmittedPerTick() - this.HeatBuffer;
        if (amount > 0) {
            this.addtoHeatBuffer(this.fillHeatBuffer(amount));
        }
    }

    @Override
    public int maxrequestHeatTick(EnumFacing directionFrom) {
        return this.getConnectionBandwidth(directionFrom);
    }

    @Override
    public int getConnectionBandwidth(EnumFacing side) {
        if (this.facingMatchesDirection(side)) {
            return this.getMaxHeatEmittedPerTick();
        }
        return 0;
    }

    @Override
    public int requestHeat(EnumFacing directionFrom, int requestheat) {
        return this.drawHeat(directionFrom, requestheat, false);
    }

    @Override
    public int drawHeat(EnumFacing side, int request, boolean simulate) {
        if (this.facingMatchesDirection(side)) {
            int heatBuffer = this.getHeatBuffer();
            if (heatBuffer >= request) {
                if (!simulate) {
                    this.setHeatBuffer(heatBuffer - request);
                    this.transmitHeat = request;
                }
                return request;
            }
            if (!simulate) {
                this.transmitHeat = heatBuffer;
                this.setHeatBuffer(0);
            }
            return heatBuffer;
        }
        return 0;
    }

    @Override
    public void func_145839_a(NBTTagCompound nbtTagCompound) {
        super.func_145839_a(nbtTagCompound);
        this.HeatBuffer = nbtTagCompound.func_74762_e("HeatBuffer");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("HeatBuffer", this.HeatBuffer);
        return nbt;
    }

    @Override
    public void func_70296_d() {
        super.func_70296_d();
        if (IC2.platform.isSimulating()) {
            this.maxHeatEmitpeerTick = this.getMaxHeatEmittedPerTick();
        }
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating()) {
            this.maxHeatEmitpeerTick = this.getMaxHeatEmittedPerTick();
        }
    }

    public boolean facingMatchesDirection(EnumFacing direction) {
        return direction == this.getFacing();
    }

    public int getHeatBuffer() {
        return this.HeatBuffer;
    }

    public void setHeatBuffer(int HeatBuffer) {
        this.HeatBuffer = HeatBuffer;
    }

    public void addtoHeatBuffer(int heat) {
        this.setHeatBuffer(this.getHeatBuffer() + heat);
    }

    public int gettransmitHeat() {
        return this.transmitHeat;
    }

    public String getOutput() {
        return this.gettransmitHeat() + " / " + this.getMaxHeatEmittedPerTick();
    }

    protected abstract int fillHeatBuffer(int var1);

    public abstract int getMaxHeatEmittedPerTick();
}


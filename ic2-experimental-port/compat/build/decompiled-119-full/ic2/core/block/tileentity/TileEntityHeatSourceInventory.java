/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.tileentity;

import ic2.api.energy.tile.IHeatSource;
import ic2.core.IC2;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.network.GuiSynced;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileEntityHeatSourceInventory
extends TileEntityInventory
implements IHeatSource {
    @GuiSynced
    protected int transmitHeat;
    @GuiSynced
    protected int maxHeatEmitpeerTick;
    protected int HeatBuffer;

    public TileEntityHeatSourceInventory(BlockEntityType<? extends TileEntityHeatSourceInventory> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)blockEntityType, blockPos, blockState);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        int n = this.getMaxHeatEmittedPerTick() - this.HeatBuffer;
        if (n > 0) {
            this.addtoHeatBuffer(this.fillHeatBuffer(n));
        }
    }

    @Override
    public int maxrequestHeatTick(Direction direction) {
        return this.getConnectionBandwidth(direction);
    }

    @Override
    public int getConnectionBandwidth(Direction direction) {
        if (this.facingMatchesDirection(direction)) {
            return this.getMaxHeatEmittedPerTick();
        }
        return 0;
    }

    @Override
    public int requestHeat(Direction direction, int n) {
        return this.drawHeat(direction, n, false);
    }

    @Override
    public int drawHeat(Direction direction, int n, boolean bl) {
        if (this.facingMatchesDirection(direction)) {
            int n2 = this.getHeatBuffer();
            if (n2 >= n) {
                if (!bl) {
                    this.setHeatBuffer(n2 - n);
                    this.transmitHeat = n;
                }
                return n;
            }
            if (!bl) {
                this.transmitHeat = n2;
                this.setHeatBuffer(0);
            }
            return n2;
        }
        return 0;
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.HeatBuffer = compoundTag.m_128451_("HeatBuffer");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("HeatBuffer", this.HeatBuffer);
    }

    @Override
    public void m_6596_() {
        super.m_6596_();
        if (IC2.sideProxy.isSimulating()) {
            this.maxHeatEmitpeerTick = this.getMaxHeatEmittedPerTick();
        }
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (IC2.sideProxy.isSimulating()) {
            this.maxHeatEmitpeerTick = this.getMaxHeatEmittedPerTick();
        }
    }

    public boolean facingMatchesDirection(Direction direction) {
        return direction == this.getFacing();
    }

    public int getHeatBuffer() {
        return this.HeatBuffer;
    }

    public void setHeatBuffer(int n) {
        this.HeatBuffer = n;
    }

    public void addtoHeatBuffer(int n) {
        this.setHeatBuffer(this.getHeatBuffer() + n);
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


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.base.misc;

import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.utils.helpers.FluidHelper;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BucketFiller {
    IHasInventory inv;
    BaseTileEntity tile;
    int input;
    int output;
    IFluidHandler handler;
    int slowSpeed = 20;
    int fastSpeed = 2;
    boolean fast = false;

    public BucketFiller(BaseInventoryTileEntity tile, IFluidHandler handler, int input, int output) {
        this.tile = tile;
        this.inv = tile;
        this.input = input;
        this.output = output;
        this.handler = handler;
    }

    public BucketFiller(IHasInventory inv, BaseTileEntity tile, IFluidHandler handler, int input, int output) {
        this.inv = inv;
        this.tile = tile;
        this.input = input;
        this.output = output;
        this.handler = handler;
    }

    public BucketFiller setFastSpeed(int fastSpeed) {
        this.fastSpeed = fastSpeed;
        return this;
    }

    public BucketFiller setSlowSpeed(int slowSpeed) {
        this.slowSpeed = slowSpeed;
        return this;
    }

    public void fillItem() {
        if (this.tile.clock(this.fast ? this.fastSpeed : this.slowSpeed)) {
            if (this.inv.getStackInSlot(this.input).m_41619_()) {
                this.fast = false;
                return;
            }
            this.fast = FluidHelper.fillContainers(this.inv, this.input, this.output, this.handler);
        }
    }

    public void fillTank() {
        if (this.tile.clock(this.fast ? this.fastSpeed : this.slowSpeed)) {
            if (this.inv.getStackInSlot(this.input).m_41619_()) {
                this.fast = false;
                return;
            }
            this.fast = FluidHelper.drainContainers(this.inv, this.input, this.output, this.handler);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumFacing
 */
package ic2.core.block.steam;

import ic2.core.IC2;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.steam.IKineticProvider;
import ic2.core.ref.FluidName;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntitySteamEngine
extends TileEntityInventory
implements IKineticProvider {
    protected int power = 0;
    protected int delta = 0;
    protected int activityMeter = 0;
    protected int ticksSinceLastActiveUpdate = IC2.random.nextInt(128);
    protected final Fluids fluids = this.addComponent(new Fluids(this));
    protected final Fluids.InternalFluidTank fluidTank = this.fluids.addTankInsert("steam", 1000, InvSlot.InvSide.ANY, Fluids.fluidPredicate(FluidName.biomass.getInstance()));

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.delta = nbt.func_74762_e("delta");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("delta", this.delta);
        return nbt;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInventoryUpdate = false;
        boolean newActive = this.work();
        if (needsInventoryUpdate) {
            this.func_70296_d();
        }
        if (!this.delayActiveUpdate()) {
            this.setActive(newActive);
        } else {
            if (this.ticksSinceLastActiveUpdate % 128 == 0) {
                this.setActive(this.activityMeter > 0);
                this.activityMeter = 0;
            }
            this.activityMeter = newActive ? ++this.activityMeter : --this.activityMeter;
            ++this.ticksSinceLastActiveUpdate;
        }
    }

    public boolean work() {
        if (this.fluidTank.getFluidAmount() > 1) {
            this.fluidTank.drainInternal(1, true);
            this.delta = Math.min(++this.delta, 200);
            this.power = (int)((double)this.getMaxPower() / 10.0 * (double)(this.delta / 20));
            return true;
        }
        this.delta = Math.max(--this.delta, 0);
        this.power = (int)((double)this.getMaxPower() / 10.0 * (double)(this.delta / 20));
        return false;
    }

    public boolean delayActiveUpdate() {
        return false;
    }

    @Override
    public int getProvidedPower(EnumFacing side) {
        return side == this.getFacing() ? this.power : 0;
    }

    @Override
    public int getMaxPower() {
        return 4;
    }
}


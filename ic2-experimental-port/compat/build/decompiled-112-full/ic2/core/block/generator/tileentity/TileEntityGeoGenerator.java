/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.FluidEvent
 *  net.minecraftforge.fluids.FluidEvent$FluidSpilledEvent
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fluids.IFluidTank
 */
package ic2.core.block.generator.tileentity;

import ic2.core.block.comp.Fluids;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.init.MainConfig;
import ic2.core.network.GuiSynced;
import ic2.core.util.ConfigUtil;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityGeoGenerator
extends TileEntityBaseGenerator {
    private static final int fluidPerTick = 2;
    public final InvSlotConsumableLiquid fluidSlot;
    public final InvSlotOutput outputSlot;
    @GuiSynced
    protected final FluidTank fluidTank;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityGeoGenerator() {
        super(20.0, 1, 2400);
        this.fluidTank = this.fluids.addTankInsert("fluid", 8000, Fluids.fluidPredicate(FluidRegistry.LAVA));
        this.production = Math.round(20.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/geothermal"));
        this.fluidSlot = new InvSlotConsumableLiquidByTank(this, "fluidSlot", InvSlot.Access.I, 1, InvSlot.InvSide.ANY, InvSlotConsumableLiquid.OpType.Drain, (IFluidTank)this.fluidTank);
        this.outputSlot = new InvSlotOutput(this, "output", 1);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.fluidSlot.processIntoTank((IFluidTank)this.fluidTank, this.outputSlot)) {
            this.func_70296_d();
        }
    }

    @Override
    public boolean gainFuel() {
        boolean dirty = false;
        FluidStack ret = this.fluidTank.drainInternal(2, false);
        if (ret != null && ret.amount >= 2) {
            this.fluidTank.drainInternal(2, true);
            ++this.fuel;
            dirty = true;
        }
        return dirty;
    }

    @Override
    public String getOperationSoundFile() {
        return "Generators/GeothermalLoop.ogg";
    }

    @Override
    protected void onBlockBreak() {
        super.onBlockBreak();
        FluidEvent.fireEvent((FluidEvent)new FluidEvent.FluidSpilledEvent(new FluidStack(FluidRegistry.LAVA, 1000), this.func_145831_w(), this.field_174879_c));
    }
}


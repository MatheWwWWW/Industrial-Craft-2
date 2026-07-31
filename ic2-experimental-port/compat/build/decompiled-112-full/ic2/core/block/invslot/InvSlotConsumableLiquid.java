/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.CapabilityFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandlerItem
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package ic2.core.block.invslot;

import ic2.api.util.FluidContainerOutputMode;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.apache.commons.lang3.mutable.MutableObject;

public class InvSlotConsumableLiquid
extends InvSlotConsumable {
    private OpType opType;

    public InvSlotConsumableLiquid(IInventorySlotHolder<?> base1, String name1, int count) {
        this(base1, name1, InvSlot.Access.I, count, InvSlot.InvSide.TOP, OpType.Drain);
    }

    public InvSlotConsumableLiquid(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, OpType opType1) {
        super(base1, name1, access1, count, preferredSide1);
        this.opType = opType1;
    }

    @Override
    public boolean accepts(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) {
            return false;
        }
        if (!LiquidUtil.isFluidContainer(stack)) {
            return false;
        }
        if (this.opType == OpType.Drain || this.opType == OpType.Both) {
            ItemStack singleStack;
            IFluidHandlerItem handler;
            FluidStack containerFluid = null;
            if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && (handler = (IFluidHandlerItem)(singleStack = StackUtil.copyWithSize(stack, 1)).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) != null) {
                containerFluid = handler.drain(Integer.MAX_VALUE, false);
            }
            if (containerFluid != null && containerFluid.amount > 0 && this.acceptsLiquid(containerFluid.getFluid())) {
                return true;
            }
        }
        return (this.opType == OpType.Fill || this.opType == OpType.Both) && LiquidUtil.isFillableFluidContainer(stack, this.getPossibleFluids());
    }

    public FluidStack drain(Fluid fluid, int maxAmount, MutableObject<ItemStack> output, boolean simulate) {
        output.setValue(null);
        if (fluid != null && !this.acceptsLiquid(fluid)) {
            return null;
        }
        if (this.opType != OpType.Drain && this.opType != OpType.Both) {
            return null;
        }
        ItemStack stack = this.get();
        if (StackUtil.isEmpty(stack)) {
            return null;
        }
        LiquidUtil.FluidOperationResult result = LiquidUtil.drainContainer(stack, fluid, maxAmount, FluidContainerOutputMode.EmptyFullToOutput);
        if (result == null) {
            return null;
        }
        if (fluid == null && !this.acceptsLiquid(result.fluidChange.getFluid())) {
            return null;
        }
        output.setValue((Object)result.extraOutput);
        if (!simulate) {
            this.put(result.inPlaceOutput);
        }
        return result.fluidChange;
    }

    public int fill(FluidStack fs, MutableObject<ItemStack> output, boolean simulate) {
        output.setValue(null);
        if (fs == null || fs.amount <= 0) {
            return 0;
        }
        if (this.opType != OpType.Fill && this.opType != OpType.Both) {
            return 0;
        }
        ItemStack stack = this.get();
        if (StackUtil.isEmpty(stack)) {
            return 0;
        }
        LiquidUtil.FluidOperationResult result = LiquidUtil.fillContainer(stack, fs, FluidContainerOutputMode.EmptyFullToOutput);
        if (result == null) {
            return 0;
        }
        output.setValue((Object)result.extraOutput);
        if (!simulate) {
            this.put(result.inPlaceOutput);
        }
        return result.fluidChange.amount;
    }

    public boolean transferToTank(IFluidTank tank, MutableObject<ItemStack> output, boolean simulate) {
        FluidStack fluid;
        int space = tank.getCapacity();
        Fluid fluidRequired = null;
        FluidStack tankFluid = tank.getFluid();
        if (tankFluid != null) {
            space -= tankFluid.amount;
            fluidRequired = tankFluid.getFluid();
        }
        if ((fluid = this.drain(fluidRequired, space, output, true)) == null) {
            return false;
        }
        int amount = tank.fill(fluid, !simulate);
        if (amount <= 0) {
            return false;
        }
        if (!simulate) {
            this.drain(fluidRequired, amount, output, false);
        }
        return true;
    }

    public boolean transferFromTank(IFluidTank tank, MutableObject<ItemStack> output, boolean simulate) {
        FluidStack tankFluid = tank.drain(tank.getFluidAmount(), false);
        if (tankFluid == null || tankFluid.amount <= 0) {
            return false;
        }
        int amount = this.fill(tankFluid, output, simulate);
        if (amount <= 0) {
            return false;
        }
        if (!simulate) {
            tank.drain(amount, true);
        }
        return true;
    }

    public boolean processIntoTank(IFluidTank tank, InvSlotOutput outputSlot) {
        if (this.isEmpty()) {
            return false;
        }
        MutableObject output = new MutableObject();
        boolean wasChange = false;
        if (this.transferToTank(tank, (MutableObject<ItemStack>)output, true) && (StackUtil.isEmpty((ItemStack)output.getValue()) || outputSlot.canAdd((ItemStack)output.getValue()))) {
            wasChange = this.transferToTank(tank, (MutableObject<ItemStack>)output, false);
            if (!StackUtil.isEmpty((ItemStack)output.getValue())) {
                outputSlot.add((ItemStack)output.getValue());
            }
        }
        return wasChange;
    }

    public boolean processFromTank(IFluidTank tank, InvSlotOutput outputSlot) {
        if (this.isEmpty() || tank.getFluidAmount() <= 0) {
            return false;
        }
        MutableObject output = new MutableObject();
        boolean wasChange = false;
        if (this.transferFromTank(tank, (MutableObject<ItemStack>)output, true) && (StackUtil.isEmpty((ItemStack)output.getValue()) || outputSlot.canAdd((ItemStack)output.getValue()))) {
            wasChange = this.transferFromTank(tank, (MutableObject<ItemStack>)output, false);
            if (!StackUtil.isEmpty((ItemStack)output.getValue())) {
                outputSlot.add((ItemStack)output.getValue());
            }
        }
        return wasChange;
    }

    public void setOpType(OpType opType1) {
        this.opType = opType1;
    }

    protected boolean acceptsLiquid(Fluid fluid) {
        return true;
    }

    protected Iterable<Fluid> getPossibleFluids() {
        return null;
    }

    public static enum OpType {
        Drain,
        Fill,
        Both,
        None;

    }
}


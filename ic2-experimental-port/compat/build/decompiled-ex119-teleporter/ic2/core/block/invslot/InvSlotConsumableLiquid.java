/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package ic2.core.block.invslot;

import ic2.api.util.FluidContainerOutputMode;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.mutable.MutableObject;

public class InvSlotConsumableLiquid
extends InvSlotConsumable {
    private OpType opType;

    public InvSlotConsumableLiquid(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n) {
        this(iInventorySlotHolder, string, InvSlot.Access.I, n, InvSlot.InvSide.TOP, OpType.Drain);
    }

    public InvSlotConsumableLiquid(IInventorySlotHolder<?> iInventorySlotHolder, String string, InvSlot.Access access, int n, InvSlot.InvSide invSide, OpType opType) {
        super(iInventorySlotHolder, string, access, n, invSide);
        this.opType = opType;
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        Ic2FluidStack ic2FluidStack;
        if (StackUtil.isEmpty(itemStack)) {
            return false;
        }
        if (!LiquidUtil.isFluidContainer(itemStack)) {
            return false;
        }
        if ((this.opType == OpType.Drain || this.opType == OpType.Both) && (ic2FluidStack = Ic2FluidStack.get(itemStack)) != null && !ic2FluidStack.isEmpty() && this.acceptsLiquid(ic2FluidStack.getFluid())) {
            return true;
        }
        return (this.opType == OpType.Fill || this.opType == OpType.Both) && LiquidUtil.isFillableFluidContainer(itemStack, this.getPossibleFluids());
    }

    public Ic2FluidStack drain(Fluid fluid, int n, MutableObject<ItemStack> mutableObject, boolean bl) {
        mutableObject.setValue(null);
        if (fluid != null && !this.acceptsLiquid(fluid)) {
            return null;
        }
        if (this.opType != OpType.Drain && this.opType != OpType.Both) {
            return null;
        }
        ItemStack itemStack = this.get();
        if (StackUtil.isEmpty(itemStack)) {
            return null;
        }
        LiquidUtil.FluidOperationResult fluidOperationResult = LiquidUtil.drainContainer(itemStack, fluid, n, FluidContainerOutputMode.EmptyFullToOutput);
        if (fluidOperationResult == null) {
            return null;
        }
        if (fluid == null && !this.acceptsLiquid(fluidOperationResult.fluidChange.getFluid())) {
            return null;
        }
        mutableObject.setValue((Object)fluidOperationResult.extraOutput);
        if (!bl) {
            this.put(fluidOperationResult.inPlaceOutput);
        }
        return fluidOperationResult.fluidChange;
    }

    public int fill(Ic2FluidStack ic2FluidStack, MutableObject<ItemStack> mutableObject, boolean bl) {
        mutableObject.setValue(null);
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            return 0;
        }
        if (this.opType != OpType.Fill && this.opType != OpType.Both) {
            return 0;
        }
        ItemStack itemStack = this.get();
        if (StackUtil.isEmpty(itemStack)) {
            return 0;
        }
        LiquidUtil.FluidOperationResult fluidOperationResult = LiquidUtil.fillContainer(itemStack, ic2FluidStack, FluidContainerOutputMode.EmptyFullToOutput);
        if (fluidOperationResult == null) {
            return 0;
        }
        mutableObject.setValue((Object)fluidOperationResult.extraOutput);
        if (!bl) {
            this.put(fluidOperationResult.inPlaceOutput);
        }
        return fluidOperationResult.fluidChange.getAmountMb();
    }

    public boolean transferToTank(Ic2FluidTank ic2FluidTank, MutableObject<ItemStack> mutableObject, boolean bl) {
        Ic2FluidStack ic2FluidStack;
        int n = ic2FluidTank.getCapacity();
        Fluid fluid = null;
        Ic2FluidStack ic2FluidStack2 = ic2FluidTank.getFluidStack();
        if (ic2FluidStack2 != null) {
            n -= ic2FluidStack2.getAmountMb();
            fluid = ic2FluidStack2.getFluid();
        }
        if ((ic2FluidStack = this.drain(fluid, n, mutableObject, true)) == null) {
            return false;
        }
        int n2 = ic2FluidTank.fillMb(ic2FluidStack, bl);
        if (n2 <= 0) {
            return false;
        }
        if (!bl) {
            this.drain(fluid, n2, mutableObject, false);
        }
        return true;
    }

    public boolean transferFromTank(Ic2FluidTank ic2FluidTank, MutableObject<ItemStack> mutableObject, boolean bl) {
        Ic2FluidStack ic2FluidStack = ic2FluidTank.drainMb(ic2FluidTank.getFluidAmount(), true);
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            return false;
        }
        int n = this.fill(ic2FluidStack, mutableObject, bl);
        if (n <= 0) {
            return false;
        }
        if (!bl) {
            ic2FluidTank.drainMb(n, false);
        }
        return true;
    }

    public boolean processIntoTank(Ic2FluidTank ic2FluidTank, InvSlotOutput invSlotOutput) {
        if (this.isEmpty()) {
            return false;
        }
        MutableObject mutableObject = new MutableObject();
        boolean bl = false;
        if (this.transferToTank(ic2FluidTank, (MutableObject<ItemStack>)mutableObject, true) && (StackUtil.isEmpty((ItemStack)mutableObject.getValue()) || invSlotOutput.canAdd((ItemStack)mutableObject.getValue()))) {
            bl = this.transferToTank(ic2FluidTank, (MutableObject<ItemStack>)mutableObject, false);
            if (!StackUtil.isEmpty((ItemStack)mutableObject.getValue())) {
                invSlotOutput.add((ItemStack)mutableObject.getValue());
            }
        }
        return bl;
    }

    public boolean processFromTank(Ic2FluidTank ic2FluidTank, InvSlotOutput invSlotOutput) {
        if (this.isEmpty() || ic2FluidTank.isEmpty()) {
            return false;
        }
        MutableObject mutableObject = new MutableObject();
        boolean bl = false;
        if (this.transferFromTank(ic2FluidTank, (MutableObject<ItemStack>)mutableObject, true) && (StackUtil.isEmpty((ItemStack)mutableObject.getValue()) || invSlotOutput.canAdd((ItemStack)mutableObject.getValue()))) {
            bl = this.transferFromTank(ic2FluidTank, (MutableObject<ItemStack>)mutableObject, false);
            if (!StackUtil.isEmpty((ItemStack)mutableObject.getValue())) {
                invSlotOutput.add((ItemStack)mutableObject.getValue());
            }
        }
        return bl;
    }

    public void setOpType(OpType opType) {
        this.opType = opType;
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


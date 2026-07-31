/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.block.invslot;

import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.ICannerEnrichRecipeManager;
import ic2.api.recipe.IFillFluidContainerRecipeManager;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.util.FluidContainerOutputMode;
import ic2.core.block.invslot.InvSlotConsumableCanner;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.machine.tileentity.TileEntityCanner;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InvSlotProcessableCanner
extends InvSlotProcessable<Object, Object, Object> {
    public InvSlotProcessableCanner(TileEntityCanner tileEntityCanner, String string, int n) {
        super(tileEntityCanner, string, n, null);
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        switch (((TileEntityCanner)this.base).getMode()) {
            case BottleSolid: 
            case EnrichLiquid: {
                return super.accepts(itemStack);
            }
            case BottleLiquid: 
            case EmptyLiquid: {
                return false;
            }
        }
        assert (false);
        return false;
    }

    @Override
    public void consume(MachineRecipeResult<Object, Object, Object> machineRecipeResult) {
        Ic2FluidStack ic2FluidStack;
        super.consume(machineRecipeResult);
        ItemStack itemStack = ((TileEntityCanner)this.base).canInputSlot.get();
        if (StackUtil.isEmpty(itemStack)) {
            ((TileEntityCanner)this.base).canInputSlot.clear();
        }
        if ((ic2FluidStack = ((TileEntityCanner)this.base).inputTank.getFluidStack()) != null && ic2FluidStack.isEmpty()) {
            ((TileEntityCanner)this.base).inputTank.setFluidStack(null);
        }
    }

    @Override
    protected Object getInput(ItemStack itemStack) {
        ItemStack itemStack2 = ((TileEntityCanner)this.base).canInputSlot.get();
        switch (((TileEntityCanner)this.base).getMode()) {
            case BottleSolid: {
                return new ICannerBottleRecipeManager.RawInput(itemStack2, itemStack);
            }
            case BottleLiquid: {
                return new IFillFluidContainerRecipeManager.Input(itemStack2, this.getTankFluid());
            }
            case EmptyLiquid: {
                return itemStack2;
            }
            case EnrichLiquid: {
                return new ICannerEnrichRecipeManager.RawInput(this.getTankFluid(), itemStack);
            }
        }
        assert (false);
        return null;
    }

    @Override
    protected void setInput(Object object) {
        InvSlotConsumableCanner invSlotConsumableCanner = ((TileEntityCanner)this.base).canInputSlot;
        Ic2FluidTank ic2FluidTank = ((TileEntityCanner)this.base).inputTank;
        switch (((TileEntityCanner)this.base).getMode()) {
            case BottleSolid: {
                ICannerBottleRecipeManager.RawInput rawInput = (ICannerBottleRecipeManager.RawInput)object;
                invSlotConsumableCanner.put(rawInput.container);
                this.put(rawInput.fill);
                break;
            }
            case BottleLiquid: {
                IFillFluidContainerRecipeManager.Input input = (IFillFluidContainerRecipeManager.Input)object;
                invSlotConsumableCanner.put(input.container);
                ic2FluidTank.drainMbUnchecked(input.fluid == null ? ic2FluidTank.getFluidAmount() : ic2FluidTank.getFluidAmount() - input.fluid.getAmountMb(), false);
                break;
            }
            case EmptyLiquid: {
                invSlotConsumableCanner.put((ItemStack)object);
                break;
            }
            case EnrichLiquid: {
                ICannerEnrichRecipeManager.RawInput rawInput = (ICannerEnrichRecipeManager.RawInput)object;
                this.put(rawInput.additive);
                ic2FluidTank.drainMbUnchecked(rawInput.fluid == null ? ic2FluidTank.getFluidAmount() : ic2FluidTank.getFluidAmount() - rawInput.fluid.getAmountMb(), false);
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
    }

    @Override
    protected boolean allowEmptyInput() {
        return true;
    }

    @Override
    protected MachineRecipeResult<Object, Object, Object> getOutputFor(Object object, boolean bl) {
        return this.getOutput(object, bl);
    }

    protected MachineRecipeResult<Object, Object, Object> getOutput(Object object, boolean bl) {
        Level level = this.base.getParent().m_58904_();
        switch (((TileEntityCanner)this.base).getMode()) {
            case BottleSolid: {
                return Recipes.cannerBottle.get(level).apply((ICannerBottleRecipeManager.RawInput)object, bl);
            }
            case BottleLiquid: {
                return Recipes.fillFluidContainer.apply((IFillFluidContainerRecipeManager.Input)object, FluidContainerOutputMode.EmptyFullToOutput, bl);
            }
            case EmptyLiquid: {
                return Recipes.emptyFluidContainer.apply((ItemStack)object, this.getTankFluid() == null ? null : this.getTankFluid().getFluid(), FluidContainerOutputMode.EmptyFullToOutput, bl);
            }
            case EnrichLiquid: {
                return Recipes.cannerEnrich.get(level).apply((ICannerEnrichRecipeManager.RawInput)object, bl);
            }
        }
        assert (false);
        return null;
    }

    private Ic2FluidStack getTankFluid() {
        return ((TileEntityCanner)this.base).inputTank.getFluidStack();
    }
}


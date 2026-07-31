/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machine;

import ic2.api.recipe.IFillFluidContainerRecipeManager;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.util.FluidContainerOutputMode;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class FillFluidContainerRecipeManager
implements IFillFluidContainerRecipeManager {
    public boolean addRecipe(Void void_, Collection<ItemStack> collection, CompoundTag compoundTag, boolean bl) {
        return false;
    }

    @Override
    public MachineRecipeResult<Void, Collection<ItemStack>, IFillFluidContainerRecipeManager.Input> apply(IFillFluidContainerRecipeManager.Input input, boolean bl) {
        return this.apply(input, FluidContainerOutputMode.AnyToOutput, bl);
    }

    @Override
    public MachineRecipeResult<Void, Collection<ItemStack>, IFillFluidContainerRecipeManager.Input> apply(IFillFluidContainerRecipeManager.Input input, FluidContainerOutputMode fluidContainerOutputMode, boolean bl) {
        if (StackUtil.isEmpty(input.container) || input.fluid == null) {
            if (!bl) {
                return null;
            }
            if (StackUtil.isEmpty(input.container) && input.fluid == null) {
                return null;
            }
            if (StackUtil.isEmpty(input.container) || LiquidUtil.isFillableFluidContainer(input.container)) {
                return new MachineRecipe(null, Collections.emptyList()).getResult(input);
            }
            return null;
        }
        if (input.fluid.isEmpty()) {
            return null;
        }
        LiquidUtil.FluidOperationResult fluidOperationResult = LiquidUtil.fillContainer(input.container, input.fluid, fluidContainerOutputMode);
        if (fluidOperationResult == null) {
            return null;
        }
        List list = StackUtil.isEmpty(fluidOperationResult.extraOutput) ? Collections.emptyList() : Collections.singletonList(fluidOperationResult.extraOutput);
        Ic2FluidStack ic2FluidStack = fluidOperationResult.fluidChange.getAmountMb() >= input.fluid.getAmountMb() ? null : input.fluid.copyWithAmountMb(input.fluid.getAmountMb() - fluidOperationResult.fluidChange.getAmountMb());
        return new MachineRecipe(null, list).getResult(new IFillFluidContainerRecipeManager.Input(fluidOperationResult.inPlaceOutput, ic2FluidStack));
    }

    @Override
    public Iterable<? extends MachineRecipe<Void, Collection<ItemStack>>> getRecipes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isIterable() {
        return false;
    }
}


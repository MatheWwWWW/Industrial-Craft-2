/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.block.machine;

import ic2.api.recipe.IEmptyFluidContainerRecipeManager;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.util.FluidContainerOutputMode;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import java.util.Collections;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class EmptyFluidContainerRecipeManager
implements IEmptyFluidContainerRecipeManager {
    public boolean addRecipe(Void void_, IEmptyFluidContainerRecipeManager.Output output, CompoundTag compoundTag, boolean bl) {
        return false;
    }

    @Override
    public MachineRecipeResult<Void, IEmptyFluidContainerRecipeManager.Output, ItemStack> apply(ItemStack itemStack, boolean bl) {
        return this.apply(itemStack, null, FluidContainerOutputMode.AnyToOutput, bl);
    }

    @Override
    public MachineRecipeResult<Void, IEmptyFluidContainerRecipeManager.Output, ItemStack> apply(ItemStack itemStack, Fluid fluid, FluidContainerOutputMode fluidContainerOutputMode, boolean bl) {
        if (StackUtil.isEmpty(itemStack)) {
            return null;
        }
        LiquidUtil.FluidOperationResult fluidOperationResult = LiquidUtil.drainContainer(itemStack, fluid, Integer.MAX_VALUE, fluidContainerOutputMode);
        if (fluidOperationResult == null) {
            return null;
        }
        List<ItemStack> list = StackUtil.isEmpty(fluidOperationResult.extraOutput) ? Collections.emptyList() : Collections.singletonList(fluidOperationResult.extraOutput);
        return new MachineRecipe<Object, IEmptyFluidContainerRecipeManager.Output>(null, new IEmptyFluidContainerRecipeManager.Output(list, fluidOperationResult.fluidChange)).getResult(fluidOperationResult.inPlaceOutput);
    }

    @Override
    public Iterable<? extends MachineRecipe<Void, IEmptyFluidContainerRecipeManager.Output>> getRecipes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isIterable() {
        return false;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraftforge.fluids.Fluid
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;

public class EmptyFluidContainerRecipeManager
implements IEmptyFluidContainerRecipeManager {
    @Override
    public boolean addRecipe(Void input, IEmptyFluidContainerRecipeManager.Output output, NBTTagCompound metadata, boolean replace) {
        return false;
    }

    @Override
    public MachineRecipeResult<Void, IEmptyFluidContainerRecipeManager.Output, ItemStack> apply(ItemStack input, boolean acceptTest) {
        return this.apply(input, null, FluidContainerOutputMode.AnyToOutput, acceptTest);
    }

    @Override
    public MachineRecipeResult<Void, IEmptyFluidContainerRecipeManager.Output, ItemStack> apply(ItemStack input, Fluid requiredFluid, FluidContainerOutputMode outputMode, boolean acceptTest) {
        if (StackUtil.isEmpty(input)) {
            return null;
        }
        LiquidUtil.FluidOperationResult result = LiquidUtil.drainContainer(input, requiredFluid, Integer.MAX_VALUE, outputMode);
        if (result == null) {
            return null;
        }
        List<ItemStack> output = StackUtil.isEmpty(result.extraOutput) ? Collections.emptyList() : Collections.singletonList(result.extraOutput);
        return new MachineRecipe<Object, IEmptyFluidContainerRecipeManager.Output>(null, new IEmptyFluidContainerRecipeManager.Output(output, result.fluidChange)).getResult(result.inPlaceOutput);
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


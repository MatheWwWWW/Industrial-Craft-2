/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machine;

import ic2.api.recipe.ICannerEnrichRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.RecipeOutput;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class CannerEnrichRecipeManager
implements ICannerEnrichRecipeManager {
    private final List<MachineRecipe<ICannerEnrichRecipeManager.Input, Ic2FluidStack>> recipes = new ArrayList<MachineRecipe<ICannerEnrichRecipeManager.Input, Ic2FluidStack>>();

    public boolean addRecipe(ICannerEnrichRecipeManager.Input input, Ic2FluidStack ic2FluidStack, CompoundTag compoundTag, boolean bl) {
        if (input.fluid == null) {
            throw new NullPointerException("The fluid recipe input is null.");
        }
        if (input.additive == null) {
            throw new NullPointerException("The additive recipe input is null.");
        }
        if (ic2FluidStack == null) {
            throw new NullPointerException("The recipe output is null.");
        }
        if (!LiquidUtil.check(input.fluid)) {
            throw new IllegalArgumentException("The fluid recipe input is invalid.");
        }
        if (!LiquidUtil.check(ic2FluidStack)) {
            throw new IllegalArgumentException("The fluid recipe output is invalid.");
        }
        for (ItemStack itemStack : input.additive.getInputs()) {
            MachineRecipe<ICannerEnrichRecipeManager.Input, Ic2FluidStack> machineRecipe = this.getRecipe(input.fluid, itemStack, true);
            if (machineRecipe == null) continue;
            if (!bl) {
                return false;
            }
            this.recipes.remove(machineRecipe);
        }
        this.recipes.add(new MachineRecipe<ICannerEnrichRecipeManager.Input, Ic2FluidStack>(input, ic2FluidStack));
        return true;
    }

    public void addRecipe(Ic2FluidStack ic2FluidStack, IRecipeInput iRecipeInput, Ic2FluidStack ic2FluidStack2) {
        if (!this.addRecipe(new ICannerEnrichRecipeManager.Input(ic2FluidStack, iRecipeInput), ic2FluidStack2, null, false)) {
            throw new RuntimeException("ambiguous recipe: [" + ic2FluidStack + "+" + iRecipeInput.getInputs() + " -> " + ic2FluidStack2 + "]");
        }
    }

    @Override
    public MachineRecipeResult<ICannerEnrichRecipeManager.Input, Ic2FluidStack, ICannerEnrichRecipeManager.RawInput> apply(ICannerEnrichRecipeManager.RawInput rawInput, boolean bl) {
        Ic2FluidStack ic2FluidStack;
        MachineRecipe<ICannerEnrichRecipeManager.Input, Ic2FluidStack> machineRecipe = this.getRecipe(rawInput.fluid, rawInput.additive, bl);
        if (machineRecipe == null) {
            return null;
        }
        if (rawInput.fluid == null) {
            ic2FluidStack = null;
        } else {
            ic2FluidStack = rawInput.fluid.copy();
            ic2FluidStack.decreaseMb(machineRecipe.getInput().fluid.getAmountMb());
            if (ic2FluidStack.isEmpty()) {
                ic2FluidStack = null;
            }
        }
        return machineRecipe.getResult(new ICannerEnrichRecipeManager.RawInput(ic2FluidStack, StackUtil.copyShrunk(rawInput.additive, machineRecipe.getInput().additive.getAmount())));
    }

    private MachineRecipe<ICannerEnrichRecipeManager.Input, Ic2FluidStack> getRecipe(Ic2FluidStack ic2FluidStack, ItemStack itemStack, boolean bl) {
        if (!bl && (ic2FluidStack == null || StackUtil.isEmpty(itemStack))) {
            return null;
        }
        for (MachineRecipe<ICannerEnrichRecipeManager.Input, Ic2FluidStack> machineRecipe : this.recipes) {
            if (ic2FluidStack != null && (!ic2FluidStack.hasExactFluid(machineRecipe.getInput().fluid) || !bl && machineRecipe.getInput().fluid.getAmountMb() > ic2FluidStack.getAmountMb()) || itemStack != null && (!machineRecipe.getInput().additive.matches(itemStack) || !bl && machineRecipe.getInput().additive.getAmount() > StackUtil.getSize(itemStack))) continue;
            return machineRecipe;
        }
        return null;
    }

    @Override
    public RecipeOutput getOutputFor(Ic2FluidStack ic2FluidStack, ItemStack itemStack, boolean bl, boolean bl2) {
        MachineRecipeResult<ICannerEnrichRecipeManager.Input, Ic2FluidStack, ICannerEnrichRecipeManager.RawInput> machineRecipeResult = this.apply(new ICannerEnrichRecipeManager.RawInput(ic2FluidStack, itemStack), bl2);
        if (machineRecipeResult == null) {
            return null;
        }
        if (bl) {
            ic2FluidStack.setAmountMb(machineRecipeResult.getAdjustedInput().fluid == null ? 0 : machineRecipeResult.getAdjustedInput().fluid.getAmountMb());
            itemStack.m_41764_(StackUtil.isEmpty(machineRecipeResult.getAdjustedInput().additive) ? 0 : StackUtil.getSize(machineRecipeResult.getAdjustedInput().additive));
        }
        CompoundTag compoundTag = new CompoundTag();
        machineRecipeResult.getOutput().toNbt(compoundTag);
        return new RecipeOutput(compoundTag, new ItemStack[0]);
    }

    @Override
    public Iterable<? extends MachineRecipe<ICannerEnrichRecipeManager.Input, Ic2FluidStack>> getRecipes() {
        return this.recipes;
    }

    @Override
    public boolean isIterable() {
        return true;
    }
}


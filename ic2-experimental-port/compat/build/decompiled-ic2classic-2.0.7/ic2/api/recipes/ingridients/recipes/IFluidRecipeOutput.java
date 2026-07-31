/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.api.recipes.ingridients.recipes;

import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidRecipeOutput
extends IRecipeOutput {
    public List<FluidStack> onFluidRecipeProcessed(RandomSource var1, CompoundTag var2, CompoundTag var3, ItemStack var4);

    default public List<FluidStack> onFluidRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags, ItemStack input, IRecipeOutput.IRecipeOverride overrides) {
        return this.onFluidRecipeProcessed(rand, persistentData, recipeFlags, input);
    }

    public List<FluidStack> getAllFluidOutputs();

    public static List<FluidStack> copyFluids(List<FluidStack> copy) {
        ObjectArrayList list = new ObjectArrayList();
        for (FluidStack stack : copy) {
            list.add(stack.copy());
        }
        return list;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.crafting.RecipeSerializer
 */
package ic2.core.recipe.v2;

import com.google.gson.JsonObject;
import ic2.api.recipe.ICannerEnrichRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.recipe.v2.RecipeHolder;
import ic2.core.recipe.v2.RecipeIo;
import ic2.core.ref.Ic2RecipeTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CannerEnrichRecipeSerializer
implements RecipeSerializer<RecipeHolder<ICannerEnrichRecipeManager.Input, Ic2FluidStack>> {
    public RecipeHolder<ICannerEnrichRecipeManager.Input, Ic2FluidStack> read(ResourceLocation resourceLocation, JsonObject jsonObject) {
        Ic2FluidStack ic2FluidStack = RecipeIo.parseFluidStack(GsonHelper.m_13930_((JsonObject)jsonObject, (String)"input_ingredient"));
        IRecipeInput iRecipeInput = RecipeIo.parseInput(jsonObject.get("additive_ingredient"));
        Ic2FluidStack ic2FluidStack2 = RecipeIo.parseFluidStack(GsonHelper.m_13930_((JsonObject)jsonObject, (String)"result"));
        return new RecipeHolder<ICannerEnrichRecipeManager.Input, Ic2FluidStack>(new MachineRecipe<ICannerEnrichRecipeManager.Input, Ic2FluidStack>(new ICannerEnrichRecipeManager.Input(ic2FluidStack, iRecipeInput), ic2FluidStack2), resourceLocation, this, Ic2RecipeTypes.CANNER_ENRICH);
    }

    public RecipeHolder<ICannerEnrichRecipeManager.Input, Ic2FluidStack> read(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
        Ic2FluidStack ic2FluidStack = RecipeIo.readFluidStack(friendlyByteBuf);
        IRecipeInput iRecipeInput = RecipeIo.readInput(friendlyByteBuf);
        Ic2FluidStack ic2FluidStack2 = RecipeIo.readFluidStack(friendlyByteBuf);
        return new RecipeHolder<ICannerEnrichRecipeManager.Input, Ic2FluidStack>(new MachineRecipe<ICannerEnrichRecipeManager.Input, Ic2FluidStack>(new ICannerEnrichRecipeManager.Input(ic2FluidStack, iRecipeInput), ic2FluidStack2), resourceLocation, this, Ic2RecipeTypes.CANNER_ENRICH);
    }

    public void write(FriendlyByteBuf friendlyByteBuf, RecipeHolder<ICannerEnrichRecipeManager.Input, Ic2FluidStack> recipeHolder) {
        RecipeIo.writeFluidStack(friendlyByteBuf, recipeHolder.recipe().getInput().fluid);
        RecipeIo.writeInput(friendlyByteBuf, recipeHolder.recipe().getInput().additive);
        RecipeIo.writeFluidStack(friendlyByteBuf, recipeHolder.recipe().getOutput());
    }
}


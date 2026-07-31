/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeSerializer
 */
package ic2.core.recipe.v2;

import com.google.gson.JsonObject;
import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.core.recipe.v2.RecipeHolder;
import ic2.core.recipe.v2.RecipeIo;
import ic2.core.ref.Ic2RecipeTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CannerBottleRecipeSerializer
implements RecipeSerializer<RecipeHolder<ICannerBottleRecipeManager.Input, ItemStack>> {
    public RecipeHolder<ICannerBottleRecipeManager.Input, ItemStack> read(ResourceLocation resourceLocation, JsonObject jsonObject) {
        IRecipeInput iRecipeInput = RecipeIo.parseInput(jsonObject.get("container_ingredient"));
        IRecipeInput iRecipeInput2 = RecipeIo.parseInput(jsonObject.get("fill_ingredient"));
        ItemStack itemStack = RecipeIo.parseOutput(GsonHelper.m_13930_((JsonObject)jsonObject, (String)"result"));
        return new RecipeHolder<ICannerBottleRecipeManager.Input, ItemStack>(new MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>(new ICannerBottleRecipeManager.Input(iRecipeInput, iRecipeInput2), itemStack), resourceLocation, this, Ic2RecipeTypes.CANNER_BOTTLE);
    }

    public RecipeHolder<ICannerBottleRecipeManager.Input, ItemStack> read(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
        IRecipeInput iRecipeInput = RecipeIo.readInput(friendlyByteBuf);
        IRecipeInput iRecipeInput2 = RecipeIo.readInput(friendlyByteBuf);
        ItemStack itemStack = friendlyByteBuf.m_130267_();
        return new RecipeHolder<ICannerBottleRecipeManager.Input, ItemStack>(new MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>(new ICannerBottleRecipeManager.Input(iRecipeInput, iRecipeInput2), itemStack), resourceLocation, this, Ic2RecipeTypes.CANNER_BOTTLE);
    }

    public void write(FriendlyByteBuf friendlyByteBuf, RecipeHolder<ICannerBottleRecipeManager.Input, ItemStack> recipeHolder) {
        RecipeIo.writeInput(friendlyByteBuf, recipeHolder.recipe().getInput().container);
        RecipeIo.writeInput(friendlyByteBuf, recipeHolder.recipe().getInput().fill);
        friendlyByteBuf.m_130055_(recipeHolder.recipe().getOutput());
    }
}


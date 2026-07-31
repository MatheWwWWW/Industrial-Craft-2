/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraft.world.item.crafting.RecipeType
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.recipe.v2;

import com.google.gson.JsonObject;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.core.recipe.v2.RecipeHolder;
import ic2.core.recipe.v2.RecipeIo;
import java.util.Collection;
import java.util.function.Function;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class BasicMachineRecipeSerializer
implements RecipeSerializer<RecipeHolder<IRecipeInput, Collection<ItemStack>>> {
    private final RecipeType<?> recipeType;
    @Nullable
    private final Function<JsonObject, CompoundTag> metaProcessor;

    public BasicMachineRecipeSerializer(RecipeType<?> recipeType, @Nullable Function<JsonObject, CompoundTag> function) {
        this.recipeType = recipeType;
        this.metaProcessor = function;
    }

    public RecipeHolder<IRecipeInput, Collection<ItemStack>> read(ResourceLocation resourceLocation, JsonObject jsonObject) {
        IRecipeInput iRecipeInput = RecipeIo.parseInput(jsonObject.get("ingredient"));
        Collection<ItemStack> collection = RecipeIo.parseOutputs(jsonObject.get("result"), "result");
        CompoundTag compoundTag = this.metaProcessor != null ? this.metaProcessor.apply(jsonObject) : null;
        return new RecipeHolder<IRecipeInput, Collection<ItemStack>>(new MachineRecipe<IRecipeInput, Collection<ItemStack>>(iRecipeInput, collection, compoundTag), resourceLocation, this, this.recipeType);
    }

    public RecipeHolder<IRecipeInput, Collection<ItemStack>> read(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
        return new RecipeHolder<IRecipeInput, Collection<ItemStack>>(new MachineRecipe<IRecipeInput, Collection<ItemStack>>(RecipeIo.readInput(friendlyByteBuf), RecipeIo.readOutput(friendlyByteBuf), friendlyByteBuf.m_130260_()), resourceLocation, this, this.recipeType);
    }

    public void write(FriendlyByteBuf friendlyByteBuf, RecipeHolder<IRecipeInput, Collection<ItemStack>> recipeHolder) {
        RecipeIo.writeInput(friendlyByteBuf, recipeHolder.recipe().getInput());
        RecipeIo.writeOutput(friendlyByteBuf, recipeHolder.recipe().getOutput());
        friendlyByteBuf.m_130079_(recipeHolder.recipe().getMetaData());
    }
}


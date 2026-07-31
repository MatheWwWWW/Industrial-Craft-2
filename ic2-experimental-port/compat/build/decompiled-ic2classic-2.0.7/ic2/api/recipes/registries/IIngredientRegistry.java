/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.api.recipes.registries;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.queue.IStackOutput;
import ic2.api.recipes.ingridients.recipes.IFluidRecipeOutput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface IIngredientRegistry {
    public void registerInput(ResourceLocation var1, Class<? extends IInput> var2, Function<FriendlyByteBuf, IInput> var3, Function<JsonObject, IInput> var4, @Nullable Function<Object, IInput> var5);

    public void registerRecipeOutput(ResourceLocation var1, Class<? extends IRecipeOutput> var2, Function<FriendlyByteBuf, IRecipeOutput> var3, Function<JsonObject, IRecipeOutput> var4);

    public void registerFluidOutput(ResourceLocation var1, Class<? extends IFluidRecipeOutput> var2, Function<FriendlyByteBuf, IFluidRecipeOutput> var3, Function<JsonObject, IFluidRecipeOutput> var4);

    public void registerQueue(ResourceLocation var1, Class<? extends IStackOutput> var2, Function<CompoundTag, IStackOutput> var3);

    public void writeInput(IInput var1, FriendlyByteBuf var2);

    public void writeRecipeOutput(IRecipeOutput var1, FriendlyByteBuf var2);

    public void writeFluidOutput(IFluidRecipeOutput var1, FriendlyByteBuf var2);

    public CompoundTag writeQueue(IStackOutput var1);

    public IInput readInput(FriendlyByteBuf var1);

    public IRecipeOutput createOutput(FriendlyByteBuf var1);

    public IFluidRecipeOutput createFluidOutput(FriendlyByteBuf var1);

    public IStackOutput readQueue(CompoundTag var1);

    public IInput readInput(JsonObject var1);

    public IRecipeOutput readOutput(JsonObject var1);

    public IFluidRecipeOutput readFluidOutput(JsonObject var1);

    public IInput createInputFrom(Object var1);

    public JsonObject serializeInput(IInput var1);

    public JsonObject serializeOutput(IRecipeOutput var1);

    public JsonObject serializeFluidOutput(IFluidRecipeOutput var1);
}


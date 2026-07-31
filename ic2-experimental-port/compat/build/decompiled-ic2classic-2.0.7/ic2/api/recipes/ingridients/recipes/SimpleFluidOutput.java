/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.api.recipes.ingridients.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.recipes.IFluidRecipeOutput;
import ic2.api.recipes.ingridients.recipes.SimpleRecipeOutput;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SimpleFluidOutput
extends SimpleRecipeOutput
implements IFluidRecipeOutput {
    List<FluidStack> fluidOutputs = new ObjectArrayList();

    public SimpleFluidOutput(List<ItemStack> outputs) {
        super(outputs);
    }

    public SimpleFluidOutput(List<ItemStack> outputs, List<FluidStack> fluidOutputs) {
        super(outputs);
        this.fluidOutputs.addAll(fluidOutputs);
    }

    public SimpleFluidOutput(List<ItemStack> outputs, CompoundTag nbt) {
        super(outputs, nbt);
    }

    public SimpleFluidOutput(List<ItemStack> outputs, List<FluidStack> fluidOutputs, CompoundTag nbt) {
        super(outputs, nbt);
        this.fluidOutputs.addAll(fluidOutputs);
    }

    public SimpleFluidOutput(List<ItemStack> outputs, float xp) {
        super(outputs, xp);
    }

    public SimpleFluidOutput(List<ItemStack> outputs, List<FluidStack> fluidOutputs, float xp) {
        super(outputs, xp);
        this.fluidOutputs.addAll(fluidOutputs);
    }

    public SimpleFluidOutput(List<ItemStack> outputs, CompoundTag nbt, float xp) {
        super(outputs, nbt, xp);
    }

    public SimpleFluidOutput(List<ItemStack> outputs, List<FluidStack> fluidOutputs, CompoundTag nbt, float xp) {
        super(outputs, nbt, xp);
        this.fluidOutputs.addAll(fluidOutputs);
    }

    public SimpleFluidOutput(JsonObject obj) {
        super(obj);
        for (JsonElement el : obj.getAsJsonArray("fluidOutputs")) {
            this.fluidOutputs.add(IInput.readFluidStack(el.getAsJsonObject()));
        }
    }

    public SimpleFluidOutput(FriendlyByteBuf buffer) {
        super(buffer);
        int m = buffer.m_130242_();
        for (int i = 0; i < m; ++i) {
            this.fluidOutputs.add(buffer.readFluidStack());
        }
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        super.serialize(buffer);
        buffer.m_130130_(this.fluidOutputs.size());
        int m = this.fluidOutputs.size();
        for (int i = 0; i < m; ++i) {
            buffer.writeFluidStack(this.fluidOutputs.get(i));
        }
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = super.serialize();
        JsonArray array = new JsonArray();
        for (FluidStack stack : this.fluidOutputs) {
            array.add((JsonElement)IInput.writeFluidStack(stack));
        }
        obj.add("fluidOutputs", (JsonElement)array);
        return obj;
    }

    @Override
    public List<FluidStack> onFluidRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags, ItemStack input) {
        return IFluidRecipeOutput.copyFluids(this.fluidOutputs);
    }

    @Override
    public List<FluidStack> getAllFluidOutputs() {
        return IFluidRecipeOutput.copyFluids(this.fluidOutputs);
    }
}


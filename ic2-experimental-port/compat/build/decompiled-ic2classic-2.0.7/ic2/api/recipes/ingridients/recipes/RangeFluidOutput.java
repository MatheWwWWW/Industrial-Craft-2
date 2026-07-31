/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.crafting.CraftingHelper
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.api.recipes.ingridients.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.recipes.IFluidRecipeOutput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;

public class RangeFluidOutput
implements IRecipeOutput,
IFluidRecipeOutput {
    CompoundTag nbt;
    ItemStack output;
    int minValue;
    int maxValue;
    FluidStack fluidOutput;
    int minFluid;
    int maxFluid;

    public RangeFluidOutput(JsonObject obj) {
        this.output = CraftingHelper.getItemStack((JsonObject)obj.getAsJsonObject("output"), (boolean)true);
        this.minValue = obj.get("minOut").getAsInt();
        this.maxValue = obj.get("maxOut").getAsInt();
        this.fluidOutput = IInput.readFluidStack(obj.getAsJsonObject("fluidOutput"));
        this.minFluid = obj.get("minFluid").getAsInt();
        this.maxFluid = obj.get("maxFluid").getAsInt();
        if (obj.has("nbt")) {
            this.nbt = IInput.readNBT(obj.get("nbt").getAsString());
        }
    }

    public RangeFluidOutput(FriendlyByteBuf buffer) {
        this.output = buffer.m_130267_();
        this.minValue = buffer.readByte();
        this.maxValue = buffer.readByte();
        this.fluidOutput = buffer.readFluidStack();
        this.minFluid = buffer.m_130242_();
        this.maxFluid = buffer.m_130242_();
    }

    public RangeFluidOutput(ItemStack output, int minValue, int maxValue) {
        this.output = output.m_41777_();
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.fluidOutput = FluidStack.EMPTY;
    }

    public RangeFluidOutput(ItemStack output, int minValue, int maxValue, CompoundTag nbt) {
        this.output = output.m_41777_();
        this.nbt = nbt;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.fluidOutput = FluidStack.EMPTY;
    }

    public RangeFluidOutput(FluidStack fluidOutput, int minFluid, int maxFluid) {
        this.output = ItemStack.f_41583_;
        this.fluidOutput = fluidOutput.copy();
        this.minFluid = minFluid;
        this.maxFluid = maxFluid;
    }

    public RangeFluidOutput(FluidStack fluidOutput, int minFluid, int maxFluid, CompoundTag nbt) {
        this.output = ItemStack.f_41583_;
        this.nbt = nbt;
        this.fluidOutput = fluidOutput.copy();
        this.minFluid = minFluid;
        this.maxFluid = maxFluid;
    }

    public RangeFluidOutput(ItemStack output, int minValue, int maxValue, FluidStack fluidOutput, int minFluid, int maxFluid) {
        this.output = output.m_41777_();
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.fluidOutput = fluidOutput.copy();
        this.minFluid = minFluid;
        this.maxFluid = maxFluid;
    }

    public RangeFluidOutput(ItemStack output, int minValue, int maxValue, FluidStack fluidOutput, int minFluid, int maxFluid, CompoundTag nbt) {
        this.output = output.m_41777_();
        this.nbt = nbt;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.fluidOutput = fluidOutput.copy();
        this.minFluid = minFluid;
        this.maxFluid = maxFluid;
    }

    @Override
    public List<ItemStack> onRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags) {
        if (this.output.m_41619_()) {
            return ObjectLists.emptyList();
        }
        int value = this.minValue + rand.m_188503_(this.maxValue - this.minValue + 1);
        if (value <= 0) {
            return ObjectLists.emptyList();
        }
        ItemStack stack = this.output.m_41777_();
        stack.m_41764_(value);
        return ObjectLists.singleton((Object)stack);
    }

    @Override
    public List<ItemStack> getAllOutputs() {
        if (this.output.m_41619_()) {
            return ObjectLists.emptyList();
        }
        ObjectArrayList result = new ObjectArrayList();
        int diff = this.maxValue - this.minValue;
        if (diff > 5) {
            ItemStack copy = this.output.m_41777_();
            copy.m_41764_(Math.max(1, this.minValue));
            result.add(copy);
            copy = this.output.m_41777_();
            copy.m_41764_(this.maxValue);
            result.add(copy);
        } else {
            for (int i = this.minValue; i <= this.maxValue; ++i) {
                if (i == 0) continue;
                ItemStack stack = this.output.m_41777_();
                stack.m_41764_(i);
                result.add(stack);
            }
        }
        return result;
    }

    @Override
    public List<FluidStack> onFluidRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags, ItemStack input) {
        if (this.fluidOutput.isEmpty()) {
            return ObjectLists.emptyList();
        }
        int value = this.minValue + rand.m_188503_(this.maxValue - this.minValue + 1);
        if (value <= 0) {
            return ObjectLists.emptyList();
        }
        return ObjectLists.singleton((Object)new FluidStack(this.fluidOutput, value));
    }

    @Override
    public List<FluidStack> getAllFluidOutputs() {
        if (this.fluidOutput.isEmpty()) {
            return ObjectLists.emptyList();
        }
        int step = Math.min(1, (this.maxFluid - this.minFluid) / 10);
        ObjectArrayList fluids = new ObjectArrayList();
        int value = this.minFluid;
        while (value <= this.maxFluid) {
            fluids.add(new FluidStack(this.fluidOutput, value));
            if (value == this.maxFluid) break;
            value = Math.min(this.maxFluid, value + step);
        }
        return fluids;
    }

    @Override
    public CompoundTag getMetadata() {
        return this.nbt == null ? EMPTY_COMPOUND : this.nbt;
    }

    @Override
    public float getExperience() {
        return 0.0f;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.m_130055_(this.output);
        buffer.writeByte(this.minValue);
        buffer.writeByte(this.maxValue);
        buffer.writeFluidStack(this.fluidOutput);
        buffer.m_130130_(this.minFluid);
        buffer.m_130130_(this.maxFluid);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.add("output", (JsonElement)IInput.writeItemStack(this.output));
        obj.addProperty("minOut", (Number)this.minValue);
        obj.addProperty("maxOut", (Number)this.maxValue);
        obj.add("fluidOutput", (JsonElement)IInput.writeFluidStack(this.fluidOutput));
        obj.addProperty("minFluid", (Number)this.minFluid);
        obj.addProperty("maxFluid", (Number)this.maxFluid);
        if (this.nbt != null && this.nbt != EMPTY_COMPOUND) {
            obj.addProperty("nbt", this.nbt.toString());
        }
        return obj;
    }
}


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
 */
package ic2.api.recipes.ingridients.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class RangeRecipeOutput
implements IRecipeOutput {
    CompoundTag nbt;
    ItemStack output;
    int minValue;
    int maxValue;

    public RangeRecipeOutput(JsonObject obj) {
        this.output = CraftingHelper.getItemStack((JsonObject)obj.getAsJsonObject("output"), (boolean)true);
        this.minValue = obj.get("minOut").getAsInt();
        this.maxValue = obj.get("maxOut").getAsInt();
        if (obj.has("nbt")) {
            this.nbt = IInput.readNBT(obj.get("nbt").getAsString());
        }
    }

    public RangeRecipeOutput(FriendlyByteBuf buffer) {
        this.output = buffer.m_130267_();
        this.minValue = buffer.readByte();
        this.maxValue = buffer.readByte();
    }

    public RangeRecipeOutput(ItemStack output, int minValue, int maxValue) {
        this.output = output.m_41777_();
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public RangeRecipeOutput(ItemStack output, CompoundTag nbt, int minValue, int maxValue) {
        this.output = output.m_41777_();
        this.nbt = nbt;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public List<ItemStack> onRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags) {
        ItemStack stack = this.output.m_41777_();
        stack.m_41764_(this.minValue + rand.m_188503_(this.maxValue - this.minValue + 1));
        return stack.m_41613_() <= 0 ? ObjectLists.emptyList() : ObjectLists.singleton((Object)stack);
    }

    @Override
    public List<ItemStack> getAllOutputs() {
        ObjectArrayList result = new ObjectArrayList();
        for (int i = this.minValue; i <= this.maxValue; ++i) {
            if (i == 0) continue;
            ItemStack stack = this.output.m_41777_();
            stack.m_41764_(i);
            result.add(stack);
        }
        return result;
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
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.add("output", (JsonElement)IInput.writeItemStack(this.output));
        obj.addProperty("minOut", (Number)this.minValue);
        obj.addProperty("maxOut", (Number)this.maxValue);
        if (this.nbt != null && this.nbt != EMPTY_COMPOUND) {
            obj.addProperty("nbt", this.nbt.toString());
        }
        return obj;
    }
}


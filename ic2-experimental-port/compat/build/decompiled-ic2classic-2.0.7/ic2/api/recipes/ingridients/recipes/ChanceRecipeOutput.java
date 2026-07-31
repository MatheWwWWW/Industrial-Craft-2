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
 *  net.minecraftforge.common.crafting.CraftingHelper
 */
package ic2.api.recipes.ingridients.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ChanceRecipeOutput
implements IRecipeOutput {
    List<ItemStack> outputs = new ObjectArrayList();
    float chance;
    CompoundTag nbt;
    float xp;

    public ChanceRecipeOutput(JsonObject obj) {
        this.chance = obj.get("chance").getAsFloat();
        this.xp = obj.get("xp").getAsFloat();
        if (obj.has("nbt")) {
            this.nbt = IInput.readNBT(obj.get("nbt").getAsString());
        }
        for (JsonElement el : obj.getAsJsonArray("outputs")) {
            this.outputs.add(CraftingHelper.getItemStack((JsonObject)el.getAsJsonObject(), (boolean)true));
        }
    }

    public ChanceRecipeOutput(FriendlyByteBuf buffer) {
        int expected = buffer.readByte();
        for (int i = 0; i < expected; ++i) {
            this.outputs.add(buffer.m_130267_());
        }
        this.chance = buffer.readFloat();
        this.nbt = buffer.m_130260_();
        this.xp = buffer.readFloat();
    }

    public ChanceRecipeOutput(List<ItemStack> outputs, float xp, CompoundTag nbt, float chance) {
        this.outputs.addAll(outputs);
        this.chance = chance;
        this.nbt = nbt;
        this.xp = xp;
    }

    public ChanceRecipeOutput(List<ItemStack> outputs, float xp, float chance) {
        this.outputs = outputs;
        this.chance = chance;
        this.xp = xp;
    }

    @Override
    public List<ItemStack> onRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags) {
        return rand.m_188501_() < this.chance ? IRecipeOutput.copyItems(this.outputs) : Collections.emptyList();
    }

    @Override
    public List<ItemStack> onRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags, IRecipeOutput.IRecipeOverride overrides) {
        return rand.m_188501_() < overrides.getChance(this.chance) ? IRecipeOutput.copyItems(this.outputs) : Collections.emptyList();
    }

    @Override
    public List<ItemStack> getAllOutputs() {
        return IRecipeOutput.copyItems(this.outputs);
    }

    @Override
    public CompoundTag getMetadata() {
        return this.nbt == null ? EMPTY_COMPOUND : this.nbt;
    }

    @Override
    public float getExperience() {
        return this.xp;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.writeByte(this.outputs.size());
        for (ItemStack out : this.outputs) {
            buffer.m_130055_(out);
        }
        buffer.writeFloat(this.chance);
        buffer.m_130079_(this.nbt);
        buffer.writeFloat(this.xp);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("chance", (Number)Float.valueOf(this.chance));
        obj.addProperty("xp", (Number)Float.valueOf(this.xp));
        if (this.nbt != null && this.nbt != EMPTY_COMPOUND) {
            obj.addProperty("nbt", this.nbt.toString());
        }
        JsonArray array = new JsonArray();
        for (ItemStack stack : this.outputs) {
            array.add((JsonElement)IInput.writeItemStack(stack));
        }
        obj.add("outputs", (JsonElement)array);
        return obj;
    }
}


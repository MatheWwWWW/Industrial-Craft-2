/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 */
package ic2.api.recipes.ingridients.inputs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientInput
implements IInput {
    Ingredient entry;
    int size;

    public IngredientInput(JsonObject obj) {
        this(Ingredient.m_43917_((JsonElement)obj.get("ingredient")), obj.get("size").getAsInt());
    }

    public IngredientInput(FriendlyByteBuf buffer) {
        this.entry = Ingredient.m_43940_((FriendlyByteBuf)buffer);
        this.size = buffer.readByte();
    }

    public IngredientInput(Ingredient entry, int size) {
        this.entry = entry;
        this.size = size;
    }

    @Override
    public List<ItemStack> getComponents() {
        return ObjectArrayList.wrap((Object[])this.entry.m_43908_());
    }

    @Override
    public Ingredient asIngredient() {
        return this.entry;
    }

    @Override
    public int getInputSize() {
        return this.size;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return this.entry.test(stack);
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        this.entry.m_43923_(buffer);
        buffer.writeByte(this.size);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.add("ingredient", this.entry.m_43942_());
        obj.addProperty("size", (Number)this.size);
        return obj;
    }
}


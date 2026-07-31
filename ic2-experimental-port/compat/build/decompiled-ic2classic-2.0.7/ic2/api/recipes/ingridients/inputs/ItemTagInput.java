/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.api.recipes.ingridients.inputs;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemTagInput
implements IInput {
    TagKey<Item> tag;
    int size;

    public ItemTagInput(JsonObject obj) {
        this.tag = ItemTags.create((ResourceLocation)ResourceLocation.m_135820_((String)obj.get("tag").getAsString()));
        this.size = obj.get("size").getAsInt();
    }

    public ItemTagInput(FriendlyByteBuf buffer) {
        this.tag = ItemTags.create((ResourceLocation)buffer.m_130281_());
        this.size = buffer.readByte();
    }

    public ItemTagInput(TagKey<Item> tag, int size) {
        this.tag = tag;
        this.size = size;
    }

    public ItemTagInput(TagKey<Item> tag) {
        this(tag, 1);
    }

    @Override
    public List<ItemStack> getComponents() {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (Item item : ForgeRegistries.ITEMS.tags().getTag(this.tag)) {
            list.add(new ItemStack((ItemLike)item, this.size));
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public Ingredient asIngredient() {
        if (this.size == 1) {
            return Ingredient.m_204132_(this.tag);
        }
        return IInput.super.asIngredient();
    }

    @Override
    public int getInputSize() {
        return this.size;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return stack.m_204117_(this.tag);
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.m_130085_(this.tag.f_203868_());
        buffer.writeByte(this.size);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("tag", this.tag.f_203868_().toString());
        obj.addProperty("size", (Number)this.size);
        return obj;
    }
}


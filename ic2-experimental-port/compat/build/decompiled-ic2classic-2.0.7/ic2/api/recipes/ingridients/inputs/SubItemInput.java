/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.api.recipes.ingridients.inputs;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class SubItemInput
implements IInput {
    Item item;
    int size;
    Ingredient ingredient;

    public SubItemInput(JsonObject obj) {
        this((Item)ForgeRegistries.ITEMS.getValue(ResourceLocation.m_135820_((String)obj.get("item").getAsString())), obj.get("size").getAsInt());
    }

    public SubItemInput(FriendlyByteBuf buffer) {
        this(Item.m_41445_((int)buffer.m_130242_()), (int)buffer.readByte());
    }

    public SubItemInput(ItemLike prov, int size) {
        this(prov.m_5456_(), size);
    }

    public SubItemInput(Item item, int size) {
        this.item = item;
        this.size = size;
        NonNullList items = NonNullList.m_122779_();
        item.m_6787_(CreativeModeTab.f_40754_, items);
        this.ingredient = Ingredient.m_43927_((ItemStack[])((ItemStack[])items.toArray((Object[])new ItemStack[items.size()])));
    }

    public SubItemInput(ItemLike prov) {
        this(prov.m_5456_());
    }

    public SubItemInput(Item item) {
        this(item, 1);
    }

    @Override
    public Ingredient asIngredient() {
        return this.ingredient;
    }

    @Override
    public List<ItemStack> getComponents() {
        NonNullList items = NonNullList.m_122779_();
        this.item.m_6787_(CreativeModeTab.f_40754_, items);
        return items;
    }

    @Override
    public int getInputSize() {
        return this.size;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return stack.m_41720_() == this.item;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.m_130130_(Item.m_41393_((Item)this.item));
        buffer.writeByte(this.size);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("item", ForgeRegistries.ITEMS.getKey((Object)this.item).toString());
        obj.addProperty("size", (Number)this.size);
        return obj;
    }
}


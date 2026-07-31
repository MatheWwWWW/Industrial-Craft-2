/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.api.recipes.ingridients.inputs;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInput
implements IInput {
    Item item;
    int size;
    Ingredient ingredient;

    public ItemInput(JsonObject obj) {
        this((Item)ForgeRegistries.ITEMS.getValue(ResourceLocation.m_135820_((String)obj.get("item").getAsString())), obj.get("size").getAsInt());
    }

    public ItemInput(FriendlyByteBuf buffer) {
        this(Item.m_41445_((int)buffer.m_130242_()), (int)buffer.readByte());
    }

    public ItemInput(ItemLike prov, int size) {
        this(prov.m_5456_(), size);
    }

    public ItemInput(Item item, int size) {
        this.item = item;
        this.size = size;
        this.ingredient = Ingredient.m_43929_((ItemLike[])new ItemLike[]{item});
    }

    public ItemInput(ItemLike prov) {
        this(prov.m_5456_());
    }

    public ItemInput(Item item) {
        this(item, 1);
    }

    @Override
    public Ingredient asIngredient() {
        return this.ingredient;
    }

    @Override
    public List<ItemStack> getComponents() {
        return ObjectLists.singleton((Object)new ItemStack((ItemLike)this.item, this.size));
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


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.machines.recipes.misc;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.core.item.food_and_drink.drinks.Beer;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class AlcoholInput
implements IInput {
    Item filter;
    int size;
    Ingredient ingredient;

    public AlcoholInput(Item item) {
        this(item, 1);
    }

    public AlcoholInput(Item filter, int size) {
        this.filter = filter;
        this.size = size;
        this.ingredient = Ingredient.m_43929_((ItemLike[])new ItemLike[]{filter});
    }

    public AlcoholInput(JsonObject obj) {
        this((Item)ForgeRegistries.ITEMS.getValue(ResourceLocation.m_135820_((String)obj.get("item").getAsString())), obj.get("size").getAsInt());
    }

    public AlcoholInput(FriendlyByteBuf buf) {
        this((Item)buf.readRegistryIdUnsafe(ForgeRegistries.ITEMS), buf.m_130242_());
    }

    @Override
    public List<ItemStack> getComponents() {
        ObjectList list = CollectionUtils.createList();
        for (int time = 2; time < 5; ++time) {
            for (int hops = 2; hops <= 6; ++hops) {
                for (int solid = 0; solid <= 6; ++solid) {
                    list.add((ItemStack)Beer.createData(new ItemStack((ItemLike)this.filter), time, solid, hops));
                }
            }
        }
        return list;
    }

    @Override
    public int getInputSize() {
        return this.size;
    }

    @Override
    public Ingredient asIngredient() {
        return this.ingredient;
    }

    @Override
    public boolean matches(ItemStack stack) {
        int data = StackUtil.getNbtData(stack).m_128451_("data");
        int time = Beer.getTime(data);
        return stack.m_150930_(this.filter) && Beer.getHops(data) >= 2 && time >= 2 && time < 5;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)this.filter);
        buffer.m_130130_(this.size);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("item", ForgeRegistries.ITEMS.getKey((Object)this.filter).toString());
        obj.addProperty("size", (Number)this.size);
        return obj;
    }
}


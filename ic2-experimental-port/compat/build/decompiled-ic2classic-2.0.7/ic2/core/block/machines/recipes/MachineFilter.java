/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.common.crafting.CraftingHelper
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.machines.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.RecipeRegistry;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.registries.IRecipeFilter;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class MachineFilter
implements IRecipeFilter,
IRecipeList {
    Set<Item> simple = CollectionUtils.createLinkedSet();
    List<IInput> filters = CollectionUtils.createList();

    @Override
    public void add(ItemLike ... items) {
        for (ItemLike prov : items) {
            this.simple.add(prov.m_5456_());
        }
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        for (Item item : this.simple) {
            array.add(ForgeRegistries.ITEMS.getKey((Object)item).toString());
        }
        obj.add("simple", (JsonElement)array);
        array = new JsonArray();
        for (IInput input : this.filters) {
            array.add((JsonElement)IngredientRegistry.INSTANCE.serializeInput(input));
        }
        obj.add("special", (JsonElement)array);
        return obj;
    }

    @Override
    public void read(JsonObject obj) {
        boolean remove = GsonHelper.m_13855_((JsonObject)obj, (String)"remove", (boolean)false);
        IRecipeList.iteratePrimitive(obj.get("simple"), T -> {
            Item item = CraftingHelper.getItem((String)T.getAsString(), (boolean)true);
            if (remove) {
                this.simple.remove(item);
            } else {
                this.simple.add(item);
            }
        });
        IRecipeList.iterateObject(obj.get("special"), T -> {
            IInput input = IngredientRegistry.INSTANCE.readInput((JsonObject)T);
            if (remove) {
                this.remove(input.getComponents().get(0), 2);
            } else {
                this.filters.add(input);
            }
        });
    }

    @Override
    public void add(IInput ... inputs) {
        this.filters.addAll((Collection<IInput>)ObjectArrayList.wrap((Object[])inputs));
    }

    @Override
    public void remove(ItemStack stack, int flags) {
        if ((flags & 1) != 0) {
            this.simple.remove(stack.m_41720_());
        }
        if ((flags & 2) != 0) {
            int m = this.filters.size();
            for (int i = 0; i < m; ++i) {
                IInput input = this.filters.get(i);
                if (!input.matches(stack)) continue;
                this.filters.remove(i--);
                --m;
            }
        }
    }

    @Override
    public boolean contains(ItemStack stack) {
        if (this.simple.contains(stack.m_41720_())) {
            return true;
        }
        int m = this.filters.size();
        for (int i = 0; i < m; ++i) {
            if (!this.filters.get(i).matches(stack)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        this.simple.clear();
        this.filters.clear();
    }

    @Override
    public List<ItemStack> getFilteredItems() {
        ObjectList list = CollectionUtils.createList();
        for (Item item : this.simple) {
            list.add((ItemStack)new ItemStack((ItemLike)item));
        }
        int m = this.filters.size();
        for (int i = 0; i < m; ++i) {
            list.addAll(this.filters.get(i).getComponents());
        }
        return list;
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.simple.size());
        for (Item item : this.simple) {
            buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)item);
        }
        buffer.m_130130_(this.filters.size());
        int m = this.filters.size();
        for (int i = 0; i < m; ++i) {
            RecipeRegistry.INGREDIENTS.writeInput(this.filters.get(i), buffer);
        }
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        ObjectSortedSet simpleSync = CollectionUtils.createLinkedSet();
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            Item item = (Item)buffer.readRegistryIdUnsafe(ForgeRegistries.ITEMS);
            if (item == null || item == Items.f_41852_) continue;
            simpleSync.add((Item)item);
        }
        this.simple = simpleSync;
        ObjectList filtersSync = CollectionUtils.createList();
        size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            IInput input = RecipeRegistry.INGREDIENTS.readInput(buffer);
            if (input == null) continue;
            filtersSync.add((IInput)input);
        }
        this.filters = filtersSync;
    }

    @Override
    public Map<ResourceLocation, JsonObject> writeRecipes() {
        return Collections.emptyMap();
    }

    @Override
    public void readRecipe(ResourceLocation id, JsonObject data) throws Exception {
    }
}


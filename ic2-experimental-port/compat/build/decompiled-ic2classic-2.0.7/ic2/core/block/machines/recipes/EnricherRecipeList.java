/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.crafting.CraftingHelper
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.machines.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.registries.IListenableRegistry;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.block.machines.recipes.misc.EnrichRecipe;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class EnricherRecipeList
implements IRecipeList,
IListenableRegistry<EnricherRecipeList> {
    List<Consumer<EnricherRecipeList>> listeners = ObjectLists.synchronize(CollectionUtils.createList());
    Map<ResourceLocation, EnrichRecipe> recipes = CollectionUtils.createLinkedMap();
    Set<Item> validInputs = CollectionUtils.createSet();

    public EnricherRecipeList(Consumer<EnricherRecipeList> listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    public void reload() {
        this.recipes.clear();
        int m = this.listeners.size();
        for (int i = 0; i < m; ++i) {
            this.listeners.get(i).accept(this);
        }
    }

    public void registerRecipe(EnrichRecipe recipe) {
        if (this.recipes.containsKey(recipe.getId())) {
            this.recipes.get(recipe.getId()).addInputs(recipe.getInputs());
        } else {
            this.recipes.put(recipe.getId(), recipe);
        }
        this.validInputs.addAll(recipe.getInputs());
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.recipes.size());
        for (EnrichRecipe recipe : this.recipes.values()) {
            recipe.write(buffer);
        }
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        Object2ObjectSortedMap<ResourceLocation, EnrichRecipe> newRecipes = CollectionUtils.createLinkedMap();
        ObjectSet newValidInputs = CollectionUtils.createSet();
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            EnrichRecipe recipe = new EnrichRecipe(buffer);
            newRecipes.put(recipe.getId(), recipe);
            newValidInputs.addAll(recipe.getInputs());
        }
        this.recipes = newRecipes;
        this.validInputs = newValidInputs;
    }

    @Override
    public String getFolder() {
        return "enricher";
    }

    @Override
    public Map<ResourceLocation, JsonObject> writeRecipes() {
        Object2ObjectSortedMap<ResourceLocation, JsonObject> map = CollectionUtils.createLinkedMap();
        for (EnrichRecipe recipe : this.recipes.values()) {
            JsonObject obj = new JsonObject();
            JsonArray inputs = new JsonArray();
            for (Item i : recipe.getInputs()) {
                inputs.add(ForgeRegistries.ITEMS.getKey((Object)i).toString());
            }
            obj.add("inputs", (JsonElement)inputs);
            JsonArray array = new JsonArray();
            for (Object2IntMap.Entry entry : recipe.getFuelValues().object2IntEntrySet()) {
                JsonObject value = new JsonObject();
                value.add("input", (JsonElement)IngredientRegistry.INSTANCE.serializeInput((IInput)entry.getKey()));
                value.addProperty("value", (Number)entry.getIntValue());
                array.add((JsonElement)value);
            }
            obj.add("fuels", (JsonElement)array);
            obj.add("output", (JsonElement)IInput.writeItemStack(recipe.getOutput()));
            obj.addProperty("points", (Number)recipe.getRequiredPoints());
            obj.addProperty("color", (Number)recipe.getColor());
            obj.addProperty("energy_usage", (Number)recipe.getEnergyCost());
            map.put(recipe.getId(), obj);
        }
        return map;
    }

    @Override
    public void readRecipe(ResourceLocation id, JsonObject data) {
        if (IRecipeList.isRemover(data)) {
            IRecipeList.getEntriesToRemove(data, this::removeRecipe);
            return;
        }
        ObjectLinkedOpenHashSet inputs = ObjectLinkedOpenHashSet.of();
        if (data.has("input")) {
            inputs.add((Object)CraftingHelper.getItem((String)GsonHelper.m_13906_((JsonObject)data, (String)"input"), (boolean)true));
        } else if (data.has("inputs")) {
            IRecipeList.iterateObject(data.get("inputs"), arg_0 -> EnricherRecipeList.lambda$readRecipe$0((ObjectSet)inputs, arg_0));
        } else {
            throw new RuntimeException("input is Empty");
        }
        Object2IntLinkedOpenHashMap fuels = new Object2IntLinkedOpenHashMap();
        IRecipeList.iterateObject(data.get("fuels"), arg_0 -> EnricherRecipeList.lambda$readRecipe$1((Object2IntMap)fuels, arg_0));
        ItemStack output = CraftingHelper.getItemStack((JsonObject)GsonHelper.m_13930_((JsonObject)data, (String)"output"), (boolean)true);
        if (output.m_41619_()) {
            throw new RuntimeException("output is Empty");
        }
        int points = GsonHelper.m_13927_((JsonObject)data, (String)"points");
        if (points <= 0) {
            throw new RuntimeException("There needs to be at least 1 point to be consumed");
        }
        int energy = GsonHelper.m_13927_((JsonObject)data, (String)"energy_usage");
        if (energy <= 0) {
            throw new RuntimeException("energy consumption has to be at least 1");
        }
        this.registerRecipe(new EnrichRecipe(id, (ObjectSet<Item>)inputs, (Object2IntMap<IInput>)fuels, output, points, GsonHelper.m_13927_((JsonObject)data, (String)"color"), energy));
    }

    @Override
    public void registerListener(Consumer<EnricherRecipeList> listener) {
        this.listeners.add(listener);
    }

    public List<EnrichRecipe> getRecipes() {
        return new ObjectArrayList(this.recipes.values());
    }

    public void removeRecipe(ResourceLocation location) {
        EnrichRecipe recipe = this.recipes.remove(location);
        if (recipe == null) {
            return;
        }
        this.validInputs.clear();
        for (EnrichRecipe r : this.recipes.values()) {
            this.validInputs.addAll(r.getInputs());
        }
    }

    public EnrichRecipe getRecipe(ResourceLocation location) {
        return this.recipes.get(location);
    }

    public boolean hasRecipeForInput(ItemStack input) {
        return this.validInputs.contains(input.m_41720_());
    }

    public EnrichRecipe getRecipeFromFuel(ItemStack fuel) {
        for (EnrichRecipe recipe : this.recipes.values()) {
            if (recipe.getFuelValue(fuel) <= 0) continue;
            return recipe;
        }
        return null;
    }

    private static /* synthetic */ void lambda$readRecipe$1(Object2IntMap fuels, JsonObject T) {
        fuels.put((Object)IngredientRegistry.INSTANCE.readInput(GsonHelper.m_13930_((JsonObject)T, (String)"input")), GsonHelper.m_13927_((JsonObject)T, (String)"value"));
    }

    private static /* synthetic */ void lambda$readRecipe$0(ObjectSet inputs, JsonObject T) {
        inputs.add((Object)CraftingHelper.getItem((String)T.getAsString(), (boolean)true));
    }
}


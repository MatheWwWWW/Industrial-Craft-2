/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.crafting.CraftingHelper
 */
package ic2.core.block.machines.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.registries.IElectrolyzerRecipeList;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ElectrolyzerRecipeList
implements IElectrolyzerRecipeList,
IRecipeList {
    List<Consumer<IElectrolyzerRecipeList>> listeners = ObjectLists.synchronize(CollectionUtils.createList());
    Map<ResourceLocation, IElectrolyzerRecipeList.ElectrolyzerRecipe> allRecipes = CollectionUtils.createMap();
    Map<ItemStack, IElectrolyzerRecipeList.ElectrolyzerRecipe> chargeRecipe = CollectionUtils.createMap(ItemStackStrategy.INSTANCE);
    Map<ItemStack, IElectrolyzerRecipeList.ElectrolyzerRecipe> dischargeRecipe = CollectionUtils.createMap(ItemStackStrategy.INSTANCE);

    public ElectrolyzerRecipeList(Consumer<IElectrolyzerRecipeList> listener) {
        this.listeners.add(listener);
    }

    public void reload() {
        this.allRecipes.clear();
        this.dischargeRecipe.clear();
        this.chargeRecipe.clear();
        for (Consumer<IElectrolyzerRecipeList> entry : this.listeners) {
            entry.accept(this);
        }
    }

    @Override
    public void registerListener(Consumer<IElectrolyzerRecipeList> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.allRecipes.size());
        for (IElectrolyzerRecipeList.ElectrolyzerRecipe entry : this.allRecipes.values()) {
            entry.serialize(buffer);
        }
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        Object2ObjectMap<ResourceLocation, IElectrolyzerRecipeList.ElectrolyzerRecipe> recipes = CollectionUtils.createMap();
        Object2ObjectMap<ItemStack, IElectrolyzerRecipeList.ElectrolyzerRecipe> tempChargeRecipe = CollectionUtils.createMap(ItemStackStrategy.INSTANCE);
        Object2ObjectMap<ItemStack, IElectrolyzerRecipeList.ElectrolyzerRecipe> tempDischargeRecipe = CollectionUtils.createMap(ItemStackStrategy.INSTANCE);
        int limit = buffer.m_130242_();
        for (int i = 0; i < limit; ++i) {
            IElectrolyzerRecipeList.ElectrolyzerRecipe recipe = new IElectrolyzerRecipeList.ElectrolyzerRecipe(buffer);
            recipes.put(recipe.getId(), recipe);
            if (recipe.isChargeRecipe()) {
                tempChargeRecipe.put(recipe.getInput(), recipe);
            }
            if (!recipe.isDischargeRecipe()) continue;
            tempDischargeRecipe.put(recipe.getOutput(), recipe);
        }
        this.allRecipes = recipes;
        this.chargeRecipe = tempChargeRecipe;
        this.dischargeRecipe = tempDischargeRecipe;
    }

    @Override
    public String getFolder() {
        return "electrolyzer";
    }

    @Override
    public Map<ResourceLocation, JsonObject> writeRecipes() {
        Object2ObjectSortedMap<ResourceLocation, JsonObject> map = CollectionUtils.createLinkedMap();
        for (IElectrolyzerRecipeList.ElectrolyzerRecipe recipe : this.allRecipes.values()) {
            JsonObject obj = new JsonObject();
            obj.add("input", (JsonElement)IInput.writeItemStack(recipe.getInput()));
            obj.add("output", (JsonElement)IInput.writeItemStack(recipe.getOutput()));
            obj.addProperty("energy", (Number)recipe.getEnergy());
            obj.addProperty("charging", Boolean.valueOf(recipe.isChargeRecipe()));
            obj.addProperty("discharging", Boolean.valueOf(recipe.isDischargeRecipe()));
            map.put(recipe.getId(), obj);
        }
        return map;
    }

    @Override
    public void readRecipe(ResourceLocation id, JsonObject data) throws Exception {
        if (IRecipeList.isRemover(data)) {
            IRecipeList.getEntriesToRemove(data, this::removeRecipe);
            return;
        }
        ItemStack input = CraftingHelper.getItemStack((JsonObject)data.getAsJsonObject("input"), (boolean)true);
        if (input.m_41619_()) {
            throw new RuntimeException("Input is empty");
        }
        ItemStack output = CraftingHelper.getItemStack((JsonObject)data.getAsJsonObject("output"), (boolean)true);
        if (output.m_41619_()) {
            throw new RuntimeException("Output is empty");
        }
        int flags = (GsonHelper.m_13855_((JsonObject)data, (String)"charging", (boolean)false) ? 1 : 0) | (GsonHelper.m_13855_((JsonObject)data, (String)"discharging", (boolean)false) ? 2 : 0);
        if (flags == 0) {
            throw new RuntimeException("Recipe at the very least charging/discharging");
        }
        this.addRecipe(id, input, output, flags, GsonHelper.m_13927_((JsonObject)data, (String)"energy"));
    }

    @Override
    public void addRecipe(ResourceLocation id, ItemStack input, ItemStack output, int flags, int energy) {
        if (flags == 0 || energy <= 100 || input.m_41619_() || output.m_41619_()) {
            return;
        }
        IElectrolyzerRecipeList.ElectrolyzerRecipe recipe = new IElectrolyzerRecipeList.ElectrolyzerRecipe(id, input.m_41777_(), output.m_41777_(), flags, energy);
        this.allRecipes.put(id, recipe);
        if (recipe.isChargeRecipe()) {
            this.chargeRecipe.put(input, recipe);
        }
        if (recipe.isDischargeRecipe()) {
            this.dischargeRecipe.put(output, recipe);
        }
    }

    @Override
    public void removeRecipe(ItemStack input, boolean charge) {
        (charge ? this.chargeRecipe : this.dischargeRecipe).remove(input);
    }

    @Override
    public void removeRecipe(ResourceLocation id) {
        IElectrolyzerRecipeList.ElectrolyzerRecipe recipe = this.allRecipes.remove(id);
        if (recipe == null) {
            return;
        }
        if (recipe.isChargeRecipe()) {
            this.chargeRecipe.remove(recipe.getInput());
        }
        if (recipe.isDischargeRecipe()) {
            this.dischargeRecipe.remove(recipe.getOutput());
        }
    }

    @Override
    public List<IElectrolyzerRecipeList.ElectrolyzerRecipe> getRecipes() {
        return new ObjectArrayList(this.allRecipes.values());
    }

    @Override
    public IElectrolyzerRecipeList.ElectrolyzerRecipe getRecipe(ItemStack input, boolean charge, boolean compareSize) {
        IElectrolyzerRecipeList.ElectrolyzerRecipe recipe = (charge ? this.chargeRecipe : this.dischargeRecipe).get(input);
        if (recipe != null && (!compareSize || recipe.matches(input, charge))) {
            return recipe;
        }
        return null;
    }
}


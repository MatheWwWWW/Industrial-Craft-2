/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.ItemStack
 *  org.apache.commons.lang3.NotImplementedException
 */
package ic2.core.block.machines.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.NotImplementedException;

public class MultiMachineRecipeList
implements IMachineRecipeList,
IRecipeList {
    List<Consumer<IMachineRecipeList>> listeners = ObjectLists.synchronize(CollectionUtils.createList());
    Map<ResourceLocation, IMachineRecipeList.RecipeEntry> recipeMap = CollectionUtils.createLinkedMap();
    int maxInputs;
    boolean exact;
    String folder;

    public MultiMachineRecipeList(String folder, int maxInputs, boolean exact, Consumer<IMachineRecipeList> listener) {
        this.folder = folder;
        this.maxInputs = maxInputs;
        this.exact = exact;
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    @Override
    public void registerListener(Consumer<IMachineRecipeList> listener) {
        this.listeners.add(listener);
    }

    public void reload() {
        this.recipeMap.clear();
        for (Consumer<IMachineRecipeList> listener : this.listeners) {
            listener.accept(this);
        }
    }

    @Override
    public void addRecipe(IMachineRecipeList.RecipeEntry recipe) {
        IInput[] inputs = recipe.getInputs();
        if (inputs == null || (!this.exact ? inputs.length > this.maxInputs : inputs.length != this.maxInputs) || recipe.getOutput() == null) {
            throw new RuntimeException("Recipe Is Invalid: " + recipe.getLocation());
        }
        this.recipeMap.put(recipe.getLocation(), recipe);
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.recipeMap.size());
        for (IMachineRecipeList.RecipeEntry entry : this.recipeMap.values()) {
            buffer.m_130085_(entry.getLocation());
            IngredientRegistry.INSTANCE.writeRecipeOutput(entry.getOutput(), buffer);
            buffer.writeByte(entry.getInputs().length);
            for (IInput input : entry.getInputs()) {
                IngredientRegistry.INSTANCE.writeInput(input, buffer);
            }
        }
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        Object2ObjectSortedMap<ResourceLocation, IMachineRecipeList.RecipeEntry> recipes = CollectionUtils.createLinkedMap();
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            ResourceLocation location = buffer.m_130281_();
            IRecipeOutput output = IngredientRegistry.INSTANCE.createOutput(buffer);
            IInput[] inputs = new IInput[buffer.readByte()];
            for (int j = 0; j < inputs.length; ++j) {
                inputs[j] = IngredientRegistry.INSTANCE.readInput(buffer);
            }
            recipes.put(location, new IMachineRecipeList.RecipeEntry(location, output, inputs));
        }
        this.recipeMap = recipes;
    }

    @Override
    public String getFolder() {
        return this.folder;
    }

    @Override
    public Map<ResourceLocation, JsonObject> writeRecipes() {
        Object2ObjectSortedMap<ResourceLocation, JsonObject> result = CollectionUtils.createLinkedMap();
        for (IMachineRecipeList.RecipeEntry entry : this.recipeMap.values()) {
            JsonObject obj = new JsonObject();
            if (entry.getInputs().length > 1) {
                JsonArray inputs = new JsonArray();
                for (IInput input : entry.getInputs()) {
                    inputs.add((JsonElement)IngredientRegistry.INSTANCE.serializeInput(input));
                }
                obj.add("input", (JsonElement)inputs);
            } else {
                obj.add("input", (JsonElement)IngredientRegistry.INSTANCE.serializeInput(entry.getInputs()[0]));
            }
            obj.add("output", (JsonElement)IngredientRegistry.INSTANCE.serializeOutput(entry.getOutput()));
            result.put(entry.getLocation(), obj);
        }
        return result;
    }

    @Override
    public void readRecipe(ResourceLocation id, JsonObject data) throws Exception {
        if (IRecipeList.isRemover(data)) {
            IRecipeList.getEntriesToRemove(data, this::removeRecipe);
            return;
        }
        ObjectList inputs = CollectionUtils.createList();
        IRecipeList.iterateObject(data.get("input"), T -> inputs.add(IngredientRegistry.INSTANCE.readInput((JsonObject)T)));
        if (inputs.isEmpty()) {
            throw new RuntimeException("Inputs are empty");
        }
        IRecipeOutput out = IngredientRegistry.INSTANCE.readOutput(GsonHelper.m_13930_((JsonObject)data, (String)"output"));
        if (out == null) {
            throw new RuntimeException("Output is Empty");
        }
        this.addRecipe(new IMachineRecipeList.RecipeEntry(id, out, (IInput[])inputs.toArray(IInput[]::new)));
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(ResourceLocation location) {
        return this.recipeMap.get(location);
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(Predicate<IMachineRecipeList.RecipeEntry> checker) {
        for (IMachineRecipeList.RecipeEntry entry : this.recipeMap.values()) {
            if (!checker.test(entry)) continue;
            return entry;
        }
        return null;
    }

    @Override
    public IMachineRecipeList.RecipeEntry removeRecipe(ResourceLocation location) {
        return this.recipeMap.remove(location);
    }

    @Override
    public List<ResourceLocation> getAllRecipes() {
        return new ObjectArrayList(this.recipeMap.keySet());
    }

    @Override
    public List<IMachineRecipeList.RecipeEntry> getAllEntries() {
        return new ObjectArrayList(this.recipeMap.values());
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(ItemStack stack, boolean hasStackSize) {
        throw new NotImplementedException("Function not supported in MultiRecipeLists");
    }
}


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
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.core.block.machines.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.recipes.IFluidRecipeOutput;
import ic2.api.recipes.registries.IRefiningRecipeList;
import ic2.core.IC2;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class RefiningRecipeList
implements IRefiningRecipeList,
IRecipeList {
    List<Consumer<IRefiningRecipeList>> listeners = ObjectLists.synchronize(CollectionUtils.createList());
    Object2ObjectMap<ResourceLocation, IRefiningRecipeList.FluidRecipe> allRecipes = CollectionUtils.createLinkedMap();
    Object2ObjectMap<Item, Map<Fluid, Map<Fluid, IRefiningRecipeList.FluidRecipe>>> mappedRecipe;
    Set<Fluid> validFirstFluid;
    Set<Fluid> validSecondFluid;
    boolean mapped = false;

    public RefiningRecipeList(Consumer<IRefiningRecipeList> listener) {
        this.registerListener(listener);
    }

    @Override
    public void registerListener(Consumer<IRefiningRecipeList> listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    @Override
    public void addRecipe(ResourceLocation location, FluidStack input, FluidStack secondInput, IInput catalyst, IFluidRecipeOutput output) {
        if (location == null || input == null || secondInput == null || catalyst == null || output == null) {
            IC2.LOGGER.info("Recipe [" + location + "] is invalid");
            return;
        }
        this.allRecipes.put((Object)location, (Object)new IRefiningRecipeList.FluidRecipe(location, input, secondInput, catalyst, output));
        this.allRecipes.put((Object)new ResourceLocation(location.m_135827_(), location.m_135815_() + "_inverted"), (Object)new IRefiningRecipeList.FluidRecipe(location, secondInput, input, catalyst, output));
        this.mapped = false;
        this.mappedRecipe = null;
    }

    public void reload() {
        this.allRecipes.clear();
        this.mappedRecipe = null;
        this.validFirstFluid = null;
        this.validSecondFluid = null;
        this.mapped = false;
        for (Consumer<IRefiningRecipeList> entry : this.listeners) {
            entry.accept(this);
        }
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.allRecipes.size());
        for (IRefiningRecipeList.FluidRecipe entry : this.allRecipes.values()) {
            buffer.m_130085_(entry.getLocation());
            IngredientRegistry.INSTANCE.writeInput(entry.getItemInput(), buffer);
            buffer.writeFluidStack(entry.getFirstTank());
            buffer.writeFluidStack(entry.getSecondTank());
            IngredientRegistry.INSTANCE.writeFluidOutput(entry.getOutput(), buffer);
        }
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        Object2ObjectSortedMap newAllRecipes = CollectionUtils.createLinkedMap();
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            ResourceLocation location = buffer.m_130281_();
            IInput input = IngredientRegistry.INSTANCE.readInput(buffer);
            FluidStack firstTank = buffer.readFluidStack();
            FluidStack secondTank = buffer.readFluidStack();
            IFluidRecipeOutput output = IngredientRegistry.INSTANCE.createFluidOutput(buffer);
            newAllRecipes.put((Object)location, (Object)new IRefiningRecipeList.FluidRecipe(location, firstTank, secondTank, input, output));
        }
        this.allRecipes = newAllRecipes;
        this.mappedRecipe = null;
        this.validFirstFluid = null;
        this.validSecondFluid = null;
        this.mapped = false;
    }

    @Override
    public String getFolder() {
        return "refinery";
    }

    @Override
    public Map<ResourceLocation, JsonObject> writeRecipes() {
        Object2ObjectSortedMap<ResourceLocation, JsonObject> map = CollectionUtils.createLinkedMap();
        for (IRefiningRecipeList.FluidRecipe entry : this.allRecipes.values()) {
            JsonObject obj = new JsonObject();
            obj.add("input", (JsonElement)IngredientRegistry.INSTANCE.serializeInput(entry.getItemInput()));
            obj.add("first_tank", (JsonElement)IInput.writeFluidStack(entry.getFirstTank()));
            obj.add("second_tank", (JsonElement)IInput.writeFluidStack(entry.getSecondTank()));
            obj.add("output", (JsonElement)IngredientRegistry.INSTANCE.serializeFluidOutput(entry.getOutput()));
            map.put(entry.getLocation(), obj);
        }
        return map;
    }

    @Override
    public void readRecipe(ResourceLocation id, JsonObject data) throws Exception {
        if (IRecipeList.isRemover(data)) {
            IRecipeList.getEntriesToRemove(data, this::removeRecipe);
            return;
        }
        IInput input = IngredientRegistry.INSTANCE.readInput(GsonHelper.m_13930_((JsonObject)data, (String)"input"));
        if (input == null) {
            throw new RuntimeException("Input is null, while Empty is allowed, Null isn't");
        }
        FluidStack first = IInput.readFluidStack(GsonHelper.m_13930_((JsonObject)data, (String)"first_tank"));
        if (first == null) {
            throw new RuntimeException("First Fluid is null. Empty is allowed, but null isn't");
        }
        FluidStack second = IInput.readFluidStack(GsonHelper.m_13930_((JsonObject)data, (String)"second_tank"));
        if (second == null) {
            throw new RuntimeException("Second Fluid is null. Empty is allowed, but null isn't");
        }
        IFluidRecipeOutput output = IngredientRegistry.INSTANCE.readFluidOutput(GsonHelper.m_13930_((JsonObject)data, (String)"output"));
        if (output == null) {
            throw new RuntimeException("Output is null");
        }
        this.addRecipe(id, first, second, input, output);
    }

    protected void mapRecipes() {
        if (this.mapped) {
            return;
        }
        this.mapped = true;
        this.mappedRecipe = CollectionUtils.createMap();
        this.validFirstFluid = CollectionUtils.createSet();
        this.validSecondFluid = CollectionUtils.createSet();
        for (IRefiningRecipeList.FluidRecipe recipe : this.allRecipes.values()) {
            this.validFirstFluid.add(recipe.getFirstTank().getFluid());
            this.validSecondFluid.add(recipe.getSecondTank().getFluid());
            for (ItemStack stack : recipe.getItemInput().getComponents()) {
                Object2ObjectMap subMap = (Object2ObjectMap)this.mappedRecipe.get((Object)stack.m_41720_());
                if (subMap == null) {
                    subMap = CollectionUtils.createMap();
                    this.mappedRecipe.put((Object)stack.m_41720_(), subMap);
                }
                this.map((Map<Fluid, Map<Fluid, IRefiningRecipeList.FluidRecipe>>)subMap, recipe.getFirstTank(), recipe.getSecondTank(), recipe);
            }
        }
        this.validFirstFluid.remove(Fluids.f_76191_);
        this.validSecondFluid.remove(Fluids.f_76191_);
    }

    private void map(Map<Fluid, Map<Fluid, IRefiningRecipeList.FluidRecipe>> subMap, FluidStack first, FluidStack last, IRefiningRecipeList.FluidRecipe recipe) {
        Map<Fluid, IRefiningRecipeList.FluidRecipe> old = subMap.get(first.getFluid());
        if (old == null) {
            old = CollectionUtils.createMap();
            subMap.put(first.getFluid(), old);
        }
        old.putIfAbsent(last.getFluid(), recipe);
    }

    @Override
    public IRefiningRecipeList.FluidRecipe getRecipe(ResourceLocation location) {
        return (IRefiningRecipeList.FluidRecipe)this.allRecipes.get((Object)location);
    }

    @Override
    public IRefiningRecipeList.FluidRecipe getRecipe(ItemStack item, FluidStack firstTank, FluidStack secondTank, boolean testAmounts) {
        this.mapRecipes();
        Map subMap = (Map)this.mappedRecipe.get((Object)item.m_41720_());
        if (subMap == null || subMap.isEmpty()) {
            return null;
        }
        Map map = (Map)subMap.get(firstTank.getFluid());
        if (map == null || map.isEmpty()) {
            return null;
        }
        IRefiningRecipeList.FluidRecipe recipe = (IRefiningRecipeList.FluidRecipe)map.get(secondTank.getFluid());
        if (recipe == null) {
            return null;
        }
        if (testAmounts) {
            return recipe.getItemInput().matches(item) && recipe.getItemInput().getInputSize() <= item.m_41613_() && firstTank.containsFluid(recipe.getFirstTank()) && secondTank.containsFluid(recipe.getSecondTank()) ? recipe : null;
        }
        return recipe.getItemInput().matches(item) && firstTank.isFluidEqual(recipe.getFirstTank()) && secondTank.isFluidEqual(recipe.getSecondTank()) ? recipe : null;
    }

    @Override
    public IRefiningRecipeList.FluidRecipe getRecipe(Predicate<IRefiningRecipeList.FluidRecipe> checker) {
        for (IRefiningRecipeList.FluidRecipe recipe : this.allRecipes.values()) {
            if (!checker.test(recipe)) continue;
            return recipe;
        }
        return null;
    }

    private void removeRecipe(ResourceLocation location) {
        this.removeRecipe(location, true);
    }

    @Override
    public IRefiningRecipeList.FluidRecipe removeRecipe(ResourceLocation location, boolean includeInverted) {
        IRefiningRecipeList.FluidRecipe recipe = (IRefiningRecipeList.FluidRecipe)this.allRecipes.remove((Object)location);
        if (recipe != null) {
            this.mapped = false;
            this.mappedRecipe = null;
            this.validFirstFluid = null;
            this.validSecondFluid = null;
            this.allRecipes.remove((Object)new ResourceLocation(location.m_135827_(), location.m_135815_() + "_inverted"));
        }
        return recipe;
    }

    @Override
    public boolean isValidCatalyst(ItemStack stack) {
        this.mapRecipes();
        return this.mappedRecipe.containsKey((Object)stack.m_41720_());
    }

    @Override
    public boolean isValidFirstFluid(FluidStack fluid) {
        this.mapRecipes();
        return this.validFirstFluid.contains(fluid.getFluid());
    }

    @Override
    public boolean isValidSecondFluid(FluidStack fluid) {
        this.mapRecipes();
        return this.validSecondFluid.contains(fluid.getFluid());
    }

    @Override
    public List<IRefiningRecipeList.FluidRecipe> getAllRecipes() {
        return new ObjectArrayList(this.allRecipes.values());
    }
}


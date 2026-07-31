/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.oredict.OreDictionary$OreRegisterEvent
 */
package ic2.core.recipe.dynamic;

import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.recipe.dynamic.DynamicRecipe;
import ic2.core.recipe.dynamic.IDynamicRecipeManager;
import ic2.core.recipe.dynamic.RecipeIngredient;
import ic2.core.recipe.dynamic.RecipeInputFluidStack;
import ic2.core.recipe.dynamic.RecipeInputIngredient;
import ic2.core.recipe.dynamic.RecipeInputItemStack;
import ic2.core.recipe.dynamic.RecipeInputOreDictionary;
import ic2.core.recipe.dynamic.RecipeOutputIngredient;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class DynamicRecipeManager
implements IDynamicRecipeManager {
    protected final Map<Collection<RecipeInputIngredient>, DynamicRecipe> recipes = new HashMap<Collection<RecipeInputIngredient>, DynamicRecipe>();
    private final Map<Item, List<DynamicRecipe>> recipeCacheItem = new IdentityHashMap<Item, List<DynamicRecipe>>();
    private final Map<String, List<DynamicRecipe>> recipeCacheFluid = new IdentityHashMap<String, List<DynamicRecipe>>();
    private final List<DynamicRecipe> uncacheableRecipes = new ArrayList<DynamicRecipe>();
    private static boolean oreRegisterEventSubscribed;
    private static final Set<DynamicRecipeManager> watchingManagers;

    public DynamicRecipe createRecipe() {
        return new DynamicRecipe(this);
    }

    @Override
    public boolean addRecipe(DynamicRecipe recipe, boolean replace) {
        if (recipe.getInputIngredients() == null) {
            throw new NullPointerException("The recipe input is null");
        }
        if (recipe.getInputIngredients().size() <= 0) {
            throw new IllegalArgumentException("No inputs");
        }
        if (recipe.getOutputIngredients() == null) {
            throw new NullPointerException("The recipe output is null");
        }
        if (recipe.getOutputIngredients().size() <= 0) {
            throw new IllegalArgumentException("No outputs");
        }
        ArrayList<RecipeInputIngredient> listOfInputs = new ArrayList<RecipeInputIngredient>(recipe.getInputIngredients().size());
        for (RecipeInputIngredient recipeInputIngredient : recipe.getInputIngredients()) {
            if (recipeInputIngredient.isEmpty()) {
                this.displayError("The RecipeInputIngredient " + recipeInputIngredient.toStringSafe() + " is invalid.");
                return false;
            }
            listOfInputs.add(recipeInputIngredient);
        }
        ArrayList<RecipeOutputIngredient> listOfOutputs = new ArrayList<RecipeOutputIngredient>(recipe.getOutputIngredients().size());
        for (RecipeOutputIngredient entry : recipe.getOutputIngredients()) {
            if (entry.isEmpty()) {
                this.displayError("The RecipeOutputIngredient " + entry.toStringSafe() + " is invalid.");
                return false;
            }
            listOfOutputs.add(entry);
        }
        DynamicRecipe dynamicRecipe = this.getRecipe(recipe.getInputIngredients());
        if (dynamicRecipe != null) {
            if (replace) {
                do {
                    this.recipes.remove(recipe.getInputIngredients());
                    this.removeCachedRecipes(recipe.getInputIngredients());
                } while ((recipe = this.getRecipe(recipe.getInputIngredients())) != null);
            } else {
                IC2.log.error(LogCategory.Recipe, "Skipping %s => %s due to duplicate recipe for %s (%s => %s)", recipe.getInputIngredients(), recipe.getOutputIngredients(), recipe.getInputIngredients(), recipe.getInputIngredients(), recipe.getOutputIngredients());
                return false;
            }
        }
        DynamicRecipe newRecipe = this.createRecipe().withInput(listOfInputs).withOutput(listOfOutputs).withOperationEnergyCost(recipe.getOperationEnergyCost()).withOperationDurationTicks(recipe.getOperationDuration()).withMetadata(recipe.getMetadata());
        this.recipes.put(recipe.getInputIngredients(), newRecipe);
        this.addToCache(newRecipe);
        return true;
    }

    protected DynamicRecipe getRecipe(Collection<RecipeInputIngredient> input) {
        RecipeInputIngredient temp;
        ListIterator<RecipeInputIngredient> itB;
        if (input.isEmpty()) {
            return null;
        }
        ArrayList recipes = new ArrayList();
        for (RecipeInputIngredient entry : input) {
            Object unspecific = entry.getUnspecific();
            if (unspecific instanceof Item) {
                if (this.recipeCacheItem.get(unspecific) == null) continue;
                recipes.addAll(this.recipeCacheItem.get(unspecific));
                continue;
            }
            if (!(unspecific instanceof Fluid) || !this.recipeCacheFluid.containsKey(((Fluid)unspecific).getName())) continue;
            recipes.addAll(this.recipeCacheFluid.get(((Fluid)unspecific).getName()));
        }
        if (!recipes.isEmpty()) {
            block1: for (DynamicRecipe recipe : recipes) {
                if (input.size() != recipe.getInputIngredients().size()) continue;
                itB = new ArrayList<RecipeInputIngredient>(recipe.getInputIngredients()).listIterator();
                block2: for (RecipeInputIngredient entry : input) {
                    while (itB.hasNext()) {
                        temp = itB.next();
                        if (!temp.matches(entry.ingredient) || entry.getCount() < temp.getCount()) continue;
                        itB.remove();
                        while (itB.hasPrevious()) {
                            itB.previous();
                        }
                        continue block2;
                    }
                    continue block1;
                }
                return recipe;
            }
        }
        block5: for (DynamicRecipe recipe : this.uncacheableRecipes) {
            if (input.size() != recipe.getInputIngredients().size()) continue;
            itB = new ArrayList<RecipeInputIngredient>(recipe.getInputIngredients()).listIterator();
            block6: for (RecipeInputIngredient entry : input) {
                while (itB.hasNext()) {
                    temp = itB.next();
                    if (!temp.matches(entry.ingredient) || entry.getCount() < temp.getCount()) continue;
                    itB.remove();
                    while (itB.hasPrevious()) {
                        itB.previous();
                    }
                    continue block6;
                }
                continue block5;
            }
            return recipe;
        }
        return null;
    }

    public DynamicRecipe findRecipe(ItemStack[] items, FluidStack[] fluids) {
        RecipeInputIngredient temp;
        ArrayList<RecipeInputIngredient> inputs = new ArrayList<RecipeInputIngredient>();
        for (ItemStack itemStack : items) {
            if (StackUtil.isEmpty(itemStack)) {
                return null;
            }
            inputs.add(RecipeInputItemStack.of(itemStack));
        }
        for (ItemStack itemStack : fluids) {
            if (itemStack.amount <= 0) {
                return null;
            }
            inputs.add(RecipeInputFluidStack.of((FluidStack)itemStack));
        }
        if (inputs.isEmpty()) {
            return null;
        }
        ArrayList recipes = new ArrayList();
        for (RecipeInputIngredient entry : inputs) {
            Object object = entry.getUnspecific();
            if (object instanceof Item) {
                if (this.recipeCacheItem.get(object) == null) continue;
                recipes.addAll(this.recipeCacheItem.get(object));
                continue;
            }
            if (!(object instanceof Fluid) || !this.recipeCacheFluid.containsKey(((Fluid)object).getName())) continue;
            recipes.addAll(this.recipeCacheFluid.get(((Fluid)object).getName()));
        }
        if (!recipes.isEmpty()) {
            block3: for (DynamicRecipe recipe : recipes) {
                if (inputs.size() != recipe.getInputIngredients().size()) continue;
                ListIterator<RecipeInputIngredient> listIterator = new ArrayList<RecipeInputIngredient>(recipe.getInputIngredients()).listIterator();
                block4: for (RecipeInputIngredient entry : inputs) {
                    while (listIterator.hasNext()) {
                        temp = listIterator.next();
                        if (!temp.matches(entry.ingredient) || entry.getCount() < temp.getCount()) continue;
                        listIterator.remove();
                        while (listIterator.hasPrevious()) {
                            listIterator.previous();
                        }
                        continue block4;
                    }
                    continue block3;
                }
                return recipe;
            }
        }
        block7: for (DynamicRecipe recipe : this.uncacheableRecipes) {
            if (inputs.size() != recipe.getInputIngredients().size()) continue;
            ListIterator<RecipeInputIngredient> listIterator = new ArrayList<RecipeInputIngredient>(recipe.getInputIngredients()).listIterator();
            block8: for (RecipeInputIngredient entry : inputs) {
                while (listIterator.hasNext()) {
                    temp = listIterator.next();
                    if (!temp.matches(entry.ingredient) || entry.getCount() < temp.getCount()) continue;
                    listIterator.remove();
                    while (listIterator.hasPrevious()) {
                        listIterator.previous();
                    }
                    continue block8;
                }
                continue block7;
            }
            return recipe;
        }
        return null;
    }

    public boolean isPartOfRecipe(ItemStack stack) {
        RecipeInputIngredient temp;
        ListIterator<RecipeInputIngredient> itB;
        if (StackUtil.isEmpty(stack)) {
            return false;
        }
        RecipeInputItemStack subject = RecipeInputItemStack.of(stack);
        ArrayList recipes = new ArrayList();
        Object unspecific = subject.getUnspecific();
        if (unspecific instanceof Item) {
            if (this.recipeCacheItem.get(unspecific) != null) {
                recipes.addAll(this.recipeCacheItem.get(unspecific));
            }
        } else if (unspecific instanceof Fluid && this.recipeCacheFluid.containsKey(((Fluid)unspecific).getName())) {
            recipes.addAll(this.recipeCacheFluid.get(((Fluid)unspecific).getName()));
        }
        if (!recipes.isEmpty()) {
            for (DynamicRecipe recipe : recipes) {
                itB = new ArrayList<RecipeInputIngredient>(recipe.getInputIngredients()).listIterator();
                while (itB.hasNext()) {
                    temp = itB.next();
                    if (!temp.matches(subject.ingredient)) continue;
                    return true;
                }
            }
        }
        if (!this.uncacheableRecipes.isEmpty()) {
            for (DynamicRecipe recipe : this.uncacheableRecipes) {
                itB = new ArrayList<RecipeInputIngredient>(recipe.getInputIngredients()).listIterator();
                temp = itB.next();
                if (!temp.matches(subject.ingredient)) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public DynamicRecipe apply(ItemStack[] items, FluidStack[] fluids, boolean simulate) {
        RecipeInputIngredient temp;
        ArrayList<RecipeInputIngredient> inputs = new ArrayList<RecipeInputIngredient>();
        for (ItemStack itemStack : items) {
            if (StackUtil.isEmpty(itemStack)) {
                return null;
            }
            inputs.add(RecipeInputItemStack.of(itemStack));
        }
        for (ItemStack itemStack : fluids) {
            if (itemStack.amount <= 0) {
                return null;
            }
            inputs.add(RecipeInputFluidStack.of((FluidStack)itemStack));
        }
        DynamicRecipe recipe = this.getRecipe(inputs);
        if (recipe == null) {
            return null;
        }
        if (items.length + fluids.length != recipe.getInputIngredients().size()) {
            return null;
        }
        ListIterator<RecipeInputIngredient> itB = new ArrayList<RecipeInputIngredient>(recipe.getInputIngredients()).listIterator();
        block2: for (RecipeInputIngredient recipeInputIngredient : inputs) {
            while (itB.hasNext()) {
                temp = itB.next();
                if (!temp.matches(recipeInputIngredient.ingredient) || recipeInputIngredient.getCount() < temp.getCount()) continue;
                itB.remove();
                while (itB.hasPrevious()) {
                    itB.previous();
                }
                continue block2;
            }
            return null;
        }
        if (!simulate) {
            itB = new ArrayList<RecipeInputIngredient>(recipe.getInputIngredients()).listIterator();
            block5: for (RecipeInputIngredient recipeInputIngredient : inputs) {
                while (itB.hasNext()) {
                    temp = itB.next();
                    if (!temp.matches(recipeInputIngredient.ingredient)) continue;
                    recipeInputIngredient.shrink(temp.getCount());
                    itB.remove();
                    while (itB.hasPrevious()) {
                        itB.previous();
                    }
                    continue block5;
                }
                return null;
            }
        }
        return recipe;
    }

    public boolean apply(DynamicRecipe recipe, ItemStack[] items, FluidStack[] fluids, boolean simulate) {
        if (recipe == null) {
            return false;
        }
        ArrayList<RecipeInputIngredient> inputs = new ArrayList<RecipeInputIngredient>();
        for (ItemStack itemStack : items) {
            if (StackUtil.isEmpty(itemStack)) {
                return false;
            }
            inputs.add(RecipeInputItemStack.of(itemStack));
        }
        for (ItemStack itemStack : fluids) {
            if (itemStack.amount <= 0) {
                return false;
            }
            inputs.add(RecipeInputFluidStack.of((FluidStack)itemStack));
        }
        if (items.length + fluids.length != recipe.getInputIngredients().size()) {
            return false;
        }
        ListIterator<RecipeInputIngredient> itB = new ArrayList<RecipeInputIngredient>(recipe.getInputIngredients()).listIterator();
        block2: for (RecipeInputIngredient entry : inputs) {
            while (itB.hasNext()) {
                RecipeInputIngredient recipeInputIngredient = itB.next();
                if (!recipeInputIngredient.matches(entry.ingredient) || entry.getCount() < recipeInputIngredient.getCount()) continue;
                itB.remove();
                while (itB.hasPrevious()) {
                    itB.previous();
                }
                continue block2;
            }
            return false;
        }
        if (!simulate) {
            itB = new ArrayList<RecipeInputIngredient>(recipe.getInputIngredients()).listIterator();
            block5: for (RecipeInputIngredient entry : inputs) {
                while (itB.hasNext()) {
                    RecipeInputIngredient recipeInputIngredient = itB.next();
                    if (!recipeInputIngredient.matches(entry.ingredient)) continue;
                    entry.shrink(recipeInputIngredient.getCount());
                    itB.remove();
                    while (itB.hasPrevious()) {
                        itB.previous();
                    }
                    continue block5;
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterable<? extends DynamicRecipe> getRecipes() {
        return () -> new Iterator<DynamicRecipe>(){
            private final Iterator recipeIt;
            private Collection lastInput;
            {
                this.recipeIt = DynamicRecipeManager.this.recipes.values().iterator();
            }

            @Override
            public boolean hasNext() {
                return this.recipeIt.hasNext();
            }

            @Override
            public DynamicRecipe next() {
                DynamicRecipe next = (DynamicRecipe)this.recipeIt.next();
                this.lastInput = next.getInputIngredients();
                return next;
            }

            @Override
            public void remove() {
                this.recipeIt.remove();
                DynamicRecipeManager.this.removeCachedRecipes(this.lastInput);
            }
        };
    }

    @Override
    public boolean isIterable() {
        return true;
    }

    protected void addToCache(DynamicRecipe recipe) {
        if (recipe.getInputIngredients().stream().anyMatch(entry -> entry instanceof RecipeInputOreDictionary)) {
            if (!oreRegisterEventSubscribed) {
                MinecraftForge.EVENT_BUS.register(DynamicRecipeManager.class);
                oreRegisterEventSubscribed = true;
            }
            watchingManagers.add(this);
        }
        Collection<Item> items = this.getItemsFromRecipe(recipe.getInputIngredients());
        Collection<Fluid> fluids = this.getFluidsFromRecipe(recipe.getInputIngredients());
        if (items != null) {
            for (Item item : items) {
                this.addToCache(item, recipe);
            }
        }
        if (fluids != null) {
            for (Fluid fluid : fluids) {
                this.addToCache(fluid, recipe);
            }
        }
        if (items == null && fluids == null) {
            this.uncacheableRecipes.add(recipe);
        }
    }

    private void addToCache(Item item, DynamicRecipe recipe) {
        List recipes = this.recipeCacheItem.computeIfAbsent(item, newValue -> new ArrayList());
        if (!recipes.contains(recipe)) {
            recipes.add(recipe);
        }
    }

    private void addToCache(Fluid fluid, DynamicRecipe recipe) {
        List recipes = this.recipeCacheFluid.computeIfAbsent(fluid.getName(), newValue -> new ArrayList());
        if (!recipes.contains(recipe)) {
            recipes.add(recipe);
        }
    }

    protected void removeCachedRecipes(Collection<RecipeInputIngredient> input) {
        List<DynamicRecipe> recipes;
        Collection<Item> items = this.getItemsFromRecipe(input);
        Collection<Fluid> fluids = this.getFluidsFromRecipe(input);
        if (items != null) {
            for (Item item : items) {
                recipes = this.recipeCacheItem.get(item);
                if (recipes == null) {
                    IC2.log.warn(LogCategory.Recipe, "Inconsistent recipe cache, the entry for the item " + item + " is missing.");
                    continue;
                }
                this.removeInputFromRecipes(recipes.iterator(), input);
                if (!recipes.isEmpty()) continue;
                this.recipeCacheItem.remove(item);
            }
        }
        if (fluids != null) {
            for (Fluid fluid : fluids) {
                recipes = this.recipeCacheFluid.get(fluid.getName());
                if (recipes == null) {
                    IC2.log.warn(LogCategory.Recipe, "Inconsistent recipe cache, the entry for the fluid " + fluid + " is missing.");
                    continue;
                }
                this.removeInputFromRecipes(recipes.iterator(), input);
                if (!recipes.isEmpty()) continue;
                this.recipeCacheFluid.remove(fluid.getName());
            }
        }
        if (items == null && fluids == null) {
            this.removeInputFromRecipes(this.uncacheableRecipes.iterator(), input);
        }
    }

    private void removeInputFromRecipes(Iterator<DynamicRecipe> it, Collection<RecipeInputIngredient> target) {
        assert (target != null);
        while (it.hasNext()) {
            if (!target.equals(it.next().getInputIngredients())) continue;
            it.remove();
        }
    }

    private Collection<Item> getItemsFromRecipe(Collection<RecipeInputIngredient> recipe) {
        ArrayList<Object> inputs = new ArrayList<Object>();
        for (RecipeInputIngredient entry : recipe) {
            if (!(entry instanceof RecipeInputItemStack)) continue;
            inputs.add(((RecipeInputItemStack)entry).ingredient);
        }
        if (inputs.isEmpty()) {
            return null;
        }
        Set<Item> ret = Collections.newSetFromMap(new IdentityHashMap(inputs.size()));
        for (ItemStack itemStack : inputs) {
            ret.add(itemStack.func_77973_b());
        }
        return ret;
    }

    private Collection<Fluid> getFluidsFromRecipe(Collection<RecipeInputIngredient> recipe) {
        ArrayList<Object> inputs = new ArrayList<Object>();
        for (RecipeInputIngredient entry : recipe) {
            if (!(entry instanceof RecipeInputFluidStack)) continue;
            inputs.add(((RecipeInputFluidStack)entry).ingredient);
        }
        if (inputs.isEmpty()) {
            return null;
        }
        Set<Fluid> ret = Collections.newSetFromMap(new IdentityHashMap(inputs.size()));
        for (FluidStack fluidStack : inputs) {
            ret.add(fluidStack.getFluid());
        }
        return ret;
    }

    @Override
    public boolean removeRecipe(Collection<RecipeInputIngredient> input, Collection<RecipeOutputIngredient> output) {
        DynamicRecipe recipe = this.getRecipe(input);
        if (recipe == null) {
            return false;
        }
        if (DynamicRecipeManager.checkListEqualityIngredient(output, recipe.getOutputIngredients(), true)) {
            this.recipes.remove(recipe.getInputIngredients());
            this.removeCachedRecipes(recipe.getInputIngredients());
        }
        return false;
    }

    private static boolean checkListEqualityIngredient(Collection<? extends RecipeIngredient> first, Collection<? extends RecipeIngredient> second, boolean strict) {
        if (first.size() != second.size()) {
            return false;
        }
        ListIterator<? extends RecipeIngredient> itB = new ArrayList<RecipeIngredient>(second).listIterator();
        block0: for (RecipeIngredient recipeIngredient : first) {
            while (itB.hasNext()) {
                if (!(strict ? recipeIngredient.matchesStrict(itB.next()) : recipeIngredient.matches(itB.next()))) continue;
                itB.remove();
                while (itB.hasPrevious()) {
                    itB.previous();
                }
                continue block0;
            }
            return false;
        }
        return true;
    }

    protected void displayError(String message) {
        if (!MainConfig.ignoreInvalidRecipes) {
            throw new RuntimeException(message);
        }
        IC2.log.warn(LogCategory.Recipe, message);
    }

    @SubscribeEvent
    public static void onOreRegister(OreDictionary.OreRegisterEvent event) {
        Item item = event.getOre().func_77973_b();
        if (item == null) {
            IC2.log.warn(LogCategory.Recipe, "Found null item ore dict registration.", new Throwable());
            return;
        }
        for (DynamicRecipeManager manager : watchingManagers) {
            manager.onOreRegister(item, event.getName());
        }
    }

    private void onOreRegister(Item item, String name) {
        for (DynamicRecipe rawRecipe : this.recipes.values()) {
            for (RecipeInputIngredient entry : rawRecipe.getInputIngredients()) {
                RecipeInputOreDictionary input;
                if (!(entry instanceof RecipeInputOreDictionary) || !(input = (RecipeInputOreDictionary)entry).matchesStrict(name)) continue;
                this.addToCache(item, rawRecipe);
            }
        }
    }

    static {
        watchingManagers = Collections.newSetFromMap(new IdentityHashMap());
    }
}


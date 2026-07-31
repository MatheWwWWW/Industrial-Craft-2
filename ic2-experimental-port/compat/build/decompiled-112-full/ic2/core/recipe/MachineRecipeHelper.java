/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.oredict.OreDictionary$OreRegisterEvent
 */
package ic2.core.recipe;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.core.IC2;
import ic2.core.recipe.RecipeInputItemStack;
import ic2.core.recipe.RecipeInputOreDict;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public abstract class MachineRecipeHelper<RI, RO>
implements IMachineRecipeManager<RI, RO, ItemStack> {
    protected final Map<RI, MachineRecipe<RI, RO>> recipes = new HashMap<RI, MachineRecipe<RI, RO>>();
    private final Map<Item, List<MachineRecipe<RI, RO>>> recipeCache = new IdentityHashMap<Item, List<MachineRecipe<RI, RO>>>();
    private final List<MachineRecipe<RI, RO>> uncacheableRecipes = new ArrayList<MachineRecipe<RI, RO>>();
    private static boolean oreRegisterEventSubscribed;
    private static final Set<MachineRecipeHelper<?, ?>> watchingManagers;

    protected abstract IRecipeInput getForInput(RI var1);

    protected IRecipeInput getForRecipe(MachineRecipe<RI, RO> recipe) {
        return this.getForInput(recipe.getInput());
    }

    protected boolean consumeContainer(ItemStack input, ItemStack container, MachineRecipe<RI, RO> recipe) {
        return false;
    }

    @Override
    public MachineRecipeResult<RI, RO, ItemStack> apply(ItemStack input, boolean acceptTest) {
        ItemStack adjustedInput;
        if (StackUtil.isEmpty(input)) {
            return null;
        }
        MachineRecipe<RI, RO> recipe = this.getRecipe(input);
        if (recipe == null) {
            return null;
        }
        IRecipeInput recipeInput = this.getForRecipe(recipe);
        if (StackUtil.getSize(input) < recipeInput.getAmount()) {
            return null;
        }
        if (input.func_77973_b().hasContainerItem(input) && !StackUtil.isEmpty(adjustedInput = input.func_77973_b().getContainerItem(input)) && !acceptTest && !this.consumeContainer(input, adjustedInput, recipe)) {
            if (!acceptTest && StackUtil.getSize(input) != recipeInput.getAmount()) {
                return null;
            }
            adjustedInput = StackUtil.copy(adjustedInput);
        } else {
            adjustedInput = StackUtil.copyWithSize(input, StackUtil.getSize(input) - recipeInput.getAmount());
        }
        return recipe.getResult(adjustedInput);
    }

    @Override
    public Iterable<? extends MachineRecipe<RI, RO>> getRecipes() {
        return new Iterable<MachineRecipe<RI, RO>>(){

            @Override
            public Iterator<MachineRecipe<RI, RO>> iterator() {
                return new Iterator<MachineRecipe<RI, RO>>(){
                    private final Iterator<MachineRecipe<RI, RO>> recipeIt;
                    private RI lastInput;
                    {
                        this.recipeIt = MachineRecipeHelper.this.recipes.values().iterator();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.recipeIt.hasNext();
                    }

                    @Override
                    public MachineRecipe<RI, RO> next() {
                        MachineRecipe next = this.recipeIt.next();
                        this.lastInput = next.getInput();
                        return next;
                    }

                    @Override
                    public void remove() {
                        this.recipeIt.remove();
                        MachineRecipeHelper.this.removeCachedRecipes(this.lastInput);
                    }
                };
            }
        };
    }

    @Override
    public boolean isIterable() {
        return true;
    }

    protected MachineRecipe<RI, RO> getRecipe(ItemStack input) {
        if (StackUtil.isEmpty(input)) {
            return null;
        }
        List<MachineRecipe<RI, RO>> recipes = this.recipeCache.get(input.func_77973_b());
        if (recipes != null) {
            for (MachineRecipe<RI, RO> recipe : recipes) {
                if (!this.getForRecipe(recipe).matches(input)) continue;
                return recipe;
            }
        }
        for (MachineRecipe<RI, RO> recipe : this.uncacheableRecipes) {
            if (!this.getForRecipe(recipe).matches(input)) continue;
            return recipe;
        }
        return null;
    }

    protected void addToCache(MachineRecipe<RI, RO> recipe) {
        Collection<Item> items = this.getItemsFromRecipe(recipe.getInput());
        if (items != null) {
            for (Item item : items) {
                this.addToCache(item, recipe);
            }
            if (recipe.getInput().getClass() == RecipeInputOreDict.class) {
                if (!oreRegisterEventSubscribed) {
                    MinecraftForge.EVENT_BUS.register(MachineRecipeHelper.class);
                    oreRegisterEventSubscribed = true;
                }
                watchingManagers.add(this);
            }
        } else {
            this.uncacheableRecipes.add(recipe);
        }
    }

    private void addToCache(Item item, MachineRecipe<RI, RO> recipe) {
        List<MachineRecipe<RI, RO>> recipes = this.recipeCache.get(item);
        if (recipes == null) {
            recipes = new ArrayList<MachineRecipe<RI, RO>>();
            this.recipeCache.put(item, recipes);
        }
        if (!recipes.contains(recipe)) {
            recipes.add(recipe);
        }
    }

    protected void removeCachedRecipes(RI input) {
        Collection<Item> items = this.getItemsFromRecipe(input);
        if (items != null) {
            for (Item item : items) {
                List<MachineRecipe<RI, RO>> recipes = this.recipeCache.get(item);
                if (recipes == null) {
                    IC2.log.warn(LogCategory.Recipe, "Inconsistent recipe cache, the entry for the item " + item + " is missing.");
                    continue;
                }
                this.removeInputFromRecipes(recipes.iterator(), input);
                if (!recipes.isEmpty()) continue;
                this.recipeCache.remove(item);
            }
        } else {
            this.removeInputFromRecipes(this.uncacheableRecipes.iterator(), input);
        }
    }

    private void removeInputFromRecipes(Iterator<MachineRecipe<RI, RO>> it, RI target) {
        assert (target != null);
        while (it.hasNext()) {
            if (!target.equals(it.next().getInput())) continue;
            it.remove();
        }
    }

    private Collection<Item> getItemsFromRecipe(RI input) {
        return this.getItemsFromRecipe(this.getForInput(input));
    }

    private Collection<Item> getItemsFromRecipe(IRecipeInput recipe) {
        Class<?> recipeClass = recipe.getClass();
        if (recipeClass == RecipeInputItemStack.class || recipeClass == RecipeInputOreDict.class) {
            List<ItemStack> inputs = recipe.getInputs();
            Set<Item> ret = Collections.newSetFromMap(new IdentityHashMap(inputs.size()));
            for (ItemStack stack : inputs) {
                ret.add(stack.func_77973_b());
            }
            return ret;
        }
        return null;
    }

    private void onOreRegister(Item item, String name) {
        for (MachineRecipe<RI, RO> rawRecipe : this.recipes.values()) {
            if (rawRecipe.getInput().getClass() != RecipeInputOreDict.class) continue;
            RecipeInputOreDict recipe = (RecipeInputOreDict)rawRecipe.getInput();
            if (!recipe.input.equals(name)) continue;
            this.addToCache(item, rawRecipe);
        }
    }

    @SubscribeEvent
    public static void onOreRegister(OreDictionary.OreRegisterEvent event) {
        Item item = event.getOre().func_77973_b();
        if (item == null) {
            IC2.log.warn(LogCategory.Recipe, "Found null item ore dict registration.", new Throwable());
            return;
        }
        for (MachineRecipeHelper<?, ?> manager : watchingManagers) {
            super.onOreRegister(item, event.getName());
        }
    }

    static {
        watchingManagers = Collections.newSetFromMap(new IdentityHashMap());
    }
}


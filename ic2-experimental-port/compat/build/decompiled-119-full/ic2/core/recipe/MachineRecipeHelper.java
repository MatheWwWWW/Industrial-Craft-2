/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.recipe;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.core.IC2;
import ic2.core.recipe.input.RecipeInputIngredient;
import ic2.core.recipe.input.RecipeInputItemStack;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class MachineRecipeHelper<RI, RO>
implements IMachineRecipeManager<RI, RO, ItemStack> {
    protected final Map<RI, MachineRecipe<RI, RO>> recipes = new HashMap<RI, MachineRecipe<RI, RO>>();
    private final Map<Item, List<MachineRecipe<RI, RO>>> recipeCache = new IdentityHashMap<Item, List<MachineRecipe<RI, RO>>>();
    private final List<MachineRecipe<RI, RO>> uncacheableRecipes = new ArrayList<MachineRecipe<RI, RO>>();

    protected abstract IRecipeInput getForInput(RI var1);

    protected IRecipeInput getForRecipe(MachineRecipe<RI, RO> machineRecipe) {
        return this.getForInput(machineRecipe.getInput());
    }

    protected boolean consumeContainer(ItemStack itemStack, ItemStack itemStack2, MachineRecipe<RI, RO> machineRecipe) {
        return false;
    }

    @Override
    public MachineRecipeResult<RI, RO, ItemStack> apply(ItemStack itemStack, boolean bl) {
        ItemStack itemStack2;
        if (StackUtil.isEmpty(itemStack)) {
            return null;
        }
        MachineRecipe<RI, RO> machineRecipe = this.getRecipe(itemStack);
        if (machineRecipe == null) {
            return null;
        }
        IRecipeInput iRecipeInput = this.getForRecipe(machineRecipe);
        if (StackUtil.getSize(itemStack) < iRecipeInput.getAmount()) {
            return null;
        }
        if (IC2.envProxy.hasRecipeRemainder(itemStack) && !StackUtil.isEmpty(itemStack2 = IC2.envProxy.getRecipeRemainder(itemStack)) && !bl && !this.consumeContainer(itemStack, itemStack2, machineRecipe)) {
            if (!bl && StackUtil.getSize(itemStack) != iRecipeInput.getAmount()) {
                return null;
            }
            itemStack2 = StackUtil.copy(itemStack2);
        } else {
            itemStack2 = StackUtil.copyWithSize(itemStack, StackUtil.getSize(itemStack) - iRecipeInput.getAmount());
        }
        return machineRecipe.getResult(itemStack2);
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
                        MachineRecipe machineRecipe = this.recipeIt.next();
                        this.lastInput = machineRecipe.getInput();
                        return machineRecipe;
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

    protected MachineRecipe<RI, RO> getRecipe(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return null;
        }
        List<MachineRecipe<RI, RO>> list = this.recipeCache.get(itemStack.m_41720_());
        if (list != null) {
            for (MachineRecipe<RI, RO> machineRecipe : list) {
                if (!this.getForRecipe(machineRecipe).matches(itemStack)) continue;
                return machineRecipe;
            }
        }
        for (MachineRecipe<RI, RO> machineRecipe : this.uncacheableRecipes) {
            if (!this.getForRecipe(machineRecipe).matches(itemStack)) continue;
            return machineRecipe;
        }
        return null;
    }

    protected void addToCache(MachineRecipe<RI, RO> machineRecipe) {
        Collection<Item> collection = this.getItemsFromRecipe(machineRecipe.getInput());
        if (collection != null) {
            for (Item item : collection) {
                this.addToCache(item, machineRecipe);
            }
        } else {
            this.uncacheableRecipes.add(machineRecipe);
        }
    }

    private void addToCache(Item item, MachineRecipe<RI, RO> machineRecipe) {
        List<MachineRecipe<RI, RO>> list = this.recipeCache.get(item);
        if (list == null) {
            list = new ArrayList<MachineRecipe<RI, RO>>();
            this.recipeCache.put(item, list);
        }
        if (!list.contains(machineRecipe)) {
            list.add(machineRecipe);
        }
    }

    protected void removeCachedRecipes(RI RI) {
        Collection<Item> collection = this.getItemsFromRecipe(RI);
        if (collection != null) {
            for (Item item : collection) {
                List<MachineRecipe<RI, RO>> list = this.recipeCache.get(item);
                if (list == null) {
                    IC2.log.warn(LogCategory.Recipe, "Inconsistent recipe cache, the entry for the item " + item + " is missing.");
                    continue;
                }
                this.removeInputFromRecipes(list.iterator(), RI);
                if (!list.isEmpty()) continue;
                this.recipeCache.remove(item);
            }
        } else {
            this.removeInputFromRecipes(this.uncacheableRecipes.iterator(), RI);
        }
    }

    private void removeInputFromRecipes(Iterator<MachineRecipe<RI, RO>> iterator, RI RI) {
        assert (RI != null);
        while (iterator.hasNext()) {
            if (!RI.equals(iterator.next().getInput())) continue;
            iterator.remove();
        }
    }

    private Collection<Item> getItemsFromRecipe(RI RI) {
        return this.getItemsFromRecipe(this.getForInput(RI));
    }

    private Collection<Item> getItemsFromRecipe(IRecipeInput iRecipeInput) {
        Class<?> clazz = iRecipeInput.getClass();
        if (clazz == RecipeInputIngredient.class || clazz == RecipeInputItemStack.class) {
            List<ItemStack> list = iRecipeInput.getInputs();
            Set<Item> set = Collections.newSetFromMap(new IdentityHashMap(list.size()));
            for (ItemStack itemStack : list) {
                set.add(itemStack.m_41720_());
            }
            return set;
        }
        return null;
    }
}


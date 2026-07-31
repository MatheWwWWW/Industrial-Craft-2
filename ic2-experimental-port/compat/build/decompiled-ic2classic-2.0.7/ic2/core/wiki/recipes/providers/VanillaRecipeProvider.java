/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  net.minecraft.core.NonNullList
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeType
 */
package ic2.core.wiki.recipes.providers;

import ic2.core.IC2;
import ic2.core.platform.recipes.crafting.RecipeIC2Base;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.wiki.recipes.providers.IRecipeProvider;
import ic2.core.wiki.recipes.renderers.CraftingRecipeRenderer;
import ic2.core.wiki.recipes.renderers.IRecipeRenderer;
import ic2.core.wiki.recipes.renderers.SmeltingRecipeRenderer;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class VanillaRecipeProvider
implements IRecipeProvider {
    Object2ObjectMap<Item, List<Recipe<?>>> outputs = CollectionUtils.createLinkedMap();
    Object2ObjectMap<Item, List<Tuple<List<Ingredient>, Recipe<?>>>> inputs = CollectionUtils.createLinkedMap();
    RecipeType<?> type;

    public VanillaRecipeProvider(RecipeType<?> type) {
        this.type = type;
    }

    @Override
    public void reloadRecipes() {
        this.inputs.clear();
        this.outputs.clear();
        ObjectSortedSet items = CollectionUtils.createLinkedSet();
        for (Recipe recipe : IC2.PLATFORM.getRecipes().m_44051_()) {
            if (recipe.m_6671_() != this.type) continue;
            this.addToList(recipe.m_8043_().m_41720_(), recipe, this.outputs);
            items.clear();
            NonNullList input = recipe.m_7527_();
            Tuple tuple = new Tuple((Object)input, (Object)recipe);
            this.collectInputs((NonNullList<Ingredient>)input, (Set<Item>)items);
            for (Item item : items) {
                this.addToList(item, tuple, this.inputs);
            }
        }
    }

    protected void collectInputs(NonNullList<Ingredient> list, Set<Item> items) {
        for (Ingredient entry : list) {
            for (ItemStack stack : entry.m_43908_()) {
                items.add(stack.m_41720_());
            }
        }
    }

    private boolean isHidden() {
        return IC2.CONFIG.recipeHiding.get();
    }

    @Override
    public List<IRecipeRenderer> getFromInput(ItemStack stack, boolean ignoreHidden) {
        ObjectList result = CollectionUtils.createList();
        block0: for (Tuple entry : (List)this.inputs.getOrDefault((Object)stack.m_41720_(), Collections.emptyList())) {
            RecipeIC2Base recipe;
            Object object;
            if (ignoreHidden && this.isHidden() && (object = entry.m_14419_()) instanceof RecipeIC2Base && (recipe = (RecipeIC2Base)object).isHidden()) continue;
            for (Ingredient test : (List)entry.m_14418_()) {
                if (!test.test(stack)) continue;
                if (this.type == RecipeType.f_44107_) {
                    result.add((CraftingRecipeRenderer)new CraftingRecipeRenderer((Recipe)entry.m_14419_()));
                    continue block0;
                }
                result.add((SmeltingRecipeRenderer)new SmeltingRecipeRenderer((Recipe)entry.m_14419_()));
                continue block0;
            }
        }
        return result;
    }

    @Override
    public List<IRecipeRenderer> getFromOutput(ItemStack stack, boolean ignoreHidden) {
        ObjectList result = CollectionUtils.createList();
        for (Recipe entry : (List)this.outputs.getOrDefault((Object)stack.m_41720_(), Collections.emptyList())) {
            RecipeIC2Base recipe;
            if (ignoreHidden && this.isHidden() && entry instanceof RecipeIC2Base && (recipe = (RecipeIC2Base)entry).isHidden()) continue;
            if (this.type == RecipeType.f_44107_) {
                result.add((CraftingRecipeRenderer)new CraftingRecipeRenderer(entry));
                continue;
            }
            result.add((SmeltingRecipeRenderer)new SmeltingRecipeRenderer(entry));
        }
        return result;
    }
}


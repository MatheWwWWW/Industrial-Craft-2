/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.common.Tags$Items
 */
package ic2.core.wiki.recipes.providers;

import ic2.api.recipes.ingridients.inputs.ItemInput;
import ic2.api.recipes.ingridients.inputs.ItemTagInput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.block.machines.recipes.RecyclerRecipeList;
import ic2.core.block.machines.recipes.misc.ScrapOutput;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.wiki.helper.ProgressBar;
import ic2.core.wiki.recipes.providers.IRecipeProvider;
import ic2.core.wiki.recipes.renderers.IRecipeRenderer;
import ic2.core.wiki.recipes.renderers.MachineRecipeRenderer;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

public class MachineRecipeProvider
implements IRecipeProvider {
    Object2ObjectMap<ItemStack, Set<IMachineRecipeList.RecipeEntry>> recipes = CollectionUtils.createMap(ItemStackStrategy.INSTANCE);
    IMachineRecipeList recipeList;
    ItemStack machine;
    ProgressBar bar;

    public MachineRecipeProvider(IMachineRecipeList recipeList, ItemStack machine, ProgressBar bar) {
        this.recipeList = recipeList;
        this.machine = machine;
        this.bar = bar;
    }

    @Override
    public void reloadRecipes() {
        this.recipes.clear();
        if (this.recipeList instanceof RecyclerRecipeList) {
            ((Set)this.recipes.computeIfAbsent((Object)new ItemStack((ItemLike)IC2Items.SCRAP_METAL), T -> CollectionUtils.createLinkedSet())).add(new IMachineRecipeList.RecipeEntry(new ResourceLocation("ic2", "scrap_metal"), new ScrapOutput(new ItemStack((ItemLike)IC2Items.SCRAP_METAL), 8), new ItemTagInput((TagKey<Item>)Tags.Items.INGOTS_IRON)));
            ((Set)this.recipes.computeIfAbsent((Object)new ItemStack((ItemLike)IC2Items.SCRAP), T -> CollectionUtils.createLinkedSet())).add(new IMachineRecipeList.RecipeEntry(new ResourceLocation("ic2", "scrap"), new ScrapOutput(new ItemStack((ItemLike)IC2Items.SCRAP), 4), new ItemInput(Items.f_42594_)));
        }
        for (IMachineRecipeList.RecipeEntry entry : this.recipeList.getAllEntries()) {
            for (ItemStack stack : entry.getOutput().getAllOutputs()) {
                this.addToSet(StackUtil.copyWithSize(stack, 1), entry, this.recipes);
            }
        }
    }

    @Override
    public List<IRecipeRenderer> getFromInput(ItemStack stack, boolean ignoreHidden) {
        ObjectList recipes = CollectionUtils.createList();
        IMachineRecipeList.RecipeEntry entry = this.recipeList.getRecipe(stack, false);
        if (entry != null && !(entry.getOutput() instanceof ScrapOutput)) {
            recipes.add((MachineRecipeRenderer)new MachineRecipeRenderer(entry, this.machine, this.bar));
        }
        return recipes;
    }

    @Override
    public List<IRecipeRenderer> getFromOutput(ItemStack stack, boolean ignoreHidden) {
        ObjectList entries = CollectionUtils.createList();
        for (IMachineRecipeList.RecipeEntry entry : (Set)this.recipes.getOrDefault((Object)stack, Collections.emptySet())) {
            entries.add((MachineRecipeRenderer)new MachineRecipeRenderer(entry, this.machine, this.bar));
        }
        return entries;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.recipes.FinishedRecipe
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 */
package ic2.data.recipe;

import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2RecipeSerializers;
import ic2.data.recipe.helper.BasicMachineRecipeGenerator;
import ic2.data.recipe.helper.IC2RecipeProvider;
import java.util.function.Consumer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class OreWasherRecipeProvider
extends IC2RecipeProvider {
    public OreWasherRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generate(Consumer<FinishedRecipe> consumer) {
        BasicMachineRecipeGenerator basicMachineRecipeGenerator = new BasicMachineRecipeGenerator(consumer, Ic2RecipeSerializers.ORE_WASHER, true);
        basicMachineRecipeGenerator.amount(1000).add((ItemLike)Items.f_41832_, 1, (ItemLike)Ic2Items.STONE_DUST);
        basicMachineRecipeGenerator.amount(1000).add((ItemLike)Ic2Items.CRUSHED_COPPER, 1, new ItemStack((ItemLike)Ic2Items.PURIFIED_COPPER), new ItemStack((ItemLike)Ic2Items.SMALL_COPPER_DUST, 2), new ItemStack((ItemLike)Ic2Items.STONE_DUST));
        basicMachineRecipeGenerator.amount(1000).add((ItemLike)Ic2Items.CRUSHED_GOLD, 1, new ItemStack((ItemLike)Ic2Items.PURIFIED_GOLD), new ItemStack((ItemLike)Ic2Items.SMALL_GOLD_DUST, 2), new ItemStack((ItemLike)Ic2Items.STONE_DUST));
        basicMachineRecipeGenerator.amount(1000).add((ItemLike)Ic2Items.CRUSHED_IRON, 1, new ItemStack((ItemLike)Ic2Items.PURIFIED_IRON), new ItemStack((ItemLike)Ic2Items.SMALL_IRON_DUST, 2), new ItemStack((ItemLike)Ic2Items.STONE_DUST));
        basicMachineRecipeGenerator.amount(1000).add((ItemLike)Ic2Items.CRUSHED_LEAD, 1, new ItemStack((ItemLike)Ic2Items.PURIFIED_LEAD), new ItemStack((ItemLike)Ic2Items.SMALL_SULFUR_DUST, 3), new ItemStack((ItemLike)Ic2Items.STONE_DUST));
        basicMachineRecipeGenerator.amount(1000).add((ItemLike)Ic2Items.CRUSHED_SILVER, 1, new ItemStack((ItemLike)Ic2Items.PURIFIED_SILVER), new ItemStack((ItemLike)Ic2Items.SMALL_SILVER_DUST, 2), new ItemStack((ItemLike)Ic2Items.STONE_DUST));
        basicMachineRecipeGenerator.amount(1000).add((ItemLike)Ic2Items.CRUSHED_TIN, 1, new ItemStack((ItemLike)Ic2Items.PURIFIED_TIN), new ItemStack((ItemLike)Ic2Items.SMALL_TIN_DUST, 2), new ItemStack((ItemLike)Ic2Items.STONE_DUST));
        basicMachineRecipeGenerator.amount(1000).add((ItemLike)Ic2Items.CRUSHED_URANIUM, 1, new ItemStack((ItemLike)Ic2Items.PURIFIED_URANIUM), new ItemStack((ItemLike)Ic2Items.SMALL_LEAD_DUST, 2), new ItemStack((ItemLike)Ic2Items.STONE_DUST));
    }
}


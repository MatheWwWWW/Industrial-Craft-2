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

import ic2.core.ref.Ic2ItemTags;
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

public class BlastFurnaceRecipeProvider
extends IC2RecipeProvider {
    public BlastFurnaceRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generate(Consumer<FinishedRecipe> consumer) {
        BasicMachineRecipeGenerator basicMachineRecipeGenerator = new BasicMachineRecipeGenerator(consumer, Ic2RecipeSerializers.BLAST_FURNACE, true);
        basicMachineRecipeGenerator.fluidDuration(1, 6000).add((ItemLike)Items.f_41834_, 1, new ItemStack((ItemLike)Ic2Items.STEEL_INGOT), new ItemStack((ItemLike)Ic2Items.SLAG));
        basicMachineRecipeGenerator.fluidDuration(1, 6000).add((ItemLike)Ic2Items.CRUSHED_IRON, 1, new ItemStack((ItemLike)Ic2Items.STEEL_INGOT), new ItemStack((ItemLike)Ic2Items.SLAG));
        basicMachineRecipeGenerator.fluidDuration(1, 6000).add((ItemLike)Ic2Items.PURIFIED_IRON, 1, new ItemStack((ItemLike)Ic2Items.STEEL_INGOT), new ItemStack((ItemLike)Ic2Items.SLAG));
        basicMachineRecipeGenerator.fluidDuration(1, 6000).add(Ic2ItemTags.IRON_DUSTS, 1, new ItemStack((ItemLike)Ic2Items.STEEL_INGOT), new ItemStack((ItemLike)Ic2Items.SLAG));
        basicMachineRecipeGenerator.fluidDuration(1, 6000).add((ItemLike)Items.f_42416_, 1, new ItemStack((ItemLike)Ic2Items.STEEL_INGOT), new ItemStack((ItemLike)Ic2Items.SLAG));
    }
}


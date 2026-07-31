/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.recipes.FinishedRecipe
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class ExtractorRecipeProvider
extends IC2RecipeProvider {
    public ExtractorRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generate(Consumer<FinishedRecipe> consumer) {
        BasicMachineRecipeGenerator basicMachineRecipeGenerator = new BasicMachineRecipeGenerator(consumer, Ic2RecipeSerializers.EXTRACTOR);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41995_, 1, (ItemLike)Items.f_42460_, 4);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41983_, 1, (ItemLike)Items.f_42461_, 4);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42095_, 1, (ItemLike)Items.f_42691_, 4);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41981_, 1, (ItemLike)Items.f_42452_, 4);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.AIR_CELL, 1, (ItemLike)Ic2Items.EMPTY_CELL);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.FILLED_TIN_CAN, 1, (ItemLike)Ic2Items.TIN_CAN);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42403_, 1, (ItemLike)Ic2Items.SULFUR_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.HYDRATED_TIN_DUST, 1, (ItemLike)Ic2Items.IODINE);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42048_, 1, (ItemLike)Ic2Items.SMALL_SULFUR_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.RESIN, 1, (ItemLike)Ic2Items.RUBBER, 3);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.RUBBER_SAPLING, 1, (ItemLike)Ic2Items.RUBBER);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.RUBBER_LOG, 1, (ItemLike)Ic2Items.RUBBER);
    }
}


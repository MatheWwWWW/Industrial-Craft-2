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

import ic2.core.ref.Ic2ItemTags;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2RecipeSerializers;
import ic2.data.recipe.helper.BasicMachineRecipeGenerator;
import ic2.data.recipe.helper.IC2RecipeProvider;
import java.util.function.Consumer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class MetalFormerRecipeProvider
extends IC2RecipeProvider {
    public MetalFormerRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generate(Consumer<FinishedRecipe> consumer) {
        MetalFormerRecipeProvider.generateCutting(consumer);
        MetalFormerRecipeProvider.generateExtruding(consumer);
        MetalFormerRecipeProvider.generateRolling(consumer);
    }

    private static void generateCutting(Consumer<FinishedRecipe> consumer) {
        BasicMachineRecipeGenerator basicMachineRecipeGenerator = new BasicMachineRecipeGenerator(consumer, Ic2RecipeSerializers.METAL_FORMER_CUTTING);
        basicMachineRecipeGenerator.add(Ic2ItemTags.COPPER_PLATES, 1, (ItemLike)Ic2Items.COPPER_CABLE, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.GOLD_PLATES, 1, (ItemLike)Ic2Items.GOLD_CABLE, 4);
        basicMachineRecipeGenerator.add(Ic2ItemTags.IRON_PLATES, 1, (ItemLike)Ic2Items.IRON_CABLE, 4);
        basicMachineRecipeGenerator.add(Ic2ItemTags.TIN_PLATES, 1, (ItemLike)Ic2Items.TIN_CABLE, 3);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.IRON_CASING, 1, (ItemLike)Ic2Items.COIN);
    }

    private static void generateExtruding(Consumer<FinishedRecipe> consumer) {
        BasicMachineRecipeGenerator basicMachineRecipeGenerator = new BasicMachineRecipeGenerator(consumer, Ic2RecipeSerializers.METAL_FORMER_EXTRUDING);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.IRON_CASING, 1, (ItemLike)Ic2Items.IRON_FENCE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.IRON_PLATES, 1, (ItemLike)Ic2Items.FUEL_ROD);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.TIN_CASING, 1, (ItemLike)Ic2Items.TIN_CAN);
        basicMachineRecipeGenerator.add(Ic2ItemTags.BRONZE_BLOCKS, 1, (ItemLike)Ic2Items.BRONZE_SHAFT);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41913_, 1, (ItemLike)Ic2Items.IRON_SHAFT);
        basicMachineRecipeGenerator.add(Ic2ItemTags.STEEL_BLOCKS, 1, (ItemLike)Ic2Items.STEEL_SHAFT);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_151052_, 1, (ItemLike)Ic2Items.COPPER_CABLE, 3);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42417_, 1, (ItemLike)Ic2Items.GOLD_CABLE, 4);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42416_, 1, (ItemLike)Ic2Items.IRON_CABLE, 4);
        basicMachineRecipeGenerator.add(Ic2ItemTags.TIN_INGOTS, 1, (ItemLike)Ic2Items.TIN_CABLE, 3);
    }

    private static void generateRolling(Consumer<FinishedRecipe> consumer) {
        BasicMachineRecipeGenerator basicMachineRecipeGenerator = new BasicMachineRecipeGenerator(consumer, Ic2RecipeSerializers.METAL_FORMER_ROLLING);
        basicMachineRecipeGenerator.add(Ic2ItemTags.BRONZE_INGOTS, 1, (ItemLike)Ic2Items.BRONZE_PLATE);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_151052_, 1, (ItemLike)Ic2Items.COPPER_PLATE);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42417_, 1, (ItemLike)Ic2Items.GOLD_PLATE);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42416_, 1, (ItemLike)Ic2Items.IRON_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.LEAD_INGOTS, 1, (ItemLike)Ic2Items.LEAD_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.STEEL_INGOTS, 1, (ItemLike)Ic2Items.STEEL_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.TIN_INGOTS, 1, (ItemLike)Ic2Items.TIN_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.BRONZE_PLATES, 1, (ItemLike)Ic2Items.BRONZE_CASING, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.COPPER_PLATES, 1, (ItemLike)Ic2Items.COPPER_CASING, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.GOLD_PLATES, 1, (ItemLike)Ic2Items.GOLD_CASING, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.IRON_PLATES, 1, (ItemLike)Ic2Items.IRON_CASING, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.LEAD_PLATES, 1, (ItemLike)Ic2Items.LEAD_CASING, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.STEEL_PLATES, 1, (ItemLike)Ic2Items.STEEL_CASING, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.TIN_PLATES, 1, (ItemLike)Ic2Items.TIN_CASING, 2);
    }
}


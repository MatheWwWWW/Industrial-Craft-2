/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.recipes.FinishedRecipe
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.data.recipe;

import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2ItemTags;
import ic2.core.ref.Ic2Items;
import ic2.data.recipe.helper.CannerBottleRecipeGenerator;
import ic2.data.recipe.helper.CannerEnrichRecipeGenerator;
import ic2.data.recipe.helper.IC2RecipeProvider;
import java.util.function.Consumer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class CannerRecipeProvider
extends IC2RecipeProvider {
    public CannerRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generate(Consumer<FinishedRecipe> consumer) {
        CannerRecipeProvider.generateBottle(consumer);
        CannerRecipeProvider.generateEnrich(consumer);
    }

    private static void generateBottle(Consumer<FinishedRecipe> consumer) {
        CannerBottleRecipeGenerator cannerBottleRecipeGenerator = new CannerBottleRecipeGenerator(consumer);
        cannerBottleRecipeGenerator.add(CannerRecipeProvider.itemInput(new ItemLike[]{Ic2Items.FUEL_ROD}), CannerRecipeProvider.itemInput(new ItemLike[]{Ic2Items.URANIUM}), new ItemStack((ItemLike)Ic2Items.URANIUM_FUEL_ROD), "uranium_fuel_rod");
        cannerBottleRecipeGenerator.add(CannerRecipeProvider.itemInput(new ItemLike[]{Ic2Items.FUEL_ROD}), CannerRecipeProvider.itemInput(new ItemLike[]{Ic2Items.MOX}), new ItemStack((ItemLike)Ic2Items.MOX_FUEL_ROD), "mox_fuel_rod");
    }

    private static void generateEnrich(Consumer<FinishedRecipe> consumer) {
        CannerEnrichRecipeGenerator cannerEnrichRecipeGenerator = new CannerEnrichRecipeGenerator(consumer);
        cannerEnrichRecipeGenerator.add(CannerRecipeProvider.bucket((Fluid)Fluids.f_76193_), CannerRecipeProvider.itemInput(new ItemLike[]{Ic2Items.CF_POWDER}), CannerRecipeProvider.bucket(Ic2Fluids.CONSTRUCTION_FOAM.still), "construction_foam");
        cannerEnrichRecipeGenerator.add(CannerRecipeProvider.bucket((Fluid)Fluids.f_76193_), CannerRecipeProvider.tagInput(Ic2ItemTags.LAPIS_DUSTS, 8), CannerRecipeProvider.bucket(Ic2Fluids.COOLANT.still), "coolant_from_water");
        cannerEnrichRecipeGenerator.add(CannerRecipeProvider.bucket(Ic2Fluids.DISTILLED_WATER.still), CannerRecipeProvider.tagInput(Ic2ItemTags.LAPIS_DUSTS), CannerRecipeProvider.bucket(Ic2Fluids.COOLANT.still), "coolant_from_distilled_water");
        cannerEnrichRecipeGenerator.add(CannerRecipeProvider.bucket((Fluid)Fluids.f_76193_), CannerRecipeProvider.itemInput(new ItemLike[]{Ic2Items.BIO_CHAFF}), CannerRecipeProvider.bucket(Ic2Fluids.BIOMASS.still), "biomass");
    }
}


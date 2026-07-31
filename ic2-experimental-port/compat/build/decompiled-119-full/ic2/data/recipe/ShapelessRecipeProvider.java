/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.recipes.FinishedRecipe
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.data.recipe;

import ic2.core.recipe.input.RecipeInputMultiple;
import ic2.core.ref.Ic2ItemTags;
import ic2.core.ref.Ic2Items;
import ic2.data.recipe.helper.AdvShapelessRecipeGenerator;
import ic2.data.recipe.helper.IC2RecipeProvider;
import ic2.data.recipe.helper.ShapelessRecipeGenerator;
import java.util.function.Consumer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class ShapelessRecipeProvider
extends IC2RecipeProvider {
    public ShapelessRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generate(Consumer<FinishedRecipe> consumer) {
        AdvShapelessRecipeGenerator advShapelessRecipeGenerator = new AdvShapelessRecipeGenerator(consumer);
        ShapelessRecipeGenerator shapelessRecipeGenerator = new ShapelessRecipeGenerator(consumer);
        RecipeInputMultiple recipeInputMultiple = new RecipeInputMultiple(1, ShapelessRecipeProvider.tagInput(Ic2ItemTags.TIN_DUSTS), ShapelessRecipeProvider.itemInput(new ItemLike[]{Ic2Items.CRUSHED_TIN}), ShapelessRecipeProvider.itemInput(new ItemLike[]{Ic2Items.PURIFIED_TIN}));
        RecipeInputMultiple recipeInputMultiple2 = new RecipeInputMultiple(1, ShapelessRecipeProvider.tagInput(Ic2ItemTags.COPPER_DUSTS), ShapelessRecipeProvider.itemInput(new ItemLike[]{Ic2Items.CRUSHED_COPPER}), ShapelessRecipeProvider.itemInput(new ItemLike[]{Ic2Items.PURIFIED_COPPER}));
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.BRONZE_DUST, 4).add(recipeInputMultiple).add(recipeInputMultiple2).add(recipeInputMultiple2).add(recipeInputMultiple2).finish("bronze_dust");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.BRONZE_CASING, 2).add(Ic2ItemTags.BRONZE_PLATES).add(Ic2ItemTags.FORGE_HAMMERS).finish("bronze_casing");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.COPPER_CASING, 2).add(Ic2ItemTags.COPPER_PLATES).add(Ic2ItemTags.FORGE_HAMMERS).finish("copper_casing");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.GOLD_CASING, 2).add(Ic2ItemTags.GOLD_PLATES).add(Ic2ItemTags.FORGE_HAMMERS).finish("gold_casing");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.IRON_CASING, 2).add(Ic2ItemTags.IRON_PLATES).add(Ic2ItemTags.FORGE_HAMMERS).finish("iron_casing");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.LEAD_CASING, 2).add(Ic2ItemTags.LEAD_PLATES).add(Ic2ItemTags.FORGE_HAMMERS).finish("lead_casing");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.TIN_CASING, 2).add(Ic2ItemTags.TIN_PLATES).add(Ic2ItemTags.FORGE_HAMMERS).finish("tin_casing");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.BRONZE_PLATE).add(Ic2ItemTags.BRONZE_INGOTS).add(Ic2ItemTags.FORGE_HAMMERS).finish("bronze_plate");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.COPPER_PLATE).add((ItemLike)Items.f_151052_).add(Ic2ItemTags.FORGE_HAMMERS).finish("copper_plate");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.GOLD_PLATE).add((ItemLike)Items.f_42417_).add(Ic2ItemTags.FORGE_HAMMERS).finish("gold_plate");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.IRON_PLATE).add((ItemLike)Items.f_42416_).add(Ic2ItemTags.FORGE_HAMMERS).finish("iron_plate");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.LEAD_PLATE).add(Ic2ItemTags.LEAD_INGOTS).add(Ic2ItemTags.FORGE_HAMMERS).finish("lead_plate");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.TIN_PLATE).add(Ic2ItemTags.TIN_INGOTS).add(Ic2ItemTags.FORGE_HAMMERS).finish("tin_plate");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.INSULATED_COPPER_CABLE).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.COPPER_CABLE).finish("insulated_copper_cable");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.COPPER_CABLE, 2).add(Ic2ItemTags.COPPER_PLATES).add(Ic2ItemTags.WIRE_CUTTERS).finish("copper_cable");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.TIN_CABLE, 3).add(Ic2ItemTags.TIN_PLATES).add(Ic2ItemTags.WIRE_CUTTERS).finish("tin_cable");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.INSULATED_TIN_CABLE).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.TIN_CABLE).finish("insulated_tin_cable");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.GOLD_CABLE, 4).add(Ic2ItemTags.GOLD_PLATES).add(Ic2ItemTags.WIRE_CUTTERS).finish("gold_cable");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.INSULATED_GOLD_CABLE).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.GOLD_CABLE).finish("insulated_gold_cable");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.DOUBLE_INSULATED_GOLD_CABLE).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.INSULATED_GOLD_CABLE).finish("double_insulated_gold_cable");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.DOUBLE_INSULATED_GOLD_CABLE).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.GOLD_CABLE).finish("double_insulated_gold_cable_2");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.INSULATED_IRON_CABLE).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.IRON_CABLE).finish("insulated_iron_cable");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.DOUBLE_INSULATED_IRON_CABLE).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.IRON_CABLE).finish("double_insulated_iron_cable");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.TRIPLE_INSULATED_IRON_CABLE).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.IRON_CABLE).finish("triple_insulated_iron_cable");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.DOUBLE_INSULATED_IRON_CABLE).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.INSULATED_IRON_CABLE).finish("double_insulated_iron_cable_2");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.TRIPLE_INSULATED_IRON_CABLE).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.INSULATED_IRON_CABLE).finish("triple_insulated_iron_cable_2");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.TRIPLE_INSULATED_IRON_CABLE).add((ItemLike)Ic2Items.RUBBER).add((ItemLike)Ic2Items.DOUBLE_INSULATED_IRON_CABLE).finish("triple_insulated_iron_cable_3");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.WATER_CELL).add((ItemLike)Ic2Items.EMPTY_CELL).add((ItemLike)Items.f_42447_).finish("water_cell");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.LAVA_CELL).add((ItemLike)Ic2Items.EMPTY_CELL).add((ItemLike)Items.f_42448_).finish("lava_cell");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.COFFEE_POWDER).add((ItemLike)Ic2Items.COFFEE_BEANS).finish("coffee_powder");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.COAL_FUEL_DUST).add(Ic2ItemTags.COAL_DUSTS).add((Fluid)Fluids.f_76193_, 1000).finish("coal_fuel_dust");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.HYDRATED_TIN_DUST).add(Ic2ItemTags.TIN_DUSTS).add((Fluid)Fluids.f_76193_, 1000).finish("hydrated_tin_dust");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.FERTILIZER, 2).add((ItemLike)Ic2Items.SCRAP).add((ItemLike)Items.f_42499_).finish("fertilizer");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.FERTILIZER, 2).add((ItemLike)Ic2Items.SCRAP).add((ItemLike)Ic2Items.SCRAP).add((ItemLike)Ic2Items.FERTILIZER).finish("fertilizer_2");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.COLD_COFFEE_MUG).add((ItemLike)Ic2Items.EMPTY_MUG).add((ItemLike)Ic2Items.COFFEE_POWDER).add((Fluid)Fluids.f_76193_, 1000).finish("cold_coffee_mug");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.COFFEE_MUG).add((ItemLike)Ic2Items.DARK_COFFEE_MUG).add((ItemLike)Items.f_42501_).add((ItemLike)Items.f_42455_).finish("coffee_mug");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.CARBON_MESH).add((ItemLike)Ic2Items.CARBON_FIBRE).add((ItemLike)Ic2Items.CARBON_FIBRE).finish("carbon_mesh");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.SMALL_PLUTONIUM, 9).add((ItemLike)Ic2Items.PLUTONIUM).finish("small_plutonium");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.BLACK_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42498_).finish("black_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.BLUE_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42494_).finish("blue_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.BROWN_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42495_).finish("brown_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.LIGHT_BLUE_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42538_).finish("light_blue_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.CYAN_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42492_).finish("cyan_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.GRAY_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42490_).finish("gray_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.GREEN_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42496_).finish("green_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.LIGHT_GRAY_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42491_).finish("light_gray_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.LIME_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42540_).finish("lime_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.MAGENTA_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42537_).finish("magenta_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.ORANGE_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42536_).finish("orange_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.PINK_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42489_).finish("pink_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.PURPLE_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42493_).finish("purple_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.RED_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42497_).finish("red_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.WHITE_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42535_).finish("white_painter");
        shapelessRecipeGenerator.start((ItemLike)Ic2Items.YELLOW_PAINTER).add((ItemLike)Ic2Items.PAINTER).add((ItemLike)Items.f_42539_).finish("yellow_painter");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.ELECTRIC_WRENCH).add((ItemLike)Ic2Items.WRENCH).add((ItemLike)Ic2Items.SMALL_POWER_UNIT).finish("electric_wrench");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.ELECTRIC_TREETAP).add((ItemLike)Ic2Items.TREETAP).add((ItemLike)Ic2Items.SMALL_POWER_UNIT).finish("electric_treetap");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.SMALL_URANIUM_235, 9).add((ItemLike)Ic2Items.URANIUM_235).finish("small_uranium_235");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.URANIUM_238, 9).add((ItemLike)Ic2Items.URANIUM_BLOCK).finish("uranium_238");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.REACTOR_PLATING).add(Ic2ItemTags.LEAD_PLATES).add((ItemLike)Ic2Items.ALLOY).finish("reactor_plating");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.CONTAINMENT_REACTOR_PLATING).add((ItemLike)Ic2Items.REACTOR_PLATING).add((ItemLike)Ic2Items.ALLOY).add((ItemLike)Ic2Items.ALLOY).finish("containment_reactor_plating");
        advShapelessRecipeGenerator.start((ItemLike)Items.f_41999_).add((Fluid)Fluids.f_76193_, 1000).add((Fluid)Fluids.f_76193_, 1000).add((Fluid)Fluids.f_76195_, 1000).add((Fluid)Fluids.f_76195_, 1000).finish("obsidian");
        advShapelessRecipeGenerator.start((ItemLike)Items.f_41862_).add((ItemLike)Items.f_41869_).add((ItemLike)Ic2Items.RESIN).hidden().finish("sticky_piston");
        advShapelessRecipeGenerator.start((ItemLike)Ic2Items.MANUAL_KINETIC_GENERATOR).add((ItemLike)Ic2Items.MACHINE).add((ItemLike)Items.f_41966_).finish("manual_kinetic_generator");
    }
}


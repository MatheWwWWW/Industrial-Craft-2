/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.recipes.FinishedRecipe
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class CompressorRecipeProvider
extends IC2RecipeProvider {
    public CompressorRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generate(Consumer<FinishedRecipe> consumer) {
        BasicMachineRecipeGenerator basicMachineRecipeGenerator = new BasicMachineRecipeGenerator(consumer, Ic2RecipeSerializers.COMPRESSOR);
        basicMachineRecipeGenerator.add((Fluid)Fluids.f_76193_, 1000, (ItemLike)Blocks.f_50127_);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_URANIUM_235, 9, (ItemLike)Ic2Items.URANIUM_235);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_PLUTONIUM, 9, (ItemLike)Ic2Items.PLUTONIUM);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.IRIDIUM_SHARD, 9, (ItemLike)Ic2Items.IRIDIUM_ORE);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41830_, 4, (ItemLike)Blocks.f_50062_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42593_, 5, (ItemLike)Items.f_42585_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42461_, 4, (ItemLike)Blocks.f_50129_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42460_, 4, (ItemLike)Blocks.f_50076_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42691_, 4, (ItemLike)Blocks.f_50197_);
        basicMachineRecipeGenerator.add((ItemLike)Blocks.f_50127_, 1, (ItemLike)Blocks.f_50126_);
        basicMachineRecipeGenerator.add((ItemLike)Blocks.f_50126_, 2, (ItemLike)Blocks.f_50354_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42452_, 4, (ItemLike)Blocks.f_50127_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42525_, 4, (ItemLike)Blocks.f_50141_);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.MIXED_METAL_INGOT, 1, (ItemLike)Ic2Items.ALLOY);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.CARBON_MESH, 1, (ItemLike)Ic2Items.CARBON_PLATE);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.COAL_BALL, 1, (ItemLike)Ic2Items.COAL_BLOCK);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.COAL_CHUNK, 1, (ItemLike)Ic2Items.INDUTRIAL_DIAMOND);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.EMPTY_CELL, 1, (ItemLike)Ic2Items.AIR_CELL);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_BRONZE_DUST, 9, (ItemLike)Ic2Items.BRONZE_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_COPPER_DUST, 9, (ItemLike)Ic2Items.COPPER_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_GOLD_DUST, 9, (ItemLike)Ic2Items.GOLD_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_IRON_DUST, 9, (ItemLike)Ic2Items.IRON_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_LAPIS_DUST, 9, (ItemLike)Ic2Items.LAPIS_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_LEAD_DUST, 9, (ItemLike)Ic2Items.LEAD_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_LITHIUM_DUST, 9, (ItemLike)Ic2Items.LITHIUM_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_OBSIDIAN_DUST, 9, (ItemLike)Ic2Items.OBSIDIAN_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_SILVER_DUST, 9, (ItemLike)Ic2Items.SILVER_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_SULFUR_DUST, 9, (ItemLike)Ic2Items.SULFUR_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.SMALL_TIN_DUST, 9, (ItemLike)Ic2Items.TIN_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.LAPIS_DUSTS, 1, (ItemLike)Ic2Items.LAPIS_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.OBSIDIAN_DUSTS, 1, (ItemLike)Ic2Items.OBSIDIAN_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.IRON_PLATES, 9, (ItemLike)Ic2Items.DENSE_IRON_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.GOLD_PLATES, 9, (ItemLike)Ic2Items.DENSE_GOLD_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.LEAD_PLATES, 9, (ItemLike)Ic2Items.DENSE_LEAD_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.BRONZE_PLATES, 9, (ItemLike)Ic2Items.DENSE_BRONZE_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.TIN_PLATES, 9, (ItemLike)Ic2Items.DENSE_TIN_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.COPPER_PLATES, 9, (ItemLike)Ic2Items.DENSE_COPPER_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.LAPIS_PLATES, 9, (ItemLike)Ic2Items.DENSE_LAPIS_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.OBSIDIAN_PLATES, 9, (ItemLike)Ic2Items.DENSE_OBSIDIAN_PLATE);
        basicMachineRecipeGenerator.add(Ic2ItemTags.STEEL_PLATES, 9, (ItemLike)Ic2Items.DENSE_STEEL_PLATE);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42416_, 9, (ItemLike)Blocks.f_50075_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42417_, 9, (ItemLike)Blocks.f_50074_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_151052_, 9, (ItemLike)Blocks.f_152504_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42534_, 9, (ItemLike)Blocks.f_50060_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42451_, 9, (ItemLike)Blocks.f_50330_);
        basicMachineRecipeGenerator.add(Ic2ItemTags.BRONZE_INGOTS, 9, (ItemLike)Ic2Items.BRONZE_BLOCK);
        basicMachineRecipeGenerator.add(Ic2ItemTags.STEEL_INGOTS, 9, (ItemLike)Ic2Items.STEEL_BLOCK);
        basicMachineRecipeGenerator.add(Ic2ItemTags.LEAD_INGOTS, 9, (ItemLike)Ic2Items.LEAD_BLOCK);
        basicMachineRecipeGenerator.add(Ic2ItemTags.TIN_INGOTS, 9, (ItemLike)Ic2Items.TIN_BLOCK);
        basicMachineRecipeGenerator.add(Ic2ItemTags.SILVER_INGOTS, 9, (ItemLike)Ic2Items.SILVER_BLOCK);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.ENERGIUM_DUST, 9, (ItemLike)Ic2Items.ENERGY_CRYSTAL);
    }
}


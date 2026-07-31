/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.recipes.FinishedRecipe
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
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
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class MaceratorRecipeProvider
extends IC2RecipeProvider {
    public MaceratorRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generate(Consumer<FinishedRecipe> consumer) {
        BasicMachineRecipeGenerator basicMachineRecipeGenerator = new BasicMachineRecipeGenerator(consumer, Ic2RecipeSerializers.MACERATOR);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42500_, 1, (ItemLike)Items.f_42535_, 4);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42585_, 1, (ItemLike)Items.f_42593_, 5);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41983_, 1, (ItemLike)Ic2Items.CLAY_DUST, 2);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42200_, 1, (ItemLike)Ic2Items.COAL_DUST, 9);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42594_, 1, (ItemLike)Items.f_41830_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42054_, 1, (ItemLike)Items.f_42525_, 4);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41832_, 1, (ItemLike)Items.f_42484_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41980_, 1, (ItemLike)Items.f_42452_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41854_, 1, (ItemLike)Items.f_42534_, 9);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42048_, 1, (ItemLike)Ic2Items.NETHERRACK_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42675_, 1, (ItemLike)Ic2Items.GRIN_POWDER);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42157_, 1, (ItemLike)Items.f_42692_, 4);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42160_, 1, (ItemLike)Items.f_42692_, 6);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42153_, 1, (ItemLike)Items.f_42451_, 9);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41856_, 1, (ItemLike)Items.f_41830_);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42591_, 1, (ItemLike)Ic2Items.GRIN_POWDER, 2);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41905_, 1, (ItemLike)Items.f_42594_);
        basicMachineRecipeGenerator.add((TagKey<Item>)ItemTags.f_13167_, 1, (ItemLike)Items.f_42401_, 2);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.BIO_CHAFF, 1, (ItemLike)Items.f_42329_);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.COFFEE_BEANS, 3, (ItemLike)Ic2Items.COFFEE_POWDER);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.ENERGY_CRYSTAL, 1, (ItemLike)Ic2Items.ENERGIUM_DUST, 9);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.FUEL_ROD, 1, (ItemLike)Ic2Items.IRON_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.IRIDIUM, 1, (ItemLike)Ic2Items.IRIDIUM_SHARD, 9);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.TIN_CAN, 2, (ItemLike)Ic2Items.TIN_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41982_, 8, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42619_, 8, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((TagKey<Item>)ItemTags.f_13143_, 8, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42028_, 8, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42578_, 16, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.PLANT_BALL, 1, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42620_, 8, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42046_, 8, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42577_, 16, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((TagKey<Item>)ItemTags.f_13180_, 4, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41909_, 8, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42210_, 8, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.WEED, 32, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42405_, 8, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42404_, 16, (ItemLike)Ic2Items.BIO_CHAFF);
        basicMachineRecipeGenerator.add(Ic2ItemTags.BRONZE_INGOTS, 1, (ItemLike)Ic2Items.BRONZE_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42413_, 1, (ItemLike)Ic2Items.COAL_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_151052_, 1, (ItemLike)Ic2Items.COPPER_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42415_, 1, (ItemLike)Ic2Items.DIAMOND_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42417_, 1, (ItemLike)Ic2Items.GOLD_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42416_, 1, (ItemLike)Ic2Items.IRON_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_42534_, 1, (ItemLike)Ic2Items.LAPIS_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.LEAD_INGOTS, 1, (ItemLike)Ic2Items.LEAD_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Items.f_41999_, 1, (ItemLike)Ic2Items.OBSIDIAN_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.SILVER_INGOTS, 1, (ItemLike)Ic2Items.SILVER_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.STEEL_INGOTS, 1, (ItemLike)Ic2Items.IRON_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.TIN_INGOTS, 1, (ItemLike)Ic2Items.TIN_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.BRONZE_PLATES, 1, (ItemLike)Ic2Items.BRONZE_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.COPPER_PLATES, 1, (ItemLike)Ic2Items.COPPER_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.GOLD_PLATES, 1, (ItemLike)Ic2Items.GOLD_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.IRON_PLATES, 1, (ItemLike)Ic2Items.IRON_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.LAPIS_PLATES, 1, (ItemLike)Ic2Items.LAPIS_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.LEAD_PLATES, 1, (ItemLike)Ic2Items.LEAD_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.OBSIDIAN_PLATES, 1, (ItemLike)Ic2Items.OBSIDIAN_DUST);
        basicMachineRecipeGenerator.add(Ic2ItemTags.TIN_PLATES, 1, (ItemLike)Ic2Items.TIN_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.DENSE_BRONZE_PLATE, 1, (ItemLike)Ic2Items.BRONZE_DUST, 9);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.DENSE_COPPER_PLATE, 1, (ItemLike)Ic2Items.COPPER_DUST, 9);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.DENSE_GOLD_PLATE, 1, (ItemLike)Ic2Items.GOLD_DUST, 9);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.DENSE_IRON_PLATE, 1, (ItemLike)Ic2Items.IRON_DUST, 9);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.DENSE_LAPIS_PLATE, 1, (ItemLike)Ic2Items.LAPIS_DUST, 9);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.DENSE_LEAD_PLATE, 1, (ItemLike)Ic2Items.LEAD_DUST, 9);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.DENSE_OBSIDIAN_PLATE, 1, (ItemLike)Ic2Items.OBSIDIAN_DUST, 9);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.DENSE_TIN_PLATE, 1, (ItemLike)Ic2Items.TIN_DUST, 9);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.CRUSHED_COPPER, 1, (ItemLike)Ic2Items.COPPER_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.CRUSHED_GOLD, 1, (ItemLike)Ic2Items.GOLD_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.CRUSHED_IRON, 1, (ItemLike)Ic2Items.IRON_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.CRUSHED_LEAD, 1, (ItemLike)Ic2Items.LEAD_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.CRUSHED_SILVER, 1, (ItemLike)Ic2Items.SILVER_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.CRUSHED_TIN, 1, (ItemLike)Ic2Items.TIN_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.PURIFIED_COPPER, 1, (ItemLike)Ic2Items.COPPER_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.PURIFIED_GOLD, 1, (ItemLike)Ic2Items.GOLD_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.PURIFIED_IRON, 1, (ItemLike)Ic2Items.IRON_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.PURIFIED_LEAD, 1, (ItemLike)Ic2Items.LEAD_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.PURIFIED_SILVER, 1, (ItemLike)Ic2Items.SILVER_DUST);
        basicMachineRecipeGenerator.add((ItemLike)Ic2Items.PURIFIED_TIN, 1, (ItemLike)Ic2Items.TIN_DUST);
        basicMachineRecipeGenerator.add((TagKey<Item>)ItemTags.f_144318_, 1, (ItemLike)Ic2Items.CRUSHED_COPPER, 2);
        basicMachineRecipeGenerator.add((TagKey<Item>)ItemTags.f_13152_, 1, (ItemLike)Ic2Items.CRUSHED_GOLD, 2);
        basicMachineRecipeGenerator.add((TagKey<Item>)ItemTags.f_144312_, 1, (ItemLike)Ic2Items.CRUSHED_IRON, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.LEAD_ORES, 1, (ItemLike)Ic2Items.CRUSHED_LEAD, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.LEAD_RAW_ORES, 1, (ItemLike)Ic2Items.CRUSHED_LEAD, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.SILVER_ORES, 1, (ItemLike)Ic2Items.CRUSHED_SILVER, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.TIN_ORES, 1, (ItemLike)Ic2Items.CRUSHED_TIN, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.TIN_RAW_ORES, 1, (ItemLike)Ic2Items.CRUSHED_TIN, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.URANIUM_ORES, 1, (ItemLike)Ic2Items.CRUSHED_URANIUM, 2);
        basicMachineRecipeGenerator.add(Ic2ItemTags.URANIUM_RAW_ORES, 1, (ItemLike)Ic2Items.CRUSHED_URANIUM, 2);
    }
}


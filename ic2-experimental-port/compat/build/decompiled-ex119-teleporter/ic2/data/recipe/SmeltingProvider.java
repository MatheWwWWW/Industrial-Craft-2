/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.CriterionTriggerInstance
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.recipes.FinishedRecipe
 *  net.minecraft.data.recipes.SimpleCookingRecipeBuilder
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 */
package ic2.data.recipe;

import ic2.core.ref.Ic2Items;
import ic2.data.recipe.helper.IC2RecipeProvider;
import java.util.function.Consumer;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class SmeltingProvider
extends IC2RecipeProvider {
    public SmeltingProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public String m_6055_() {
        return "IC2 Smelting Recipes";
    }

    @Override
    protected void generate(Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.TIN_ORE}), (ItemLike)Ic2Items.TIN_INGOT, (float)0.5f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.TIN_ORE)).m_176500_(consumer, "ic2:smelting/tin_ingot");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.LEAD_ORE}), (ItemLike)Ic2Items.LEAD_INGOT, (float)0.5f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.LEAD_ORE)).m_176500_(consumer, "ic2:smelting/lead_ingot");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.URANIUM_ORE}), (ItemLike)Ic2Items.URANIUM_INGOT, (float)0.5f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.URANIUM_ORE)).m_176500_(consumer, "ic2:smelting/uranium_ingot");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.DEEPSLATE_TIN_ORE}), (ItemLike)Ic2Items.TIN_INGOT, (float)0.5f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.DEEPSLATE_TIN_ORE)).m_176500_(consumer, "ic2:smelting/tin_ingot_from_deepslate");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.DEEPSLATE_LEAD_ORE}), (ItemLike)Ic2Items.LEAD_INGOT, (float)0.5f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.DEEPSLATE_LEAD_ORE)).m_176500_(consumer, "ic2:smelting/lead_ingot_from_deepslate");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.DEEPSLATE_URANIUM_ORE}), (ItemLike)Ic2Items.URANIUM_INGOT, (float)0.5f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.DEEPSLATE_URANIUM_ORE)).m_176500_(consumer, "ic2:smelting/uranium_ingot_from_deepslate");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.CRUSHED_IRON}), (ItemLike)Items.f_42416_, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.CRUSHED_IRON)).m_176500_(consumer, "ic2:smelting/iron_ingot_from_crushed_iron");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.CRUSHED_GOLD}), (ItemLike)Items.f_42417_, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.CRUSHED_GOLD)).m_176500_(consumer, "ic2:smelting/gold_ingot_from_crushed_gold");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.CRUSHED_COPPER}), (ItemLike)Items.f_151052_, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.CRUSHED_COPPER)).m_176500_(consumer, "ic2:smelting/copper_ingot_from_crushed_copper");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.CRUSHED_TIN}), (ItemLike)Ic2Items.TIN_INGOT, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.CRUSHED_TIN)).m_176500_(consumer, "ic2:smelting/tin_ingot_from_crushed_tin");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.CRUSHED_LEAD}), (ItemLike)Ic2Items.LEAD_INGOT, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.CRUSHED_LEAD)).m_176500_(consumer, "ic2:smelting/lead_ingot_from_crushed_lead");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.CRUSHED_URANIUM}), (ItemLike)Ic2Items.URANIUM_INGOT, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.CRUSHED_URANIUM)).m_176500_(consumer, "ic2:smelting/uranium_ingot_from_crushed_uranium");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.CRUSHED_SILVER}), (ItemLike)Ic2Items.SILVER_INGOT, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.CRUSHED_SILVER)).m_176500_(consumer, "ic2:smelting/silver_ingot_from_crushed_silver");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.PURIFIED_IRON}), (ItemLike)Items.f_42416_, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.PURIFIED_IRON)).m_176500_(consumer, "ic2:smelting/iron_ingot_from_purified_iron");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.PURIFIED_GOLD}), (ItemLike)Items.f_42417_, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.PURIFIED_GOLD)).m_176500_(consumer, "ic2:smelting/gold_ingot_from_purified_gold");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.PURIFIED_COPPER}), (ItemLike)Items.f_151052_, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.PURIFIED_COPPER)).m_176500_(consumer, "ic2:smelting/copper_ingot_from_purified_copper");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.PURIFIED_TIN}), (ItemLike)Ic2Items.TIN_INGOT, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.PURIFIED_TIN)).m_176500_(consumer, "ic2:smelting/tin_ingot_from_purified_tin");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.PURIFIED_LEAD}), (ItemLike)Ic2Items.LEAD_INGOT, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.PURIFIED_LEAD)).m_176500_(consumer, "ic2:smelting/lead_ingot_from_purified_lead");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.PURIFIED_URANIUM}), (ItemLike)Ic2Items.URANIUM_INGOT, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.PURIFIED_URANIUM)).m_176500_(consumer, "ic2:smelting/uranium_ingot_from_purified_uranium");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.PURIFIED_SILVER}), (ItemLike)Ic2Items.SILVER_INGOT, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.PURIFIED_SILVER)).m_176500_(consumer, "ic2:smelting/silver_ingot_from_purified_silver");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.IRON_DUST}), (ItemLike)Items.f_42416_, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.IRON_DUST)).m_176500_(consumer, "ic2:smelting/iron_ingot_from_iron_dust");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.GOLD_DUST}), (ItemLike)Items.f_42417_, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.GOLD_DUST)).m_176500_(consumer, "ic2:smelting/gold_ingot_from_gold_dust");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.COPPER_DUST}), (ItemLike)Items.f_151052_, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.COPPER_DUST)).m_176500_(consumer, "ic2:smelting/copper_ingot_from_copper_dust");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.TIN_DUST}), (ItemLike)Ic2Items.TIN_INGOT, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.TIN_DUST)).m_176500_(consumer, "ic2:smelting/tin_ingot_from_tin_dust");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.LEAD_DUST}), (ItemLike)Ic2Items.LEAD_INGOT, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.LEAD_DUST)).m_176500_(consumer, "ic2:smelting/lead_ingot_from_lead_dust");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.SILVER_DUST}), (ItemLike)Ic2Items.SILVER_INGOT, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.SILVER_DUST)).m_176500_(consumer, "ic2:smelting/silver_ingot_from_silver_dust");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.BRONZE_DUST}), (ItemLike)Ic2Items.BRONZE_INGOT, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.BRONZE_DUST)).m_176500_(consumer, "ic2:smelting/bronze_ingot_from_bronze_dust");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.RUBBER_LOG}), (ItemLike)Items.f_41840_, (float)0.1f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.RUBBER_LOG)).m_176500_(consumer, "ic2:smelting/jungle_log_from_rubber_log");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.RESIN}), (ItemLike)Ic2Items.RUBBER, (float)0.3f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.RESIN)).m_176500_(consumer, "ic2:smelting/rubber");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.COAL_FUEL_DUST}), (ItemLike)Ic2Items.COAL_DUST, (float)0.0f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.COAL_FUEL_DUST)).m_176500_(consumer, "ic2:smelting/coal_dust");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.COLD_COFFEE_MUG}), (ItemLike)Ic2Items.DARK_COFFEE_MUG, (float)0.1f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.COLD_COFFEE_MUG)).m_176500_(consumer, "ic2:smelting/dark_coffee_mug");
        SimpleCookingRecipeBuilder.m_126272_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Ic2Items.RAW_CRYSTAL_MEMORY}), (ItemLike)Ic2Items.CRYSTAL_MEMORY, (float)0.1f, (int)200).m_126132_("has_item", (CriterionTriggerInstance)SmeltingProvider.conditionsFromItem((ItemLike)Ic2Items.RAW_CRYSTAL_MEMORY)).m_176500_(consumer, "ic2:smelting/crystal_memory");
    }
}


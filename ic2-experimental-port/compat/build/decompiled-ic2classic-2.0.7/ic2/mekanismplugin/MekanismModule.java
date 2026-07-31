/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.fml.ModList
 */
package ic2.mekanismplugin;

import ic2.api.addons.IC2Plugin;
import ic2.api.addons.IModule;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.ItemInput;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.recipes.registries.IRefiningRecipeList;
import ic2.core.IC2;
import ic2.core.block.machines.recipes.EnricherRecipeList;
import ic2.core.block.machines.recipes.misc.EnrichRecipe;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.ColorUtils;
import ic2.mekanismplugin.MekanismItems;
import ic2.mekanismplugin.MekanismTags;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

@IC2Plugin(id="mekanism", name="Mekanism-Plugin", version="1.0")
public class MekanismModule
implements IModule {
    @Override
    public boolean canLoad(Dist side) {
        return ModList.get().isLoaded("mekanism");
    }

    @Override
    public void preInit(IEventBus modBus) {
        IC2.RECIPES.get((boolean)true).enricher.registerListener(this::registerEnricherRecipes);
        IC2.RECIPES.get((boolean)true).macerator.registerListener(this::registerMaceratorRecipes);
        IC2.RECIPES.get((boolean)true).refining.registerListener(this::registerRefineryRecipes);
    }

    private void registerRefineryRecipes(IRefiningRecipeList recipes) {
        if (MekanismItems.OSMIUM_RAW == null || MekanismItems.LEAD_RAW == null) {
            return;
        }
        MekanismModule.createOreBuilder(recipes, "osmium_to_raw", MekanismTags.ORE_OSMIUM, MekanismItems.OSMIUM_RAW, 1);
        MekanismModule.createOreBuilder(recipes, "lead_to_raw", MekanismTags.ORE_LEAD, MekanismItems.LEAD_RAW, 1);
    }

    private static void createOreBuilder(IRefiningRecipeList recipe, String id, TagKey<Item> ore, Item output, int base) {
        recipe.addIC2Recipe(id).addMod(RecipeMods.RECIPE_TIME, 0.5).catalyst(ore).mainInput((Fluid)Fluids.f_76195_, 25 * base).buildRange(new ItemStack((ItemLike)output), base, base * 3);
        recipe.addIC2Recipe(id + "_advanced").addMod(RecipeMods.RECIPE_TIME, 0.5).catalyst(ore).mainInput(IC2Fluids.BIO_FUEL, 50 * base).buildRange(new ItemStack((ItemLike)output), base * 3, base * 5);
        recipe.addIC2Recipe(id + "_alk").addMod(RecipeMods.RECIPE_TIME, 0.5).catalyst(ore).mainInput(IC2Fluids.ALCOHOL, 25 * base).buildRange(new ItemStack((ItemLike)output), base * 5, base * 7);
    }

    private void registerMaceratorRecipes(IMachineRecipeList recipes) {
        if (MekanismItems.OSMIUM_DUST == null || MekanismItems.LEAD_DUST == null) {
            return;
        }
        recipes.addIC2XPRecipe("osmium_ore_to_dust", new ItemStack((ItemLike)MekanismItems.OSMIUM_DUST, 2), 0.7f, MekanismTags.ORE_OSMIUM);
        recipes.addIC2XPRecipe("raw_osmium_to_dust", new ItemStack((ItemLike)MekanismItems.OSMIUM_DUST, 2), 0.7f, MekanismTags.RAW_OSMIUM);
        recipes.addIC2XPRecipe("raw_osmium_block_to_dust", new ItemStack((ItemLike)MekanismItems.OSMIUM_DUST, 18), 6.3f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{MekanismTags.STORAGE_RAW_OSMIUM});
        recipes.addIC2XPRecipe("osmium_block_to_dust", new ItemStack((ItemLike)MekanismItems.OSMIUM_DUST, 9), 0.9f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{MekanismTags.STORAGE_OSMIUM});
        recipes.addIC2XPRecipe("osmium_ingot_to_dust", new ItemStack((ItemLike)MekanismItems.OSMIUM_DUST, 1), 0.1f, MekanismTags.INGOT_OSMIUM);
        recipes.addIC2XPRecipe("lead_ore_to_dust", new ItemStack((ItemLike)MekanismItems.LEAD_DUST, 2), 0.7f, MekanismTags.ORE_LEAD);
        recipes.addIC2XPRecipe("raw_lead_to_dust", new ItemStack((ItemLike)MekanismItems.LEAD_DUST, 2), 0.7f, MekanismTags.RAW_LEAD);
        recipes.addIC2XPRecipe("raw_lead_block_to_dust", new ItemStack((ItemLike)MekanismItems.LEAD_DUST, 18), 6.3f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{MekanismTags.STORAGE_RAW_LEAD});
        recipes.addIC2XPRecipe("lead_block_to_dust", new ItemStack((ItemLike)MekanismItems.LEAD_DUST, 9), 0.9f, RecipeMods.RECIPE_TIME.create(4.0), new Object[]{MekanismTags.STORAGE_LEAD});
        recipes.addIC2XPRecipe("lead_ingot_to_dust", new ItemStack((ItemLike)MekanismItems.LEAD_DUST, 1), 0.1f, MekanismTags.INGOT_LEAD);
    }

    private void registerEnricherRecipes(EnricherRecipeList recipes) {
        if (MekanismItems.URANIUM_INGOT == null) {
            return;
        }
        recipes.registerRecipe(EnrichRecipe.createIC2Recipe(MekanismItems.URANIUM_INGOT, Items.f_42585_, 200, IC2Items.INGOT_URANIUM_ENRICHED_BLAZE, 100, ColorUtils.rgb(232, 155, 7, 255), 20, "blaze"));
        Object2IntLinkedOpenHashMap inputs = new Object2IntLinkedOpenHashMap();
        inputs.put((Object)new ItemInput(Items.f_42414_), 25);
        inputs.put((Object)new ItemInput((ItemLike)IC2Blocks.CHARCOAL_BLOCK), 200);
        recipes.registerRecipe(EnrichRecipe.createIC2Recipe(MekanismItems.URANIUM_INGOT, (Object2IntMap<IInput>)inputs, IC2Items.INGOT_URANIUM_ENRICHED_CHARCOAL, 100, ColorUtils.rgb(54, 54, 54, 255), 5, "charcoal"));
        recipes.registerRecipe(EnrichRecipe.createIC2Recipe(MekanismItems.URANIUM_INGOT, Items.f_42584_, 100, IC2Items.INGOT_URANIUM_ENRICHED_ENDERPEARL, 100, ColorUtils.rgb(35, 174, 113, 255), 75, "ender"));
        recipes.registerRecipe(EnrichRecipe.createIC2Recipe(MekanismItems.URANIUM_INGOT, Items.f_42686_, 200, IC2Items.INGOT_URANIUM_ENRICHED_NETHERSTAR, 150, ColorUtils.rgb(255, 239, 106, 255), 150, "nether_star"));
        inputs = new Object2IntLinkedOpenHashMap();
        inputs.put((Object)new ItemInput(Items.f_42451_), 25);
        inputs.put((Object)new ItemInput(Items.f_42153_), 200);
        recipes.registerRecipe(EnrichRecipe.createIC2Recipe(MekanismItems.URANIUM_INGOT, (Object2IntMap<IInput>)inputs, IC2Items.INGOT_URANIUM_ENRICHED_REDSTONE, 100, ColorUtils.RED, 25, "redstone"));
    }
}


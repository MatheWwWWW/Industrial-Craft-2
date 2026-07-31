/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.api.recipes.registries.IAdvancedCraftingManager
 *  ic2.api.recipes.registries.IElectrolyzerRecipeList
 *  ic2.core.IC2
 *  ic2.core.block.machines.recipes.ElectrolyzerRecipeList
 *  ic2.core.platform.recipes.misc.AdvRecipeRegistry
 *  ic2.core.platform.recipes.misc.GlobalRecipes
 *  ic2.core.platform.registries.IC2Blocks
 *  ic2.core.platform.registries.IC2Items
 *  ic2.core.platform.registries.IC2Tags
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.common.Tags$Items
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.registries.ForgeRegistries
 */
package trinsdar.advancedsolars.util;

import ic2.api.recipes.registries.IAdvancedCraftingManager;
import ic2.api.recipes.registries.IElectrolyzerRecipeList;
import ic2.core.IC2;
import ic2.core.block.machines.recipes.ElectrolyzerRecipeList;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import ic2.core.platform.recipes.misc.GlobalRecipes;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import trinsdar.advancedsolars.util.AdvancedSolarsConfig;
import trinsdar.advancedsolars.util.Registry;

public class AdvancedSolarsRecipes {
    public static final ElectrolyzerRecipeList MOLECULAR_TRANSFORMER = new ElectrolyzerRecipeList(AdvancedSolarsRecipes::initMolecularTransformerRecipes){

        public void addDualRecipe(ResourceLocation id, ItemStack input, ItemStack output, int energy) {
            this.addChargeRecipe(id, input, output, energy);
        }

        public void addDischargeRecipe(ResourceLocation id, ItemStack input, ItemStack output, int energy) {
        }

        public String getFolder() {
            return "molecular_transformer";
        }
    };

    public static void init() {
        ((GlobalRecipes)IC2.RECIPES.get(true)).getLists().add(MOLECULAR_TRANSFORMER);
        ((GlobalRecipes)IC2.RECIPES.get((boolean)true)).electrolyzer.registerListener(r -> MOLECULAR_TRANSFORMER.reload());
        ((GlobalRecipes)IC2.RECIPES.get((boolean)true)).compressor.registerListener(r -> r.addSimpleRecipe(AdvancedSolarsRecipes.id("iridium_ore_to_iridium_ingot"), new ItemStack((ItemLike)Registry.IRIDIUM_INGOT), new Object[]{IC2Items.ORE_IRIDIUM}));
        AdvRecipeRegistry.INSTANCE.registerListener(AdvancedSolarsRecipes::initCraftingRecipes);
    }

    private static void initMolecularTransformerRecipes(IElectrolyzerRecipeList list) {
        list.addChargeRecipe(AdvancedSolarsRecipes.id("nether_star"), new ItemStack((ItemLike)Items.f_42679_), new ItemStack((ItemLike)Items.f_42686_), 250000000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("iridium_ore"), new ItemStack((ItemLike)Items.f_42416_), new ItemStack((ItemLike)IC2Items.ORE_IRIDIUM), 9000000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("gunpowder"), new ItemStack((ItemLike)Items.f_42048_), new ItemStack((ItemLike)Items.f_42403_, 2), 70000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("gravel"), new ItemStack((ItemLike)Items.f_41830_), new ItemStack((ItemLike)Items.f_41832_), 50000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("clay"), new ItemStack((ItemLike)Items.f_42329_), new ItemStack((ItemLike)Items.f_41983_), 50000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("coal"), new ItemStack((ItemLike)Items.f_42414_), new ItemStack((ItemLike)Items.f_42413_), 60000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("sunnarium_piece"), new ItemStack((ItemLike)Items.f_42525_), new ItemStack((ItemLike)Registry.SUNNARIUM_PART), 1000000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("sunnarium"), new ItemStack((ItemLike)Items.f_42054_), new ItemStack((ItemLike)Registry.SUNNARIUM), 9000000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("glowstone"), new ItemStack((ItemLike)Items.f_41874_), new ItemStack((ItemLike)Items.f_42054_), 500000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("lapis_block"), new ItemStack((ItemLike)Items.f_41934_), new ItemStack((ItemLike)Items.f_41854_), 500000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("redstone_block"), new ItemStack((ItemLike)Items.f_41937_), new ItemStack((ItemLike)Items.f_42153_), 500000);
        if (ModList.get().isLoaded("antimatter_shared")) {
            Item item = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation("antimatter_shared", "sapphire"));
            if (item != null) {
                list.addChargeRecipe(AdvancedSolarsRecipes.id("sapphire"), new ItemStack((ItemLike)Items.f_42534_), new ItemStack((ItemLike)item), 5000000);
            }
            if ((item = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation("antimatter_shared", "ruby"))) != null) {
                list.addChargeRecipe(AdvancedSolarsRecipes.id("ruby"), new ItemStack((ItemLike)Items.f_42451_), new ItemStack((ItemLike)item), 5000000);
            }
            item = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation("antimatter_shared", "dust_chrome"));
            Item item2 = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation("antimatter_shared", "dust_titanium"));
            if (item != null && item2 != null) {
                list.addChargeRecipe(AdvancedSolarsRecipes.id("chrome_dust"), new ItemStack((ItemLike)item2), new ItemStack((ItemLike)item), 500000);
            }
            item = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation("antimatter_shared", "ingot_chrome"));
            item2 = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation("antimatter_shared", "ingot_titanium"));
            if (item != null && item2 != null) {
                list.addChargeRecipe(AdvancedSolarsRecipes.id("chrome_ingot"), new ItemStack((ItemLike)item2), new ItemStack((ItemLike)item), 500000);
            }
        }
        if (ModList.get().isLoaded("ae2")) {
            list.addChargeRecipe(AdvancedSolarsRecipes.id("certus_quartz"), new ItemStack((ItemLike)Items.f_42692_), new ItemStack((ItemLike)ForgeRegistries.ITEMS.getValue(new ResourceLocation("ae2", "certus_quartz_crystal"))), 500000);
        }
        list.addChargeRecipe(AdvancedSolarsRecipes.id("silver_ingot"), new ItemStack((ItemLike)IC2Items.INGOT_TIN), new ItemStack((ItemLike)IC2Items.INGOT_SILVER), 500000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("gold_ingot"), new ItemStack((ItemLike)IC2Items.INGOT_SILVER), new ItemStack((ItemLike)Items.f_42417_), 500000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("industrial_diamond"), new ItemStack((ItemLike)Items.f_42413_), new ItemStack((ItemLike)IC2Items.INDUSTRIAL_DIAMOND), 9000000);
        list.addChargeRecipe(AdvancedSolarsRecipes.id("diamond"), new ItemStack((ItemLike)IC2Items.INDUSTRIAL_DIAMOND), new ItemStack((ItemLike)Items.f_42415_), 1000000);
    }

    public static void initCraftingRecipes(IAdvancedCraftingManager registry) {
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("sunnarium"), new ItemStack((ItemLike)Registry.SUNNARIUM), new Object[]{"UUU", "GGG", "UUU", Character.valueOf('U'), IC2Items.UUMATTER, Character.valueOf('G'), Tags.Items.DUSTS_GLOWSTONE});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("sunnarium_alloy"), new ItemStack((ItemLike)Registry.SUNNARIUM_ALLOY), new Object[]{"III", "ISI", "III", Character.valueOf('I'), IC2Items.PLATE_IRIDIUM, Character.valueOf('S'), Registry.SUNNARIUM});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("iridium_iron_plate"), new ItemStack((ItemLike)Registry.IRIDIUM_IRON_PLATE), new Object[]{"rrr", "rir", "rrr", Character.valueOf('r'), IC2Tags.INGOT_REFINED_IRON, Character.valueOf('i'), AdvancedSolarsRecipes.getItemTag(new ResourceLocation("forge", "ingots/iridium"))});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("reinforced_iridium_iron_plate"), new ItemStack((ItemLike)Registry.REINFORCED_IRIDIUM_IRON_PLATE), new Object[]{"aca", "cic", "aca", Character.valueOf('a'), IC2Items.PLATE_ADVANCED_ALLOY, Character.valueOf('c'), IC2Items.CARBON_PLATE, Character.valueOf('i'), Registry.IRIDIUM_IRON_PLATE});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("irradiant_reinforced_plate"), new ItemStack((ItemLike)Registry.IRRADIANT_REINFORCED_PLATE), new Object[]{"rsr", "lRl", "rdr", Character.valueOf('r'), Tags.Items.DUSTS_REDSTONE, Character.valueOf('s'), Registry.SUNNARIUM_PART, Character.valueOf('l'), Tags.Items.DYES_BLUE, Character.valueOf('R'), Registry.REINFORCED_IRIDIUM_IRON_PLATE, Character.valueOf('d'), Tags.Items.GEMS_DIAMOND});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("sunnarium_part"), new ItemStack((ItemLike)Registry.SUNNARIUM_PART), new Object[]{"ugu", Character.valueOf('u'), IC2Items.UUMATTER, Character.valueOf('g'), Tags.Items.DUSTS_GLOWSTONE});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("irradiant_uranium"), new ItemStack((ItemLike)Registry.IRRADIANT_URANIUM), new Object[]{"ugu", "geg", "ugu", Character.valueOf('u'), IC2Items.UUMATTER, Character.valueOf('e'), AdvancedSolarsRecipes.getUranium(), Character.valueOf('g'), Tags.Items.DUSTS_GLOWSTONE});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("enriched_sunnarium"), new ItemStack((ItemLike)Registry.ENRICHED_SUNNARIUM), new Object[]{"iii", "isi", "iii", Character.valueOf('i'), Registry.IRRADIANT_URANIUM, Character.valueOf('s'), Registry.SUNNARIUM});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("irradiant_glass_pane"), new ItemStack((ItemLike)Registry.IRRADIANT_GLASS_PANE, 6), new Object[]{"rrr", "igi", "rrr", Character.valueOf('r'), IC2Blocks.REINFORCED_GLASS, Character.valueOf('i'), Registry.IRRADIANT_URANIUM, Character.valueOf('g'), Tags.Items.DUSTS_GLOWSTONE});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("enriched_sunnarium_alloy"), new ItemStack((ItemLike)Registry.ENRICHED_SUNNARIUM_ALLOY), new Object[]{"pep", "ese", "pep", Character.valueOf('p'), IC2Items.CELL_PLASMA, Character.valueOf('e'), Registry.ENRICHED_SUNNARIUM, Character.valueOf('s'), Registry.SUNNARIUM_ALLOY});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("mt_core"), new ItemStack((ItemLike)Registry.MT_CORE), new Object[]{"INI", "I I", "INI", Character.valueOf('I'), Registry.IRRADIANT_GLASS_PANE, Character.valueOf('N'), IC2Items.REFLECTOR_THICK});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("advanced_solar_panel"), new ItemStack((ItemLike)Registry.ADVANCED_SOLAR_PANEL), new Object[]{"rrr", "asa", "cic", Character.valueOf('r'), Registry.IRRADIANT_GLASS_PANE, Character.valueOf('a'), IC2Items.PLATE_ADVANCED_ALLOY, Character.valueOf('s'), IC2Blocks.SOLAR_PANEL_LV, Character.valueOf('c'), IC2Items.ADVANCED_CIRCUIT, Character.valueOf('i'), Registry.IRRADIANT_REINFORCED_PLATE});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("advanced_solar_helmet"), new ItemStack((ItemLike)Registry.ADVANCED_SOLAR_HELMET), new Object[]{"sas", "cnc", "glg", Character.valueOf('s'), IC2Items.SOLAR_HELMET_ADVANCED, Character.valueOf('a'), Registry.ADVANCED_SOLAR_PANEL, Character.valueOf('c'), IC2Items.ADVANCED_CIRCUIT, Character.valueOf('n'), IC2Items.SOLAR_HELMET_ADVANCED, Character.valueOf('g'), IC2Items.GOLD_CABLE_2X_INSULATED, Character.valueOf('l'), IC2Blocks.TRANSFORMER_LV});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("hybrid_solar_panel"), new ItemStack((ItemLike)Registry.HYBRID_SOLAR_PANEL), new Object[]{"CmC", "iai", "csc", Character.valueOf('C'), IC2Items.CARBON_PLATE, Character.valueOf('m'), IC2Blocks.SOLAR_PANEL_MV, Character.valueOf('i'), IC2Items.PLATE_IRIDIUM, Character.valueOf('a'), Registry.ADVANCED_SOLAR_PANEL, Character.valueOf('c'), IC2Items.ADVANCED_CIRCUIT, Character.valueOf('s'), Registry.ENRICHED_SUNNARIUM});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("hybrid_solar_helmet"), new ItemStack((ItemLike)Registry.HYBRID_SOLAR_HELMET), new Object[]{" H ", "cqc", "ghg", Character.valueOf('H'), Registry.HYBRID_SOLAR_PANEL, Character.valueOf('c'), IC2Items.ADVANCED_CIRCUIT, Character.valueOf('q'), Registry.ADVANCED_SOLAR_HELMET, Character.valueOf('g'), IC2Items.GLASSFIBER_CABLE, Character.valueOf('h'), IC2Blocks.TRANSFORMER_HV});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("ultimate_hybrid_solar_panel"), new ItemStack((ItemLike)Registry.ULTIMATE_HYBRID_SOLAR_PANEL), new Object[]{" h ", "pHp", "scs", Character.valueOf('h'), IC2Blocks.SOLAR_PANEL_HV, Character.valueOf('p'), IC2Items.PLASMA_CORE, Character.valueOf('H'), Registry.HYBRID_SOLAR_PANEL, Character.valueOf('s'), Registry.ENRICHED_SUNNARIUM_ALLOY, Character.valueOf('c'), IC2Items.COAL_CHUNK});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("ultimate_hybrid_solar_helmet"), new ItemStack((ItemLike)Registry.ULTIMATE_HYBRID_SOLAR_HELMET), new Object[]{"ppp", "pup", "ihi", Character.valueOf('p'), IC2Items.CELL_PLASMA, Character.valueOf('u'), Registry.ULTIMATE_HYBRID_SOLAR_PANEL, Character.valueOf('i'), IC2Items.PLATE_IRIDIUM, Character.valueOf('h'), Registry.HYBRID_SOLAR_HELMET});
        registry.addShapedRecipe(AdvancedSolarsRecipes.id("molecular_transformer"), new ItemStack((ItemLike)Registry.MOLECULAR_TRANSFORMER), new Object[]{"AEA", "CMC", "AEA", Character.valueOf('A'), IC2Blocks.ADVANCED_MACHINE_BLOCK, Character.valueOf('E'), IC2Blocks.TRANSFORMER_EV, Character.valueOf('C'), IC2Items.ADVANCED_CIRCUIT, Character.valueOf('M'), Registry.MT_CORE});
    }

    public static Object getUranium() {
        TagKey<Item> defaultUranium = IC2Items.INGOT_URANIUM_ENRICHED_ENDERPEARL;
        return switch (AdvancedSolarsConfig.MISC.INGOT_IN_IRRADIANT_URANIUM) {
            case AdvancedSolarsConfig.Misc.CenterIngot.URANIUM -> AdvancedSolarsRecipes.getItemTag(new ResourceLocation("forge", "ingots/uranium"));
            case AdvancedSolarsConfig.Misc.CenterIngot.URANIUM233 -> AdvancedSolarsRecipes.getItemTag(new ResourceLocation("forge", "ingots/uranium233"));
            case AdvancedSolarsConfig.Misc.CenterIngot.URANIUM235 -> AdvancedSolarsRecipes.getItemTag(new ResourceLocation("forge", "ingots/uranium235"));
            default -> defaultUranium;
        };
    }

    private static TagKey<Item> getItemTag(ResourceLocation location) {
        return TagKey.m_203882_((ResourceKey)net.minecraft.core.Registry.f_122904_, (ResourceLocation)location);
    }

    private static ResourceLocation id(String id) {
        return new ResourceLocation("advanced_solars", id);
    }
}


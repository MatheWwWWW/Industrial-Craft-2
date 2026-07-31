/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  ic2.api.recipes.registries.IAdvancedCraftingManager
 *  ic2.core.platform.recipes.mods.IRecipeModifier
 *  ic2.core.platform.registries.IC2Blocks
 *  ic2.core.platform.registries.IC2Items
 *  ic2.core.platform.registries.IC2Tags
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.common.Tags$Items
 */
package trinsdar.gravisuit.util;

import com.google.gson.JsonObject;
import ic2.api.recipes.registries.IAdvancedCraftingManager;
import ic2.core.platform.recipes.mods.IRecipeModifier;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import trinsdar.gravisuit.util.Registry;

public class GravisuitRecipes {
    public static void loadRecipes(IAdvancedCraftingManager registry) {
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "super_conductor_cover"), new ItemStack((ItemLike)Registry.SUPER_CONDUCTOR_COVER, 3), new Object[]{"AIA", "CCC", "AIA", Character.valueOf('A'), IC2Items.PLATE_ADVANCED_ALLOY, Character.valueOf('I'), IC2Items.PLATE_IRIDIUM, Character.valueOf('C'), IC2Items.CARBON_PLATE});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "super_conductor"), new ItemStack((ItemLike)Registry.SUPER_CONDUCTOR, 3), new Object[]{"SSS", "GUG", "SSS", Character.valueOf('S'), Registry.SUPER_CONDUCTOR_COVER, Character.valueOf('G'), IC2Items.GLASSFIBER_CABLE, Character.valueOf('U'), IC2Items.UUMATTER});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "cooling_core"), new ItemStack((ItemLike)Registry.COOLING_CORE), new Object[]{"CAC", "HIH", "CAC", Character.valueOf('C'), IC2Items.COOLANT_CELL_60K, Character.valueOf('A'), IC2Items.HEAT_EXCHANGER_ADVANCED, Character.valueOf('H'), IC2Items.PLATING_HEAT_CAPACITY, Character.valueOf('I'), IC2Items.PLATE_IRIDIUM});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "gravitation_engine"), new ItemStack((ItemLike)Registry.GRAVITATION_ENGINE), new Object[]{"TST", "CIC", "TST", Character.valueOf('T'), IC2Blocks.TESLA_COIL, Character.valueOf('S'), Registry.SUPER_CONDUCTOR, Character.valueOf('C'), Registry.COOLING_CORE, Character.valueOf('I'), IC2Blocks.TRANSFORMER_IV});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "magnetron"), new ItemStack((ItemLike)Registry.MAGNETRON), new Object[]{"ICI", "CSC", "ICI", Character.valueOf('I'), IC2Tags.INGOT_REFINED_IRON, Character.valueOf('C'), IC2Tags.INGOT_COPPER, Character.valueOf('S'), Registry.SUPER_CONDUCTOR});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "vajra_core"), new ItemStack((ItemLike)Registry.VAJRA_CORE), new Object[]{" M ", "ITI", "StS", Character.valueOf('M'), Registry.MAGNETRON, Character.valueOf('I'), IC2Items.PLATE_IRIDIUM, Character.valueOf('T'), IC2Blocks.TESLA_COIL, Character.valueOf('S'), Registry.SUPER_CONDUCTOR, Character.valueOf('t'), IC2Blocks.TRANSFORMER_IV});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "engine_boost"), new ItemStack((ItemLike)Registry.ENGINE_BOOST), new Object[]{"GAG", "COC", "AHA", Character.valueOf('G'), Tags.Items.DUSTS_GLOWSTONE, Character.valueOf('A'), IC2Items.PLATE_ADVANCED_ALLOY, Character.valueOf('C'), IC2Items.ADVANCED_CIRCUIT, Character.valueOf('O'), IC2Items.OVERCLOCKER_UPGRADE, Character.valueOf('H'), IC2Items.VENT_HEAT_ADVANCED});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "advanced_electric_jetpack"), new ItemStack((ItemLike)Registry.ADVANCED_ELECTRIC_JETPACK), new Object[]{"CEC", "BLB", "GAG", Character.valueOf('C'), IC2Items.CARBON_PLATE, Character.valueOf('E'), IC2Items.JETPACK_ELECTRIC, Character.valueOf('B'), Registry.ENGINE_BOOST, Character.valueOf('L'), Registry.ADVANCED_LAPPACK, Character.valueOf('G'), IC2Items.GLASSFIBER_CABLE, Character.valueOf('A'), IC2Items.ADVANCED_CIRCUIT});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "advanced_nuclear_jetpack"), new ItemStack((ItemLike)Registry.ADVANCED_NUCLEAR_JETPACK), new Object[]{"CEC", "BLB", "GAG", Character.valueOf('C'), IC2Items.CARBON_PLATE, Character.valueOf('E'), IC2Items.JETPACK_NUCLEAR, Character.valueOf('B'), Registry.ENGINE_BOOST, Character.valueOf('L'), Registry.ADVANCED_LAPPACK, Character.valueOf('G'), IC2Items.GLASSFIBER_CABLE, Character.valueOf('A'), IC2Items.ADVANCED_CIRCUIT});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "advanced_nuclear_jetpack_upgrade"), new ItemStack((ItemLike)Registry.ADVANCED_NUCLEAR_JETPACK), new Object[]{"CTC", "RNR", "CAC", Character.valueOf('C'), IC2Items.ADVANCED_CIRCUIT, Character.valueOf('T'), IC2Blocks.TRANSFORMER_EV, Character.valueOf('R'), IC2Blocks.REACTOR_CHAMBER, Character.valueOf('N'), IC2Blocks.NUCLEAR_REACTOR, Character.valueOf('A'), Registry.ADVANCED_ELECTRIC_JETPACK});
        registry.addShapedIC2Recipe("compacted_nuclear_jetpack_1", new ItemStack((ItemLike)IC2Items.JETPACK_NUCLEAR_COMPACT), new Object[]{" B ", "XYX", "CVC", Character.valueOf('Y'), Registry.ADVANCED_NUCLEAR_JETPACK, Character.valueOf('C'), IC2Items.BAT_PACK, Character.valueOf('X'), new ItemStack((ItemLike)IC2Items.ADVANCED_CIRCUIT, 4), Character.valueOf('V'), Registry.ADVANCED_ELECTRIC_JETPACK, Character.valueOf('B'), IC2Items.LAP_PACK});
        registry.addShapedIC2Recipe("compacted_electric_jetpack", new ItemStack((ItemLike)IC2Items.JETPACK_ELECTRIC_COMPACT), new Object[]{" C ", "XYX", "VBV", Character.valueOf('C'), IC2Items.TRANSFORMER_UPGRADE, Character.valueOf('Y'), IC2Items.BAT_PACK, Character.valueOf('X'), new ItemStack((ItemLike)IC2Items.ADVANCED_CIRCUIT, 4), Character.valueOf('V'), Registry.ADVANCED_ELECTRIC_JETPACK, Character.valueOf('B'), IC2Items.LAP_PACK});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "gravitation_jetpack"), new ItemStack((ItemLike)Registry.GRAVITATION_JETPACK), new Object[]{"SJS", "GTG", "SUS", Character.valueOf('S'), Registry.SUPER_CONDUCTOR, Character.valueOf('J'), IC2Items.JETPACK_ELECTRIC_COMPACT, Character.valueOf('G'), Registry.GRAVITATION_ENGINE, Character.valueOf('T'), IC2Blocks.TRANSFORMER_EV, Character.valueOf('U'), Registry.ULTIMATE_LAPPACK});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "nuclear_gravitation_jetpack"), new ItemStack((ItemLike)Registry.NUCLEAR_GRAVITATION_JETPACK), new Object[]{"SJS", "GTG", "SUS", Character.valueOf('S'), Registry.SUPER_CONDUCTOR, Character.valueOf('J'), IC2Items.JETPACK_NUCLEAR_COMPACT, Character.valueOf('G'), Registry.GRAVITATION_ENGINE, Character.valueOf('T'), IC2Blocks.TRANSFORMER_EV, Character.valueOf('U'), Registry.ULTIMATE_LAPPACK});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "nuclear_gravitation_jetpack_upgrade"), new ItemStack((ItemLike)Registry.NUCLEAR_GRAVITATION_JETPACK), new Object[]{"CTC", "RNR", "CAC", Character.valueOf('C'), IC2Items.ADVANCED_CIRCUIT, Character.valueOf('T'), IC2Blocks.TRANSFORMER_EV, Character.valueOf('R'), IC2Blocks.REACTOR_CHAMBER, Character.valueOf('N'), IC2Blocks.NUCLEAR_REACTOR, Character.valueOf('A'), Registry.GRAVITATION_JETPACK});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "ultimate_lappack"), new ItemStack((ItemLike)Registry.ULTIMATE_LAPPACK), new Object[]{"LIL", "LQL", "LSL", Character.valueOf('L'), IC2Items.GLOWTRONIC_CRYSTAL, Character.valueOf('I'), IC2Items.PLATE_IRIDIUM, Character.valueOf('Q'), IC2Items.QUANTUM_PACK, Character.valueOf('S'), Registry.SUPER_CONDUCTOR});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "advanced_lappack"), new ItemStack((ItemLike)Registry.ADVANCED_LAPPACK), new Object[]{"L", "A", "C", Character.valueOf('L'), IC2Items.LAP_PACK, Character.valueOf('A'), IC2Items.ADVANCED_CIRCUIT, Character.valueOf('C'), IC2Items.LAPATRON_CRYSTAL});
        registry.addShapedIC2Recipe("quantum_pack", new ItemStack((ItemLike)IC2Items.QUANTUM_PACK), new Object[]{" X ", "YCY", " V ", Character.valueOf('Y'), IC2Items.PLATE_IRIDIUM, Character.valueOf('X'), IC2Items.ADVANCED_CIRCUIT, Character.valueOf('C'), Registry.ADVANCED_LAPPACK, Character.valueOf('V'), IC2Items.LAPATRON_CRYSTAL});
        ItemStack importTreeTap = new ItemStack((ItemLike)Registry.GRAVITOOL);
        CompoundTag nbt_import = importTreeTap.m_41784_();
        nbt_import.m_128379_("inv_import", true);
        registry.addShapelessRecipe(new ResourceLocation("gravisuit", "gravitool_upgrade"), importTreeTap, new Object[]{Registry.GRAVITOOL, Items.f_42155_, IC2Items.IMPORT_UPGRADE_SIMPLE});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "gravitool"), new ItemStack((ItemLike)Registry.GRAVITOOL), new Object[]{"CHC", "AEA", "WaT", new TreetapModifier(), Character.valueOf('C'), IC2Items.CARBON_PLATE, Character.valueOf('H'), IC2Items.ELECTRIC_HOE, Character.valueOf('A'), IC2Items.PLATE_ADVANCED_ALLOY, Character.valueOf('E'), IC2Items.ENERGY_CRYSTAL, Character.valueOf('W'), IC2Items.PRECISION_WRENCH, Character.valueOf('a'), IC2Items.ADVANCED_CIRCUIT, Character.valueOf('T'), IC2Items.ELECTRIC_TREETAP});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "vajra"), new ItemStack((ItemLike)Registry.VAJRA), new Object[]{"IMI", "DVC", "ALA", Character.valueOf('I'), IC2Items.PLATE_IRIDIUM, Character.valueOf('M'), IC2Items.MINING_LASER, Character.valueOf('D'), IC2Items.DRILL_ADVANCED, Character.valueOf('V'), Registry.VAJRA_CORE, Character.valueOf('C'), IC2Items.CHAINSAW_ADVANCED, Character.valueOf('A'), IC2Items.PLATE_ADVANCED_ALLOY, Character.valueOf('L'), IC2Items.LAPATRON_CRYSTAL});
        registry.addShapedRecipe(new ResourceLocation("gravisuit", "relocator"), new ItemStack((ItemLike)Registry.RELOCATOR), new Object[]{"MEM", "ETE", "MEM", Character.valueOf('M'), IC2Items.MEMORY_STICK, Character.valueOf('E'), Items.f_42584_, Character.valueOf('T'), IC2Items.PORTABLE_TELEPORTER});
    }

    public static class TreetapModifier
    implements IRecipeModifier {
        ItemStack original;

        public TreetapModifier() {
        }

        public TreetapModifier(JsonObject obj) {
        }

        public TreetapModifier(FriendlyByteBuf buffer) {
        }

        public void reset() {
            this.original = null;
        }

        public boolean isSlotValid(ItemStack input) {
            if (input.m_41720_() == IC2Items.ELECTRIC_TREETAP) {
                if (this.original != null) {
                    return false;
                }
                this.original = input.m_41777_();
            }
            return true;
        }

        public boolean isOutputItem(ItemStack input) {
            return false;
        }

        public ItemStack applyChanges(ItemStack input, boolean forDisplay) {
            CompoundTag nbt_import;
            if (this.original != null && (nbt_import = this.original.m_41784_()).m_128441_("inv_import") && nbt_import.m_128471_("inv_import")) {
                input.m_41784_().m_128379_("inv_import", true);
            }
            return input;
        }

        public void serialize(FriendlyByteBuf buffer) {
        }

        public JsonObject serialize() {
            return new JsonObject();
        }
    }
}


package ru.mot.ic2exfidelity.advancedsolars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

/** Exact item transformations and EU costs from Advanced Solars 2.0.2. */
public final class LegacyMolecularTransformerRecipes {
    private static List<Recipe> recipes;

    private LegacyMolecularTransformerRecipes() {
    }

    public static List<Recipe> all() {
        if (recipes == null) {
            recipes = Collections.unmodifiableList(createRecipes());
        }
        return recipes;
    }

    public static Recipe find(ItemStack stack) {
        if (stack == null || stack.m_41619_()) {
            return null;
        }
        for (Recipe recipe : all()) {
            if (stack.m_41720_() == recipe.input() && stack.m_41613_() >= recipe.inputCount()) {
                return recipe;
            }
        }
        return null;
    }

    public static int indexOf(Recipe recipe) {
        return recipe == null ? -1 : all().indexOf(recipe);
    }

    public static Recipe byIndex(int index) {
        List<Recipe> available = all();
        return index >= 0 && index < available.size() ? available.get(index) : null;
    }

    private static List<Recipe> createRecipes() {
        List<Recipe> result = new ArrayList<>();
        add(result, "nether_star", "minecraft:wither_skeleton_skull", 1,
                "minecraft:nether_star", 1, 250_000_000);
        add(result, "iridium_ore", "minecraft:iron_ingot", 1,
                "ic2:iridium_ore", 1, 9_000_000);
        add(result, "gunpowder", "minecraft:netherrack", 1,
                "minecraft:gunpowder", 2, 70_000);
        add(result, "gravel", "minecraft:sand", 1,
                "minecraft:gravel", 1, 50_000);
        add(result, "clay", "minecraft:dirt", 1,
                "minecraft:clay", 1, 50_000);
        add(result, "coal", "minecraft:charcoal", 1,
                "minecraft:coal", 1, 60_000);
        add(result, "sunnarium_piece", "minecraft:glowstone_dust", 1,
                "advanced_solars:sunnarium_part", 1, 1_000_000);
        add(result, "sunnarium", "minecraft:glowstone", 1,
                "advanced_solars:sunnarium", 1, 9_000_000);
        add(result, "glowstone", "minecraft:yellow_wool", 1,
                "minecraft:glowstone", 1, 500_000);
        add(result, "lapis_block", "minecraft:blue_wool", 1,
                "minecraft:lapis_block", 1, 500_000);
        add(result, "redstone_block", "minecraft:red_wool", 1,
                "minecraft:redstone_block", 1, 500_000);

        if (ModList.get().isLoaded("antimatter_shared")) {
            if (item("antimatter_shared:sapphire") != null) {
                add(result, "sapphire", "minecraft:lapis_lazuli", 1,
                        "antimatter_shared:sapphire", 1, 5_000_000);
            }
            if (item("antimatter_shared:ruby") != null) {
                add(result, "ruby", "minecraft:redstone", 1,
                        "antimatter_shared:ruby", 1, 5_000_000);
            }
            if (item("antimatter_shared:dust_chrome") != null
                    && item("antimatter_shared:dust_titanium") != null) {
                add(result, "chrome_dust", "antimatter_shared:dust_titanium", 1,
                        "antimatter_shared:dust_chrome", 1, 500_000);
            }
            if (item("antimatter_shared:ingot_chrome") != null
                    && item("antimatter_shared:ingot_titanium") != null) {
                add(result, "chrome_ingot", "antimatter_shared:ingot_titanium", 1,
                        "antimatter_shared:ingot_chrome", 1, 500_000);
            }
        }
        if (ModList.get().isLoaded("ae2") && item("ae2:certus_quartz_crystal") != null) {
            add(result, "certus_quartz", "minecraft:quartz", 1,
                    "ae2:certus_quartz_crystal", 1, 500_000);
        }

        add(result, "silver_ingot", "ic2:tin_ingot", 1,
                "ic2:silver_ingot", 1, 500_000);
        add(result, "gold_ingot", "ic2:silver_ingot", 1,
                "minecraft:gold_ingot", 1, 500_000);
        add(result, "industrial_diamond", "minecraft:coal", 1,
                "ic2:industrial_diamond", 1, 9_000_000);
        add(result, "diamond", "ic2:industrial_diamond", 1,
                "minecraft:diamond", 1, 1_000_000);
        return result;
    }

    private static void add(
            List<Recipe> recipes,
            String id,
            String input,
            int inputCount,
            String output,
            int outputCount,
            int energy) {
        Item inputItem = item(input);
        Item outputItem = item(output);
        if (inputItem == null || outputItem == null) {
            throw new IllegalStateException(
                    "Missing Molecular Transformer recipe item for " + id
                            + ": " + input + " -> " + output);
        }
        recipes.add(new Recipe(
                new ResourceLocation(LegacyAdvancedSolarsMod.MOD_ID, id),
                inputItem,
                inputCount,
                outputItem,
                outputCount,
                energy));
    }

    private static Item item(String id) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
    }

    public record Recipe(
            ResourceLocation id,
            Item input,
            int inputCount,
            Item output,
            int outputCount,
            int energy) {
        public ItemStack outputStack() {
            return new ItemStack(output, outputCount);
        }
    }
}

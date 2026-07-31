/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeType
 */
package ic2.core.ref;

import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.ICannerEnrichRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.core.IC2;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.recipe.v2.RecipeHolder;
import java.util.Collection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class Ic2RecipeTypes {
    public static final RecipeType<RecipeHolder<IRecipeInput, Collection<ItemStack>>> MACERATOR = Ic2RecipeTypes.register("macerator");
    public static final RecipeType<RecipeHolder<IRecipeInput, Collection<ItemStack>>> EXTRACTOR = Ic2RecipeTypes.register("extractor");
    public static final RecipeType<RecipeHolder<IRecipeInput, Collection<ItemStack>>> COMPRESSOR = Ic2RecipeTypes.register("compressor");
    public static final RecipeType<RecipeHolder<IRecipeInput, Collection<ItemStack>>> CENTRIFUGE = Ic2RecipeTypes.register("centrifuge");
    public static final RecipeType<RecipeHolder<IRecipeInput, Collection<ItemStack>>> BLOCK_CUTTER = Ic2RecipeTypes.register("block_cutter");
    public static final RecipeType<RecipeHolder<IRecipeInput, Collection<ItemStack>>> BLAST_FURNACE = Ic2RecipeTypes.register("blast_furnace");
    public static final RecipeType<RecipeHolder<IRecipeInput, Collection<ItemStack>>> METAL_FORMER_EXTRUDING = Ic2RecipeTypes.register("metal_former_extruding");
    public static final RecipeType<RecipeHolder<IRecipeInput, Collection<ItemStack>>> METAL_FORMER_CUTTING = Ic2RecipeTypes.register("metal_former_cutting");
    public static final RecipeType<RecipeHolder<IRecipeInput, Collection<ItemStack>>> METAL_FORMER_ROLLING = Ic2RecipeTypes.register("metal_former_rolling");
    public static final RecipeType<RecipeHolder<IRecipeInput, Collection<ItemStack>>> ORE_WASHER = Ic2RecipeTypes.register("ore_washing");
    public static final RecipeType<RecipeHolder<ICannerBottleRecipeManager.Input, ItemStack>> CANNER_BOTTLE = Ic2RecipeTypes.register("canner_bottle");
    public static final RecipeType<RecipeHolder<ICannerEnrichRecipeManager.Input, Ic2FluidStack>> CANNER_ENRICH = Ic2RecipeTypes.register("canner_enrich");

    public static void init() {
    }

    private static <T extends Recipe<?>> RecipeType<T> register(String string) {
        return IC2.envProxy.registerRecipeType(IC2.getIdentifier(string));
    }
}


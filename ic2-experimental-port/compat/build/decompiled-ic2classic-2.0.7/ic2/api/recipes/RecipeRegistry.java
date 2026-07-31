/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.recipes;

import ic2.api.recipes.registries.IAdvancedCraftingManager;
import ic2.api.recipes.registries.ICannerRecipeRegistry;
import ic2.api.recipes.registries.IElectrolyzerRecipeList;
import ic2.api.recipes.registries.IFluidFuelRegistry;
import ic2.api.recipes.registries.IFoodCanRegistry;
import ic2.api.recipes.registries.IFusionRecipeList;
import ic2.api.recipes.registries.IIngredientRegistry;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.recipes.registries.IPotionBrewRegistry;
import ic2.api.recipes.registries.IRareEarthRegistry;
import ic2.api.recipes.registries.IRecyclerRecipeList;
import ic2.api.recipes.registries.IScrapBoxRegistry;
import ic2.api.recipes.registries.IUUMatterRegistry;
import ic2.api.util.SidedObject;

public final class RecipeRegistry {
    public static IIngredientRegistry INGREDIENTS;
    public static IAdvancedCraftingManager CRAFTING;
    public static final SidedObject<IUUMatterRegistry> UU_SHAPES;
    public static final SidedObject<IMachineRecipeList> FURNACE;
    public static final SidedObject<IMachineRecipeList> BLAST_FURNACE;
    public static final SidedObject<IMachineRecipeList> SMOKER;
    public static final SidedObject<IMachineRecipeList> MACERATOR;
    public static final SidedObject<IMachineRecipeList> EXTRACTOR;
    public static final SidedObject<IMachineRecipeList> COMPRESSOR;
    public static final SidedObject<IRecyclerRecipeList> RECYCLER;
    public static final SidedObject<IMachineRecipeList> SAWMILL;
    public static final SidedObject<IRareEarthRegistry> RARE_EARTH;
    public static final SidedObject<ICannerRecipeRegistry> CANNER;
    public static final SidedObject<IElectrolyzerRecipeList> ELECTROLYZER;
    public static final SidedObject<IMachineRecipeList> MIXING_FURNACE;
    public static final SidedObject<IScrapBoxRegistry> SCRAP_BOX;
    public static final SidedObject<IMachineRecipeList> MASS_FABRICATOR;
    public static IFoodCanRegistry CAN_EFFECTS;
    public static final SidedObject<IPotionBrewRegistry> POTION_BREWING;
    public static final SidedObject<IFluidFuelRegistry> FLUID_FUELS;
    public static final SidedObject<IFusionRecipeList> FUSION_REACTOR;

    static {
        UU_SHAPES = new SidedObject();
        FURNACE = new SidedObject();
        BLAST_FURNACE = new SidedObject();
        SMOKER = new SidedObject();
        MACERATOR = new SidedObject();
        EXTRACTOR = new SidedObject();
        COMPRESSOR = new SidedObject();
        RECYCLER = new SidedObject();
        SAWMILL = new SidedObject();
        RARE_EARTH = new SidedObject();
        CANNER = new SidedObject();
        ELECTROLYZER = new SidedObject();
        MIXING_FURNACE = new SidedObject();
        SCRAP_BOX = new SidedObject();
        MASS_FABRICATOR = new SidedObject();
        POTION_BREWING = new SidedObject();
        FLUID_FUELS = new SidedObject();
        FUSION_REACTOR = new SidedObject();
    }
}


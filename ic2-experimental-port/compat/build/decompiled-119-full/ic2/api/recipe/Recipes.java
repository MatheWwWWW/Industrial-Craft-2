/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.api.recipe;

import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.ICannerEnrichRecipeManager;
import ic2.api.recipe.IElectrolyzerRecipeManager;
import ic2.api.recipe.IEmptyFluidContainerRecipeManager;
import ic2.api.recipe.IFermenterRecipeManager;
import ic2.api.recipe.IFillFluidContainerRecipeManager;
import ic2.api.recipe.IFluidHeatManager;
import ic2.api.recipe.ILiquidHeatExchangerManager;
import ic2.api.recipe.IListRecipeManager;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.IRecipeInputFactory;
import ic2.api.recipe.IScrapboxManager;
import ic2.api.recipe.ISemiFluidFuelManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Recipes {
    public static IRecipeInputFactory inputFactory;
    public static IMachineRecipeManager<ItemStack, ItemStack, ItemStack> furnace;
    public static IGetter<IBasicMachineRecipeManager> macerator;
    public static IGetter<IBasicMachineRecipeManager> extractor;
    public static IGetter<IBasicMachineRecipeManager> compressor;
    public static IGetter<IBasicMachineRecipeManager> centrifuge;
    public static IGetter<IBasicMachineRecipeManager> blockcutter;
    public static IGetter<IBasicMachineRecipeManager> blastfurnace;
    public static IBasicMachineRecipeManager recycler;
    public static IGetter<IBasicMachineRecipeManager> metalformerExtruding;
    public static IGetter<IBasicMachineRecipeManager> metalformerCutting;
    public static IGetter<IBasicMachineRecipeManager> metalformerRolling;
    public static IGetter<IBasicMachineRecipeManager> oreWashing;
    public static IGetter<ICannerBottleRecipeManager> cannerBottle;
    public static IGetter<ICannerEnrichRecipeManager> cannerEnrich;
    public static IElectrolyzerRecipeManager electrolyzer;
    public static IFermenterRecipeManager fermenter;
    public static IMachineRecipeManager<IRecipeInput, Integer, ItemStack> matterAmplifier;
    public static IScrapboxManager scrapboxDrops;
    public static IListRecipeManager recyclerBlacklist;
    public static IListRecipeManager recyclerWhitelist;
    public static ISemiFluidFuelManager semiFluidGenerator;
    public static IFluidHeatManager fluidHeatGenerator;
    public static ILiquidHeatExchangerManager liquidCooldownManager;
    public static ILiquidHeatExchangerManager liquidHeatupManager;
    public static IEmptyFluidContainerRecipeManager emptyFluidContainer;
    public static IFillFluidContainerRecipeManager fillFluidContainer;

    public static interface IGetter<T> {
        public T get(Level var1);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.recipe.IRecipeWrapper
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.jeiIntegration.recipe.machine;

import ic2.api.item.IBlockCuttingBlade;
import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.ICannerEnrichRecipeManager;
import ic2.api.recipe.IElectrolyzerRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.core.item.type.BlockCuttingBladeType;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.core.util.StackUtil;
import ic2.jeiIntegration.recipe.machine.AdvancedIORecipeWrapper;
import ic2.jeiIntegration.recipe.machine.CannerCanningWrapper;
import ic2.jeiIntegration.recipe.machine.CannerEnrichmentWrapper;
import ic2.jeiIntegration.recipe.machine.ElectrolyzerWrapper;
import ic2.jeiIntegration.recipe.machine.IORecipeCategory;
import ic2.jeiIntegration.recipe.machine.IORecipeWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public interface IRecipeWrapperGenerator<T> {
    public static final IRecipeWrapperGenerator<IBasicMachineRecipeManager> basicMachine = new IRecipeWrapperGenerator<IBasicMachineRecipeManager>(){

        @Override
        public List<IRecipeWrapper> getRecipeList(IORecipeCategory<IBasicMachineRecipeManager> category) {
            ArrayList<IRecipeWrapper> recipes = new ArrayList<IRecipeWrapper>();
            for (MachineRecipe<IRecipeInput, Collection<ItemStack>> machineRecipe : ((IBasicMachineRecipeManager)category.recipeManager).getRecipes()) {
                recipes.add((IRecipeWrapper)new IORecipeWrapper(machineRecipe, category));
            }
            return recipes;
        }
    };
    public static final IRecipeWrapperGenerator<IBasicMachineRecipeManager> recycler = new IRecipeWrapperGenerator<IBasicMachineRecipeManager>(){

        @Override
        public List<IRecipeWrapper> getRecipeList(IORecipeCategory<IBasicMachineRecipeManager> category) {
            IRecipeInput input = new IRecipeInput(){

                @Override
                public boolean matches(ItemStack subject) {
                    return StackUtil.checkItemEquality(subject, BlockName.te.getItemStack(TeBlock.recycler));
                }

                @Override
                public List<ItemStack> getInputs() {
                    return Collections.singletonList(BlockName.te.getItemStack(TeBlock.recycler));
                }

                @Override
                public int getAmount() {
                    return 1;
                }
            };
            return Collections.singletonList(new IORecipeWrapper(new MachineRecipe<IRecipeInput, Collection<ItemStack>>(input, Collections.singletonList(ItemName.crafting.getItemStack(CraftingItemType.scrap))), category));
        }
    };
    public static final IRecipeWrapperGenerator<IBasicMachineRecipeManager> blockCutter = new IRecipeWrapperGenerator<IBasicMachineRecipeManager>(){
        private final List<ItemStack> candidates = Arrays.asList(ItemName.block_cutting_blade.getItemStack(BlockCuttingBladeType.iron), ItemName.block_cutting_blade.getItemStack(BlockCuttingBladeType.steel), ItemName.block_cutting_blade.getItemStack(BlockCuttingBladeType.diamond));

        @Override
        public List<IRecipeWrapper> getRecipeList(IORecipeCategory<IBasicMachineRecipeManager> category) {
            ArrayList<IRecipeWrapper> list = new ArrayList<IRecipeWrapper>();
            for (MachineRecipe<IRecipeInput, Collection<ItemStack>> machineRecipe : ((IBasicMachineRecipeManager)category.recipeManager).getRecipes()) {
                list.add((IRecipeWrapper)new AdvancedIORecipeWrapper(machineRecipe, this.getInput(this.getHardness(machineRecipe.getMetaData())), category));
            }
            return list;
        }

        private int getHardness(NBTTagCompound metadata) {
            if (metadata == null) {
                return Integer.MAX_VALUE;
            }
            return metadata.func_74762_e("hardness");
        }

        private IRecipeInput getInput(final int hardness) {
            return new IRecipeInput(){

                @Override
                public boolean matches(ItemStack subject) {
                    return subject != null && subject.func_77973_b() instanceof IBlockCuttingBlade && ((IBlockCuttingBlade)subject.func_77973_b()).getHardness(subject) > hardness;
                }

                @Override
                public List<ItemStack> getInputs() {
                    ArrayList<ItemStack> list = new ArrayList<ItemStack>(candidates.size());
                    for (ItemStack stack : candidates) {
                        if (((IBlockCuttingBlade)stack.func_77973_b()).getHardness(stack) < hardness) continue;
                        list.add(stack);
                    }
                    return list;
                }

                @Override
                public int getAmount() {
                    return 1;
                }
            };
        }
    };
    public static final IRecipeWrapperGenerator<IElectrolyzerRecipeManager> electrolyzer = new IRecipeWrapperGenerator<IElectrolyzerRecipeManager>(){

        @Override
        public List<IRecipeWrapper> getRecipeList(IORecipeCategory<IElectrolyzerRecipeManager> category) {
            ArrayList<IRecipeWrapper> recipes = new ArrayList<IRecipeWrapper>();
            for (Map.Entry<String, IElectrolyzerRecipeManager.ElectrolyzerRecipe> recipe : ((IElectrolyzerRecipeManager)category.recipeManager).getRecipeMap().entrySet()) {
                Fluid input = FluidRegistry.getFluid((String)recipe.getKey());
                if (input == null) continue;
                recipes.add((IRecipeWrapper)new ElectrolyzerWrapper(new FluidStack(input, recipe.getValue().inputAmount), recipe.getValue().outputs, category));
            }
            return recipes;
        }
    };
    public static final IRecipeWrapperGenerator<ICannerEnrichRecipeManager> cannerEnrichment = new IRecipeWrapperGenerator<ICannerEnrichRecipeManager>(){

        @Override
        public List<IRecipeWrapper> getRecipeList(IORecipeCategory<ICannerEnrichRecipeManager> category) {
            ArrayList<IRecipeWrapper> recipes = new ArrayList<IRecipeWrapper>();
            for (MachineRecipe recipe : ((ICannerEnrichRecipeManager)category.recipeManager).getRecipes()) {
                recipes.add((IRecipeWrapper)new CannerEnrichmentWrapper((ICannerEnrichRecipeManager.Input)recipe.getInput(), (FluidStack)recipe.getOutput(), category));
            }
            return recipes;
        }
    };
    public static final IRecipeWrapperGenerator<ICannerBottleRecipeManager> cannerBottling = new IRecipeWrapperGenerator<ICannerBottleRecipeManager>(){

        @Override
        public List<IRecipeWrapper> getRecipeList(IORecipeCategory<ICannerBottleRecipeManager> category) {
            ArrayList<IRecipeWrapper> recipes = new ArrayList<IRecipeWrapper>();
            for (MachineRecipe recipe : ((ICannerBottleRecipeManager)category.recipeManager).getRecipes()) {
                recipes.add((IRecipeWrapper)new CannerCanningWrapper((ICannerBottleRecipeManager.Input)recipe.getInput(), (ItemStack)recipe.getOutput(), category));
            }
            return recipes;
        }
    };

    public List<IRecipeWrapper> getRecipeList(IORecipeCategory<T> var1);
}


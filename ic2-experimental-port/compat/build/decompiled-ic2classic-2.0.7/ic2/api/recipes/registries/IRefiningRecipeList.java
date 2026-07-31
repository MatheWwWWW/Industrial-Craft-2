/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.registries.RegistryObject
 */
package ic2.api.recipes.registries;

import ic2.api.recipes.ingridients.inputs.EmptyInput;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.ItemInput;
import ic2.api.recipes.ingridients.inputs.ItemStackInput;
import ic2.api.recipes.ingridients.inputs.ItemTagInput;
import ic2.api.recipes.ingridients.recipes.IFluidRecipeOutput;
import ic2.api.recipes.ingridients.recipes.RangeFluidOutput;
import ic2.api.recipes.ingridients.recipes.SimpleFluidOutput;
import ic2.api.recipes.misc.RecipeFlags;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.recipes.registries.IListenableRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.RegistryObject;

public interface IRefiningRecipeList
extends IListenableRegistry<IRefiningRecipeList> {
    default public Builder addIC2Recipe(String id) {
        return new Builder(this, new ResourceLocation("ic2", id));
    }

    default public Builder addRecipe(ResourceLocation id) {
        return new Builder(this, id);
    }

    public void addRecipe(ResourceLocation var1, FluidStack var2, FluidStack var3, IInput var4, IFluidRecipeOutput var5);

    public FluidRecipe getRecipe(ResourceLocation var1);

    public FluidRecipe getRecipe(ItemStack var1, FluidStack var2, FluidStack var3, boolean var4);

    public FluidRecipe getRecipe(Predicate<FluidRecipe> var1);

    public FluidRecipe removeRecipe(ResourceLocation var1, boolean var2);

    public List<FluidRecipe> getAllRecipes();

    public boolean isValidCatalyst(ItemStack var1);

    public boolean isValidFirstFluid(FluidStack var1);

    public boolean isValidSecondFluid(FluidStack var1);

    public static class Builder {
        IRefiningRecipeList registry;
        ResourceLocation id;
        FluidStack firstInput = FluidStack.EMPTY;
        FluidStack secondInput = FluidStack.EMPTY;
        IInput catalyst = EmptyInput.INSTANCE;
        CompoundTag data;

        public Builder(IRefiningRecipeList registry, ResourceLocation id) {
            this.registry = registry;
            this.id = id;
        }

        public Builder catalyst(ItemLike provider) {
            this.catalyst = new ItemInput(provider);
            return this;
        }

        public Builder catalyst(ItemStack stack) {
            this.catalyst = new ItemStackInput(stack);
            return this;
        }

        public Builder catalyst(TagKey<Item> tag) {
            this.catalyst = new ItemTagInput(tag);
            return this;
        }

        public Builder catalyst(RegistryObject<Item> provider) {
            return this.catalyst((ItemLike)provider.get());
        }

        public Builder catalyst(IInput input) {
            this.catalyst = input;
            return this;
        }

        public Builder mainInput(RegistryObject<Fluid> fluid, int amount) {
            return this.mainInput((Fluid)fluid.get(), amount);
        }

        public Builder mainInput(Fluid fluid, int amount) {
            this.firstInput = new FluidStack(fluid, amount);
            return this;
        }

        public Builder mainInput(FluidStack fluid) {
            this.firstInput = fluid;
            return this;
        }

        public Builder secondInput(RegistryObject<Fluid> fluid, int amount) {
            return this.secondInput((Fluid)fluid.get(), amount);
        }

        public Builder secondInput(Fluid fluid, int amount) {
            this.secondInput = new FluidStack(fluid, amount);
            return this;
        }

        public Builder secondInput(FluidStack fluid) {
            this.secondInput = fluid;
            return this;
        }

        private void checkNBT() {
            if (this.data == null) {
                this.data = new CompoundTag();
            }
        }

        public Builder addNBT(String id, Tag nbt) {
            this.checkNBT();
            this.data.m_128365_(id, nbt);
            return this;
        }

        public Builder addNBT(CompoundTag data) {
            this.checkNBT();
            data.m_128391_(data);
            return this;
        }

        public Builder setNBT(CompoundTag data) {
            this.data = data;
            return this;
        }

        public Builder addFlag(RecipeFlags flag, boolean value) {
            this.checkNBT();
            flag.applyFlag(this.data, value);
            return this;
        }

        public Builder addMod(RecipeMods mod, int value) {
            this.checkNBT();
            mod.create(this.data, value);
            return this;
        }

        public Builder addMod(RecipeMods mod, double value) {
            this.checkNBT();
            mod.create(this.data, value);
            return this;
        }

        public Builder addMod(RecipeMods mod, int exactValue, double modValue) {
            this.checkNBT();
            mod.create(this.data, modValue, exactValue);
            return this;
        }

        public void buildSimple(ItemStack output) {
            this.registry.addRecipe(this.id, this.firstInput, this.secondInput, this.catalyst, new SimpleFluidOutput((List<ItemStack>)ObjectLists.singleton((Object)output), this.data));
        }

        public void buildSimple(ItemStack output, FluidStack ... fluidOutputs) {
            this.registry.addRecipe(this.id, this.firstInput, this.secondInput, this.catalyst, new SimpleFluidOutput((List<ItemStack>)ObjectLists.singleton((Object)output), (List<FluidStack>)ObjectArrayList.wrap((Object[])fluidOutputs), this.data));
        }

        public void buildSimple(ItemStack ... itemOutputs) {
            this.registry.addRecipe(this.id, this.firstInput, this.secondInput, this.catalyst, new SimpleFluidOutput((List<ItemStack>)ObjectArrayList.wrap((Object[])itemOutputs), this.data));
        }

        public void buildSimple(FluidStack ... fluidOutputs) {
            this.registry.addRecipe(this.id, this.firstInput, this.secondInput, this.catalyst, new SimpleFluidOutput(Collections.emptyList(), (List<FluidStack>)ObjectArrayList.wrap((Object[])fluidOutputs), this.data));
        }

        public void buildSimple(List<ItemStack> itemOutputs, List<FluidStack> fluidOutputs) {
            this.registry.addRecipe(this.id, this.firstInput, this.secondInput, this.catalyst, new SimpleFluidOutput(itemOutputs, fluidOutputs, this.data));
        }

        public void buildRange(ItemStack itemOutput, int minItem, int maxItem) {
            this.registry.addRecipe(this.id, this.firstInput, this.secondInput, this.catalyst, new RangeFluidOutput(itemOutput, minItem, maxItem, this.data));
        }

        public void buildRange(FluidStack fluidOutput, int minFluid, int maxFluid) {
            this.registry.addRecipe(this.id, this.firstInput, this.secondInput, this.catalyst, new RangeFluidOutput(fluidOutput, minFluid, maxFluid, this.data));
        }

        public void buildRange(ItemStack itemOutput, int minItem, int maxItem, FluidStack fluidOutput, int minFluid, int maxFluid) {
            this.registry.addRecipe(this.id, this.firstInput, this.secondInput, this.catalyst, new RangeFluidOutput(itemOutput, minItem, maxItem, fluidOutput, minFluid, maxFluid, this.data));
        }

        public void buildCustom(Function<CompoundTag, IFluidRecipeOutput> mapper) {
            this.registry.addRecipe(this.id, this.firstInput, this.secondInput, this.catalyst, mapper.apply(this.data));
        }
    }

    public static class FluidRecipe {
        ResourceLocation location;
        FluidStack firstTank;
        FluidStack secondTank;
        IInput itemInput;
        IFluidRecipeOutput output;

        public FluidRecipe(ResourceLocation location, FluidStack firstTank, FluidStack secondTank, IInput itemInput, IFluidRecipeOutput output) {
            this.location = location;
            this.firstTank = firstTank;
            this.secondTank = secondTank;
            this.itemInput = itemInput;
            this.output = output;
        }

        public ResourceLocation getLocation() {
            return this.location;
        }

        public FluidStack getFirstTank() {
            return this.firstTank;
        }

        public FluidStack getSecondTank() {
            return this.secondTank;
        }

        public IInput getItemInput() {
            return this.itemInput;
        }

        public IFluidRecipeOutput getOutput() {
            return this.output;
        }

        public FluidRecipe invert() {
            return new FluidRecipe(new ResourceLocation(this.location.m_135827_(), this.location.m_135827_() + "_inverted"), this.secondTank, this.firstTank, this.itemInput, this.output);
        }
    }
}


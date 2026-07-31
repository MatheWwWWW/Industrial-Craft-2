/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipes.registries;

import ic2.api.recipes.RecipeRegistry;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.INullableInput;
import ic2.api.recipes.ingridients.recipes.ChanceRecipeOutput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.ingridients.recipes.RangeRecipeOutput;
import ic2.api.recipes.ingridients.recipes.SimpleRecipeOutput;
import ic2.api.recipes.registries.IListenableRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface IMachineRecipeList
extends IListenableRegistry<IMachineRecipeList> {
    public void addRecipe(RecipeEntry var1);

    default public void addRecipe(ResourceLocation id, IRecipeOutput output, IInput ... inputs) {
        this.addRecipe(new RecipeEntry(id, output, inputs));
    }

    default public void addSimpleRecipe(ResourceLocation id, ItemStack output, Object ... inputs) {
        this.addRecipe(id, (IRecipeOutput)new SimpleRecipeOutput((List<ItemStack>)ObjectLists.singleton((Object)output)), inputs);
    }

    default public void addSimpleRecipe(ResourceLocation id, ItemStack output, CompoundTag data, Object ... inputs) {
        this.addRecipe(id, (IRecipeOutput)new SimpleRecipeOutput((List<ItemStack>)ObjectLists.singleton((Object)output), data), inputs);
    }

    default public void addXPRecipe(ResourceLocation id, ItemStack output, float xp, Object ... inputs) {
        this.addRecipe(id, (IRecipeOutput)new SimpleRecipeOutput((List<ItemStack>)ObjectLists.singleton((Object)output), xp), inputs);
    }

    default public void addXPRecipe(ResourceLocation id, ItemStack output, float xp, CompoundTag data, Object ... inputs) {
        this.addRecipe(id, (IRecipeOutput)new SimpleRecipeOutput((List<ItemStack>)ObjectLists.singleton((Object)output), data, xp), inputs);
    }

    default public void addChanceRecipe(ResourceLocation id, ItemStack output, float xp, float chance, Object ... inputs) {
        this.addRecipe(id, (IRecipeOutput)new ChanceRecipeOutput((List<ItemStack>)ObjectLists.singleton((Object)output), xp, chance), inputs);
    }

    default public void addChanceRecipe(ResourceLocation id, ItemStack output, float xp, float chance, CompoundTag data, Object ... inputs) {
        this.addRecipe(id, (IRecipeOutput)new ChanceRecipeOutput((List<ItemStack>)ObjectLists.singleton((Object)output), xp, data, chance), inputs);
    }

    default public void addRangeRecipe(ResourceLocation id, ItemStack output, int minValue, int maxValue, Object ... inputs) {
        this.addRecipe(id, (IRecipeOutput)new RangeRecipeOutput(output, minValue, maxValue), inputs);
    }

    default public void addRangeRecipe(ResourceLocation id, ItemStack output, int minValue, int maxValue, CompoundTag data, Object ... inputs) {
        this.addRecipe(id, (IRecipeOutput)new RangeRecipeOutput(output, data, minValue, maxValue), inputs);
    }

    default public void addRecipe(ResourceLocation id, IRecipeOutput output, Object ... inputs) {
        this.addRecipe(id, output, IMachineRecipeList.convertInputs(inputs));
    }

    default public void addIC2SimpleRecipe(String id, ItemStack output, Object ... inputs) {
        this.addSimpleRecipe(new ResourceLocation("ic2", id), output, inputs);
    }

    default public void addIC2SimpleRecipe(String id, ItemStack output, CompoundTag data, Object ... inputs) {
        this.addSimpleRecipe(new ResourceLocation("ic2", id), output, data, inputs);
    }

    default public void addIC2XPRecipe(String id, ItemStack output, float xp, Object ... inputs) {
        this.addXPRecipe(new ResourceLocation("ic2", id), output, xp, inputs);
    }

    default public void addIC2XPRecipe(String id, ItemStack output, float xp, CompoundTag data, Object ... inputs) {
        this.addXPRecipe(new ResourceLocation("ic2", id), output, xp, data, inputs);
    }

    default public void addIC2ChanceRecipe(String id, ItemStack output, float xp, float chance, Object ... inputs) {
        this.addChanceRecipe(new ResourceLocation("ic2", id), output, xp, chance, inputs);
    }

    default public void addIC2ChanceRecipe(String id, ItemStack output, float xp, float chance, CompoundTag data, Object ... inputs) {
        this.addChanceRecipe(new ResourceLocation("ic2", id), output, xp, chance, data, inputs);
    }

    default public void addIC2RangeRecipe(String id, ItemStack output, int minValue, int maxValue, Object ... inputs) {
        this.addRangeRecipe(new ResourceLocation("ic2", id), output, minValue, maxValue, inputs);
    }

    default public void addIC2RangeRecipe(String id, ItemStack output, int minValue, int maxValue, CompoundTag data, Object ... inputs) {
        this.addRangeRecipe(new ResourceLocation("ic2", id), output, minValue, maxValue, data, inputs);
    }

    default public void addIC2Recipe(String id, IRecipeOutput output, Object ... inputs) {
        this.addRecipe(new ResourceLocation("ic2", id), output, inputs);
    }

    default public boolean hasRecipeForItem(ItemStack stack) {
        return this.getRecipe(stack, false) != null;
    }

    public RecipeEntry getRecipe(ResourceLocation var1);

    public RecipeEntry getRecipe(ItemStack var1, boolean var2);

    public RecipeEntry getRecipe(Predicate<RecipeEntry> var1);

    public RecipeEntry removeRecipe(ResourceLocation var1);

    public List<ResourceLocation> getAllRecipes();

    public List<RecipeEntry> getAllEntries();

    public static IInput[] convertInputs(Object ... inputs) {
        ObjectArrayList result = new ObjectArrayList();
        for (int i = 0; i < inputs.length; ++i) {
            IInput input = RecipeRegistry.INGREDIENTS.createInputFrom(inputs[i]);
            if (input == null) continue;
            result.add(input);
        }
        return result.toArray(new IInput[result.size()]);
    }

    public static class RecipeEntry {
        final ResourceLocation location;
        final IInput[] inputs;
        final IRecipeOutput output;
        final boolean nullInput;

        public RecipeEntry(ResourceLocation location, IRecipeOutput output, IInput ... inputs) {
            this.location = location;
            this.inputs = inputs;
            this.output = output;
            for (int i = 0; i < inputs.length; ++i) {
                if (!(inputs[i] instanceof INullableInput)) continue;
                this.nullInput = true;
                return;
            }
            this.nullInput = false;
        }

        public ResourceLocation getLocation() {
            return this.location;
        }

        public IInput[] getInputs() {
            return this.inputs;
        }

        public IRecipeOutput getOutput() {
            return this.output;
        }

        public boolean hasNullInput() {
            return this.nullInput;
        }
    }
}


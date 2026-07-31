/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  net.minecraft.advancements.critereon.EntityPredicate$Composite
 *  net.minecraft.advancements.critereon.InventoryChangeTrigger$TriggerInstance
 *  net.minecraft.advancements.critereon.ItemPredicate
 *  net.minecraft.advancements.critereon.ItemPredicate$Builder
 *  net.minecraft.advancements.critereon.MinMaxBounds$Ints
 *  net.minecraft.data.CachedOutput
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.DataGenerator$PathProvider
 *  net.minecraft.data.DataGenerator$Target
 *  net.minecraft.data.DataProvider
 *  net.minecraft.data.recipes.FinishedRecipe
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 *  org.slf4j.Logger
 */
package ic2.data.recipe.helper;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import ic2.core.fluid.FluidHandler;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.recipe.input.RecipeInputIngredient;
import ic2.data.Ic2DataGenerators;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.function.Consumer;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import org.slf4j.Logger;

public abstract class IC2RecipeProvider
implements DataProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;
    private final DataGenerator.PathProvider recipesPathResolver;
    private final DataGenerator.PathProvider advancementsPathResolver;

    public IC2RecipeProvider(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
        this.recipesPathResolver = dataGenerator.m_236036_(DataGenerator.Target.DATA_PACK, "recipes");
        this.advancementsPathResolver = dataGenerator.m_236036_(DataGenerator.Target.DATA_PACK, "advancements");
    }

    protected abstract void generate(Consumer<FinishedRecipe> var1);

    protected static RecipeInputIngredient tagInput(TagKey<Item> tagKey) {
        return IC2RecipeProvider.tagInput(tagKey, 1);
    }

    protected static RecipeInputIngredient tagInput(TagKey<Item> tagKey, int n) {
        return new RecipeInputIngredient(Ingredient.m_204132_(tagKey), n);
    }

    protected static RecipeInputIngredient itemInput(ItemLike ... itemLikeArray) {
        return new RecipeInputIngredient(Ingredient.m_43929_((ItemLike[])itemLikeArray), 1);
    }

    protected static Ic2FluidStack bucket(Fluid fluid) {
        return FluidHandler.createFluidStackMb(fluid, 1000, null);
    }

    public String m_6055_() {
        return this.getClass().getSimpleName();
    }

    public void m_213708_(CachedOutput cachedOutput) {
        HashSet hashSet = Sets.newHashSet();
        this.generate(finishedRecipe -> {
            if (!hashSet.add(finishedRecipe.m_6445_())) {
                throw new IllegalStateException("Duplicate recipe " + finishedRecipe.m_6445_());
            }
            IC2RecipeProvider.saveRecipe(cachedOutput, finishedRecipe.m_125966_(), this.recipesPathResolver.m_236048_(finishedRecipe.m_6445_()));
            JsonObject jsonObject = finishedRecipe.m_5860_();
            if (jsonObject != null) {
                IC2RecipeProvider.saveRecipeAdvancement(cachedOutput, jsonObject, this.advancementsPathResolver.m_236048_(finishedRecipe.m_6448_()));
            }
        });
    }

    private static void saveRecipe(CachedOutput cachedOutput, JsonObject jsonObject, Path path) {
        try {
            Ic2DataGenerators.saveJsonPreserveOrder(GSON, cachedOutput, (JsonElement)jsonObject, path);
        }
        catch (IOException iOException) {
            LOGGER.error("Couldn't save recipe {}", (Object)path, (Object)iOException);
        }
    }

    private static void saveRecipeAdvancement(CachedOutput cachedOutput, JsonObject jsonObject, Path path) {
        try {
            DataProvider.m_236072_((CachedOutput)cachedOutput, (JsonElement)jsonObject, (Path)path);
        }
        catch (IOException iOException) {
            LOGGER.error("Couldn't save recipe advancement {}", (Object)path, (Object)iOException);
        }
    }

    protected static InventoryChangeTrigger.TriggerInstance conditionsFromItem(MinMaxBounds.Ints ints, ItemLike itemLike) {
        return IC2RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.m_45068_().m_151445_(new ItemLike[]{itemLike}).m_151443_(ints).m_45077_());
    }

    protected static InventoryChangeTrigger.TriggerInstance conditionsFromItem(ItemLike itemLike) {
        return IC2RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.m_45068_().m_151445_(new ItemLike[]{itemLike}).m_45077_());
    }

    protected static InventoryChangeTrigger.TriggerInstance conditionsFromTag(TagKey<Item> tagKey) {
        return IC2RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.m_45068_().m_204145_(tagKey).m_45077_());
    }

    protected static InventoryChangeTrigger.TriggerInstance conditionsFromItemPredicates(ItemPredicate ... itemPredicateArray) {
        return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, itemPredicateArray);
    }
}


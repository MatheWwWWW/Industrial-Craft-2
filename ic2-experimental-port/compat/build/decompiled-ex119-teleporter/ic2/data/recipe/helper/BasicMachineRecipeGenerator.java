/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.core.Registry
 *  net.minecraft.data.recipes.FinishedRecipe
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 *  org.jetbrains.annotations.Nullable
 */
package ic2.data.recipe.helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.core.recipe.v2.RecipeIo;
import ic2.core.ref.Ic2RecipeSerializers;
import ic2.data.recipe.helper.MachineRecipeJsonProvider;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

public final class BasicMachineRecipeGenerator {
    private final Consumer<FinishedRecipe> exporter;
    private final RecipeSerializer<?> recipeSerializer;
    private final boolean requiresMeta;
    @Nullable
    private Consumer<JsonObject> currentMeta = null;

    public BasicMachineRecipeGenerator(Consumer<FinishedRecipe> consumer, RecipeSerializer<?> recipeSerializer) {
        this(consumer, recipeSerializer, false);
    }

    public BasicMachineRecipeGenerator(Consumer<FinishedRecipe> consumer, RecipeSerializer<?> recipeSerializer, boolean bl) {
        this.exporter = consumer;
        this.recipeSerializer = recipeSerializer;
        this.requiresMeta = bl;
    }

    public void add(ItemLike itemLike, int n, ItemLike itemLike2) {
        this.add(itemLike, n, itemLike2, 1);
    }

    public void add(ItemLike itemLike, int n, ItemLike itemLike2, int n2) {
        this.add(itemLike, n, new ItemStack(itemLike2, n2));
    }

    public void add(ItemLike itemLike, int n, ItemStack ... itemStackArray) {
        this.add("%s_to_%s".formatted(BasicMachineRecipeGenerator.path(itemLike), BasicMachineRecipeGenerator.path((ItemLike)itemStackArray[0].m_41720_())), jsonObject -> {
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("item", Registry.f_122827_.m_7981_((Object)itemLike.m_5456_()).toString());
            if (n != 1) {
                jsonObject2.addProperty("count", (Number)n);
            }
            jsonObject.add("ingredient", (JsonElement)jsonObject2);
            BasicMachineRecipeGenerator.writeOutput(jsonObject, itemStackArray);
        });
    }

    public void add(TagKey<Item> tagKey, int n, ItemLike itemLike) {
        this.add(tagKey, n, itemLike, 1);
    }

    public void add(TagKey<Item> tagKey, int n, ItemLike itemLike, int n2) {
        this.add(tagKey, n, new ItemStack(itemLike, n2));
    }

    public void add(TagKey<Item> tagKey, int n, ItemStack ... itemStackArray) {
        this.add("%s_to_%s".formatted(BasicMachineRecipeGenerator.path(tagKey), BasicMachineRecipeGenerator.path((ItemLike)itemStackArray[0].m_41720_())), jsonObject -> {
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("tag", tagKey.f_203868_().toString());
            if (n != 1) {
                jsonObject2.addProperty("count", (Number)n);
            }
            jsonObject.add("ingredient", (JsonElement)jsonObject2);
            BasicMachineRecipeGenerator.writeOutput(jsonObject, itemStackArray);
        });
    }

    public void add(Fluid fluid, int n, ItemLike itemLike) {
        this.add("%s_to_%s".formatted(BasicMachineRecipeGenerator.path(fluid), BasicMachineRecipeGenerator.path(itemLike)), jsonObject -> {
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("fluid", Registry.f_122822_.m_7981_((Object)fluid).toString());
            jsonObject2.addProperty("amount", (Number)n);
            jsonObject.add("ingredient", (JsonElement)jsonObject2);
            BasicMachineRecipeGenerator.writeOutput(jsonObject, new ItemStack(itemLike, 1));
        });
    }

    private static String path(ItemLike itemLike) {
        return Registry.f_122827_.m_7981_((Object)itemLike.m_5456_()).m_135815_();
    }

    private static String path(TagKey<Item> tagKey) {
        return tagKey.f_203868_().m_135815_().replace('/', '_');
    }

    private static String path(Fluid fluid) {
        return Registry.f_122822_.m_7981_((Object)fluid).m_135815_();
    }

    private static void writeOutput(JsonObject jsonObject, ItemStack ... itemStackArray) {
        if (itemStackArray.length == 0) {
            throw new IllegalArgumentException("Need at least one output stack.");
        }
        if (itemStackArray.length == 1) {
            jsonObject.add("result", (JsonElement)RecipeIo.resultToJson(itemStackArray[0]));
        } else {
            JsonArray jsonArray = new JsonArray(itemStackArray.length);
            for (ItemStack itemStack : itemStackArray) {
                jsonArray.add((JsonElement)RecipeIo.resultToJson(itemStack));
            }
            jsonObject.add("result", (JsonElement)jsonArray);
        }
    }

    private void add(String string, final Consumer<JsonObject> consumer) {
        this.exporter.accept(new MachineRecipeJsonProvider(this.recipeSerializer, string){

            public void m_7917_(JsonObject jsonObject) {
                consumer.accept(jsonObject);
                if (BasicMachineRecipeGenerator.this.requiresMeta == (BasicMachineRecipeGenerator.this.currentMeta == null)) {
                    throw new IllegalStateException("Missing meta");
                }
                if (BasicMachineRecipeGenerator.this.requiresMeta) {
                    BasicMachineRecipeGenerator.this.currentMeta.accept(jsonObject);
                    BasicMachineRecipeGenerator.this.currentMeta = null;
                }
            }
        });
    }

    public BasicMachineRecipeGenerator minHeat(int n) {
        if (this.recipeSerializer != Ic2RecipeSerializers.CENTRIFUGE) {
            throw new IllegalArgumentException();
        }
        return this.setMeta(jsonObject -> jsonObject.addProperty("minHeat", (Number)n));
    }

    public BasicMachineRecipeGenerator fluidDuration(int n, int n2) {
        if (this.recipeSerializer != Ic2RecipeSerializers.BLAST_FURNACE) {
            throw new IllegalArgumentException();
        }
        return this.setMeta(jsonObject -> {
            jsonObject.addProperty("fluid", (Number)n);
            jsonObject.addProperty("duration", (Number)n2);
        });
    }

    public BasicMachineRecipeGenerator amount(int n) {
        if (this.recipeSerializer != Ic2RecipeSerializers.ORE_WASHER) {
            throw new IllegalArgumentException();
        }
        return this.setMeta(jsonObject -> jsonObject.addProperty("amount", (Number)n));
    }

    private BasicMachineRecipeGenerator setMeta(Consumer<JsonObject> consumer) {
        if (this.currentMeta != null) {
            throw new IllegalStateException("Meta already set");
        }
        this.currentMeta = consumer;
        return this;
    }
}


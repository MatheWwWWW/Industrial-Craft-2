/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.crafting.RecipeSerializer
 */
package ic2.core.ref;

import com.google.gson.JsonObject;
import ic2.core.IC2;
import ic2.core.recipe.AdvRecipe;
import ic2.core.recipe.AdvShapelessRecipe;
import ic2.core.recipe.v2.BasicMachineRecipeSerializer;
import ic2.core.recipe.v2.CannerBottleRecipeSerializer;
import ic2.core.recipe.v2.CannerEnrichRecipeSerializer;
import ic2.core.ref.Ic2RecipeTypes;
import java.util.function.Function;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class Ic2RecipeSerializers {
    public static final RecipeSerializer<AdvRecipe> SHAPED = Ic2RecipeSerializers.register("shaped", new AdvRecipe.Serializer());
    public static final RecipeSerializer<AdvShapelessRecipe> SHAPELESS = Ic2RecipeSerializers.register("shapeless", new AdvShapelessRecipe.Serializer());
    public static final BasicMachineRecipeSerializer MACERATOR = Ic2RecipeSerializers.register("macerator", new BasicMachineRecipeSerializer(Ic2RecipeTypes.MACERATOR, null));
    public static final BasicMachineRecipeSerializer EXTRACTOR = Ic2RecipeSerializers.register("extractor", new BasicMachineRecipeSerializer(Ic2RecipeTypes.EXTRACTOR, null));
    public static final BasicMachineRecipeSerializer COMPRESSOR = Ic2RecipeSerializers.register("compressor", new BasicMachineRecipeSerializer(Ic2RecipeTypes.COMPRESSOR, null));
    public static final BasicMachineRecipeSerializer CENTRIFUGE = Ic2RecipeSerializers.register("centrifuge", new BasicMachineRecipeSerializer(Ic2RecipeTypes.CENTRIFUGE, Ic2RecipeSerializers.intMeta("minHeat")));
    public static final BasicMachineRecipeSerializer BLOCK_CUTTER = Ic2RecipeSerializers.register("block_cutter", new BasicMachineRecipeSerializer(Ic2RecipeTypes.BLOCK_CUTTER, Ic2RecipeSerializers.intMeta("hardness")));
    public static final BasicMachineRecipeSerializer BLAST_FURNACE = Ic2RecipeSerializers.register("blast_furnace", new BasicMachineRecipeSerializer(Ic2RecipeTypes.BLAST_FURNACE, Ic2RecipeSerializers.twoIntsMeta("fluid", "duration")));
    public static final BasicMachineRecipeSerializer METAL_FORMER_EXTRUDING = Ic2RecipeSerializers.register("metal_former_extruding", new BasicMachineRecipeSerializer(Ic2RecipeTypes.METAL_FORMER_EXTRUDING, null));
    public static final BasicMachineRecipeSerializer METAL_FORMER_CUTTING = Ic2RecipeSerializers.register("metal_former_cutting", new BasicMachineRecipeSerializer(Ic2RecipeTypes.METAL_FORMER_CUTTING, null));
    public static final BasicMachineRecipeSerializer METAL_FORMER_ROLLING = Ic2RecipeSerializers.register("metal_former_rolling", new BasicMachineRecipeSerializer(Ic2RecipeTypes.METAL_FORMER_ROLLING, null));
    public static final BasicMachineRecipeSerializer ORE_WASHER = Ic2RecipeSerializers.register("ore_washer", new BasicMachineRecipeSerializer(Ic2RecipeTypes.ORE_WASHER, Ic2RecipeSerializers.intMeta("amount")));
    public static final CannerBottleRecipeSerializer CANNER_BOTTLE = Ic2RecipeSerializers.register("canner_bottle", new CannerBottleRecipeSerializer());
    public static final CannerEnrichRecipeSerializer CANNER_ENRICH = Ic2RecipeSerializers.register("canner_enrich", new CannerEnrichRecipeSerializer());

    public static void init() {
    }

    private static <T extends RecipeSerializer<?>> T register(String string, T t) {
        IC2.envProxy.registerRecipeSerializer(IC2.getIdentifier(string), t);
        return t;
    }

    private static Function<JsonObject, CompoundTag> intMeta(String string) {
        return jsonObject -> {
            int n = GsonHelper.m_13927_((JsonObject)jsonObject, (String)string);
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.m_128405_(string, n);
            return compoundTag;
        };
    }

    private static Function<JsonObject, CompoundTag> twoIntsMeta(String string, String string2) {
        return jsonObject -> {
            int n = GsonHelper.m_13927_((JsonObject)jsonObject, (String)string);
            int n2 = GsonHelper.m_13927_((JsonObject)jsonObject, (String)string2);
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.m_128405_(string, n);
            compoundTag.m_128405_(string2, n2);
            return compoundTag;
        };
    }
}


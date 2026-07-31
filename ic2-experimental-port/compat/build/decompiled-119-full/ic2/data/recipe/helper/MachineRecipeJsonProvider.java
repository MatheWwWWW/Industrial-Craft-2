/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.core.Registry
 *  net.minecraft.data.recipes.FinishedRecipe
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  org.jetbrains.annotations.Nullable
 */
package ic2.data.recipe.helper;

import com.google.gson.JsonObject;
import ic2.core.IC2;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

public abstract class MachineRecipeJsonProvider
implements FinishedRecipe {
    private final RecipeSerializer<?> serializer;
    private final String fileName;

    protected MachineRecipeJsonProvider(RecipeSerializer<?> recipeSerializer, String string) {
        this.serializer = recipeSerializer;
        this.fileName = string;
    }

    public final ResourceLocation m_6445_() {
        return IC2.getIdentifier(Registry.f_122865_.m_7981_(this.serializer).m_135815_() + "/" + this.fileName);
    }

    public final RecipeSerializer<?> m_6637_() {
        return this.serializer;
    }

    @Nullable
    public final JsonObject m_5860_() {
        return null;
    }

    @Nullable
    public final ResourceLocation m_6448_() {
        return null;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.data.recipes;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public interface FinishedRecipe {
    public void m_7917_(JsonObject var1);

    default public JsonObject m_125966_() {
        JsonObject $$0 = new JsonObject();
        $$0.addProperty("type", Registry.f_122865_.m_7981_(this.m_6637_()).toString());
        this.m_7917_($$0);
        return $$0;
    }

    public ResourceLocation m_6445_();

    public RecipeSerializer<?> m_6637_();

    @Nullable
    public JsonObject m_5860_();

    @Nullable
    public ResourceLocation m_6448_();
}


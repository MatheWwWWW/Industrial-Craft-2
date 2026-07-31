/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.data.recipes;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public interface RecipeBuilder {
    public static final ResourceLocation f_236353_ = new ResourceLocation("recipes/root");

    public RecipeBuilder m_126132_(String var1, CriterionTriggerInstance var2);

    public RecipeBuilder m_126145_(@Nullable String var1);

    public Item m_142372_();

    public void m_126140_(Consumer<FinishedRecipe> var1, ResourceLocation var2);

    default public void m_176498_(Consumer<FinishedRecipe> p_176499_) {
        this.m_126140_(p_176499_, RecipeBuilder.m_176493_(this.m_142372_()));
    }

    default public void m_176500_(Consumer<FinishedRecipe> p_176501_, String p_176502_) {
        ResourceLocation $$3 = new ResourceLocation(p_176502_);
        ResourceLocation $$2 = RecipeBuilder.m_176493_(this.m_142372_());
        if ($$3.equals($$2)) {
            throw new IllegalStateException("Recipe " + p_176502_ + " should remove its 'save' argument as it is equal to default one");
        }
        this.m_126140_(p_176501_, $$3);
    }

    public static ResourceLocation m_176493_(ItemLike p_176494_) {
        return Registry.f_122827_.m_7981_(p_176494_.m_5456_());
    }
}


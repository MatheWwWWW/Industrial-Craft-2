/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.data.recipes;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

public class ShapelessRecipeBuilder
implements RecipeBuilder {
    private final Item f_126173_;
    private final int f_126174_;
    private final List<Ingredient> f_126175_ = Lists.newArrayList();
    private final Advancement.Builder f_126176_ = Advancement.Builder.m_138353_();
    @Nullable
    private String f_126177_;

    public ShapelessRecipeBuilder(ItemLike p_126180_, int p_126181_) {
        this.f_126173_ = p_126180_.m_5456_();
        this.f_126174_ = p_126181_;
    }

    public static ShapelessRecipeBuilder m_126189_(ItemLike p_126190_) {
        return new ShapelessRecipeBuilder(p_126190_, 1);
    }

    public static ShapelessRecipeBuilder m_126191_(ItemLike p_126192_, int p_126193_) {
        return new ShapelessRecipeBuilder(p_126192_, p_126193_);
    }

    public ShapelessRecipeBuilder m_206419_(TagKey<Item> p_206420_) {
        return this.m_126184_(Ingredient.m_204132_(p_206420_));
    }

    public ShapelessRecipeBuilder m_126209_(ItemLike p_126210_) {
        return this.m_126211_(p_126210_, 1);
    }

    public ShapelessRecipeBuilder m_126211_(ItemLike p_126212_, int p_126213_) {
        for (int $$2 = 0; $$2 < p_126213_; ++$$2) {
            this.m_126184_(Ingredient.m_43929_(p_126212_));
        }
        return this;
    }

    public ShapelessRecipeBuilder m_126184_(Ingredient p_126185_) {
        return this.m_126186_(p_126185_, 1);
    }

    public ShapelessRecipeBuilder m_126186_(Ingredient p_126187_, int p_126188_) {
        for (int $$2 = 0; $$2 < p_126188_; ++$$2) {
            this.f_126175_.add(p_126187_);
        }
        return this;
    }

    @Override
    public ShapelessRecipeBuilder m_126132_(String p_126197_, CriterionTriggerInstance p_126198_) {
        this.f_126176_.m_138386_(p_126197_, p_126198_);
        return this;
    }

    @Override
    public ShapelessRecipeBuilder m_126145_(@Nullable String p_126195_) {
        this.f_126177_ = p_126195_;
        return this;
    }

    @Override
    public Item m_142372_() {
        return this.f_126173_;
    }

    @Override
    public void m_126140_(Consumer<FinishedRecipe> p_126205_, ResourceLocation p_126206_) {
        this.m_126207_(p_126206_);
        this.f_126176_.m_138396_(f_236353_).m_138386_("has_the_recipe", RecipeUnlockedTrigger.m_63728_(p_126206_)).m_138354_(AdvancementRewards.Builder.m_10009_(p_126206_)).m_138360_(RequirementsStrategy.f_15979_);
        p_126205_.accept(new Result(p_126206_, this.f_126173_, this.f_126174_, this.f_126177_ == null ? "" : this.f_126177_, this.f_126175_, this.f_126176_, new ResourceLocation(p_126206_.m_135827_(), "recipes/" + this.f_126173_.m_41471_().m_40783_() + "/" + p_126206_.m_135815_())));
    }

    private void m_126207_(ResourceLocation p_126208_) {
        if (this.f_126176_.m_138405_().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_126208_);
        }
    }

    @Override
    public /* synthetic */ RecipeBuilder m_126145_(@Nullable String string) {
        return this.m_126145_(string);
    }

    @Override
    public /* synthetic */ RecipeBuilder m_126132_(String string, CriterionTriggerInstance criterionTriggerInstance) {
        return this.m_126132_(string, criterionTriggerInstance);
    }

    public static class Result
    implements FinishedRecipe {
        private final ResourceLocation f_126214_;
        private final Item f_126215_;
        private final int f_126216_;
        private final String f_126217_;
        private final List<Ingredient> f_126218_;
        private final Advancement.Builder f_126219_;
        private final ResourceLocation f_126220_;

        public Result(ResourceLocation p_126222_, Item p_126223_, int p_126224_, String p_126225_, List<Ingredient> p_126226_, Advancement.Builder p_126227_, ResourceLocation p_126228_) {
            this.f_126214_ = p_126222_;
            this.f_126215_ = p_126223_;
            this.f_126216_ = p_126224_;
            this.f_126217_ = p_126225_;
            this.f_126218_ = p_126226_;
            this.f_126219_ = p_126227_;
            this.f_126220_ = p_126228_;
        }

        @Override
        public void m_7917_(JsonObject p_126230_) {
            if (!this.f_126217_.isEmpty()) {
                p_126230_.addProperty("group", this.f_126217_);
            }
            JsonArray $$1 = new JsonArray();
            for (Ingredient $$2 : this.f_126218_) {
                $$1.add($$2.m_43942_());
            }
            p_126230_.add("ingredients", (JsonElement)$$1);
            JsonObject $$3 = new JsonObject();
            $$3.addProperty("item", Registry.f_122827_.m_7981_(this.f_126215_).toString());
            if (this.f_126216_ > 1) {
                $$3.addProperty("count", (Number)this.f_126216_);
            }
            p_126230_.add("result", (JsonElement)$$3);
        }

        @Override
        public RecipeSerializer<?> m_6637_() {
            return RecipeSerializer.f_44077_;
        }

        @Override
        public ResourceLocation m_6445_() {
            return this.f_126214_;
        }

        @Override
        @Nullable
        public JsonObject m_5860_() {
            return this.f_126219_.m_138400_();
        }

        @Override
        @Nullable
        public ResourceLocation m_6448_() {
            return this.f_126220_;
        }
    }
}


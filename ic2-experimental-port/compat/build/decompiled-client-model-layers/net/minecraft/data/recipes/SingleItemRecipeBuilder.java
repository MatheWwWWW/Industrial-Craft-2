/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.data.recipes;

import com.google.gson.JsonObject;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

public class SingleItemRecipeBuilder
implements RecipeBuilder {
    private final Item f_126302_;
    private final Ingredient f_126303_;
    private final int f_126304_;
    private final Advancement.Builder f_126305_ = Advancement.Builder.m_138353_();
    @Nullable
    private String f_126306_;
    private final RecipeSerializer<?> f_126307_;

    public SingleItemRecipeBuilder(RecipeSerializer<?> p_126309_, Ingredient p_126310_, ItemLike p_126311_, int p_126312_) {
        this.f_126307_ = p_126309_;
        this.f_126302_ = p_126311_.m_5456_();
        this.f_126303_ = p_126310_;
        this.f_126304_ = p_126312_;
    }

    public static SingleItemRecipeBuilder m_126313_(Ingredient p_126314_, ItemLike p_126315_) {
        return new SingleItemRecipeBuilder(RecipeSerializer.f_44095_, p_126314_, p_126315_, 1);
    }

    public static SingleItemRecipeBuilder m_126316_(Ingredient p_126317_, ItemLike p_126318_, int p_126319_) {
        return new SingleItemRecipeBuilder(RecipeSerializer.f_44095_, p_126317_, p_126318_, p_126319_);
    }

    @Override
    public SingleItemRecipeBuilder m_126132_(String p_176810_, CriterionTriggerInstance p_176811_) {
        this.f_126305_.m_138386_(p_176810_, p_176811_);
        return this;
    }

    @Override
    public SingleItemRecipeBuilder m_126145_(@Nullable String p_176808_) {
        this.f_126306_ = p_176808_;
        return this;
    }

    @Override
    public Item m_142372_() {
        return this.f_126302_;
    }

    @Override
    public void m_126140_(Consumer<FinishedRecipe> p_126327_, ResourceLocation p_126328_) {
        this.m_126329_(p_126328_);
        this.f_126305_.m_138396_(f_236353_).m_138386_("has_the_recipe", RecipeUnlockedTrigger.m_63728_(p_126328_)).m_138354_(AdvancementRewards.Builder.m_10009_(p_126328_)).m_138360_(RequirementsStrategy.f_15979_);
        p_126327_.accept(new Result(p_126328_, this.f_126307_, this.f_126306_ == null ? "" : this.f_126306_, this.f_126303_, this.f_126302_, this.f_126304_, this.f_126305_, new ResourceLocation(p_126328_.m_135827_(), "recipes/" + this.f_126302_.m_41471_().m_40783_() + "/" + p_126328_.m_135815_())));
    }

    private void m_126329_(ResourceLocation p_126330_) {
        if (this.f_126305_.m_138405_().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_126330_);
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
        private final ResourceLocation f_126331_;
        private final String f_126332_;
        private final Ingredient f_126333_;
        private final Item f_126334_;
        private final int f_126335_;
        private final Advancement.Builder f_126336_;
        private final ResourceLocation f_126337_;
        private final RecipeSerializer<?> f_126338_;

        public Result(ResourceLocation p_126340_, RecipeSerializer<?> p_126341_, String p_126342_, Ingredient p_126343_, Item p_126344_, int p_126345_, Advancement.Builder p_126346_, ResourceLocation p_126347_) {
            this.f_126331_ = p_126340_;
            this.f_126338_ = p_126341_;
            this.f_126332_ = p_126342_;
            this.f_126333_ = p_126343_;
            this.f_126334_ = p_126344_;
            this.f_126335_ = p_126345_;
            this.f_126336_ = p_126346_;
            this.f_126337_ = p_126347_;
        }

        @Override
        public void m_7917_(JsonObject p_126349_) {
            if (!this.f_126332_.isEmpty()) {
                p_126349_.addProperty("group", this.f_126332_);
            }
            p_126349_.add("ingredient", this.f_126333_.m_43942_());
            p_126349_.addProperty("result", Registry.f_122827_.m_7981_(this.f_126334_).toString());
            p_126349_.addProperty("count", (Number)this.f_126335_);
        }

        @Override
        public ResourceLocation m_6445_() {
            return this.f_126331_;
        }

        @Override
        public RecipeSerializer<?> m_6637_() {
            return this.f_126338_;
        }

        @Override
        @Nullable
        public JsonObject m_5860_() {
            return this.f_126336_.m_138400_();
        }

        @Override
        @Nullable
        public ResourceLocation m_6448_() {
            return this.f_126337_;
        }
    }
}


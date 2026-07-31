/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.data.recipes;

import com.google.gson.JsonElement;
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

public class UpgradeRecipeBuilder {
    private final Ingredient f_126375_;
    private final Ingredient f_126376_;
    private final Item f_126377_;
    private final Advancement.Builder f_126378_ = Advancement.Builder.m_138353_();
    private final RecipeSerializer<?> f_126379_;

    public UpgradeRecipeBuilder(RecipeSerializer<?> p_126381_, Ingredient p_126382_, Ingredient p_126383_, Item p_126384_) {
        this.f_126379_ = p_126381_;
        this.f_126375_ = p_126382_;
        this.f_126376_ = p_126383_;
        this.f_126377_ = p_126384_;
    }

    public static UpgradeRecipeBuilder m_126385_(Ingredient p_126386_, Ingredient p_126387_, Item p_126388_) {
        return new UpgradeRecipeBuilder(RecipeSerializer.f_44096_, p_126386_, p_126387_, p_126388_);
    }

    public UpgradeRecipeBuilder m_126389_(String p_126390_, CriterionTriggerInstance p_126391_) {
        this.f_126378_.m_138386_(p_126390_, p_126391_);
        return this;
    }

    public void m_126392_(Consumer<FinishedRecipe> p_126393_, String p_126394_) {
        this.m_126395_(p_126393_, new ResourceLocation(p_126394_));
    }

    public void m_126395_(Consumer<FinishedRecipe> p_126396_, ResourceLocation p_126397_) {
        this.m_126398_(p_126397_);
        this.f_126378_.m_138396_(RecipeBuilder.f_236353_).m_138386_("has_the_recipe", RecipeUnlockedTrigger.m_63728_(p_126397_)).m_138354_(AdvancementRewards.Builder.m_10009_(p_126397_)).m_138360_(RequirementsStrategy.f_15979_);
        p_126396_.accept(new Result(p_126397_, this.f_126379_, this.f_126375_, this.f_126376_, this.f_126377_, this.f_126378_, new ResourceLocation(p_126397_.m_135827_(), "recipes/" + this.f_126377_.m_41471_().m_40783_() + "/" + p_126397_.m_135815_())));
    }

    private void m_126398_(ResourceLocation p_126399_) {
        if (this.f_126378_.m_138405_().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_126399_);
        }
    }

    public static class Result
    implements FinishedRecipe {
        private final ResourceLocation f_126400_;
        private final Ingredient f_126401_;
        private final Ingredient f_126402_;
        private final Item f_126403_;
        private final Advancement.Builder f_126404_;
        private final ResourceLocation f_126405_;
        private final RecipeSerializer<?> f_126406_;

        public Result(ResourceLocation p_126408_, RecipeSerializer<?> p_126409_, Ingredient p_126410_, Ingredient p_126411_, Item p_126412_, Advancement.Builder p_126413_, ResourceLocation p_126414_) {
            this.f_126400_ = p_126408_;
            this.f_126406_ = p_126409_;
            this.f_126401_ = p_126410_;
            this.f_126402_ = p_126411_;
            this.f_126403_ = p_126412_;
            this.f_126404_ = p_126413_;
            this.f_126405_ = p_126414_;
        }

        @Override
        public void m_7917_(JsonObject p_126416_) {
            p_126416_.add("base", this.f_126401_.m_43942_());
            p_126416_.add("addition", this.f_126402_.m_43942_());
            JsonObject $$1 = new JsonObject();
            $$1.addProperty("item", Registry.f_122827_.m_7981_(this.f_126403_).toString());
            p_126416_.add("result", (JsonElement)$$1);
        }

        @Override
        public ResourceLocation m_6445_() {
            return this.f_126400_;
        }

        @Override
        public RecipeSerializer<?> m_6637_() {
            return this.f_126406_;
        }

        @Override
        @Nullable
        public JsonObject m_5860_() {
            return this.f_126404_.m_138400_();
        }

        @Override
        @Nullable
        public ResourceLocation m_6448_() {
            return this.f_126405_;
        }
    }
}


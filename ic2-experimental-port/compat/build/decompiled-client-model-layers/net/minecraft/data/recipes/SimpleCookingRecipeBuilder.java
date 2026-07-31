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
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;

public class SimpleCookingRecipeBuilder
implements RecipeBuilder {
    private final Item f_126235_;
    private final Ingredient f_126236_;
    private final float f_126237_;
    private final int f_126238_;
    private final Advancement.Builder f_126239_ = Advancement.Builder.m_138353_();
    @Nullable
    private String f_126240_;
    private final SimpleCookingSerializer<?> f_126241_;

    private SimpleCookingRecipeBuilder(ItemLike p_126243_, Ingredient p_126244_, float p_126245_, int p_126246_, SimpleCookingSerializer<?> p_126247_) {
        this.f_126235_ = p_126243_.m_5456_();
        this.f_126236_ = p_126244_;
        this.f_126237_ = p_126245_;
        this.f_126238_ = p_126246_;
        this.f_126241_ = p_126247_;
    }

    public static SimpleCookingRecipeBuilder m_126248_(Ingredient p_126249_, ItemLike p_126250_, float p_126251_, int p_126252_, SimpleCookingSerializer<?> p_126253_) {
        return new SimpleCookingRecipeBuilder(p_126250_, p_126249_, p_126251_, p_126252_, p_126253_);
    }

    public static SimpleCookingRecipeBuilder m_176784_(Ingredient p_176785_, ItemLike p_176786_, float p_176787_, int p_176788_) {
        return SimpleCookingRecipeBuilder.m_126248_(p_176785_, p_176786_, p_176787_, p_176788_, RecipeSerializer.f_44094_);
    }

    public static SimpleCookingRecipeBuilder m_126267_(Ingredient p_126268_, ItemLike p_126269_, float p_126270_, int p_126271_) {
        return SimpleCookingRecipeBuilder.m_126248_(p_126268_, p_126269_, p_126270_, p_126271_, RecipeSerializer.f_44092_);
    }

    public static SimpleCookingRecipeBuilder m_126272_(Ingredient p_126273_, ItemLike p_126274_, float p_126275_, int p_126276_) {
        return SimpleCookingRecipeBuilder.m_126248_(p_126273_, p_126274_, p_126275_, p_126276_, RecipeSerializer.f_44091_);
    }

    public static SimpleCookingRecipeBuilder m_176796_(Ingredient p_176797_, ItemLike p_176798_, float p_176799_, int p_176800_) {
        return SimpleCookingRecipeBuilder.m_126248_(p_176797_, p_176798_, p_176799_, p_176800_, RecipeSerializer.f_44093_);
    }

    @Override
    public SimpleCookingRecipeBuilder m_126132_(String p_126255_, CriterionTriggerInstance p_126256_) {
        this.f_126239_.m_138386_(p_126255_, p_126256_);
        return this;
    }

    @Override
    public SimpleCookingRecipeBuilder m_126145_(@Nullable String p_176795_) {
        this.f_126240_ = p_176795_;
        return this;
    }

    @Override
    public Item m_142372_() {
        return this.f_126235_;
    }

    @Override
    public void m_126140_(Consumer<FinishedRecipe> p_126263_, ResourceLocation p_126264_) {
        this.m_126265_(p_126264_);
        this.f_126239_.m_138396_(f_236353_).m_138386_("has_the_recipe", RecipeUnlockedTrigger.m_63728_(p_126264_)).m_138354_(AdvancementRewards.Builder.m_10009_(p_126264_)).m_138360_(RequirementsStrategy.f_15979_);
        p_126263_.accept(new Result(p_126264_, this.f_126240_ == null ? "" : this.f_126240_, this.f_126236_, this.f_126235_, this.f_126237_, this.f_126238_, this.f_126239_, new ResourceLocation(p_126264_.m_135827_(), "recipes/" + this.f_126235_.m_41471_().m_40783_() + "/" + p_126264_.m_135815_()), this.f_126241_));
    }

    private void m_126265_(ResourceLocation p_126266_) {
        if (this.f_126239_.m_138405_().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_126266_);
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
        private final ResourceLocation f_126277_;
        private final String f_126278_;
        private final Ingredient f_126279_;
        private final Item f_126280_;
        private final float f_126281_;
        private final int f_126282_;
        private final Advancement.Builder f_126283_;
        private final ResourceLocation f_126284_;
        private final RecipeSerializer<? extends AbstractCookingRecipe> f_126285_;

        public Result(ResourceLocation p_126287_, String p_126288_, Ingredient p_126289_, Item p_126290_, float p_126291_, int p_126292_, Advancement.Builder p_126293_, ResourceLocation p_126294_, RecipeSerializer<? extends AbstractCookingRecipe> p_126295_) {
            this.f_126277_ = p_126287_;
            this.f_126278_ = p_126288_;
            this.f_126279_ = p_126289_;
            this.f_126280_ = p_126290_;
            this.f_126281_ = p_126291_;
            this.f_126282_ = p_126292_;
            this.f_126283_ = p_126293_;
            this.f_126284_ = p_126294_;
            this.f_126285_ = p_126295_;
        }

        @Override
        public void m_7917_(JsonObject p_126297_) {
            if (!this.f_126278_.isEmpty()) {
                p_126297_.addProperty("group", this.f_126278_);
            }
            p_126297_.add("ingredient", this.f_126279_.m_43942_());
            p_126297_.addProperty("result", Registry.f_122827_.m_7981_(this.f_126280_).toString());
            p_126297_.addProperty("experience", (Number)Float.valueOf(this.f_126281_));
            p_126297_.addProperty("cookingtime", (Number)this.f_126282_);
        }

        @Override
        public RecipeSerializer<?> m_6637_() {
            return this.f_126285_;
        }

        @Override
        public ResourceLocation m_6445_() {
            return this.f_126277_;
        }

        @Override
        @Nullable
        public JsonObject m_5860_() {
            return this.f_126283_.m_138400_();
        }

        @Override
        @Nullable
        public ResourceLocation m_6448_() {
            return this.f_126284_;
        }
    }
}


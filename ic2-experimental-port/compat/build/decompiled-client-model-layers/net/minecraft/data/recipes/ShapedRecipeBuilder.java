/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.data.recipes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

public class ShapedRecipeBuilder
implements RecipeBuilder {
    private final Item f_126106_;
    private final int f_126107_;
    private final List<String> f_126108_ = Lists.newArrayList();
    private final Map<Character, Ingredient> f_126109_ = Maps.newLinkedHashMap();
    private final Advancement.Builder f_126110_ = Advancement.Builder.m_138353_();
    @Nullable
    private String f_126111_;

    public ShapedRecipeBuilder(ItemLike p_126114_, int p_126115_) {
        this.f_126106_ = p_126114_.m_5456_();
        this.f_126107_ = p_126115_;
    }

    public static ShapedRecipeBuilder m_126116_(ItemLike p_126117_) {
        return ShapedRecipeBuilder.m_126118_(p_126117_, 1);
    }

    public static ShapedRecipeBuilder m_126118_(ItemLike p_126119_, int p_126120_) {
        return new ShapedRecipeBuilder(p_126119_, p_126120_);
    }

    public ShapedRecipeBuilder m_206416_(Character p_206417_, TagKey<Item> p_206418_) {
        return this.m_126124_(p_206417_, Ingredient.m_204132_(p_206418_));
    }

    public ShapedRecipeBuilder m_126127_(Character p_126128_, ItemLike p_126129_) {
        return this.m_126124_(p_126128_, Ingredient.m_43929_(p_126129_));
    }

    public ShapedRecipeBuilder m_126124_(Character p_126125_, Ingredient p_126126_) {
        if (this.f_126109_.containsKey(p_126125_)) {
            throw new IllegalArgumentException("Symbol '" + p_126125_ + "' is already defined!");
        }
        if (p_126125_.charValue() == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        }
        this.f_126109_.put(p_126125_, p_126126_);
        return this;
    }

    public ShapedRecipeBuilder m_126130_(String p_126131_) {
        if (!this.f_126108_.isEmpty() && p_126131_.length() != this.f_126108_.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        }
        this.f_126108_.add(p_126131_);
        return this;
    }

    @Override
    public ShapedRecipeBuilder m_126132_(String p_126133_, CriterionTriggerInstance p_126134_) {
        this.f_126110_.m_138386_(p_126133_, p_126134_);
        return this;
    }

    @Override
    public ShapedRecipeBuilder m_126145_(@Nullable String p_126146_) {
        this.f_126111_ = p_126146_;
        return this;
    }

    @Override
    public Item m_142372_() {
        return this.f_126106_;
    }

    @Override
    public void m_126140_(Consumer<FinishedRecipe> p_126141_, ResourceLocation p_126142_) {
        this.m_126143_(p_126142_);
        this.f_126110_.m_138396_(f_236353_).m_138386_("has_the_recipe", RecipeUnlockedTrigger.m_63728_(p_126142_)).m_138354_(AdvancementRewards.Builder.m_10009_(p_126142_)).m_138360_(RequirementsStrategy.f_15979_);
        p_126141_.accept(new Result(p_126142_, this.f_126106_, this.f_126107_, this.f_126111_ == null ? "" : this.f_126111_, this.f_126108_, this.f_126109_, this.f_126110_, new ResourceLocation(p_126142_.m_135827_(), "recipes/" + this.f_126106_.m_41471_().m_40783_() + "/" + p_126142_.m_135815_())));
    }

    private void m_126143_(ResourceLocation p_126144_) {
        if (this.f_126108_.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + p_126144_ + "!");
        }
        HashSet $$1 = Sets.newHashSet(this.f_126109_.keySet());
        $$1.remove(Character.valueOf(' '));
        for (String $$2 : this.f_126108_) {
            for (int $$3 = 0; $$3 < $$2.length(); ++$$3) {
                char $$4 = $$2.charAt($$3);
                if (!this.f_126109_.containsKey(Character.valueOf($$4)) && $$4 != ' ') {
                    throw new IllegalStateException("Pattern in recipe " + p_126144_ + " uses undefined symbol '" + $$4 + "'");
                }
                $$1.remove(Character.valueOf($$4));
            }
        }
        if (!$$1.isEmpty()) {
            throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + p_126144_);
        }
        if (this.f_126108_.size() == 1 && this.f_126108_.get(0).length() == 1) {
            throw new IllegalStateException("Shaped recipe " + p_126144_ + " only takes in a single item - should it be a shapeless recipe instead?");
        }
        if (this.f_126110_.m_138405_().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_126144_);
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

    static class Result
    implements FinishedRecipe {
        private final ResourceLocation f_126148_;
        private final Item f_126149_;
        private final int f_126150_;
        private final String f_126151_;
        private final List<String> f_126152_;
        private final Map<Character, Ingredient> f_126153_;
        private final Advancement.Builder f_126154_;
        private final ResourceLocation f_126155_;

        public Result(ResourceLocation p_176754_, Item p_176755_, int p_176756_, String p_176757_, List<String> p_176758_, Map<Character, Ingredient> p_176759_, Advancement.Builder p_176760_, ResourceLocation p_176761_) {
            this.f_126148_ = p_176754_;
            this.f_126149_ = p_176755_;
            this.f_126150_ = p_176756_;
            this.f_126151_ = p_176757_;
            this.f_126152_ = p_176758_;
            this.f_126153_ = p_176759_;
            this.f_126154_ = p_176760_;
            this.f_126155_ = p_176761_;
        }

        @Override
        public void m_7917_(JsonObject p_126167_) {
            if (!this.f_126151_.isEmpty()) {
                p_126167_.addProperty("group", this.f_126151_);
            }
            JsonArray $$1 = new JsonArray();
            for (String string : this.f_126152_) {
                $$1.add(string);
            }
            p_126167_.add("pattern", (JsonElement)$$1);
            JsonObject $$3 = new JsonObject();
            for (Map.Entry<Character, Ingredient> entry : this.f_126153_.entrySet()) {
                $$3.add(String.valueOf(entry.getKey()), entry.getValue().m_43942_());
            }
            p_126167_.add("key", (JsonElement)$$3);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Registry.f_122827_.m_7981_(this.f_126149_).toString());
            if (this.f_126150_ > 1) {
                jsonObject.addProperty("count", (Number)this.f_126150_);
            }
            p_126167_.add("result", (JsonElement)jsonObject);
        }

        @Override
        public RecipeSerializer<?> m_6637_() {
            return RecipeSerializer.f_44076_;
        }

        @Override
        public ResourceLocation m_6445_() {
            return this.f_126148_;
        }

        @Override
        @Nullable
        public JsonObject m_5860_() {
            return this.f_126154_.m_138400_();
        }

        @Override
        @Nullable
        public ResourceLocation m_6448_() {
            return this.f_126155_;
        }
    }
}


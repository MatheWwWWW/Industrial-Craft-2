/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ApplyBonusCount
extends LootItemConditionalFunction {
    static final Map<ResourceLocation, FormulaDeserializer> f_79898_ = Maps.newHashMap();
    final Enchantment f_79899_;
    final Formula f_79900_;

    ApplyBonusCount(LootItemCondition[] p_79903_, Enchantment p_79904_, Formula p_79905_) {
        super(p_79903_);
        this.f_79899_ = p_79904_;
        this.f_79900_ = p_79905_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80750_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return ImmutableSet.of(LootContextParams.f_81463_);
    }

    @Override
    public ItemStack m_7372_(ItemStack p_79913_, LootContext p_79914_) {
        ItemStack $$2 = p_79914_.m_78953_(LootContextParams.f_81463_);
        if ($$2 != null) {
            int $$3 = EnchantmentHelper.m_44843_(this.f_79899_, $$2);
            int $$4 = this.f_79900_.m_213779_(p_79914_.m_230907_(), p_79913_.m_41613_(), $$3);
            p_79913_.m_41764_($$4);
        }
        return p_79913_;
    }

    public static LootItemConditionalFunction.Builder<?> m_79917_(Enchantment p_79918_, float p_79919_, int p_79920_) {
        return ApplyBonusCount.m_80683_(p_79928_ -> new ApplyBonusCount((LootItemCondition[])p_79928_, p_79918_, new BinomialWithBonusCount(p_79920_, p_79919_)));
    }

    public static LootItemConditionalFunction.Builder<?> m_79915_(Enchantment p_79916_) {
        return ApplyBonusCount.m_80683_(p_79943_ -> new ApplyBonusCount((LootItemCondition[])p_79943_, p_79916_, new OreDrops()));
    }

    public static LootItemConditionalFunction.Builder<?> m_79939_(Enchantment p_79940_) {
        return ApplyBonusCount.m_80683_(p_79935_ -> new ApplyBonusCount((LootItemCondition[])p_79935_, p_79940_, new UniformBonusCount(1)));
    }

    public static LootItemConditionalFunction.Builder<?> m_79921_(Enchantment p_79922_, int p_79923_) {
        return ApplyBonusCount.m_80683_(p_79932_ -> new ApplyBonusCount((LootItemCondition[])p_79932_, p_79922_, new UniformBonusCount(p_79923_)));
    }

    static {
        f_79898_.put(BinomialWithBonusCount.f_79947_, BinomialWithBonusCount::m_79955_);
        f_79898_.put(OreDrops.f_79973_, OreDrops::m_79979_);
        f_79898_.put(UniformBonusCount.f_80012_, UniformBonusCount::m_80018_);
    }

    static interface Formula {
        public int m_213779_(RandomSource var1, int var2, int var3);

        public void m_6417_(JsonObject var1, JsonSerializationContext var2);

        public ResourceLocation m_5713_();
    }

    static final class UniformBonusCount
    implements Formula {
        public static final ResourceLocation f_80012_ = new ResourceLocation("uniform_bonus_count");
        private final int f_80013_;

        public UniformBonusCount(int p_80016_) {
            this.f_80013_ = p_80016_;
        }

        @Override
        public int m_213779_(RandomSource p_230976_, int p_230977_, int p_230978_) {
            return p_230977_ + p_230976_.m_188503_(this.f_80013_ * p_230978_ + 1);
        }

        @Override
        public void m_6417_(JsonObject p_80022_, JsonSerializationContext p_80023_) {
            p_80022_.addProperty("bonusMultiplier", (Number)this.f_80013_);
        }

        public static Formula m_80018_(JsonObject p_80019_, JsonDeserializationContext p_80020_) {
            int $$2 = GsonHelper.m_13927_(p_80019_, "bonusMultiplier");
            return new UniformBonusCount($$2);
        }

        @Override
        public ResourceLocation m_5713_() {
            return f_80012_;
        }
    }

    static final class OreDrops
    implements Formula {
        public static final ResourceLocation f_79973_ = new ResourceLocation("ore_drops");

        OreDrops() {
        }

        @Override
        public int m_213779_(RandomSource p_230972_, int p_230973_, int p_230974_) {
            if (p_230974_ > 0) {
                int $$3 = p_230972_.m_188503_(p_230974_ + 2) - 1;
                if ($$3 < 0) {
                    $$3 = 0;
                }
                return p_230973_ * ($$3 + 1);
            }
            return p_230973_;
        }

        @Override
        public void m_6417_(JsonObject p_79983_, JsonSerializationContext p_79984_) {
        }

        public static Formula m_79979_(JsonObject p_79980_, JsonDeserializationContext p_79981_) {
            return new OreDrops();
        }

        @Override
        public ResourceLocation m_5713_() {
            return f_79973_;
        }
    }

    static final class BinomialWithBonusCount
    implements Formula {
        public static final ResourceLocation f_79947_ = new ResourceLocation("binomial_with_bonus_count");
        private final int f_79948_;
        private final float f_79949_;

        public BinomialWithBonusCount(int p_79952_, float p_79953_) {
            this.f_79948_ = p_79952_;
            this.f_79949_ = p_79953_;
        }

        @Override
        public int m_213779_(RandomSource p_230965_, int p_230966_, int p_230967_) {
            for (int $$3 = 0; $$3 < p_230967_ + this.f_79948_; ++$$3) {
                if (!(p_230965_.m_188501_() < this.f_79949_)) continue;
                ++p_230966_;
            }
            return p_230966_;
        }

        @Override
        public void m_6417_(JsonObject p_79959_, JsonSerializationContext p_79960_) {
            p_79959_.addProperty("extra", (Number)this.f_79948_);
            p_79959_.addProperty("probability", (Number)Float.valueOf(this.f_79949_));
        }

        public static Formula m_79955_(JsonObject p_79956_, JsonDeserializationContext p_79957_) {
            int $$2 = GsonHelper.m_13927_(p_79956_, "extra");
            float $$3 = GsonHelper.m_13915_(p_79956_, "probability");
            return new BinomialWithBonusCount($$2, $$3);
        }

        @Override
        public ResourceLocation m_5713_() {
            return f_79947_;
        }
    }

    static interface FormulaDeserializer {
        public Formula m_79970_(JsonObject var1, JsonDeserializationContext var2);
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<ApplyBonusCount> {
        @Override
        public void m_6170_(JsonObject p_79995_, ApplyBonusCount p_79996_, JsonSerializationContext p_79997_) {
            super.m_6170_(p_79995_, p_79996_, p_79997_);
            p_79995_.addProperty("enchantment", Registry.f_122825_.m_7981_(p_79996_.f_79899_).toString());
            p_79995_.addProperty("formula", p_79996_.f_79900_.m_5713_().toString());
            JsonObject $$3 = new JsonObject();
            p_79996_.f_79900_.m_6417_($$3, p_79997_);
            if ($$3.size() > 0) {
                p_79995_.add("parameters", (JsonElement)$$3);
            }
        }

        @Override
        public ApplyBonusCount m_6821_(JsonObject p_79991_, JsonDeserializationContext p_79992_, LootItemCondition[] p_79993_) {
            Formula $$8;
            ResourceLocation $$3 = new ResourceLocation(GsonHelper.m_13906_(p_79991_, "enchantment"));
            Enchantment $$4 = Registry.f_122825_.m_6612_($$3).orElseThrow(() -> new JsonParseException("Invalid enchantment id: " + $$3));
            ResourceLocation $$5 = new ResourceLocation(GsonHelper.m_13906_(p_79991_, "formula"));
            FormulaDeserializer $$6 = f_79898_.get($$5);
            if ($$6 == null) {
                throw new JsonParseException("Invalid formula id: " + $$5);
            }
            if (p_79991_.has("parameters")) {
                Formula $$7 = $$6.m_79970_(GsonHelper.m_13930_(p_79991_, "parameters"), p_79992_);
            } else {
                $$8 = $$6.m_79970_(new JsonObject(), p_79992_);
            }
            return new ApplyBonusCount(p_79993_, $$4, $$8);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}


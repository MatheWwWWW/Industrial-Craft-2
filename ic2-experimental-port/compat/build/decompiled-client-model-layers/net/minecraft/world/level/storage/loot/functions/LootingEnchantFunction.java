/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class LootingEnchantFunction
extends LootItemConditionalFunction {
    public static final int f_165224_ = 0;
    final NumberProvider f_80776_;
    final int f_80777_;

    LootingEnchantFunction(LootItemCondition[] p_165226_, NumberProvider p_165227_, int p_165228_) {
        super(p_165226_);
        this.f_80776_ = p_165227_;
        this.f_80777_ = p_165228_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80741_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return Sets.union((Set)ImmutableSet.of(LootContextParams.f_81458_), this.f_80776_.m_6231_());
    }

    boolean m_80798_() {
        return this.f_80777_ > 0;
    }

    @Override
    public ItemStack m_7372_(ItemStack p_80789_, LootContext p_80790_) {
        Entity $$2 = p_80790_.m_78953_(LootContextParams.f_81458_);
        if ($$2 instanceof LivingEntity) {
            int $$3 = EnchantmentHelper.m_44930_((LivingEntity)$$2);
            if ($$3 == 0) {
                return p_80789_;
            }
            float $$4 = (float)$$3 * this.f_80776_.m_142688_(p_80790_);
            p_80789_.m_41769_(Math.round($$4));
            if (this.m_80798_() && p_80789_.m_41613_() > this.f_80777_) {
                p_80789_.m_41764_(this.f_80777_);
            }
        }
        return p_80789_;
    }

    public static Builder m_165229_(NumberProvider p_165230_) {
        return new Builder(p_165230_);
    }

    public static class Builder
    extends LootItemConditionalFunction.Builder<Builder> {
        private final NumberProvider f_80801_;
        private int f_80802_ = 0;

        public Builder(NumberProvider p_165232_) {
            this.f_80801_ = p_165232_;
        }

        @Override
        protected Builder m_6477_() {
            return this;
        }

        public Builder m_80806_(int p_80807_) {
            this.f_80802_ = p_80807_;
            return this;
        }

        @Override
        public LootItemFunction m_7453_() {
            return new LootingEnchantFunction(this.m_80699_(), this.f_80801_, this.f_80802_);
        }

        @Override
        protected /* synthetic */ LootItemConditionalFunction.Builder m_6477_() {
            return this.m_6477_();
        }
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<LootingEnchantFunction> {
        @Override
        public void m_6170_(JsonObject p_80820_, LootingEnchantFunction p_80821_, JsonSerializationContext p_80822_) {
            super.m_6170_(p_80820_, p_80821_, p_80822_);
            p_80820_.add("count", p_80822_.serialize((Object)p_80821_.f_80776_));
            if (p_80821_.m_80798_()) {
                p_80820_.add("limit", p_80822_.serialize((Object)p_80821_.f_80777_));
            }
        }

        @Override
        public LootingEnchantFunction m_6821_(JsonObject p_80812_, JsonDeserializationContext p_80813_, LootItemCondition[] p_80814_) {
            int $$3 = GsonHelper.m_13824_(p_80812_, "limit", 0);
            return new LootingEnchantFunction(p_80814_, GsonHelper.m_13836_(p_80812_, "count", p_80813_, NumberProvider.class), $$3);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}


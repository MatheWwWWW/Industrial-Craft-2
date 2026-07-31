/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class EnchantWithLevelsFunction
extends LootItemConditionalFunction {
    final NumberProvider f_80471_;
    final boolean f_80472_;

    EnchantWithLevelsFunction(LootItemCondition[] p_165193_, NumberProvider p_165194_, boolean p_165195_) {
        super(p_165193_);
        this.f_80471_ = p_165194_;
        this.f_80472_ = p_165195_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80737_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return this.f_80471_.m_6231_();
    }

    @Override
    public ItemStack m_7372_(ItemStack p_80483_, LootContext p_80484_) {
        RandomSource $$2 = p_80484_.m_230907_();
        return EnchantmentHelper.m_220292_($$2, p_80483_, this.f_80471_.m_142683_(p_80484_), this.f_80472_);
    }

    public static Builder m_165196_(NumberProvider p_165197_) {
        return new Builder(p_165197_);
    }

    public static class Builder
    extends LootItemConditionalFunction.Builder<Builder> {
        private final NumberProvider f_80492_;
        private boolean f_80493_;

        public Builder(NumberProvider p_165200_) {
            this.f_80492_ = p_165200_;
        }

        @Override
        protected Builder m_6477_() {
            return this;
        }

        public Builder m_80499_() {
            this.f_80493_ = true;
            return this;
        }

        @Override
        public LootItemFunction m_7453_() {
            return new EnchantWithLevelsFunction(this.m_80699_(), this.f_80492_, this.f_80493_);
        }

        @Override
        protected /* synthetic */ LootItemConditionalFunction.Builder m_6477_() {
            return this.m_6477_();
        }
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<EnchantWithLevelsFunction> {
        @Override
        public void m_6170_(JsonObject p_80506_, EnchantWithLevelsFunction p_80507_, JsonSerializationContext p_80508_) {
            super.m_6170_(p_80506_, p_80507_, p_80508_);
            p_80506_.add("levels", p_80508_.serialize((Object)p_80507_.f_80471_));
            p_80506_.addProperty("treasure", Boolean.valueOf(p_80507_.f_80472_));
        }

        @Override
        public EnchantWithLevelsFunction m_6821_(JsonObject p_80502_, JsonDeserializationContext p_80503_, LootItemCondition[] p_80504_) {
            NumberProvider $$3 = GsonHelper.m_13836_(p_80502_, "levels", p_80503_, NumberProvider.class);
            boolean $$4 = GsonHelper.m_13855_(p_80502_, "treasure", false);
            return new EnchantWithLevelsFunction(p_80504_, $$3, $$4);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}


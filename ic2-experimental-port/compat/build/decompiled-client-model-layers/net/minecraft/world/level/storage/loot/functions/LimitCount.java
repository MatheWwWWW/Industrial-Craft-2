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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LimitCount
extends LootItemConditionalFunction {
    final IntRange f_80635_;

    LimitCount(LootItemCondition[] p_165213_, IntRange p_165214_) {
        super(p_165213_);
        this.f_80635_ = p_165214_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80749_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return this.f_80635_.m_165008_();
    }

    @Override
    public ItemStack m_7372_(ItemStack p_80644_, LootContext p_80645_) {
        int $$2 = this.f_80635_.m_165014_(p_80645_, p_80644_.m_41613_());
        p_80644_.m_41764_($$2);
        return p_80644_;
    }

    public static LootItemConditionalFunction.Builder<?> m_165215_(IntRange p_165216_) {
        return LimitCount.m_80683_(p_165219_ -> new LimitCount((LootItemCondition[])p_165219_, p_165216_));
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<LimitCount> {
        @Override
        public void m_6170_(JsonObject p_80660_, LimitCount p_80661_, JsonSerializationContext p_80662_) {
            super.m_6170_(p_80660_, p_80661_, p_80662_);
            p_80660_.add("limit", p_80662_.serialize((Object)p_80661_.f_80635_));
        }

        @Override
        public LimitCount m_6821_(JsonObject p_80656_, JsonDeserializationContext p_80657_, LootItemCondition[] p_80658_) {
            IntRange $$3 = GsonHelper.m_13836_(p_80656_, "limit", p_80657_, IntRange.class);
            return new LimitCount(p_80658_, $$3);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}


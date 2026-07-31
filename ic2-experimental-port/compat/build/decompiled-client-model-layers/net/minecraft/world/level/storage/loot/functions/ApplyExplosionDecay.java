/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ApplyExplosionDecay
extends LootItemConditionalFunction {
    ApplyExplosionDecay(LootItemCondition[] p_80029_) {
        super(p_80029_);
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80752_;
    }

    @Override
    public ItemStack m_7372_(ItemStack p_80034_, LootContext p_80035_) {
        Float $$2 = p_80035_.m_78953_(LootContextParams.f_81464_);
        if ($$2 != null) {
            RandomSource $$3 = p_80035_.m_230907_();
            float $$4 = 1.0f / $$2.floatValue();
            int $$5 = p_80034_.m_41613_();
            int $$6 = 0;
            for (int $$7 = 0; $$7 < $$5; ++$$7) {
                if (!($$3.m_188501_() <= $$4)) continue;
                ++$$6;
            }
            p_80034_.m_41764_($$6);
        }
        return p_80034_;
    }

    public static LootItemConditionalFunction.Builder<?> m_80037_() {
        return ApplyExplosionDecay.m_80683_(ApplyExplosionDecay::new);
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<ApplyExplosionDecay> {
        @Override
        public ApplyExplosionDecay m_6821_(JsonObject p_80040_, JsonDeserializationContext p_80041_, LootItemCondition[] p_80042_) {
            return new ApplyExplosionDecay(p_80042_);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}


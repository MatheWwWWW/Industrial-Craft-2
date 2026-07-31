/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class LootItemRandomChanceWithLootingCondition
implements LootItemCondition {
    final float f_81953_;
    final float f_81954_;

    LootItemRandomChanceWithLootingCondition(float p_81956_, float p_81957_) {
        this.f_81953_ = p_81956_;
        this.f_81954_ = p_81957_;
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81814_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return ImmutableSet.of(LootContextParams.f_81458_);
    }

    @Override
    public boolean test(LootContext p_81967_) {
        Entity $$1 = p_81967_.m_78953_(LootContextParams.f_81458_);
        int $$2 = 0;
        if ($$1 instanceof LivingEntity) {
            $$2 = EnchantmentHelper.m_44930_((LivingEntity)$$1);
        }
        return p_81967_.m_230907_().m_188501_() < this.f_81953_ + (float)$$2 * this.f_81954_;
    }

    public static LootItemCondition.Builder m_81963_(float p_81964_, float p_81965_) {
        return () -> new LootItemRandomChanceWithLootingCondition(p_81964_, p_81965_);
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<LootItemRandomChanceWithLootingCondition> {
        @Override
        public void m_6170_(JsonObject p_81983_, LootItemRandomChanceWithLootingCondition p_81984_, JsonSerializationContext p_81985_) {
            p_81983_.addProperty("chance", (Number)Float.valueOf(p_81984_.f_81953_));
            p_81983_.addProperty("looting_multiplier", (Number)Float.valueOf(p_81984_.f_81954_));
        }

        @Override
        public LootItemRandomChanceWithLootingCondition m_7561_(JsonObject p_81991_, JsonDeserializationContext p_81992_) {
            return new LootItemRandomChanceWithLootingCondition(GsonHelper.m_13915_(p_81991_, "chance"), GsonHelper.m_13915_(p_81991_, "looting_multiplier"));
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


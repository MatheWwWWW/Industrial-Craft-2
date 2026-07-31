/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class LootItemRandomChanceCondition
implements LootItemCondition {
    final float f_81921_;

    LootItemRandomChanceCondition(float p_81923_) {
        this.f_81921_ = p_81923_;
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81813_;
    }

    @Override
    public boolean test(LootContext p_81930_) {
        return p_81930_.m_230907_().m_188501_() < this.f_81921_;
    }

    public static LootItemCondition.Builder m_81927_(float p_81928_) {
        return () -> new LootItemRandomChanceCondition(p_81928_);
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<LootItemRandomChanceCondition> {
        @Override
        public void m_6170_(JsonObject p_81943_, LootItemRandomChanceCondition p_81944_, JsonSerializationContext p_81945_) {
            p_81943_.addProperty("chance", (Number)Float.valueOf(p_81944_.f_81921_));
        }

        @Override
        public LootItemRandomChanceCondition m_7561_(JsonObject p_81951_, JsonDeserializationContext p_81952_) {
            return new LootItemRandomChanceCondition(GsonHelper.m_13915_(p_81951_, "chance"));
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


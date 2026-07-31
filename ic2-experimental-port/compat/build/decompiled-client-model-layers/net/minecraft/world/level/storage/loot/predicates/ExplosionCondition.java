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
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class ExplosionCondition
implements LootItemCondition {
    static final ExplosionCondition f_81654_ = new ExplosionCondition();

    private ExplosionCondition() {
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81821_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return ImmutableSet.of(LootContextParams.f_81464_);
    }

    @Override
    public boolean test(LootContext p_81659_) {
        Float $$1 = p_81659_.m_78953_(LootContextParams.f_81464_);
        if ($$1 != null) {
            RandomSource $$2 = p_81659_.m_230907_();
            float $$3 = 1.0f / $$1.floatValue();
            return $$2.m_188501_() <= $$3;
        }
        return true;
    }

    public static LootItemCondition.Builder m_81661_() {
        return () -> f_81654_;
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<ExplosionCondition> {
        @Override
        public void m_6170_(JsonObject p_81671_, ExplosionCondition p_81672_, JsonSerializationContext p_81673_) {
        }

        @Override
        public ExplosionCondition m_7561_(JsonObject p_81679_, JsonDeserializationContext p_81680_) {
            return f_81654_;
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


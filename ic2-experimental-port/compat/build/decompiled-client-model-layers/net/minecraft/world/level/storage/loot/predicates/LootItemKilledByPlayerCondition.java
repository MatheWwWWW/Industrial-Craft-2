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
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class LootItemKilledByPlayerCondition
implements LootItemCondition {
    static final LootItemKilledByPlayerCondition f_81894_ = new LootItemKilledByPlayerCondition();

    private LootItemKilledByPlayerCondition() {
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81816_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return ImmutableSet.of(LootContextParams.f_81456_);
    }

    @Override
    public boolean test(LootContext p_81899_) {
        return p_81899_.m_78936_(LootContextParams.f_81456_);
    }

    public static LootItemCondition.Builder m_81901_() {
        return () -> f_81894_;
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<LootItemKilledByPlayerCondition> {
        @Override
        public void m_6170_(JsonObject p_81911_, LootItemKilledByPlayerCondition p_81912_, JsonSerializationContext p_81913_) {
        }

        @Override
        public LootItemKilledByPlayerCondition m_7561_(JsonObject p_81919_, JsonDeserializationContext p_81920_) {
            return f_81894_;
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


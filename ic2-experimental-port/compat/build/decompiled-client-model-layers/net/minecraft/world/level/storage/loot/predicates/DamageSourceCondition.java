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
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.phys.Vec3;

public class DamageSourceCondition
implements LootItemCondition {
    final DamageSourcePredicate f_81582_;

    DamageSourceCondition(DamageSourcePredicate p_81584_) {
        this.f_81582_ = p_81584_;
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81822_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return ImmutableSet.of(LootContextParams.f_81460_, LootContextParams.f_81457_);
    }

    @Override
    public boolean test(LootContext p_81592_) {
        DamageSource $$1 = p_81592_.m_78953_(LootContextParams.f_81457_);
        Vec3 $$2 = p_81592_.m_78953_(LootContextParams.f_81460_);
        return $$2 != null && $$1 != null && this.f_81582_.m_25444_(p_81592_.m_78952_(), $$2, $$1);
    }

    public static LootItemCondition.Builder m_81589_(DamageSourcePredicate.Builder p_81590_) {
        return () -> new DamageSourceCondition(p_81590_.m_25476_());
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<DamageSourceCondition> {
        @Override
        public void m_6170_(JsonObject p_81605_, DamageSourceCondition p_81606_, JsonSerializationContext p_81607_) {
            p_81605_.add("predicate", p_81606_.f_81582_.m_25443_());
        }

        @Override
        public DamageSourceCondition m_7561_(JsonObject p_81613_, JsonDeserializationContext p_81614_) {
            DamageSourcePredicate $$2 = DamageSourcePredicate.m_25451_(p_81613_.get("predicate"));
            return new DamageSourceCondition($$2);
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


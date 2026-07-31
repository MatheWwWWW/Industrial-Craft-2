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
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class MatchTool
implements LootItemCondition {
    final ItemPredicate f_81993_;

    public MatchTool(ItemPredicate p_81995_) {
        this.f_81993_ = p_81995_;
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81819_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return ImmutableSet.of(LootContextParams.f_81463_);
    }

    @Override
    public boolean test(LootContext p_82000_) {
        ItemStack $$1 = p_82000_.m_78953_(LootContextParams.f_81463_);
        return $$1 != null && this.f_81993_.m_45049_($$1);
    }

    public static LootItemCondition.Builder m_81997_(ItemPredicate.Builder p_81998_) {
        return () -> new MatchTool(p_81998_.m_45077_());
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<MatchTool> {
        @Override
        public void m_6170_(JsonObject p_82013_, MatchTool p_82014_, JsonSerializationContext p_82015_) {
            p_82013_.add("predicate", p_82014_.f_81993_.m_45048_());
        }

        @Override
        public MatchTool m_7561_(JsonObject p_82021_, JsonDeserializationContext p_82022_) {
            ItemPredicate $$2 = ItemPredicate.m_45051_(p_82021_.get("predicate"));
            return new MatchTool($$2);
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


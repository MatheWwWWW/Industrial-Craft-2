/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

public class EntityHasScoreCondition
implements LootItemCondition {
    final Map<String, IntRange> f_81615_;
    final LootContext.EntityTarget f_81616_;

    EntityHasScoreCondition(Map<String, IntRange> p_81618_, LootContext.EntityTarget p_81619_) {
        this.f_81615_ = ImmutableMap.copyOf(p_81618_);
        this.f_81616_ = p_81619_;
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81817_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return (Set)Stream.concat(Stream.of(this.f_81616_.m_79003_()), this.f_81615_.values().stream().flatMap(p_165487_ -> p_165487_.m_165008_().stream())).collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public boolean test(LootContext p_81631_) {
        Entity $$1 = p_81631_.m_78953_(this.f_81616_.m_79003_());
        if ($$1 == null) {
            return false;
        }
        Scoreboard $$2 = $$1.f_19853_.m_6188_();
        for (Map.Entry<String, IntRange> $$3 : this.f_81615_.entrySet()) {
            if (this.m_165490_(p_81631_, $$1, $$2, $$3.getKey(), $$3.getValue())) continue;
            return false;
        }
        return true;
    }

    protected boolean m_165490_(LootContext p_165491_, Entity p_165492_, Scoreboard p_165493_, String p_165494_, IntRange p_165495_) {
        Objective $$5 = p_165493_.m_83477_(p_165494_);
        if ($$5 == null) {
            return false;
        }
        String $$6 = p_165492_.m_6302_();
        if (!p_165493_.m_83461_($$6, $$5)) {
            return false;
        }
        return p_165495_.m_165028_(p_165491_, p_165493_.m_83471_($$6, $$5).m_83400_());
    }

    public static Builder m_165488_(LootContext.EntityTarget p_165489_) {
        return new Builder(p_165489_);
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Builder
    implements LootItemCondition.Builder {
        private final Map<String, IntRange> f_165496_ = Maps.newHashMap();
        private final LootContext.EntityTarget f_165497_;

        public Builder(LootContext.EntityTarget p_165499_) {
            this.f_165497_ = p_165499_;
        }

        public Builder m_165500_(String p_165501_, IntRange p_165502_) {
            this.f_165496_.put(p_165501_, p_165502_);
            return this;
        }

        @Override
        public LootItemCondition m_6409_() {
            return new EntityHasScoreCondition(this.f_165496_, this.f_165497_);
        }
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<EntityHasScoreCondition> {
        @Override
        public void m_6170_(JsonObject p_81644_, EntityHasScoreCondition p_81645_, JsonSerializationContext p_81646_) {
            JsonObject $$3 = new JsonObject();
            for (Map.Entry<String, IntRange> $$4 : p_81645_.f_81615_.entrySet()) {
                $$3.add($$4.getKey(), p_81646_.serialize((Object)$$4.getValue()));
            }
            p_81644_.add("scores", (JsonElement)$$3);
            p_81644_.add("entity", p_81646_.serialize((Object)p_81645_.f_81616_));
        }

        @Override
        public EntityHasScoreCondition m_7561_(JsonObject p_81652_, JsonDeserializationContext p_81653_) {
            Set $$2 = GsonHelper.m_13930_(p_81652_, "scores").entrySet();
            LinkedHashMap $$3 = Maps.newLinkedHashMap();
            for (Map.Entry $$4 : $$2) {
                $$3.put((String)$$4.getKey(), GsonHelper.m_13808_((JsonElement)$$4.getValue(), "score", p_81653_, IntRange.class));
            }
            return new EntityHasScoreCondition($$3, GsonHelper.m_13836_(p_81652_, "entity", p_81653_, LootContext.EntityTarget.class));
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


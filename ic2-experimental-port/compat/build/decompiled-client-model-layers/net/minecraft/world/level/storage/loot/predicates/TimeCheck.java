/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class TimeCheck
implements LootItemCondition {
    @Nullable
    final Long f_82023_;
    final IntRange f_82024_;

    TimeCheck(@Nullable Long p_165507_, IntRange p_165508_) {
        this.f_82023_ = p_165507_;
        this.f_82024_ = p_165508_;
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81826_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return this.f_82024_.m_165008_();
    }

    @Override
    public boolean test(LootContext p_82033_) {
        ServerLevel $$1 = p_82033_.m_78952_();
        long $$2 = $$1.m_46468_();
        if (this.f_82023_ != null) {
            $$2 %= this.f_82023_.longValue();
        }
        return this.f_82024_.m_165028_(p_82033_, (int)$$2);
    }

    public static Builder m_165509_(IntRange p_165510_) {
        return new Builder(p_165510_);
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Builder
    implements LootItemCondition.Builder {
        @Nullable
        private Long f_165512_;
        private final IntRange f_165513_;

        public Builder(IntRange p_165515_) {
            this.f_165513_ = p_165515_;
        }

        public Builder m_165516_(long p_165517_) {
            this.f_165512_ = p_165517_;
            return this;
        }

        @Override
        public TimeCheck m_6409_() {
            return new TimeCheck(this.f_165512_, this.f_165513_);
        }

        @Override
        public /* synthetic */ LootItemCondition m_6409_() {
            return this.m_6409_();
        }
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<TimeCheck> {
        @Override
        public void m_6170_(JsonObject p_82046_, TimeCheck p_82047_, JsonSerializationContext p_82048_) {
            p_82046_.addProperty("period", (Number)p_82047_.f_82023_);
            p_82046_.add("value", p_82048_.serialize((Object)p_82047_.f_82024_));
        }

        @Override
        public TimeCheck m_7561_(JsonObject p_82054_, JsonDeserializationContext p_82055_) {
            Long $$2 = p_82054_.has("period") ? Long.valueOf(GsonHelper.m_13921_(p_82054_, "period")) : null;
            IntRange $$3 = GsonHelper.m_13836_(p_82054_, "value", p_82055_, IntRange.class);
            return new TimeCheck($$2, $$3);
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


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
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class WeatherCheck
implements LootItemCondition {
    @Nullable
    final Boolean f_82056_;
    @Nullable
    final Boolean f_82057_;

    WeatherCheck(@Nullable Boolean p_82059_, @Nullable Boolean p_82060_) {
        this.f_82056_ = p_82059_;
        this.f_82057_ = p_82060_;
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81824_;
    }

    @Override
    public boolean test(LootContext p_82066_) {
        ServerLevel $$1 = p_82066_.m_78952_();
        if (this.f_82056_ != null && this.f_82056_.booleanValue() != $$1.m_46471_()) {
            return false;
        }
        return this.f_82057_ == null || this.f_82057_.booleanValue() == $$1.m_46470_();
    }

    public static Builder m_165552_() {
        return new Builder();
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Builder
    implements LootItemCondition.Builder {
        @Nullable
        private Boolean f_165553_;
        @Nullable
        private Boolean f_165554_;

        public Builder m_165556_(@Nullable Boolean p_165557_) {
            this.f_165553_ = p_165557_;
            return this;
        }

        public Builder m_165559_(@Nullable Boolean p_165560_) {
            this.f_165554_ = p_165560_;
            return this;
        }

        @Override
        public WeatherCheck m_6409_() {
            return new WeatherCheck(this.f_165553_, this.f_165554_);
        }

        @Override
        public /* synthetic */ LootItemCondition m_6409_() {
            return this.m_6409_();
        }
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<WeatherCheck> {
        @Override
        public void m_6170_(JsonObject p_82079_, WeatherCheck p_82080_, JsonSerializationContext p_82081_) {
            p_82079_.addProperty("raining", p_82080_.f_82056_);
            p_82079_.addProperty("thundering", p_82080_.f_82057_);
        }

        @Override
        public WeatherCheck m_7561_(JsonObject p_82087_, JsonDeserializationContext p_82088_) {
            Boolean $$2 = p_82087_.has("raining") ? Boolean.valueOf(GsonHelper.m_13912_(p_82087_, "raining")) : null;
            Boolean $$3 = p_82087_.has("thundering") ? Boolean.valueOf(GsonHelper.m_13912_(p_82087_, "thundering")) : null;
            return new WeatherCheck($$2, $$3);
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class AlternativeLootItemCondition
implements LootItemCondition {
    final LootItemCondition[] f_81468_;
    private final Predicate<LootContext> f_81469_;

    AlternativeLootItemCondition(LootItemCondition[] p_81471_) {
        this.f_81468_ = p_81471_;
        this.f_81469_ = LootItemConditions.m_81841_(p_81471_);
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81812_;
    }

    @Override
    public final boolean test(LootContext p_81476_) {
        return this.f_81469_.test(p_81476_);
    }

    @Override
    public void m_6169_(ValidationContext p_81478_) {
        LootItemCondition.super.m_6169_(p_81478_);
        for (int $$1 = 0; $$1 < this.f_81468_.length; ++$$1) {
            this.f_81468_[$$1].m_6169_(p_81478_.m_79365_(".term[" + $$1 + "]"));
        }
    }

    public static Builder m_81481_(LootItemCondition.Builder ... p_81482_) {
        return new Builder(p_81482_);
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Builder
    implements LootItemCondition.Builder {
        private final List<LootItemCondition> f_81486_ = Lists.newArrayList();

        public Builder(LootItemCondition.Builder ... p_81488_) {
            for (LootItemCondition.Builder $$1 : p_81488_) {
                this.f_81486_.add($$1.m_6409_());
            }
        }

        @Override
        public Builder m_7818_(LootItemCondition.Builder p_81490_) {
            this.f_81486_.add(p_81490_.m_6409_());
            return this;
        }

        @Override
        public LootItemCondition m_6409_() {
            return new AlternativeLootItemCondition(this.f_81486_.toArray(new LootItemCondition[0]));
        }
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<AlternativeLootItemCondition> {
        @Override
        public void m_6170_(JsonObject p_81497_, AlternativeLootItemCondition p_81498_, JsonSerializationContext p_81499_) {
            p_81497_.add("terms", p_81499_.serialize((Object)p_81498_.f_81468_));
        }

        @Override
        public AlternativeLootItemCondition m_7561_(JsonObject p_81505_, JsonDeserializationContext p_81506_) {
            LootItemCondition[] $$2 = GsonHelper.m_13836_(p_81505_, "terms", p_81506_, LootItemCondition[].class);
            return new AlternativeLootItemCondition($$2);
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


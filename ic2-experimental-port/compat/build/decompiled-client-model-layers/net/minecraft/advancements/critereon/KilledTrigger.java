/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class KilledTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    final ResourceLocation f_48100_;

    public KilledTrigger(ResourceLocation p_48102_) {
        this.f_48100_ = p_48102_;
    }

    @Override
    public ResourceLocation m_7295_() {
        return this.f_48100_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_48116_, EntityPredicate.Composite p_48117_, DeserializationContext p_48118_) {
        return new TriggerInstance(this.f_48100_, p_48117_, EntityPredicate.Composite.m_36677_(p_48116_, "entity", p_48118_), DamageSourcePredicate.m_25451_(p_48116_.get("killing_blow")));
    }

    public void m_48104_(ServerPlayer p_48105_, Entity p_48106_, DamageSource p_48107_) {
        LootContext $$3 = EntityPredicate.m_36616_(p_48105_, p_48106_);
        this.m_66234_(p_48105_, p_48112_ -> p_48112_.m_48130_(p_48105_, $$3, p_48107_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final EntityPredicate.Composite f_48123_;
        private final DamageSourcePredicate f_48124_;

        public TriggerInstance(ResourceLocation p_48126_, EntityPredicate.Composite p_48127_, EntityPredicate.Composite p_48128_, DamageSourcePredicate p_48129_) {
            super(p_48126_, p_48127_);
            this.f_48123_ = p_48128_;
            this.f_48124_ = p_48129_;
        }

        public static TriggerInstance m_152108_(EntityPredicate p_152109_) {
            return new TriggerInstance(CriteriaTriggers.f_10568_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_152109_), DamageSourcePredicate.f_25420_);
        }

        public static TriggerInstance m_48134_(EntityPredicate.Builder p_48135_) {
            return new TriggerInstance(CriteriaTriggers.f_10568_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_48135_.m_36662_()), DamageSourcePredicate.f_25420_);
        }

        public static TriggerInstance m_48141_() {
            return new TriggerInstance(CriteriaTriggers.f_10568_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, DamageSourcePredicate.f_25420_);
        }

        public static TriggerInstance m_152113_(EntityPredicate p_152114_, DamageSourcePredicate p_152115_) {
            return new TriggerInstance(CriteriaTriggers.f_10568_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_152114_), p_152115_);
        }

        public static TriggerInstance m_152105_(EntityPredicate.Builder p_152106_, DamageSourcePredicate p_152107_) {
            return new TriggerInstance(CriteriaTriggers.f_10568_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_152106_.m_36662_()), p_152107_);
        }

        public static TriggerInstance m_152110_(EntityPredicate p_152111_, DamageSourcePredicate.Builder p_152112_) {
            return new TriggerInstance(CriteriaTriggers.f_10568_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_152111_), p_152112_.m_25476_());
        }

        public static TriggerInstance m_48136_(EntityPredicate.Builder p_48137_, DamageSourcePredicate.Builder p_48138_) {
            return new TriggerInstance(CriteriaTriggers.f_10568_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_48137_.m_36662_()), p_48138_.m_25476_());
        }

        public static TriggerInstance m_220237_() {
            return new TriggerInstance(CriteriaTriggers.f_215656_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, DamageSourcePredicate.f_25420_);
        }

        public static TriggerInstance m_152124_(EntityPredicate p_152125_) {
            return new TriggerInstance(CriteriaTriggers.f_10569_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_152125_), DamageSourcePredicate.f_25420_);
        }

        public static TriggerInstance m_152116_(EntityPredicate.Builder p_152117_) {
            return new TriggerInstance(CriteriaTriggers.f_10569_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_152117_.m_36662_()), DamageSourcePredicate.f_25420_);
        }

        public static TriggerInstance m_48142_() {
            return new TriggerInstance(CriteriaTriggers.f_10569_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, DamageSourcePredicate.f_25420_);
        }

        public static TriggerInstance m_152129_(EntityPredicate p_152130_, DamageSourcePredicate p_152131_) {
            return new TriggerInstance(CriteriaTriggers.f_10569_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_152130_), p_152131_);
        }

        public static TriggerInstance m_152121_(EntityPredicate.Builder p_152122_, DamageSourcePredicate p_152123_) {
            return new TriggerInstance(CriteriaTriggers.f_10569_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_152122_.m_36662_()), p_152123_);
        }

        public static TriggerInstance m_152126_(EntityPredicate p_152127_, DamageSourcePredicate.Builder p_152128_) {
            return new TriggerInstance(CriteriaTriggers.f_10569_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_152127_), p_152128_.m_25476_());
        }

        public static TriggerInstance m_152118_(EntityPredicate.Builder p_152119_, DamageSourcePredicate.Builder p_152120_) {
            return new TriggerInstance(CriteriaTriggers.f_10569_.f_48100_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_152119_.m_36662_()), p_152120_.m_25476_());
        }

        public boolean m_48130_(ServerPlayer p_48131_, LootContext p_48132_, DamageSource p_48133_) {
            if (!this.f_48124_.m_25448_(p_48131_, p_48133_)) {
                return false;
            }
            return this.f_48123_.m_36681_(p_48132_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_48140_) {
            JsonObject $$1 = super.m_7683_(p_48140_);
            $$1.add("entity", this.f_48123_.m_36675_(p_48140_));
            $$1.add("killing_blow", this.f_48124_.m_25443_());
            return $$1;
        }
    }
}


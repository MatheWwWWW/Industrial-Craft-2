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
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class DistanceTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    final ResourceLocation f_186161_;

    public DistanceTrigger(ResourceLocation p_186163_) {
        this.f_186161_ = p_186163_;
    }

    @Override
    public ResourceLocation m_7295_() {
        return this.f_186161_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_186174_, EntityPredicate.Composite p_186175_, DeserializationContext p_186176_) {
        LocationPredicate $$3 = LocationPredicate.m_52629_(p_186174_.get("start_position"));
        DistancePredicate $$4 = DistancePredicate.m_26264_(p_186174_.get("distance"));
        return new TriggerInstance(this.f_186161_, p_186175_, $$3, $$4);
    }

    public void m_186165_(ServerPlayer p_186166_, Vec3 p_186167_) {
        Vec3 $$2 = p_186166_.m_20182_();
        this.m_66234_(p_186166_, p_186172_ -> p_186172_.m_186188_(p_186166_.m_9236_(), p_186167_, $$2));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final LocationPredicate f_186181_;
        private final DistancePredicate f_186182_;

        public TriggerInstance(ResourceLocation p_186184_, EntityPredicate.Composite p_186185_, LocationPredicate p_186186_, DistancePredicate p_186187_) {
            super(p_186184_, p_186185_);
            this.f_186181_ = p_186186_;
            this.f_186182_ = p_186187_;
        }

        public static TriggerInstance m_186197_(EntityPredicate.Builder p_186198_, DistancePredicate p_186199_, LocationPredicate p_186200_) {
            return new TriggerInstance(CriteriaTriggers.f_184759_.f_186161_, EntityPredicate.Composite.m_36673_(p_186198_.m_36662_()), p_186200_, p_186199_);
        }

        public static TriggerInstance m_186194_(EntityPredicate.Builder p_186195_, DistancePredicate p_186196_) {
            return new TriggerInstance(CriteriaTriggers.f_184760_.f_186161_, EntityPredicate.Composite.m_36673_(p_186195_.m_36662_()), LocationPredicate.f_52592_, p_186196_);
        }

        public static TriggerInstance m_186192_(DistancePredicate p_186193_) {
            return new TriggerInstance(CriteriaTriggers.f_10552_.f_186161_, EntityPredicate.Composite.f_36667_, LocationPredicate.f_52592_, p_186193_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_186202_) {
            JsonObject $$1 = super.m_7683_(p_186202_);
            $$1.add("start_position", this.f_186181_.m_52616_());
            $$1.add("distance", this.f_186182_.m_26254_());
            return $$1;
        }

        public boolean m_186188_(ServerLevel p_186189_, Vec3 p_186190_, Vec3 p_186191_) {
            if (!this.f_186181_.m_52617_(p_186189_, p_186190_.f_82479_, p_186190_.f_82480_, p_186190_.f_82481_)) {
                return false;
            }
            return this.f_186182_.m_26255_(p_186190_.f_82479_, p_186190_.f_82480_, p_186190_.f_82481_, p_186191_.f_82479_, p_186191_.f_82480_, p_186191_.f_82481_);
        }
    }
}


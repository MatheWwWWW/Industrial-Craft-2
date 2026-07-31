/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class LevitationTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_49112_ = new ResourceLocation("levitation");

    @Override
    public ResourceLocation m_7295_() {
        return f_49112_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_49126_, EntityPredicate.Composite p_49127_, DeserializationContext p_49128_) {
        DistancePredicate $$3 = DistancePredicate.m_26264_(p_49126_.get("distance"));
        MinMaxBounds.Ints $$4 = MinMaxBounds.Ints.m_55373_(p_49126_.get("duration"));
        return new TriggerInstance(p_49127_, $$3, $$4);
    }

    public void m_49116_(ServerPlayer p_49117_, Vec3 p_49118_, int p_49119_) {
        this.m_66234_(p_49117_, p_49124_ -> p_49124_.m_49140_(p_49117_, p_49118_, p_49119_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final DistancePredicate f_49134_;
        private final MinMaxBounds.Ints f_49135_;

        public TriggerInstance(EntityPredicate.Composite p_49137_, DistancePredicate p_49138_, MinMaxBounds.Ints p_49139_) {
            super(f_49112_, p_49137_);
            this.f_49134_ = p_49138_;
            this.f_49135_ = p_49139_;
        }

        public static TriggerInstance m_49144_(DistancePredicate p_49145_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_49145_, MinMaxBounds.Ints.f_55364_);
        }

        public boolean m_49140_(ServerPlayer p_49141_, Vec3 p_49142_, int p_49143_) {
            if (!this.f_49134_.m_26255_(p_49142_.f_82479_, p_49142_.f_82480_, p_49142_.f_82481_, p_49141_.m_20185_(), p_49141_.m_20186_(), p_49141_.m_20189_())) {
                return false;
            }
            return this.f_49135_.m_55390_(p_49143_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_49147_) {
            JsonObject $$1 = super.m_7683_(p_49147_);
            $$1.add("distance", this.f_49134_.m_26254_());
            $$1.add("duration", this.f_49135_.m_55328_());
            return $$1;
        }
    }
}


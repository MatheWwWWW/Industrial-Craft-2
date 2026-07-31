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
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ConstructBeaconTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_22742_ = new ResourceLocation("construct_beacon");

    @Override
    public ResourceLocation m_7295_() {
        return f_22742_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_22753_, EntityPredicate.Composite p_22754_, DeserializationContext p_22755_) {
        MinMaxBounds.Ints $$3 = MinMaxBounds.Ints.m_55373_(p_22753_.get("level"));
        return new TriggerInstance(p_22754_, $$3);
    }

    public void m_148029_(ServerPlayer p_148030_, int p_148031_) {
        this.m_66234_(p_148030_, p_148028_ -> p_148028_.m_148032_(p_148031_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final MinMaxBounds.Ints f_22761_;

        public TriggerInstance(EntityPredicate.Composite p_22763_, MinMaxBounds.Ints p_22764_) {
            super(f_22742_, p_22763_);
            this.f_22761_ = p_22764_;
        }

        public static TriggerInstance m_148034_() {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, MinMaxBounds.Ints.f_55364_);
        }

        public static TriggerInstance m_22765_(MinMaxBounds.Ints p_22766_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_22766_);
        }

        public boolean m_148032_(int p_148033_) {
            return this.f_22761_.m_55390_(p_148033_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_22770_) {
            JsonObject $$1 = super.m_7683_(p_22770_);
            $$1.add("level", this.f_22761_.m_55328_());
            return $$1;
        }
    }
}


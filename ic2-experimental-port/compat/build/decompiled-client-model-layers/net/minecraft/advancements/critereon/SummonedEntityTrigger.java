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
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class SummonedEntityTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_68252_ = new ResourceLocation("summoned_entity");

    @Override
    public ResourceLocation m_7295_() {
        return f_68252_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_68260_, EntityPredicate.Composite p_68261_, DeserializationContext p_68262_) {
        EntityPredicate.Composite $$3 = EntityPredicate.Composite.m_36677_(p_68260_, "entity", p_68262_);
        return new TriggerInstance(p_68261_, $$3);
    }

    public void m_68256_(ServerPlayer p_68257_, Entity p_68258_) {
        LootContext $$2 = EntityPredicate.m_36616_(p_68257_, p_68258_);
        this.m_66234_(p_68257_, p_68265_ -> p_68265_.m_68279_($$2));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final EntityPredicate.Composite f_68271_;

        public TriggerInstance(EntityPredicate.Composite p_68273_, EntityPredicate.Composite p_68274_) {
            super(f_68252_, p_68273_);
            this.f_68271_ = p_68274_;
        }

        public static TriggerInstance m_68275_(EntityPredicate.Builder p_68276_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_68276_.m_36662_()));
        }

        public boolean m_68279_(LootContext p_68280_) {
            return this.f_68271_.m_36681_(p_68280_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_68278_) {
            JsonObject $$1 = super.m_7683_(p_68278_);
            $$1.add("entity", this.f_68271_.m_36675_(p_68278_));
            return $$1;
        }
    }
}


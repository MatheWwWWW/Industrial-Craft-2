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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.storage.loot.LootContext;

public class TameAnimalTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_68825_ = new ResourceLocation("tame_animal");

    @Override
    public ResourceLocation m_7295_() {
        return f_68825_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_68833_, EntityPredicate.Composite p_68834_, DeserializationContext p_68835_) {
        EntityPredicate.Composite $$3 = EntityPredicate.Composite.m_36677_(p_68833_, "entity", p_68835_);
        return new TriggerInstance(p_68834_, $$3);
    }

    public void m_68829_(ServerPlayer p_68830_, Animal p_68831_) {
        LootContext $$2 = EntityPredicate.m_36616_(p_68830_, p_68831_);
        this.m_66234_(p_68830_, p_68838_ -> p_68838_.m_68852_($$2));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final EntityPredicate.Composite f_68844_;

        public TriggerInstance(EntityPredicate.Composite p_68846_, EntityPredicate.Composite p_68847_) {
            super(f_68825_, p_68846_);
            this.f_68844_ = p_68847_;
        }

        public static TriggerInstance m_68854_() {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_);
        }

        public static TriggerInstance m_68848_(EntityPredicate p_68849_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_68849_));
        }

        public boolean m_68852_(LootContext p_68853_) {
            return this.f_68844_.m_36681_(p_68853_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_68851_) {
            JsonObject $$1 = super.m_7683_(p_68851_);
            $$1.add("entity", this.f_68844_.m_36675_(p_68851_));
            return $$1;
        }
    }
}


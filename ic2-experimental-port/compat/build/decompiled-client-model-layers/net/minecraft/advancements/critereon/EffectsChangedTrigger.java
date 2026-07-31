/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class EffectsChangedTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_26756_ = new ResourceLocation("effects_changed");

    @Override
    public ResourceLocation m_7295_() {
        return f_26756_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_26766_, EntityPredicate.Composite p_26767_, DeserializationContext p_26768_) {
        MobEffectsPredicate $$3 = MobEffectsPredicate.m_56559_(p_26766_.get("effects"));
        EntityPredicate.Composite $$4 = EntityPredicate.Composite.m_36677_(p_26766_, "source", p_26768_);
        return new TriggerInstance(p_26767_, $$3, $$4);
    }

    public void m_149262_(ServerPlayer p_149263_, @Nullable Entity p_149264_) {
        LootContext $$2 = p_149264_ != null ? EntityPredicate.m_36616_(p_149263_, p_149264_) : null;
        this.m_66234_(p_149263_, p_149268_ -> p_149268_.m_149274_(p_149263_, $$2));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final MobEffectsPredicate f_26774_;
        private final EntityPredicate.Composite f_149269_;

        public TriggerInstance(EntityPredicate.Composite p_149271_, MobEffectsPredicate p_149272_, EntityPredicate.Composite p_149273_) {
            super(f_26756_, p_149271_);
            this.f_26774_ = p_149272_;
            this.f_149269_ = p_149273_;
        }

        public static TriggerInstance m_26780_(MobEffectsPredicate p_26781_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_26781_, EntityPredicate.Composite.f_36667_);
        }

        public static TriggerInstance m_149277_(EntityPredicate p_149278_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, MobEffectsPredicate.f_56547_, EntityPredicate.Composite.m_36673_(p_149278_));
        }

        public boolean m_149274_(ServerPlayer p_149275_, @Nullable LootContext p_149276_) {
            if (!this.f_26774_.m_56557_(p_149275_)) {
                return false;
            }
            return this.f_149269_ == EntityPredicate.Composite.f_36667_ || p_149276_ != null && this.f_149269_.m_36681_(p_149276_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_26783_) {
            JsonObject $$1 = super.m_7683_(p_26783_);
            $$1.add("effects", this.f_26774_.m_56565_());
            $$1.add("source", this.f_149269_.m_36675_(p_26783_));
            return $$1;
        }
    }
}


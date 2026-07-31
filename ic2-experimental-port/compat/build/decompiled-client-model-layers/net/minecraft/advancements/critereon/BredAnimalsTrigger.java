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
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.storage.loot.LootContext;

public class BredAnimalsTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_18636_ = new ResourceLocation("bred_animals");

    @Override
    public ResourceLocation m_7295_() {
        return f_18636_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_18646_, EntityPredicate.Composite p_18647_, DeserializationContext p_18648_) {
        EntityPredicate.Composite $$3 = EntityPredicate.Composite.m_36677_(p_18646_, "parent", p_18648_);
        EntityPredicate.Composite $$4 = EntityPredicate.Composite.m_36677_(p_18646_, "partner", p_18648_);
        EntityPredicate.Composite $$5 = EntityPredicate.Composite.m_36677_(p_18646_, "child", p_18648_);
        return new TriggerInstance(p_18647_, $$3, $$4, $$5);
    }

    public void m_147278_(ServerPlayer p_147279_, Animal p_147280_, Animal p_147281_, @Nullable AgeableMob p_147282_) {
        LootContext $$4 = EntityPredicate.m_36616_(p_147279_, p_147280_);
        LootContext $$5 = EntityPredicate.m_36616_(p_147279_, p_147281_);
        LootContext $$6 = p_147282_ != null ? EntityPredicate.m_36616_(p_147279_, p_147282_) : null;
        this.m_66234_(p_147279_, p_18653_ -> p_18653_.m_18675_($$4, $$5, $$6));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final EntityPredicate.Composite f_18659_;
        private final EntityPredicate.Composite f_18660_;
        private final EntityPredicate.Composite f_18661_;

        public TriggerInstance(EntityPredicate.Composite p_18663_, EntityPredicate.Composite p_18664_, EntityPredicate.Composite p_18665_, EntityPredicate.Composite p_18666_) {
            super(f_18636_, p_18663_);
            this.f_18659_ = p_18664_;
            this.f_18660_ = p_18665_;
            this.f_18661_ = p_18666_;
        }

        public static TriggerInstance m_18679_() {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_);
        }

        public static TriggerInstance m_18667_(EntityPredicate.Builder p_18668_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_18668_.m_36662_()));
        }

        public static TriggerInstance m_18669_(EntityPredicate p_18670_, EntityPredicate p_18671_, EntityPredicate p_18672_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_18670_), EntityPredicate.Composite.m_36673_(p_18671_), EntityPredicate.Composite.m_36673_(p_18672_));
        }

        public boolean m_18675_(LootContext p_18676_, LootContext p_18677_, @Nullable LootContext p_18678_) {
            if (!(this.f_18661_ == EntityPredicate.Composite.f_36667_ || p_18678_ != null && this.f_18661_.m_36681_(p_18678_))) {
                return false;
            }
            return this.f_18659_.m_36681_(p_18676_) && this.f_18660_.m_36681_(p_18677_) || this.f_18659_.m_36681_(p_18677_) && this.f_18660_.m_36681_(p_18676_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_18674_) {
            JsonObject $$1 = super.m_7683_(p_18674_);
            $$1.add("parent", this.f_18659_.m_36675_(p_18674_));
            $$1.add("partner", this.f_18660_.m_36675_(p_18674_));
            $$1.add("child", this.f_18661_.m_36675_(p_18674_));
            return $$1;
        }
    }
}


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
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.storage.loot.LootContext;

public class CuredZombieVillagerTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_24270_ = new ResourceLocation("cured_zombie_villager");

    @Override
    public ResourceLocation m_7295_() {
        return f_24270_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_24279_, EntityPredicate.Composite p_24280_, DeserializationContext p_24281_) {
        EntityPredicate.Composite $$3 = EntityPredicate.Composite.m_36677_(p_24279_, "zombie", p_24281_);
        EntityPredicate.Composite $$4 = EntityPredicate.Composite.m_36677_(p_24279_, "villager", p_24281_);
        return new TriggerInstance(p_24280_, $$3, $$4);
    }

    public void m_24274_(ServerPlayer p_24275_, Zombie p_24276_, Villager p_24277_) {
        LootContext $$3 = EntityPredicate.m_36616_(p_24275_, p_24276_);
        LootContext $$4 = EntityPredicate.m_36616_(p_24275_, p_24277_);
        this.m_66234_(p_24275_, p_24285_ -> p_24285_.m_24299_($$3, $$4));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final EntityPredicate.Composite f_24291_;
        private final EntityPredicate.Composite f_24292_;

        public TriggerInstance(EntityPredicate.Composite p_24294_, EntityPredicate.Composite p_24295_, EntityPredicate.Composite p_24296_) {
            super(f_24270_, p_24294_);
            this.f_24291_ = p_24295_;
            this.f_24292_ = p_24296_;
        }

        public static TriggerInstance m_24302_() {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_);
        }

        public boolean m_24299_(LootContext p_24300_, LootContext p_24301_) {
            if (!this.f_24291_.m_36681_(p_24300_)) {
                return false;
            }
            return this.f_24292_.m_36681_(p_24301_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_24298_) {
            JsonObject $$1 = super.m_7683_(p_24298_);
            $$1.add("zombie", this.f_24291_.m_36675_(p_24298_));
            $$1.add("villager", this.f_24292_.m_36675_(p_24298_));
            return $$1;
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.storage.loot.LootContext;

public class LightningStrikeTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_153384_ = new ResourceLocation("lightning_strike");

    @Override
    public ResourceLocation m_7295_() {
        return f_153384_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_153396_, EntityPredicate.Composite p_153397_, DeserializationContext p_153398_) {
        EntityPredicate.Composite $$3 = EntityPredicate.Composite.m_36677_(p_153396_, "lightning", p_153398_);
        EntityPredicate.Composite $$4 = EntityPredicate.Composite.m_36677_(p_153396_, "bystander", p_153398_);
        return new TriggerInstance(p_153397_, $$3, $$4);
    }

    public void m_153391_(ServerPlayer p_153392_, LightningBolt p_153393_, List<Entity> p_153394_) {
        List $$3 = p_153394_.stream().map(p_153390_ -> EntityPredicate.m_36616_(p_153392_, p_153390_)).collect(Collectors.toList());
        LootContext $$4 = EntityPredicate.m_36616_(p_153392_, p_153393_);
        this.m_66234_(p_153392_, p_153402_ -> p_153402_.m_153418_($$4, $$3));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final EntityPredicate.Composite f_153407_;
        private final EntityPredicate.Composite f_153408_;

        public TriggerInstance(EntityPredicate.Composite p_153410_, EntityPredicate.Composite p_153411_, EntityPredicate.Composite p_153412_) {
            super(f_153384_, p_153410_);
            this.f_153407_ = p_153411_;
            this.f_153408_ = p_153412_;
        }

        public static TriggerInstance m_153413_(EntityPredicate p_153414_, EntityPredicate p_153415_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_153414_), EntityPredicate.Composite.m_36673_(p_153415_));
        }

        public boolean m_153418_(LootContext p_153419_, List<LootContext> p_153420_) {
            if (!this.f_153407_.m_36681_(p_153419_)) {
                return false;
            }
            if (this.f_153408_ != EntityPredicate.Composite.f_36667_) {
                if (p_153420_.stream().noneMatch(this.f_153408_::m_36681_)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_153417_) {
            JsonObject $$1 = super.m_7683_(p_153417_);
            $$1.add("lightning", this.f_153407_.m_36675_(p_153417_));
            $$1.add("bystander", this.f_153408_.m_36675_(p_153417_));
            return $$1;
        }
    }
}


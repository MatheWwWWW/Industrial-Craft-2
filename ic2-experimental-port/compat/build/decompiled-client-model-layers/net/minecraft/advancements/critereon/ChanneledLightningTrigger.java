/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class ChanneledLightningTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_21714_ = new ResourceLocation("channeled_lightning");

    @Override
    public ResourceLocation m_7295_() {
        return f_21714_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_21725_, EntityPredicate.Composite p_21726_, DeserializationContext p_21727_) {
        EntityPredicate.Composite[] $$3 = EntityPredicate.Composite.m_36692_(p_21725_, "victims", p_21727_);
        return new TriggerInstance(p_21726_, $$3);
    }

    public void m_21721_(ServerPlayer p_21722_, Collection<? extends Entity> p_21723_) {
        List $$2 = p_21723_.stream().map(p_21720_ -> EntityPredicate.m_36616_(p_21722_, p_21720_)).collect(Collectors.toList());
        this.m_66234_(p_21722_, p_21730_ -> p_21730_.m_21744_($$2));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final EntityPredicate.Composite[] f_21736_;

        public TriggerInstance(EntityPredicate.Composite p_21738_, EntityPredicate.Composite[] p_21739_) {
            super(f_21714_, p_21738_);
            this.f_21736_ = p_21739_;
        }

        public static TriggerInstance m_21746_(EntityPredicate ... p_21747_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, (EntityPredicate.Composite[])Stream.of(p_21747_).map(EntityPredicate.Composite::m_36673_).toArray(EntityPredicate.Composite[]::new));
        }

        public boolean m_21744_(Collection<? extends LootContext> p_21745_) {
            for (EntityPredicate.Composite $$1 : this.f_21736_) {
                boolean $$2 = false;
                for (LootContext lootContext : p_21745_) {
                    if (!$$1.m_36681_(lootContext)) continue;
                    $$2 = true;
                    break;
                }
                if ($$2) continue;
                return false;
            }
            return true;
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_21743_) {
            JsonObject $$1 = super.m_7683_(p_21743_);
            $$1.add("victims", EntityPredicate.Composite.m_36687_(this.f_21736_, p_21743_));
            return $$1;
        }
    }
}


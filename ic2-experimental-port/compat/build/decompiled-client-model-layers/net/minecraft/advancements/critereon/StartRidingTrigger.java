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
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class StartRidingTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_160383_ = new ResourceLocation("started_riding");

    @Override
    public ResourceLocation m_7295_() {
        return f_160383_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_160390_, EntityPredicate.Composite p_160391_, DeserializationContext p_160392_) {
        return new TriggerInstance(p_160391_);
    }

    public void m_160387_(ServerPlayer p_160388_) {
        this.m_66234_(p_160388_, p_160394_ -> true);
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        public TriggerInstance(EntityPredicate.Composite p_160400_) {
            super(f_160383_, p_160400_);
        }

        public static TriggerInstance m_160401_(EntityPredicate.Builder p_160402_) {
            return new TriggerInstance(EntityPredicate.Composite.m_36673_(p_160402_.m_36662_()));
        }
    }
}


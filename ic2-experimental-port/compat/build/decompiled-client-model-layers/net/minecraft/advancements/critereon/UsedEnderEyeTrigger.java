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
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class UsedEnderEyeTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_73928_ = new ResourceLocation("used_ender_eye");

    @Override
    public ResourceLocation m_7295_() {
        return f_73928_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_73939_, EntityPredicate.Composite p_73940_, DeserializationContext p_73941_) {
        MinMaxBounds.Doubles $$3 = MinMaxBounds.Doubles.m_154791_(p_73939_.get("distance"));
        return new TriggerInstance(p_73940_, $$3);
    }

    public void m_73935_(ServerPlayer p_73936_, BlockPos p_73937_) {
        double $$2 = p_73936_.m_20185_() - (double)p_73937_.m_123341_();
        double $$3 = p_73936_.m_20189_() - (double)p_73937_.m_123343_();
        double $$4 = $$2 * $$2 + $$3 * $$3;
        this.m_66234_(p_73936_, p_73934_ -> p_73934_.m_73951_($$4));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final MinMaxBounds.Doubles f_73947_;

        public TriggerInstance(EntityPredicate.Composite p_73949_, MinMaxBounds.Doubles p_73950_) {
            super(f_73928_, p_73949_);
            this.f_73947_ = p_73950_;
        }

        public boolean m_73951_(double p_73952_) {
            return this.f_73947_.m_154812_(p_73952_);
        }
    }
}


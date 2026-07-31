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
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class FilledBucketTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_38768_ = new ResourceLocation("filled_bucket");

    @Override
    public ResourceLocation m_7295_() {
        return f_38768_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_38779_, EntityPredicate.Composite p_38780_, DeserializationContext p_38781_) {
        ItemPredicate $$3 = ItemPredicate.m_45051_(p_38779_.get("item"));
        return new TriggerInstance(p_38780_, $$3);
    }

    public void m_38772_(ServerPlayer p_38773_, ItemStack p_38774_) {
        this.m_66234_(p_38773_, p_38777_ -> p_38777_.m_38791_(p_38774_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final ItemPredicate f_38787_;

        public TriggerInstance(EntityPredicate.Composite p_38789_, ItemPredicate p_38790_) {
            super(f_38768_, p_38789_);
            this.f_38787_ = p_38790_;
        }

        public static TriggerInstance m_38793_(ItemPredicate p_38794_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_38794_);
        }

        public boolean m_38791_(ItemStack p_38792_) {
            return this.f_38787_.m_45049_(p_38792_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_38796_) {
            JsonObject $$1 = super.m_7683_(p_38796_);
            $$1.add("item", this.f_38787_.m_45048_());
            return $$1;
        }
    }
}


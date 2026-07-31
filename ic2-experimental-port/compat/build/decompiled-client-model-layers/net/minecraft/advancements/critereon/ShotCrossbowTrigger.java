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
import net.minecraft.world.level.ItemLike;

public class ShotCrossbowTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_65458_ = new ResourceLocation("shot_crossbow");

    @Override
    public ResourceLocation m_7295_() {
        return f_65458_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_65469_, EntityPredicate.Composite p_65470_, DeserializationContext p_65471_) {
        ItemPredicate $$3 = ItemPredicate.m_45051_(p_65469_.get("item"));
        return new TriggerInstance(p_65470_, $$3);
    }

    public void m_65462_(ServerPlayer p_65463_, ItemStack p_65464_) {
        this.m_66234_(p_65463_, p_65467_ -> p_65467_.m_65481_(p_65464_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final ItemPredicate f_65477_;

        public TriggerInstance(EntityPredicate.Composite p_65479_, ItemPredicate p_65480_) {
            super(f_65458_, p_65479_);
            this.f_65477_ = p_65480_;
        }

        public static TriggerInstance m_159431_(ItemPredicate p_159432_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_159432_);
        }

        public static TriggerInstance m_65483_(ItemLike p_65484_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, ItemPredicate.Builder.m_45068_().m_151445_(p_65484_).m_45077_());
        }

        public boolean m_65481_(ItemStack p_65482_) {
            return this.f_65477_.m_45049_(p_65482_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_65486_) {
            JsonObject $$1 = super.m_7683_(p_65486_);
            $$1.add("item", this.f_65477_.m_45048_());
            return $$1;
        }
    }
}


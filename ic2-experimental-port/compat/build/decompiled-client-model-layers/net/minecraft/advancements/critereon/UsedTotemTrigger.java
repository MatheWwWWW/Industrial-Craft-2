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

public class UsedTotemTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_74427_ = new ResourceLocation("used_totem");

    @Override
    public ResourceLocation m_7295_() {
        return f_74427_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_74438_, EntityPredicate.Composite p_74439_, DeserializationContext p_74440_) {
        ItemPredicate $$3 = ItemPredicate.m_45051_(p_74438_.get("item"));
        return new TriggerInstance(p_74439_, $$3);
    }

    public void m_74431_(ServerPlayer p_74432_, ItemStack p_74433_) {
        this.m_66234_(p_74432_, p_74436_ -> p_74436_.m_74450_(p_74433_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final ItemPredicate f_74446_;

        public TriggerInstance(EntityPredicate.Composite p_74448_, ItemPredicate p_74449_) {
            super(f_74427_, p_74448_);
            this.f_74446_ = p_74449_;
        }

        public static TriggerInstance m_163724_(ItemPredicate p_163725_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_163725_);
        }

        public static TriggerInstance m_74452_(ItemLike p_74453_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, ItemPredicate.Builder.m_45068_().m_151445_(p_74453_).m_45077_());
        }

        public boolean m_74450_(ItemStack p_74451_) {
            return this.f_74446_.m_45049_(p_74451_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_74455_) {
            JsonObject $$1 = super.m_7683_(p_74455_);
            $$1.add("item", this.f_74446_.m_45048_());
            return $$1;
        }
    }
}


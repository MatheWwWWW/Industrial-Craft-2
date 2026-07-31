/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ItemInteractWithBlockTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    final ResourceLocation f_220036_;

    public ItemInteractWithBlockTrigger(ResourceLocation p_220038_) {
        this.f_220036_ = p_220038_;
    }

    @Override
    public ResourceLocation m_7295_() {
        return this.f_220036_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_220045_, EntityPredicate.Composite p_220046_, DeserializationContext p_220047_) {
        LocationPredicate $$3 = LocationPredicate.m_52629_(p_220045_.get("location"));
        ItemPredicate $$4 = ItemPredicate.m_45051_(p_220045_.get("item"));
        return new TriggerInstance(this.f_220036_, p_220046_, $$3, $$4);
    }

    public void m_220040_(ServerPlayer p_220041_, BlockPos p_220042_, ItemStack p_220043_) {
        BlockState $$3 = p_220041_.m_9236_().m_8055_(p_220042_);
        this.m_66234_(p_220041_, p_220053_ -> p_220053_.m_220070_($$3, p_220041_.m_9236_(), p_220042_, p_220043_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final LocationPredicate f_220058_;
        private final ItemPredicate f_220059_;

        public TriggerInstance(ResourceLocation p_220061_, EntityPredicate.Composite p_220062_, LocationPredicate p_220063_, ItemPredicate p_220064_) {
            super(p_220061_, p_220062_);
            this.f_220058_ = p_220063_;
            this.f_220059_ = p_220064_;
        }

        public static TriggerInstance m_220065_(LocationPredicate.Builder p_220066_, ItemPredicate.Builder p_220067_) {
            return new TriggerInstance(CriteriaTriggers.f_10562_.f_220036_, EntityPredicate.Composite.f_36667_, p_220066_.m_52658_(), p_220067_.m_45077_());
        }

        public static TriggerInstance m_220075_(LocationPredicate.Builder p_220076_, ItemPredicate.Builder p_220077_) {
            return new TriggerInstance(CriteriaTriggers.f_215657_.f_220036_, EntityPredicate.Composite.f_36667_, p_220076_.m_52658_(), p_220077_.m_45077_());
        }

        public boolean m_220070_(BlockState p_220071_, ServerLevel p_220072_, BlockPos p_220073_, ItemStack p_220074_) {
            if (!this.f_220058_.m_52617_(p_220072_, (double)p_220073_.m_123341_() + 0.5, (double)p_220073_.m_123342_() + 0.5, (double)p_220073_.m_123343_() + 0.5)) {
                return false;
            }
            return this.f_220059_.m_45049_(p_220074_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_220069_) {
            JsonObject $$1 = super.m_7683_(p_220069_);
            $$1.add("location", this.f_220058_.m_52616_());
            $$1.add("item", this.f_220059_.m_45048_());
            return $$1;
        }
    }
}


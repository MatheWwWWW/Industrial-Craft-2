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
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public class PickedUpItemTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    private final ResourceLocation f_221294_;

    public PickedUpItemTrigger(ResourceLocation p_221296_) {
        this.f_221294_ = p_221296_;
    }

    @Override
    public ResourceLocation m_7295_() {
        return this.f_221294_;
    }

    @Override
    protected TriggerInstance m_7214_(JsonObject p_221308_, EntityPredicate.Composite p_221309_, DeserializationContext p_221310_) {
        ItemPredicate $$3 = ItemPredicate.m_45051_(p_221308_.get("item"));
        EntityPredicate.Composite $$4 = EntityPredicate.Composite.m_36677_(p_221308_, "entity", p_221310_);
        return new TriggerInstance(this.f_221294_, p_221309_, $$3, $$4);
    }

    public void m_221298_(ServerPlayer p_221299_, ItemStack p_221300_, @Nullable Entity p_221301_) {
        LootContext $$3 = EntityPredicate.m_36616_(p_221299_, p_221301_);
        this.m_66234_(p_221299_, p_221306_ -> p_221306_.m_221322_(p_221299_, p_221300_, $$3));
    }

    @Override
    protected /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final ItemPredicate f_221315_;
        private final EntityPredicate.Composite f_221316_;

        public TriggerInstance(ResourceLocation p_221318_, EntityPredicate.Composite p_221319_, ItemPredicate p_221320_, EntityPredicate.Composite p_221321_) {
            super(p_221318_, p_221319_);
            this.f_221315_ = p_221320_;
            this.f_221316_ = p_221321_;
        }

        public static TriggerInstance m_221326_(EntityPredicate.Composite p_221327_, ItemPredicate p_221328_, EntityPredicate.Composite p_221329_) {
            return new TriggerInstance(CriteriaTriggers.f_215654_.m_7295_(), p_221327_, p_221328_, p_221329_);
        }

        public static TriggerInstance m_221332_(EntityPredicate.Composite p_221333_, ItemPredicate p_221334_, EntityPredicate.Composite p_221335_) {
            return new TriggerInstance(CriteriaTriggers.f_215655_.m_7295_(), p_221333_, p_221334_, p_221335_);
        }

        public boolean m_221322_(ServerPlayer p_221323_, ItemStack p_221324_, LootContext p_221325_) {
            if (!this.f_221315_.m_45049_(p_221324_)) {
                return false;
            }
            return this.f_221316_.m_36681_(p_221325_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_221331_) {
            JsonObject $$1 = super.m_7683_(p_221331_);
            $$1.add("item", this.f_221315_.m_45048_());
            $$1.add("entity", this.f_221316_.m_36675_(p_221331_));
            return $$1;
        }
    }
}


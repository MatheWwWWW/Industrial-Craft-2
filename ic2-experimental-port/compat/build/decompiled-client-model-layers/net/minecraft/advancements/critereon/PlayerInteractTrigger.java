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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public class PlayerInteractTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_61490_ = new ResourceLocation("player_interacted_with_entity");

    @Override
    public ResourceLocation m_7295_() {
        return f_61490_;
    }

    @Override
    protected TriggerInstance m_7214_(JsonObject p_61503_, EntityPredicate.Composite p_61504_, DeserializationContext p_61505_) {
        ItemPredicate $$3 = ItemPredicate.m_45051_(p_61503_.get("item"));
        EntityPredicate.Composite $$4 = EntityPredicate.Composite.m_36677_(p_61503_, "entity", p_61505_);
        return new TriggerInstance(p_61504_, $$3, $$4);
    }

    public void m_61494_(ServerPlayer p_61495_, ItemStack p_61496_, Entity p_61497_) {
        LootContext $$3 = EntityPredicate.m_36616_(p_61495_, p_61497_);
        this.m_66234_(p_61495_, p_61501_ -> p_61501_.m_61521_(p_61496_, $$3));
    }

    @Override
    protected /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final ItemPredicate f_61511_;
        private final EntityPredicate.Composite f_61512_;

        public TriggerInstance(EntityPredicate.Composite p_61514_, ItemPredicate p_61515_, EntityPredicate.Composite p_61516_) {
            super(f_61490_, p_61514_);
            this.f_61511_ = p_61515_;
            this.f_61512_ = p_61516_;
        }

        public static TriggerInstance m_61517_(EntityPredicate.Composite p_61518_, ItemPredicate.Builder p_61519_, EntityPredicate.Composite p_61520_) {
            return new TriggerInstance(p_61518_, p_61519_.m_45077_(), p_61520_);
        }

        public static TriggerInstance m_222015_(ItemPredicate.Builder p_222016_, EntityPredicate.Composite p_222017_) {
            return TriggerInstance.m_61517_(EntityPredicate.Composite.f_36667_, p_222016_, p_222017_);
        }

        public boolean m_61521_(ItemStack p_61522_, LootContext p_61523_) {
            if (!this.f_61511_.m_45049_(p_61522_)) {
                return false;
            }
            return this.f_61512_.m_36681_(p_61523_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_61525_) {
            JsonObject $$1 = super.m_7683_(p_61525_);
            $$1.add("item", this.f_61511_.m_45048_());
            $$1.add("entity", this.f_61512_.m_36675_(p_61525_));
            return $$1;
        }
    }
}


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
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public class TradeTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_70955_ = new ResourceLocation("villager_trade");

    @Override
    public ResourceLocation m_7295_() {
        return f_70955_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_70964_, EntityPredicate.Composite p_70965_, DeserializationContext p_70966_) {
        EntityPredicate.Composite $$3 = EntityPredicate.Composite.m_36677_(p_70964_, "villager", p_70966_);
        ItemPredicate $$4 = ItemPredicate.m_45051_(p_70964_.get("item"));
        return new TriggerInstance(p_70965_, $$3, $$4);
    }

    public void m_70959_(ServerPlayer p_70960_, AbstractVillager p_70961_, ItemStack p_70962_) {
        LootContext $$3 = EntityPredicate.m_36616_(p_70960_, p_70961_);
        this.m_66234_(p_70960_, p_70970_ -> p_70970_.m_70984_($$3, p_70962_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final EntityPredicate.Composite f_70976_;
        private final ItemPredicate f_70977_;

        public TriggerInstance(EntityPredicate.Composite p_70979_, EntityPredicate.Composite p_70980_, ItemPredicate p_70981_) {
            super(f_70955_, p_70979_);
            this.f_70976_ = p_70980_;
            this.f_70977_ = p_70981_;
        }

        public static TriggerInstance m_70987_() {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, ItemPredicate.f_45028_);
        }

        public static TriggerInstance m_191436_(EntityPredicate.Builder p_191437_) {
            return new TriggerInstance(EntityPredicate.Composite.m_36673_(p_191437_.m_36662_()), EntityPredicate.Composite.f_36667_, ItemPredicate.f_45028_);
        }

        public boolean m_70984_(LootContext p_70985_, ItemStack p_70986_) {
            if (!this.f_70976_.m_36681_(p_70985_)) {
                return false;
            }
            return this.f_70977_.m_45049_(p_70986_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_70983_) {
            JsonObject $$1 = super.m_7683_(p_70983_);
            $$1.add("item", this.f_70977_.m_45048_());
            $$1.add("villager", this.f_70976_.m_36675_(p_70983_));
            return $$1;
        }
    }
}


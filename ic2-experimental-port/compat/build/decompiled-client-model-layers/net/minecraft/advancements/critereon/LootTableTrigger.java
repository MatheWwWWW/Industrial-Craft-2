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
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class LootTableTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_54593_ = new ResourceLocation("player_generates_container_loot");

    @Override
    public ResourceLocation m_7295_() {
        return f_54593_;
    }

    @Override
    protected TriggerInstance m_7214_(JsonObject p_54601_, EntityPredicate.Composite p_54602_, DeserializationContext p_54603_) {
        ResourceLocation $$3 = new ResourceLocation(GsonHelper.m_13906_(p_54601_, "loot_table"));
        return new TriggerInstance(p_54602_, $$3);
    }

    public void m_54597_(ServerPlayer p_54598_, ResourceLocation p_54599_) {
        this.m_66234_(p_54598_, p_54606_ -> p_54606_.m_54620_(p_54599_));
    }

    @Override
    protected /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final ResourceLocation f_54612_;

        public TriggerInstance(EntityPredicate.Composite p_54614_, ResourceLocation p_54615_) {
            super(f_54593_, p_54614_);
            this.f_54612_ = p_54615_;
        }

        public static TriggerInstance m_54618_(ResourceLocation p_54619_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_54619_);
        }

        public boolean m_54620_(ResourceLocation p_54621_) {
            return this.f_54612_.equals(p_54621_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_54617_) {
            JsonObject $$1 = super.m_7683_(p_54617_);
            $$1.addProperty("loot_table", this.f_54612_.toString());
            return $$1;
        }
    }
}


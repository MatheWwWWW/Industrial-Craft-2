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
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class EnchantedItemTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_27664_ = new ResourceLocation("enchanted_item");

    @Override
    public ResourceLocation m_7295_() {
        return f_27664_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_27677_, EntityPredicate.Composite p_27678_, DeserializationContext p_27679_) {
        ItemPredicate $$3 = ItemPredicate.m_45051_(p_27677_.get("item"));
        MinMaxBounds.Ints $$4 = MinMaxBounds.Ints.m_55373_(p_27677_.get("levels"));
        return new TriggerInstance(p_27678_, $$3, $$4);
    }

    public void m_27668_(ServerPlayer p_27669_, ItemStack p_27670_, int p_27671_) {
        this.m_66234_(p_27669_, p_27675_ -> p_27675_.m_27691_(p_27670_, p_27671_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final ItemPredicate f_27685_;
        private final MinMaxBounds.Ints f_27686_;

        public TriggerInstance(EntityPredicate.Composite p_27688_, ItemPredicate p_27689_, MinMaxBounds.Ints p_27690_) {
            super(f_27664_, p_27688_);
            this.f_27685_ = p_27689_;
            this.f_27686_ = p_27690_;
        }

        public static TriggerInstance m_27696_() {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, ItemPredicate.f_45028_, MinMaxBounds.Ints.f_55364_);
        }

        public boolean m_27691_(ItemStack p_27692_, int p_27693_) {
            if (!this.f_27685_.m_45049_(p_27692_)) {
                return false;
            }
            return this.f_27686_.m_55390_(p_27693_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_27695_) {
            JsonObject $$1 = super.m_7683_(p_27695_);
            $$1.add("item", this.f_27685_.m_45048_());
            $$1.add("levels", this.f_27686_.m_55328_());
            return $$1;
        }
    }
}


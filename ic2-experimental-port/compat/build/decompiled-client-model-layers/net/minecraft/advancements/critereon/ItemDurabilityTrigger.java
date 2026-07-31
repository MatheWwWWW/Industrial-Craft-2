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

public class ItemDurabilityTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_43665_ = new ResourceLocation("item_durability_changed");

    @Override
    public ResourceLocation m_7295_() {
        return f_43665_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_43678_, EntityPredicate.Composite p_43679_, DeserializationContext p_43680_) {
        ItemPredicate $$3 = ItemPredicate.m_45051_(p_43678_.get("item"));
        MinMaxBounds.Ints $$4 = MinMaxBounds.Ints.m_55373_(p_43678_.get("durability"));
        MinMaxBounds.Ints $$5 = MinMaxBounds.Ints.m_55373_(p_43678_.get("delta"));
        return new TriggerInstance(p_43679_, $$3, $$4, $$5);
    }

    public void m_43669_(ServerPlayer p_43670_, ItemStack p_43671_, int p_43672_) {
        this.m_66234_(p_43670_, p_43676_ -> p_43676_.m_43698_(p_43671_, p_43672_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final ItemPredicate f_43686_;
        private final MinMaxBounds.Ints f_43687_;
        private final MinMaxBounds.Ints f_43688_;

        public TriggerInstance(EntityPredicate.Composite p_43690_, ItemPredicate p_43691_, MinMaxBounds.Ints p_43692_, MinMaxBounds.Ints p_43693_) {
            super(f_43665_, p_43690_);
            this.f_43686_ = p_43691_;
            this.f_43687_ = p_43692_;
            this.f_43688_ = p_43693_;
        }

        public static TriggerInstance m_151286_(ItemPredicate p_151287_, MinMaxBounds.Ints p_151288_) {
            return TriggerInstance.m_43694_(EntityPredicate.Composite.f_36667_, p_151287_, p_151288_);
        }

        public static TriggerInstance m_43694_(EntityPredicate.Composite p_43695_, ItemPredicate p_43696_, MinMaxBounds.Ints p_43697_) {
            return new TriggerInstance(p_43695_, p_43696_, p_43697_, MinMaxBounds.Ints.f_55364_);
        }

        public boolean m_43698_(ItemStack p_43699_, int p_43700_) {
            if (!this.f_43686_.m_45049_(p_43699_)) {
                return false;
            }
            if (!this.f_43687_.m_55390_(p_43699_.m_41776_() - p_43700_)) {
                return false;
            }
            return this.f_43688_.m_55390_(p_43699_.m_41773_() - p_43700_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_43702_) {
            JsonObject $$1 = super.m_7683_(p_43702_);
            $$1.add("item", this.f_43686_.m_45048_());
            $$1.add("durability", this.f_43687_.m_55328_());
            $$1.add("delta", this.f_43688_.m_55328_());
            return $$1;
        }
    }
}


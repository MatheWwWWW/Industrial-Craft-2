/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonObject
 */
package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import java.util.Set;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ConsumeItemTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_23678_ = new ResourceLocation("consume_item");

    @Override
    public ResourceLocation m_7295_() {
        return f_23678_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_23689_, EntityPredicate.Composite p_23690_, DeserializationContext p_23691_) {
        return new TriggerInstance(p_23690_, ItemPredicate.m_45051_(p_23689_.get("item")));
    }

    public void m_23682_(ServerPlayer p_23683_, ItemStack p_23684_) {
        this.m_66234_(p_23683_, p_23687_ -> p_23687_.m_23701_(p_23684_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final ItemPredicate f_23697_;

        public TriggerInstance(EntityPredicate.Composite p_23699_, ItemPredicate p_23700_) {
            super(f_23678_, p_23699_);
            this.f_23697_ = p_23700_;
        }

        public static TriggerInstance m_23707_() {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, ItemPredicate.f_45028_);
        }

        public static TriggerInstance m_148081_(ItemPredicate p_148082_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_148082_);
        }

        public static TriggerInstance m_23703_(ItemLike p_23704_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, new ItemPredicate(null, (Set<Item>)ImmutableSet.of((Object)p_23704_.m_5456_()), MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, EnchantmentPredicate.f_30465_, EnchantmentPredicate.f_30465_, null, NbtPredicate.f_57471_));
        }

        public boolean m_23701_(ItemStack p_23702_) {
            return this.f_23697_.m_45049_(p_23702_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_23706_) {
            JsonObject $$1 = super.m_7683_(p_23706_);
            $$1.add("item", this.f_23697_.m_45048_());
            return $$1;
        }
    }
}


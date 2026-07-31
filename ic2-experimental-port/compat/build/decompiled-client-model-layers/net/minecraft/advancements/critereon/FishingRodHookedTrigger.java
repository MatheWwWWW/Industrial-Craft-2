/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import java.util.Collection;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class FishingRodHookedTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_40412_ = new ResourceLocation("fishing_rod_hooked");

    @Override
    public ResourceLocation m_7295_() {
        return f_40412_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_40427_, EntityPredicate.Composite p_40428_, DeserializationContext p_40429_) {
        ItemPredicate $$3 = ItemPredicate.m_45051_(p_40427_.get("rod"));
        EntityPredicate.Composite $$4 = EntityPredicate.Composite.m_36677_(p_40427_, "entity", p_40429_);
        ItemPredicate $$5 = ItemPredicate.m_45051_(p_40427_.get("item"));
        return new TriggerInstance(p_40428_, $$3, $$4, $$5);
    }

    public void m_40416_(ServerPlayer p_40417_, ItemStack p_40418_, FishingHook p_40419_, Collection<ItemStack> p_40420_) {
        LootContext $$4 = EntityPredicate.m_36616_(p_40417_, p_40419_.m_37170_() != null ? p_40419_.m_37170_() : p_40419_);
        this.m_66234_(p_40417_, p_40425_ -> p_40425_.m_40443_(p_40418_, $$4, p_40420_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final ItemPredicate f_40435_;
        private final EntityPredicate.Composite f_40436_;
        private final ItemPredicate f_40437_;

        public TriggerInstance(EntityPredicate.Composite p_40439_, ItemPredicate p_40440_, EntityPredicate.Composite p_40441_, ItemPredicate p_40442_) {
            super(f_40412_, p_40439_);
            this.f_40435_ = p_40440_;
            this.f_40436_ = p_40441_;
            this.f_40437_ = p_40442_;
        }

        public static TriggerInstance m_40447_(ItemPredicate p_40448_, EntityPredicate p_40449_, ItemPredicate p_40450_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_40448_, EntityPredicate.Composite.m_36673_(p_40449_), p_40450_);
        }

        public boolean m_40443_(ItemStack p_40444_, LootContext p_40445_, Collection<ItemStack> p_40446_) {
            if (!this.f_40435_.m_45049_(p_40444_)) {
                return false;
            }
            if (!this.f_40436_.m_36681_(p_40445_)) {
                return false;
            }
            if (this.f_40437_ != ItemPredicate.f_45028_) {
                ItemEntity $$5;
                boolean $$3 = false;
                Entity $$4 = p_40445_.m_78953_(LootContextParams.f_81455_);
                if ($$4 instanceof ItemEntity && this.f_40437_.m_45049_(($$5 = (ItemEntity)$$4).m_32055_())) {
                    $$3 = true;
                }
                for (ItemStack $$6 : p_40446_) {
                    if (!this.f_40437_.m_45049_($$6)) continue;
                    $$3 = true;
                    break;
                }
                if (!$$3) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_40452_) {
            JsonObject $$1 = super.m_7683_(p_40452_);
            $$1.add("rod", this.f_40435_.m_45048_());
            $$1.add("entity", this.f_40436_.m_36675_(p_40452_));
            $$1.add("item", this.f_40437_.m_45048_());
            return $$1;
        }
    }
}


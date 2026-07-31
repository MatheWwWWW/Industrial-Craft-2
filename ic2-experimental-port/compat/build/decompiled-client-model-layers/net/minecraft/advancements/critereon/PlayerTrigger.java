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
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class PlayerTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    final ResourceLocation f_222614_;

    public PlayerTrigger(ResourceLocation p_222616_) {
        this.f_222614_ = p_222616_;
    }

    @Override
    public ResourceLocation m_7295_() {
        return this.f_222614_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_222621_, EntityPredicate.Composite p_222622_, DeserializationContext p_222623_) {
        return new TriggerInstance(this.f_222614_, p_222622_);
    }

    public void m_222618_(ServerPlayer p_222619_) {
        this.m_66234_(p_222619_, p_222625_ -> true);
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ResourceLocation p_222631_, EntityPredicate.Composite p_222632_) {
            super(p_222631_, p_222632_);
        }

        public static TriggerInstance m_222635_(LocationPredicate p_222636_) {
            return new TriggerInstance(CriteriaTriggers.f_10582_.f_222614_, EntityPredicate.Composite.m_36673_(EntityPredicate.Builder.m_36633_().m_36650_(p_222636_).m_36662_()));
        }

        public static TriggerInstance m_222633_(EntityPredicate p_222634_) {
            return new TriggerInstance(CriteriaTriggers.f_10582_.f_222614_, EntityPredicate.Composite.m_36673_(p_222634_));
        }

        public static TriggerInstance m_222640_() {
            return new TriggerInstance(CriteriaTriggers.f_10583_.f_222614_, EntityPredicate.Composite.f_36667_);
        }

        public static TriggerInstance m_222641_() {
            return new TriggerInstance(CriteriaTriggers.f_10557_.f_222614_, EntityPredicate.Composite.f_36667_);
        }

        public static TriggerInstance m_222642_() {
            return new TriggerInstance(CriteriaTriggers.f_215658_.f_222614_, EntityPredicate.Composite.f_36667_);
        }

        public static TriggerInstance m_222637_(Block p_222638_, Item p_222639_) {
            return TriggerInstance.m_222633_(EntityPredicate.Builder.m_36633_().m_36640_(EntityEquipmentPredicate.Builder.m_32204_().m_32212_(ItemPredicate.Builder.m_45068_().m_151445_(p_222639_).m_45077_()).m_32207_()).m_150330_(LocationPredicate.Builder.m_52651_().m_52652_(BlockPredicate.Builder.m_17924_().m_146726_(p_222638_).m_17931_()).m_52658_()).m_36662_());
        }
    }
}


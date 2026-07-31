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
import net.minecraft.world.item.crafting.Recipe;

public class RecipeUnlockedTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_63714_ = new ResourceLocation("recipe_unlocked");

    @Override
    public ResourceLocation m_7295_() {
        return f_63714_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_63725_, EntityPredicate.Composite p_63726_, DeserializationContext p_63727_) {
        ResourceLocation $$3 = new ResourceLocation(GsonHelper.m_13906_(p_63725_, "recipe"));
        return new TriggerInstance(p_63726_, $$3);
    }

    public void m_63718_(ServerPlayer p_63719_, Recipe<?> p_63720_) {
        this.m_66234_(p_63719_, p_63723_ -> p_63723_.m_63739_(p_63720_));
    }

    public static TriggerInstance m_63728_(ResourceLocation p_63729_) {
        return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_63729_);
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final ResourceLocation f_63735_;

        public TriggerInstance(EntityPredicate.Composite p_63737_, ResourceLocation p_63738_) {
            super(f_63714_, p_63737_);
            this.f_63735_ = p_63738_;
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_63742_) {
            JsonObject $$1 = super.m_7683_(p_63742_);
            $$1.addProperty("recipe", this.f_63735_.toString());
            return $$1;
        }

        public boolean m_63739_(Recipe<?> p_63740_) {
            return this.f_63735_.equals(p_63740_.m_6423_());
        }
    }
}


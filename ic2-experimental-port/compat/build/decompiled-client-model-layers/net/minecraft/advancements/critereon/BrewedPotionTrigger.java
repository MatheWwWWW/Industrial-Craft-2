/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.alchemy.Potion;

public class BrewedPotionTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_19116_ = new ResourceLocation("brewed_potion");

    @Override
    public ResourceLocation m_7295_() {
        return f_19116_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_19127_, EntityPredicate.Composite p_19128_, DeserializationContext p_19129_) {
        Potion $$3 = null;
        if (p_19127_.has("potion")) {
            ResourceLocation $$4 = new ResourceLocation(GsonHelper.m_13906_(p_19127_, "potion"));
            $$3 = Registry.f_122828_.m_6612_($$4).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + $$4 + "'"));
        }
        return new TriggerInstance(p_19128_, $$3);
    }

    public void m_19120_(ServerPlayer p_19121_, Potion p_19122_) {
        this.m_66234_(p_19121_, p_19125_ -> p_19125_.m_19141_(p_19122_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        @Nullable
        private final Potion f_19137_;

        public TriggerInstance(EntityPredicate.Composite p_19139_, @Nullable Potion p_19140_) {
            super(f_19116_, p_19139_);
            this.f_19137_ = p_19140_;
        }

        public static TriggerInstance m_19145_() {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, null);
        }

        public boolean m_19141_(Potion p_19142_) {
            return this.f_19137_ == null || this.f_19137_ == p_19142_;
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_19144_) {
            JsonObject $$1 = super.m_7683_(p_19144_);
            if (this.f_19137_ != null) {
                $$1.addProperty("potion", Registry.f_122828_.m_7981_(this.f_19137_).toString());
            }
            return $$1;
        }
    }
}


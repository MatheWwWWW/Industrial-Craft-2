/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;

public class ChangeDimensionTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_19753_ = new ResourceLocation("changed_dimension");

    @Override
    public ResourceLocation m_7295_() {
        return f_19753_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_19762_, EntityPredicate.Composite p_19763_, DeserializationContext p_19764_) {
        ResourceKey<Level> $$3 = p_19762_.has("from") ? ResourceKey.m_135785_(Registry.f_122819_, new ResourceLocation(GsonHelper.m_13906_(p_19762_, "from"))) : null;
        ResourceKey<Level> $$4 = p_19762_.has("to") ? ResourceKey.m_135785_(Registry.f_122819_, new ResourceLocation(GsonHelper.m_13906_(p_19762_, "to"))) : null;
        return new TriggerInstance(p_19763_, $$3, $$4);
    }

    public void m_19757_(ServerPlayer p_19758_, ResourceKey<Level> p_19759_, ResourceKey<Level> p_19760_) {
        this.m_66234_(p_19758_, p_19768_ -> p_19768_.m_19784_(p_19759_, p_19760_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        @Nullable
        private final ResourceKey<Level> f_19774_;
        @Nullable
        private final ResourceKey<Level> f_19775_;

        public TriggerInstance(EntityPredicate.Composite p_19777_, @Nullable ResourceKey<Level> p_19778_, @Nullable ResourceKey<Level> p_19779_) {
            super(f_19753_, p_19777_);
            this.f_19774_ = p_19778_;
            this.f_19775_ = p_19779_;
        }

        public static TriggerInstance m_147565_() {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, null, null);
        }

        public static TriggerInstance m_147560_(ResourceKey<Level> p_147561_, ResourceKey<Level> p_147562_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_147561_, p_147562_);
        }

        public static TriggerInstance m_19782_(ResourceKey<Level> p_19783_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, null, p_19783_);
        }

        public static TriggerInstance m_147563_(ResourceKey<Level> p_147564_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_147564_, null);
        }

        public boolean m_19784_(ResourceKey<Level> p_19785_, ResourceKey<Level> p_19786_) {
            if (this.f_19774_ != null && this.f_19774_ != p_19785_) {
                return false;
            }
            return this.f_19775_ == null || this.f_19775_ == p_19786_;
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_19781_) {
            JsonObject $$1 = super.m_7683_(p_19781_);
            if (this.f_19774_ != null) {
                $$1.addProperty("from", this.f_19774_.m_135782_().toString());
            }
            if (this.f_19775_ != null) {
                $$1.addProperty("to", this.f_19775_.m_135782_().toString());
            }
            return $$1;
        }
    }
}


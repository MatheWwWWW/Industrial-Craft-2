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
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class EnterBlockTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_31265_ = new ResourceLocation("enter_block");

    @Override
    public ResourceLocation m_7295_() {
        return f_31265_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_31281_, EntityPredicate.Composite p_31282_, DeserializationContext p_31283_) {
        Block $$3 = EnterBlockTrigger.m_31278_(p_31281_);
        StatePropertiesPredicate $$4 = StatePropertiesPredicate.m_67679_(p_31281_.get("state"));
        if ($$3 != null) {
            $$4.m_67672_($$3.m_49965_(), p_31274_ -> {
                throw new JsonSyntaxException("Block " + $$3 + " has no property " + p_31274_);
            });
        }
        return new TriggerInstance(p_31282_, $$3, $$4);
    }

    @Nullable
    private static Block m_31278_(JsonObject p_31279_) {
        if (p_31279_.has("block")) {
            ResourceLocation $$1 = new ResourceLocation(GsonHelper.m_13906_(p_31279_, "block"));
            return Registry.f_122824_.m_6612_($$1).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + $$1 + "'"));
        }
        return null;
    }

    public void m_31269_(ServerPlayer p_31270_, BlockState p_31271_) {
        this.m_66234_(p_31270_, p_31277_ -> p_31277_.m_31299_(p_31271_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        @Nullable
        private final Block f_31291_;
        private final StatePropertiesPredicate f_31292_;

        public TriggerInstance(EntityPredicate.Composite p_31294_, @Nullable Block p_31295_, StatePropertiesPredicate p_31296_) {
            super(f_31265_, p_31294_);
            this.f_31291_ = p_31295_;
            this.f_31292_ = p_31296_;
        }

        public static TriggerInstance m_31297_(Block p_31298_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_31298_, StatePropertiesPredicate.f_67658_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_31302_) {
            JsonObject $$1 = super.m_7683_(p_31302_);
            if (this.f_31291_ != null) {
                $$1.addProperty("block", Registry.f_122824_.m_7981_(this.f_31291_).toString());
            }
            $$1.add("state", this.f_31292_.m_67666_());
            return $$1;
        }

        public boolean m_31299_(BlockState p_31300_) {
            if (this.f_31291_ != null && !p_31300_.m_60713_(this.f_31291_)) {
                return false;
            }
            return this.f_31292_.m_67667_(p_31300_);
        }
    }
}


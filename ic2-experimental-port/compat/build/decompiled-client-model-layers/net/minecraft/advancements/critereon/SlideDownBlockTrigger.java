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

public class SlideDownBlockTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_66974_ = new ResourceLocation("slide_down_block");

    @Override
    public ResourceLocation m_7295_() {
        return f_66974_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_66990_, EntityPredicate.Composite p_66991_, DeserializationContext p_66992_) {
        Block $$3 = SlideDownBlockTrigger.m_66987_(p_66990_);
        StatePropertiesPredicate $$4 = StatePropertiesPredicate.m_67679_(p_66990_.get("state"));
        if ($$3 != null) {
            $$4.m_67672_($$3.m_49965_(), p_66983_ -> {
                throw new JsonSyntaxException("Block " + $$3 + " has no property " + p_66983_);
            });
        }
        return new TriggerInstance(p_66991_, $$3, $$4);
    }

    @Nullable
    private static Block m_66987_(JsonObject p_66988_) {
        if (p_66988_.has("block")) {
            ResourceLocation $$1 = new ResourceLocation(GsonHelper.m_13906_(p_66988_, "block"));
            return Registry.f_122824_.m_6612_($$1).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + $$1 + "'"));
        }
        return null;
    }

    public void m_66978_(ServerPlayer p_66979_, BlockState p_66980_) {
        this.m_66234_(p_66979_, p_66986_ -> p_66986_.m_67008_(p_66980_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        @Nullable
        private final Block f_67000_;
        private final StatePropertiesPredicate f_67001_;

        public TriggerInstance(EntityPredicate.Composite p_67003_, @Nullable Block p_67004_, StatePropertiesPredicate p_67005_) {
            super(f_66974_, p_67003_);
            this.f_67000_ = p_67004_;
            this.f_67001_ = p_67005_;
        }

        public static TriggerInstance m_67006_(Block p_67007_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_67007_, StatePropertiesPredicate.f_67658_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_67011_) {
            JsonObject $$1 = super.m_7683_(p_67011_);
            if (this.f_67000_ != null) {
                $$1.addProperty("block", Registry.f_122824_.m_7981_(this.f_67000_).toString());
            }
            $$1.add("state", this.f_67001_.m_67666_());
            return $$1;
        }

        public boolean m_67008_(BlockState p_67009_) {
            if (this.f_67000_ != null && !p_67009_.m_60713_(this.f_67000_)) {
                return false;
            }
            return this.f_67001_.m_67667_(p_67009_);
        }
    }
}


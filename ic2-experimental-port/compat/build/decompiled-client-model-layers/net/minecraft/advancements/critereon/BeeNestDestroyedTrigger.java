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
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BeeNestDestroyedTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_17473_ = new ResourceLocation("bee_nest_destroyed");

    @Override
    public ResourceLocation m_7295_() {
        return f_17473_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_17490_, EntityPredicate.Composite p_17491_, DeserializationContext p_17492_) {
        Block $$3 = BeeNestDestroyedTrigger.m_17487_(p_17490_);
        ItemPredicate $$4 = ItemPredicate.m_45051_(p_17490_.get("item"));
        MinMaxBounds.Ints $$5 = MinMaxBounds.Ints.m_55373_(p_17490_.get("num_bees_inside"));
        return new TriggerInstance(p_17491_, $$3, $$4, $$5);
    }

    @Nullable
    private static Block m_17487_(JsonObject p_17488_) {
        if (p_17488_.has("block")) {
            ResourceLocation $$1 = new ResourceLocation(GsonHelper.m_13906_(p_17488_, "block"));
            return Registry.f_122824_.m_6612_($$1).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + $$1 + "'"));
        }
        return null;
    }

    public void m_146651_(ServerPlayer p_146652_, BlockState p_146653_, ItemStack p_146654_, int p_146655_) {
        this.m_66234_(p_146652_, p_146660_ -> p_146660_.m_146661_(p_146653_, p_146654_, p_146655_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        @Nullable
        private final Block f_17500_;
        private final ItemPredicate f_17501_;
        private final MinMaxBounds.Ints f_17502_;

        public TriggerInstance(EntityPredicate.Composite p_17504_, @Nullable Block p_17505_, ItemPredicate p_17506_, MinMaxBounds.Ints p_17507_) {
            super(f_17473_, p_17504_);
            this.f_17500_ = p_17505_;
            this.f_17501_ = p_17506_;
            this.f_17502_ = p_17507_;
        }

        public static TriggerInstance m_17512_(Block p_17513_, ItemPredicate.Builder p_17514_, MinMaxBounds.Ints p_17515_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_17513_, p_17514_.m_45077_(), p_17515_);
        }

        public boolean m_146661_(BlockState p_146662_, ItemStack p_146663_, int p_146664_) {
            if (this.f_17500_ != null && !p_146662_.m_60713_(this.f_17500_)) {
                return false;
            }
            if (!this.f_17501_.m_45049_(p_146663_)) {
                return false;
            }
            return this.f_17502_.m_55390_(p_146664_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_17517_) {
            JsonObject $$1 = super.m_7683_(p_17517_);
            if (this.f_17500_ != null) {
                $$1.addProperty("block", Registry.f_122824_.m_7981_(this.f_17500_).toString());
            }
            $$1.add("item", this.f_17501_.m_45048_());
            $$1.add("num_bees_inside", this.f_17502_.m_55328_());
            return $$1;
        }
    }
}


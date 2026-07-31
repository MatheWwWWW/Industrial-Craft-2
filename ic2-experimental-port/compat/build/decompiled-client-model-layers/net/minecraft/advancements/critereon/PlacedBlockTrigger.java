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
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PlacedBlockTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_59465_ = new ResourceLocation("placed_block");

    @Override
    public ResourceLocation m_7295_() {
        return f_59465_;
    }

    @Override
    public TriggerInstance m_7214_(JsonObject p_59485_, EntityPredicate.Composite p_59486_, DeserializationContext p_59487_) {
        Block $$3 = PlacedBlockTrigger.m_59482_(p_59485_);
        StatePropertiesPredicate $$4 = StatePropertiesPredicate.m_67679_(p_59485_.get("state"));
        if ($$3 != null) {
            $$4.m_67672_($$3.m_49965_(), p_59475_ -> {
                throw new JsonSyntaxException("Block " + $$3 + " has no property " + p_59475_ + ":");
            });
        }
        LocationPredicate $$5 = LocationPredicate.m_52629_(p_59485_.get("location"));
        ItemPredicate $$6 = ItemPredicate.m_45051_(p_59485_.get("item"));
        return new TriggerInstance(p_59486_, $$3, $$4, $$5, $$6);
    }

    @Nullable
    private static Block m_59482_(JsonObject p_59483_) {
        if (p_59483_.has("block")) {
            ResourceLocation $$1 = new ResourceLocation(GsonHelper.m_13906_(p_59483_, "block"));
            return Registry.f_122824_.m_6612_($$1).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + $$1 + "'"));
        }
        return null;
    }

    public void m_59469_(ServerPlayer p_59470_, BlockPos p_59471_, ItemStack p_59472_) {
        BlockState $$3 = p_59470_.m_9236_().m_8055_(p_59471_);
        this.m_66234_(p_59470_, p_59481_ -> p_59481_.m_59507_($$3, p_59471_, p_59470_.m_9236_(), p_59472_));
    }

    @Override
    public /* synthetic */ AbstractCriterionTriggerInstance m_7214_(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return this.m_7214_(jsonObject, composite, deserializationContext);
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        @Nullable
        private final Block f_59495_;
        private final StatePropertiesPredicate f_59496_;
        private final LocationPredicate f_59497_;
        private final ItemPredicate f_59498_;

        public TriggerInstance(EntityPredicate.Composite p_59500_, @Nullable Block p_59501_, StatePropertiesPredicate p_59502_, LocationPredicate p_59503_, ItemPredicate p_59504_) {
            super(f_59465_, p_59500_);
            this.f_59495_ = p_59501_;
            this.f_59496_ = p_59502_;
            this.f_59497_ = p_59503_;
            this.f_59498_ = p_59504_;
        }

        public static TriggerInstance m_59505_(Block p_59506_) {
            return new TriggerInstance(EntityPredicate.Composite.f_36667_, p_59506_, StatePropertiesPredicate.f_67658_, LocationPredicate.f_52592_, ItemPredicate.f_45028_);
        }

        public boolean m_59507_(BlockState p_59508_, BlockPos p_59509_, ServerLevel p_59510_, ItemStack p_59511_) {
            if (this.f_59495_ != null && !p_59508_.m_60713_(this.f_59495_)) {
                return false;
            }
            if (!this.f_59496_.m_67667_(p_59508_)) {
                return false;
            }
            if (!this.f_59497_.m_52617_(p_59510_, p_59509_.m_123341_(), p_59509_.m_123342_(), p_59509_.m_123343_())) {
                return false;
            }
            return this.f_59498_.m_45049_(p_59511_);
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_59513_) {
            JsonObject $$1 = super.m_7683_(p_59513_);
            if (this.f_59495_ != null) {
                $$1.addProperty("block", Registry.f_122824_.m_7981_(this.f_59495_).toString());
            }
            $$1.add("state", this.f_59496_.m_67666_());
            $$1.add("location", this.f_59497_.m_52616_());
            $$1.add("item", this.f_59498_.m_45048_());
            return $$1;
        }
    }
}


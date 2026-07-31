/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSyntaxException
 */
package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Set;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class LootItemBlockStatePropertyCondition
implements LootItemCondition {
    final Block f_81759_;
    final StatePropertiesPredicate f_81760_;

    LootItemBlockStatePropertyCondition(Block p_81762_, StatePropertiesPredicate p_81763_) {
        this.f_81759_ = p_81762_;
        this.f_81760_ = p_81763_;
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81818_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return ImmutableSet.of(LootContextParams.f_81461_);
    }

    @Override
    public boolean test(LootContext p_81772_) {
        BlockState $$1 = p_81772_.m_78953_(LootContextParams.f_81461_);
        return $$1 != null && $$1.m_60713_(this.f_81759_) && this.f_81760_.m_67667_($$1);
    }

    public static Builder m_81769_(Block p_81770_) {
        return new Builder(p_81770_);
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Builder
    implements LootItemCondition.Builder {
        private final Block f_81780_;
        private StatePropertiesPredicate f_81781_ = StatePropertiesPredicate.f_67658_;

        public Builder(Block p_81783_) {
            this.f_81780_ = p_81783_;
        }

        public Builder m_81784_(StatePropertiesPredicate.Builder p_81785_) {
            this.f_81781_ = p_81785_.m_67706_();
            return this;
        }

        @Override
        public LootItemCondition m_6409_() {
            return new LootItemBlockStatePropertyCondition(this.f_81780_, this.f_81781_);
        }
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<LootItemBlockStatePropertyCondition> {
        @Override
        public void m_6170_(JsonObject p_81795_, LootItemBlockStatePropertyCondition p_81796_, JsonSerializationContext p_81797_) {
            p_81795_.addProperty("block", Registry.f_122824_.m_7981_(p_81796_.f_81759_).toString());
            p_81795_.add("properties", p_81796_.f_81760_.m_67666_());
        }

        @Override
        public LootItemBlockStatePropertyCondition m_7561_(JsonObject p_81805_, JsonDeserializationContext p_81806_) {
            ResourceLocation $$2 = new ResourceLocation(GsonHelper.m_13906_(p_81805_, "block"));
            Block $$3 = Registry.f_122824_.m_6612_($$2).orElseThrow(() -> new IllegalArgumentException("Can't find block " + $$2));
            StatePropertiesPredicate $$4 = StatePropertiesPredicate.m_67679_(p_81805_.get("properties"));
            $$4.m_67672_($$3.m_49965_(), p_81790_ -> {
                throw new JsonSyntaxException("Block " + $$3 + " has no property " + p_81790_);
            });
            return new LootItemBlockStatePropertyCondition($$3, $$4);
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


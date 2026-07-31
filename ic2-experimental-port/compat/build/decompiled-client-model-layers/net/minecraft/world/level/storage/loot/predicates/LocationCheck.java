/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.phys.Vec3;

public class LocationCheck
implements LootItemCondition {
    final LocationPredicate f_81716_;
    final BlockPos f_81717_;

    LocationCheck(LocationPredicate p_81719_, BlockPos p_81720_) {
        this.f_81716_ = p_81719_;
        this.f_81717_ = p_81720_;
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81823_;
    }

    @Override
    public boolean test(LootContext p_81731_) {
        Vec3 $$1 = p_81731_.m_78953_(LootContextParams.f_81460_);
        return $$1 != null && this.f_81716_.m_52617_(p_81731_.m_78952_(), $$1.m_7096_() + (double)this.f_81717_.m_123341_(), $$1.m_7098_() + (double)this.f_81717_.m_123342_(), $$1.m_7094_() + (double)this.f_81717_.m_123343_());
    }

    public static LootItemCondition.Builder m_81725_(LocationPredicate.Builder p_81726_) {
        return () -> new LocationCheck(p_81726_.m_52658_(), BlockPos.f_121853_);
    }

    public static LootItemCondition.Builder m_81727_(LocationPredicate.Builder p_81728_, BlockPos p_81729_) {
        return () -> new LocationCheck(p_81728_.m_52658_(), p_81729_);
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<LocationCheck> {
        @Override
        public void m_6170_(JsonObject p_81749_, LocationCheck p_81750_, JsonSerializationContext p_81751_) {
            p_81749_.add("predicate", p_81750_.f_81716_.m_52616_());
            if (p_81750_.f_81717_.m_123341_() != 0) {
                p_81749_.addProperty("offsetX", (Number)p_81750_.f_81717_.m_123341_());
            }
            if (p_81750_.f_81717_.m_123342_() != 0) {
                p_81749_.addProperty("offsetY", (Number)p_81750_.f_81717_.m_123342_());
            }
            if (p_81750_.f_81717_.m_123343_() != 0) {
                p_81749_.addProperty("offsetZ", (Number)p_81750_.f_81717_.m_123343_());
            }
        }

        @Override
        public LocationCheck m_7561_(JsonObject p_81757_, JsonDeserializationContext p_81758_) {
            LocationPredicate $$2 = LocationPredicate.m_52629_(p_81757_.get("predicate"));
            int $$3 = GsonHelper.m_13824_(p_81757_, "offsetX", 0);
            int $$4 = GsonHelper.m_13824_(p_81757_, "offsetY", 0);
            int $$5 = GsonHelper.m_13824_(p_81757_, "offsetZ", 0);
            return new LocationCheck($$2, new BlockPos($$3, $$4, $$5));
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


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
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class InvertedLootItemCondition
implements LootItemCondition {
    final LootItemCondition f_81681_;

    InvertedLootItemCondition(LootItemCondition p_81683_) {
        this.f_81681_ = p_81683_;
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81811_;
    }

    @Override
    public final boolean test(LootContext p_81689_) {
        return !this.f_81681_.test(p_81689_);
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return this.f_81681_.m_6231_();
    }

    @Override
    public void m_6169_(ValidationContext p_81691_) {
        LootItemCondition.super.m_6169_(p_81691_);
        this.f_81681_.m_6169_(p_81691_);
    }

    public static LootItemCondition.Builder m_81694_(LootItemCondition.Builder p_81695_) {
        InvertedLootItemCondition $$1 = new InvertedLootItemCondition(p_81695_.m_6409_());
        return () -> $$1;
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<InvertedLootItemCondition> {
        @Override
        public void m_6170_(JsonObject p_81706_, InvertedLootItemCondition p_81707_, JsonSerializationContext p_81708_) {
            p_81706_.add("term", p_81708_.serialize((Object)p_81707_.f_81681_));
        }

        @Override
        public InvertedLootItemCondition m_7561_(JsonObject p_81714_, JsonDeserializationContext p_81715_) {
            LootItemCondition $$2 = GsonHelper.m_13836_(p_81714_, "term", p_81715_, LootItemCondition.class);
            return new InvertedLootItemCondition($$2);
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


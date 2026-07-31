/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.providers.number;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public final class ConstantValue
implements NumberProvider {
    final float f_165688_;

    ConstantValue(float p_165690_) {
        this.f_165688_ = p_165690_;
    }

    @Override
    public LootNumberProviderType m_142587_() {
        return NumberProviders.f_165731_;
    }

    @Override
    public float m_142688_(LootContext p_165695_) {
        return this.f_165688_;
    }

    public static ConstantValue m_165692_(float p_165693_) {
        return new ConstantValue(p_165693_);
    }

    public boolean equals(Object p_165697_) {
        if (this == p_165697_) {
            return true;
        }
        if (p_165697_ == null || this.getClass() != p_165697_.getClass()) {
            return false;
        }
        return Float.compare(((ConstantValue)p_165697_).f_165688_, this.f_165688_) == 0;
    }

    public int hashCode() {
        return this.f_165688_ != 0.0f ? Float.floatToIntBits(this.f_165688_) : 0;
    }

    public static class InlineSerializer
    implements GsonAdapterFactory.InlineSerializer<ConstantValue> {
        @Override
        public JsonElement m_142413_(ConstantValue p_165704_, JsonSerializationContext p_165705_) {
            return new JsonPrimitive((Number)Float.valueOf(p_165704_.f_165688_));
        }

        @Override
        public ConstantValue m_142268_(JsonElement p_165710_, JsonDeserializationContext p_165711_) {
            return new ConstantValue(GsonHelper.m_13888_(p_165710_, "value"));
        }

        @Override
        public /* synthetic */ Object m_142268_(JsonElement jsonElement, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_142268_(jsonElement, jsonDeserializationContext);
        }
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<ConstantValue> {
        @Override
        public void m_6170_(JsonObject p_165717_, ConstantValue p_165718_, JsonSerializationContext p_165719_) {
            p_165717_.addProperty("value", (Number)Float.valueOf(p_165718_.f_165688_));
        }

        @Override
        public ConstantValue m_7561_(JsonObject p_165725_, JsonDeserializationContext p_165726_) {
            float $$2 = GsonHelper.m_13915_(p_165725_, "value");
            return new ConstantValue($$2);
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


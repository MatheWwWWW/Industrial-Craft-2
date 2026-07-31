/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.ImmutableSet$Builder
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class IntRange {
    @Nullable
    final NumberProvider f_165001_;
    @Nullable
    final NumberProvider f_165002_;
    private final IntLimiter f_165003_;
    private final IntChecker f_165004_;

    public Set<LootContextParam<?>> m_165008_() {
        ImmutableSet.Builder $$0 = ImmutableSet.builder();
        if (this.f_165001_ != null) {
            $$0.addAll(this.f_165001_.m_6231_());
        }
        if (this.f_165002_ != null) {
            $$0.addAll(this.f_165002_.m_6231_());
        }
        return $$0.build();
    }

    IntRange(@Nullable NumberProvider p_165006_, @Nullable NumberProvider p_165007_) {
        this.f_165001_ = p_165006_;
        this.f_165002_ = p_165007_;
        if (p_165006_ == null) {
            if (p_165007_ == null) {
                this.f_165003_ = (p_165050_, p_165051_) -> p_165051_;
                this.f_165004_ = (p_165043_, p_165044_) -> true;
            } else {
                this.f_165003_ = (p_165054_, p_165055_) -> Math.min(p_165007_.m_142683_(p_165054_), p_165055_);
                this.f_165004_ = (p_165047_, p_165048_) -> p_165048_ <= p_165007_.m_142683_(p_165047_);
            }
        } else if (p_165007_ == null) {
            this.f_165003_ = (p_165033_, p_165034_) -> Math.max(p_165006_.m_142683_(p_165033_), p_165034_);
            this.f_165004_ = (p_165019_, p_165020_) -> p_165020_ >= p_165006_.m_142683_(p_165019_);
        } else {
            this.f_165003_ = (p_165038_, p_165039_) -> Mth.m_14045_(p_165039_, p_165006_.m_142683_(p_165038_), p_165007_.m_142683_(p_165038_));
            this.f_165004_ = (p_165024_, p_165025_) -> p_165025_ >= p_165006_.m_142683_(p_165024_) && p_165025_ <= p_165007_.m_142683_(p_165024_);
        }
    }

    public static IntRange m_165009_(int p_165010_) {
        ConstantValue $$1 = ConstantValue.m_165692_(p_165010_);
        return new IntRange($$1, $$1);
    }

    public static IntRange m_165011_(int p_165012_, int p_165013_) {
        return new IntRange(ConstantValue.m_165692_(p_165012_), ConstantValue.m_165692_(p_165013_));
    }

    public static IntRange m_165026_(int p_165027_) {
        return new IntRange(ConstantValue.m_165692_(p_165027_), null);
    }

    public static IntRange m_165040_(int p_165041_) {
        return new IntRange(null, ConstantValue.m_165692_(p_165041_));
    }

    public int m_165014_(LootContext p_165015_, int p_165016_) {
        return this.f_165003_.m_165059_(p_165015_, p_165016_);
    }

    public boolean m_165028_(LootContext p_165029_, int p_165030_) {
        return this.f_165004_.m_165056_(p_165029_, p_165030_);
    }

    @FunctionalInterface
    static interface IntLimiter {
        public int m_165059_(LootContext var1, int var2);
    }

    @FunctionalInterface
    static interface IntChecker {
        public boolean m_165056_(LootContext var1, int var2);
    }

    public static class Serializer
    implements JsonDeserializer<IntRange>,
    JsonSerializer<IntRange> {
        public IntRange deserialize(JsonElement p_165064_, Type p_165065_, JsonDeserializationContext p_165066_) {
            if (p_165064_.isJsonPrimitive()) {
                return IntRange.m_165009_(p_165064_.getAsInt());
            }
            JsonObject $$3 = GsonHelper.m_13918_(p_165064_, "value");
            NumberProvider $$4 = $$3.has("min") ? GsonHelper.m_13836_($$3, "min", p_165066_, NumberProvider.class) : null;
            NumberProvider $$5 = $$3.has("max") ? GsonHelper.m_13836_($$3, "max", p_165066_, NumberProvider.class) : null;
            return new IntRange($$4, $$5);
        }

        public JsonElement serialize(IntRange p_165068_, Type p_165069_, JsonSerializationContext p_165070_) {
            JsonObject $$3 = new JsonObject();
            if (Objects.equals(p_165068_.f_165002_, p_165068_.f_165001_)) {
                return p_165070_.serialize((Object)p_165068_.f_165001_);
            }
            if (p_165068_.f_165002_ != null) {
                $$3.add("max", p_165070_.serialize((Object)p_165068_.f_165002_));
            }
            if (p_165068_.f_165001_ != null) {
                $$3.add("min", p_165070_.serialize((Object)p_165068_.f_165001_));
            }
            return $$3;
        }

        public /* synthetic */ JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((IntRange)object, type, jsonSerializationContext);
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}


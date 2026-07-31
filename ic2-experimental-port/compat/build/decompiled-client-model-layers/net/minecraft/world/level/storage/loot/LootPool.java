/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.world.level.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.MutableInt;

public class LootPool {
    final LootPoolEntryContainer[] f_79023_;
    final LootItemCondition[] f_79024_;
    private final Predicate<LootContext> f_79025_;
    final LootItemFunction[] f_79026_;
    private final BiFunction<ItemStack, LootContext, ItemStack> f_79027_;
    final NumberProvider f_79028_;
    final NumberProvider f_79029_;

    LootPool(LootPoolEntryContainer[] p_165128_, LootItemCondition[] p_165129_, LootItemFunction[] p_165130_, NumberProvider p_165131_, NumberProvider p_165132_) {
        this.f_79023_ = p_165128_;
        this.f_79024_ = p_165129_;
        this.f_79025_ = LootItemConditions.m_81834_(p_165129_);
        this.f_79026_ = p_165130_;
        this.f_79027_ = LootItemFunctions.m_80770_(p_165130_);
        this.f_79028_ = p_165131_;
        this.f_79029_ = p_165132_;
    }

    private void m_79058_(Consumer<ItemStack> p_79059_, LootContext p_79060_) {
        RandomSource $$2 = p_79060_.m_230907_();
        ArrayList $$3 = Lists.newArrayList();
        MutableInt $$4 = new MutableInt();
        for (LootPoolEntryContainer $$5 : this.f_79023_) {
            $$5.m_6562_(p_79060_, p_79048_ -> {
                int $$4 = p_79048_.m_7067_(p_79060_.m_78945_());
                if ($$4 > 0) {
                    $$3.add(p_79048_);
                    $$4.add($$4);
                }
            });
        }
        int $$6 = $$3.size();
        if ($$4.intValue() == 0 || $$6 == 0) {
            return;
        }
        if ($$6 == 1) {
            ((LootPoolEntry)$$3.get(0)).m_6941_(p_79059_, p_79060_);
            return;
        }
        int $$7 = $$2.m_188503_($$4.intValue());
        for (LootPoolEntry $$8 : $$3) {
            if (($$7 -= $$8.m_7067_(p_79060_.m_78945_())) >= 0) continue;
            $$8.m_6941_(p_79059_, p_79060_);
            return;
        }
    }

    public void m_79053_(Consumer<ItemStack> p_79054_, LootContext p_79055_) {
        if (!this.f_79025_.test(p_79055_)) {
            return;
        }
        Consumer<ItemStack> $$2 = LootItemFunction.m_80724_(this.f_79027_, p_79054_, p_79055_);
        int $$3 = this.f_79028_.m_142683_(p_79055_) + Mth.m_14143_(this.f_79029_.m_142688_(p_79055_) * p_79055_.m_78945_());
        for (int $$4 = 0; $$4 < $$3; ++$$4) {
            this.m_79058_($$2, p_79055_);
        }
    }

    public void m_79051_(ValidationContext p_79052_) {
        for (int $$1 = 0; $$1 < this.f_79024_.length; ++$$1) {
            this.f_79024_[$$1].m_6169_(p_79052_.m_79365_(".condition[" + $$1 + "]"));
        }
        for (int $$2 = 0; $$2 < this.f_79026_.length; ++$$2) {
            this.f_79026_[$$2].m_6169_(p_79052_.m_79365_(".functions[" + $$2 + "]"));
        }
        for (int $$3 = 0; $$3 < this.f_79023_.length; ++$$3) {
            this.f_79023_[$$3].m_6165_(p_79052_.m_79365_(".entries[" + $$3 + "]"));
        }
        this.f_79028_.m_6169_(p_79052_.m_79365_(".rolls"));
        this.f_79029_.m_6169_(p_79052_.m_79365_(".bonusRolls"));
    }

    public static Builder m_79043_() {
        return new Builder();
    }

    public static class Builder
    implements FunctionUserBuilder<Builder>,
    ConditionUserBuilder<Builder> {
        private final List<LootPoolEntryContainer> f_79067_ = Lists.newArrayList();
        private final List<LootItemCondition> f_79068_ = Lists.newArrayList();
        private final List<LootItemFunction> f_79069_ = Lists.newArrayList();
        private NumberProvider f_79070_ = ConstantValue.m_165692_(1.0f);
        private NumberProvider f_79071_ = ConstantValue.m_165692_(0.0f);

        public Builder m_165133_(NumberProvider p_165134_) {
            this.f_79070_ = p_165134_;
            return this;
        }

        @Override
        public Builder m_79073_() {
            return this;
        }

        public Builder m_165135_(NumberProvider p_165136_) {
            this.f_79071_ = p_165136_;
            return this;
        }

        public Builder m_79076_(LootPoolEntryContainer.Builder<?> p_79077_) {
            this.f_79067_.add(p_79077_.m_7512_());
            return this;
        }

        @Override
        public Builder m_79080_(LootItemCondition.Builder p_79081_) {
            this.f_79068_.add(p_79081_.m_6409_());
            return this;
        }

        @Override
        public Builder m_79078_(LootItemFunction.Builder p_79079_) {
            this.f_79069_.add(p_79079_.m_7453_());
            return this;
        }

        public LootPool m_79082_() {
            if (this.f_79070_ == null) {
                throw new IllegalArgumentException("Rolls not set");
            }
            return new LootPool(this.f_79067_.toArray(new LootPoolEntryContainer[0]), this.f_79068_.toArray(new LootItemCondition[0]), this.f_79069_.toArray(new LootItemFunction[0]), this.f_79070_, this.f_79071_);
        }

        @Override
        public /* synthetic */ FunctionUserBuilder m_79073_() {
            return this.m_79073_();
        }

        @Override
        public /* synthetic */ FunctionUserBuilder m_79078_(LootItemFunction.Builder builder) {
            return this.m_79078_(builder);
        }

        @Override
        public /* synthetic */ ConditionUserBuilder m_79073_() {
            return this.m_79073_();
        }

        @Override
        public /* synthetic */ ConditionUserBuilder m_79080_(LootItemCondition.Builder builder) {
            return this.m_79080_(builder);
        }
    }

    public static class Serializer
    implements JsonDeserializer<LootPool>,
    JsonSerializer<LootPool> {
        public LootPool deserialize(JsonElement p_79090_, Type p_79091_, JsonDeserializationContext p_79092_) throws JsonParseException {
            JsonObject $$3 = GsonHelper.m_13918_(p_79090_, "loot pool");
            LootPoolEntryContainer[] $$4 = GsonHelper.m_13836_($$3, "entries", p_79092_, LootPoolEntryContainer[].class);
            LootItemCondition[] $$5 = GsonHelper.m_13845_($$3, "conditions", new LootItemCondition[0], p_79092_, LootItemCondition[].class);
            LootItemFunction[] $$6 = GsonHelper.m_13845_($$3, "functions", new LootItemFunction[0], p_79092_, LootItemFunction[].class);
            NumberProvider $$7 = GsonHelper.m_13836_($$3, "rolls", p_79092_, NumberProvider.class);
            NumberProvider $$8 = GsonHelper.m_13845_($$3, "bonus_rolls", ConstantValue.m_165692_(0.0f), p_79092_, NumberProvider.class);
            return new LootPool($$4, $$5, $$6, $$7, $$8);
        }

        public JsonElement serialize(LootPool p_79094_, Type p_79095_, JsonSerializationContext p_79096_) {
            JsonObject $$3 = new JsonObject();
            $$3.add("rolls", p_79096_.serialize((Object)p_79094_.f_79028_));
            $$3.add("bonus_rolls", p_79096_.serialize((Object)p_79094_.f_79029_));
            $$3.add("entries", p_79096_.serialize((Object)p_79094_.f_79023_));
            if (!ArrayUtils.isEmpty((Object[])p_79094_.f_79024_)) {
                $$3.add("conditions", p_79096_.serialize((Object)p_79094_.f_79024_));
            }
            if (!ArrayUtils.isEmpty((Object[])p_79094_.f_79026_)) {
                $$3.add("functions", p_79096_.serialize((Object)p_79094_.f_79026_));
            }
            return $$3;
        }

        public /* synthetic */ JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((LootPool)object, type, jsonSerializationContext);
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}


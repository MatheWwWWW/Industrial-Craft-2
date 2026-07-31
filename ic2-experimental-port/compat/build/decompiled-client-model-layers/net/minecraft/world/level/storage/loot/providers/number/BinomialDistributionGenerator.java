/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.providers.number;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public final class BinomialDistributionGenerator
implements NumberProvider {
    final NumberProvider f_165653_;
    final NumberProvider f_165654_;

    BinomialDistributionGenerator(NumberProvider p_165656_, NumberProvider p_165657_) {
        this.f_165653_ = p_165656_;
        this.f_165654_ = p_165657_;
    }

    @Override
    public LootNumberProviderType m_142587_() {
        return NumberProviders.f_165733_;
    }

    @Override
    public int m_142683_(LootContext p_165663_) {
        int $$1 = this.f_165653_.m_142683_(p_165663_);
        float $$2 = this.f_165654_.m_142688_(p_165663_);
        RandomSource $$3 = p_165663_.m_230907_();
        int $$4 = 0;
        for (int $$5 = 0; $$5 < $$1; ++$$5) {
            if (!($$3.m_188501_() < $$2)) continue;
            ++$$4;
        }
        return $$4;
    }

    @Override
    public float m_142688_(LootContext p_165666_) {
        return this.m_142683_(p_165666_);
    }

    public static BinomialDistributionGenerator m_165659_(int p_165660_, float p_165661_) {
        return new BinomialDistributionGenerator(ConstantValue.m_165692_(p_165660_), ConstantValue.m_165692_(p_165661_));
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return Sets.union(this.f_165653_.m_6231_(), this.f_165654_.m_6231_());
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<BinomialDistributionGenerator> {
        @Override
        public BinomialDistributionGenerator m_7561_(JsonObject p_165680_, JsonDeserializationContext p_165681_) {
            NumberProvider $$2 = GsonHelper.m_13836_(p_165680_, "n", p_165681_, NumberProvider.class);
            NumberProvider $$3 = GsonHelper.m_13836_(p_165680_, "p", p_165681_, NumberProvider.class);
            return new BinomialDistributionGenerator($$2, $$3);
        }

        @Override
        public void m_6170_(JsonObject p_165672_, BinomialDistributionGenerator p_165673_, JsonSerializationContext p_165674_) {
            p_165672_.add("n", p_165674_.serialize((Object)p_165673_.f_165653_));
            p_165672_.add("p", p_165674_.serialize((Object)p_165673_.f_165654_));
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


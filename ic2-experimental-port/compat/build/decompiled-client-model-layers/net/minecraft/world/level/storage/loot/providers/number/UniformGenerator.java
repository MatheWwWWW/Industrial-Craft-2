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
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public class UniformGenerator
implements NumberProvider {
    final NumberProvider f_165774_;
    final NumberProvider f_165775_;

    UniformGenerator(NumberProvider p_165777_, NumberProvider p_165778_) {
        this.f_165774_ = p_165777_;
        this.f_165775_ = p_165778_;
    }

    @Override
    public LootNumberProviderType m_142587_() {
        return NumberProviders.f_165732_;
    }

    public static UniformGenerator m_165780_(float p_165781_, float p_165782_) {
        return new UniformGenerator(ConstantValue.m_165692_(p_165781_), ConstantValue.m_165692_(p_165782_));
    }

    @Override
    public int m_142683_(LootContext p_165784_) {
        return Mth.m_216271_(p_165784_.m_230907_(), this.f_165774_.m_142683_(p_165784_), this.f_165775_.m_142683_(p_165784_));
    }

    @Override
    public float m_142688_(LootContext p_165787_) {
        return Mth.m_216267_(p_165787_.m_230907_(), this.f_165774_.m_142688_(p_165787_), this.f_165775_.m_142688_(p_165787_));
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return Sets.union(this.f_165774_.m_6231_(), this.f_165775_.m_6231_());
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<UniformGenerator> {
        @Override
        public UniformGenerator m_7561_(JsonObject p_165801_, JsonDeserializationContext p_165802_) {
            NumberProvider $$2 = GsonHelper.m_13836_(p_165801_, "min", p_165802_, NumberProvider.class);
            NumberProvider $$3 = GsonHelper.m_13836_(p_165801_, "max", p_165802_, NumberProvider.class);
            return new UniformGenerator($$2, $$3);
        }

        @Override
        public void m_6170_(JsonObject p_165793_, UniformGenerator p_165794_, JsonSerializationContext p_165795_) {
            p_165793_.add("min", p_165795_.serialize((Object)p_165794_.f_165774_));
            p_165793_.add("max", p_165795_.serialize((Object)p_165794_.f_165775_));
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


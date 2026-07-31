/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.slf4j.Logger;

public class PredicateManager
extends SimpleJsonResourceReloadListener {
    private static final Logger f_79227_ = LogUtils.getLogger();
    private static final Gson f_79228_ = Deserializers.m_78798_().create();
    private Map<ResourceLocation, LootItemCondition> f_79229_ = ImmutableMap.of();

    public PredicateManager() {
        super(f_79228_, "predicates");
    }

    @Nullable
    public LootItemCondition m_79252_(ResourceLocation p_79253_) {
        return this.f_79229_.get(p_79253_);
    }

    @Override
    protected void m_5787_(Map<ResourceLocation, JsonElement> p_79249_, ResourceManager p_79250_, ProfilerFiller p_79251_) {
        ImmutableMap.Builder $$3 = ImmutableMap.builder();
        p_79249_.forEach((p_79235_, p_79236_) -> {
            try {
                if (p_79236_.isJsonArray()) {
                    LootItemCondition[] $$3 = (LootItemCondition[])f_79228_.fromJson(p_79236_, LootItemCondition[].class);
                    $$3.put(p_79235_, (Object)new CompositePredicate($$3));
                } else {
                    LootItemCondition $$4 = (LootItemCondition)f_79228_.fromJson(p_79236_, LootItemCondition.class);
                    $$3.put(p_79235_, (Object)$$4);
                }
            }
            catch (Exception $$5) {
                f_79227_.error("Couldn't parse loot table {}", p_79235_, (Object)$$5);
            }
        });
        ImmutableMap $$4 = $$3.build();
        ValidationContext $$5 = new ValidationContext(LootContextParamSets.f_81420_, ((Map)$$4)::get, p_79255_ -> null);
        $$4.forEach((p_79239_, p_79240_) -> p_79240_.m_6169_($$5.m_79367_("{" + p_79239_ + "}", (ResourceLocation)p_79239_)));
        $$5.m_79352_().forEach((p_79246_, p_79247_) -> f_79227_.warn("Found validation problem in {}: {}", p_79246_, p_79247_));
        this.f_79229_ = $$4;
    }

    public Set<ResourceLocation> m_79232_() {
        return Collections.unmodifiableSet(this.f_79229_.keySet());
    }

    static class CompositePredicate
    implements LootItemCondition {
        private final LootItemCondition[] f_79256_;
        private final Predicate<LootContext> f_79257_;

        CompositePredicate(LootItemCondition[] p_79259_) {
            this.f_79256_ = p_79259_;
            this.f_79257_ = LootItemConditions.m_81834_(p_79259_);
        }

        @Override
        public final boolean test(LootContext p_79264_) {
            return this.f_79257_.test(p_79264_);
        }

        @Override
        public void m_6169_(ValidationContext p_79266_) {
            LootItemCondition.super.m_6169_(p_79266_);
            for (int $$1 = 0; $$1 < this.f_79256_.length; ++$$1) {
                this.f_79256_[$$1].m_6169_(p_79266_.m_79365_(".term[" + $$1 + "]"));
            }
        }

        @Override
        public LootItemConditionType m_7940_() {
            throw new UnsupportedOperationException();
        }

        @Override
        public /* synthetic */ boolean test(Object object) {
            return this.test((LootContext)object);
        }
    }
}


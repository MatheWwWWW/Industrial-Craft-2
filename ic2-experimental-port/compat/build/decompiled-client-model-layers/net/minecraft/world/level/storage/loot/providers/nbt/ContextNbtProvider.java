/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot.providers.nbt;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.nbt.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.nbt.LootNbtProviderType;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProviders;

public class ContextNbtProvider
implements NbtProvider {
    private static final String f_165563_ = "block_entity";
    private static final Getter f_165564_ = new Getter(){

        @Override
        public Tag m_142135_(LootContext p_165582_) {
            BlockEntity $$1 = p_165582_.m_78953_(LootContextParams.f_81462_);
            return $$1 != null ? $$1.m_187480_() : null;
        }

        @Override
        public String m_142016_() {
            return ContextNbtProvider.f_165563_;
        }

        @Override
        public Set<LootContextParam<?>> m_142524_() {
            return ImmutableSet.of(LootContextParams.f_81462_);
        }
    };
    public static final ContextNbtProvider f_165562_ = new ContextNbtProvider(f_165564_);
    final Getter f_165565_;

    private static Getter m_165577_(final LootContext.EntityTarget p_165578_) {
        return new Getter(){

            @Override
            @Nullable
            public Tag m_142135_(LootContext p_165589_) {
                Entity $$1 = p_165589_.m_78953_(p_165578_.m_79003_());
                return $$1 != null ? NbtPredicate.m_57485_($$1) : null;
            }

            @Override
            public String m_142016_() {
                return p_165578_.name();
            }

            @Override
            public Set<LootContextParam<?>> m_142524_() {
                return ImmutableSet.of(p_165578_.m_79003_());
            }
        };
    }

    private ContextNbtProvider(Getter p_165568_) {
        this.f_165565_ = p_165568_;
    }

    @Override
    public LootNbtProviderType m_142624_() {
        return NbtProviders.f_165624_;
    }

    @Override
    @Nullable
    public Tag m_142301_(LootContext p_165573_) {
        return this.f_165565_.m_142135_(p_165573_);
    }

    @Override
    public Set<LootContextParam<?>> m_142677_() {
        return this.f_165565_.m_142524_();
    }

    public static NbtProvider m_165570_(LootContext.EntityTarget p_165571_) {
        return new ContextNbtProvider(ContextNbtProvider.m_165577_(p_165571_));
    }

    static ContextNbtProvider m_165574_(String p_165575_) {
        if (p_165575_.equals(f_165563_)) {
            return new ContextNbtProvider(f_165564_);
        }
        LootContext.EntityTarget $$1 = LootContext.EntityTarget.m_79006_(p_165575_);
        return new ContextNbtProvider(ContextNbtProvider.m_165577_($$1));
    }

    static interface Getter {
        @Nullable
        public Tag m_142135_(LootContext var1);

        public String m_142016_();

        public Set<LootContextParam<?>> m_142524_();
    }

    public static class InlineSerializer
    implements GsonAdapterFactory.InlineSerializer<ContextNbtProvider> {
        @Override
        public JsonElement m_142413_(ContextNbtProvider p_165597_, JsonSerializationContext p_165598_) {
            return new JsonPrimitive(p_165597_.f_165565_.m_142016_());
        }

        @Override
        public ContextNbtProvider m_142268_(JsonElement p_165603_, JsonDeserializationContext p_165604_) {
            String $$2 = p_165603_.getAsString();
            return ContextNbtProvider.m_165574_($$2);
        }

        @Override
        public /* synthetic */ Object m_142268_(JsonElement jsonElement, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_142268_(jsonElement, jsonDeserializationContext);
        }
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<ContextNbtProvider> {
        @Override
        public void m_6170_(JsonObject p_165610_, ContextNbtProvider p_165611_, JsonSerializationContext p_165612_) {
            p_165610_.addProperty("target", p_165611_.f_165565_.m_142016_());
        }

        @Override
        public ContextNbtProvider m_7561_(JsonObject p_165618_, JsonDeserializationContext p_165619_) {
            String $$2 = GsonHelper.m_13906_(p_165618_, "target");
            return ContextNbtProvider.m_165574_($$2);
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}


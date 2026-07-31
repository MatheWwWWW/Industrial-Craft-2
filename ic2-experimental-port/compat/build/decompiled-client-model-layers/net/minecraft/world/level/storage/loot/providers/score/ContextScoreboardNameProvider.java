/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot.providers.score;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.score.LootScoreProviderType;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProvider;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProviders;

public class ContextScoreboardNameProvider
implements ScoreboardNameProvider {
    final LootContext.EntityTarget f_165803_;

    ContextScoreboardNameProvider(LootContext.EntityTarget p_165805_) {
        this.f_165803_ = p_165805_;
    }

    public static ScoreboardNameProvider m_165807_(LootContext.EntityTarget p_165808_) {
        return new ContextScoreboardNameProvider(p_165808_);
    }

    @Override
    public LootScoreProviderType m_142680_() {
        return ScoreboardNameProviders.f_165869_;
    }

    @Override
    @Nullable
    public String m_142600_(LootContext p_165810_) {
        Entity $$1 = p_165810_.m_78953_(this.f_165803_.m_79003_());
        return $$1 != null ? $$1.m_6302_() : null;
    }

    @Override
    public Set<LootContextParam<?>> m_142636_() {
        return ImmutableSet.of(this.f_165803_.m_79003_());
    }

    public static class InlineSerializer
    implements GsonAdapterFactory.InlineSerializer<ContextScoreboardNameProvider> {
        @Override
        public JsonElement m_142413_(ContextScoreboardNameProvider p_165817_, JsonSerializationContext p_165818_) {
            return p_165818_.serialize((Object)p_165817_.f_165803_);
        }

        @Override
        public ContextScoreboardNameProvider m_142268_(JsonElement p_165823_, JsonDeserializationContext p_165824_) {
            LootContext.EntityTarget $$2 = (LootContext.EntityTarget)((Object)p_165824_.deserialize(p_165823_, LootContext.EntityTarget.class));
            return new ContextScoreboardNameProvider($$2);
        }

        @Override
        public /* synthetic */ Object m_142268_(JsonElement jsonElement, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_142268_(jsonElement, jsonDeserializationContext);
        }
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<ContextScoreboardNameProvider> {
        @Override
        public void m_6170_(JsonObject p_165830_, ContextScoreboardNameProvider p_165831_, JsonSerializationContext p_165832_) {
            p_165830_.addProperty("target", p_165831_.f_165803_.name());
        }

        @Override
        public ContextScoreboardNameProvider m_7561_(JsonObject p_165838_, JsonDeserializationContext p_165839_) {
            LootContext.EntityTarget $$2 = GsonHelper.m_13836_(p_165838_, "target", p_165839_, LootContext.EntityTarget.class);
            return new ContextScoreboardNameProvider($$2);
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}

